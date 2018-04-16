package com.lalikum.getdrunkforless.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lalikum.getdrunkforless.R;

public class TutorialSliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public TutorialSliderAdapter(Context context) {
        this.context = context;
    }

    private int[] slide_images = {
            // TODO make a the pics in circle
            R.drawable.tutorial_1,
            R.drawable.tutorial_2,
            R.drawable.tutorial_3
    };

    private String[] slide_texts = {
            "Running out of money?",
            "Still want to party HARD? Don't worry no more...",
            "I can help you to find the best value beverages by calculating it's pure alcohol value!"
    };


    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (View) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout_tutorial, container, false);

        ImageView tutorialImageView = view.findViewById(R.id.ivTutorialSlideLayoutImage);
        TextView tutorialTextView = view.findViewById(R.id.tvTutorialSlideLayoutText);

        tutorialImageView.setImageResource(slide_images[position]);
        tutorialTextView.setText(slide_texts[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
