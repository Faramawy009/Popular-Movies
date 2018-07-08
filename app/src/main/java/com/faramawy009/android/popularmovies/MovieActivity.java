package com.faramawy009.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {
    private ImageView poster_iv;
    private TextView title_tv;
    private TextView rate_tv;
    private TextView release_tv;
    private TextView plot_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        poster_iv = findViewById(R.id.poster_iv);
        title_tv = findViewById(R.id.title_tv);
        rate_tv = findViewById(R.id.rate_tv);
        release_tv = findViewById(R.id.release_tv);
        plot_tv = findViewById(R.id.plot_tv);
        Intent me = getIntent();
        if(me.hasExtra("poster")){
            String posterPath = me.getStringExtra("poster");
            Picasso.with(this).load(posterPath).into(poster_iv);
        }
        if(me.hasExtra("date")){
            String date = me.getStringExtra("date");
            release_tv.setText(date);
        }
        if(me.hasExtra("rating")){
            double rating = me.getDoubleExtra("rating", 0.0);
            rate_tv.setText(Double.toString(rating));
        }
        if(me.hasExtra("title")){
            String title = me.getStringExtra("title");
            title_tv.setText(title);
        }
        if(me.hasExtra("plot")){
            String plot = me.getStringExtra("plot");
            plot_tv.setText(plot);
        }
    }
}
