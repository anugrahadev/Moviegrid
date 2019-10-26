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

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewGHolder> {
    private Context mContext;
    private List<Movie> movieList;

    public MoviesAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MyViewGHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_movie, viewGroup, false);
        return new MyViewGHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewGHolder viewHolder, int i) {
        viewHolder.title.setText(movieList.get(i).getOriginalTitle());
        String vote = Double.toString(movieList.get(i).getVoteAverage());

        if (movieList.get(i).getVoteAverage()==0){
            viewHolder.userrating.setText("NR");
        }else{
            viewHolder.userrating.setText(vote);
            if (movieList.get(i).getVoteAverage() < 5){
                viewHolder.userrating.setTextColor(Color.RED);
            }else if(movieList.get(i).getVoteAverage() < 7.5){
                viewHolder.userrating.setTextColor(Color.parseColor("#CC7832"));
            }else{
                viewHolder.userrating.setTextColor(Color.parseColor("#005618"));
            }
        }

        viewHolder.tv_release.setText(movieList.get(i).getReleaseDate());
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w342"+movieList.get(i).getPosterPath())
                .placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

    public class MyViewGHolder extends RecyclerView.ViewHolder{
        public TextView title, userrating,tv_release;
        public ImageView thumbnail;

        public MyViewGHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            tv_release = (TextView) view.findViewById(R.id.tv_release);
            userrating = (TextView) view.findViewById(R.id.tv_user_rating);
            thumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieList.get(pos);
                        Intent intent1 = new Intent(mContext, DetailActivity.class);
                        intent1.putExtra("original_title",movieList.get(pos).getOriginalTitle());
                        intent1.putExtra("poster_path",movieList.get(pos).getPosterPath());
                        intent1.putExtra("backdrop_path",movieList.get(pos).getBackdropPath());
                        intent1.putExtra("overview",movieList.get(pos).getOverview());
                        intent1.putExtra("vote_average",Double.toString(movieList.get(pos).getVoteAverage()));
                        intent1.putExtra("release_date",movieList.get(pos).getReleaseDate());
                        intent1.putExtra("id",movieList.get(pos).getId());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent1);

                    }
                }
            });
        }
    }
}
