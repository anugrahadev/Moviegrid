package com.anugraha.project.moviegrid.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anugraha.project.moviegrid.FragmentMovies.PopularFragment;
import com.anugraha.project.moviegrid.FragmentMovies.ShowingFragment;
import com.anugraha.project.moviegrid.FragmentMovies.TopFragment;
import com.anugraha.project.moviegrid.FragmentMovies.UpcomingFragment;
import com.anugraha.project.moviegrid.FragmentTV.AiringTV;
import com.anugraha.project.moviegrid.FragmentTV.On_the_air;
import com.anugraha.project.moviegrid.FragmentTV.PopularTV;
import com.anugraha.project.moviegrid.FragmentTV.Top_TV;

public class PagerTV extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerTV(FragmentManager fm, int tabCount) {
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
                PopularTV tab1 = new PopularTV();
                return tab1;
            case 1:
                On_the_air tab2 = new On_the_air();
                return tab2;
            case 2:
                AiringTV tab3 = new AiringTV();
                return tab3;
            case 3:
                Top_TV tab4 = new Top_TV();
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