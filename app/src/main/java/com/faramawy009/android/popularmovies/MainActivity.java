package com.faramawy009.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener{
    private List<Movie> movieResult = null;
    private RecyclerView recyclerView = null;
    private MovieAdapter movieAdapter = null;

    @Override
    public void onGridItemClick(int clickedItemIndex) {
        Movie movie = movieResult.get(clickedItemIndex);
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    public class TMDbQueryTask extends AsyncTask<URL, Void, Void> {

        @Override
        protected Void doInBackground(URL... urls) {
            movieResult.clear();
            URL url = urls[0];
            JSONObject obj = null;

            //Represents the number of pages that the api have in case we want to use all of the movies in these pages
            int pagesCount = 0;
            try {
                obj = new JSONObject(NetworkUtils.getUrlResponse(url));
                pagesCount = obj.getInt("total_pages");
            } catch (Exception e) {
                e.printStackTrace();
            }
            getNumPagesFromUrl(10, url.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            movieAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieResult = new ArrayList<>();
        setupRecyclerView();
        asyncQuery(urlString(TheMovieDatabaseApiManager.popularMoviesPath));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_rate:
                asyncQuery(urlString(TheMovieDatabaseApiManager.topRatedMoviesPath));
                break;
            case R.id.sort_popularity:
                asyncQuery(urlString(TheMovieDatabaseApiManager.popularMoviesPath));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public String urlString(String sortingPath) {
       return   TheMovieDatabaseApiManager.apiBaseUrl +
                sortingPath +
                TheMovieDatabaseApiManager.apiKeyQueryStringKey +
                TheMovieDatabaseApiManager.apiKeyQueryStringVal;
    }

    public void asyncQuery(String url){
        URL [] urls = new URL[1];
        try {
            urls[0] = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new TMDbQueryTask().execute(urls);
    }

    public void setupRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this,calculateBestSpanCount(500)));
        movieAdapter = new MovieAdapter(movieResult, this);
        recyclerView.setAdapter(movieAdapter);
    }

    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    public void getNumPagesFromUrl(int num, String url){
        JSONObject obj = null;
        long start = System.currentTimeMillis();
        for(int page=1; page<=num; page++) {
            try {
                //Because the API limits to 40 requests every 10 seconds:
                if(page%40==0) {
                    Thread.sleep(10000-(System.currentTimeMillis()-start));
                    start = System.currentTimeMillis();
                }
                obj = new JSONObject(NetworkUtils.getUrlResponse(new URL(url + "&page=" + page)));
                JSONArray arr = obj.getJSONArray("results");
                for(int i=0; i<arr.length(); i++) {
                    JSONObject currResult = arr.getJSONObject(i);
                    String title = currResult.getString("title");
                    String posterLink = TheMovieDatabaseApiManager.imageBaseUrl + currResult.getString("backdrop_path");
                    String plot = currResult.getString("overview");
                    double rating = currResult.getDouble("vote_average");
                    String date = currResult.getString("release_date");
                    movieResult.add(new Movie(title, posterLink, plot, rating, date));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
