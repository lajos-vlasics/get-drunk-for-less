package com.example.lalikum.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText drinkSizeEditText = findViewById(R.id.drinkSizeEditText);
                EditText alcoholPercentEditText = findViewById(R.id.alcoholPercentEditText);
                EditText priceEditText = findViewById(R.id.priceEditText);

                float drinkSize = Float.parseFloat(drinkSizeEditText.getText().toString());
                float alcoholPercent = Float.parseFloat(alcoholPercentEditText.getText().toString());
                float price = Float.parseFloat(priceEditText.getText().toString());

                float alcoholQuantity = drinkSize * alcoholPercent / 100;

                float pricePerAlcohol = price / (alcoholQuantity * 100);

                TextView resultTextView = findViewById(R.id.resultTextView);

                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                df.setRoundingMode(RoundingMode.HALF_UP);

                resultTextView.setText(df.format(pricePerAlcohol) + " Ft/cl");



            }
        });
    }
}
