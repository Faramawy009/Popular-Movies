package com.faramawy009.android.popularmovies;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.faramawy009.android.popularmovies.Database.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        TextView reviews_tv = findViewById(R.id.reviews_tv);
        TextView trailers_tv = findViewById(R.id.trailers_tv);
        CheckBox favorites_cb = findViewById(R.id.favorite_cb);
        Intent me = getIntent();

        if(me.hasExtra("movie")){
            Movie movie = me.getParcelableExtra("movie");
            Picasso.with(this).load(movie.getPosterLink()).into(poster_iv);
            release_tv.setText(movie.getDate());
            rate_tv.setText(Double.toString(movie.getRating()));
            title_tv.setText(movie.getTitle());
            plot_tv.setText(movie.getPlot());
            List<String> reviews = movie.getReviews();
            String reviewString = "";
            for(int i=0; i<reviews.size(); i++){
                reviewString += (i+1) + ". " + reviews.get(i) + "\n\n";
            }
            reviews_tv.setText(reviewString);

            List<String> trailers = movie.getTrailers();
            String trailerString = "";
            for(int i=0; i<trailers.size(); i++){
                trailerString += (i+1) + ". https://www.youtube.com/watch?v=" + trailers.get(i) + "\n\n";
            }
            trailers_tv.setText(trailerString);
        }
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        favorites_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){

                } else{

                }
            }
        });
    }
}
