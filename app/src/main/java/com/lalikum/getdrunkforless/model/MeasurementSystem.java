package com.lalikum.getdrunkforless.model;

public enum MeasurementSystem {
    METRIC("ml"), IMPERIAL("fl oz");

    private String unit;

    MeasurementSystem(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }


}
