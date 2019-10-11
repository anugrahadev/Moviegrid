package com.anugraha.project.moviegrid.FragmentDrawer;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.anugraha.project.moviegrid.Adapter.MoviesAdapter;
import com.anugraha.project.moviegrid.Adapter.PersonAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.MoviesResponse;
import com.anugraha.project.moviegrid.model.PersonKnownFor;
import com.anugraha.project.moviegrid.model.PersonResponse;
import com.anugraha.project.moviegrid.model.PersonResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {

    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<PersonResult> personResultList;
    public PersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_person);

        personResultList=new ArrayList<>();
        adapter = new PersonAdapter(getContext(), personResultList);

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
            Call<PersonResponse> call = apiService.getPopularPerson(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<PersonResponse>() {
                @Override
                public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {
                    List<PersonResult> personResults = response.body().getResults();
                    recyclerView.setAdapter(new PersonAdapter(getContext(), personResults));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<PersonResponse> call, Throwable t) {
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
