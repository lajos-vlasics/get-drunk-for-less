package com.lalikum.getdrunkforless.controller;

import android.widget.TextView;

import com.lalikum.getdrunkforless.R;
import com.lalikum.getdrunkforless.model.Beverage;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BeverageController {

    public Beverage create(String beverageName, float beverageSize, float alcoholByVolume, float price, int bottles) {
        Beverage beverage = new Beverage(beverageName, beverageSize, alcoholByVolume, price, bottles);

        calculate(beverage);

        return beverage;
    }

    private void calculate(Beverage beverage) {
        float beverageSize = beverage.getSize();
        float alcoholByVolume = beverage.getAlcoholByVolume();
        float price = beverage.getPrice();
        int bottles = beverage.getBottles();

        float alcoholQuantity = beverageSize * bottles * alcoholByVolume / 100;
        float alcoholValue = price / alcoholQuantity;

        beverage.setAlcoholQuantity(alcoholQuantity);
        beverage.setAlcoholValue(alcoholValue);
    }

    public void save(Beverage beverage) {
        beverage.save();
    }

    public Beverage getById(int id) {
        return Beverage.findById(Beverage.class, id);
    }
}
