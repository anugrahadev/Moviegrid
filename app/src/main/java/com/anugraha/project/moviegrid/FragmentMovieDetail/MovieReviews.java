package com.anugraha.project.moviegrid.FragmentMovieDetail;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.ReviewsAdapter;
import com.anugraha.project.moviegrid.Adapter.TrailerAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.ReviewsResponse;
import com.anugraha.project.moviegrid.model.ReviewsResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieReviews extends Fragment {
    ReviewsAdapter reviewsAdapter;
    List<ReviewsResult> reviewsResultList;
    RecyclerView rv_review;
    ProgressDialog progressDialog;
    Integer movie_id;
    public MovieReviews() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        movie_id = getActivity().getIntent().getExtras().getInt("id");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        rv_review = (RecyclerView) view.findViewById(R.id.rv_reviews);
        reviewsResultList = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(getContext(), reviewsResultList);
        RecyclerView.LayoutManager layoutManagerReview = new LinearLayoutManager(getContext());
        rv_review.setLayoutManager(layoutManagerReview);
        rv_review.setAdapter(reviewsAdapter);
        rv_review.setHasFixedSize(false);
        reviewsAdapter.notifyDataSetChanged();

        loadJSONReview();

        return view;
    }

    private void loadJSONReview() {
        com.anugraha.project.moviegrid.api.Service apiservices = Client.getClient().create(Service.class);
        Call<ReviewsResponse> call = apiservices.getReviews(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {

                reviewsResultList = response.body().getResults();
                rv_review.setAdapter(new ReviewsAdapter(getContext(), reviewsResultList));
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {

            }
        });
    }

}
