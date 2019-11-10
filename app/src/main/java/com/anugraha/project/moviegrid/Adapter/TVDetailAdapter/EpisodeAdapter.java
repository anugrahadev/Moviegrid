package com.anugraha.project.moviegrid.Adapter.TVDetailAdapter;

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
import com.anugraha.project.moviegrid.Activity.EpisodesActivity;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.MoviesAdapter;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.TVDetail.Season;
import com.anugraha.project.moviegrid.model.TVDetail.Seasons.Episode;
import com.bumptech.glide.Glide;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MyViewHolder> {

    Context mContext;
    List<Episode> episodeList;

    public EpisodeAdapter(Context mContext, List<Episode> episodeList){
        this.mContext = mContext;
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_episodes, viewGroup, false);
        return new EpisodeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w300/"+episodeList.get(i).getStillPath())
                .into(myViewHolder.iv_images);
        myViewHolder.tv_episode_overview.setText(episodeList.get(i).getOverview());
        myViewHolder.tv_date.setText(episodeList.get(i).getAirDate());
        myViewHolder.tv_episode_title.setText(episodeList.get(i).getName());
        myViewHolder.tv_episode_number.setText(episodeList.get(i).getEpisodeNumber().toString());

    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_episode_overview, tv_date,tv_episode_title, tv_episode_number;
        public ImageView iv_images;
        public MyViewHolder(@NonNull View view) {
            super(view);
            iv_images = (ImageView) view.findViewById(R.id.iv_images);
            tv_episode_overview = (TextView) view.findViewById(R.id.tv_episode_overview);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_episode_title = (TextView) view.findViewById(R.id.tv_episode_title);
            tv_episode_number = (TextView) view.findViewById(R.id.tv_episode_number);

        }
    }
}
