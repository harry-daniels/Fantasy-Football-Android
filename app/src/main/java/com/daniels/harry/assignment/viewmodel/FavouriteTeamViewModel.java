package com.daniels.harry.assignment.viewmodel;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class FavouriteTeamViewModel implements SortedListAdapter.ViewModel {

    private final String mName;
    private final int mDistance;

    public FavouriteTeamViewModel(String name, int distance) {
        mName = name;
        mDistance = distance;
    }

    public String getName() {
        return mName;
    }

    public int getDistance() {
        return mDistance;
    }

}
