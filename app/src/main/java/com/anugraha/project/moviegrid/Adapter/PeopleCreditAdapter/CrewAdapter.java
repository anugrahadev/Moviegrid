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
import com.anugraha.project.moviegrid.model.PeopleModel.Crew;
import com.bumptech.glide.Glide;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.MyViewGHolder> {
    private Context mContext;
    private List<Crew> movieList;

    public CrewAdapter(Context mContext, List<Crew> movieList) {
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

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w342"+movieList.get(i).getPosterPath())
                .placeholder(R.drawable.load)
                .into(viewHolder.iv_postercast);

        viewHolder.tv_judul_cast.setText(movieList.get(i).getOriginalTitle());
        viewHolder.tv_peran.setText(movieList.get(i).getDepartment());
        viewHolder.tv_release.setText(movieList.get(i).getReleaseDate());
    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

    public class MyViewGHolder extends RecyclerView.ViewHolder{
        public TextView tv_judul_cast, tv_peran,tv_release;
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
                        Crew clickedDataItem = movieList.get(pos);
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
