package com.weezee.newsappstage2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weezee.newsappstage2.adapters.RecyclerAdapter;
import com.weezee.newsappstage2.pojo.StoryItem;
import com.weezee.newsappstage2.utilities.StoriesLoader;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<StoryItem>> {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private static final String baseURL = "https://content.guardianapis.com/search?show-fields=all&page-size=20&q=Football&api-key=~~~~~~";//replace '~' characters with your own api key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Football News");

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);// check if there is inrternet connection or being connected already
        NetworkInfo netInfo = cm.getActiveNetworkInfo();


        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            getSupportLoaderManager().destroyLoader(0);
            getSupportLoaderManager().restartLoader(0, null, MainActivity.this);

        } else {
            ProgressBar progressBar = findViewById(R.id.progressBar1);
            progressBar.setVisibility(View.GONE);
            TextView textView = findViewById(R.id.error_loading);
            textView.setText(getString(R.string.no_internet_connection_error));
            textView.setVisibility(View.VISIBLE);
        }



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }

    @NonNull
    @Override
    public Loader<ArrayList<StoryItem>> onCreateLoader(int i, @Nullable Bundle bundle) {
        recyclerAdapter= new RecyclerAdapter(new ArrayList<StoryItem>(),this );
        recyclerAdapter.notifyDataSetChanged();
        ProgressBar progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String newsOrder = sharedPrefs.getString(
                getString(R.string.preference_key),
                getString(R.string.settings_default));

        StringBuilder builder = new StringBuilder(baseURL);
        builder.append("&order-by=").append(newsOrder);
        return new StoriesLoader(MainActivity.this, builder.toString());

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<StoryItem>> loader, ArrayList<StoryItem> storyItems) {
        ProgressBar progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        if (storyItems.isEmpty()) {
            TextView textView = findViewById(R.id.error_loading);
            textView.setVisibility(View.VISIBLE);
        } else {
            TextView textView = findViewById(R.id.error_loading);
            textView.setVisibility(View.GONE);
            recyclerAdapter = new RecyclerAdapter(storyItems, this);
            recyclerView.setAdapter(recyclerAdapter);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<StoryItem>> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        recyclerView.setAdapter(new RecyclerAdapter(new ArrayList<StoryItem>(),this ));
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            getSupportLoaderManager().destroyLoader(0);
            getSupportLoaderManager().restartLoader(0, null, MainActivity.this);

        } else {
            ProgressBar progressBar = findViewById(R.id.progressBar1);
            progressBar.setVisibility(View.GONE);
            TextView textView = findViewById(R.id.error_loading);
            textView.setText(getString(R.string.no_internet_connection_error));
            textView.setVisibility(View.VISIBLE);
        }

        super.onResume();

    }
}
