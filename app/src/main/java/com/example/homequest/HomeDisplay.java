package com.example.homequest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeDisplay extends AppCompatActivity {

    private ListView listViewHomeDetail;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_display);

        listViewHomeDetail = findViewById(R.id.list_view_home_detail);
        Button buttonSearch = findViewById(R.id.button_search);
        Button buttonPopular = findViewById(R.id.button_popular);
        databaseHelper = new DatabaseHelper(this);

        displayProducts();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleSearch();
            }
        });

        buttonPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handlePopular();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayProducts();
    }

    private void displayProducts() {
        Cursor cursor = databaseHelper.getAllHomes(); // Assuming this retrieves all homes
        // Initialize adapter with the correct parameters
        HouseAdapter adapter = new HouseAdapter(this, cursor, 0);
        listViewHomeDetail.setAdapter(adapter);

        // Close the cursor after use

    }

    private void handleSearch() {
        Intent intent = new Intent(HomeDisplay.this, UserActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Search button clicked", Toast.LENGTH_SHORT).show();
    }

    private void handlePopular() {
        Intent intent = new Intent(HomeDisplay.this, PopularActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Popular button clicked", Toast.LENGTH_SHORT).show();
    }
}