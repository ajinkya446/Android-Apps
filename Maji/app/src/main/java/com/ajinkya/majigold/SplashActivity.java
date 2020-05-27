package com.ajinkya.majigold;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    private sharedPreference shared;
    SharedPreferences.Editor edit;
    ImageView logo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        shared = new sharedPreference(getApplicationContext());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        logo = findViewById(R.id.splash);

        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            context = getApplicationContext();


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //already logged in
                    if (shared.readLoginStatus()) {
                        startActivity(new Intent(SplashActivity.this, Home.class));
                        finish();
                    } else {
                        Intent main = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(main);
                        finish();
                    }

                }
            }, 1000);

        } catch (Exception ignored) {
        }
    }
}

