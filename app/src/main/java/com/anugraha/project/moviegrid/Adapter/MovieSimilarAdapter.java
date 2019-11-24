package com.anugraha.project.moviegrid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anugraha.project.moviegrid.Activity.DetailActivity;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.model.Movie;
import com.bumptech.glide.Glide;

import java.util.List;

public class MovieSimilarAdapter extends RecyclerView.Adapter<MovieSimilarAdapter.MyViewGHolder> {
    private Context mContext;
    private List<Movie> movieSimilarResults;

    public MovieSimilarAdapter(Context mContext, List<Movie> movieSimilarResults) {
        this.mContext = mContext;
        this.movieSimilarResults = movieSimilarResults;
    }

    @Override
    public MyViewGHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_similar_movie, viewGroup, false);

        return new MyViewGHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewGHolder viewHolder, int i) {
        String vote = Double.toString(movieSimilarResults.get(i).getVoteAverage());


        if (movieSimilarResults.get(i).getVoteAverage()==0){
            viewHolder.userrating.setText("NR");
        }else{
            viewHolder.userrating.setText(vote);
            if (movieSimilarResults.get(i).getVoteAverage() < 5){
                viewHolder.userrating.setTextColor(Color.RED);
            }else if(movieSimilarResults.get(i).getVoteAverage() < 7.5){
                viewHolder.userrating.setTextColor(Color.parseColor("#CC7832"));
            }else{
                viewHolder.userrating.setTextColor(Color.parseColor("#005618"));
            }
        }

        viewHolder.tv_release.setText(movieSimilarResults.get(i).getReleaseDate());
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w300"+movieSimilarResults.get(i).getPosterPath())
                .placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount(){
        return movieSimilarResults.size();
    }

    public class MyViewGHolder extends RecyclerView.ViewHolder{
        public TextView  userrating,tv_release;
        public ImageView thumbnail;

        public MyViewGHolder(View view){
            super(view);
            tv_release = (TextView) view.findViewById(R.id.tv_release);
            userrating = (TextView) view.findViewById(R.id.tv_user_rating);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieSimilarResults.get(pos);
                        Intent intent1 = new Intent(mContext, DetailActivity.class);
                        intent1.putExtra("id",movieSimilarResults.get(pos).getId());
                        intent1.putExtra("movies",clickedDataItem);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent1);

                    }
                }
            });
        }
    }
}
