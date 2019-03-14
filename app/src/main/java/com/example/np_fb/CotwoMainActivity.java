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

public class CotwoMainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     * this is the url to our webservice
     * make sure you are using the ip instead of localhost
     * it will not work if you are using localhost
     * */
    public static final String URL_SAVE_NAME = "http://192.168.10.100/REAL/commentsecond.php";
    //public static final String URL_SAVE_NAME = "http://172.30.6.87/REAL/commentsecond.php";

    //database helper object


    //View objects
    private Button buttonSave;
    private EditText editTextName;
    private ListView listViewNames;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cotwoactivity_main);



        buttonSave = findViewById(R.id.buttonSave);
        editTextName = findViewById(R.id.editTextName);
        listViewNames = findViewById(R.id.listViewNames);

        //adding click listener to button
        buttonSave.setOnClickListener(this);


    }


    private void saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        Intent intent = new Intent(CotwoMainActivity.this,DMainActivity.class);
        startActivity(intent);

        final String name = editTextName.getText().toString().trim();

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
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onClick(View view) {
        saveNameToServer();
    }
}
