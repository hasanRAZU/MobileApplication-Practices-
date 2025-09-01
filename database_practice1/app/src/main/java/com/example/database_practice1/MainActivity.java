package com.example.database_practice1;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected DatabaseHelper dbHelper;
    private EditText editTextName, editTextAge, editTextGetData, editTextUpdateName, editTextUpdateAge;
    private TextView textViewOutput, textViewSerialNo;
    private Button buttonStoreData, buttonFetchData, buttonUpdateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        editTextName = findViewById(R.id.edit_text_name);
        editTextAge = findViewById(R.id.edit_text_age);
        editTextGetData = findViewById(R.id.edit_text_get_data);
        editTextUpdateName = findViewById(R.id.edit_text_update_name);
        editTextUpdateAge = findViewById(R.id.edit_text_update_age);
        textViewOutput = findViewById(R.id.text_view_output);
        buttonStoreData = findViewById(R.id.button_store_data);
        buttonFetchData = findViewById(R.id.button_fetch_data);
        buttonUpdateData = findViewById(R.id.button_update_data);
        textViewSerialNo = findViewById(R.id.text_view_serialNo);

        // Set button click listeners
        buttonStoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeData();
            }
        });

        buttonFetchData.setOnClickListener(v -> {
            fetchData();
        });

        buttonUpdateData.setOnClickListener(v -> {
            updateData();
        });
    }

    @SuppressLint("SetTextI18n")
    private void storeData() {
        String name = editTextName.getText().toString();
        String ageText = editTextAge.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ageText)) {
            Toast.makeText(this, "Please enter both name and age", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageText);
        long newRowId = dbHelper.insertData(name, age);
        textViewSerialNo.setText("Serial no: " + (int) newRowId);

        if (newRowId != -1) {
            Toast.makeText(this, "Data stored successfully", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            editTextAge.setText("");
        } else {
            Toast.makeText(this, "Error storing data", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void fetchData() {
        String nameToFetch = editTextGetData.getText().toString();

        if (TextUtils.isEmpty(nameToFetch)) {
            Toast.makeText(this, "Please enter a name to fetch data", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dbHelper.fetchDataByName(nameToFetch);

        if (cursor.moveToFirst()) {
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int itemAge = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
            textViewOutput.setText("Name: " + itemName + "\nAge: " + itemAge);
        } else {
            textViewOutput.setText("No data found for the given name");
        }
        cursor.close();
    }

    @SuppressLint("SetTextI18n")
    private void updateData() {
        String nameToUpdate = editTextUpdateName.getText().toString();
        String newAgeText = editTextUpdateAge.getText().toString();

        if (TextUtils.isEmpty(nameToUpdate) || TextUtils.isEmpty(newAgeText)) {
            Toast.makeText(this, "Please enter both name and new age", Toast.LENGTH_SHORT).show();
            return;
        }

        int newAge = Integer.parseInt(newAgeText);
        int rowsAffected = dbHelper.updateDataByName(nameToUpdate, newAge);
        TextView text_view_rowsUpdate = findViewById(R.id.text_view_rowsUpdate);
        text_view_rowsUpdate.setText("rows Affected: " +rowsAffected);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            editTextUpdateName.setText("");
            editTextUpdateAge.setText("");
        } else {
            Toast.makeText(this, "No data found for the given name", Toast.LENGTH_SHORT).show();
        }
    }
}
