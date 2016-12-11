package com.daniels.harry.assignment.viewholder;

import android.widget.ImageView;

import com.daniels.harry.assignment.adapter.FavouriteTeamListViewAdapter;
import com.daniels.harry.assignment.databinding.ListitemFavouriteBinding;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamViewModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class FavouritePickerListViewHolder extends SortedListAdapter.ViewHolder<FavouriteTeamViewModel> {

    private final ListitemFavouriteBinding mBinding;

    public FavouritePickerListViewHolder(ListitemFavouriteBinding binding, FavouriteTeamListViewAdapter.Listener listener) {
        super(binding.getRoot());
        binding.setListener(listener);
        mBinding = binding;
    }

    @Override
    protected void performBind(FavouriteTeamViewModel item) {
        mBinding.setViewmodel(item);
    }

}
