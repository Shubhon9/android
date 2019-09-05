package com.cetpainfotech.classifieds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();


        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);
            }

        };

        new Timer().schedule(timerTask,3000);


    }
}
