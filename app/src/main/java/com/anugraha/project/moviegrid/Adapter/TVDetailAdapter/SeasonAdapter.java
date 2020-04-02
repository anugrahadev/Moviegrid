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
import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.TVDetail.Season;
import com.bumptech.glide.Glide;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {

    Context mContext;
    List<Season> seasonList;
    SharedPrefManager sharedPrefManager;


    public SeasonAdapter(Context mContext, List<Season> seasonList){
        this.mContext = mContext;
        this.seasonList = seasonList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_season, viewGroup, false);
        return new SeasonAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w300/"+seasonList.get(i).getPosterPath())
                .placeholder(R.drawable.load)
                .into(myViewHolder.iv_poster);
        myViewHolder.tv_judul_season.setText(seasonList.get(i).getName());
        myViewHolder.tv_total_episode.setText(seasonList.get(i).getEpisodeCount().toString());
//        myViewHolder.tv_overview.setText(seasonList.get(i).getOverview());
        myViewHolder.tv_date.setText(seasonList.get(i).getAirDate());
//        String tahun = seasonList.get(i).getAirDate().toString().substring(0,4);
//        myViewHolder.tv_tahun_season.setText(tahun);


    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_judul_season, tv_tahun_season,tv_total_episode, tv_date, tv_overview;
        public ImageView iv_poster;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            tv_judul_season = (TextView) itemView.findViewById(R.id.tv_judul_season);
//            tv_tahun_season = (TextView) itemView.findViewById(R.id.tv_tahun_season);
            tv_total_episode = (TextView) itemView.findViewById(R.id.tv_total_episode);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_overview = (TextView) itemView.findViewById(R.id.tv_overview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Season clickedDataItem = seasonList.get(pos);
                        Intent intent1 = new Intent(mContext, EpisodesActivity.class);
                        intent1.putExtra("number",seasonList.get(pos).getSeasonNumber());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent1);

                    }
                }
            });
        }
    }
}
