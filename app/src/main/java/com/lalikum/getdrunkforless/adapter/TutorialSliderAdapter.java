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
            R.drawable.iw_tutorial_1,
            R.drawable.iw_tutorial_2,
            R.drawable.iw_tutorial_3,
            R.drawable.iw_tutorial_4
    };

    private String[] slide_texts = {
            "Running out of money?",
            "Still want to party HARD? Don't worry no more...",
            "I can help you to find the best beverages deals in a store or a bar!",
            "By calculating it's pure alcohol value!"
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

        // TODO running out of memory
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
