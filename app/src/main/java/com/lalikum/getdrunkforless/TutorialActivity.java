package com.lalikum.getdrunkforless;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lalikum.getdrunkforless.adapter.TutorialSliderAdapter;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout slideDotsLayout;

    private TextView[] slideDotsTextViewList;

    private TutorialSliderAdapter tutorialSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        slideViewPager = findViewById(R.id.slideViewPager);
        slideDotsLayout = findViewById(R.id.slideDotsLayout);

        tutorialSliderAdapter = new TutorialSliderAdapter(this);

        slideViewPager.setAdapter(tutorialSliderAdapter);

        addDotsIndicator();

        slideViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    private void addDotsIndicator() {
        slideDotsTextViewList = new TextView[3];

        for (int i = 0; i < 3; i++) {
            TextView dotTextView = new TextView(this);
            dotTextView.setText(Html.fromHtml("&#8226;"));
            dotTextView.setTextSize(35);

            slideDotsLayout.addView(dotTextView);
            slideDotsTextViewList[i] = dotTextView;
        }
        changeDotsColor(0);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changeDotsColor(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeDotsColor(int position) {
        for (int i = 0; i < slideDotsTextViewList.length; i++) {
            if (position == i) {
                slideDotsTextViewList[i].setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                slideDotsTextViewList[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

}
