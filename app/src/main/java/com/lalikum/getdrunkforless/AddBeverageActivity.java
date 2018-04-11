package com.lalikum.getdrunkforless;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.controller.OptionsController;
import com.lalikum.getdrunkforless.model.Beverage;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AddBeverageActivity extends AppCompatActivity {

    private OptionsController optionsController = new OptionsController();
    private BeverageController beverageController = new BeverageController();

    private TextView beverageSizeTextView;
    private TextView priceTextView;

    private EditText beverageNameEditText;
    private EditText beverageSizeEditText;
    private EditText alcoholByVolumeEditText;
    private EditText priceEditText;
    private EditText bottlesEditText;

    private String unit;
    private String currency;

    private String beverageName;
    private float beverageSize;
    private float alcoholByVolume;
    private float price;
    private int bottles;
    private float pricePerAlcoholCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beverage);

        // TODO fill inputs if edit mode

        beverageSizeTextView = findViewById(R.id.beverageSizeTextView);
        priceTextView = findViewById(R.id.priceTextView);

        beverageNameEditText = findViewById(R.id.beverageNameEditText);
        beverageSizeEditText = findViewById(R.id.beverageSizeEditText);
        alcoholByVolumeEditText = findViewById(R.id.alcoholByVolumeEditText);
        priceEditText = findViewById(R.id.priceEditText);
        bottlesEditText = findViewById(R.id.bottlesEditText);

        // set unit and currency field from options
        unit = optionsController.getUnit();
        currency = optionsController.getCurrency();

        beverageSizeTextView.setText(String.format("Beverage size (%s)", unit));
        priceTextView.setText(String.format("Price (%s)", currency));
    }

    private boolean checkIfInputsAreEmpty(EditText... editTextList) {
        // Show error message in input field if somethings wrong
        boolean isEmptyInput = false;

        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                editText.setError("Please fill this out!");
                isEmptyInput = true;
                //TODO error for string input
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

    private void toHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void calculateButtonEvent(View view) {
        try {
            if (checkIfInputsAreEmpty(beverageNameEditText, beverageSizeEditText, alcoholByVolumeEditText, priceEditText, bottlesEditText)) {
                // TODO hide keyboard only if its over an error input field not visible
                return;
            }
            beverageName = beverageNameEditText.getText().toString();
            beverageSize = Float.parseFloat(beverageSizeEditText.getText().toString());
            alcoholByVolume = Float.parseFloat(alcoholByVolumeEditText.getText().toString());
            price = Float.parseFloat(priceEditText.getText().toString());
            bottles = Integer.parseInt(bottlesEditText.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println("Can't parse input to float or int!");
            return;
        }

        Beverage beverage = beverageController.create(beverageName, beverageSize, alcoholByVolume, price, bottles);

//        float alcoholQuantityCl = beverageSize * bottles * alcoholByVolume;
//
//        pricePerAlcoholCl = price / alcoholQuantityCl;

        TextView pureAlcoholTextView = findViewById(R.id.pureAlcoholTextView);
        TextView pricePerAlcoholTextView = findViewById(R.id.alcoholValueTextView);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setRoundingMode(RoundingMode.HALF_UP);
        // TODO show currency and unit after values
        pureAlcoholTextView.setText("There is " + df.format(beverage.getAlcoholQuantity()) + " ml pure alcohol in the beverage.");
        pricePerAlcoholTextView.setText("That's " + df.format(beverage.getAlcoholValue()) + " Ft/ml alcohol value!");

        hideKeyboard();
    }

    public void saveButtonEvent(View view) {
        calculateButtonEvent(view);
        // TODO save to DB and return home screen
        toHomeActivity(view);
    }

}
