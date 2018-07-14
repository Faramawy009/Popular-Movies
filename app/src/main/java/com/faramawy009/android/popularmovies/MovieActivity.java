package com.faramawy009.android.popularmovies;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ImageView poster_iv = findViewById(R.id.poster_iv);
        TextView title_tv = findViewById(R.id.title_tv);
        TextView rate_tv = findViewById(R.id.rate_tv);
        TextView release_tv = findViewById(R.id.release_tv);
        TextView plot_tv = findViewById(R.id.plot_tv);
        Intent me = getIntent();

        if(me.hasExtra("movie")){
            Movie movie = me.getParcelableExtra("movie");
            Picasso.with(this).load(movie.getPosterLink()).into(poster_iv);
            release_tv.setText(movie.getDate());
            rate_tv.setText(Double.toString(movie.getRating()));
            title_tv.setText(movie.getTitle());
            plot_tv.setText(movie.getPlot());
        }
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
