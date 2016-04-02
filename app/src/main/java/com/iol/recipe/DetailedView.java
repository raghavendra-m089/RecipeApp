package com.iol.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.iol.recipe.adapter.IngredientAdapter;
import com.iol.recipe.utils.ImageUnicodeConverter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailedView extends AppCompatActivity {
    String href, title, ingredients, img;
    TextView titleofRecipe, hrefofRecipe;
    GridView ingredientsofRecipe;
    ImageView imgofRecipe;
    IngredientAdapter ingredientAdapter;
    ArrayList<String> ingredient = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_view);
        href = getIntent().getExtras().getString("RecipeHref");
        title = getIntent().getExtras().getString("RecipeTitle");
        ingredients = getIntent().getExtras().getString("RecipeIngredients");
        img = getIntent().getExtras().getString("RecipeImg");
        titleofRecipe = (TextView) this.findViewById(R.id.itemResNameDet);
        ingredientsofRecipe = (GridView) this.findViewById(R.id.itemResIngDet);
        hrefofRecipe = (TextView) this.findViewById(R.id.itemResHrefDet);
        imgofRecipe = (ImageView) this.findViewById(R.id.itemResImgDet);

        titleofRecipe.setText(title);
        //ArrayList<String> ingredient = ingredients.toString().split(",");
        for (int i = 0; i < ingredients.toString().split(",").length; i++) {
            ingredient.add(ingredients.toString().split(",")[i]);
        }


        ingredientAdapter = new IngredientAdapter(DetailedView.this, ingredient);
        ingredientsofRecipe.setAdapter(ingredientAdapter);

        hrefofRecipe.setText("Read more...");
        Picasso.with(this)
                .load(ImageUnicodeConverter.getImageUnicode(img))
                .placeholder(R.drawable.defresimg)
                .error(R.drawable.defresimg).fit().centerCrop().into(imgofRecipe);

        hrefofRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailedView.this, ShowBrowser.class);
                i.putExtra("URLToCall", href);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
