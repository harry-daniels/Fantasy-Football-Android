package com.daniels.harry.assignment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daniels.harry.assignment.fragment.FavouriteDashboardFragment;
import com.daniels.harry.assignment.fragment.LeagueDashboardFragment;
import com.daniels.harry.assignment.fragment.TeamDashboardFragment;

public class DashboardViewPagerAdapter extends FragmentPagerAdapter {

    public DashboardViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        Fragment page = TeamDashboardFragment.newInstance();

        switch (index)
        {
            case 0 :
                page = TeamDashboardFragment.newInstance();
                break;
            case 1 :
                page = LeagueDashboardFragment.newInstance();
                break;
            case 2 :
                page = FavouriteDashboardFragment.newInstance();
                break;
        }

        return page;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int index) {

        String title = "";

        switch (index)
        {
            case 0 :
                title = "Team";
                break;
            case 1 :
                title = "Leagues";
                break;
            case 2 :
                title = "Favourite";
                break;
        }

        return title;
    }
}
