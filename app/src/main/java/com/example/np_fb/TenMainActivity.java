package com.example.np_fb;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

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
import java.util.jar.Attributes;

public class TenMainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text;
    private RatingBar rating;

    //ipv4 local host address
    public static final String URL_SAVE_NAME = "http://192.168.10.100/REAL/tenrate.php";
    //public static final String URL_SAVE_NAME = "http://172.30.6.87/REAL/tenrate.php";


    //View objects
    private Button buttonSave;
    private TextView editTextName;
    private ListView listViewNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenactivity_main);



        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (TextView) findViewById(R.id.editTextName);
        listViewNames = (ListView) findViewById(R.id.listViewNames);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        text = (TextView)findViewById(R.id.textView);

        //adding click listener to button
        buttonSave.setOnClickListener(this);

        rating.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        text.setText(String.valueOf(rating));
                    }
                }
        );


    }


    private void saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        Intent intent = new Intent(TenMainActivity.this,CotwoMainActivity.class);
        startActivity(intent);


        final String name = text.getText().toString().trim();

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
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
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
