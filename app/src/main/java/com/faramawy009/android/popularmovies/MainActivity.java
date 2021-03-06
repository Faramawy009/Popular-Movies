package com.faramawy009.android.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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

import com.faramawy009.android.popularmovies.Database.AppDatabase;
import com.faramawy009.android.popularmovies.Database.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener{
    private ArrayList<Movie> movieResult = null;
    private RecyclerView mRecyclerView = null;
    private MovieAdapter mMovieAdapter = null;
    private AppDatabase db = null;

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
                pagesCount = obj.optInt("total_pages");
            } catch (Exception e) {
                e.printStackTrace();
            }
            getNumPagesFromUrl(1, url.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            mMovieAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieResult = new ArrayList<>();
        setupRecyclerView();
        db = AppDatabase.getInstance(this);

        if(savedInstanceState!=null){
            queryFavorites();
            movieResult = savedInstanceState.getParcelableArrayList("savedMovies");
            setupRecyclerView();
        } else {
            asyncQuery(urlString(TheMovieDatabaseApiManager.sPopularMoviesPath));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList("savedMovies", movieResult);
        super.onSaveInstanceState(savedInstanceState);
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
                asyncQuery(urlString(TheMovieDatabaseApiManager.sTopRatedMoviesPath));
                break;
            case R.id.sort_popularity:
                asyncQuery(urlString(TheMovieDatabaseApiManager.sPopularMoviesPath));
                break;
            case R.id.show_favorites:
                queryFavorites();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String urlString(String sortingPath) {
        // TODO: Inset Api key in Build Config through app build.gradle with a name "TMDB_API_KEY"
       return   TheMovieDatabaseApiManager.sApiBaseUrl +
                sortingPath +
                TheMovieDatabaseApiManager.sApiKeyQueryStringKey +
                BuildConfig.TMDB_API_KEY;
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
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,calculateBestSpanCount(500)));
        mMovieAdapter = new MovieAdapter(movieResult, this);
        mRecyclerView.setAdapter(mMovieAdapter);
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
                    String title = currResult.optString("title");
                    String posterLink = TheMovieDatabaseApiManager.sImageBaseUrl + currResult.optString("poster_path");
                    String plot = currResult.optString("overview");
                    double rating = currResult.optDouble("vote_average");
                    String date = currResult.optString("release_date");
                    int id = currResult.optInt("id");

                    JSONObject reviews = new JSONObject(NetworkUtils.getUrlResponse(new URL(
                            TheMovieDatabaseApiManager.sApiBaseUrl + "/movie/" + id
                            + "/reviews" + TheMovieDatabaseApiManager.sApiKeyQueryStringKey
                            + BuildConfig.TMDB_API_KEY
                    )));

                    JSONArray reviewResults = reviews.getJSONArray("results");
                    List<String> reviewContent = new ArrayList<>();
                    for(int j=0; j<reviewResults.length(); j++){
                        reviewContent.add(reviewResults.getJSONObject(j).optString("content"));
                    }

                    JSONObject trailers = new JSONObject(NetworkUtils.getUrlResponse(new URL(
                            TheMovieDatabaseApiManager.sApiBaseUrl + "/movie/" + id
                                    + "/videos" + TheMovieDatabaseApiManager.sApiKeyQueryStringKey
                                    + BuildConfig.TMDB_API_KEY
                    )));

                    JSONArray trailerResults = trailers.getJSONArray("results");
                    List<String> trailerKeys = new ArrayList<>();
                    for(int j=0; j<trailerResults.length(); j++){
                        trailerKeys.add(trailerResults.getJSONObject(j).optString("key"));
                    }

                    movieResult.add(new Movie(id, title, posterLink, plot, rating, date, trailerKeys, reviewContent ));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void queryFavorites(){
        LiveData<List<Movie>> favorites = db.movieDao().loadFavorites();
        favorites.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieResult.clear();
                movieResult.addAll(movies);
                mMovieAdapter.notifyDataSetChanged();
            }
        });
    }
}
