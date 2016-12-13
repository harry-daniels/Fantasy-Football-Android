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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.databinding.FragmentFavouriteDashboardBinding;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.User;
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
    private static final String REQUEST_NEXT_CREST = "req_nc";
    private static final String REQUEST_PREV_CREST = "req_pc";

    private String mTeamApiEndpoint = "http://fifabuddy.net/api/team/";
    private String mNextFixtureApiEndpoint = "http://api.football-data.org/v1/teams/###/fixtures?timeFrame=n10";
    private String mPrevFixtureApiEndpoint = "http://api.football-data.org/v1/teams/###/fixtures?timeFrame=p10";
    private String mNextCrestApiEndpoint = "http://fifabuddy.net/api/Crest/";
    private String mPrevCrestApiEndpoint = "http://fifabuddy.net/api/Crest/";
    private String mPositionApiEndpoint = "http://api.football-data.org/v1/competitions/426/leagueTable";

    private RequestQueue mRequestQueue;
    private FragmentFavouriteDashboardBinding mBinding;
    private FavouriteTeamViewModel mFavouriteTeamVm;
    private User mCurrentUser;

    private JsonObjectRequest mTeamRequest, mPositionRequest, mPrevFixtureRequest, mNextFixtureRequest;
    private StringRequest mPrevCrestRequest, mNextCrestRequest;

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

        mCurrentUser = User.first(User.class);

        mTeamApiEndpoint += mCurrentUser.favouriteTeam.apiId;
        mNextFixtureApiEndpoint = mNextFixtureApiEndpoint.replace("###", mCurrentUser.favouriteTeam.apiId);
        mPrevFixtureApiEndpoint = mPrevFixtureApiEndpoint.replace("###", mCurrentUser.favouriteTeam.apiId);

        mRequestQueue = Volley.newRequestQueue(getActivity());
        handleHttpRequests();

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_dashboard, container, false);
        View view = mBinding.getRoot();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void handleHttpRequests(){

        createTeamRequest();
        createPositionRequest();
        createFixtureRequests();

        mRequestQueue.addRequestFinishedListener(this);
        mRequestQueue.add(mTeamRequest);
    }

    public void createPositionRequest()
    {
        final String teamName = mCurrentUser.favouriteTeam.name;

        mPositionRequest = new JsonObjectRequest(Request.Method.GET, mPositionApiEndpoint, null,
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
                                    mFavouriteTeamVm.setPoints(standing.getString("points"));
                                    mFavouriteTeamVm.setWins(standing.getString("wins"));
                                    mFavouriteTeamVm.setDraws(standing.getString("draws"));
                                    mFavouriteTeamVm.setLosses(standing.getString("losses"));
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

    }

    public void createTeamRequest()
    {
        mTeamRequest = new JsonObjectRequest(Request.Method.GET, mTeamApiEndpoint, null,
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
        mTeamRequest.setTag(REQUEST_TEAM);
    }

    public void createFixtureRequests()
    {
        mPrevFixtureRequest = new JsonObjectRequest(Request.Method.GET, mPrevFixtureApiEndpoint, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray fixtures = response.getJSONArray("fixtures");
                            JSONObject fixture = fixtures.getJSONObject(fixtures.length() - 1);
                            FixtureViewModel vm = LoganSquare.parse(fixture.toString(), FixtureViewModel.class);
                            JSONObject result = fixture.getJSONObject("result");
                            vm.setHomeScore(result.getInt("goalsHomeTeam"));
                            vm.setAwayScore(result.getInt("goalsAwayTeam"));
                            mFavouriteTeamVm.setPrevFixture(vm);
                            mFavouriteTeamVm.calculateDetails();
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

        mNextFixtureRequest = new JsonObjectRequest(Request.Method.GET, mNextFixtureApiEndpoint, null,
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
    }

    public void createCrestRequests()
    {
        mNextCrestRequest = new StringRequest(Request.Method.GET, mNextCrestApiEndpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mFavouriteTeamVm.getNextFixture().setCrestUrl(response.substring(1, response.length() -1));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Add error handling
            }
        });
        mNextCrestRequest.setTag(REQUEST_NEXT_CREST);

        mPrevCrestRequest = new StringRequest(Request.Method.GET, mPrevCrestApiEndpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mFavouriteTeamVm.getPrevFixture().setCrestUrl(response.substring(1, response.length() -1));
                        mBinding.setViewmodel(mFavouriteTeamVm);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Add error handling
            }
        });
        mPrevCrestRequest.setTag(REQUEST_PREV_CREST);
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
            case REQUEST_PREV_FIXTURE :
                mNextCrestApiEndpoint += mFavouriteTeamVm.getNextFixture().getOppositionName().replace(" ", "%20");
                mPrevCrestApiEndpoint += mFavouriteTeamVm.getPrevFixture().getOppositionName().replace(" ", "%20");
                createCrestRequests();
                mRequestQueue.add(mNextCrestRequest);
                break;
            case REQUEST_NEXT_CREST :
                mRequestQueue.add(mPrevCrestRequest);
                break;
        }
    }

    //TODO: Move Api Key
    public Map<String, String> getHttpHeaders()
    {
        Map<String, String>  params = new HashMap<>();
        params.put("X-Auth-Token", "debf9352e2b745759c3eb424fc776d6d");
        return params;
    }
}
