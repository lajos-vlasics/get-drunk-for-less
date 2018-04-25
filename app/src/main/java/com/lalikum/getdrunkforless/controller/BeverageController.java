package com.lalikum.getdrunkforless.controller;

import com.lalikum.getdrunkforless.model.Beverage;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class BeverageController {

    private SettingsController optionsController = new SettingsController();
    private DecimalFormat decimalFormat = new DecimalFormat();

    public BeverageController() {
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    public Beverage create(String beverageName, float beverageSize, float alcoholByVolume, float price) {
        Beverage beverage = new Beverage(beverageName, beverageSize, alcoholByVolume, price);
        calculateAlcoholValue(beverage);
        return beverage;
    }

    public void calculateAlcoholValue(Beverage beverage) {
        float beverageSize = beverage.getSize();
        float alcoholByVolume = beverage.getAlcoholByVolume();
        float price = beverage.getPrice();

        float alcoholQuantity = beverageSize * alcoholByVolume / 100;
        float alcoholValue = price / alcoholQuantity;

        beverage.setAlcoholQuantity(alcoholQuantity);
        beverage.setAlcoholValue(alcoholValue);
    }

    public void save(Beverage beverage) {
        beverage.save();
    }

    public Beverage getById(long id) {
        return Beverage.findById(Beverage.class, id);
    }

    public List<Beverage> getAll() {
        return Beverage.listAll(Beverage.class);
    }

    public String getSizeWithUnit(Beverage beverage) {
        return decimalFormat.format(beverage.getSize()) + " " + optionsController.getUnit();
    }

    public String getAlcoholByVolumeWithUnit(Beverage beverage) {
        return decimalFormat.format(beverage.getAlcoholByVolume()) + " %";
    }

    public String getPriceWithUnit(Beverage beverage) {
        // TODO remove commas from 1,000 things
        return decimalFormat.format(beverage.getPrice()) + " " + optionsController.getCurrency();
    }

    public String getAlcoholQuantityWithUnit(Beverage beverage) {
        return String.format("%s %s", decimalFormat.format(beverage.getAlcoholQuantity()), optionsController.getUnit());
    }

    public String getAlcoholValueWithUnit(Beverage beverage) {
        return String.format("%s %s/%s", decimalFormat.format(beverage.getAlcoholValue()), optionsController.getCurrency(), optionsController.getUnit());

    }

    public List<Beverage> getAllSortedByAlcoholValue() {
        return Beverage.listAll(Beverage.class, "alcohol_value");
    }
}
