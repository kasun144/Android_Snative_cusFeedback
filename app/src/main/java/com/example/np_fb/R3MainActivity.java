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

public class R3MainActivity extends AppCompatActivity implements View.OnClickListener {

    //ipv4 local host address
    public static final String URL_SAVE_NAME = "http://192.168.10.100/REAL/rate3.php";
   // public static final String URL_SAVE_NAME = "http://172.30.6.87/real/rate3.php";



    //View objects
    private Button buttonSave;
    private TextView text1,text2,text3;
    private RatingBar rating1,rating2,rating3;



    // private EditText editTextEmail;

    private ListView listViewNames;

    //List to store all the names







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r3activity_main);



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

    }


    private boolean saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

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

                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced

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


    @Override
    public void onClick(View view) {


        saveNameToServer();

    }
}
