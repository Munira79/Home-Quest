package com.example.homequest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private EditText etSearch;
    private ImageView searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        etSearch = findViewById(R.id.et_search);
        searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = etSearch.getText().toString().trim();
                if (!searchText.isEmpty()) {
                    searchHome(searchText);
                } else {
                    Toast.makeText(UserActivity.this, "Please enter a home name to search", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchHome(String searchText) {
        // Dummy data for demonstration
        List<String> homeList = new ArrayList<>();
        homeList.add("Sanjida Vila");
        homeList.add("Munira Vila");
        homeList.add("Zuarder Vila");

        List<String> searchResults = new ArrayList<>();
        for (String home : homeList) {
            if (home.toLowerCase().contains(searchText.toLowerCase())) {
                searchResults.add(home);
            }
        }

        if (!searchResults.isEmpty()) {
            // Show search results (for simplicity, we'll just show a Toast)
            String results = "Found: " + searchResults.toString();
            Toast.makeText(UserActivity.this, results, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(UserActivity.this, "No homes found", Toast.LENGTH_SHORT).show();
        }
    }
}
