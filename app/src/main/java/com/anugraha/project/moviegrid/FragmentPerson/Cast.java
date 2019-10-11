package com.anugraha.project.moviegrid.FragmentPerson;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anugraha.project.moviegrid.Activity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cast extends Fragment {


    public Cast() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cast, container, false);
    }

}
