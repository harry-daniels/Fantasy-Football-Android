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
import com.daniels.harry.assignment.activity.SignInActivity;
import com.daniels.harry.assignment.activity.TeamEditorActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class TeamDashboardFragment extends Fragment implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener{

    public GoogleApiClient mGoogleApiClient;

    public TeamDashboardFragment() {

    }

    public static TeamDashboardFragment newInstance() {
        TeamDashboardFragment fragment = new TeamDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_team_dashboard, container, false);

        CardView b = (CardView)rootView.findViewById(R.id.btn_edit_team);
        b.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_edit_team:
                Intent i = new Intent(getActivity(), TeamEditorActivity.class);
                startActivity(i);
                break;
            case R.id.btn_sign_out:
                signOut();
        }


    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent i = new Intent(getActivity(), SignInActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //TODO: Handle
    }
}
