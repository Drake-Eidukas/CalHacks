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
import java.util.HashMap;

/**
 * Created by solmoms on 11/12/2016.
 */

public class FoodSearcher {
    private static Gson gson = new Gson();
    private static final String SPOONACULAR_SEARCH_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes";

    public static HashMap<String, Integer> getFrequencyMap(String foodQuery) {
        HashMap<String, Integer> freqMap = new HashMap<>();
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

//    public static void main(String[] args) {
//        System.out.println(getFrequencyMap("garlic_bread"));
//    }
}
