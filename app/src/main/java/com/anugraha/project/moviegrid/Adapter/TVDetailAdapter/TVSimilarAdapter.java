package com.anugraha.project.moviegrid.Adapter.TVDetailAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anugraha.project.moviegrid.Activity.DetailActivity;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Activity.TVDetailActivity;
import com.anugraha.project.moviegrid.Adapter.MovieSimilarAdapter;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieSimilarResult;
import com.anugraha.project.moviegrid.model.TVDetail.Seasons.TVSimilarResult;
import com.bumptech.glide.Glide;

import java.util.List;

public class TVSimilarAdapter extends RecyclerView.Adapter<TVSimilarAdapter.MyViewHolder> {
    private Context mContext;
    private List<TVSimilarResult> tvSimilarResults;

    public TVSimilarAdapter(Context mContext, List<TVSimilarResult> tvSimilarResults) {
        this.mContext = mContext;
        this.tvSimilarResults = tvSimilarResults;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_similar_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String vote = Double.toString(tvSimilarResults.get(i).getVoteAverage());


        if (tvSimilarResults.get(i).getVoteAverage()==0){
            myViewHolder.userrating.setText("NR");
        }else{
            myViewHolder.userrating.setText(vote);
            if (tvSimilarResults.get(i).getVoteAverage() < 5){
                myViewHolder.userrating.setTextColor(Color.RED);
            }else if(tvSimilarResults.get(i).getVoteAverage() < 7.5){
                myViewHolder.userrating.setTextColor(Color.parseColor("#CC7832"));
            }else{
                myViewHolder.userrating.setTextColor(Color.parseColor("#005618"));
            }
        }

//        myViewHolder.tv_release.setText(tvSimilarResults.get(i).getA());
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w300"+tvSimilarResults.get(i).getPosterPath())
                .placeholder(R.drawable.load)
                .into(myViewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return tvSimilarResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  userrating,tv_release;
        public ImageView thumbnail;
        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_release = (TextView) view.findViewById(R.id.tv_release);
            userrating = (TextView) view.findViewById(R.id.tv_user_rating);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION){
//                        TVSimilarResult clickedDataItem = tvSimilarResults.get(pos);
//                        Intent intent1 = new Intent(mContext, DetailActivity.class);
//                        intent1.putExtra("id",tvSimilarResults.get(pos).getId());
//                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        mContext.startActivity(intent1);
//
//                    }
//                }
//            });
        }
    }
}
