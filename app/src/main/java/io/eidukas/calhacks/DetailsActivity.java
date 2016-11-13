package io.eidukas.calhacks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Map;

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
        foodSearcher.execute(SPOONACULAR_SEARCH_URL + "/search?query=" + foodQuery + "&mashape-key=" + APIKey.getRecipeKey(this));
    }

    private static final String SPOONACULAR_SEARCH_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes";

    public class FoodSearcher extends AsyncTask<String, Integer, Map<String,Integer>> {

        @Override
        protected Map<String, Integer> doInBackground(String... strings) {
            return io.eidukas.calhacks.FoodSearcher.getFrequencyMap(foodQuery, APIKey.getRecipeKey(getApplicationContext()));
        }

        @Override
        protected void onPostExecute(Map<String, Integer> map) {
            String[][] result = new String[map.size()][2];
            int i = 0;
            for (String key : map.keySet()) {
                result[i][0] = key;
                result[i][1] = Integer.toString(map.get(key));
                i++;
            }
            adapter = new MyAdapter(result);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(map);
        }
    }

}
