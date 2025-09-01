package com.example.database_practice1;

// Existing imports
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table and columns
    private static final String TABLE_NAME = "my_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";

    // SQL statement to create a new table
                                            /*  Format to create table
                                            CREATE TABLE table_name (
                                                    column1 datatype,
                                                    column2 datatype,
                                                    column3 datatype,
                                           ....
                                            );
                                            */
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_AGE + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  //null means -> use 'default' Cursor Factory
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
    public long insertData(String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        return db.insert(TABLE_NAME, null, values);
    }

    // Method to fetch data by name
    public Cursor fetchDataByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_AGE};
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {name};
        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    // Method to update data by name
    public int updateDataByName(String name, int newAge) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AGE, newAge);

        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {name};

        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}
