package com.anugraha.project.moviegrid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.model.ReviewsResult;

import java.util.List;
import java.util.Random;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder>  {
    List<ReviewsResult> resultList;
    Context mContext;
    public String[] mColors = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    };
    public ReviewsAdapter(Context mContext,List<ReviewsResult> resultList){
        this.mContext=mContext;
        this.resultList=resultList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_reviews, viewGroup, false);
        return new ReviewsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String namaDosen = resultList.get(i).getAuthor();
        String firstCharNamaDosen = namaDosen.substring(0,1);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstCharNamaDosen, getColor());
        myViewHolder.iv_authorpic.setImageDrawable(drawable);
        myViewHolder.tv_author.setText(resultList.get(i).getAuthor());
        myViewHolder.tv_review.setText(resultList.get(i).getContent());

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_authorpic;
        TextView tv_author,tv_review;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_authorpic = (ImageView) itemView.findViewById(R.id.iv_authorpic);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_review = (TextView) itemView.findViewById(R.id.tv_review);
        }
    }

    public int getColor() {
        String color;

        // Randomly select a fact
        Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }
}
