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

public class ButtMainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     * this is the url to our webservice
     * make sure you are using the ip instead of localhost
     * it will not work if you are using localhost
     * */
   // public static final String URL_SAVE_NAME = "http://172.30.6.87/real/buttons.php";
   public static final String URL_SAVE_NAME = "http://192.168.10.100/REAL/buttons.php";



    //View objects
    private Button buttonSave;
    private EditText editTextName;
    private ListView listViewNames;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttactivity_main);


        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (EditText) findViewById(R.id.editTextName);
        listViewNames = (ListView) findViewById(R.id.listViewNames);

        //click in button "yes"
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        // click in button "no"
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        //adding click listener to button
        buttonSave.setOnClickListener(this);


    }


    private void saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();


        Intent intent = new Intent(ButtMainActivity.this,RMainActivity.class);
        startActivity(intent);

        final String red = editTextName.getText().toString().trim();

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("red", red);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {

        //displaying message"Viewtext"(responseText) tranfer to "Edittext"
        EditText responseText = (EditText) findViewById(R.id.editTextName);

        switch (view.getId()) {
            case R.id.button1:
                responseText.setText("Yes");
                saveNameToServer();
                break;
            case R.id.button2:
                responseText.setText("No");
                saveNameToServer();
                break;
           /* case R.id.buttonSave:
                saveNameToServer();*/
        }
    }
}
