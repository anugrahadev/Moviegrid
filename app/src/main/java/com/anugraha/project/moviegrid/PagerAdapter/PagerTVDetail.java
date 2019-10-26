package com.anugraha.project.moviegrid.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anugraha.project.moviegrid.FragmentMovieDetail.MovieOverview;
import com.anugraha.project.moviegrid.FragmentMovieDetail.MovieReviews;
import com.anugraha.project.moviegrid.FragmentMovieDetail.MovieVideos;
import com.anugraha.project.moviegrid.FragmentMovieDetail.SimilarMovies;
import com.anugraha.project.moviegrid.FragmentTVDetail.TVOverviewFragment;
import com.anugraha.project.moviegrid.FragmentTVDetail.TVSeasonFragment;
import com.anugraha.project.moviegrid.FragmentTVDetail.TVSimilarFragment;
import com.anugraha.project.moviegrid.FragmentTVDetail.TVvideosFragment;

public class PagerTVDetail extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerTVDetail(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                TVOverviewFragment tab1 = new TVOverviewFragment();
                return tab1;
            case 1:
                TVvideosFragment tab2 = new TVvideosFragment();
                return tab2;
            case 2:
                TVSeasonFragment tab3 = new TVSeasonFragment();
                return tab3;
            case 3:
                TVSimilarFragment tab4 = new TVSimilarFragment();
                return tab4;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}