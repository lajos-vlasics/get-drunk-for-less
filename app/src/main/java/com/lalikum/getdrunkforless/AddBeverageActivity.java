package com.lalikum.getdrunkforless;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;

import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.controller.SettingsController;
import com.lalikum.getdrunkforless.model.Beverage;
import com.lalikum.getdrunkforless.util.InputChecker;

public class AddBeverageActivity extends AppCompatActivity {

    private static EditText beverageNameEditText;
    private static EditText beverageSizeEditText;
    private static EditText alcoholByVolumeEditText;
    private static EditText priceEditText;
    private static EditText bottlesEditText;
    private static MenuItem saveMenuItem;
    private static int maxAlcoholByVolume = 100;
    private static int maxBeverageSize = 10000000;
    private static int maxPrice = 1000000;
    private static int defaultBottles = 1;
    private static int maxBottles = 100; // TODO set in EditText xml
    private TextInputLayout beverageNameTextInputLayout;
    private TextInputLayout beverageSizeTextInputLayout;
    private TextInputLayout alcoholByVolumeTextInputLayout;
    private TextInputLayout priceByVolumeTextInputLayout;
    private TextInputLayout bottlesTextInputLayout;
    private TextView alcoholValueTextView;
    private TextView ofAlcoholTextView;
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
    private int bottles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO make it scrollable
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beverage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        beverageNameTextInputLayout = findViewById(R.id.tilAddBeverageName);
        beverageSizeTextInputLayout = findViewById(R.id.tilAddBeverageSize);
        alcoholByVolumeTextInputLayout = findViewById(R.id.tilAddBeverageABV);
        priceByVolumeTextInputLayout = findViewById(R.id.tilAddBeveragePrice);
        bottlesTextInputLayout = findViewById(R.id.tilAddBeverageBottles);

        ofAlcoholTextView = findViewById(R.id.tvAddBeverageOfAlcohol);
        alcoholValueTextView = findViewById(R.id.tvAddBeverageAlcoholValue);

        // TODO autocomplete beverages names
        beverageNameEditText = findViewById(R.id.etAddBeverageName);
        beverageSizeEditText = findViewById(R.id.etAddBeverageSize); // TODO change l/dl/cl/ml
        alcoholByVolumeEditText = findViewById(R.id.etAddBeverageABV);
        priceEditText = findViewById(R.id.etAddBeveragePrice);
        bottlesEditText = findViewById(R.id.etAddBeverageBottles);

        // Set unit and currency field from settings DB
        unit = settingsController.getUnit();
        currency = settingsController.getCurrency();

        beverageSizeTextInputLayout.setHint(String.format("%s (%s)", getString(R.string.add_beverage_size), unit));
        priceByVolumeTextInputLayout.setHint(String.format("%s (%s)", getString(R.string.add_beverage_price), currency));

        bottlesTextInputLayout.setHintAnimationEnabled(false);
        bottlesEditText.setText(String.valueOf(defaultBottles));
        bottlesTextInputLayout.setHintAnimationEnabled(true);

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
            bottlesTextInputLayout.setHintAnimationEnabled(false);

            editBeverage = beverageController.getById(beverageId);
            beverageNameEditText.setText(editBeverage.getName());
            beverageSizeEditText.setText(getNoDecimalStringIfInteger(editBeverage.getSize()));
            alcoholByVolumeEditText.setText(String.valueOf(editBeverage.getAlcoholByVolume()));
            priceEditText.setText(getNoDecimalStringIfInteger(editBeverage.getPrice()));
            bottlesEditText.setText(String.valueOf(editBeverage.getBottles()));
            calculate();

            setAlcoholValueTextLayoutVisibility(true);

            // turn on textInputLayout hint animations
            beverageNameTextInputLayout.setHintAnimationEnabled(true);
            beverageSizeTextInputLayout.setHintAnimationEnabled(true);
            alcoholByVolumeTextInputLayout.setHintAnimationEnabled(true);
            priceByVolumeTextInputLayout.setHintAnimationEnabled(true);
            bottlesTextInputLayout.setHintAnimationEnabled(true);
        }

        // set event listeners for edit texts
        // TODO error message fills auto if screen orientation changes
        beverageNameEditText.addTextChangedListener(new TextWatcher() {
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

        beverageNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isBeverageNameInputError(true);
                    calculateIfPossible();
                }
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

        beverageSizeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isBeverageSizeInputError(true);
                    calculateIfPossible();
                }
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

        alcoholByVolumeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isAlcoholByVolumeInputError(true);
                    calculateIfPossible();
                }
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

        priceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isPriceInputError(true);
                    calculateIfPossible();
                }
            }
        });

        bottlesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isBottlesInputError(true);
                calculateIfPossible();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bottlesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isBottlesInputError(true);
                    calculateIfPossible();
                }
            }
        });
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
    // TODO keyboard always pops up after change edittext field
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_name), beverageNameEditText);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(beverageNameEditText);
        }
        return isEmptyInput;
    }

    private boolean isBeverageSizeInputError(boolean setErrorText) {
        boolean isEmptyInput;
        boolean isZeroInput;
        boolean isHigherInput;
        if (setErrorText) {
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_size), beverageSizeEditText);
            isZeroInput = inputChecker.isZeroInput(getString(R.string.add_beverage_zero_size), beverageSizeEditText);
            isHigherInput = inputChecker.isHigherInput(getString(R.string.add_beverage_higher_size), maxBeverageSize, beverageSizeEditText);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(beverageSizeEditText);
            isZeroInput = inputChecker.isZeroInput(beverageSizeEditText);
            isHigherInput = inputChecker.isHigherInput(maxBeverageSize, beverageSizeEditText);

        }
        return isEmptyInput || isZeroInput || isHigherInput;
    }

    private boolean isAlcoholByVolumeInputError(boolean setErrorText) {
        boolean isEmptyInput;
        boolean isZeroInput;
        boolean isHigherInput;
        if (setErrorText) {
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_abv), alcoholByVolumeEditText);
            isZeroInput = inputChecker.isZeroInput(getString(R.string.add_beverage_error_zero_abv), alcoholByVolumeEditText);
            isHigherInput = inputChecker.isHigherInput(getString(R.string.add_beverage_error_higher_abv), maxAlcoholByVolume, alcoholByVolumeEditText);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(alcoholByVolumeEditText);
            isZeroInput = inputChecker.isZeroInput(alcoholByVolumeEditText);
            isHigherInput = inputChecker.isHigherInput(maxAlcoholByVolume, alcoholByVolumeEditText);
        }
        return isEmptyInput || isZeroInput || isHigherInput;
    }

    private boolean isPriceInputError(boolean setErrorText) {
        boolean isEmptyInput;
        boolean isZeroInput;
        boolean isHigherInput;
        if (setErrorText) {
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_price), priceEditText);
            isZeroInput = inputChecker.isZeroInput(getString(R.string.add_beverage_error_zero_price), priceEditText);
            isHigherInput = inputChecker.isHigherInput(getString(R.string.add_beverage_error_higher_price), maxPrice, priceEditText);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(priceEditText);
            isZeroInput = inputChecker.isZeroInput(priceEditText);
            isHigherInput = inputChecker.isHigherInput(maxPrice, priceEditText);
        }
        return isEmptyInput || isZeroInput || isHigherInput;
    }

    private boolean isBottlesInputError(boolean setErrorText) {
        boolean isEmptyInput;
        boolean isZeroInput;
        boolean isHigherInput;
        if (setErrorText) {
            isEmptyInput = inputChecker.isEmptyInput(getString(R.string.add_beverage_error_empty_bottles), bottlesEditText);
            isZeroInput = inputChecker.isZeroInput(getString(R.string.add_beverage_error_zero_bottles), bottlesEditText);
            isHigherInput = inputChecker.isHigherInput(getString(R.string.add_beverage_error_higher_bottles), maxBottles, bottlesEditText);
        } else {
            isEmptyInput = inputChecker.isEmptyInput(bottlesEditText);
            isZeroInput = inputChecker.isZeroInput(bottlesEditText);
            isHigherInput = inputChecker.isHigherInput(maxBottles, bottlesEditText);
        }
        return isEmptyInput || isZeroInput || isHigherInput;
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
        boolean isBottlesInputError = isBottlesInputError(false);
        return isNameInputError || isSizeInputError || isAlcoholInputError || isPriceInputError || isBottlesInputError;
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
        beverageName = beverageNameEditText.getText().toString();
        beverageSize = Float.parseFloat(beverageSizeEditText.getText().toString());
        alcoholByVolume = Float.parseFloat(alcoholByVolumeEditText.getText().toString());
        price = Float.parseFloat(priceEditText.getText().toString());
        bottles = Integer.parseInt(bottlesEditText.getText().toString());
        return true;
    }

    private void setBeverage() {
        if (editBeverage == null) {
            newBeverage = beverageController.create(beverageName, beverageSize, alcoholByVolume, price, bottles);
        } else {
            newBeverage = editBeverage;
            newBeverage.setName(beverageName);
            newBeverage.setSize(beverageSize);
            newBeverage.setAlcoholByVolume(alcoholByVolume);
            newBeverage.setPrice(price);
            newBeverage.setBottles(bottles);
            beverageController.calculateAlcoholValue(newBeverage);
        }
    }

}
