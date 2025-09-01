package com.example.alarmclock1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button setAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAlarm = findViewById(R.id.btnAlarm);


        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}