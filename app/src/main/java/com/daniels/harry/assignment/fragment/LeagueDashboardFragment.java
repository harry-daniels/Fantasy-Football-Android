package com.daniels.harry.assignment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniels.harry.assignment.R;


public class LeagueDashboardFragment extends Fragment {

    // Overshot expectations for the app and didn't have time to implement the league functionality
    // as it would require writing a new post api endpoint for my external database, otherwise leagues would just be local on a
    // single device and therefore pretty much pointless! something to look into in the future.


    public LeagueDashboardFragment() {

    }

    public static LeagueDashboardFragment newInstance() {
        LeagueDashboardFragment fragment = new LeagueDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_league_dashboard, container, false);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
