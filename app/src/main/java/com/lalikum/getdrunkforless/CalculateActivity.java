package com.lalikum.getdrunkforless;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalculateActivity extends AppCompatActivity {

    private float beverageSize;
    private float alcoholPercent;
    private float price;
    private int quantity;
    private float pricePerAlcoholCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText beverageSizeEditText = findViewById(R.id.beverageSizeEditText);
                EditText alcoholPercentEditText = findViewById(R.id.alcoholPercentEditText);
                EditText priceEditText = findViewById(R.id.priceEditText);
                EditText quantityEditText = findViewById(R.id.quantityEditText);

                try {
                    if (checkIfInputsAreEmpty(beverageSizeEditText, alcoholPercentEditText, priceEditText, quantityEditText)) {
                        // TODO hide keyboard only if its over an error input field not visible
//                    hideKeyboard();
                        return;
                    }

                    beverageSize = Float.parseFloat(beverageSizeEditText.getText().toString());
                    alcoholPercent = Float.parseFloat(alcoholPercentEditText.getText().toString());
                    price = Float.parseFloat(priceEditText.getText().toString());
                    quantity = Integer.parseInt(quantityEditText.getText().toString());
                } catch (NumberFormatException e) {
                    System.out.println("Can't parse input to float or int!");
                    return;
                }

                float alcoholQuantityCl = beverageSize * quantity * alcoholPercent;

                pricePerAlcoholCl = price / alcoholQuantityCl;

                TextView pureAlcoholTextView = findViewById(R.id.pureAlcoholTextView);
                TextView pricePerAlcoholTextView = findViewById(R.id.pricePerAlcoholTextView);

                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                df.setRoundingMode(RoundingMode.HALF_UP);
                // TODO show currency and unit after values
                pureAlcoholTextView.setText("There is " + df.format(alcoholQuantityCl) + " cl pure alcohol in the beverage.");
                pricePerAlcoholTextView.setText("That's " + df.format(pricePerAlcoholCl) + " Ft/cl alcohol value!");

                hideKeyboard();
            }
        });
    }

    private boolean checkIfInputsAreEmpty(EditText... editTextList) {
        boolean isEmptyInput = false;

        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                editText.setError("Please fill this out!");
                isEmptyInput = true;
            } else if (Float.parseFloat(inputText) == 0) {
                editText.setError("This cannot be null! Do you want to get drunk or what?");
                isEmptyInput = true;
            }
        }

        return isEmptyInput;
    }


    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}
