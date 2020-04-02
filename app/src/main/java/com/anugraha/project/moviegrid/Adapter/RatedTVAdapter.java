package com.anugraha.project.moviegrid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anugraha.project.moviegrid.Activity.DetailActivity;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Activity.TVDetailActivity;
import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.model.TVResult;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RatedTVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w342";
    Context context;
    List<TVResult> tvResultList;
    SharedPrefManager sharedPrefManager;
    private boolean isLoadingAdded = false;

    //Variable ArrayList dengan Parameter dari Class DataFilter (Nama, ImageID)
    private ArrayList<DataFilter> arrayList;

    RatedTVAdapter(ArrayList<DataFilter> arrayList){
        this.arrayList = arrayList;
    }

    public RatedTVAdapter(Context context) {
        this.context = context;
        tvResultList = new ArrayList<>();
    }

    public List<TVResult> getMovies() {
        return tvResultList;
    }

    public void setMovies(List<TVResult> movieResults) {
        this.tvResultList = tvResultList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        sharedPrefManager = new SharedPrefManager(context);


        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new RatedTVAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.card_movie2, parent, false);
        viewHolder = new RatedTVAdapter.MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TVResult result = tvResultList.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final RatedTVAdapter.MovieVH movieVH = (RatedTVAdapter.MovieVH) holder;
                String vote = Double.toString(result.getVoteAverage());
                movieVH.title.setText(result.getName());
                movieVH.tv_release.setText(result.getFirstAirDate());
                movieVH.userrating.setText(result.getRating().toString());

                /**
                 * Using Glide to handle image loading.
                 * Learn more about Glide here:
                 *
                 */
                Glide.with(context)
                        .load(BASE_URL_IMG+result.getPosterPath())
                        .placeholder(R.drawable.load)
                        .into(movieVH.thumbnail);


                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return tvResultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == tvResultList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

     /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(TVResult r) {
        tvResultList.add(r);
        notifyItemInserted(tvResultList.size() - 1);
    }

    public void addAll(List<TVResult> moveResults) {
        for (TVResult result : moveResults) {
            add(result);
        }
    }

    public void remove(TVResult r) {
        int position = tvResultList.indexOf(r);
        if (position > -1) {
            tvResultList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new TVResult());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = tvResultList.size() - 1;
        TVResult result = getItem(position);

        if (result != null) {
            tvResultList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TVResult getItem(int position) {
        return tvResultList.get(position);
    }

    /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        public TextView title, userrating,tv_release, tv_yourrate;
        public ImageView thumbnail;
        public ProgressBar mProgress;

        public MovieVH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_release = (TextView) itemView.findViewById(R.id.tv_date);
            thumbnail = (ImageView) itemView.findViewById(R.id.iv_poster);
            userrating = (TextView) itemView.findViewById(R.id.tv_yourrate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        TVResult clickedDataItem = tvResultList.get(pos);
                        Intent intent1 = new Intent(context, TVDetailActivity.class);
//                        intent1.putExtra("tv", clickedDataItem );
                        intent1.putExtra("original_title",tvResultList.get(pos).getName());
                        intent1.putExtra("poster_path",tvResultList.get(pos).getPosterPath());
                        intent1.putExtra("backdrop_path",tvResultList.get(pos).getBackdropPath());
                        intent1.putExtra("overview",tvResultList.get(pos).getOverview());
                        intent1.putExtra("vote_average",Double.toString(tvResultList.get(pos).getVoteAverage()));
                        intent1.putExtra("release_date",tvResultList.get(pos).getFirstAirDate());
                        intent1.putExtra("id",tvResultList.get(pos).getId());
                        sharedPrefManager.setSpTVID(tvResultList.get(pos).getId());

                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);

                    }
                }
            });

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    void setFilter(ArrayList<DataFilter> filterList){
        arrayList = new ArrayList<>();
        arrayList.addAll(filterList);
        notifyDataSetChanged();
    }

}