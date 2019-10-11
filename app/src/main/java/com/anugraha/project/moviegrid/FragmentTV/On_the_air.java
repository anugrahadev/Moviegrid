package com.anugraha.project.moviegrid.FragmentTV;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.TVAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
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
public class On_the_air extends Fragment {

    private RecyclerView recyclerView;
    private TVAdapter adapter;
    private List<TVResult> tvResultList;
    public On_the_air() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_showing, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        tvResultList=new ArrayList<>();
        adapter = new TVAdapter(getContext(), tvResultList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        }
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
            Call<TVResponse> call = apiService.getOTRTV(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<TVResponse>() {
                @Override
                public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                    List<TVResult> results = response.body().getResults();
                    recyclerView.setAdapter(new TVAdapter(getContext(), results));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<TVResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception err){
            Log.d("Error", err.getMessage());
            Toast.makeText(getActivity(), err.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}