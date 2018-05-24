package com.lalikum.getdrunkforless.model;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

public class Beverage extends SugarRecord {

    @NotNull
    private String name;

    @NotNull
    private float size;

    @NotNull
    private float alcoholByVolume;

    @NotNull
    private float price;

    @NotNull
    private float alcoholQuantity;

    @NotNull
    private float alcoholValue;

    public Beverage() {
    }

    public Beverage(String name, float size, float alcoholByVolume, float price) {
        this.name = name;
        this.size = size;
        this.alcoholByVolume = alcoholByVolume;
        this.price = price;
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
