package com.example.razu_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    static String currentData = "0";
    float valueBeforeOperator, valueAfterOperator;
    static final String isZero = "0";
    String result;
    TextView display;
    Button buttonAC, buttonDelete, buttonPercentage, buttonDivision,     //row 3
            buttonSeven, buttonEight, buttonNine, buttonMultiplication, //row 4
            buttonFour, buttonFive, buttonSix, buttonSubtraction,       //row 5
            buttonOne, buttonTwo, buttonThree, buttonAddition,          //row 6
            buttonNull, buttonZero, buttonDot, buttonEqual;             // row 7
    static boolean addition, subtraction, multiplication, division, percentage, equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //access screen information
        display = findViewById(R.id.screen);

        //access all button
        buttonAC = findViewById(R.id.button_ac);
        buttonDelete = findViewById(R.id.button_delete);
        buttonPercentage = findViewById(R.id.button_percentage);
        buttonDivision = findViewById(R.id.button_division);
        buttonSeven = findViewById(R.id.button_seven);
        buttonEight = findViewById(R.id.button_eight);
        buttonNine = findViewById(R.id.button_nine);
        buttonMultiplication = findViewById(R.id.button_multiplication);
        buttonFour = findViewById(R.id.button_four);
        buttonFive = findViewById(R.id.button_five);
        buttonSix = findViewById(R.id.button_six);
        buttonSubtraction = findViewById(R.id.button_subtraction);
        buttonOne = findViewById(R.id.button_one);
        buttonTwo = findViewById(R.id.button_two);
        buttonThree = findViewById(R.id.button_three);
        buttonAddition = findViewById(R.id.button_addition);
        buttonNull = findViewById(R.id.button_null);
        buttonZero = findViewById(R.id.button_zero);
        buttonDot = findViewById(R.id.button_dot);
        buttonEqual = findViewById(R.id.button_equal);



        //Digit Listener
        buttonZero.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "0");
            display.setText(currentData);
        });

        buttonOne.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "1");
            display.setText(currentData);
        });

        buttonTwo.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "2");
            display.setText(currentData);
        });

        buttonThree.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "3");
            display.setText(currentData);
        });

        buttonFour.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "4");
            display.setText(currentData);
        });

        buttonFive.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "5");
            display.setText(currentData);
        });

        buttonSix.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "6");
            display.setText(currentData);
        });
        buttonSeven.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "7");
            display.setText(currentData);
        });

        buttonEight.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "8");
            display.setText(currentData);
        });

        buttonNine.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, "9");
            display.setText(currentData);
        });

        buttonDot.setOnClickListener(v -> {
            currentData = DigitHelper(currentData, ".");
            display.setText(currentData);
        });





        //Syntax Listener
        buttonAC.setOnClickListener(v -> {
            display.setText("");
            currentData = "";
        });

        buttonDelete.setOnClickListener(v -> {
            if(currentData == null){
                display.setText("");
            }
            else if(currentData.equals(isZero)){
                display.setText("0");
            }else{
                String tempValue = currentData;
                currentData = "";
                for (int i = 0; i < tempValue.length()-1; i++){
                    currentData += tempValue.charAt(i);
                }
                display.setText(currentData);
            }
        });




        // Operator Listener
        buttonAddition.setOnClickListener(v -> {
            operatorHelper("buttonAddition");
            currentData += "+";
            display.setText(currentData);
        });
        buttonSubtraction.setOnClickListener(v -> {
            operatorHelper("buttonSubtraction");
            currentData += "-";
            display.setText(currentData);
        });

        buttonMultiplication.setOnClickListener(v -> {
            operatorHelper("buttonMultiplication");
            currentData += "*";
            display.setText(currentData);
        });

        buttonDivision.setOnClickListener(v -> {
            operatorHelper("buttonDivision");
            currentData += "/";
            display.setText(currentData);
        });


        buttonPercentage.setOnClickListener(v -> {
            float tempVal = Float.parseFloat(currentData) / 100;
            currentData = String.valueOf(tempVal);
            display.setText(currentData);
        });

        buttonEqual.setOnClickListener(v -> {
            equal = true;
            if(currentData == null || currentData.equals("0")){
                currentData = "";
                display.setText("");
                equal = false;
            }else{
                calculating();
            }

        });

    }

    String DigitHelper(String currentData, String button){
        if(equal) {
            currentData = "0";
            display.setText(currentData);
            equal = false;
        }
        if(currentData.equals(isZero) && button.equals(isZero)) currentData = isZero;
        else if(currentData.equals(isZero)) currentData = button;
        else currentData = currentData + button;
        return currentData;

    }

    void operatorHelper(String operator){
        addition = false;
        subtraction = false;
        multiplication = false;
        division = false;

        switch (operator){
            case "buttonAddition": addition = true;
            case "buttonSubtraction": subtraction = true;
            case "buttonMultiplication": multiplication = true;
            case "buttonDivision": division = true;
        }
    }

    @SuppressLint("SetTextI18n")
    void calculating() {
        String previousData = currentData;
        String concatedResult = "";
        StringTokenizer token;
        if (!addition && !subtraction && !multiplication && !division) {
            display.setText(String.valueOf(Float.parseFloat(currentData)));
        } else {
            if ((addition) && (currentData.charAt(currentData.length() - 1) != '+')) {
                token = new StringTokenizer(currentData, "+");
                valueBeforeOperator = Float.parseFloat(token.nextToken());
                valueAfterOperator = Float.parseFloat(token.nextToken());

                result = String.valueOf(valueBeforeOperator + valueAfterOperator);
                concatedResult = currentData +"\n" + result;
                display.setText(concatedResult);
                
                currentData = result;
                addition = false;
            }
            else if ((subtraction) && (currentData.charAt(currentData.length() - 1) != '-')) {
                token = new StringTokenizer(currentData, "-");
                valueBeforeOperator = Float.parseFloat(token.nextToken());
                valueAfterOperator = Float.parseFloat(token.nextToken());

                result = String.valueOf(valueBeforeOperator - valueAfterOperator);
                concatedResult = currentData +"\n" + result;
                display.setText(concatedResult);

                currentData = result;
                subtraction = false;
            } else if ((multiplication) && (currentData.charAt(currentData.length() - 1) != '*')) {
                token = new StringTokenizer(currentData, "*");
                valueBeforeOperator = Float.parseFloat(token.nextToken());
                valueAfterOperator = Float.parseFloat(token.nextToken());

                result = String.valueOf(valueBeforeOperator * valueAfterOperator);
                concatedResult = currentData +"\n" + result;
                display.setText(concatedResult);

                currentData = result;
                multiplication = false;
            } else if ((division) && (currentData.charAt(currentData.length() - 1) != '/')) {
                token = new StringTokenizer(currentData, "/");
                valueBeforeOperator = Float.parseFloat(token.nextToken());
                valueAfterOperator = Float.parseFloat(token.nextToken());

                result = String.valueOf(valueBeforeOperator / valueAfterOperator);
                concatedResult = currentData +"\n" + result;
                display.setText(concatedResult);

                currentData = result;
                division = false;
            }
        }


    }
}
