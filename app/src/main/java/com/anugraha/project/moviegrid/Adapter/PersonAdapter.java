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
import com.anugraha.project.moviegrid.Activity.PersonActivity;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.Person;
import com.anugraha.project.moviegrid.model.PersonKnownFor;
import com.anugraha.project.moviegrid.model.PersonResult;
import com.bumptech.glide.Glide;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    private Context mContext;
    private List<PersonResult> personResultList;
    public PersonAdapter(Context mContext, List<PersonResult> personResultList) {
        this.mContext = mContext;
        this.personResultList = personResultList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_person, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.name.setText(personResultList.get(i).getName());
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w342"+personResultList.get(i).getProfilePath())
                .placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return personResultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, knownfor;
        public ImageView thumbnail;
        public MyViewHolder(@NonNull View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        PersonResult clickedDataItem = personResultList.get(pos);
                        Intent intent1 = new Intent(mContext, PersonActivity.class);
                        intent1.putExtra("id",personResultList.get(pos).getId());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent1);

                    }
                }
            });
        }
    }
}
