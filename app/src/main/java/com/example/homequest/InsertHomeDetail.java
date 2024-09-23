package com.example.homequest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertHomeDetail extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    private EditText houseNameEditText;
    private EditText locationEditText;
    private EditText totalRoomEditText;
    private EditText rentAmountEditText;
    private ImageView selectedImageView;
    private Button selectImageButton;
    private Button insertHomeButton;
    private DatabaseHelper databaseHelper;
    private byte[] imageByteArray;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_home_detail);

        houseNameEditText = findViewById(R.id.et_house_name);
        locationEditText = findViewById(R.id.et_location);
        rentAmountEditText = findViewById(R.id.et_rent_amount);
        totalRoomEditText = findViewById(R.id.et_total_room);
        selectedImageView = findViewById(R.id.iv_selected_image);
        selectImageButton = findViewById(R.id.btn_select_image);
        insertHomeButton = findViewById(R.id.btn_insert_home);

        databaseHelper = new DatabaseHelper(this);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    selectedImageView.setImageBitmap(imageBitmap);
                    imageByteArray = bitmapToByteArray(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        selectImageButton.setOnClickListener(view -> showImageSelectionDialog());

        insertHomeButton.setOnClickListener(view -> insertHome());
    }

    private void showImageSelectionDialog() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePickerLauncher.launch(pickIntent);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void insertHome() {
        String houseName = houseNameEditText.getText().toString();
        String location = locationEditText.getText().toString();
        int totalRoom = Integer.parseInt(totalRoomEditText.getText().toString());
        double rentAmount = Double.parseDouble(rentAmountEditText.getText().toString());

        if (houseName.isEmpty() || location.isEmpty() || imageByteArray == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseHelper.insertHome(houseName, location, totalRoom, rentAmount, imageByteArray);
        Toast.makeText(this, "Home details inserted successfully", Toast.LENGTH_SHORT).show();
    }
}