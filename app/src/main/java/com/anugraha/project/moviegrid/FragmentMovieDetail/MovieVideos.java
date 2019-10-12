package com.anugraha.project.moviegrid.FragmentMovieDetail;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.CrewAdapter;
import com.anugraha.project.moviegrid.Adapter.TrailerAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.Trailer;
import com.anugraha.project.moviegrid.model.TrailerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieVideos extends Fragment {

    RecyclerView rv_trailer_frag;
    TextView tv_judul;
    ImageView iv_thumbnail;
    List<Trailer> trailerList;
    TrailerAdapter adapter;
    Integer movie_id;
    ProgressDialog progressDialog;
    public MovieVideos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_movie_videos, container, false);
        movie_id = getActivity().getIntent().getExtras().getInt("id");
        tv_judul = (TextView) view.findViewById(R.id.tv_judul_trailer);
        iv_thumbnail = (ImageView) view.findViewById(R.id.iv_ytthumbnail);
        rv_trailer_frag = (RecyclerView) view.findViewById(R.id.rv_trailer_frag);
        trailerList = new ArrayList<>();
        adapter = new TrailerAdapter(getContext(), trailerList);
        RecyclerView.LayoutManager layoutManagerCrew = new LinearLayoutManager(getContext());
        rv_trailer_frag.setLayoutManager(layoutManagerCrew);
        rv_trailer_frag.setAdapter(adapter);
        rv_trailer_frag.setHasFixedSize(false);
        adapter.notifyDataSetChanged();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        loadJSONTrailer();


        return view;
    }

    private void loadJSONTrailer() {
        Service apiservices = Client.getClient().create(Service.class);
        Call<TrailerResponse> call = apiservices.getTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                progressDialog.dismiss();
                trailerList = response.body().getResults();
                rv_trailer_frag.setAdapter(new TrailerAdapter(getContext(), trailerList));
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

            }
        });
    }

}
