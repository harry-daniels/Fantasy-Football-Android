package com.daniels.harry.assignment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniels.harry.assignment.R;


public class LeagueDashboardFragment extends Fragment {

    private static final String KEY_TAB_INDEX = "tab_index";

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
