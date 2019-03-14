package com.example.np_fb;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class R3MainActivity extends AppCompatActivity implements View.OnClickListener {

    //ipv4 local host address
    public static final String URL_SAVE_NAME = "http://192.168.10.100/real/rate3.php";

    //database helper object
    private R3DatabaseHelper db;

    //View objects
    private Button buttonSave;
    private TextView text1,text2,text3;
    private RatingBar rating1,rating2,rating3;



    // private EditText editTextEmail;

    private ListView listViewNames;

    //List to store all the names
    private List<R3Name> names;

    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;


    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";




    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //adapterobject for list view
    private R3NameAdapter nameAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r3activity_main);

        registerReceiver(new R3NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        //initializing views and objects
        db = new R3DatabaseHelper(this);
        names = new ArrayList<>();

        buttonSave = findViewById(R.id.buttonSave);
        //editTextName = findViewById(R.id.editTextName);
       // editTextEmail= findViewById(R.id.editTextEmail);
       // editTextNumber = findViewById(R.id.editTextNumber);
        listViewNames = findViewById(R.id.listViewNames);
        rating1 = findViewById(R.id.ratingBar1);
        rating2 = findViewById(R.id.ratingBar2);
        rating3= findViewById(R.id.ratingBar3);

        text1 = findViewById(R.id.textView1);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);

        //adding click listener to button
        buttonSave.setOnClickListener(this);

        rating1.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        text1.setText(String.valueOf(rating));
                    }
                }
        );

        rating2.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        text2.setText(String.valueOf(rating));
                    }
                }
        );

        rating3.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        text3.setText(String.valueOf(rating));
                    }
                }
        );





        //calling the method to load all the stored names
        loadNames();

        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
                loadNames();
            }
        };

        //registering the broadcast receiver to update sync status
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
    }

    /*
     * this method will
     * load the names from the database
     * with updated sync status
     * */
    private void loadNames() {
        names.clear();
        Cursor cursor = db.getNames();
        if (cursor.moveToFirst()) {
            do {
                R3Name name = new R3Name(
                        cursor.getString(cursor.getColumnIndex(R3DatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(R3DatabaseHelper.COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(R3DatabaseHelper.COLUMN_CONTACTNO)),
                        cursor.getInt(cursor.getColumnIndex(R3DatabaseHelper.COLUMN_STATUS))
                );
                names.add(name);
            } while (cursor.moveToNext());
        }

        nameAdapter = new R3NameAdapter(this, R.layout.r3names, names);
        listViewNames.setAdapter(nameAdapter);
    }

    /*
     * this method will simply refresh the list
     * */
    private void refreshList() {
        nameAdapter.notifyDataSetChanged();
    }

    /*
     * this method is saving the name to ther server
     * */
    private boolean saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();


        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 5000);


        Intent intent = new Intent(R3MainActivity.this,TenMainActivity.class);
        startActivity(intent);


        //editTextName
        final String name = text1.getText().toString().trim();
        final String email = text2.getText().toString().trim();
        final String contactno = text3.getText().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced
                                saveNameToLocalStorage(name,email,contactno, NAME_SYNCED_WITH_SERVER);
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(name,email,contactno, NAME_NOT_SYNCED_WITH_SERVER);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //on error storing the name to sqlite with status unsynced
                        saveNameToLocalStorage(name,email,contactno,NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("contactno", contactno);


                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        return false;
    }

    //saving the name to local storage
    private void saveNameToLocalStorage(String name,String email,String contactno, int status) {
        text1.setText("");
        text2.setText("");
        text3.setText("");


        db.addName(name,email,contactno,status);
        R3Name n = new R3Name(name,email,contactno,status);
        names.add(n);
        refreshList();
    }

    @Override
    public void onClick(View view) {


        saveNameToServer();

    }
}
