package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.graphics.Color;
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

    private static int pageCount = 4;
    private Button previousButton;
    private Button nextButton;
    private ViewPager slideViewPager;
    private LinearLayout slideDotsLayout;
    private TextView[] slideDotsTextViewList;
    private TutorialSliderAdapter tutorialSliderAdapter;
    private int currentPagePosition;


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            changeDotsColor(position);
            currentPagePosition = position;

            if (currentPagePosition == 0) {
                previousButton.setEnabled(false);
            } else if (currentPagePosition == pageCount - 1) {
                previousButton.setEnabled(true);
                nextButton.setText(getString(R.string.tutorial_continue));
                nextButton.setTextColor(getResources().getColor(R.color.colorGdGreen));
                nextButton.setOnClickListener(null);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        continueButtonEventListener(v);
                    }
                });

            } else {
                previousButton.setEnabled(true);
                nextButton.setText(getString(R.string.tutorial_next));
                nextButton.setTextColor(Color.WHITE); // TODO set to default text color not mock
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

        slideViewPager = findViewById(R.id.vpTutorialSlide);
        slideDotsLayout = findViewById(R.id.llTutorialSlideDots);

        previousButton = findViewById(R.id.btnTutorialPrevious);
        nextButton = findViewById(R.id.btnTutorialNext);

        int[] slideImageIds = {
                R.drawable.iw_tutorial_1,
                R.drawable.iw_tutorial_2,
                R.drawable.iw_tutorial_3,
                R.drawable.iw_tutorial_4
        };

        String[] slideTexts = {
                getString(R.string.tutorial_slide_text1),
                getString(R.string.tutorial_slide_text2),
                getString(R.string.tutorial_slide_text3),
                getString(R.string.tutorial_slide_text4)
        };

        tutorialSliderAdapter = new TutorialSliderAdapter(this, slideImageIds, slideTexts);

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
                slideDotsTextViewList[i].setTextColor(getResources().getColor(R.color.colorGdBlue));
            } else {
                slideDotsTextViewList[i].setTextColor(getResources().getColor(R.color.colorGdLightGrey));
            }
        }
    }

    public void previousButtonEventListener(View view) {
        slideViewPager.setCurrentItem(currentPagePosition - 1);
    }

    public void nextButtonEventListener(View view) {
        slideViewPager.setCurrentItem(currentPagePosition + 1);
    }

    public void continueButtonEventListener(View view) {
        Intent intent = new Intent(TutorialActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
