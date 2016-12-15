package com.daniels.harry.assignment.viewholder;

import com.daniels.harry.assignment.adapter.FavouriteTeamListViewAdapter;
import com.daniels.harry.assignment.databinding.ListitemFavouriteBinding;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamPickerViewModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class FavouritePickerListViewHolder extends SortedListAdapter.ViewHolder<FavouriteTeamPickerViewModel> {

    private final ListitemFavouriteBinding mBinding;

    public FavouritePickerListViewHolder(ListitemFavouriteBinding binding, FavouriteTeamListViewAdapter.Listener listener) {
        super(binding.getRoot());
        binding.setListener(listener);
        mBinding = binding;
    }

    @Override
    protected void performBind(FavouriteTeamPickerViewModel item) {
        mBinding.setViewmodel(item);
    }

}
