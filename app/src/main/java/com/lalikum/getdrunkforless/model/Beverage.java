package com.lalikum.getdrunkforless.model;

import com.lalikum.getdrunkforless.controller.OptionsController;
import com.orm.SugarRecord;

public class Beverage extends SugarRecord {

    private String name;
    private float size;
    private float alcoholByVolume;
    private float price;
    private int bottles;
    private float alcoholQuantity;
    private float alcoholValue;

    public Beverage() {
    }

    public Beverage(String name, float size, float alcoholByVolume, float price, int bottles) {
        this.name = name;
        this.size = size;
        this.alcoholByVolume = alcoholByVolume;
        this.price = price;
        this.bottles = bottles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getAlcoholByVolume() {
        return alcoholByVolume;
    }

    public void setAlcoholByVolume(float alcoholByVolume) {
        this.alcoholByVolume = alcoholByVolume;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getBottles() {
        return bottles;
    }

    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public float getAlcoholQuantity() {
        return alcoholQuantity;
    }

    public void setAlcoholQuantity(float alcoholQuantity) {
        this.alcoholQuantity = alcoholQuantity;
    }

    public float getAlcoholValue() {
        return alcoholValue;
    }

    public void setAlcoholValue(float alcoholValue) {
        this.alcoholValue = alcoholValue;
    }


}
