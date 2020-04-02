package com.anugraha.project.moviegrid.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.anugraha.project.moviegrid.PagerAdapter.PagerMovieDetail;
import com.anugraha.project.moviegrid.PagerAdapter.PagerTVDetail;
import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailResponse;
import com.anugraha.project.moviegrid.model.TVDetail.TVDetailResponse;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVDetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    ImageView iv_colappsingtoolbar;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    LinearLayout fragment_detail;
    SharedPrefManager sharedPrefManager;
    int imgquality=0;

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdetail);
        Intent intentStarted = getIntent();
        sharedPrefManager = new SharedPrefManager(this);

//        progressDialog = new ProgressDialog(DetailActivity.this);
//        progressDialog.setMessage("Loading movies...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.greenPrimary), PorterDuff.Mode.SRC_ATOP);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.colappsingtoolbar);
        collapsingToolbarLayout.setTitle("Movie Details");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#00D277"));

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nested);
        scrollView.setFillViewport (true);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Season"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Similar"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        //Creating our pager adapter
        PagerTVDetail adapter = new PagerTVDetail(getSupportFragmentManager(), tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (sharedPrefManager.getImgQuality()=="High"){
            imgquality=780;
        }else if(sharedPrefManager.getImgQuality()=="Medium"){
            imgquality=780;
        }else {
            imgquality=300;
        }


        iv_colappsingtoolbar = (ImageView) findViewById(R.id.thumbnail_image_header);
        if (intentStarted.hasExtra("id")){
            Integer movie_id = getIntent().getExtras().getInt("id");
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<TVDetailResponse> call = apiService.getTVDetail(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSpLang());
            call.enqueue(new Callback<TVDetailResponse>() {
                @Override
                public void onResponse(Call<TVDetailResponse> call, Response<TVDetailResponse> response) {
//                    progressDialog.dismiss();
                    Glide.with(getApplicationContext())
                            .load("https://image.tmdb.org/t/p/w"+imgquality+response.body().getBackdropPath())
                            .placeholder(R.drawable.load)
                            .into(iv_colappsingtoolbar);

                }

                @Override
                public void onFailure(Call<TVDetailResponse> call, Throwable t) {

                }
            });
        }




    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}
