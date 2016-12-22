package com.daniels.harry.assignment.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.adapter.SelectPlayerListViewAdapter;
import com.daniels.harry.assignment.constant.Constants;
import com.daniels.harry.assignment.constant.Enums;
import com.daniels.harry.assignment.databinding.ActivitySelectPlayerBinding;
import com.daniels.harry.assignment.dialog.ErrorDialogs;
import com.daniels.harry.assignment.listener.OnDbGetAsyncListener;
import com.daniels.harry.assignment.mapper.PlayerMapper;
import com.daniels.harry.assignment.model.Player;
import com.daniels.harry.assignment.repository.DbGetAllAsync;
import com.daniels.harry.assignment.repository.DbGetByAttributeAsync;
import com.daniels.harry.assignment.repository.PlayerRepository;
import com.daniels.harry.assignment.singleton.CurrentUser;
import com.daniels.harry.assignment.util.Calculators;
import com.daniels.harry.assignment.viewmodel.SelectPlayerViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SelectPlayerActivity extends AppCompatActivity implements
        RequestQueue.RequestFinishedListener, SelectPlayerListViewAdapter.Listener, SearchView.OnQueryTextListener,
        OnDbGetAsyncListener {

    private static final Comparator<SelectPlayerViewModel> VALUE_COMPARATOR = new Comparator<SelectPlayerViewModel>() {
        @Override
        public int compare(SelectPlayerViewModel a, SelectPlayerViewModel b) {
            return Double.compare(b.getPrice(), a.getPrice());
        }
    };

    private ActivitySelectPlayerBinding mBinding;
    private SelectPlayerListViewAdapter mListViewAdapter;

    private List<SelectPlayerViewModel> mViewModels = new ArrayList<SelectPlayerViewModel>();

    private Enums.Position mPosition;

    private ProgressDialog mProgressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_player);

        getSupportActionBar().setElevation(0);

        mListViewAdapter = new SelectPlayerListViewAdapter(this, VALUE_COMPARATOR, this);

        mBinding.listPlayerSelector.setLayoutManager(new LinearLayoutManager(this));
        mBinding.listPlayerSelector.setAdapter(mListViewAdapter);

        mPosition = (Enums.Position)getIntent().getSerializableExtra(Constants.IE_POSITION);

        getData();
    }

    // setup the search view using the action bar and options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        return true;
    }

    // handle search query and filter listview
    @Override
    public boolean onQueryTextChange(String query) {
        final List<SelectPlayerViewModel> filteredModelList = filter(mViewModels, query);

        resetListAdapter(filteredModelList);

        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<SelectPlayerViewModel> filter (List<SelectPlayerViewModel> models, String query)
    {
        final String lowerCaseQuery = query.toLowerCase();

        final List<SelectPlayerViewModel> filteredModelList = new ArrayList<>();
        for (SelectPlayerViewModel model : models) {
            final String name = model.getName().toLowerCase();
            if (name.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }

        return filteredModelList;
    }

    @Override
    public void onRequestFinished(Request request) {

    }

    // reset the list adapter with new viewmodels
    public void setViewModels(List<Player> players) {
        for (Player p : players) {
            mViewModels.add(PlayerMapper.modelToViewModel(p));
        }
        resetListAdapter(mViewModels);
    }

    private void resetListAdapter(List<SelectPlayerViewModel> items){
        mListViewAdapter.edit().removeAll().commit();
        mListViewAdapter.edit().add(items).commit();
        mBinding.listPlayerSelector.scrollToPosition(0);
    }

    // retrieve player data asychronously from the database and show a progress dialog
    public void getData() {
        mProgressDialog = ProgressDialog.show(this,
                getString(R.string.loading_players_progress_dialog),
                getString(R.string.please_wait));

        String playerId = CurrentUser.getInstance().getPlayer(mPosition) == null ?
                "-1" :
                CurrentUser.getInstance().getPlayer(mPosition).getId().toString();

        DbGetByAttributeAsync<Player> getByArea = new DbGetByAttributeAsync<>("area = ? and id <> ?",
                new String[] {
                    Calculators.calculatePlayerAreaFromEnum(mPosition).toString(),
                    playerId
                },
                Player.class,
                this,
                Constants.DB_PLAYERS_TAG);

        getByArea.execute();
    }

    // upon player selection, save this to the database and close the activity
    @Override
    public void onClick(SelectPlayerViewModel model) {

        Player chosen = Player.findById(Player.class, model.getId());

        if (CurrentUser.getInstance().getFantasyTeam().remainingBudget > chosen.price) {
            CurrentUser.getInstance().setPlayer(mPosition, chosen);
            finish();
        } else {
            ErrorDialogs.showErrorDialog(this,
                    getString(R.string.dialog_error_nice_try),
                    getString(R.string.dialog_error_no_money));
        }
    }

    // listeners for async database interactions
    @Override
    public void onDbGetSuccess(String tag, List result) {
        switch (tag) {
            case Constants.DB_PLAYERS_TAG:
                mProgressDialog.dismiss();
                setViewModels(result);
        }
    }
    @Override
    public void onDbGetFailure(String tag) {

    }
}
