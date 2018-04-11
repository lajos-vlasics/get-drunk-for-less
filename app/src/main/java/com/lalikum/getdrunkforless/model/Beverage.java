package com.lalikum.getdrunkforless.model;

import com.orm.SugarRecord;

public class Beverage extends SugarRecord {

    private String name;
    private float size;
    private float alcoholVolume;
    private float price;
    private int quantity;
    private float drunkValue;

    public Beverage() {
    }

    public Beverage(String name, float size, float alcoholVolume, float price, int quantity, float drunkValue) {
        this.name = name;
        this.size = size;
        this.alcoholVolume = alcoholVolume;
        this.price = price;
        this.quantity = quantity;
        this.drunkValue = drunkValue;
    }
}
