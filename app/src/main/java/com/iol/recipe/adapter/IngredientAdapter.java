package com.iol.recipe.adapter;

/**
 * Created by root on 1/4/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iol.recipe.R;

import java.util.ArrayList;

public class IngredientAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> ingredients;
    private LayoutInflater layoutInflater;
    ViewHolder holder;

    // Constructor
    public IngredientAdapter(Context c, ArrayList<String> ingredients) {
        mContext = c;
        this.ingredients = ingredients;
        layoutInflater = LayoutInflater.from(c);

    }

    public int getCount() {
        return ingredients.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.ingredientitem, parent, false);
            holder = new ViewHolder();
            holder.ingredientItemImg = (TextView) convertView
                    .findViewById(R.id.ingredientItemImg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ingredientItemImg.setText(ingredients.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView ingredientItemImg;
    }

}