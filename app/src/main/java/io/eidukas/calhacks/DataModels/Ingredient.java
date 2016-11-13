package io.eidukas.calhacks.DataModels;

/**
 * Created by solmoms on 11/12/2016.
 */

public class Ingredient {
    private String name, unit, unitShort, unitLong;
    private double amount;

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public double getAmount() {
        return amount;
    }
}
