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

import java.util.Objects;

public class WelcomeFirstActivity extends AppCompatActivity {

    SettingsController settingsController = new SettingsController();
    private ImageView welcomeTitleImageView;
    private Button getStartedButton;
    private Animation fromTopAnim;
    private Animation fromBottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_first);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // go to simple welcome screen without get started button if Nth launch
        if (settingsController.isSettingsExists()) {
            toWelcomeActivity();
        }

        welcomeTitleImageView = findViewById(R.id.ivWelcomeTitle);
        getStartedButton = findViewById(R.id.btnWelcomeGetStarted);

        fromTopAnim = AnimationUtils.loadAnimation(this, R.anim.from_top);
        fromBottomAnim = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        welcomeTitleImageView.setAnimation(fromTopAnim);
        getStartedButton.setAnimation(fromBottomAnim);

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

    public void toWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeNthActivity.class);
        startActivity(intent);
    }

}
