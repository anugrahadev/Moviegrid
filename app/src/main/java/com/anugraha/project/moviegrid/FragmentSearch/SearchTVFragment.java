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
import com.anugraha.project.moviegrid.Adapter.TVAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.MoviesResponse;
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
public class SearchTVFragment extends Fragment {
    private RecyclerView recyclerView;
    private TVAdapter tvAdapter;
    private List<TVResult> tvResultList;
    String query = "the";
    ProgressDialog progressDialog;
    Button cari;
    EditText cariform;

    public SearchTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);
        tvResultList = new ArrayList<>();
        cari = (Button) view.findViewById(R.id.btn_cari);
        cariform = (EditText) view.findViewById(R.id.editText);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
        tvAdapter = new TVAdapter(getContext(), tvResultList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tvAdapter);
        tvAdapter.notifyDataSetChanged();
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

    private void loadJSON() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getContext(),"API Token Invalid",Toast.LENGTH_SHORT).show();
                return;
            }
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<TVResponse> call = apiService.getSearchtv(BuildConfig.THE_MOVIE_DB_API_TOKEN,query);
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
