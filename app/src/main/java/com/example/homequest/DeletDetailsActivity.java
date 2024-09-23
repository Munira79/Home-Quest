package com.example.homequest;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeletDetailsActivity extends AppCompatActivity {

    private EditText editTextHouseName;
    private TextView textViewLocation;
    private TextView textViewTotalRoom;
    private TextView textViewRentAmount;
    private ImageView imageViewHouse;
    private Button buttonDelete;
    private Button buttonSearch;
    private TextView textViewHouseId;

    private DatabaseHelper databaseHelper;
    private byte[] houseImageByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delet_details); // Corrected layout name

        editTextHouseName = findViewById(R.id.text_view_house_name);
        textViewLocation = findViewById(R.id.text_view_location);
        textViewTotalRoom = findViewById(R.id.text_view_total_room);
        textViewRentAmount = findViewById(R.id.text_view_rent_amount);
        textViewHouseId = findViewById(R.id.text_view_house_id);
        imageViewHouse = findViewById(R.id.image_view_home);
        buttonDelete = findViewById(R.id.button_delete);
        buttonSearch = findViewById(R.id.button_search);

        databaseHelper = new DatabaseHelper(this);

        buttonSearch.setOnClickListener(view -> searchHouse());
        buttonDelete.setOnClickListener(view -> deleteHouse());
    }

    private void searchHouse() {
        String houseName = editTextHouseName.getText().toString().trim();
        if (houseName.isEmpty()) {
            Toast.makeText(this, "Please enter a house name to search", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseHelper.getHomeByName(houseName);
        if (cursor != null && cursor.moveToFirst()) {
            int houseId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LOCATION));
            int totalRoom = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TOTAL_ROOM));
            double rentAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RENT_AMOUNT));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_HOUSE_IMAGE_URI));

            textViewLocation.setText(location);
            textViewTotalRoom.setText(String.valueOf(totalRoom));
            textViewRentAmount.setText(String.valueOf(rentAmount));
            textViewHouseId.setText("House ID: " + houseId);

            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageViewHouse.setImageBitmap(bitmap);
                houseImageByteArray = image;
            }
            cursor.close();
        } else {
            Toast.makeText(this, "House not found", Toast.LENGTH_SHORT).show();
            clearFields(); // Optional: Clear UI fields if house is not found
        }
    }

    private void deleteHouse() {
        String houseName = editTextHouseName.getText().toString().trim();
        if (houseName.isEmpty()) {
            Toast.makeText(this, "Please enter a house name to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        int rowsAffected = databaseHelper.deleteHome(houseName);
        if (rowsAffected > 0) {
            Toast.makeText(this, "House deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields(); // Clear UI fields after deletion
        } else {
            Toast.makeText(this, "Failed to delete house", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        textViewLocation.setText("");
        textViewTotalRoom.setText("");
        textViewRentAmount.setText("");
        textViewHouseId.setText("");
        imageViewHouse.setImageDrawable(null); // Clear image
        houseImageByteArray = null; // Clear byte array if necessary
    }
}
