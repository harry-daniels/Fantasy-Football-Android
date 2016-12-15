package com.daniels.harry.assignment.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.databinding.FragmentFavouriteDashboardBinding;
import com.daniels.harry.assignment.handler.HttpRequestHandler;
import com.daniels.harry.assignment.jsonobject.FavouriteTeamJson;
import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.LeagueTableJson;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.User;
import com.daniels.harry.assignment.singleton.CurrentUser;
import com.daniels.harry.assignment.singleton.HttpRequestQueue;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamViewModel;
import com.daniels.harry.assignment.viewmodel.FixtureViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.R.attr.type;


public class FavouriteDashboardFragment <T> extends Fragment implements RequestQueue.RequestFinishedListener {

    private   String REQUEST_TEAM = getString(R.string.team_request_tag);
    private   String REQUEST_POSITION = getString(R.string.position_request_tag);
    private   String REQUEST_NEXT_FIXTURE = getString(R.string.next_fixture_request_tag);
    private   String REQUEST_PREV_FIXTURE = getString(R.string.prev_fixture_request_tag);
    private   String REQUEST_CREST = getString(R.string.crest_request_tag);

    private String mNextFixtureApiEndpoint = getString(R.string.fixture_api_endpoint);
    private String mNextCrestApiEndpoint = "http://fifabuddy.net/api/Crest/";
    private String mPrevCrestApiEndpoint = "http://fifabuddy.net/api/Crest/";
    private String mPositionApiEndpoint = getString(R.string.league_table_api_endpoint);

    private String mTeamApiUrl, mNextCrestApiUrl, mPrevCrestApiUrl, mNextFixtureApiUrl, mPrevFixtureApiUrl;

    private boolean mIsTeamChosen;

    private HttpRequestHandler mRequestHandler;
    private FragmentFavouriteDashboardBinding mBinding;
    private CurrentUser mCurrentUser;

    private LinearLayout mAvailableLayout, mPlaceholderLayout;

    public static FavouriteDashboardFragment newInstance() {
        FavouriteDashboardFragment fragment = new FavouriteDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRequestHandler = new HttpRequestHandler(getActivity(), this);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_dashboard, container, false);
        View rootView = mBinding.getRoot();

        mAvailableLayout = (LinearLayout)rootView.findViewById(R.id.layout_favourite_available);
        mPlaceholderLayout = (LinearLayout)rootView.findViewById(R.id.layout_favourite_placeholder);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mCurrentUser = CurrentUser.getInstance();

        checkIsTeamChosen();
        setVisibility();

        if (mIsTeamChosen) {
            mTeamApiUrl = getString(R.string.team_api_endpoint) + mCurrentUser.getFavouriteTeam().apiId;
            handleHttpRequests();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mRequestHandler.removeRequestFinishedListener();
    }

    private void handleHttpRequests() {
//        createTeamRequest();
//        createPositionRequest();
//        createFixtureRequests();
//
//        mRequestQueue.addRequest(mTeamRequest, getActivity());

        mRequestHandler.sendJsonObjectRequest(mTeamApiUrl, REQUEST_TEAM, FavouriteTeamJson.class);
    }

//    public void createCrestRequests() {
//        mNextCrestRequest = new StringRequest(Request.Method.GET, mNextCrestUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        mFavouriteTeamVm.getNextFixture().setCrestUrl(response.substring(1, response.length() - 1));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //TODO: Add error handling
//            }
//        });
//        mNextCrestRequest.setTag(REQUEST_NEXT_CREST);
//
//        mPrevCrestRequest = new StringRequest(Request.Method.GET, mPrevCrestUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        mFavouriteTeamVm.getPrevFixture().setCrestUrl(response.substring(1, response.length() - 1));
//                        mBinding.setViewmodel(mFavouriteTeamVm);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //TODO: Add error handling
//            }
//        });
//        mPrevCrestRequest.setTag(REQUEST_PREV_CREST);
//    }

    @Override
    public void onRequestFinished(Request request) {
        switch (request.getTag().toString()) {
            case REQUEST_TEAM:
                mFavouriteTeamJson = (FavouriteTeamJson)mRequestHandler.getResultObject();
                break;
            case REQUEST_POSITION:
                mLeagueTableJson = (LeagueTableJson)mRequestHandler.getResultObject();
                break;
            case REQUEST_NEXT_FIXTURE:
                break;
            case REQUEST_PREV_FIXTURE:
//                mNextCrestUrl = mNextCrestApiEndpoint + mFavouriteTeamVm.getNextFixture().getOppositionName().replace(" ", "%20");
//                mPrevCrestUrl = mPrevCrestApiEndpoint + mFavouriteTeamVm.getPrevFixture().getOppositionName().replace(" ", "%20");
//                createCrestRequests();
                break;
            case REQUEST_NEXT_CREST:
                break;
        }
    }

    private void checkIsTeamChosen() {
        if (mCurrentUser.getFavouriteTeam() != null) {
            mIsTeamChosen = true;
        } else {
            mIsTeamChosen = false;
        }
    }

    private void setVisibility()
    {
        if (mIsTeamChosen){
            mAvailableLayout.setVisibility(View.VISIBLE);
            mPlaceholderLayout.setVisibility(View.GONE);
        } else {
            mAvailableLayout.setVisibility(View.GONE);
            mPlaceholderLayout.setVisibility(View.VISIBLE);
        }
    }
}
