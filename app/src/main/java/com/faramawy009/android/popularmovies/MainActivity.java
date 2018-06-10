package com.faramawy009.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tempText = null;

    public class TMDbQueryTask extends AsyncTask<URL, Void, String> {



        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result = "";
            try {
                result = NetworkUtils.getUrlResponse(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")){
                tempText.setText(s);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempText = (TextView) findViewById(R.id.tempText);
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
        new TMDbQueryTask().execute(urls);
    }
}
