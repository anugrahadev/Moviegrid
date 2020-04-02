package com.anugraha.project.moviegrid.FragmentRated;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.MoviesAdapter;
import com.anugraha.project.moviegrid.Adapter.PaginationAdapter;
import com.anugraha.project.moviegrid.Adapter.RatedAdapter;
import com.anugraha.project.moviegrid.Adapter.RatedTVAdapter;
import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.TVResponse;
import com.anugraha.project.moviegrid.model.TVResult;
import com.anugraha.project.moviegrid.utils.PaginationScrollListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatedTVFragment extends Fragment {

    RatedTVAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    SharedPrefManager sharedPrefManager;

    RecyclerView rv;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES=1;
    private int currentPage = PAGE_START;

    private Service movieService;
    ProgressDialog progressDialog;
    public  static final String TAG = MoviesAdapter.class.getName();
    public RatedTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rated_movie, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());
        rv = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new RatedTVAdapter(getContext());

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            linearLayoutManager = new GridLayoutManager(getContext(),1);
            rv.setLayoutManager(linearLayoutManager);

        }else{
            linearLayoutManager = new GridLayoutManager(getContext(),1);
            rv.setLayoutManager(linearLayoutManager);
        }


        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);




        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //init service and load data
        movieService = Client.getClient().create(Service.class);

        loadFirstPage();

        return view;
    }
    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");

        callTopRatedMoviesApi().enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                // Got data. Send it to adapter
                TOTAL_PAGES = response.body().getTotalPages();
                List<TVResult> results = fetchResults(response);
                adapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    /**
     * @param response extracts List<{@link TVResult>} from response
     * @return
     */
    private List<TVResult> fetchResults(Response<TVResponse> response) {
        TVResponse topRatedMovies = response.body();
        return topRatedMovies.getResults();
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        callTopRatedMoviesApi().enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<TVResult> results = fetchResults(response);
                adapter.addAll(results);

                if (TOTAL_PAGES==1){
                    isLastPage = true;
                }else if (currentPage != TOTAL_PAGES){
                    adapter.addLoadingFooter();
                }else {
                    isLastPage = true;
                }

            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });
    }


    /**
     * Performs a Retrofit call to the top rated movies API.
     * Same API call for Pagination.
     * As {@link #currentPage} will be incremented automatically
     * by @{@link PaginationScrollListener} to load next page.
     */
    private Call<TVResponse> callTopRatedMoviesApi() {
        return movieService.getRatedTV(
                BuildConfig.THE_MOVIE_DB_API_TOKEN, sharedPrefManager.getSPSession(), "created_at.desc",
                currentPage
        );
    }


}