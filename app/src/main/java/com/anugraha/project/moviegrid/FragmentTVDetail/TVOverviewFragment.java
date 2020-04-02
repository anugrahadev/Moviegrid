package com.anugraha.project.moviegrid.FragmentTVDetail;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.CreditAdapter;
import com.anugraha.project.moviegrid.Adapter.CrewAdapter;
import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.BodyFavorite;
import com.anugraha.project.moviegrid.model.BodyWatchlist;
import com.anugraha.project.moviegrid.model.Cast;
import com.anugraha.project.moviegrid.model.CreditResponse;
import com.anugraha.project.moviegrid.model.Crew;
import com.anugraha.project.moviegrid.model.FavResponse;
import com.anugraha.project.moviegrid.model.MovieStateResponse;
import com.anugraha.project.moviegrid.model.MovieStateResponse2;
import com.anugraha.project.moviegrid.model.TVDetail.Network;
import com.anugraha.project.moviegrid.model.TVDetail.TVDetailResponse;
import com.anugraha.project.moviegrid.model.TVStateResponse;
import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVOverviewFragment extends Fragment {
    TextView tv_yourrate, tv_ratethismovie, tv_crew,tv_overview,tv_title, tv_season, tv_episode, tv_runtime, tv_status, tv_rating,tv_first_air_date, tv_last_episode,tv_production, tv_genre;
    ImageView iv_poster,iv_production;
    Integer id;
    String title;
    ProgressDialog progressDialog;
    RecyclerView rv_cast,rv_crew;
    SharedPrefManager sharedPrefManager;
    MaterialFavoriteButton materialFavoriteButton,watchlist_button;
    private List<Cast> castList;
    private CreditAdapter adaptercast;
    private List<Crew> crewList;
    private CrewAdapter adaptercrew;


    public TVOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tvoverview, container, false);
        sharedPrefManager = new SharedPrefManager(getContext());
        materialFavoriteButton  = (MaterialFavoriteButton) view.findViewById(R.id.favorite_button);
        watchlist_button = (MaterialFavoriteButton) view.findViewById(R.id.watchlist_button);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        tv_yourrate = (TextView) view.findViewById(R.id.tv_yourrate);
        tv_ratethismovie = (TextView) view.findViewById(R.id.tv_ratethismovie);
        tv_overview = (TextView) view.findViewById(R.id.tv_overview);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_season = (TextView) view.findViewById(R.id.tv_season);
        tv_episode = (TextView) view.findViewById(R.id.tv_episode);
        tv_runtime = (TextView) view.findViewById(R.id.tv_runtime);
        tv_rating = (TextView) view.findViewById(R.id.tv_rating);
        tv_first_air_date = (TextView) view.findViewById(R.id.tv_first_air_date);
        tv_last_episode = (TextView) view.findViewById(R.id.tv_last_episode);
        tv_genre = (TextView) view.findViewById(R.id.tv_genre);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_production = (TextView) view.findViewById(R.id.tv_production);
        tv_crew = (TextView) view.findViewById(R.id.tv_crew);
        iv_poster = (ImageView) view.findViewById(R.id.iv_poster);
        rv_cast = view.findViewById(R.id.rv_cast);
        rv_crew = view.findViewById(R.id.rv_crew);
        Intent startedIntent = getActivity().getIntent();
        id = startedIntent.getExtras().getInt("id");
        Service apiService = Client.getClient().create(Service.class);
        Call<TVDetailResponse> call = apiService.getTVDetail(id, BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSpLang());
        call.enqueue(new Callback<TVDetailResponse>() {
            @Override
            public void onResponse(Call<TVDetailResponse> call, Response<TVDetailResponse> response) {

                progressDialog.dismiss();
                Glide.with(getContext())
                        .load("https://image.tmdb.org/t/p/w300/"+response.body().getPosterPath())
                        .placeholder(R.drawable.load)
                        .into(iv_poster);
                title = response.body().getName();
                tv_title.setText(response.body().getName());
                tv_season.setText(response.body().getNumberOfSeasons().toString());
                tv_episode.setText(response.body().getNumberOfEpisodes().toString());
                tv_overview.setText(response.body().getOverview());

                if (response.body().getEpisodeRunTime().size()>1){
                    String firstruntime = response.body().getEpisodeRunTime().get(0).toString();
                    String lastruntime = response.body().getEpisodeRunTime().get(response.body().getEpisodeRunTime().size()-1).toString();
                    tv_runtime.setText(firstruntime+"-"+lastruntime+" Minutes");
                }else{
                    tv_runtime.setText(response.body().getEpisodeRunTime().get(0).toString()+" Minutes");
                }

                if (response.body().getVoteAverage() == 0 || response.body().getVoteAverage()==0.0){
                    tv_rating.setText("Not Rated");
                }else{
                    tv_rating.setText(" "+response.body().getVoteAverage().toString()+" / 10");
                }
                tv_first_air_date.setText(response.body().getFirstAirDate());
                tv_last_episode.setText(response.body().getLastAirDate());
                tv_status.setText(response.body().getStatus());

//                List<Network> networkList = response.body().getNetworks();
//                tv_production.setText(networkList.get(0).getName());
//                Glide.with(getContext())
//                        .load("https://image.tmdb.org/t/p/w92/"+networkList.get(0).getLogoPath())
//                        .into(iv_production);

            }

            @Override
            public void onFailure(Call<TVDetailResponse> call, Throwable t) {

            }
        });

        castList= new ArrayList<>();
        adaptercast = new CreditAdapter(getContext(), castList);
        RecyclerView.LayoutManager mLayoutManagerCast = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false);
        rv_cast.setLayoutManager(mLayoutManagerCast);
        rv_cast.setAdapter(adaptercast);
        rv_cast.setHasFixedSize(false);
        adaptercast.notifyDataSetChanged();
        loadJSONCast();

        crewList = new ArrayList<>();
        adaptercrew = new CrewAdapter(getContext(), crewList);
        RecyclerView.LayoutManager layoutManagerCrew = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
        rv_crew.setLayoutManager(layoutManagerCrew);
        rv_crew.setAdapter(adaptercrew);
        rv_crew.setHasFixedSize(false);
        adaptercrew.notifyDataSetChanged();
        loadJSONCrew();

        Service stateapiService = Client.getClient().create(Service.class);
        Call<TVStateResponse> callstate = stateapiService.getTVState(id, BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
        callstate.enqueue(new Callback<TVStateResponse>() {
            @Override
            public void onResponse(Call<TVStateResponse> call, Response<TVStateResponse> response) {
                tv_yourrate.setText(String.valueOf(response.body().getRated().getValue()));
                tv_yourrate.setBackgroundColor(Color.parseColor("#00D277"));
                if (response.isSuccessful()){
                    if (response.body().getFavorite()==true){
                        materialFavoriteButton.setFavorite(true);
                        materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                            @Override
                            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                Service apiService = Client.getClient().create(Service.class);
                                Call<FavResponse> call = apiService.markAsFavorite(new BodyFavorite("tv",id,false), BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                                call.enqueue(new Callback<FavResponse>() {
                                    @Override
                                    public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                        if (response.isSuccessful()){
                                            Toast.makeText(getContext(), "Removed from favorite!", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getContext(), "Failed to Removed from Favorite", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<FavResponse> call, Throwable t) {

                                    }
                                });
                            }
                        });
                    }else{
                        materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                            @Override
                            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                if (favorite){
                                    Service apiService = Client.getClient().create(Service.class);
                                    Call<FavResponse> call = apiService.markAsFavorite(new BodyFavorite("tv",id,true), BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                                    call.enqueue(new Callback<FavResponse>() {
                                        @Override
                                        public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                            if (response.isSuccessful()){
                                                Toast.makeText(getContext(), "Movie added to favorite!", Toast.LENGTH_SHORT).show();

                                            }else{
                                                Toast.makeText(getContext(), "Failed to Added to Favorite", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<FavResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        });
                    }

                    if(response.body().getWatchlist()==true){
                        watchlist_button.setFavorite(true);
                        watchlist_button.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                            @Override
                            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                Service apiService = Client.getClient().create(Service.class);
                                Call<FavResponse> call = apiService.markAsWatchlisted(new BodyWatchlist("tv",id,false), BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                                call.enqueue(new Callback<FavResponse>() {
                                    @Override
                                    public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                        if (response.isSuccessful()){
                                            Toast.makeText(getContext(), "Removed from watchlist!", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getContext(), "Failed to Removed from watchlist", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<FavResponse> call, Throwable t) {

                                    }
                                });
                            }
                        });
                    }else if (response.body().getWatchlist() == false){
                        watchlist_button.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                            @Override
                            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                if (favorite){
                                    Service apiService = Client.getClient().create(Service.class);
                                    Call<FavResponse> call = apiService.markAsWatchlisted(new BodyWatchlist("tv",id,true), BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                                    call.enqueue(new Callback<FavResponse>() {
                                        @Override
                                        public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                            if (response.isSuccessful()){
                                                Toast.makeText(getContext(), "Movie added to watchlist!", Toast.LENGTH_SHORT).show();

                                            }else{
                                                Toast.makeText(getContext(), "Failed to Added to watchlist", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<FavResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        });
                    }
                    if (response.body().getRated() != null){
                        tv_ratethismovie.setText(" Change your rating!");
                    }

                }else{
                    Toast.makeText(getContext(), "Failed to load states", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TVStateResponse> call, Throwable t) {

                Service stateapiService = Client.getClient().create(Service.class);
                Call<MovieStateResponse2> callstate = stateapiService.getTVStateObject(id, BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                callstate.enqueue(new Callback<MovieStateResponse2>() {
                    @Override
                    public void onResponse(Call<MovieStateResponse2> call, Response<MovieStateResponse2> response) {
                        if (response.isSuccessful()){
                            if (response.body().getFavorite()==true){
                                materialFavoriteButton.setFavorite(true);
                                materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                    @Override
                                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                        Service apiService = Client.getClient().create(Service.class);
                                        Call<FavResponse> call = apiService.markAsFavorite(new BodyFavorite("tv",id,false), BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                                        call.enqueue(new Callback<FavResponse>() {
                                            @Override
                                            public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                                if (response.isSuccessful()){
                                                    Toast.makeText(getContext(), "Removed from favorite!", Toast.LENGTH_SHORT).show();

                                                }else{
                                                    Toast.makeText(getContext(), "Failed to Removed from Favorite", Toast.LENGTH_SHORT).show();

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<FavResponse> call, Throwable t) {

                                            }
                                        });
                                    }
                                });
                            }else{
                                materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                    @Override
                                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                        if (favorite){
                                            Service apiService = Client.getClient().create(Service.class);
                                            Call<FavResponse> call = apiService.markAsFavorite(new BodyFavorite("tv",id,true), BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                                            call.enqueue(new Callback<FavResponse>() {
                                                @Override
                                                public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                                    if (response.isSuccessful()){
                                                        Toast.makeText(getContext(), "Movie added to favorite!", Toast.LENGTH_SHORT).show();

                                                    }else{
                                                        Toast.makeText(getContext(), "Failed to Added to Favorite", Toast.LENGTH_SHORT).show();

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<FavResponse> call, Throwable t) {

                                                }
                                            });
                                        }
                                    }
                                });
                            }

                            if(response.body().getWatchlist()==true){
                                watchlist_button.setFavorite(true);
                                watchlist_button.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                    @Override
                                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                        Service apiService = Client.getClient().create(Service.class);
                                        Call<FavResponse> call = apiService.markAsWatchlisted(new BodyWatchlist("tv",id,false), BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                                        call.enqueue(new Callback<FavResponse>() {
                                            @Override
                                            public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                                if (response.isSuccessful()){
                                                    Toast.makeText(getContext(), "Removed from watchlist!", Toast.LENGTH_SHORT).show();

                                                }else{
                                                    Toast.makeText(getContext(), "Failed to Removed from watchlist", Toast.LENGTH_SHORT).show();

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<FavResponse> call, Throwable t) {

                                            }
                                        });
                                    }
                                });
                            }else if (response.body().getWatchlist() == false){
                                watchlist_button.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                    @Override
                                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                        if (favorite){
                                            Service apiService = Client.getClient().create(Service.class);
                                            Call<FavResponse> call = apiService.markAsWatchlisted(new BodyWatchlist("tv",id,true), BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession());
                                            call.enqueue(new Callback<FavResponse>() {
                                                @Override
                                                public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                                    if (response.isSuccessful()){
                                                        Toast.makeText(getContext(), "Movie added to watchlist!", Toast.LENGTH_SHORT).show();

                                                    }else{
                                                        Toast.makeText(getContext(), "Failed to Added to watchlist", Toast.LENGTH_SHORT).show();

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<FavResponse> call, Throwable t) {

                                                }
                                            });
                                        }
                                    }
                                });
                            }

                        }else{
                            Toast.makeText(getContext(), "Failed to load states", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<MovieStateResponse2> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to load json", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        tv_ratethismovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                builder.setTitle("Rate : "+title);
                View dialogLayout = inflater.inflate(R.layout.rate, null);
                final RatingBar ratingBar = dialogLayout.findViewById(R.id.ratingBar);
                builder.setView(dialogLayout);
                builder.setPositiveButton("SUMBIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Service apiService = Client.getClient().create(Service.class);
                        Call<FavResponse> call = apiService.postTVRate(id,BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession(), ratingBar.getRating());
                        call.enqueue(new Callback<FavResponse>() {
                            @Override
                            public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                                if (response.isSuccessful()){
                                    tv_yourrate.setText(String.valueOf(ratingBar.getRating()));
                                    tv_yourrate.setBackgroundColor(Color.parseColor("#00D277"));
                                    tv_ratethismovie.setText(" Change your rating!");
                                    Toast.makeText(getContext(), "Rating is " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<FavResponse> call, Throwable t) {

                            }
                        });

                    }
                });
                builder.show();

            }
        });



        return view;
    }

    private void loadJSONCrew() {
        Service apiService = Client.getClient().create(Service.class);
        Call<CreditResponse> call = apiService.getCreditstv(id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<CreditResponse>() {

            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                crewList = response.body().getCrew();
                rv_crew.setAdapter(new CrewAdapter(getContext(), crewList));
                if (crewList.size()>1){
                    tv_crew.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {

            }
        });
    }

    private void loadJSONCast() {
        Service apiService = Client.getClient().create(Service.class);
        Call<CreditResponse> call = apiService.getCreditstv(id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                castList = response.body().getCast();
                rv_cast.setAdapter(new CreditAdapter(getContext(), castList));
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {

            }
        });
    }

}
