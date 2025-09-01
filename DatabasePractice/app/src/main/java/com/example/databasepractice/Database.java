package com.example.databasepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table and Column
    private static final String TABLE_NAME = "myStorage";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONE = "phone";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ( " +
            COLUMN_EMAIL + " VARCHAR(25) PRIMARY KEY, " +
            COLUMN_PASSWORD + " VARCHAR(20) NOT NULL, " +
            COLUMN_PHONE + " TEXT NOT NULL);";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long signUp(String email, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, phone);

        long result = -1;
        try {
            result = db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("DatabaseError", "Insertion failed", e);
        }
        Log.d("DatabaseInfo", "Insertion result: " + result);
        return result;
    }

    public Cursor fetchData(String email, String password, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_PHONE};
        String selections = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ? AND " + COLUMN_PHONE + " = ?";
        String[] selectionsArg = {email, password, phone};
        return db.query(TABLE_NAME, columns, selections, selectionsArg, null, null, null);
    }
}
