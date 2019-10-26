package com.anugraha.project.moviegrid.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anugraha.project.moviegrid.FragmentSearch.SearchMovieFragment;
import com.anugraha.project.moviegrid.FragmentSearch.SearchPeopleFragment;
import com.anugraha.project.moviegrid.FragmentSearch.SearchTVFragment;
import com.anugraha.project.moviegrid.FragmentTV.AiringTV;
import com.anugraha.project.moviegrid.FragmentTV.On_the_air;
import com.anugraha.project.moviegrid.FragmentTV.PopularTV;
import com.anugraha.project.moviegrid.FragmentTV.Top_TV;

public class PagerSearch extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerSearch(FragmentManager fm, int tabCount) {
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
                SearchMovieFragment tab1 = new SearchMovieFragment();
                return tab1;
            case 1:
                SearchTVFragment tab2 = new SearchTVFragment();
                return tab2;
            case 2:
                SearchPeopleFragment tab3 = new SearchPeopleFragment();
                return tab3;
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