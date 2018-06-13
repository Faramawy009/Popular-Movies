package com.faramawy009.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    TextView tempText = null;

    public class TMDbQueryTask extends AsyncTask<URL, Void, List<Movie>> {



        @Override
        protected List<Movie> doInBackground(URL... urls) {
            URL url = urls[0];
            List<Movie> result = new ArrayList<>();
            JSONObject obj = null;
            int pagesCount = 0;
            try {
                obj = new JSONObject(NetworkUtils.getUrlResponse(url));
                pagesCount = obj.getInt("total_pages");
            } catch (Exception e) {
                e.printStackTrace();
            }
            long start = System.currentTimeMillis();
            for(int page=1; page<=120; page++) {
                try {
                    //Because the API limits to 40 requests every 10 seconds:
                    if(page%40==0) {
                        Thread.sleep(10000-(System.currentTimeMillis()-start));
                        start = System.currentTimeMillis();
                    }
                    obj = new JSONObject(NetworkUtils.getUrlResponse(new URL(url.toString() + "&page=" + page)));
                    JSONArray arr = obj.getJSONArray("results");
                    for(int i=0; i<arr.length(); i++) {
                        JSONObject currResult = arr.getJSONObject(i);
                        String title = currResult.getString("title");
                        String posterLink = TheMovieDatabaseApiManager.imageBaseUrl + currResult.getString("backdrop_path");
                        String plot = currResult.getString("overview");
                        double rating = currResult.getDouble("vote_average");
                        String date = currResult.getString("release_date");

                        result.add(new Movie(title, posterLink, plot, rating, date));


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            String test = "";
            for(Movie m : result) {
                test += "Title: " + m.getTitle()
                        + "\nPoster: " + m.getPosterLink()
                        + "\nPlot: " + m.getPlot()
                        + "\nRating: " + m.getRating()
                        + "\nRelease Date: " + m.getDate()
                        + "\n\n\n";
            }
            if(!test.equals("")){
                tempText.setText(test);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempText = (TextView) findViewById(R.id.tv_test);
        String url =
                    TheMovieDatabaseApiManager.apiBaseUrl +
                    TheMovieDatabaseApiManager.popularMoviesPath +
                    TheMovieDatabaseApiManager.apiKeyQueryStringKey +
                    TheMovieDatabaseApiManager.apiKeyQueryStringVal;
        URL [] urls = new URL[1];
        try {
            urls[0] = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        Picasso.with(this).load("http").into(ImageView);
        new TMDbQueryTask().execute(urls);
    }
}
