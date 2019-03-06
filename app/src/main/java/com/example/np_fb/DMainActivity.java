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
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
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

public class DMainActivity extends AppCompatActivity implements View.OnClickListener {

    //ipv4 local host address
    public static final String URL_SAVE_NAME = "http://192.168.42.13/REAL/saveName.php";
    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";


    // private EditText editTextEmail;
    //database helper object
    private DDatabaseHelper db;
    //View objects
    private Button buttonSave;
    private EditText editTextName, editTextEmail, editTextNumber;
    private ListView listViewNames;
    //List to store all the names
    private List<DName> names;
    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //adapterobject for list view
    private DNameAdapter nameAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dactivity_main);

        registerReceiver(new DNetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        //initializing views and objects
        db = new DDatabaseHelper(this);
        names = new ArrayList<>();

        buttonSave = findViewById(R.id.buttonSave);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNumber = findViewById(R.id.editTextNumber);
        listViewNames = findViewById(R.id.listViewNames);

        //adding click listener to button
        buttonSave.setOnClickListener(this);


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
                DName name = new DName(
                        cursor.getString(cursor.getColumnIndex(DDatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(DDatabaseHelper.COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(DDatabaseHelper.COLUMN_CONTACTNO)),
                        cursor.getInt(cursor.getColumnIndex(DDatabaseHelper.COLUMN_STATUS))
                );
                names.add(name);
            } while (cursor.moveToNext());
        }

        nameAdapter = new DNameAdapter(this, R.layout.dnames, names);
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
    private void saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        //editTextName
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String contactno = editTextNumber.getText().toString().trim();



        if (name.isEmpty()|| name.length() < 3){
            editTextName.setError("Please Enter Your Name");
            editTextName.requestFocus();

        }

        // if(contactno.isEmpty()|| contactno.length()!=15){
        if(contactno.isEmpty()){
            editTextNumber.setError("Enter Valid Mobile Number");
            editTextNumber.requestFocus();

        }
        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter A Valid Email Address");
            editTextEmail.requestFocus();
        }
        else{
            //final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Saving...");
            progressDialog.show();

            Intent intent = new Intent(DMainActivity.this,finalSplashScreen.class);
            startActivity(intent);

        }


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
                                saveNameToLocalStorage(name, email, contactno, NAME_SYNCED_WITH_SERVER);
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(name, email, contactno, NAME_NOT_SYNCED_WITH_SERVER);
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
                        saveNameToLocalStorage(name, email, contactno, NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("contactno", contactno);


                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    //saving the name to local storage
    private void saveNameToLocalStorage(String name, String email, String contactno, int status) {
        editTextName.setText("");
        editTextEmail.setText("");
        editTextNumber.setText("");


        db.addName(name, email, contactno, status);
        DName n = new DName(name, email, contactno, status);
        names.add(n);
        refreshList();
    }

    @Override
    public void onClick(View view) {




        saveNameToServer();

        // Toast.makeText(this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();

    }
}
