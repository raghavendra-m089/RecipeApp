package com.iol.recipe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    ActionBar actionBar;
    private int SPLASH_TIME = 4 * 1000;// 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        actionBar = getSupportActionBar();
        actionBar.hide();

        try {
            new Handler().postDelayed(new Runnable() {

                public void run() {
                    Intent intent = new Intent(SplashScreen.this,
                                RecipeList.class);
                        startActivity(intent);
                    SplashScreen.this.finish();
                }

            }, SPLASH_TIME);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
