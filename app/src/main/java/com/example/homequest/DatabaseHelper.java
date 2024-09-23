package com.example.homequest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HomeQuest_DB";
    public static final int DATABASE_VERSION = 3;

    // Table and Column names for Register table
    private static final String TABLE_REGISTER = "register";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_MOBILE = "mobile";

    // Table and Column names for Homes table
    private static final String TABLE_HOMES = "homes";
    public static final String COL_HOUSE_NAME = "house_name";
    public static final String COL_LOCATION = "location";
    public static final String COL_TOTAL_ROOM = "total_room";
    public static final String COL_RENT_AMOUNT = "rent_amount";
    public static final String COL_HOUSE_IMAGE_URI = "house_image_uri";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Register table
        db.execSQL("CREATE TABLE " + TABLE_REGISTER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_MOBILE + " TEXT)");

        // Create Homes table
        db.execSQL("CREATE TABLE " + TABLE_HOMES + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_HOUSE_NAME + " TEXT, " +
                COL_LOCATION + " TEXT, " +
                COL_TOTAL_ROOM + " INTEGER, " +
                COL_RENT_AMOUNT + " REAL, " +
                COL_HOUSE_IMAGE_URI + " BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOMES);
        onCreate(db);
    }

    // Insert user data into Register table
    public boolean insertUser(String username, String email, String password, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_MOBILE, mobile);

        long result = db.insert(TABLE_REGISTER, null, contentValues);
        return result != -1;
    }

    // Check if a user exists in Register table
    public boolean checkUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Insert home data into Homes table
    public void insertHome(String houseName, String location, int totalRoom, double rentAmount, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_HOUSE_NAME, houseName);
        values.put(COL_LOCATION, location);
        values.put(COL_TOTAL_ROOM, totalRoom);
        values.put(COL_RENT_AMOUNT, rentAmount);
        values.put(COL_HOUSE_IMAGE_URI, imageByteArray);
        db.insert(TABLE_HOMES, null, values);
        db.close();
    }

    // Retrieve all homes from Homes table
    public Cursor getAllHomes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HOMES, null);
    }

    // Retrieve a specific home by name from Homes table
    public Cursor getHomeByName(String houseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HOMES + " WHERE " + COL_HOUSE_NAME + " = ?", new String[]{houseName});
    }

    // Update home data in Homes table
    public void updateHome(int homeId, String houseName, String location, int totalRoom, double rentAmount, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_HOUSE_NAME, houseName);
        values.put(COL_LOCATION, location);
        values.put(COL_TOTAL_ROOM, totalRoom);
        values.put(COL_RENT_AMOUNT, rentAmount);
        values.put(COL_HOUSE_IMAGE_URI, imageByteArray);

        db.update(TABLE_HOMES, values, COL_ID + " = ?", new String[]{String.valueOf(homeId)});
        db.close();
    }

    // Delete home data from Homes table
    public int deleteHome(String houseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HOMES, COL_HOUSE_NAME + " = ?", new String[]{houseName});
        db.close();
        return 0;
    }
}
