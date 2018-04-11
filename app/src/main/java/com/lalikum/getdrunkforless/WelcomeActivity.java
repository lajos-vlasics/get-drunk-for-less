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

    OptionsController optionsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // check if its first launch, if not, go straight to home menu
        //TODO what if no database exists?
        optionsController = new OptionsController();
        if (optionsController.isOptionsExists()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeTitleTextView = findViewById(R.id.welcomeTitleTextView);
        getStartedButton = findViewById(R.id.getStartedButton);

        fromTopAnimation = AnimationUtils.loadAnimation(this, R.anim.from_top);
        fromBottomAnimation = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        welcomeTitleTextView.setAnimation(fromTopAnimation);
        getStartedButton.setAnimation(fromBottomAnimation);
    }

    public void toTutorialActivity(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }

}
