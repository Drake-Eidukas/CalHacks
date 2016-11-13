package io.eidukas.calhacks;


import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

import io.eidukas.calhacks.DataModels.Recipe;

/**
 * Created by daniel on 11/12/16.
 */

public class MyAdapter extends android.support.v7.widget.RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Recipe[] mDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredient;
        public TextView frequency;

        public ViewHolder(View v) {
            super(v);
            ingredient = (TextView) v.findViewById(R.id.ingredient);
            frequency = (TextView) v.findViewById(R.id.frequency);
        }
    }

    public MyAdapter() {
    }

    public MyAdapter(Recipe[] myDataSet) {
        mDataSet = myDataSet;

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ingredient.setText(mDataSet[position].getTitle());
        holder.frequency.setText("Number of aggregate likes : " + mDataSet[position].getAggregateLikes());
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        }
        return mDataSet.length;
    }
}
