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
import android.widget.Toast;

import com.anugraha.project.moviegrid.Activity.DetailActivity;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.model.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by delaroy on 12/5/17.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w342";

    private List<Movie> movieResults;
    private Context context;

    private boolean isLoadingAdded = false;

    //Variable ArrayList dengan Parameter dari Class DataFilter (Nama, ImageID)
    private ArrayList<DataFilter> arrayList;

    PaginationAdapter(ArrayList<DataFilter> arrayList){
        this.arrayList = arrayList;
    }

    public PaginationAdapter(Context context) {
        this.context = context;
        movieResults = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return movieResults;
    }

    public void setMovies(List<Movie> movieResults) {
        this.movieResults = movieResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.card_movie, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Movie result = movieResults.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;
                String vote = Double.toString(result.getVoteAverage());
                movieVH.title.setText(result.getTitle());
                if (result.getVoteAverage()==0){
                    movieVH.userrating.setText("NR");
                }else{
                    movieVH.userrating.setText(vote);
                    if (result.getVoteAverage() < 5){
                        movieVH.userrating.setTextColor(Color.RED);
                    }else if(result.getVoteAverage() < 7.5){
                        movieVH.userrating.setTextColor(Color.parseColor("#CC7832"));
                    }else{
                        movieVH.userrating.setTextColor(Color.parseColor("#005618"));
                    }
                }
                movieVH.tv_release.setText(result.getReleaseDate());

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
        return movieResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Movie r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<Movie> moveResults) {
        for (Movie result : moveResults) {
            add(result);
        }
    }

    public void remove(Movie r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
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
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return movieResults.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        public TextView title, userrating,tv_release;
        public ImageView thumbnail;
        public ProgressBar mProgress;

        public MovieVH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            tv_release = (TextView) itemView.findViewById(R.id.tv_release);
            userrating = (TextView) itemView.findViewById(R.id.tv_user_rating);
            thumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieResults.get(pos);
                        Intent intent1 = new Intent(context, DetailActivity.class);
                        intent1.putExtra("movies", clickedDataItem );
                        intent1.putExtra("original_title",movieResults.get(pos).getOriginalTitle());
                        intent1.putExtra("poster_path",movieResults.get(pos).getPosterPath());
                        intent1.putExtra("backdrop_path",movieResults.get(pos).getBackdropPath());
                        intent1.putExtra("overview",movieResults.get(pos).getOverview());
                        intent1.putExtra("vote_average",Double.toString(movieResults.get(pos).getVoteAverage()));
                        intent1.putExtra("release_date",movieResults.get(pos).getReleaseDate());
                        intent1.putExtra("id",movieResults.get(pos).getId());
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