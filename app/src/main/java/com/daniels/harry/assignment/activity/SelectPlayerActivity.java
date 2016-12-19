package com.daniels.harry.assignment.activity;

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
import com.daniels.harry.assignment.mapper.PlayerMapper;
import com.daniels.harry.assignment.model.Player;
import com.daniels.harry.assignment.repository.PlayerRepository;
import com.daniels.harry.assignment.util.Calculators;
import com.daniels.harry.assignment.viewmodel.SelectPlayerViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SelectPlayerActivity extends AppCompatActivity implements View.OnClickListener,
        RequestQueue.RequestFinishedListener, SelectPlayerListViewAdapter.Listener, SearchView.OnQueryTextListener {

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_player);

        getSupportActionBar().setElevation(0);

        mListViewAdapter = new SelectPlayerListViewAdapter(this, VALUE_COMPARATOR, this);

        mBinding.listPlayerSelector.setLayoutManager(new LinearLayoutManager(this));
        mBinding.listPlayerSelector.setAdapter(mListViewAdapter);

        mPosition = (Enums.Position)getIntent().getSerializableExtra(Constants.IE_POSITION);

        setViewModels();
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
    public void onClick(View v) {
    }

    @Override
    public void onRequestFinished(Request request) {

    }

    public void setViewModels() {
        for (Player p : PlayerRepository.getByArea(Calculators.calculatePlayerAreaFromEnum(mPosition))) {
            mViewModels.add(PlayerMapper.modelToViewModel(p));
        }
        resetListAdapter(mViewModels);
    }

    private void resetListAdapter(List<SelectPlayerViewModel> items){
        mListViewAdapter.edit().removeAll().commit();
        mListViewAdapter.edit().add(items).commit();
        mBinding.listPlayerSelector.scrollToPosition(0);
    }

    @Override
    public void onClick(SelectPlayerViewModel model) {

    }
}
