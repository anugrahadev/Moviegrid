package com.anugraha.project.moviegrid.Activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.anugraha.project.moviegrid.Adapter.TVDetailAdapter.EpisodeAdapter;
import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.TVDetail.Seasons.Episode;
import com.anugraha.project.moviegrid.model.TVDetail.Seasons.SeasonResponse;
import com.anugraha.project.moviegrid.model.TVResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodesActivity extends AppCompatActivity {
    RecyclerView rv_episodes;
    List<Episode> episodeList;
    EpisodeAdapter adapter;
    Integer id=0,sn=0;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        sharedPrefManager = new SharedPrefManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        rv_episodes = (RecyclerView) findViewById(R.id.rv_episodes);
        episodeList = new ArrayList<>();
        adapter = new EpisodeAdapter(getApplicationContext(),episodeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_episodes.setLayoutManager(layoutManager);
        rv_episodes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sn = getIntent().getExtras().getInt("number");
        loadJSON();

    }

    private void loadJSON() {
        Service apiService = Client.getClient().create(Service.class);
        Call<SeasonResponse> call = apiService.getSeasonEpisodes(sharedPrefManager.getSpTVID(), sn, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<SeasonResponse>() {
            @Override
            public void onResponse(Call<SeasonResponse> call, Response<SeasonResponse> response) {
                List<Episode> episodes = response.body().getEpisodes();
                rv_episodes.setAdapter(new EpisodeAdapter(getApplicationContext(),episodes));
            }

            @Override
            public void onFailure(Call<SeasonResponse> call, Throwable t) {

            }
        });
    }
}
