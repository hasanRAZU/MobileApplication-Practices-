package com.example.database_practice2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected DatabaseHelper databaseHelper;
    private EditText editTextName, editTextAge, editTextGetData, editTextUpdateName, editTextUpdateAge;
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        editTextName = findViewById(R.id.edit_text_name);
        editTextAge = findViewById(R.id.edit_text_age);
        editTextGetData = findViewById(R.id.edit_text_get_data);
        editTextUpdateName = findViewById(R.id.edit_text_update_name);
        editTextUpdateAge = findViewById(R.id.edit_text_update_age);
        textViewOutput = findViewById(R.id.text_view_output);
        Button buttonStoreData = findViewById(R.id.button_store_data);
        Button buttonFetchData = findViewById(R.id.button_fetch_data);
        Button buttonUpdateData = findViewById(R.id.button_update_data);

        buttonStoreData.setOnClickListener(v -> storeData() );
        buttonFetchData.setOnClickListener(v -> {
            String message = fetchDataByName("", "");
            textViewOutput.setText(message);
        });
        buttonUpdateData.setOnClickListener(v -> updateDataByName());
    }

    @SuppressLint("SetTextI18n")
    private void storeData(){
        String name = editTextName.getText().toString();
        String ageText = editTextAge.getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(ageText)){
            Toast.makeText(this, "Name or Age Can Not Be Null", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = fetchDataByName(name, "");
        if(message.contains(name)){
            textViewOutput.setText("Duplicate Name");
            return;
        }

        int age = Integer.parseInt(ageText);
        long serialNo = databaseHelper.storage(name, age);

        if(serialNo != -1){
            Toast.makeText(this, "Stored Successfully", Toast.LENGTH_SHORT).show();

            message = "Data Send: \nname: " + name + "\nage: " + age + "\n\n";
            fetchDataByName(name, message);
        }else{
            Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private String fetchDataByName(String fetchName, String message){
        String name;
        if(!TextUtils.isEmpty(fetchName))
            name = fetchName;
        else
            name = editTextGetData.getText().toString();


        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
            return "";
        }


        Cursor cursor = databaseHelper.fetchData(name);
        if(cursor.moveToFirst()){
            Toast.makeText(this, "Data Found", Toast.LENGTH_SHORT).show();

            String getId = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String getName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String getAge = cursor.getString(cursor.getColumnIndexOrThrow("age"));
            message += "Data Found:\nid: " + getId + "\nname: " + getName + "\nage: " + getAge;
        }else {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        return message;
    }


    @SuppressLint("SetTextI18n")
    private void updateDataByName(){
        String name = editTextUpdateName.getText().toString();
        String ageText = editTextUpdateAge.getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(ageText)){
            Toast.makeText(this, "Name or Age Can Not Be Null", Toast.LENGTH_SHORT).show();
            return;
        }
        String message1 = fetchDataByName(name,"Previous ");
        int age = Integer.parseInt(ageText);
        int rowAffected = databaseHelper.update(name, age);

        if(rowAffected > 0) {
            Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
            String message2 = "Update Info: \nrowAffected: " + rowAffected +"\n\n";
            message1 += fetchDataByName(name, message2);
            textViewOutput.setText(message1);
        }else
            Toast.makeText(this, "No Data Match for the 'name'", Toast.LENGTH_SHORT).show();

    }
}