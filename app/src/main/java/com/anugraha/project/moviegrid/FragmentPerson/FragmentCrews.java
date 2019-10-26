package com.anugraha.project.moviegrid.FragmentPerson;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.MoviesAdapter;
import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.PeopleCreditAdapter.CastAdapter;
import com.anugraha.project.moviegrid.Adapter.PeopleCreditAdapter.CrewAdapter;
import com.anugraha.project.moviegrid.Adapter.PeopleCreditAdapter.CrewCombinedAdapter;
import com.anugraha.project.moviegrid.Adapter.TVAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.MoviesResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.Cast;
import com.anugraha.project.moviegrid.model.PeopleModel.CastResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.Crew;
import com.anugraha.project.moviegrid.model.PeopleModel.CrewCombined;
import com.anugraha.project.moviegrid.model.PeopleModel.CrewCombinedResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CrewResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.PeopleCreditResponse;
import com.anugraha.project.moviegrid.model.TVResponse;
import com.anugraha.project.moviegrid.model.TVResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCrews extends Fragment {
    Integer id;
    private RecyclerView recyclerView;
    private CrewCombinedAdapter adapter;
    private List<CrewCombined> movieList;
    public FragmentCrews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_casts, container, false);
        Intent intentStarted = getActivity().getIntent();
        id = getActivity().getIntent().getExtras().getInt("id");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        movieList=new ArrayList<>();
        adapter = new CrewCombinedAdapter(getContext(), movieList);

        RecyclerView.LayoutManager mLayoutManagerCast = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManagerCast);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadJSON();
        return view;
    }

    private void loadJSON() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getContext(),"API Token Invalid",Toast.LENGTH_SHORT).show();
                return;
            }
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<CrewCombinedResponse> call = apiService.getcombined_creditscrew(id,BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<CrewCombinedResponse>() {
                @Override
                public void onResponse(Call<CrewCombinedResponse> call, Response<CrewCombinedResponse> response) {
                    movieList = response.body().getCrew();
                    recyclerView.setAdapter(new CrewCombinedAdapter(getContext(), movieList));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<CrewCombinedResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getContext(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception err){
            Log.d("Error", err.getMessage());
            Toast.makeText(getContext(), err.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
