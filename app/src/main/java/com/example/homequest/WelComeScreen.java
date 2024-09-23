package com.example.homequest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class WelComeScreen extends AppCompatActivity {

    ImageView logo;
    TextView appName;
    Animation Splash_top,Splash_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wel_come_screen);

        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);

        Splash_top = AnimationUtils.loadAnimation(this, R.anim.splash_top);
        Splash_bottom = AnimationUtils.loadAnimation(this, R.anim.splash_bottom);

        logo.setAnimation(Splash_top);
        appName.setAnimation(Splash_bottom);

        // Hide Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(WelComeScreen.this, IdentifyActivity.class);
                startActivity(myIntent);
                finish();
            }
        }, 5000);
    }
}
