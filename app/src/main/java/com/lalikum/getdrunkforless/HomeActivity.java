package com.lalikum.getdrunkforless;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lalikum.getdrunkforless.adapter.BeveragesListAdapter;
import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.controller.OptionsController;
import com.lalikum.getdrunkforless.model.Beverage;
import com.lalikum.getdrunkforless.model.Options;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    OptionsController optionsController = new OptionsController();
    BeverageController beverageController = new BeverageController();

    BeveragesListAdapter beveragesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<Beverage> beverageList = beverageController.getAll();

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.beveragesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        beveragesListAdapter = new BeveragesListAdapter(this, beverageList);
//        beveragesListAdapter.setClickListener(this);
        recyclerView.setAdapter(beveragesListAdapter);

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
        // TODO edit drink here
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}
