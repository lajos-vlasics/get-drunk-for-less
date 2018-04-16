package com.lalikum.getdrunkforless.controller;


import com.lalikum.getdrunkforless.model.MeasurementSystem;
import com.lalikum.getdrunkforless.model.Settings;

public class SettingsController {

    public boolean isOptionsExists() {
        Settings instance = Settings.findById(Settings.class, 1);
        return instance != null;
    }

    public void saveInstance(String userName, MeasurementSystem measurementSystem, String currency) {
        Settings instance;
        if (isOptionsExists()) {
            instance = Settings.findById(Settings.class, 1);
        } else {
            instance = new Settings();
        }
        instance.setUserName(userName);
        instance.setMeasurementSystem(measurementSystem);
        instance.setCurrency(currency);
        instance.save();
    }

    public Settings getInstance() {
        return Settings.findById(Settings.class, 1);
    }

    public String getUnit() {
        return getInstance().getUnit();
    }

    public String getCurrency() {
        return getInstance().getCurrency();
    }

    public String getUserName() {
        return getInstance().getUserName();
    }
}
