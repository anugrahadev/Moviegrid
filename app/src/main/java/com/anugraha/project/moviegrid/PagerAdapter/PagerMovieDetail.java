package com.anugraha.project.moviegrid.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anugraha.project.moviegrid.FragmentMovieDetail.MovieOverview;
import com.anugraha.project.moviegrid.FragmentMovieDetail.MovieReviews;
import com.anugraha.project.moviegrid.FragmentMovieDetail.MovieVideos;
import com.anugraha.project.moviegrid.FragmentMovieDetail.SimilarMovies;

public class PagerMovieDetail extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerMovieDetail(FragmentManager fm, int tabCount) {
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
                MovieOverview tab1 = new MovieOverview();
                return tab1;
            case 1:
                MovieVideos tab2 = new MovieVideos();
                return tab2;
            case 2:
                MovieReviews tab3 = new MovieReviews();
                return tab3;
            case 3:
                SimilarMovies tab4 = new SimilarMovies();
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