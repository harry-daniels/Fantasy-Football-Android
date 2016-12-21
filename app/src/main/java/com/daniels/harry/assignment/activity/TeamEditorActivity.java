package com.daniels.harry.assignment.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.constant.Constants;
import com.daniels.harry.assignment.constant.Enums;
import com.daniels.harry.assignment.dialog.ErrorDialogs;
import com.daniels.harry.assignment.handler.HttpRequestHandler;
import com.daniels.harry.assignment.jsonobject.AllPlayersJson;
import com.daniels.harry.assignment.jsonobject.AllTeamsJson;
import com.daniels.harry.assignment.listener.OnDbGetAsyncListener;
import com.daniels.harry.assignment.listener.OnDbSaveAsyncListener;
import com.daniels.harry.assignment.mapper.FavouriteTeamMapper;
import com.daniels.harry.assignment.mapper.PlayerMapper;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Player;
import com.daniels.harry.assignment.repository.DbGetAllAsync;
import com.daniels.harry.assignment.repository.DbSaveAllAsync;
import com.daniels.harry.assignment.util.Calculators;
import com.daniels.harry.assignment.util.UrlBuilders;

import java.util.ArrayList;
import java.util.List;

public class TeamEditorActivity extends AppCompatActivity implements View.OnClickListener,
        RequestQueue.RequestFinishedListener, OnDbSaveAsyncListener, OnDbGetAsyncListener {

    private HttpRequestHandler mRequestHandler;
    private ProgressDialog mProgressDialog;

    private Enums.Position mInitialiseForPosition;

    private LinearLayout[] mPositionButtons;

    private List<Player> mPlayers = new ArrayList<>();
    int count = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_editor);

        getSupportActionBar().setElevation(0);

        mRequestHandler = new HttpRequestHandler(this, this, this);
        mRequestHandler.addRequestFinishedListener();

        setButtons();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_select_gk:
                mInitialiseForPosition = Enums.Position.GK;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_lb:
                mInitialiseForPosition = Enums.Position.LB;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_lcb:
                mInitialiseForPosition = Enums.Position.LCB;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_rcb:
                mInitialiseForPosition = Enums.Position.RCB;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_rb:
                mInitialiseForPosition = Enums.Position.RB;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_lm:
                mInitialiseForPosition = Enums.Position.LM;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_lcm:
                mInitialiseForPosition = Enums.Position.LCM;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_rcm:
                mInitialiseForPosition = Enums.Position.RCM;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_rm:
                mInitialiseForPosition = Enums.Position.RM;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_ls:
                mInitialiseForPosition = Enums.Position.LS;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
            case R.id.btn_select_rs:
                mInitialiseForPosition = Enums.Position.RS;
                if (!isInitialised()) {
                    initialise();
                } else {
                    launchSelectPlayerActivity();
                }
                break;
        }
    }

    @Override
    public void onRequestFinished(Request request) {

        if (request.hasHadResponseDelivered()) {

            switch (request.getTag().toString()) {
                case Constants.REQUEST_TEAMS: {
                    AllTeamsJson teamsJson = (AllTeamsJson) mRequestHandler.getResultObject();
                    List<FavouriteTeam> teams = FavouriteTeamMapper.jsonToModels(teamsJson);
                    DbSaveAllAsync<FavouriteTeam> save = new DbSaveAllAsync<>(teams, this, Constants.DB_TEAMS_TAG);
                    save.execute();
                    getPlayerData();
                    break;
                }
                default:
                    AllPlayersJson playersJson = (AllPlayersJson) mRequestHandler.getResultObject();
                    String teamId = Calculators.calculateTeamIdFromTag(request.getTag().toString());
                    mPlayers.addAll(PlayerMapper.jsonToModels(playersJson, teamId));
                    count++;
                    if (count == 20) {
                        mProgressDialog.dismiss();
                        DbSaveAllAsync<Player> save = new DbSaveAllAsync<>(mPlayers, this, Constants.DB_PLAYERS_TAG);
                        save.execute();
                    }
            }
        }
    }

    private void initialise() {
        long count = Player.count(Player.class);
        if (mRequestHandler.isNetworkConnected() && Player.count(Player.class) == 0) {

            mProgressDialog = ProgressDialog.show(this,
                    getString(R.string.dialog_title_initialising_progress),
                    getString(R.string.please_wait), true);

            handleTeamsRequest();

        } else if (!mRequestHandler.isNetworkConnected() && Player.count(Player.class) == 0) {
            ErrorDialogs.showErrorDialog(this,
                    getString(R.string.dialog_title_noplayers_error),
                    getString(R.string.dialog_title_noplayers_error));
        }
    }

    private void handleTeamsRequest() {
        if (FavouriteTeam.count(FavouriteTeam.class) == 0) {
            mRequestHandler.sendJsonObjectRequest(
                    getString(R.string.team_api_endpoint),
                    Constants.REQUEST_TEAMS,
                    AllTeamsJson.class);
        } else {
            getPlayerData();
        }
    }

    private void handlePlayersRequest(List<FavouriteTeam> items) {
        for (FavouriteTeam t : items) {
            mRequestHandler.sendJsonObjectRequest(
                    UrlBuilders.buildPlayerApiUrl(t.apiId, getString(R.string.players_api_endpoint)),
                    Constants.REQUEST_PLAYERS + "_" + t.apiId,
                    AllPlayersJson.class);
        }
    }

    private void getPlayerData() {
        DbGetAllAsync<FavouriteTeam> get = new DbGetAllAsync<>(FavouriteTeam.class, this, Constants.DB_TEAMS_TAG);
        get.execute();
    }

    private void setViewModel() {

    }

    private boolean isInitialised() {
        if (Player.count(Player.class) > 0)
            return true;
        else
            return false;
    }

    private void launchSelectPlayerActivity() {
        Intent i = new Intent(this, SelectPlayerActivity.class);
        i.putExtra(Constants.IE_POSITION, mInitialiseForPosition);
        startActivity(i);
    }

    private void setButtons() {
        mPositionButtons = new LinearLayout[]{

                (LinearLayout) findViewById(R.id.btn_select_gk),
                (LinearLayout) findViewById(R.id.btn_select_lb),
                (LinearLayout) findViewById(R.id.btn_select_lcb),
                (LinearLayout) findViewById(R.id.btn_select_rcb),
                (LinearLayout) findViewById(R.id.btn_select_rb),
                (LinearLayout) findViewById(R.id.btn_select_lm),
                (LinearLayout) findViewById(R.id.btn_select_lcm),
                (LinearLayout) findViewById(R.id.btn_select_rcm),
                (LinearLayout) findViewById(R.id.btn_select_rm),
                (LinearLayout) findViewById(R.id.btn_select_ls),
                (LinearLayout) findViewById(R.id.btn_select_rs),
        };

        for (LinearLayout btn : mPositionButtons) {
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onDbSaveSuccess(String tag) {
        switch (tag) {
            case Constants.DB_PLAYERS_TAG:
                launchSelectPlayerActivity();
                break;
        }
    }

    @Override
    public void onDbSaveFailure(String tag) {

    }

    @Override
    public void onDbGetSuccess(String tag, List result) {
        switch (tag) {
            case Constants.DB_TEAMS_TAG:
                handlePlayersRequest(result);
                break;
        }
    }

    @Override
    public void onDbGetFailure(String tag) {

    }
}
