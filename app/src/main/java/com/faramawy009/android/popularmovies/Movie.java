package com.faramawy009.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by elfar009 on 6/10/18.
 */

public class Movie implements Parcelable {
    private int _id;
    private String mTitle;
    private String mPosterLink;
    private String mPlot;
    private double mRating;
    private String mDate;
    private List<String> mTrailers;
    private List<String> mReviews;

    public Movie(String title, String posterLink, String plot, double rating, String date) {
        this.mTitle = title;
        this.mPosterLink = posterLink;
        this.mPlot = plot;
        this.mRating = rating;
        this.mDate = date;
    }

    public Movie(int id, String title, String posterLink, String plot, double rating, String date,
                 List<String> trailers, List<String> reviews) {
        this._id = id;
        this.mTitle = title;
        this.mPosterLink = posterLink;
        this.mPlot = plot;
        this.mRating = rating;
        this.mDate = date;
        this.mTrailers = new ArrayList<>(trailers);
        this.mReviews = new ArrayList<>(reviews);
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

    public String getPlot() {
        return mPlot;
    }

    public double getRating() {
        return mRating;
    }

    public String getDate() {
        return mDate;
    }

    public List<String> getTrailers() {
        return mTrailers;
    }

    public List<String> getReviews() {
        return mReviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(_id);
        parcel.writeString(mTitle);
        parcel.writeString(mPosterLink);
        parcel.writeString(mPlot);
        parcel.writeDouble(mRating);
        parcel.writeString(mDate);
        parcel.writeStringList(mTrailers);
        parcel.writeStringList(mReviews);
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
        _id = in.readInt();
        mTitle = in.readString();
        mPosterLink = in.readString();
        mPlot = in.readString();
        mRating = in.readDouble();
        mDate = in.readString();
        mTrailers = in.createStringArrayList();
        mReviews = in.createStringArrayList();
//        in.readStringList(mTrailers);
//        in.readStringList(mReviews);
    }
}
