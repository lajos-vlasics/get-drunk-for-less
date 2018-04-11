package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lalikum.getdrunkforless.controller.OptionsController;
import com.lalikum.getdrunkforless.model.MeasurementSystem;
import com.lalikum.getdrunkforless.model.Options;

public class OptionsActivity extends AppCompatActivity {

    String userName;
    MeasurementSystem measurementSystem = MeasurementSystem.METRIC;
    String currency;

    EditText userNameEditText;
    EditText currencyEditText;
    RadioButton metricRadioButton;
    RadioButton imperialRadioButton;
    RadioGroup unitRadioGroup;

    OptionsController optionsController = new OptionsController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        userNameEditText = findViewById(R.id.userNameEditText);
        currencyEditText = findViewById(R.id.currencyEditText);
        metricRadioButton = findViewById(R.id.metricRadioButton);
        imperialRadioButton = findViewById(R.id.imperialRadioButton);
        unitRadioGroup = findViewById(R.id.unitRadioGroup);

        // If options exists fill from DB
        if (optionsController.isOptionsExists()) {
            Options options = optionsController.getInstance();

            userNameEditText.setText(options.getUserName());

            measurementSystem = options.getMeasurementSystem();
            unitRadioGroup.clearCheck();
            switch (measurementSystem) {
                case METRIC:
                    metricRadioButton.toggle();
                    break;
                case IMPERIAL:
                    imperialRadioButton.toggle();
                    break;
            }

            currencyEditText.setText(options.getCurrency());
        }
    }

    public void saveOptions(View view) {
        // TODO check if null
        if (checkIfInputsAreEmpty(userNameEditText, currencyEditText)) {
            return;
        }
        userName = userNameEditText.getText().toString();
        currency = currencyEditText.getText().toString();

        optionsController.saveInstance(userName, measurementSystem, currency);
        toHomeActivity(view);
    }

    public void toHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void unitTypeRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.metricRadioButton:
                if (checked)
                    measurementSystem = MeasurementSystem.METRIC;
                break;
            case R.id.imperialRadioButton:
                if (checked)
                    measurementSystem = MeasurementSystem.IMPERIAL;
                break;
        }
    }


    private boolean checkIfInputsAreEmpty(EditText... editTextList) {
        boolean isEmptyInput = false;

        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                editText.setError("Please fill this out!");
                isEmptyInput = true;
            }
        }

        return isEmptyInput;
    }
}
