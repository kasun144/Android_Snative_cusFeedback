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
    public static final String URL_SAVE_NAME = "http://192.168.10.100/REAL/saveName.php";
    //public static final String URL_SAVE_NAME = "http://172.30.6.87/REAL/saveName.php";



    // private EditText editTextEmail;
    //database helper object

    //View objects
    private Button buttonSave;
    private EditText editTextName, editTextEmail, editTextNumber;
    private ListView listViewNames;
    //List to store all the names


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dactivity_main);


        buttonSave = findViewById(R.id.buttonSave);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNumber = findViewById(R.id.editTextNumber);
        listViewNames = findViewById(R.id.listViewNames);

        //adding click listener to button
        buttonSave.setOnClickListener(this);


    }


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

                            } else {

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


    @Override
    public void onClick(View view) {

        saveNameToServer();

        // Toast.makeText(this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();

    }
}
