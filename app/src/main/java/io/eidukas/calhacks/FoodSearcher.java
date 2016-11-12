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

    public static HashMap<String, Double> getFrequencyMap(String foodQuery) {
        HashMap<String, Double> freqMap = new HashMap<>();
        String jsonString = getJsonString(SPOONACULAR_SEARCH_URL + "/search?query=" + foodQuery + "&mashape-key=" + APIKey.getAPIKey());
        if (jsonString == null) return null;
        FoodList foodList = gson.fromJson(jsonString, FoodList.class);
        for (FoodList.FoodId food : foodList.getResults()) {
            StringBuilder sb = new StringBuilder();
            sb.append(SPOONACULAR_SEARCH_URL);
            sb.append("/");
            sb.append(food.getId());
            sb.append("?mashape-key=");
            sb.append(APIKey.getAPIKey());
            String jsonString2 = getJsonString(sb.toString());
            Recipe recipe = gson.fromJson(jsonString2, Recipe.class);
            for (Ingredient ingredient : recipe.getExtendedIngredients()) {
                if (freqMap.get(ingredient.getName()) == null) {
                    freqMap.put(ingredient.getName(), 1.0 / foodList.getResults().length);
                } else {
                    double incrValue = freqMap.get(ingredient.getName()) + 1.0 / foodList.getResults().length;
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
}
