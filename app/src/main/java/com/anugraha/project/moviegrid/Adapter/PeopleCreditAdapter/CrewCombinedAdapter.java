package com.anugraha.project.moviegrid.Adapter.PeopleCreditAdapter;

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
import com.anugraha.project.moviegrid.model.PeopleModel.Cast;
import com.anugraha.project.moviegrid.model.PeopleModel.CastCombined;
import com.anugraha.project.moviegrid.model.PeopleModel.Crew;
import com.anugraha.project.moviegrid.model.PeopleModel.CrewCombined;
import com.bumptech.glide.Glide;

import java.util.List;

public class CrewCombinedAdapter extends RecyclerView.Adapter<CrewCombinedAdapter.MyViewGHolder> {
    private Context mContext;
    private List<CrewCombined> movieList;

    public CrewCombinedAdapter(Context mContext, List<CrewCombined> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MyViewGHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_peoplecredit, viewGroup, false);
        return new MyViewGHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewGHolder viewHolder, int i) {

        if (movieList.get(i).getMediaType().equals("movie")){
            Glide.with(mContext)
                    .load("https://image.tmdb.org/t/p/w342"+movieList.get(i).getPosterPath())
                    .placeholder(R.drawable.load)
                    .into(viewHolder.iv_postercast);
            viewHolder.tv_judul_cast.setText(movieList.get(i).getOriginalTitle());
            viewHolder.tv_judul_cast.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie_white_24dp, 0, 0, 0);
            viewHolder.tv_peran.setText(movieList.get(i).getJob());
            viewHolder.tv_release.setText(movieList.get(i).getReleaseDate());
        }else{
            Glide.with(mContext)
                    .load("https://image.tmdb.org/t/p/w342"+movieList.get(i).getPosterPath())
                    .placeholder(R.drawable.load)
                    .into(viewHolder.iv_postercast);
            viewHolder.tv_judul_cast.setText(movieList.get(i).getOriginalName());
            viewHolder.tv_judul_cast.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tv_white_24dp, 0, 0, 0);
            viewHolder.tv_peran.setText(movieList.get(i).getJob());
            viewHolder.tv_release.setText(movieList.get(i).getFirstAirDate());
        }
    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

    public class MyViewGHolder extends RecyclerView.ViewHolder{
        public TextView tv_judul_cast, tv_peran,tv_mediatype,tv_release;
        public ImageView iv_postercast;

        public MyViewGHolder(View view){
            super(view);
            tv_judul_cast = (TextView) view.findViewById(R.id.tv_judul_cast);
            tv_peran = (TextView) view.findViewById(R.id.tv_peran);
            tv_release = (TextView) view.findViewById(R.id.tv_release);
            iv_postercast = (ImageView) view.findViewById(R.id.iv_postercast);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        CrewCombined clickedDataItem = movieList.get(pos);
                        if (movieList.get(pos).getMediaType().equals("movie")){
                            Intent intent1 = new Intent(mContext, DetailActivity.class);
                            intent1.putExtra("id",movieList.get(pos).getId());
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent1);
                        }


                    }
                }
            });
        }
    }
}
