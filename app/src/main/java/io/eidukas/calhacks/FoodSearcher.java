package io.eidukas.calhacks;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collections;

import io.eidukas.calhacks.DataModels.Ingredient;
import io.eidukas.calhacks.DataModels.Recipe;


public class FoodSearcher {
    private static Gson gson = new Gson();
    private static final String SPOONACULAR_SEARCH_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes";

    public static Recipe[] getDifferentRecipes(String foodQuery, String ApiKey) {
        TreeMap<String, Integer> freqMap = new TreeMap<>();
        String jsonString = getJsonString(SPOONACULAR_SEARCH_URL + "/search?query=" + foodQuery + "&mashape-key=" + ApiKey);
        if (jsonString == null) return null;
        FoodList foodList = gson.fromJson(jsonString, FoodList.class);
        Recipe[] recipes = new Recipe[foodList.getResults().length];
        int i = 0;
        for (FoodList.FoodId food : foodList.getResults()) {
            String jsonString2 = getJsonString(SPOONACULAR_SEARCH_URL + "/" + food.getId() + "/information?mashape-key=" + ApiKey);
            Recipe recipe = gson.fromJson(jsonString2, Recipe.class);
            recipes[i] = recipe;
            i++;
        }
        return recipes;
    }


    @Nullable
    private static String getJsonString(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonString = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }
            jsonString = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

}
