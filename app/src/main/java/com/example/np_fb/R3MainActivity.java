package com.example.np_fb;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
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

public class R3MainActivity extends AppCompatActivity {

    String ServerURL =  "http://220.247.222.131/REAL/rate3.php" ;
    RatingBar rating1,rating2,rating3;
    TextView  name, email,contactno ;
    Button button;
    String TempName, TempEmail ,Tempontactno ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r3activity_main);

        rating1 = (RatingBar) findViewById(R.id.ratingBar1);
        rating2 = (RatingBar) findViewById(R.id.ratingBar2);
        rating3= (RatingBar) findViewById(R.id.ratingBar3);

        name = (TextView) findViewById(R.id.textView1);
        email = (TextView) findViewById(R.id.textView2);
        contactno = (TextView) findViewById(R.id.textView3);

        button = (Button)findViewById(R.id.buttonSave);

        rating1.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        name.setText(String.valueOf(rating));
                    }
                }
        );

        rating2.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        email.setText(String.valueOf(rating));
                    }
                }
        );

        rating3.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        contactno.setText(String.valueOf(rating));
                    }
                }
        );




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetData();

                //InsertData(TempName, TempEmail);
                InsertData(TempName, TempEmail, Tempontactno);

            }
        });
    }

    public void GetData(){

        TempName = name.getText().toString();
        TempEmail = email.getText().toString();
        Tempontactno = contactno.getText().toString();

        //TempEmail = email.getText().toString();

    }

    public void InsertData(final String name,final String email,final String contactno){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String NameHolder = name ;
                String EmailHolder = email ;
                String ContactnoHolder = contactno ;
                // String EmailHolder = email ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("email", EmailHolder));
                nameValuePairs.add(new BasicNameValuePair("contactno", ContactnoHolder));

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

                Toast.makeText(R3MainActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(R3MainActivity.this,TenMainActivity.class);
                startActivity(intent);


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        //sendPostReqAsyncTask.execute(name, email);
        sendPostReqAsyncTask.execute(name,email,contactno);
    }

}
