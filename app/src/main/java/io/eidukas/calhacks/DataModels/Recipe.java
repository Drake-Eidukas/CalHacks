package io.eidukas.calhacks.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by solmoms on 11/12/2016.
 *
 * API Representation of the class Recipe
 *
 *
 */

public class Recipe implements Parcelable {
    private boolean vegetarian, vegan, glutenFree, dairyFree, ketogenic;
    private String sourceUrl, title, image;
    private int servings, aggregateLikes;
    private Ingredient[] extendedIngredients;

    protected Recipe(Parcel in) {
        vegetarian = in.readByte() != 0;
        vegan = in.readByte() != 0;
        glutenFree = in.readByte() != 0;
        dairyFree = in.readByte() != 0;
        ketogenic = in.readByte() != 0;
        sourceUrl = in.readString();
        title = in.readString();
        image = in.readString();
        servings = in.readInt();
        aggregateLikes = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (vegetarian ? 1 : 0));
        parcel.writeByte((byte) (vegan ? 1 : 0));
        parcel.writeByte((byte) (glutenFree ? 1 : 0));
        parcel.writeByte((byte) (dairyFree ? 1 : 0));
        parcel.writeByte((byte) (ketogenic ? 1 : 0));
        parcel.writeString(sourceUrl);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeInt(servings);
        parcel.writeInt(aggregateLikes);
    }
}