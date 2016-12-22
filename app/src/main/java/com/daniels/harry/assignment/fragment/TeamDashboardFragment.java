package com.daniels.harry.assignment.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.activity.SignInActivity;
import com.daniels.harry.assignment.activity.TeamEditorActivity;
import com.daniels.harry.assignment.constant.Constants;
import com.daniels.harry.assignment.databinding.FragmentTeamDashboardBinding;
import com.daniels.harry.assignment.dialog.ConfirmDialogs;
import com.daniels.harry.assignment.handler.HttpRequestHandler;
import com.daniels.harry.assignment.jsonobject.AllTeamsJson;
import com.daniels.harry.assignment.jsonobject.LeagueTableJson;
import com.daniels.harry.assignment.mapper.FantasyTeamMapper;
import com.daniels.harry.assignment.singleton.CurrentUser;
import com.daniels.harry.assignment.viewmodel.FantasyTeamDashboardViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Date;


public class TeamDashboardFragment extends Fragment implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener, DialogInterface.OnClickListener, RequestQueue.RequestFinishedListener{

    public GoogleApiClient mGoogleApiClient;
    private HttpRequestHandler mRequestHandler;
    private FragmentTeamDashboardBinding mBinding;
    private CurrentUser mCurrentUser;
    private ProgressDialog mProgressDialog;
    private FantasyTeamDashboardViewModel mViewModel;

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

        mRequestHandler = new HttpRequestHandler(getActivity(), getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_dashboard, container, false);
        View rootView = mBinding.getRoot();

        CardView editTeambutton = (CardView)rootView.findViewById(R.id.btn_edit_team);
        CardView signOutbutton = (CardView) rootView.findViewById(R.id.btn_sign_out);

        editTeambutton.setOnClickListener(this);
        signOutbutton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRequestHandler.addRequestFinishedListener();
        mCurrentUser = CurrentUser.getInstance();
        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRequestHandler.removeRequestFinishedListener();
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_edit_team:
                Intent i = new Intent(getActivity(), TeamEditorActivity.class);
                startActivity(i);
                break;
            case R.id.btn_sign_out:
                ConfirmDialogs.showConfirmSignOutDialog(getActivity(), this);
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        signOut();
    }

    @Override
    public void onRequestFinished(Request request) {
        if (request.hasHadResponseDelivered()) {
            switch (request.getTag().toString()) {
                case Constants.REQUEST_TABLE:
                    mProgressDialog.dismiss();

                    LeagueTableJson json = (LeagueTableJson) mRequestHandler.getResultObject();
                    mViewModel = FantasyTeamMapper.jsonToViewModel(json, mCurrentUser.getFantasyTeam());

                    mCurrentUser.getFantasyTeam().lastUpdated = new Date();
                    mCurrentUser.getFantasyTeam().points = Integer.valueOf(mViewModel.getPoints());
                    mCurrentUser.getFantasyTeam().save();

                    mBinding.setViewmodel(mViewModel);
            }
        }
    }

    private void getData() {
        if(mCurrentUser.isAllPlayersSelected() && mRequestHandler.isNetworkConnected() /*&& mCurrentUser.getFantasyTeam().lastUpdated == null*/) {
            mRequestHandler.sendJsonObjectRequest(getString(R.string.league_table_api_endpoint),
                    Constants.REQUEST_TABLE,
                    LeagueTableJson.class);

            mProgressDialog = ProgressDialog.show(getActivity(),
                    getString(R.string.updating),
                    getString(R.string.please_wait),
                    true);
        } else {
            mBinding.setViewmodel(FantasyTeamMapper.modelToDashboardViewModel(mCurrentUser.getFantasyTeam()));
        }
    }
}
