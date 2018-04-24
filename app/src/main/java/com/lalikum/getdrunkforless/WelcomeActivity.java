package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.lalikum.getdrunkforless.controller.SettingsController;

public class WelcomeActivity extends AppCompatActivity {

    ImageView welcomeTitleImageView;
    ImageView unicornImageView;
    Button getStartedButton;
    Animation fromTopAnim;
    Animation fromBottomAnim;

    SettingsController settingsController = new SettingsController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO make proper horizontal view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // TODO hide action bar correctly
        getSupportActionBar().hide();

        welcomeTitleImageView = findViewById(R.id.ivWelcomeTitle);
        unicornImageView = findViewById(R.id.ivWelcomeUnicorn);
        getStartedButton = findViewById(R.id.btnWelcomeGetStarted);

        fromTopAnim = AnimationUtils.loadAnimation(this, R.anim.from_top);
        fromBottomAnim = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        welcomeTitleImageView.setAnimation(fromTopAnim);
        getStartedButton.setAnimation(fromBottomAnim);
        unicornImageView.setAnimation(fromBottomAnim);

        // simple welcome screen without get started button if nth launch
        // TODO db error at first run, but app can run
        if (settingsController.isSettingsExists()) {
            getStartedButton.setVisibility(View.GONE);
            unicornImageView.setVisibility(View.VISIBLE);

            fromBottomAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    try {
                        Thread.sleep(1000);
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

        // event listeners
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTutorialActivity();
            }
        });
    }

    public void toTutorialActivity() {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }

    public void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
