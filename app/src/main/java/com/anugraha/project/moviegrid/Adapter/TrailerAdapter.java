package com.anugraha.project.moviegrid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Activity.VideosActivity;
import com.anugraha.project.moviegrid.model.Trailer;
import com.bumptech.glide.Glide;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Trailer> trailerList;

    public TrailerAdapter(Context mContext, List<Trailer> trailerList){
        this.mContext = mContext;
        this.trailerList = trailerList;

    }

    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_trailer, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final TrailerAdapter.MyViewHolder viewHolder, int i){
//        viewHolder.videoWeb.loadData("<iframe src=\\\"https://www.youtube.com/embed/\""+trailerList.get(i).getKey()+"\\\" frameborder=\\\"0\\\" allow=\\\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\\\" allowfullscreen></iframe>", "text/html","utf-8");
        Glide.with(mContext)
                .load("http://i3.ytimg.com/vi/"+trailerList.get(i).getKey()+"/hqdefault.jpg")
                .placeholder(R.drawable.load)
                .override(100, 175)
                .into(viewHolder.iv_ytthumbnail);
        viewHolder.tv_judul.setText(trailerList.get(i).getName());

    }

    @Override
    public int getItemCount(){

        return trailerList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
//        public TextView title;
//        public ImageView thumbnail;
//        public YouTubePlayerView youtubePlayerView;
        public WebView videoWeb;
        public ImageView iv_ytthumbnail,iv_playbutton;
        public TextView tv_judul;
        public MyViewHolder(View view){
            super(view);
            iv_playbutton = (ImageView) view.findViewById(R.id.iv_playbutton);
            iv_ytthumbnail = (ImageView) view.findViewById(R.id.iv_ytthumbnail);
            tv_judul = (TextView) view.findViewById(R.id.tv_judul_trailer);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Trailer clickedDataItem = trailerList.get(pos);
                        String videoId = trailerList.get(pos).getKey();
                        Intent intent = new Intent(mContext, VideosActivity.class);
                        intent.putExtra("VIDEO_ID", videoId);
                        intent.putExtra("name",trailerList.get(pos).getName());
                        intent.putExtra("type",trailerList.get(pos).getType());
                        intent.putExtra("language",trailerList.get(pos).getIso6391());
                        intent.putExtra("languagereg",trailerList.get(pos).getIso31661());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });

        }
    }
}
