package com.lalikum.getdrunkforless;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lalikum.getdrunkforless.model.Options;

import java.util.Iterator;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Options options = Options.findById(Options.class, 1);

        System.out.println(options.getUserName());
        System.out.println(options.getMeasurementSystem().getUnit());
        System.out.println(options.getCurrency());

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}
