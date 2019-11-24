package com.anugraha.project.moviegrid.FragmentTVDetail;

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
import com.anugraha.project.moviegrid.Adapter.TVDetailAdapter.TVSimilarAdapter;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.TVDetail.Result;
import com.anugraha.project.moviegrid.model.TVDetail.TVSimilarResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVSimilarFragment extends Fragment {
    private RecyclerView recyclerView;
    private TVSimilarAdapter adapter;
    private List<Result> similarResultsList;
    Integer movie_id;

    public TVSimilarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tvsimilar, container, false);
        movie_id = getActivity().getIntent().getExtras().getInt("id");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_similar);
        similarResultsList=new ArrayList<>();
        adapter = new TVSimilarAdapter(getContext(), similarResultsList);

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
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<TVSimilarResponse> call = apiService.getSimiliarTV(60625, "67317e16c17fe1dc7b1b5ba1e217d143");
            call.enqueue(new Callback<TVSimilarResponse>() {
                @Override
                public void onResponse(Call<TVSimilarResponse> call, Response<TVSimilarResponse> response) {
                    if (response.isSuccessful()){
                        similarResultsList=response.body().getResults();
                        recyclerView.setAdapter(new TVSimilarAdapter(getContext(), similarResultsList));
                        recyclerView.smoothScrollToPosition(0);
                    }
                }

                @Override
                public void onFailure(Call<TVSimilarResponse> call, Throwable t) {

                }
            });


    }

}