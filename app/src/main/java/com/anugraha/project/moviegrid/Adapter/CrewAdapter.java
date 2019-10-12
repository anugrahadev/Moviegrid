package com.anugraha.project.moviegrid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anugraha.project.moviegrid.Activity.PersonActivity;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.model.Cast;
import com.anugraha.project.moviegrid.model.Crew;
import com.bumptech.glide.Glide;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.MyViewHolder> {
    private List<Crew> crewList;
    private Context mContext;
    public CrewAdapter(Context mContext, List<Crew> crewList){
        this.crewList = crewList;
        this.mContext=mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_cast, viewGroup, false);
        return new CrewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_castact.setText(crewList.get(position).getDepartment());
        holder.tv_castname.setText(crewList.get(position).getName());
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w154/"+crewList.get(position).getProfilePath())
                .placeholder(R.drawable.load)
                .override(100, 175)
                .into(holder.iv_castpic);
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_castname, tv_castact;
        public ImageView iv_castpic;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_castpic = (ImageView) itemView.findViewById(R.id.iv_cast);
            tv_castact = (TextView) itemView.findViewById(R.id.tv_castact);
            tv_castname = (TextView) itemView.findViewById(R.id.tv_castname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Crew clickedDataItem = crewList.get(pos);
                        Intent intent1 = new Intent(mContext, PersonActivity.class);
                        intent1.putExtra("id",crewList.get(pos).getId());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent1);

                    }
                }
            });
        }
    }
}