package com.example.np_fb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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


public class DMainActivity extends AppCompatActivity {

    String ServerURL =  "http://220.247.222.131/REAL/saveName.php" ;
    EditText name, email,contactno ;
    Button button;
    String TempName, TempEmail ,TempContactno ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dactivity_main);

        name = (EditText)findViewById(R.id.editTextName);
        email = (EditText)findViewById(R.id.editTextEmail);
        contactno = (EditText)findViewById(R.id.editTextNumber);
        button = (Button)findViewById(R.id.buttonSave);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetData();

                InsertData(TempName, TempEmail , TempContactno);
                //InsertData(TempName);

            }
        });
    }


    public void GetData(){

        TempName = name.getText().toString();
        TempEmail = email.getText().toString();
        TempContactno = contactno.getText().toString();



        if (TempName.isEmpty()|| TempName.length() < 3){
            name.setError("Please Enter Your Name");
            name.requestFocus();

        }

        if(TempContactno.isEmpty()){
            contactno.setError("Enter Valid Mobile Number");
            contactno.requestFocus();

        }

        if(TempEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(TempEmail).matches()) {
            email.setError("Enter A Valid Email Address");
            email.requestFocus();
        }
        else{

            Intent intent = new Intent(DMainActivity.this,finalSplashScreen.class);
            startActivity(intent);

        }


    }
    //public void InsertData(final String name, final String email){
    public void InsertData ( final String name, final String email, final String contactno ){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                String NameHolder = name;
                String EmailHolder = email;
                String ContactHolder = contactno;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("email", EmailHolder));
                nameValuePairs.add(new BasicNameValuePair("contactno", ContactHolder));

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

                
              //  Toast.makeText(DMainActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                /*Intent intent = new Intent(DMainActivity.this,finalSplashScreen.class);
                startActivity(intent);*/


            }
        }


        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(name, email, contactno);
        // sendPostReqAsyncTask.execute(name);
    }


}
