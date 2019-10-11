package com.anugraha.project.moviegrid.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anugraha.project.moviegrid.FragmentMovies.PopularFragment;
import com.anugraha.project.moviegrid.FragmentMovies.ShowingFragment;
import com.anugraha.project.moviegrid.FragmentMovies.TopFragment;
import com.anugraha.project.moviegrid.FragmentMovies.UpcomingFragment;

public class PagerMovies extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerMovies(FragmentManager fm, int tabCount) {
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
                ShowingFragment tab1 = new ShowingFragment();
                return tab1;
            case 1:
                PopularFragment tab2 = new PopularFragment();
                return tab2;
            case 2:
                TopFragment tab3 = new TopFragment();
                return tab3;
            case 3:
                UpcomingFragment tab4 = new UpcomingFragment();
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