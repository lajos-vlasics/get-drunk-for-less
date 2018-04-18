package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lalikum.getdrunkforless.controller.SettingsController;
import com.lalikum.getdrunkforless.model.MeasurementSystem;
import com.lalikum.getdrunkforless.model.Settings;
import com.lalikum.getdrunkforless.util.InputChecker;

public class SettingsActivity extends AppCompatActivity {

    private static TextInputLayout userNameTextInputLayout;
    private static TextInputLayout currencyTextInputLayout;
    private static EditText userNameEditText;
    private static EditText currencyEditText;
    private static MenuItem saveMenuItem;
    private RadioButton metricRadioButton;
    private RadioButton imperialRadioButton;
    private RadioGroup unitRadioGroup;
    private ImageButton tutorialButton;

    private SettingsController settingsController = new SettingsController();
    private InputChecker inputChecker = new InputChecker();

    private String userName;
    private MeasurementSystem measurementSystem = MeasurementSystem.METRIC;
    private String currency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        userNameTextInputLayout = findViewById(R.id.tilSettingsUserName);
        currencyTextInputLayout = findViewById(R.id.tilSettingsCurrency);
        // TODO set max length
        userNameEditText = findViewById(R.id.etSettingsUserName);
        // TODO set max length
        currencyEditText = findViewById(R.id.etSettingsCurrency);
        metricRadioButton = findViewById(R.id.rbSettingsMetric);
        imperialRadioButton = findViewById(R.id.ebSettingsImperial);
        unitRadioGroup = findViewById(R.id.rbgSettingsUnit);
        tutorialButton = findViewById(R.id.btnSettingsTutorial);

        // init
        tutorialButton.setVisibility(View.GONE);
        // set unit fields from ENUM
        metricRadioButton.setText(String.format("Metric (%s)", MeasurementSystem.METRIC.getUnit()));
        imperialRadioButton.setText(String.format("Imperial (%s)", MeasurementSystem.IMPERIAL.getUnit()));

        // If settings DB exists (from home menu button) fill from DB and enable Save button
        if (settingsController.isSettingsExists()) {
            Settings settings = settingsController.getInstance();
            // show things
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            tutorialButton.setVisibility(View.VISIBLE);

            // disable floating label animations
            userNameTextInputLayout.setHintAnimationEnabled(false);
            currencyTextInputLayout.setHintAnimationEnabled(false);

            // populate fields
            userNameEditText.setText(settings.getUserName());

            measurementSystem = settings.getMeasurementSystem();
            unitRadioGroup.clearCheck();
            switch (measurementSystem) {
                // TODO convert beverage units too...
                case METRIC:
                    metricRadioButton.toggle();
                    break;
                case IMPERIAL:
                    imperialRadioButton.toggle();
                    break;
            }

            currencyEditText.setText(settings.getCurrency());

            // enable floating label animations
            userNameTextInputLayout.setHintAnimationEnabled(true);
            currencyTextInputLayout.setHintAnimationEnabled(true);
        }

        // add listeners
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setUserNameInputError();
                setSaveButtonStatus();
            }
        });

        currencyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCurrencyInputError();
                setSaveButtonStatus();
            }
        });

        userNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setUserNameInputError();
                    setSaveButtonStatus();
                }
            }
        });

        currencyEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setCurrencyInputError();
                    setSaveButtonStatus();
                }
            }
        });

        currencyEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                saveSettings();
                return false;
            }
        });

        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTutorialActivity(v);
            }
        });

    }

    // Set action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        saveMenuItem = menu.findItem(R.id.itemSettingsMenuSave);
        // enable save button if settings edited
        if (settingsController.isSettingsExists()) {
            setSaveButtonActive();
        } else {
            setSaveButtonInactive();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSettingsMenuSave:
                saveSettings();
                toHomeActivity();
                return true;
            case android.R.id.home:
                toHomeActivity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveSettings() {
        if (setUserNameInputError() || setCurrencyInputError()) {
            return;
        }
        userName = userNameEditText.getText().toString();
        currency = currencyEditText.getText().toString();

        settingsController.saveInstance(userName, measurementSystem, currency);
        toHomeActivity();
    }

    public void toHomeActivity() {
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
            case R.id.rbSettingsMetric:
                if (checked)
                    measurementSystem = MeasurementSystem.METRIC;
                break;
            case R.id.ebSettingsImperial:
                if (checked)
                    measurementSystem = MeasurementSystem.IMPERIAL;
                break;
        }
    }

    private void setSaveButtonStatus() {
        boolean isInputError = inputChecker.isEmptyInput(userNameEditText, currencyEditText);
        if (isInputError) {
            setSaveButtonInactive();
        } else {
            setSaveButtonActive();
        }
    }

    public void setSaveButtonActive() {
        saveMenuItem.setIcon(R.drawable.ic_save_active);
        saveMenuItem.setEnabled(true);
    }

    public void setSaveButtonInactive() {
        saveMenuItem.setIcon(R.drawable.ic_save_inactive);
        saveMenuItem.setEnabled(false);
    }

    private boolean setUserNameInputError() {
        return inputChecker.isEmptyInput("Please tell me your name!", userNameEditText);
    }

    private boolean setCurrencyInputError() {
        return inputChecker.isEmptyInput("Set the default currency please!", currencyEditText);
    }

}
