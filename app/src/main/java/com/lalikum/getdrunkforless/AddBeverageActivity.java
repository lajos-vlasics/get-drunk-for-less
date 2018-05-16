package com.lalikum.getdrunkforless;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.controller.SettingsController;
import com.lalikum.getdrunkforless.model.Beverage;
import com.lalikum.getdrunkforless.util.InputChecker;

import java.util.Objects;

public class AddBeverageActivity extends AppCompatActivity {

    private static final String[] BEVERAGE_NAMES = new String[]{
            "Kőbányai Világos", "Dreher Classic", "Dreher BAK", "Dreher Pale Ale", "Borsodi", "Soproni",
            "Arany Ászok", "Balatoni Világos",
            "Soproni Démon", "Soproni IPA", "Soproni 1895", "Soproni Radler", "Szalon sör",
            "Heineken",
    };

    private static final int MAX_ALCOHOL_BY_VOLUME = 100;
    private static MenuItem saveMenuItem;
    private AutoCompleteTextView beverageNameAutoCompleteTextView;
    private EditText beverageSizeEditText;
    private EditText alcoholByVolumeEditText;
    private EditText priceEditText;

    private TextInputLayout beverageNameTextInputLayout;
    private TextInputLayout beverageSizeTextInputLayout;
    private TextInputLayout alcoholByVolumeTextInputLayout;
    private TextInputLayout priceByVolumeTextInputLayout;

    private TextView alcoholValueTextView;
    private TextView ofAlcoholTextView;

    private AdView mAdView;

    private SettingsController settingsController = new SettingsController();
    private BeverageController beverageController = new BeverageController();

    private InputChecker inputChecker = new InputChecker();
    private Beverage newBeverage;
    private Beverage editBeverage;
    private String unit;
    private String currency;
    private String beverageName;
    private float beverageSize;
    private float alcoholByVolume;
    private float price;

    @Override
    protected void onResume() {
        super.onResume();
        // add TextWatcher listeners here to prevent error messages after orientation changes
        setTextChangedListeners();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO check max name length on small screen
        // TODO autocomplete beverage names
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beverage);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        beverageNameTextInputLayout = findViewById(R.id.tilAddBeverageName);
        beverageSizeTextInputLayout = findViewById(R.id.tilAddBeverageSize);
        alcoholByVolumeTextInputLayout = findViewById(R.id.tilAddBeverageABV);
        priceByVolumeTextInputLayout = findViewById(R.id.tilAddBeveragePrice);

        ofAlcoholTextView = findViewById(R.id.tvAddBeverageOfAlcohol);
        alcoholValueTextView = findViewById(R.id.tvAddBeverageAlcoholValue);
        // set beverage name edit text autocomplete
        beverageNameAutoCompleteTextView = findViewById(R.id.etAddBeverageName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, BEVERAGE_NAMES);
        beverageNameAutoCompleteTextView.setAdapter(adapter);

        beverageSizeEditText = findViewById(R.id.etAddBeverageSize);
        alcoholByVolumeEditText = findViewById(R.id.etAddBeverageABV);
        priceEditText = findViewById(R.id.etAddBeveragePrice);

        // set adView
        mAdView = findViewById(R.id.adViewAddBeverage);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                mAdView.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        // Set unit and currency field from settings DB
        unit = settingsController.getUnit();
        currency = settingsController.getCurrency();

        beverageSizeTextInputLayout.setHint(String.format("%s (%s)",
                getString(R.string.add_beverage_size), unit));
        priceByVolumeTextInputLayout.setHint(String.format("%s (%s)",
                getString(R.string.add_beverage_price), currency));

        // fill inputs if edit mode
        Intent intent = getIntent();
        long beverageId = intent.getLongExtra("beverageId", -1);
        if (beverageId > -1) {
            setTitle(getString(R.string.add_beverage_title_edit));
            // turn off textInputLayout hint animations
            beverageNameTextInputLayout.setHintAnimationEnabled(false);
            beverageSizeTextInputLayout.setHintAnimationEnabled(false);
            alcoholByVolumeTextInputLayout.setHintAnimationEnabled(false);
            priceByVolumeTextInputLayout.setHintAnimationEnabled(false);

            editBeverage = beverageController.getById(beverageId);
            beverageNameAutoCompleteTextView.setText(editBeverage.getName());
            beverageSizeEditText.setText(getNoDecimalStringIfInteger(editBeverage.getSize()));
            alcoholByVolumeEditText.setText(String.valueOf(editBeverage.getAlcoholByVolume()));
            priceEditText.setText(getNoDecimalStringIfInteger(editBeverage.getPrice()));
            calculate();

            setAlcoholValueTextLayoutVisibility(true);

            // turn on textInputLayout hint animations
            beverageNameTextInputLayout.setHintAnimationEnabled(true);
            beverageSizeTextInputLayout.setHintAnimationEnabled(true);
            alcoholByVolumeTextInputLayout.setHintAnimationEnabled(true);
            priceByVolumeTextInputLayout.setHintAnimationEnabled(true);
        }

        // set event listeners for
        setFocusChangeListeners();
    }

    private String getNoDecimalStringIfInteger(float f) {
        if (f % 1 == 0) {
            return String.valueOf(Integer.valueOf(Math.round(f)));
        }
        return String.valueOf(f);
    }

    private void setAlcoholValueTextLayoutVisibility(boolean b) {
        if (b) {
            ofAlcoholTextView.setVisibility(View.VISIBLE);
            alcoholValueTextView.setVisibility(View.VISIBLE);
        } else {
            ofAlcoholTextView.setVisibility(View.INVISIBLE);
            alcoholValueTextView.setVisibility(View.INVISIBLE);
        }
    }

    // hide keyboard when touching outside an EditText
    // TODO keyboard always pops up after change edittext field and water bg moves too
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    // Set action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_beverage_menu, menu);

        saveMenuItem = menu.findItem(R.id.itemAddBeverageMenuSave);
        // enable save button if beverage edited
        Intent intent = getIntent();
        long beverageId = intent.getLongExtra("beverageId", -1);
        if (beverageId > -1) {
            setSaveButtonActive();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAddBeverageMenuSave:
                saveButtonEvent();
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

    private boolean isBeverageNameInputError(boolean setErrorText) {
        boolean isEmptyInput;
        if (setErrorText) {
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_name), beverageNameAutoCompleteTextView);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(beverageNameAutoCompleteTextView);
        }
        return isEmptyInput;
    }

    private boolean isBeverageSizeInputError(boolean setErrorText) {
        boolean isEmptyInput;
        boolean isZeroInput;
        if (setErrorText) {
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_size), beverageSizeEditText);
            isZeroInput = inputChecker.isZeroInput(getString(R.string.add_beverage_zero_size), beverageSizeEditText);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(beverageSizeEditText);
            isZeroInput = inputChecker.isZeroInput(beverageSizeEditText);

        }
        return isEmptyInput || isZeroInput;
    }

    private boolean isAlcoholByVolumeInputError(boolean setErrorText) {
        boolean isEmptyInput;
        boolean isZeroInput;
        boolean isHigherInput;
        if (setErrorText) {
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_abv), alcoholByVolumeEditText);
            isZeroInput = inputChecker.isZeroInput(getString(R.string.add_beverage_error_zero_abv), alcoholByVolumeEditText);
            isHigherInput = inputChecker.isHigherInput(getString(R.string.add_beverage_error_higher_abv), MAX_ALCOHOL_BY_VOLUME, alcoholByVolumeEditText);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(alcoholByVolumeEditText);
            isZeroInput = inputChecker.isZeroInput(alcoholByVolumeEditText);
            isHigherInput = inputChecker.isHigherInput(MAX_ALCOHOL_BY_VOLUME, alcoholByVolumeEditText);
        }
        return isEmptyInput || isZeroInput || isHigherInput;
    }

    private boolean isPriceInputError(boolean setErrorText) {
        boolean isEmptyInput;
        boolean isZeroInput;
        if (setErrorText) {
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_price), priceEditText);
            isZeroInput = inputChecker.isZeroInput(getString(R.string.add_beverage_error_zero_price), priceEditText);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(priceEditText);
            isZeroInput = inputChecker.isZeroInput(priceEditText);
        }
        return isEmptyInput || isZeroInput;
    }

    private void calculateIfPossible() {
        boolean isAnyInputError = isAnyInputError();
        if (isAnyInputError) {
            setAlcoholValueTextLayoutVisibility(false);
            setSaveButtonInactive();
        } else {
            calculate();
            setAlcoholValueTextLayoutVisibility(true);
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

    private boolean isAnyInputError() {
        boolean isNameInputError = isBeverageNameInputError(false);
        boolean isSizeInputError = isBeverageSizeInputError(false);
        boolean isAlcoholInputError = isAlcoholByVolumeInputError(false);
        boolean isPriceInputError = isPriceInputError(false);
        return isNameInputError || isSizeInputError || isAlcoholInputError || isPriceInputError;
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void calculate() {
        if (!setInputs()) {
            return;
        }
        setBeverage();

        alcoholValueTextView.setText(beverageController.getAlcoholValueWithUnit(newBeverage));
    }

    public void saveButtonEvent() {
        beverageController.save(newBeverage);
        toHomeActivity();
    }

    private boolean setInputs() {
        if (isAnyInputError()) {
            return false;
        }
        beverageName = beverageNameAutoCompleteTextView.getText().toString();
        beverageSize = Float.parseFloat(beverageSizeEditText.getText().toString());
        alcoholByVolume = Float.parseFloat(alcoholByVolumeEditText.getText().toString());
        price = Float.parseFloat(priceEditText.getText().toString());
        return true;
    }

    private void setBeverage() {
        if (editBeverage == null) {
            newBeverage = beverageController.create(beverageName, beverageSize, alcoholByVolume, price);
        } else {
            newBeverage = editBeverage;
            newBeverage.setName(beverageName);
            newBeverage.setSize(beverageSize);
            newBeverage.setAlcoholByVolume(alcoholByVolume);
            newBeverage.setPrice(price);
            beverageController.calculateAlcoholValue(newBeverage);
        }
    }

    private void setTextChangedListeners() {
        beverageNameAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isBeverageNameInputError(true);
                calculateIfPossible();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        beverageSizeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isBeverageSizeInputError(true);
                calculateIfPossible();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        alcoholByVolumeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isAlcoholByVolumeInputError(true);
                calculateIfPossible();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPriceInputError(true);
                calculateIfPossible();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setFocusChangeListeners() {
        beverageNameAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isBeverageNameInputError(true);
                    calculateIfPossible();
                }
            }
        });

        beverageSizeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isBeverageSizeInputError(true);
                    calculateIfPossible();
                }
            }
        });

        alcoholByVolumeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isAlcoholByVolumeInputError(true);
                    calculateIfPossible();
                }
            }
        });

        priceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isPriceInputError(true);
                    calculateIfPossible();
                }
            }
        });
    }

}
