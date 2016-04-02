package com.iol.recipe.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.iol.recipe.R;
import com.iol.recipe.utils.FeedAddress;
import com.iol.recipe.utils.ImageUnicodeConverter;
import com.squareup.picasso.Picasso;

public class RecipeAdapter extends BaseAdapter {
    private ArrayList<FeedAddress> listData;
    private LayoutInflater layoutInflater;
    private Context mContext;
    ViewHolder holder;

    public RecipeAdapter(ArrayList<FeedAddress> listData, Context context) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        super.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // ViewHolder holder;
        final FeedAddress newsItem = (FeedAddress) listData.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.recipe_item, parent, false);
            holder = new ViewHolder();
            holder.recipeTtl = (TextView) convertView
                    .findViewById(R.id.itemResName);
            holder.recipeIngredients = (TextView) convertView
                    .findViewById(R.id.itemResIng);
            holder.recipeImg = (ImageView) convertView
                    .findViewById(R.id.itemResImg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.recipeTtl.setText(Html.fromHtml(newsItem.getTitle().trim()));
        holder.recipeIngredients.setText(newsItem.getIngredients());
        if (holder.recipeImg != null) {
            Picasso.with(mContext)
                    .load(ImageUnicodeConverter.getImageUnicode(newsItem
                            .getThumbnail()))
                    .placeholder(R.drawable.defresimg)
                    .error(R.drawable.defresimg).fit().centerCrop().into(holder.recipeImg);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView recipeTtl, recipeIngredients;
        ImageView recipeImg;
    }

}
