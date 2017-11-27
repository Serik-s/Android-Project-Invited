package com.example.serik.invited_app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.serik.invited_app.Fragments.EventsFragment;
import com.example.serik.invited_app.Fragments.ProfileFragment;
import com.example.serik.invited_app.Fragments.SearchFragment;

/**
 * Created by Serik on 27.11.2017.
 */

public class MainAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;
    private static String mainAdapterTAG = "MAIN ADAPTER";

    public MainAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        Log.e(mainAdapterTAG, "We are on method getItem and its position is " + position);
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return EventsFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return SearchFragment.newInstance();
            case 2:
                return ProfileFragment.newInstance();
            default:
                return EventsFragment.newInstance();
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
