package com.example.database_practic3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table and columns
    private static final String TABLE_NAME = "my_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_NATIONAL_ID_NUMBER = "national_id_number";

    // SQL statement to create a new table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_AGE + " INTEGER, " +
                    COLUMN_PHONE_NUMBER + " TEXT, " +
                    COLUMN_NATIONAL_ID_NUMBER + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to insert data
    public long insertData(String name, int age, String phoneNumber, String nationalIdNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_NATIONAL_ID_NUMBER, nationalIdNumber);
        return db.insert(TABLE_NAME, null, values);
    }

    // Method to fetch data by name and national id number
    public Cursor fetchDataByNameAndId(String name, String nationalIdNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_AGE, COLUMN_PHONE_NUMBER, COLUMN_NATIONAL_ID_NUMBER};
        String selection = COLUMN_NAME + " = ? AND " + COLUMN_NATIONAL_ID_NUMBER + " = ?";
        String[] selectionArgs = {name, nationalIdNumber};
        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    // Method to update data by name
    public int updateDataByName(String name, int newAge, String newPhoneNumber, String newNationalIdNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AGE, newAge);
        values.put(COLUMN_PHONE_NUMBER, newPhoneNumber);
        values.put(COLUMN_NATIONAL_ID_NUMBER, newNationalIdNumber);

        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {name};

        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}
