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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.CreditAdapter;
import com.anugraha.project.moviegrid.Adapter.CrewAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.Cast;
import com.anugraha.project.moviegrid.model.CreditResponse;
import com.anugraha.project.moviegrid.model.Crew;
import com.anugraha.project.moviegrid.model.TVDetail.Network;
import com.anugraha.project.moviegrid.model.TVDetail.TVDetailResponse;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVOverviewFragment extends Fragment {
    TextView tv_crew,tv_overview,tv_title, tv_season, tv_episode, tv_runtime, tv_status, tv_rating,tv_first_air_date, tv_last_episode,tv_production, tv_genre;
    ImageView iv_poster,iv_production;
    Integer id;
    ProgressDialog progressDialog;
    RecyclerView rv_cast,rv_crew;
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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
        iv_production = (ImageView) view.findViewById(R.id.iv_production);
        rv_cast = view.findViewById(R.id.rv_cast);
        rv_crew = view.findViewById(R.id.rv_crew);
        Intent startedIntent = getActivity().getIntent();
        id = startedIntent.getExtras().getInt("id");
        Service apiService = Client.getClient().create(Service.class);
        Call<TVDetailResponse> call = apiService.getTVDetail(id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<TVDetailResponse>() {
            @Override
            public void onResponse(Call<TVDetailResponse> call, Response<TVDetailResponse> response) {
                progressDialog.dismiss();
                Glide.with(getContext())
                        .load("https://image.tmdb.org/t/p/w300/"+response.body().getPosterPath())
                        .placeholder(R.drawable.load)
                        .into(iv_poster);
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

                tv_rating.setText(response.body().getVoteAverage().toString());
                tv_first_air_date.setText(response.body().getFirstAirDate());
                tv_last_episode.setText(response.body().getLastAirDate());
                tv_status.setText(response.body().getStatus());

                List<Network> networkList = response.body().getNetworks();
                tv_production.setText(networkList.get(0).getName());
                Glide.with(getContext())
                        .load("https://image.tmdb.org/t/p/w92/"+networkList.get(0).getLogoPath())
                        .into(iv_production);



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
