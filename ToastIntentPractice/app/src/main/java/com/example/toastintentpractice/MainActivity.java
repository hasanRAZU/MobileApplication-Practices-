package com.example.toastintentpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout_main);
        relativeLayout.setOnClickListener(v ->{
            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        });

    }
}
