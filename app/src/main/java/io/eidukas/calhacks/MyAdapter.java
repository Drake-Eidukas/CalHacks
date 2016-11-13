package io.eidukas.calhacks;


import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

import io.eidukas.calhacks.DataModels.Recipe;

/**
 * Created by daniel on 11/12/16.
 */

public class MyAdapter extends ArrayAdapter<Recipe> {

    public MyAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.list_item, null);
        }

        Recipe recipe = getItem(position);

        TextView title = (TextView) v.findViewById(R.id.textView_title);
        TextView likes = (TextView) v.findViewById(R.id.aggregateLikes);

        if (title != null) {
            title.setText(recipe.getTitle());
        }
        if (likes != null) {
            likes.setText(recipe.getAggregateLikes());
        }

        return v;
    }
}
