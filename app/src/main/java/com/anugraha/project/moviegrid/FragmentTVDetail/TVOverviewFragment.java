package com.anugraha.project.moviegrid.FragmentTVDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anugraha.project.moviegrid.Activity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVOverviewFragment extends Fragment {


    public TVOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvoverview, container, false);
    }

}
