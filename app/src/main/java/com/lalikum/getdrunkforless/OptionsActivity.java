package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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
import com.lalikum.getdrunkforless.util.InputChecker;

public class OptionsActivity extends AppCompatActivity {

    private String userName;
    private MeasurementSystem measurementSystem = MeasurementSystem.METRIC;
    private String currency;

    private EditText userNameEditText;
    private EditText currencyEditText;

    private RadioButton metricRadioButton;
    private RadioButton imperialRadioButton;
    private RadioGroup unitRadioGroup;

    private Button optionsOkButton;
    private FloatingActionButton tutorialButton;
    private FloatingActionButton homeButton;

    private OptionsController optionsController = new OptionsController();
    private InputChecker inputChecker = new InputChecker();

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
        tutorialButton = findViewById(R.id.optionTutorialButton);
        homeButton = findViewById(R.id.optionHomeButton);

        // set unit fields from ENUM
        metricRadioButton.setText(String.format("Metric (%s)", MeasurementSystem.METRIC.getUnit()));
        imperialRadioButton.setText(String.format("Imperial (%s)", MeasurementSystem.IMPERIAL.getUnit()));

        // If options exists (from home menu button) fill from DB and enable Save button
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
            tutorialButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
        }

        // add listeners
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setUserNameInputError();
                setOptionsOkButtonActive();
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
                setCurrencyInputError();
                setOptionsOkButtonActive();
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
                    setUserNameInputError();
                    setOptionsOkButtonActive();
                }
            }
        });

        currencyEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setCurrencyInputError();
                    setOptionsOkButtonActive();
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

        optionsOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeActivity(v);
            }
        });

        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTutorialActivity(v);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeActivity(v);
            }
        });
    }

    public void saveOptions(View view) {
        if (setUserNameInputError() || setCurrencyInputError()) {
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

    private void toTutorialActivity(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
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

    private void setOptionsOkButtonActive() {
        boolean isInputError = inputChecker.isEmptyInput(userNameEditText, currencyEditText);
        if (isInputError) {
            optionsOkButton.setEnabled(false);
        } else {
            optionsOkButton.setEnabled(true);
        }
    }

    private boolean setUserNameInputError() {
        return inputChecker.isEmptyInput("Please tell me your name!", userNameEditText);
    }

    private boolean setCurrencyInputError() {
        return inputChecker.isEmptyInput("Set the default currency please!", currencyEditText);
    }

}
