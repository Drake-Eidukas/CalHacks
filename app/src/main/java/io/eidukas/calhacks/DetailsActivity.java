package io.eidukas.calhacks;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.eidukas.calhacks.DataModels.Recipe;

/**
 * Created by daniel on 11/12/16.
 */

public class DetailsActivity extends AppCompatActivity {

    private ListView listView;
    private MyAdapter adapter;
    private ListView.LayoutManager layoutManager;
    private String foodQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        listView = (ListView) findViewById(R.id.recycler_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = adapter.getItem(position);

                Intent intent = new Intent(parent.getContext(), RecipeActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, recipe);

                // Attempt to start an activity that can handle the Intent
                startActivity(intent);
            }

        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        SearchFoodTask task = new SearchFoodTask();
        foodQuery = getIntent().getStringExtra("args");
        task.execute(SPOONACULAR_SEARCH_URL + "/search?query=" + foodQuery + "&mashape-key=" + APIKey.getAPIKey());
    }

    private static final String SPOONACULAR_SEARCH_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes";

    public class SearchFoodTask extends AsyncTask<String, Integer, Recipe[]> {

        @Override
        protected Recipe[] doInBackground(String... strings) {
            return FoodSearcher.getDifferentRecipes(strings[0]);
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            adapter = new MyAdapter(recipes);
            listView.setAdapter(adapter);
            super.onPostExecute(recipes);
        }
    }

}
