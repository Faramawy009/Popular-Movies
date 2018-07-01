package com.faramawy009.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elfar009 on 6/10/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieItemView;
        MovieViewHolder(View itemView) {
            super(itemView);
            movieItemView = (ImageView) itemView.findViewById(R.id.iv_movie_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickIndex = getAdapterPosition();
            itemOnClickListener.onGridItemClick(clickIndex);
        }
    }

    public interface GridItemClickListener {
        void onGridItemClick(int clickedItemIndex);
    }
    private List<Movie> moviesList;
    GridItemClickListener itemOnClickListener;
    public MovieAdapter(List<Movie> moviesList, GridItemClickListener itemOnClickListener) {
        this.moviesList = moviesList;
        this.itemOnClickListener = itemOnClickListener;

    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForMovieItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForMovieItem, parent, shouldAttachToParentImmediately);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie m = moviesList.get(position);
        Picasso.with(holder.movieItemView.getContext()).load(m.getPosterLink()).into(holder.movieItemView);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
