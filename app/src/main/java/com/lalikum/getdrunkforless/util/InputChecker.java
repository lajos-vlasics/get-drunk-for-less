package com.lalikum.getdrunkforless.util;

import android.text.TextUtils;
import android.widget.EditText;

public class InputChecker {

    public boolean isEmptyInput(EditText... editTextList) {
        return isEmptyInput(false, "", editTextList);
    }

    public boolean isEmptyInput(String errorMessage, EditText... editTextList) {
        return isEmptyInput(true, errorMessage, editTextList);
    }

    private boolean isEmptyInput(boolean isErrorMessage, String errorMessage, EditText... editTextList) {
        boolean isError = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                if (isErrorMessage) {
                    editText.setError(errorMessage);
                }
                isError = true;
            }
        }
        return isError;
    }

    public boolean isZeroInput(EditText... editTextList) {
        return isZeroInput(false, "", editTextList);
    }

    public boolean isZeroInput(String errorMessage, EditText... editTextList) {
        return isZeroInput(true, errorMessage, editTextList);
    }

    private boolean isZeroInput(boolean isErrorMessage, String errorMessage, EditText... editTextList) {
        boolean isError = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            // prevent parse error null string
            if (TextUtils.isEmpty(inputText)) {
                isError = true;
                continue;
            }
            if (inputText.equals(".") || Float.parseFloat(inputText) == 0) {
                if (isErrorMessage) {
                    editText.setError(errorMessage);
                    isError = true;
                }
            }
        }
        return isError;
    }

    public boolean isHigherInput(int max, EditText... editTextList) {
        return isHigherInput(false, "", max, editTextList);
    }

    public boolean isHigherInput(String errorMessage, int max, EditText... editTextList) {
        return isHigherInput(true, errorMessage, max, editTextList);
    }

    private boolean isHigherInput(boolean isErrorMessage, String errorMessage, int max, EditText... editTextList) {
        boolean isError = false;
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();
            // prevent parse error null string
            if (TextUtils.isEmpty(inputText) || inputText.equals(".")) {
                isError = true;
                continue;
            }
            if (Float.parseFloat(inputText) > max) {
                if (isErrorMessage) {
                    editText.setError(errorMessage);
                    isError = true;
                }
            }
        }
        return isError;
    }
}
