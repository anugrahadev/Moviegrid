package com.anugraha.project.moviegrid.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anugraha.project.moviegrid.FragmentFavorite.FavoriteMovieFragment;
import com.anugraha.project.moviegrid.FragmentFavorite.FavoriteTVFragment;

public class PagerFavorite extends FragmentStatePagerAdapter {
    int tabCount;
    public PagerFavorite(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                FavoriteMovieFragment tab1 = new FavoriteMovieFragment();
                return tab1;
            case 1:
                FavoriteTVFragment tab2 = new FavoriteTVFragment();
                return tab2;

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