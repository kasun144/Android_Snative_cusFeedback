package com.example.np_fb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ButtMainActivity extends AppCompatActivity implements View.OnClickListener {

    String ServerURL = "http://220.247.222.131/REAL/buttons.php";
    EditText name, email;
    Button button;
    String TempName, TempEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttactivity_main);


        name = (EditText) findViewById(R.id.editTextName);
        // email = (EditText)findViewById(R.id.editText3);
        //  button = (Button)findViewById(R.id.button);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        // click in button "no"
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);


    }

    public void GetData() {

        TempName = name.getText().toString();

        //TempEmail = email.getText().toString();

    }

    //public void InsertData(final String name, final String email){
    public void InsertData(final String name) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String NameHolder = name;
                // String EmailHolder = email ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("red", NameHolder));
                // nameValuePairs.add(new BasicNameValuePair("email", EmailHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(ButtMainActivity.this, "Data Submit Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ButtMainActivity.this,RMainActivity.class);
                startActivity(intent);

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

//sendPostReqAsyncTask.execute(name, email);
        sendPostReqAsyncTask.execute(name);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.button1:
                name.setText("Yes");
                GetData();

                //InsertData(TempName, TempEmail);
                InsertData(TempName);
                break;
            case R.id.button2:
                name.setText("No");
                GetData();

                //InsertData(TempName, TempEmail);
                InsertData(TempName);
                break;

        }

    }
}

