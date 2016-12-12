package com.daniels.harry.assignment.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.databinding.FragmentFavouriteDashboardBinding;
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


public class FavouriteDashboardFragment extends Fragment implements RequestQueue.RequestFinishedListener {

    private static final String REQUEST_TEAM = "req_team";
    private static final String REQUEST_POSITION = "req_pos";
    private static final String REQUEST_NEXT_FIXTURE = "req_nf";
    private static final String REQUEST_PREV_FIXTURE = "req_pf";

    private RequestQueue mRequestQueue;
    private FragmentFavouriteDashboardBinding mBinding;
    private FavouriteTeamViewModel mFavouriteTeamVm;

    private JsonObjectRequest mPositionRequest, mPrevFixtureRequest, mNextFixtureRequest;

    public FavouriteDashboardFragment() {

    }

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

        mRequestQueue = Volley.newRequestQueue(getActivity());
        handleHttpRequest();

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_dashboard, container, false);
        View view = mBinding.getRoot();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void handleHttpRequest(){
        //TODO: Link with SQLdb
        final String teamName = "Liverpool FC";

        //TODO: Move URL strings to string resources
        String teamUrl = "http://fifabuddy.net/api/team/64";
        String nextFixtureUrl = "http://api.football-data.org/v1/teams/64/fixtures?timeFrame=n10";
        String prevFixtureUrl = "http://api.football-data.org/v1/teams/64/fixtures?timeFrame=p10";
        String positionUrl = "http://api.football-data.org/v1/competitions/426/leagueTable";

        mPrevFixtureRequest = new JsonObjectRequest(Request.Method.GET, prevFixtureUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray fixtures = response.getJSONArray("fixtures");
                            JSONObject fixture = fixtures.getJSONObject(fixtures.length() - 1);
                            FixtureViewModel vm = LoganSquare.parse(fixture.toString(), FixtureViewModel.class);
                            mFavouriteTeamVm.setPrevFixture(vm);
                            mFavouriteTeamVm.calculateDetails();
                            mBinding.setViewmodel(mFavouriteTeamVm);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Add error handling
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };
        mPrevFixtureRequest.setTag(REQUEST_PREV_FIXTURE);

        mNextFixtureRequest = new JsonObjectRequest(Request.Method.GET, nextFixtureUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray fixtures = response.getJSONArray("fixtures");
                            for (int i = 0; i < fixtures.length(); i++) {
                                JSONObject fixture = fixtures.getJSONObject(i);
                                if (!Objects.equals(fixture.getString("status"), "FINISHED"))
                                {
                                    FixtureViewModel vm = LoganSquare.parse(fixture.toString(), FixtureViewModel.class);
                                    mFavouriteTeamVm.setNextFixture(vm);
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Add error handling
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };
        mNextFixtureRequest.setTag(REQUEST_NEXT_FIXTURE);

        mPositionRequest = new JsonObjectRequest(Request.Method.GET, positionUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray standings = response.getJSONArray("standing");
                            for (int i = 0; i < standings.length(); i++) {
                                JSONObject standing = standings.getJSONObject(i);
                                if (Objects.equals(standing.getString("teamName"), teamName))
                                {
                                    mFavouriteTeamVm.setPosition(standing.getString("position"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Add error handling
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };
        mPositionRequest.setTag(REQUEST_POSITION);

        JsonObjectRequest teamRequest = new JsonObjectRequest(Request.Method.GET, teamUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mFavouriteTeamVm = LoganSquare.parse(response.toString(), FavouriteTeamViewModel.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Add error handling
            }
        });
        teamRequest.setTag(REQUEST_TEAM);

        mRequestQueue.addRequestFinishedListener(this);
        mRequestQueue.add(teamRequest);
    }

    @Override
    public void onRequestFinished(Request request) {
        switch (request.getTag().toString())
        {
            case REQUEST_TEAM :
                mRequestQueue.add(mPositionRequest);
                break;
            case REQUEST_POSITION :
                mRequestQueue.add(mNextFixtureRequest);
                break;
            case REQUEST_NEXT_FIXTURE :
                mRequestQueue.add(mPrevFixtureRequest);
                break;
        }
    }

    public Map<String, String> getHttpHeaders()
    {
        Map<String, String>  params = new HashMap<>();
        params.put("X-Auth-Token", "debf9352e2b745759c3eb424fc776d6d");
        return params;
    }
}
