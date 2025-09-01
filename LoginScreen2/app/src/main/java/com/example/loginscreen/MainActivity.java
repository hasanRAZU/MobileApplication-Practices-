package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{


    // Declare Variable
    EditText emailField, passField;
    Button signUp, signIn;
    String getEmailFromInputField, getPassFromInputField, getEmailFromDatabase, getPassFromDatabase;

    // Create Database
    // Create Database
    static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = openOrCreateDatabase("myDatabase", MODE_PRIVATE, null);

        // create table if not exists
        database.execSQL("CREATE TABLE IF NOT EXISTS authentication (id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR, password VARCHAR)");

        // Locate emailField and passwordField and button by ID
        emailField = findViewById(R.id.editText_emailField);
        passField = findViewById(R.id.editText_passwordField);
        signUp = findViewById(R.id.button_signUp);
        signIn = findViewById(R.id.button_signIn);

        signUp.setOnClickListener(v -> {
            // get Email and Password from editText
            String getEmailFromInputField = emailField.getText().toString();
            String getPassFromInputField = passField.getText().toString();

            // Insert data into the database
            ContentValues values = new ContentValues();
            values.put("email", getEmailFromInputField);
            values.put("password", getPassFromInputField);
            long newRowId = database.insert("authentication", null, values);

            if (newRowId != -1) {
                Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }
        });

        signIn.setOnClickListener(v -> {
            // Fetch data from the database
            Cursor cursor = database.rawQuery("SELECT * FROM authentication", null);

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String getEmailFromDatabase = cursor.getString(cursor.getColumnIndex("email"));
                    @SuppressLint("Range") String getPassFromDatabase = cursor.getString(cursor.getColumnIndex("password"));

                    // Handle fetched data as needed
                    // For example, you can log them, display them, etc.
                    Log.d("DatabaseData", "Email: " + getEmailFromDatabase + ", Password: " + getPassFromDatabase);

                } while (cursor.moveToNext());
            }
            cursor.close();
        });




        // check box for 'show pass'
        CheckBox checkBoxShowPass = findViewById(R.id.checkBox_showPassword);
        EditText passwordField = passField;

        checkBoxShowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    passwordField.setTransformationMethod(null);
                }else{
                    passwordField.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }

    @SuppressLint("Range")
    boolean checkDuplicate(){

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery("SELECT * FROM authentication WHERE email = ?", new String[] {getEmailFromInputField});

        if (cursor.moveToFirst()) {
            getEmailFromInputField = emailField.getText().toString();
            getPassFromInputField = passField.getText().toString();

            getEmailFromDatabase = cursor.getString(cursor.getColumnIndex("email"));
        }
        return getEmailFromInputField.equals(getEmailFromDatabase);
    }

}