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
import com.daniels.harry.assignment.dialog.ErrorDialogs;
import com.daniels.harry.assignment.handler.HttpRequestHandler;
import com.daniels.harry.assignment.jsonobject.CrestsJson;
import com.daniels.harry.assignment.jsonobject.FavouriteTeamJson;
import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.LeagueTableJson;
import com.daniels.harry.assignment.jsonobject.MatchdayJson;
import com.daniels.harry.assignment.jsonobject.StandingJson;
import com.daniels.harry.assignment.mapper.FavouriteTeamMapper;
import com.daniels.harry.assignment.mapper.FixtureMapper;
import com.daniels.harry.assignment.mapper.HomeAwayStatMapper;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Fixture;
import com.daniels.harry.assignment.model.HomeAwayStat;
import com.daniels.harry.assignment.singleton.CurrentUser;
import com.daniels.harry.assignment.util.Calculators;
import com.daniels.harry.assignment.util.Constants;
import com.daniels.harry.assignment.util.UrlBuilders;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamDashboardViewModel;
import com.daniels.harry.assignment.viewmodel.FixtureViewModel;

import java.util.Objects;


public class FavouriteDashboardFragment extends Fragment implements RequestQueue.RequestFinishedListener {

    private HttpRequestHandler mRequestHandler;
    private FragmentFavouriteDashboardBinding mBinding;
    private CurrentUser mCurrentUser;

    private FavouriteTeamDashboardViewModel mViewModel;

    private FavouriteTeamJson mFavouriteTeamJson;
    private LeagueTableJson mLeagueTableJson;
    private MatchdayJson mPrevMatchdayJson, mNextMatchdayJson;
    private FixtureJson mPrevFixtureJson, mNextFixtureJson;
    private CrestsJson mCrestsJson;
    private StandingJson mStandingJson;

    private LinearLayout mAvailableLayout, mPlaceholderLayout, mNoDataLayout;

    private String mTeamApiUrl, mCrestApiUrl, mNextFixtureApiUrl, mPrevFixtureApiUrl;

    public static FavouriteDashboardFragment newInstance() {
        FavouriteDashboardFragment fragment = new FavouriteDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestHandler = new HttpRequestHandler(getActivity(), getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_dashboard, container, false);
        View rootView = mBinding.getRoot();

        mAvailableLayout = (LinearLayout)rootView.findViewById(R.id.layout_favourite_available);
        mPlaceholderLayout = (LinearLayout)rootView.findViewById(R.id.layout_favourite_placeholder);
        mNoDataLayout = (LinearLayout)rootView.findViewById(R.id.layout_favourite_no_data);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mCurrentUser = CurrentUser.getInstance();
        setVisibility();
        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRequestHandler.removeRequestFinishedListener();
    }

    @Override
    public void onRequestFinished(Request request) {
        switch (request.getTag().toString()) {
            case Constants.REQUEST_TEAM: {
                mFavouriteTeamJson = (FavouriteTeamJson) mRequestHandler.getResultObject();

                mRequestHandler.sendJsonObjectRequest(
                        getString(R.string.league_table_api_endpoint),
                        Constants.REQUEST_POSITION,
                        LeagueTableJson.class);
                break;
            }
            case Constants.REQUEST_POSITION: {
                mLeagueTableJson = (LeagueTableJson) mRequestHandler.getResultObject();
                mStandingJson = Calculators.calculateStanding(mFavouriteTeamJson.getName(), mLeagueTableJson);

                mNextFixtureApiUrl = UrlBuilders.buildFixtureApiUrls(
                        getString(R.string.fixture_api_endpoint),
                        mLeagueTableJson.getMatchday(),
                        true);

                mRequestHandler.sendJsonObjectRequest(
                        mNextFixtureApiUrl,
                        Constants.REQUEST_NEXT_FIXTURE,
                        MatchdayJson.class);
                break;
            }
            case Constants.REQUEST_NEXT_FIXTURE: {
                mNextMatchdayJson = (MatchdayJson) mRequestHandler.getResultObject();
                mNextFixtureJson = Calculators.calculateFixture(mFavouriteTeamJson.getName(), mNextMatchdayJson);

                mPrevFixtureApiUrl = UrlBuilders.buildFixtureApiUrls(
                        getString(R.string.fixture_api_endpoint),
                        mLeagueTableJson.getMatchday(),
                        false);

                mRequestHandler.sendJsonObjectRequest(
                        mPrevFixtureApiUrl,
                        Constants.REQUEST_PREV_FIXTURE,
                        MatchdayJson.class);
                break;
            }
            case Constants.REQUEST_PREV_FIXTURE: {
                mPrevMatchdayJson = (MatchdayJson) mRequestHandler.getResultObject();
                mPrevFixtureJson = Calculators.calculateFixture(mFavouriteTeamJson.getName(), mPrevMatchdayJson);

                mCrestApiUrl = UrlBuilders.buildCrestApiUrls(getString(R.string.crests_api_endpoint),
                        mFavouriteTeamJson.getName(),
                        mPrevFixtureJson,
                        mNextFixtureJson);

                mRequestHandler.sendJsonObjectRequest(
                        mCrestApiUrl,
                        Constants.REQUEST_CRESTS,
                        CrestsJson.class);
                break;
            }
            case Constants.REQUEST_CRESTS: {
                mCrestsJson = (CrestsJson) mRequestHandler.getResultObject();
                mapJsonToDb();
                setViewModel();
                break;
            }
        }
    }

    private void setViewModel() {
        try {
            mViewModel = FavouriteTeamMapper.modelToViewModel(mCurrentUser.getFavouriteTeam());
            mBinding.setViewmodel(mViewModel);
        } catch (Exception e) {
            ErrorDialogs.showNoStatisticsErrorDialog(getActivity());
        }
    }

    private void mapJsonToDb() {
        Fixture prevFixture = FixtureMapper.jsonToModel(mPrevFixtureJson,
                mCurrentUser.getFavouriteTeam().previousFixture,
                mCurrentUser.getFavouriteTeam().name,
                mCrestsJson.getPrevCrest());

        Fixture nextFixture = FixtureMapper.jsonToModel(mNextFixtureJson,
                mCurrentUser.getFavouriteTeam().nextFixture,
                mCurrentUser.getFavouriteTeam().name,
                mCrestsJson.getNextCrest());

        HomeAwayStat homeStat = HomeAwayStatMapper.jsonToModel(mStandingJson.getHomeStats(),
                mCurrentUser.getFavouriteTeam().homeStat);

        HomeAwayStat awayStat = HomeAwayStatMapper.jsonToModel(mStandingJson.getAwayStats(),
                mCurrentUser.getFavouriteTeam().awayStat);

        FavouriteTeam favTeam = FavouriteTeamMapper.jsonToModel(mFavouriteTeamJson,
                mStandingJson,
                mCurrentUser.getFavouriteTeam(),
                prevFixture, nextFixture,
                homeStat, awayStat);
    }

    private boolean isTeamChosen() {
        boolean isChosen = false;

        if(mCurrentUser != null) {
            isChosen = mCurrentUser.getFavouriteTeam() != null;
        }

        return isChosen;
    }

    private boolean isTeamPopulated() {
        if (isTeamChosen() && mCurrentUser.getFavouriteTeam().populated)
            return true;

        return false;
    }

    private void setVisibility()
    {
        if (isTeamChosen()){
            if (isTeamPopulated()) {
                mAvailableLayout.setVisibility(View.VISIBLE);
                mPlaceholderLayout.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.GONE);
            } else {
                mAvailableLayout.setVisibility(View.GONE);
                mPlaceholderLayout.setVisibility(View.VISIBLE);
                mNoDataLayout.setVisibility(View.GONE);
            }
        } else {
            mAvailableLayout.setVisibility(View.GONE);
            mPlaceholderLayout.setVisibility(View.VISIBLE);
            mNoDataLayout.setVisibility(View.GONE);
        }
    }

    private void getData() {
        if (isTeamChosen()) {
            if (mRequestHandler.isNetworkConnected() ||
                    !Objects.equals(Calculators.calculateMobileNetworkType(getActivity()), Constants.NETWORK_2G)) {

                mRequestHandler.addRequestFinishedListener();

                mTeamApiUrl = getString(R.string.team_api_endpoint)
                        + mCurrentUser.getFavouriteTeam().apiId;

                mRequestHandler.sendJsonObjectRequest(mTeamApiUrl,
                        Constants.REQUEST_TEAM,
                        FavouriteTeamJson.class);
            } else if (isTeamPopulated()) {
                setViewModel();
            } else {
                ErrorDialogs.showNoStatisticsErrorDialog(getActivity());
            }
        }
    }

}
