package com.example.finalyearproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.admin.AdminHomeActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    VideoView videologo;
    TextView tvTitle;
    Handler handler;
    Animation animtranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        videologo=findViewById(R.id.vvsplashvideologo);
        tvTitle=findViewById(R.id.tvsplashTitle);

        String videopath="android.resource://"+getPackageName()+"/"+R.raw.burgeranimation;
        videologo.setVideoPath(videopath);
        videologo.start();


        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = sharedPreferences.getBoolean("isLogin", false);

                if (isLogin) {
                    // Get stored role and redirect accordingly
                    String role = sharedPreferences.getString("role", "user"); // default to "user" just in case
                    if (role.equals("admin")) {
                        startActivity(new Intent(SplashActivity.this, AdminHomeActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                } else {
                    // If not logged in yet
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

                finish(); // End splash activity
            }
        }, 4000); // Delay for 2 seconds (optional)
    }
}
