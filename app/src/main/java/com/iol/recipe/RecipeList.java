package com.iol.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iol.recipe.adapter.LoadMoreListView;
import com.iol.recipe.adapter.RecipeAdapter;
import com.iol.recipe.app.AppController;
import com.iol.recipe.utils.CheckInternetService;
import com.iol.recipe.utils.FeedAddress;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {
    ListView recipeList;
    CheckInternetService cis;
    RecipeAdapter recipeAdapter;
    ArrayList<FeedAddress> arrayListOfRecipe = new ArrayList<FeedAddress>();
    int recipePage = 1;
    String URL;
    private String tag_json_obj = "jobj_req";
    boolean mError = false;
    String toSearch = "";
    EditText SearchET;
    ProgressBar progressBarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        recipeList = (ListView) this.findViewById(R.id.recipeList);
        progressBarList = (ProgressBar) this.findViewById(R.id.progressBarList);
        SearchET = (EditText) this.findViewById(R.id.SearchET);
        cis = new CheckInternetService(getApplicationContext());
        if (cis.isConnectingToInternet()) {
            loadRecipeList();
        } else {
            Toast.makeText(getApplicationContext(), "Check with the Internet Connection!", Toast.LENGTH_LONG).show();
        }

        recipeAdapter = new RecipeAdapter(arrayListOfRecipe, RecipeList.this);
        recipeList.setAdapter(recipeAdapter);

        // load more used to load the paginated values to the same list
        ((LoadMoreListView) recipeList)
                .setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        // TODO Auto-generated method stub
                        recipePage += 1;
                        loadRecipeList();
                    }
                });

        SearchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void performSearch() {
        Log.d("Search method", "I Came Here");
        if (SearchET.getText().toString().trim().length() != 0) {
            toSearch = SearchET.getText().toString().trim();
        }
        if (cis.isConnectingToInternet()) {
            progressBarList.setVisibility(View.VISIBLE);
            recipePage = 1;
            loadRecipeList();
            try {
                arrayListOfRecipe.clear();
                recipeAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Check with the Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    private void loadRecipeList() {
        URL = "http://www.recipepuppy.com/api/?i=" + toSearch + "&p=" + recipePage;

        JsonObjectRequest listedNewsReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, createMyReqSuccessListener(),
                createMyReqErrorListener());

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(listedNewsReq,
                tag_json_obj);

    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray recipeArray = response.getJSONArray("results");
                    for (int i = 0; i < recipeArray.length(); i++) {
                        FeedAddress feedItem = new FeedAddress();
                        JSONObject itemObj = recipeArray.getJSONObject(i);
                        feedItem.setTitle(itemObj.getString("title"));
                        feedItem.setHref(itemObj.getString("href"));
                        feedItem.setIngredients(itemObj.getString("ingredients"));
                        if (itemObj.getString("thumbnail").trim().length() == 0 || itemObj.getString("thumbnail").equals("") || itemObj.getString("thumbnail") == null) {
                            feedItem.setThumbnail("http://img.recipepuppy.com/ihavenoimg");
                        } else {
                            feedItem.setThumbnail(itemObj.getString("thumbnail"));
                        }
                        arrayListOfRecipe.add(feedItem);
                    }
                    progressBarList.setVisibility(View.GONE);
                    ((BaseAdapter) recipeAdapter).notifyDataSetChanged();
                    // Call onLoadMoreComplete when the LoadMore task, has
                    // finished
                    ((LoadMoreListView) recipeList)
                            .onLoadMoreComplete();
                    updateList();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }

            public void updateList() {
                // TODO Auto-generated method stub
                recipeList
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent,
                                                    View view, int position, long id) {
                                // TODO Auto-generated method stub
                                Object o = recipeList
                                        .getItemAtPosition(position);
                                FeedAddress address = (FeedAddress) o;
                                Intent i = new Intent(RecipeList.this, DetailedView.class);
                                i.putExtra("RecipeTitle", address.getTitle());
                                i.putExtra("RecipeHref", address.getHref());
                                i.putExtra("RecipeImg", address.getThumbnail());
                                i.putExtra("RecipeIngredients", address.getIngredients());
                                startActivity(i);
                            }
                        });
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mError = true;
                error.printStackTrace();
            }
        };

    }
}
