package com.example.homequest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);

        Button btnAdmin = findViewById(R.id.btn_admin);
        Button btnUser = findViewById(R.id.btn_user);

        btnAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(IdentifyActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        });

        btnUser.setOnClickListener(v -> {
            Intent intent = new Intent(IdentifyActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
