package com.daniels.harry.assignment.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniels.harry.assignment.R;


public class TeamDashboardFragment extends Fragment {

    public TeamDashboardFragment() {

    }

    public static TeamDashboardFragment newInstance() {
        TeamDashboardFragment fragment = new TeamDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_team_dashboard, container, false);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
