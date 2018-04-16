package com.lalikum.getdrunkforless.model;

import com.orm.SugarRecord;

public class Settings extends SugarRecord {

    private String userName;
    private MeasurementSystem measurementSystem;
    private String currency;

    public Settings() {
    }

    public Settings(String userName, MeasurementSystem measurement, String currency) {
        this.userName = userName;
        this.measurementSystem = measurement;
        this.currency = currency;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public MeasurementSystem getMeasurementSystem() {
        return measurementSystem;
    }

    public void setMeasurementSystem(MeasurementSystem measurementSystem) {
        this.measurementSystem = measurementSystem;
    }

    public String getUnit() {
        return measurementSystem.getUnit();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
