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
import com.lalikum.getdrunkforless.util.DecodeBitmap;

import java.util.Objects;

public class TutorialSliderAdapter extends PagerAdapter {

    private Context context;

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
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout_tutorial, container, false);

        ImageView tutorialImageView = view.findViewById(R.id.ivTutorialSlideLayoutImage);
        tutorialImageView.setImageBitmap(DecodeBitmap.decodeResource(context.getResources(), slideImages[position]));


        TextView tutorialTextView = view.findViewById(R.id.tvTutorialSlideLayoutText);

        tutorialTextView.setText(slideTexts[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
