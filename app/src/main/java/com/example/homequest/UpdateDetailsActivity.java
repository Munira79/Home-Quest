package com.example.homequest;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateDetailsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextHouseName;
    private EditText editTextLocation;
    private EditText editTextTotalRoom;
    private EditText editTextRentAmount;
    private ImageView imageViewHouse;
    private Button buttonUpdate;
    private Button buttonSelectImage;
    private Button buttonSearch;
    private TextView textViewHouseId;

    private DatabaseHelper databaseHelper;
    private byte[] houseImageByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        editTextHouseName = findViewById(R.id.edit_house_name);
        editTextLocation = findViewById(R.id.edit_location);
        editTextTotalRoom = findViewById(R.id.edit_total_room);
        editTextRentAmount = findViewById(R.id.edit_rent_amount);
        imageViewHouse = findViewById(R.id.image_view_house);
        buttonUpdate = findViewById(R.id.button_update);
        buttonSelectImage = findViewById(R.id.button_select_image);
        buttonSearch = findViewById(R.id.button_search);
        textViewHouseId = findViewById(R.id.text_house_id);

        databaseHelper = new DatabaseHelper(this);

        buttonSearch.setOnClickListener(view -> searchHouse());
        buttonSelectImage.setOnClickListener(view -> selectImage());
        buttonUpdate.setOnClickListener(view -> updateHome());
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

            editTextLocation.setText(location);
            editTextTotalRoom.setText(String.valueOf(totalRoom));
            editTextRentAmount.setText(String.valueOf(rentAmount));
            textViewHouseId.setText("House ID: " + houseId);

            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageViewHouse.setImageBitmap(bitmap);
                houseImageByteArray = image;
            }
            cursor.close();
        } else {
            Toast.makeText(this, "House not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageViewHouse.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                houseImageByteArray = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateHome() {
        String houseName = editTextHouseName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String totalRoom = editTextTotalRoom.getText().toString().trim();
        String rentAmount = editTextRentAmount.getText().toString().trim();

        if (houseName.isEmpty() || location.isEmpty() || totalRoom.isEmpty() || rentAmount.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalRoomInt = Integer.parseInt(totalRoom);
        double rentAmountDouble = Double.parseDouble(rentAmount);

        String houseIdText = textViewHouseId.getText().toString();
        int houseId = Integer.parseInt(houseIdText.replaceAll("\\D+", ""));

        databaseHelper.updateHome(houseId, houseName, location, totalRoomInt, rentAmountDouble, houseImageByteArray);

        Toast.makeText(this, "House updated successfully", Toast.LENGTH_SHORT).show();
    }
}
