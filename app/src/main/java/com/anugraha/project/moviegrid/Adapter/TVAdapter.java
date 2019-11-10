package com.anugraha.project.moviegrid.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.anugraha.project.moviegrid.model.TVResult;
import com.bumptech.glide.Glide;

import java.util.List;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.MyViewHolder>{
    Context mContext;
    List<TVResult> tvResultList;
    public TVAdapter(Context mContext, List<TVResult> tvResultList){
        this.mContext=mContext;
        this.tvResultList=tvResultList;
    }
    @NonNull
    @Override
    public TVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_movie, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(tvResultList.get(i).getOriginalName());
        viewHolder.date.setText(tvResultList.get(i).getFirstAirDate());
        String vote = Double.toString(tvResultList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w342"+tvResultList.get(i).getPosterPath())
                .placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return tvResultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, userrating, date;
        public ImageView thumbnail;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            userrating = (TextView) view.findViewById(R.id.tv_user_rating);
            date = (TextView) view.findViewById(R.id.tv_release);
            thumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        TVResult clickedDataItem = tvResultList.get(pos);
                        Intent intent1 = new Intent(mContext, TVDetailActivity.class);
                        intent1.putExtra("original_title",tvResultList.get(pos).getOriginalName());
                        intent1.putExtra("poster_path",tvResultList.get(pos).getPosterPath());
                        intent1.putExtra("backdrop_path",tvResultList.get(pos).getBackdropPath());
                        intent1.putExtra("overview",tvResultList.get(pos).getOverview());
                        intent1.putExtra("vote_average",Double.toString(tvResultList.get(pos).getVoteAverage()));
                        intent1.putExtra("release_date",tvResultList.get(pos).getFirstAirDate());
                        intent1.putExtra("id",tvResultList.get(pos).getId());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent1);

                    }
                }
            });
        }
    }
}
