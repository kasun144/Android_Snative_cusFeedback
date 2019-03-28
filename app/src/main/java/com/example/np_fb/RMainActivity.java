package com.example.np_fb;


/*
S.D.M.K.S.S.Dissanayake
*/


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class RMainActivity extends AppCompatActivity {

    String ServerURL =  "http://220.247.222.131/REAL/rate.php" ;
    EditText name, email ;
    RatingBar rating;

    Button button;
    String TempName, TempEmail ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ractivity_main);

        name = (EditText)findViewById(R.id.editText2);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        button = (Button)findViewById(R.id.buttonSave);


        rating.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        name.setText(String.valueOf(rating));
                    }
                }
        );



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetData();

                //InsertData(TempName, TempEmail);
                InsertData(TempName);

            }
        });
    }

    public void GetData(){

        TempName = name.getText().toString();

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

                //Toast.makeText(RMainActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RMainActivity.this,CoMainActivity.class);
                startActivity(intent);

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        //sendPostReqAsyncTask.execute(name, email);
        sendPostReqAsyncTask.execute(name);
    }

}

