package com.daniels.harry.assignment.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.databinding.FragmentFavouriteDashboardBinding;
import com.daniels.harry.assignment.handler.HttpRequestHandler;
import com.daniels.harry.assignment.jsonobject.CrestsJson;
import com.daniels.harry.assignment.jsonobject.FavouriteTeamJson;
import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.LeagueTableJson;
import com.daniels.harry.assignment.jsonobject.MatchdayJson;
import com.daniels.harry.assignment.mapper.FavouriteTeamMapper;
import com.daniels.harry.assignment.mapper.FixtureMapper;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Fixture;
import com.daniels.harry.assignment.singleton.CurrentUser;
import com.daniels.harry.assignment.util.UrlBuilders;


public class FavouriteDashboardFragment extends Fragment implements RequestQueue.RequestFinishedListener {

    private static final String REQUEST_TEAM = "req_team";
    private static final String REQUEST_POSITION = "req_pos";
    private static final String REQUEST_PREV_FIXTURE = "req_pf";
    private static final String REQUEST_NEXT_FIXTURE = "req_nf";
    private static final String REQUEST_CRESTS = "req_crest";

    private HttpRequestHandler mRequestHandler;
    private FragmentFavouriteDashboardBinding mBinding;
    private CurrentUser mCurrentUser;

    private FavouriteTeamJson mFavouriteTeamJson;
    private LeagueTableJson mLeagueTableJson;
    private MatchdayJson mPrevMatchdayJson, mNextMatchdayJson;
    private FixtureJson mPrevFixtureJson, mNextFixtureJson;
    private CrestsJson mCrestsJson;

    private LinearLayout mAvailableLayout, mPlaceholderLayout;

    private String mTeamApiUrl, mCrestApiUrl, mNextFixtureApiUrl, mPrevFixtureApiUrl;

    private boolean mIsTeamChosen;

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
            mTeamApiUrl = getString(R.string.team_api_endpoint)
                    + mCurrentUser.getFavouriteTeam().apiId;

            mRequestHandler.sendJsonObjectRequest(mTeamApiUrl,
                    REQUEST_TEAM,
                    FavouriteTeamJson.class);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mRequestHandler.removeRequestFinishedListener();
    }

    @Override
    public void onRequestFinished(Request request) {
        switch (request.getTag().toString()) {
            case REQUEST_TEAM: {
                mFavouriteTeamJson = (FavouriteTeamJson) mRequestHandler.getResultObject();

                mRequestHandler.sendJsonObjectRequest(
                        mTeamApiUrl,
                        REQUEST_TEAM,
                        FavouriteTeamJson.class);
                break;
            }
            case REQUEST_POSITION: {
                mLeagueTableJson = (LeagueTableJson) mRequestHandler.getResultObject();

                mNextFixtureApiUrl = UrlBuilders.buildFixtureApiUrls(
                        getString(R.string.fixture_api_endpoint),
                        mLeagueTableJson.getMatchday(),
                        true);

                mRequestHandler.sendJsonObjectRequest(
                        mNextFixtureApiUrl,
                        REQUEST_NEXT_FIXTURE,
                        FixtureJson.class);
                break;
            }
            case REQUEST_NEXT_FIXTURE: {
                mNextMatchdayJson = (MatchdayJson) mRequestHandler.getResultObject();

                mPrevFixtureApiUrl = UrlBuilders.buildFixtureApiUrls(
                        getString(R.string.fixture_api_endpoint),
                        mLeagueTableJson.getMatchday(),
                        false);

                mRequestHandler.sendJsonObjectRequest(
                        mPrevFixtureApiUrl,
                        REQUEST_PREV_FIXTURE,
                        FixtureJson.class);
                break;
            }
            case REQUEST_PREV_FIXTURE: {
                mPrevMatchdayJson = (MatchdayJson) mRequestHandler.getResultObject();

                mCrestApiUrl = UrlBuilders.buildCrestApiUrls(getString(R.string.crests_api_endpoint),
                        mFavouriteTeamJson.getName(),
                        mPrevFixtureJson,
                        mNextFixtureJson);

                mRequestHandler.sendJsonObjectRequest(
                        mCrestApiUrl,
                        REQUEST_CRESTS,
                        CrestsJson.class);
                break;
            }
            case REQUEST_CRESTS: {
                mCrestsJson = (CrestsJson) mRequestHandler.getResultObject();
                updateDatabase();
                break;
            }
        }
    }

    private void updateDatabase() {
        FavouriteTeam favouriteTeam = mCurrentUser.getFavouriteTeam();
        Fixture prevFixture = FixtureMapper.jsonToModel(mPrevFixtureJson, favouriteTeam.previousFixture, favouriteTeam.name, mCrestsJson.getPrevCrest());
        Fixture nextFixture = FixtureMapper.jsonToModel(mNextFixtureJson, favouriteTeam.nextFixture, favouriteTeam.name, mCrestsJson.getNextCrest());
        favouriteTeam = FavouriteTeamMapper.jsonToModel()
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
