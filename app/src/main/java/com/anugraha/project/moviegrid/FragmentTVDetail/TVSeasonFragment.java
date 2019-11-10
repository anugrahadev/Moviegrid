package com.anugraha.project.moviegrid.FragmentTVDetail;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.TVDetailAdapter.SeasonAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.TVDetail.Season;
import com.anugraha.project.moviegrid.model.TVDetail.TVDetailResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVSeasonFragment extends Fragment {
    RecyclerView rv_season;
    List<Season> seasonList;
    SeasonAdapter adapter;
    Integer id;
    ProgressDialog progressDialog;
    public TVSeasonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tvseason, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        id = getActivity().getIntent().getExtras().getInt("id");
        rv_season = view.findViewById(R.id.rv_season);
        seasonList = new ArrayList<>();
        adapter = new SeasonAdapter(getContext(),seasonList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_season.setLayoutManager(layoutManager);
        rv_season.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadJSON();

        return view;
    }

    private void loadJSON() {
        Service apiservices = Client.getClient().create(Service.class);
        Call<TVDetailResponse> call = apiservices.getTVDetail(id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<TVDetailResponse>() {
            @Override
            public void onResponse(Call<TVDetailResponse> call, Response<TVDetailResponse> response) {
                seasonList = response.body().getSeasons();
                rv_season.setAdapter(new SeasonAdapter(getContext(), seasonList));
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TVDetailResponse> call, Throwable t) {

            }
        });
    }

}
