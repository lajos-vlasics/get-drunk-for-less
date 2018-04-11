package com.lalikum.getdrunkforless;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.controller.OptionsController;
import com.lalikum.getdrunkforless.model.Options;

public class HomeActivity extends AppCompatActivity {

    OptionsController optionsController = new OptionsController();
    BeverageController beverageController = new BeverageController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



    }

    public void toOptionsActivity(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void toAddBeverageActivity(View view) {
        Intent intent = new Intent(this, AddBeverageActivity.class);
        startActivity(intent);
    }

    public void deleteBeverage() {
        // TODO show modal for it
    }

    public void editBeverage() {

    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}
