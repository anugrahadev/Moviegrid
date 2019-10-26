package com.anugraha.project.moviegrid.FragmentSearch;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.MoviesAdapter;
import com.anugraha.project.moviegrid.Adapter.PersonAdapter;
import com.anugraha.project.moviegrid.Adapter.TVAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.MoviesResponse;
import com.anugraha.project.moviegrid.model.PersonResponse;
import com.anugraha.project.moviegrid.model.PersonResult;
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
public class SearchPeopleFragment extends Fragment {
    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private List<PersonResult> personResultList;
    String query = "chris";
    ProgressDialog progressDialog;
    Button cari;
    EditText cariform;

    public SearchPeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);
        personResultList = new ArrayList<>();
        cari = (Button) view.findViewById(R.id.btn_cari);
        cariform = (EditText) view.findViewById(R.id.editText);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
        personAdapter = new PersonAdapter(getContext(), personResultList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(personAdapter);
        personAdapter.notifyDataSetChanged();
        loadJSON();
        cariform.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    query = " ";
                } else {
                    query = cariform.getText().toString();
                    loadJSON();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        return view;
    }

    private void loadJSON(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getContext(),"API Token Invalid",Toast.LENGTH_SHORT).show();
                return;
            }
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<PersonResponse> call = apiService.getSearchperson(BuildConfig.THE_MOVIE_DB_API_TOKEN,query);
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
