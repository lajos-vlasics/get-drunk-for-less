package com.lalikum.getdrunkforless.controller;

import com.lalikum.getdrunkforless.model.MeasurementSystem;
import com.lalikum.getdrunkforless.model.Options;

public class OptionsController {

    public boolean isOptionsExists() {
        Options instance = Options.findById(Options.class, 1);
        return instance != null;
    }

    public void saveInstance(String userName, MeasurementSystem measurementSystem, String currency) {
        Options instance;
        if (isOptionsExists()) {
            instance = Options.findById(Options.class, 1);
        } else {
            instance = new Options();
        }
        instance.setUserName(userName);
        instance.setMeasurementSystem(measurementSystem);
        instance.setCurrency(currency);
        instance.save();
    }

    public Options getInstance() {
        return Options.findById(Options.class, 1);
    }

    public String getUnit() {
        return getInstance().getUnit();
    }

    public String getCurrency() {
        return getInstance().getCurrency();
    }
}
