package com.daniels.harry.assignment.activity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.adapter.FavouriteTeamListViewAdapter;
import com.daniels.harry.assignment.databinding.ActivityFavouritePickerBinding;
import com.daniels.harry.assignment.dialog.ConfirmDialogs;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamPickerViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerSelectorActivity
        extends AppCompatActivity
        implements SearchView.OnQueryTextListener, FavouriteTeamListViewAdapter.Listener, DialogInterface.OnClickListener {

    private static final Comparator<FavouriteTeamPickerViewModel> DISTANCE_COMPARATOR = new Comparator<FavouriteTeamPickerViewModel>() {
        @Override
        public int compare(FavouriteTeamPickerViewModel a, FavouriteTeamPickerViewModel b) {
            return Float.compare(a.getDistance(), b.getDistance());
        }
    };

    private List<FavouriteTeamPickerViewModel> mViewModels = new ArrayList<>();
    private FavouriteTeamPickerViewModel mSelectedViewModel;

    private FavouriteTeamListViewAdapter mListViewAdapter;
    private ActivityFavouritePickerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_picker);

        mListViewAdapter = new FavouriteTeamListViewAdapter(this, DISTANCE_COMPARATOR, this);

        mBinding.listFavouritePicker.setLayoutManager(new LinearLayoutManager(this));
        mBinding.listFavouritePicker.setAdapter(mListViewAdapter);

        getData();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<FavouriteTeamPickerViewModel> filteredModelList = filter(mViewModels, query);

        resetListAdapter(filteredModelList);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<FavouriteTeamPickerViewModel> filter (List<FavouriteTeamPickerViewModel> models, String query)
    {
        final String lowerCaseQuery = query.toLowerCase();

        final List<FavouriteTeamPickerViewModel> filteredModelList = new ArrayList<>();
        for (FavouriteTeamPickerViewModel model : models) {
            final String name = model.getTeamName().toLowerCase();
            if (name.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }

        return filteredModelList;
    }

    @Override
    public void onClick(FavouriteTeamPickerViewModel model) {
        mSelectedViewModel = model;
        ConfirmDialogs.showConfirmFavouriteDialog(this, this, model.getTeamName());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }


    private void resetListAdapter(List<FavouriteTeamPickerViewModel> items){
        mListViewAdapter.edit().removeAll().commit();
        mListViewAdapter.edit().add(items).commit();
        mBinding.listFavouritePicker.scrollToPosition(0);
    }


    private void setViewModels(List<FavouriteTeam> teams) {

    }

    private void getData() {
    }
}
