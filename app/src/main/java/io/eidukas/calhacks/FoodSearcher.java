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

/**
 * Created by solmoms on 11/12/2016.
 */

public class FoodSearcher {
    private static Gson gson = new Gson();
    private static final String SPOONACULAR_SEARCH_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes";

    public static Recipe[] getDifferentRecipes(String foodQuery) {
        TreeMap<String, Integer> freqMap = new TreeMap<>();
        String jsonString = getJsonString(SPOONACULAR_SEARCH_URL + "/search?query=" + foodQuery + "&mashape-key=" + APIKey.API_KEY);
        if (jsonString == null) return null;
        FoodList foodList = gson.fromJson(jsonString, FoodList.class);
        Recipe[] recipes = new Recipe[foodList.getResults().length];
        int i = 0;
        for (FoodList.FoodId food : foodList.getResults()) {
            String jsonString2 = getJsonString(SPOONACULAR_SEARCH_URL + "/" + food.getId() + "/information?mashape-key=" + APIKey.API_KEY);
            Recipe recipe = gson.fromJson(jsonString2, Recipe.class);
            recipes[i] = recipe;
            i++;
        }
        return recipes;
    }

    private static TreeMap<String, Integer> getFrequencyMap(String foodQuery) {
        TreeMap<String, Integer> freqMap = new TreeMap<>();
        String jsonString = getJsonString(SPOONACULAR_SEARCH_URL + "/search?query=" + foodQuery + "&mashape-key=" + APIKey.API_KEY);
        if (jsonString == null) return null;
        FoodList foodList = gson.fromJson(jsonString, FoodList.class);
        for (FoodList.FoodId food : foodList.getResults()) {
            String jsonString2 = getJsonString(SPOONACULAR_SEARCH_URL + "/" + food.getId() + "/information?mashape-key=" + APIKey.API_KEY);
            Recipe recipe = gson.fromJson(jsonString2, Recipe.class);
            for (Ingredient ingredient : recipe.getExtendedIngredients()) {
                if (freqMap.get(ingredient.getName()) == null) {
                    freqMap.put(ingredient.getName(), 1);
                } else {
                    int incrValue = freqMap.get(ingredient.getName()) + 1;
                    freqMap.put(ingredient.getName(), incrValue);
                }
            }
        }
        return freqMap;
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

    public static ArrayList<PopularIngredient> getPopularIngredients(String queryString){
        TreeMap<String,Integer> freqMap = getFrequencyMap(queryString);
        ArrayList<PopularIngredient> p = new ArrayList<>(0);
            for (Map.Entry<String, Integer> ingredient1 : freqMap.entrySet()){
                String ingr = ingredient1.getKey();
                int frq = ingredient1.getValue();
                p.add(new FoodSearcher.PopularIngredient(ingr, frq));
            }
        Collections.sort(p,Collections.<PopularIngredient>reverseOrder());
        return p;
    }

    public static class PopularIngredient implements Comparable<PopularIngredient>{
        private String ingredient;
        private int freq;
        public PopularIngredient(String ingredient, int freq){
            this.ingredient = ingredient;
            this.freq = freq;
        }
        public int compareTo(PopularIngredient p2) {
            Integer f2 = new Integer(p2.freq);
            Integer f1 = new Integer(this.freq);
            return f1.compareTo(f2);
        }

        public String getIngredient() {
            return ingredient;
        }

        public int getFreq() {
            return freq;
        }
    }

//
//    public static void main(String[] args) {
//        System.out.println(getPopularIngredients(getFrequencyMap("garlic_bread")));
//    }
}
