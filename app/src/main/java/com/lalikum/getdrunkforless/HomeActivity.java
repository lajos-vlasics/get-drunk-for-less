package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lalikum.getdrunkforless.adapter.BeveragesListAdapter;
import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.controller.SettingsController;
import com.lalikum.getdrunkforless.model.Beverage;
import com.lalikum.getdrunkforless.util.RecyclerItemTouchHelper;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static int beverageCountLimit = 30;

    private TextView addBeverageHereTextView;
    private RecyclerView beveragesRecyclerView;
    private ImageButton addBeverageImageButton;

    private SettingsController settingsController = new SettingsController();
    private BeverageController beverageController = new BeverageController();
    private BeveragesListAdapter beveragesListAdapter;

    private List<Beverage> beverageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        beveragesRecyclerView = findViewById(R.id.rvHomeBeverages);
        addBeverageHereTextView = findViewById(R.id.tvHomeAddBeverageHere);
        addBeverageImageButton = findViewById(R.id.btnHomeAddBeverage);

        beverageList = beverageController.getAllSortedByAlcoholValue();

        // TODO search field in actionbar
        // TODO fix water img to bg somehow

        // init
        setTitle(settingsController.getUserName() + " " + getString(R.string.home_title_suffix));
        hideAddBeverageHereTextView();

        // listeners
        addBeverageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddBeverageActivity();
            }
        });

        // in no beverage, return immediately
        if (beverageList.size() == 0) {
            showAddBeverageHereTextView();
        } else {
            // set up the RecyclerView
            beveragesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            beveragesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            beveragesListAdapter = new BeveragesListAdapter(this, beverageList);

//            RecyclerView.ItemDecoration dividerItemDecoration = new BeverageDividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider));
//            beveragesRecyclerView.addItemDecoration(dividerItemDecoration);

            beveragesRecyclerView.setAdapter(beveragesListAdapter);

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(beveragesRecyclerView);
        }
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

    public void toAddBeverageActivity() {
        // prevent adding new beverages if already got 30
        if (beverageList.size() > beverageCountLimit) {
            Toast.makeText(this, getString(R.string.home_cant_add_beverage_toast_message), Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AddBeverageActivity.class);
        startActivity(intent);
    }

    public void deleteBeverage(int position) {
        // prevent delete if its the last empty row
        if (beveragesListAdapter.isLastPosition(position)) {
            return;
        }
        Beverage beverage = beveragesListAdapter.getItem(position);
        beveragesListAdapter.removeItem(position);
        beverageController.delete(beverage);

        // show add button text, if adapter contains no beverages, only the null row
        if (beverageList.size() == 1) {
            showAddBeverageHereTextView();
        }
    }

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

    public void hideAddBeverageHereTextView() {
        addBeverageHereTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (beveragesListAdapter.isLastPosition(position)) {
            return;
        }
        Beverage beverage = beveragesListAdapter.getItem(position);
        deleteBeverage(viewHolder.getAdapterPosition());

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.clHome), beverage.getName() + " " + getString(R.string.home_snack_delete_suffix), Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(R.string.home_snackbar_undo), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // undo is selected, restore the deleted item
                beverageController.save(beverage);
                beveragesListAdapter.addItem(beverage, position);
                // prevent to undo multiple times
                snackbar.setAction(getString(R.string.home_snackbar_undo), null);
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();

    }
}
