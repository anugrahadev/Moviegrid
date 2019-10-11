package com.anugraha.project.moviegrid.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.MovieDetailResponse;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ImageView iv_colappsingtoolbar;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.greenPrimary), PorterDuff.Mode.SRC_ATOP);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.colappsingtoolbar);

        collapsingToolbarLayout.setTitle("MovieGrid");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#00D277"));


        iv_colappsingtoolbar = (ImageView) findViewById(R.id.thumbnail_image_header);
        Intent intentStarted = getIntent();
        if (intentStarted.hasExtra("id")){
            Integer movie_id = getIntent().getExtras().getInt("id");
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MovieDetailResponse> call = apiService.getMovieDEtail(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieDetailResponse>() {
                @Override
                public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                    progressDialog.dismiss();
                    Glide.with(getApplicationContext())
                            .load("https://image.tmdb.org/t/p/w780/"+response.body().getBackdropPath())
                            .placeholder(R.drawable.load)
                            .into(iv_colappsingtoolbar);
                }

                @Override
                public void onFailure(Call<MovieDetailResponse> call, Throwable t) {

                }
            });
        }

    }
}
