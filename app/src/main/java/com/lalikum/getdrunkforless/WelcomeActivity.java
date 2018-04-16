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
    Button getStartedButton;
    Animation fromTopAnim;
    Animation fromBottomAnim;

    SettingsController optionsController = new SettingsController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // TODO hide welcome correctly
        getSupportActionBar().hide();

        welcomeTitleImageView = findViewById(R.id.ivWelcomeTitle);
        getStartedButton = findViewById(R.id.btnWelcomeGetStarted);

        fromTopAnim = AnimationUtils.loadAnimation(this, R.anim.from_top);
        fromBottomAnim = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        welcomeTitleImageView.setAnimation(fromTopAnim);
        getStartedButton.setAnimation(fromBottomAnim);

        // simple welcome screen without get started button if nth launch
        // TODO no db error at first run, but app can run
        if (optionsController.isOptionsExists()) {
            getStartedButton.setVisibility(View.GONE);
            // TODO show pony instead of the GET STARTED btn

            fromTopAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // TODO there is a little movement after thread sleep
                    try {
                        Thread.sleep(2000);
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
