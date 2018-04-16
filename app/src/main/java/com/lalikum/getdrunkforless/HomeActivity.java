package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lalikum.getdrunkforless.adapter.BeveragesListAdapter;
import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.controller.SettingsController;
import com.lalikum.getdrunkforless.model.Beverage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView addBeverageHereTextView;
    RecyclerView beveragesRecyclerView;

    SettingsController settingsController = new SettingsController();
    BeverageController beverageController = new BeverageController();
    BeveragesListAdapter beveragesListAdapter;

    List<Beverage> beverageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        beveragesRecyclerView = findViewById(R.id.rvHomeBeverages);
        addBeverageHereTextView = findViewById(R.id.tvHomeAddBeverageHere);

        beverageList = beverageController.getAll();

        // TODO set icon to actionbar
        setTitle(settingsController.getUserName() + "'s beverages");

        // TODO visible scrollbar
        // TODO search field in actionbar

        // in no beverage, than show Add beverage here text only
        if (beverageList.size() == 0) {
            showAddBeverageHereTextView();
            return;
        }

        // sort list by alcohol value
        // TODO move the sorting thin to controller class
        Collections.sort(beverageList, new Comparator<Beverage>() {
            @Override
            public int compare(Beverage o1, Beverage o2) {
                return Float.compare(o1.getAlcoholValue(), o2.getAlcoholValue());
            }
        });

        // set up the RecyclerView
        beveragesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        beveragesListAdapter = new BeveragesListAdapter(this, beverageList);
        beveragesRecyclerView.setAdapter(beveragesListAdapter);

    }

    // Create action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnHomeMenuSettings:
                toSettingsActivity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    public void toSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void toAddBeverageActivity(View view) {
        Intent intent = new Intent(this, AddBeverageActivity.class);
        startActivity(intent);
    }

    public void deleteBeverage(View view) {
        // TODO show modal for it
        ConstraintLayout layout = (ConstraintLayout) view.getParent();
        int position = beveragesRecyclerView.getChildLayoutPosition(layout);

        Beverage beverage = beveragesListAdapter.getItem(position);
        beverage.delete();

        beveragesListAdapter.removeItem(position);
        beveragesListAdapter.notifyDataSetChanged();

        if (beveragesListAdapter.getItemCount() == 0) {
            showAddBeverageHereTextView();
        }
    }

/*    public void editBeverageButtonEvent(View view) {
        RelativeLayout layout = (RelativeLayout) view.getParent();
        int position = beveragesRecyclerView.getChildLayoutPosition(layout);

        Beverage beverage = beveragesListAdapter.getItem(position);
        Intent intent = new Intent(this, AddBeverageActivity.class);
        intent.putExtra("beverageId", beverage.getId());
        startActivity(intent);
    }*/

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void showAddBeverageHereTextView() {
        addBeverageHereTextView.setVisibility(View.VISIBLE);
    }
}
