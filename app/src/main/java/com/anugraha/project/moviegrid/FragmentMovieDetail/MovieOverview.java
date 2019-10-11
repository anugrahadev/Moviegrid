package com.anugraha.project.moviegrid.FragmentMovieDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anugraha.project.moviegrid.Activity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieOverview extends Fragment {


    public MovieOverview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_overview, container, false);
    }

}
