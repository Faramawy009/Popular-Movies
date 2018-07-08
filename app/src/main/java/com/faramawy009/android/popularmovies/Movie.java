package com.faramawy009.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by elfar009 on 6/10/18.
 */

public class Movie implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterLink);
        parcel.writeString(plot);
        parcel.writeDouble(rating);
        parcel.writeString(date);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        title = in.readString();
        posterLink = in.readString();
        plot = in.readString();
        rating = in.readDouble();
        date = in.readString();
    }
}
