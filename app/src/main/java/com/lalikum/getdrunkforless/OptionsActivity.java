package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    Button optionsOkButton;

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
        optionsOkButton = findViewById(R.id.optionsOkButton);

        // set unit fields from ENUM
        metricRadioButton.setText(String.format("Metric (%s)", MeasurementSystem.METRIC.getUnit()));
        imperialRadioButton.setText(String.format("Imperial (%s)", MeasurementSystem.IMPERIAL.getUnit()));

        // If options exists fill from DB and enable Save button
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
            optionsOkButton.setEnabled(true);
        }

        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEmptyInputError(userNameEditText);
                setSaveButtonCheck();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        currencyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEmptyInputError(currencyEditText);
                setSaveButtonCheck();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        userNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setEmptyInputError(userNameEditText);
                    setSaveButtonCheck();
                }
            }
        });

        currencyEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setEmptyInputError(currencyEditText);
                    setSaveButtonCheck();
                }
            }
        });

        currencyEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                saveOptions(view);
                return false;
            }
        });
    }

    public void saveOptions(View view) {
        // TODO check if null
        if (setEmptyInputError(userNameEditText, currencyEditText)) {
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

    private boolean setEmptyInputError(EditText... editTextList) {
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

    private boolean isEmptyInput(EditText... editTextList) {
        boolean isEmptyInput = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                isEmptyInput = true;
            }
        }
        return isEmptyInput;
    }

    private void setSaveButtonCheck() {
        boolean isEmpty = isEmptyInput(userNameEditText, currencyEditText);
        if (isEmpty) {
            optionsOkButton.setEnabled(false);
        } else {
            optionsOkButton.setEnabled(true);
        }
    }

}
