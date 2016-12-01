package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.user.onedaynquestions.R;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class SplashActivity extends Activity {

    public static final int SPLASH_TIMEOUT_MILLIS = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT_MILLIS);
    }
}
