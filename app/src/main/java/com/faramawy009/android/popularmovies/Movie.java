package com.faramawy009.android.popularmovies;

import java.util.Date;

/**
 * Created by elfar009 on 6/10/18.
 */

public class Movie {
    private String title;
    private String posterLink;
    private String plot;
    private double rating;
    private String date;

    public Movie(String title, String posterLink, String plot, double rating, String date) {
        this.title = title;
        this.posterLink = posterLink;
        this.plot = plot;
        this.rating = rating;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
