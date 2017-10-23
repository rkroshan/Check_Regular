package com.roshan_kumar.alternatingcurrent006.check_regular.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        preferences = getSharedPreferences("comingfromStudentProfile", MODE_PRIVATE);
        if (preferences.getBoolean("comingfromStudentProfile", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_splash_screen);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            };

            thread.start();
        }
    }
}
