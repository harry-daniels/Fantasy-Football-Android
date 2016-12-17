package com.daniels.harry.assignment.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.activity.TeamEditorActivity;


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

        CardView b = (CardView)rootView.findViewById(R.id.btn_edit_team);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TeamEditorActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
