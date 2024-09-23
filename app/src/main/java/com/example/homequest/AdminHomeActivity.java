package com.example.homequest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_home);
        Button btnInsertHome =findViewById(R.id.btn_insert_home);
        Button btnViewHome = findViewById(R.id.btn_view_home);

        btnInsertHome.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, InsertHomeDetail.class);
            startActivity(intent);
        });


        btnViewHome.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, ViewHomeDetail.class);
            startActivity(intent);
        });




        

        };
    }
