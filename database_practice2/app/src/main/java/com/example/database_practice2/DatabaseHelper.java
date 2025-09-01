package com.example.database_practice2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper{

    // Database Information
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table Column
    private static final String TABLE_NAME = "storeInfo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";

    // Create Table and Column
                                /*  Format to create table
                                            CREATE TABLE table_name (
                                                    column1 datatype,
                                                    column2 datatype,
                                                    column3 datatype,
                                           ....
                                            );
                                            */

    private static  final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " STRING NOT NULL, " +
            COLUMN_AGE + " INTEGER NOT NULL );";

    // Constructor & Overridden Methods
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public long storage(String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor fetchData(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_AGE};
        String selection = COLUMN_NAME + " = ? ";
        String[] selectionArg = {name};

        return db.query(TABLE_NAME, columns, selection, selectionArg, null, null, null);
    }

    public int update(String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AGE, age);
        String selection = COLUMN_NAME + " = ? ";
        String[] selectionArg = {name};
        return db.update(TABLE_NAME, values, selection, selectionArg);
    }
}
