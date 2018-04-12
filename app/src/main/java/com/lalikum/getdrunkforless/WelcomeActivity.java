package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.lalikum.getdrunkforless.controller.OptionsController;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeTitleTextView;
    Button getStartedButton;
    Animation fromTopAnimation;
    Animation fromBottomAnimation;

    OptionsController optionsController = new OptionsController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // check if its first launch, if not, go straight to welcome and home menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeTitleTextView = findViewById(R.id.welcomeTitleTextView);
        getStartedButton = findViewById(R.id.getStartedButton);

        fromTopAnimation = AnimationUtils.loadAnimation(this, R.anim.from_top);
        fromBottomAnimation = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        welcomeTitleTextView.setAnimation(fromTopAnimation);
        getStartedButton.setAnimation(fromBottomAnimation);

        // simple welcome screen without get started button if nth launch
        if (optionsController.isOptionsExists()) {
            getStartedButton.setVisibility(View.GONE);

            fromTopAnimation.setAnimationListener(new Animation.AnimationListener() {
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
