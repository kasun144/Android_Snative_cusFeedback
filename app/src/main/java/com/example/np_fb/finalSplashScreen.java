package com.example.np_fb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static java.lang.System.exit;

public class finalSplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_two);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
              /*  Intent i = new Intent(finalSplashScreen.this,finalSplashScreen.class);
                startActivity(i);
*/
                // close this activity
                //finish();

            }
        }, SPLASH_TIME_OUT);



    }
}



























