package com.example.np_fb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import static java.lang.System.exit;

public class finalSplashScreen extends Activity {

    private Button buttonhome;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_two);

                // close this activity
                buttonhome = findViewById(R.id.home);
                buttonhome.setOnClickListener( new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(finalSplashScreen.this,SplashScreen.class);
                        startActivity(i);


                    }
                });



    }
}



























