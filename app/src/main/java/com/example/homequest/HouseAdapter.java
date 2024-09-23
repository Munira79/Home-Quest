package com.example.homequest;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HouseAdapter extends CursorAdapter {

    public HouseAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_of_homes, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.text_view_house_name);
        TextView locationTextView = view.findViewById(R.id.text_view_location);
        TextView totalroomTextView = view.findViewById(R.id.text_view_total_room);
        TextView rentamountTextView = view.findViewById(R.id.text_view_rent_amount);
        ImageView houseImageView = view.findViewById(R.id.image_view_house);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_HOUSE_NAME));
        String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LOCATION));
        int totalRoom = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TOTAL_ROOM));
        int rentAmount = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RENT_AMOUNT));
        byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_HOUSE_IMAGE_URI));

        // Set text and image
        nameTextView.setText(name);
        locationTextView.setText(location);
        totalroomTextView.setText(String.valueOf(totalRoom));
        rentamountTextView.setText(String.valueOf(rentAmount));
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        houseImageView.setImageBitmap(bitmap);
    }
}
