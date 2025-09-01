package com.example.database_practic3;

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

import com.example.database_practic3.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextName, editTextAge, editTextPhoneNumber, editTextNationalId;
    private EditText editTextGetDataName, editTextGetDataId;
    private EditText editTextUpdateName, editTextUpdateAge, editTextUpdatePhoneNumber, editTextUpdateNationalId;
    private TextView textViewOutput;
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
        editTextPhoneNumber = findViewById(R.id.edit_text_phone_number);
        editTextNationalId = findViewById(R.id.edit_text_national_id);

        editTextGetDataName = findViewById(R.id.edit_text_get_data_name);
        editTextGetDataId = findViewById(R.id.edit_text_get_data_id);

        editTextUpdateName = findViewById(R.id.edit_text_update_name);
        editTextUpdateAge = findViewById(R.id.edit_text_update_age);
        editTextUpdatePhoneNumber = findViewById(R.id.edit_text_update_phone_number);
        editTextUpdateNationalId = findViewById(R.id.edit_text_update_national_id);

        textViewOutput = findViewById(R.id.text_view_output);
        buttonStoreData = findViewById(R.id.button_store_data);
        buttonFetchData = findViewById(R.id.button_fetch_data);
        buttonUpdateData = findViewById(R.id.button_update_data);

        // Set button click listeners
        buttonStoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeData();
            }
        });

        buttonFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });

        buttonUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void storeData() {
        String name = editTextName.getText().toString();
        String ageText = editTextAge.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String nationalId = editTextNationalId.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ageText) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(nationalId)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageText);
        long newRowId = dbHelper.insertData(name, age, phoneNumber, nationalId);

        if (newRowId != -1) {
            Toast.makeText(this, "Data stored successfully", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            editTextAge.setText("");
            editTextPhoneNumber.setText("");
            editTextNationalId.setText("");
        } else {
            Toast.makeText(this, "Error storing data", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void fetchData() {
        String nameToFetch = editTextGetDataName.getText().toString();
        String idToFetch = editTextGetDataId.getText().toString();

        if (TextUtils.isEmpty(nameToFetch) || TextUtils.isEmpty(idToFetch)) {
            Toast.makeText(this, "Please enter both name and national ID number to fetch data", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dbHelper.fetchDataByNameAndId(nameToFetch, idToFetch);

        if (cursor.moveToFirst()) {
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int itemAge = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
            String itemPhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
            String itemNationalId = cursor.getString(cursor.getColumnIndexOrThrow("national_id_number"));

            textViewOutput.setText("Name: " + itemName + "\nAge: " + itemAge + "\nPhone Number: " + itemPhoneNumber + "\nNational ID Number: " + itemNationalId);
        } else {
            textViewOutput.setText("No data found for the given name and national ID number");
        }
        cursor.close();
    }

    private void updateData() {
        String nameToUpdate = editTextUpdateName.getText().toString();
        String newAgeText = editTextUpdateAge.getText().toString();
        String newPhoneNumber = editTextUpdatePhoneNumber.getText().toString();
        String newNationalId = editTextUpdateNationalId.getText().toString();

        if (TextUtils.isEmpty(nameToUpdate) || TextUtils.isEmpty(newAgeText) || TextUtils.isEmpty(newPhoneNumber) || TextUtils.isEmpty(newNationalId)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int newAge = Integer.parseInt(newAgeText);
        int rowsAffected = dbHelper.updateDataByName(nameToUpdate, newAge, newPhoneNumber, newNationalId);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            editTextUpdateName.setText("");
            editTextUpdateAge.setText("");
            editTextUpdatePhoneNumber.setText("");
            editTextUpdateNationalId.setText("");
        } else {
            Toast.makeText(this, "No data found for the given name", Toast.LENGTH_SHORT).show();
        }
    }
}
