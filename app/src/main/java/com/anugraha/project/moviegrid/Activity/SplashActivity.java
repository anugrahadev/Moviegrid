package com.anugraha.project.moviegrid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    Animation app_splash,tagline_to_top;
    ImageView app_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        app_logo = findViewById(R.id.app_logo);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        //run animation
        app_logo.startAnimation(app_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //berpindah acvitivy (intent)
                Intent gotoGetStarted = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(gotoGetStarted);
                finish();
            }
        },2000);

    }
}
