package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.lalikum.getdrunkforless.adapter.BeveragesListAdapter;
import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.controller.OptionsController;
import com.lalikum.getdrunkforless.model.Beverage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    OptionsController optionsController = new OptionsController();
    BeverageController beverageController = new BeverageController();

    BeveragesListAdapter beveragesListAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        List<Beverage> beverageList = beverageController.getAll();
        // sort list by alcohol value
        Collections.sort(beverageList, new Comparator<Beverage>() {
            @Override
            public int compare(Beverage o1, Beverage o2) {
                return Float.compare(o1.getAlcoholValue(), o2.getAlcoholValue());
            }
        });

        // set up the RecyclerView
        recyclerView = findViewById(R.id.beveragesRecyclerView);
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

    public void deleteBeverage(View view) {
        // TODO show modal for it
        RelativeLayout layout = (RelativeLayout) view.getParent();
        int position = recyclerView.getChildLayoutPosition(layout);

        Beverage beverage = beveragesListAdapter.getItem(position);
        beverage.delete();

        beveragesListAdapter.removeItem(position);
        beveragesListAdapter.notifyDataSetChanged();
    }

    public void editBeverage(View view) {
        RelativeLayout layout = (RelativeLayout) view.getParent();
        int position = recyclerView.getChildLayoutPosition(layout);

        Beverage beverage = beveragesListAdapter.getItem(position);
        Intent intent = new Intent(this, AddBeverageActivity.class);
        intent.putExtra("beverageId", beverage.getId());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
