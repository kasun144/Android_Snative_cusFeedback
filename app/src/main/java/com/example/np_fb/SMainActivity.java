package com.example.np_fb;

/*
S.D.M.K.S.S.Dissanayake
*/


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Timer;
import java.util.TimerTask;

public class SMainActivity extends AppCompatActivity {

    //220.247.222.131
   String ServerURL =  "http://220.247.222.131/REAL/location.php" ;
    Spinner name, email ;
    Button button;
    String TempName, TempEmail ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_main);

        name = (Spinner)findViewById(R.id.editSpinnerLocation);
        // email = (EditText)findViewById(R.id.editText3);
        button = (Button)findViewById(R.id.buttonSave);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetData();

                //InsertData(TempName, TempEmail);
                InsertData(TempName);

                Intent intent = new Intent(SMainActivity.this,ButtMainActivity.class);
                startActivity(intent);


            }
        });
    }

    public void GetData(){

        TempName = name.getSelectedItem().toString().trim();
        //TempEmail = email.getText().toString();

    }

    //public void InsertData(final String name, final String email){
    public void InsertData(final String name){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String NameHolder = name ;
                // String EmailHolder = email ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", NameHolder));
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

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        //sendPostReqAsyncTask.execute(name, email);
        sendPostReqAsyncTask.execute(name);
    }

}
