package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.lalikum.getdrunkforless.controller.SettingsController;

import java.util.Objects;

public class WelcomeNthActivity extends AppCompatActivity {

    SettingsController settingsController = new SettingsController();
    private ImageView welcomeTitleImageView;
    private ImageView unicornImageView;
    private Animation fromTopAnim;
    private Animation fromBottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_nth);
        Objects.requireNonNull(getSupportActionBar()).hide();

        welcomeTitleImageView = findViewById(R.id.ivWelcomeTitle);
        unicornImageView = findViewById(R.id.ivWelcomeUnicorn);

        fromTopAnim = AnimationUtils.loadAnimation(this, R.anim.from_top);
        fromBottomAnim = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        welcomeTitleImageView.setAnimation(fromTopAnim);
        unicornImageView.setAnimation(fromBottomAnim);

        fromBottomAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toHomeActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
