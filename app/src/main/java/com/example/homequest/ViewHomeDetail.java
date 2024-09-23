package com.example.homequest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewHomeDetail extends AppCompatActivity {
    private ListView listViewHomeDetail;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_detail);

        listViewHomeDetail = findViewById(R.id.list_view_home_detail);
        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonDelete = findViewById(R.id.button_delete);
        databaseHelper = new DatabaseHelper(this);

        displayProducts();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdate();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelete();
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



    }

    private void handleUpdate() {
        Intent intent = new Intent(ViewHomeDetail.this, UpdateDetailsActivity.class);
        startActivity(intent);
    }

    private void handleDelete() {
        Intent intent = new Intent(ViewHomeDetail.this, DeletDetailsActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Delete button clicked", Toast.LENGTH_SHORT).show();
    }
}
