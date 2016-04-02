package com.iol.recipe;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class ShowBrowser extends AppCompatActivity {
    WebView ourprodinfo;
    String urlToCall;
    ProgressBar mProgress;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_browser);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.progress_bar_background);
        mProgress = (ProgressBar) findViewById(R.id.progressbarwebview);
        mProgress.setProgress(25); // Main Progress
        mProgress.setSecondaryProgress(50); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);

        ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress",
                0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        animation.setDuration(10000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        actionBar = getSupportActionBar();
        actionBar.setTitle("Foodie Pvt. Ltd.");
        urlToCall = getIntent().getExtras().getString("URLToCall");

        ourprodinfo = (WebView) this.findViewById(R.id.ourprodwv);
        ourprodinfo.getSettings().setJavaScriptEnabled(true);
        ourprodinfo.getSettings().setBuiltInZoomControls(true);
        // loads the WebView completely zoomed out
        ourprodinfo.getSettings().setLoadWithOverviewMode(true);

        ourprodinfo.getSettings().setUseWideViewPort(true);
        // ourprodinfo.addJavascriptInterface(new JsInterface(),
        // "CitrusResponse");
        ourprodinfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            // when finish loading page
            public void onPageFinished(WebView view, String url) {
                mProgress.setVisibility(View.GONE);
            }
        });

        ourprodinfo.loadUrl(urlToCall);


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
