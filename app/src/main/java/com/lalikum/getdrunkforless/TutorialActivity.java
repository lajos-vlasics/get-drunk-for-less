package com.lalikum.getdrunkforless;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lalikum.getdrunkforless.adapter.TutorialSliderAdapter;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout slideDotsLayout;

    private int pageCount = 3;

    private TextView[] slideDotsTextViewList;

    private Button previousButton;
    private Button nextButton;
    private int currentPage;

    private TutorialSliderAdapter tutorialSliderAdapter;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            changeDotsColor(position);
            currentPage = position;

            if (currentPage == 0) {
                previousButton.setEnabled(false);
            } else if (currentPage == pageCount - 1) {
                previousButton.setEnabled(true);
                nextButton.setText("Continue");
                // TODO change color of text
                nextButton.setOnClickListener(null);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        continueButtonEventListener(v);
                    }
                });

            } else {
                previousButton.setEnabled(true);
                nextButton.setText("Next");
                nextButton.setOnClickListener(null);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextButtonEventListener(v);
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        slideViewPager = findViewById(R.id.slideViewPager);
        slideDotsLayout = findViewById(R.id.slideDotsLayout);

        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

        tutorialSliderAdapter = new TutorialSliderAdapter(this);

        slideViewPager.setAdapter(tutorialSliderAdapter);

        addDotsIndicator();

        slideViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    private void addDotsIndicator() {
        slideDotsTextViewList = new TextView[pageCount];

        for (int i = 0; i < slideDotsTextViewList.length; i++) {
            TextView dotTextView = new TextView(this);
            dotTextView.setText(Html.fromHtml("&#8226;"));
            dotTextView.setTextSize(35);

            slideDotsLayout.addView(dotTextView);
            slideDotsTextViewList[i] = dotTextView;
        }
        changeDotsColor(0);
    }

    private void changeDotsColor(int position) {
        for (int i = 0; i < slideDotsTextViewList.length; i++) {
            if (position == i) {
                slideDotsTextViewList[i].setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                slideDotsTextViewList[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    public void previousButtonEventListener(View view) {
        slideViewPager.setCurrentItem(currentPage - 1);
    }

    public void nextButtonEventListener(View view) {
        slideViewPager.setCurrentItem(currentPage + 1);
    }

    public void continueButtonEventListener(View view) {
        Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }

}
