package com.lalikum.getdrunkforless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.lalikum.getdrunkforless.controller.OptionsController;

public class WelcomeActivity extends AppCompatActivity {

    ImageView ivWelcomeTitle;
    Button bGetStarted;
    Animation anFromTop;
    Animation anFromBottom;

    OptionsController optionsController = new OptionsController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // TODO hide welcome correctly
//        getSupportActionBar().hide();

        ivWelcomeTitle = findViewById(R.id.ivWelcomeTitle);
        bGetStarted = findViewById(R.id.bWelcomeGetStarted);

        anFromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        anFromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        ivWelcomeTitle.setAnimation(anFromTop);
        bGetStarted.setAnimation(anFromBottom);

        // simple welcome screen without get started button if nth launch
        // TODO no db error at first run, but app can run
        if (optionsController.isOptionsExists()) {
            bGetStarted.setVisibility(View.GONE);

            anFromTop.setAnimationListener(new Animation.AnimationListener() {
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
        bGetStarted.setOnClickListener(new View.OnClickListener() {
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
