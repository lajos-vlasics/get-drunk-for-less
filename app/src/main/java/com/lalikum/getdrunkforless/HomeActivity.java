package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.lalikum.getdrunkforless.util.BeverageDividerItemDecoration;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

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
            beveragesListAdapter = new BeveragesListAdapter(this, beverageList);
            beveragesRecyclerView.setItemAnimator(new DefaultItemAnimator());

            RecyclerView.ItemDecoration dividerItemDecoration = new BeverageDividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider));
            beveragesRecyclerView.addItemDecoration(dividerItemDecoration);

            beveragesRecyclerView.setAdapter(beveragesListAdapter);
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

    // slide delete listener
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // Row is swiped from recycler view
            // remove it from adapter
            // TODO show animation for it
            deleteBeverage(viewHolder.getAdapterPosition());
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            // view the background view
        }
    };


    public void toSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void toAddBeverageActivity() {
        // prevent adding new beverages if already got 30
        if (beverageList.size() > beverageCountLimit) {
            Toast.makeText(this, getString(R.string.add_beverage_toast_message), Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AddBeverageActivity.class);
        startActivity(intent);
    }

    public void deleteBeverage(int position) {
        // prevent delete if its the last empty row
        if (position == beveragesListAdapter.getItemCount() - 1) {
            return;
        }
        Beverage beverage = beveragesListAdapter.getItem(position);
        beveragesListAdapter.removeItem(position);
        beverage.delete();

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
}
