package com.example.databasepractice;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Database database;

    private EditText editTextEmail, editTextPassword, editTextPhone;
    private TextView textViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(this);

        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        editTextPhone = findViewById(R.id.editText_phone);
        textViewMain = findViewById(R.id.textView_main);
    }

    @SuppressLint("SetTextI18n")
    public void signUp(View view) {
        String userEmail, userPassword, userPhone;

        userEmail = editTextEmail.getText().toString();
        userPassword = editTextPassword.getText().toString();
        userPhone = editTextPhone.getText().toString();

        if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userPhone)) {
            Toast.makeText(this, "Field Can Not Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }


        long rowPos = database.signUp(userEmail, userPassword, userPhone);
        if (rowPos != -1) {
            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

            Cursor cursor = database.fetchData(userEmail, userPassword, userPhone);
            if (cursor.moveToFirst()) {
                String getEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String getPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String getPhone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                textViewMain.setText("Your Result: \nEmail: " + getEmail + "\nPassword: " + getPassword + "\nPhone: " + getPhone);
                cursor.close();
            } else {
                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }

            Intent goToSignUpPage = new Intent(this, SignUp.class);
            startActivity(goToSignUpPage);
        }
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
    }

    public void signIn(View view) {
        Intent goToSignInPage = new Intent(this, SignIn.class);
        startActivity(goToSignInPage);
    }
}
