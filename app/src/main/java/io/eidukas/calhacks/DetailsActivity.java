package io.eidukas.calhacks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by daniel on 11/12/16.
 */

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String foodQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        FoodSearcher foodSearcher = new FoodSearcher();
        foodQuery = getIntent().getStringExtra("args");
        foodSearcher.execute(SPOONACULAR_SEARCH_URL + "/search?query=" + foodQuery + "&mashape-key=" + APIKey.getAPIKey());
    }

    private static final String SPOONACULAR_SEARCH_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes";

    public class FoodSearcher extends AsyncTask<String, Integer, Map<String,Integer>> {
        private Gson gson = new Gson();

        @Override
        protected Map<String, Integer> doInBackground(String... strings) {
            System.out.println(strings);
            return io.eidukas.calhacks.FoodSearcher.getFrequencyMap(foodQuery);
        }

//        @Override
//        protected HashMap<String, Double> doInBackground(String... strings) {
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            String jsonString = null;
//            try {
//                URL url = new URL(strings[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line);
//                    buffer.append("\n");
//                }
//                jsonString = buffer.toString();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//            HashMap<String, Double> freqMap = new HashMap<>();
//            if (jsonString == null) return null;
//            FoodList foodList = gson.fromJson(jsonString, FoodList.class);
//            for (FoodList.FoodId food : foodList.getResults()) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(SPOONACULAR_SEARCH_URL);
//                sb.append("/");
//                sb.append(food.getId());
//                sb.append("?mashape-key=");
//                sb.append(APIKey.getAPIKey());
//                String jsonString2 = null;
//                try {
//                    URL url = new URL(strings[0]);
//                    urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setRequestMethod("GET");
//                    urlConnection.connect();
//
//                    InputStream inputStream = urlConnection.getInputStream();
//                    StringBuffer buffer = new StringBuffer();
//                    reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        buffer.append(line);
//                        buffer.append("\n");
//                    }
//                    jsonString2 = buffer.toString();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//                Recipe recipe = gson.fromJson(jsonString2, Recipe.class);
//                for (Ingredient ingredient : recipe.getExtendedIngredients()) {
//                    if (freqMap.get(ingredient.getName()) == null) {
//                        freqMap.put(ingredient.getName(), 1.0 / foodList.getResults().length);
//                    } else {
//                        double incrValue = freqMap.get(ingredient.getName()) + 1.0 / foodList.getResults().length;
//                        freqMap.put(ingredient.getName(), incrValue);
//                    }
//                }
//            }
//            return freqMap;
//        }

        @Override
        protected void onPostExecute(Map<String, Integer> map) {
            String[] result = new String[map.size()];
            int i = 0;
            for (String key : map.keySet()) {
                result[i] = "" + key + " - " + map.get(key);
                i++;
            }
            adapter = new MyAdapter(result);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(map);
        }
    }

}
