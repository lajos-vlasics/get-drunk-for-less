package com.lalikum.getdrunkforless.controller;

import com.lalikum.getdrunkforless.model.Beverage;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeverageController {

    private OptionsController optionsController = new OptionsController();
    private DecimalFormat decimalFormat = new DecimalFormat();

    public BeverageController() {
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    public Beverage create(String beverageName, float beverageSize, float alcoholByVolume, float price, int bottles) {
        Beverage beverage = new Beverage(beverageName, beverageSize, alcoholByVolume, price, bottles);
        calculate(beverage);
        return beverage;
    }

    public void calculate(Beverage beverage) {
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

    public Beverage getById(long id) {
        return Beverage.findById(Beverage.class, id);
    }

    public List<Beverage> getAll() {
        Iterator<Beverage> beverageIterator = Beverage.findAll(Beverage.class);

        List<Beverage> beverageList = new ArrayList<>();

        while (beverageIterator.hasNext()) {
            beverageList.add(beverageIterator.next());
        }

        return beverageList;
    }

    public String getSizeWithSuffix(Beverage beverage) {
        return decimalFormat.format(beverage.getSize()) + " " + optionsController.getUnit();
    }

    public String getAlcoholByVolumeWithSuffix(Beverage beverage) {
        return decimalFormat.format(beverage.getAlcoholByVolume()) + " %";
    }

    public String getPriceWithSuffix(Beverage beverage) {
        // TODO remove commas from 1,000 things
        return decimalFormat.format(beverage.getPrice()) + " " + optionsController.getCurrency();
    }

    public String getBottlesWithSuffix(Beverage beverage) {
        return beverage.getBottles() + " bottle";
    }

    public String getAlcoholQuantityWithSuffix(Beverage beverage) {
        return String.format("%s %s", decimalFormat.format(beverage.getAlcoholQuantity()), optionsController.getUnit());
    }

    public String getAlcoholValueWithSuffix(Beverage beverage) {
        return String.format("%s %s/%s", decimalFormat.format(beverage.getAlcoholValue()), optionsController.getCurrency(), optionsController.getUnit());

    }
}
