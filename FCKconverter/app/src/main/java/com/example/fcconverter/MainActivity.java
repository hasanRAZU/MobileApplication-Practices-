package com.example.fcconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    EditText getTemperature;
    Spinner selectUnit;
    Button convertButton;
    EditText output;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTemperature = findViewById(R.id.temperatureInputField);
        selectUnit = findViewById(R.id.action_bar_spinner);
        convertButton = findViewById(R.id.convertButton);
        output = findViewById(R.id.showResult);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temperatureUnitType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectUnit.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                convertTemperature();
            }
        });
    }


    void convertTemperature(){

        double getTemp = Double.parseDouble(getTemperature.getText().toString());
        String tempType = selectUnit.getSelectedItem().toString();

        StringBuilder showResult = new StringBuilder();
        if("Celsius".equals(tempType)){
            showResult.append("Fahrenheit: ");
            showResult.append((getTemp * 1.8) + 32);
        }else{
            showResult.append("Celsius: ");
            showResult.append((getTemp - 32) * 5/9);
        }





//        //double getTemp = Double.parseDouble(String.valueOf(getTemperature.getText()));
//        String getUnitType = selectUnit.getSelectedItem().toString();
//
//        String showResult;
//        if("Celsius".equals(getUnitType)){
//            showResult = "Fahrenheit: ";
//            showResult += String.valueOf((getTemp * 1.8) + 32);
//
//        }else{
//            showResult = "Celsius: ";
//            showResult += String.valueOf((getTemp - 32) * 5/9);
//        }

        output.setText(showResult);
    }
}