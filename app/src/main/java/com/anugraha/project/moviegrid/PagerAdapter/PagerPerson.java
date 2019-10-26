package com.anugraha.project.moviegrid.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anugraha.project.moviegrid.FragmentPerson.FragmentCasts;
import com.anugraha.project.moviegrid.FragmentPerson.FragmentCrews;
import com.anugraha.project.moviegrid.FragmentPerson.Overview;

public class PagerPerson extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerPerson(FragmentManager fm, int tabCount) {
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
                Overview tab1 = new Overview();
                return tab1;
            case 1:
                FragmentCasts tab2 = new FragmentCasts();
                return tab2;
            case 2:
                FragmentCrews tab3 = new FragmentCrews();
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