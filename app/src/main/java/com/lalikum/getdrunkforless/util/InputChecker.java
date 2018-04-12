package com.lalikum.getdrunkforless.util;

import android.text.TextUtils;
import android.widget.EditText;

public class InputChecker {

    public boolean isEmptyInput(EditText... editTextList) {
        boolean isEmptyInput = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                isEmptyInput = true;
            }
        }
        return isEmptyInput;
    }

    public boolean isEmptyInput(String errorMessage, EditText... editTextList) {
        boolean isEmptyInput = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                editText.setError(errorMessage);
                isEmptyInput = true;
            }
        }
        return isEmptyInput;
    }

    public boolean isZeroInput(EditText... editTextList) {
        boolean isZeroInput = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            // prevent parse error null string
            if (TextUtils.isEmpty(inputText)) {
                isZeroInput = true;
                continue;
            }
            if (Float.parseFloat(inputText) == 0) {
                isZeroInput = true;
            }
        }
        return isZeroInput;
    }

    public boolean isZeroInput(String errorMessage, EditText... editTextList) {
        boolean isZeroInput = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            // prevent parse error null string
            if (TextUtils.isEmpty(inputText)) {
                isZeroInput = true;
                continue;
            }
            if (Float.parseFloat(inputText) == 0) {
                editText.setError(errorMessage);
                isZeroInput = true;
            }
        }
        return isZeroInput;
    }

    public boolean isHigherInput(int max, EditText... editTextList) {
        boolean isHigherInput = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            // prevent parse error null string
            if (TextUtils.isEmpty(inputText)) {
                isHigherInput = true;
                continue;
            }
            if (Float.parseFloat(inputText) > max) {
                isHigherInput = true;
            }
        }
        return isHigherInput;
    }

    public boolean isHigherInput(String errorMessage, int max, EditText... editTextList) {
        boolean isHigherInput = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            // prevent parse error null string
            if (TextUtils.isEmpty(inputText)) {
                isHigherInput = true;
                continue;
            }
            if (Float.parseFloat(inputText) > max) {
                editText.setError(errorMessage);
                isHigherInput = true;
            }
        }
        return isHigherInput;
    }
}
