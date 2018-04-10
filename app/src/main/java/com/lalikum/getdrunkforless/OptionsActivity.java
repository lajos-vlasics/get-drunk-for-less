package com.lalikum.getdrunkforless;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class OptionsActivity extends AppCompatActivity {

    String userName = "Name";
    String unitType = "ml";
    String currency = "Ft";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
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
                    unitType = "ml";
                break;
            case R.id.imperialRadioButton:
                if (checked)
                    unitType = "fl oz";
                break;
        }
    }

}
