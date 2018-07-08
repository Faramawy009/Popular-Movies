package com.faramawy009.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by elfar009 on 6/10/18.
 */

public class Movie implements Parcelable {
    private String mTitle;
    private String mPosterLink;
    private String mPlot;
    private double mRating;
    private String mDate;

    public Movie(String title, String posterLink, String plot, double rating, String date) {
        this.mTitle = title;
        this.mPosterLink = posterLink;
        this.mPlot = plot;
        this.mRating = rating;
        this.mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getPosterLink() {
        return mPosterLink;
    }

    public void setPosterLink(String posterLink) {
        this.mPosterLink = posterLink;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String plot) {
        this.mPlot = plot;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        this.mRating = rating;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mPosterLink);
        parcel.writeString(mPlot);
        parcel.writeDouble(mRating);
        parcel.writeString(mDate);
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
        mTitle = in.readString();
        mPosterLink = in.readString();
        mPlot = in.readString();
        mRating = in.readDouble();
        mDate = in.readString();
    }
}
