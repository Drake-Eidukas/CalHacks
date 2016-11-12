package io.eidukas.calhacks;

/**
 * Created by solmoms on 11/12/2016.
 *
 * API Representation of the class Recipe
 *
 *
 */

public class Recipe {
    private boolean vegetarian, vegan, glutenFree, dairyFree, ketogenic;
    private String sourceUrl, title;
    private int servings, aggregateLikes;
    private Ingredient[] extendedIngredients;

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public boolean isKetogenic() {
        return ketogenic;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getServings() {
        return servings;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public Ingredient[] getExtendedIngredients() {
        return extendedIngredients;
    }
}