package com.lalikum.getdrunkforless.util;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class InputChecker {

    public void setButtonCheck(Button button, EditText... editTextList) {
        boolean isEmpty = isEmptyInput(editTextList);
        if (isEmpty) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }

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

    public boolean setEmptyInputError(String errorMessage, EditText... editTextList) {
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
}
