package com.lalikum.getdrunkforless;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.lalikum.getdrunkforless.adapter.TutorialSliderAdapter;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout slideDotsLayout;

    private TutorialSliderAdapter tutorialSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        slideViewPager = findViewById(R.id.slideViewPager);
        slideDotsLayout = findViewById(R.id.slideDotsLayout);

        tutorialSliderAdapter = new TutorialSliderAdapter(this);

        slideViewPager.setAdapter(tutorialSliderAdapter);
    }
}
