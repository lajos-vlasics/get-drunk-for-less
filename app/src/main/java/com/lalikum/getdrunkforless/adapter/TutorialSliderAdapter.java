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

    private int[] slideImages;
    private String[] slideTexts;

    public TutorialSliderAdapter(Context context, int[] slideImageIds, String[] slideTexts) {
        this.context = context;
        this.slideImages = slideImageIds;
        this.slideTexts = slideTexts;
    }

    @Override
    public int getCount() {
        return slideImages.length;
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

        // TODO running out of memory beacuse of big pics
        tutorialImageView.setImageResource(slideImages[position]);
        tutorialTextView.setText(slideTexts[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
