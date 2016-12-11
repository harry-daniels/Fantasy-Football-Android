package com.daniels.harry.assignment.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.adapter.FavouriteTeamListViewAdapter;
import com.daniels.harry.assignment.databinding.ActivityFavouritePickerBinding;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamViewModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class FavouritePickerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String[] TEAMS = new String[]{
            "liverpool",
            "chelsea",
            "arsenal",
            "city",
            "swansea",
            "the scum",
            "newcastle",
            "everton",
            "yids",
    };

    private static final Comparator<FavouriteTeamViewModel> DISTANCE_COMPARATOR = new Comparator<FavouriteTeamViewModel>() {
        @Override
        public int compare(FavouriteTeamViewModel a, FavouriteTeamViewModel b) {
            return a.getDistance() - b.getDistance();
        }
    };

    private FavouriteTeamListViewAdapter mListViewAdapter;
    private List<FavouriteTeamViewModel> mModels;
    private ActivityFavouritePickerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_picker);

        mListViewAdapter = new FavouriteTeamListViewAdapter(this, DISTANCE_COMPARATOR, new FavouriteTeamListViewAdapter.Listener() {
            @Override
            public void onClick(FavouriteTeamViewModel vm) {
                Snackbar.make(mBinding.getRoot(), "piss", Snackbar.LENGTH_SHORT).show();
            }
        });

        mBinding.listFavouritePicker.setLayoutManager(new LinearLayoutManager(this));
        mBinding.listFavouritePicker.setAdapter(mListViewAdapter);

        Random r = new Random();

        mModels = new ArrayList<>();
        for (int i = 0, count = TEAMS.length; i < count; i++) {
            mModels.add(new FavouriteTeamViewModel(TEAMS[i], r.nextInt(1024 - 65) + 65));
        }
        mListViewAdapter.edit()
                .replaceAll(mModels)
                .commit();
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
        final List<FavouriteTeamViewModel> filteredModelList = filter(mModels, query);
        mListViewAdapter.edit()
                .replaceAll(filteredModelList)
                .commit();
        mBinding.listFavouritePicker.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<FavouriteTeamViewModel> filter (List<FavouriteTeamViewModel> models, String query)
    {
        final String lowerCaseQuery = query.toLowerCase();

        final List<FavouriteTeamViewModel> filteredModelList = new ArrayList<>();
        for (FavouriteTeamViewModel model : models) {
            final String name = model.getName().toLowerCase();
            if (name.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }

        return filteredModelList;
    }
}
