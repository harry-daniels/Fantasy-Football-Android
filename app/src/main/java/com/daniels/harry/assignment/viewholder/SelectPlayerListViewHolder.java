package com.daniels.harry.assignment.viewholder;

import com.daniels.harry.assignment.adapter.FavouriteTeamListViewAdapter;
import com.daniels.harry.assignment.adapter.SelectPlayerListViewAdapter;
import com.daniels.harry.assignment.databinding.ListitemFavouriteBinding;
import com.daniels.harry.assignment.databinding.ListitemPlayerBinding;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamPickerViewModel;
import com.daniels.harry.assignment.viewmodel.SelectPlayerViewModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class SelectPlayerListViewHolder extends SortedListAdapter.ViewHolder<SelectPlayerViewModel> {

    private final ListitemPlayerBinding mBinding;

    public SelectPlayerListViewHolder(ListitemPlayerBinding binding, SelectPlayerListViewAdapter.Listener listener) {
        super(binding.getRoot());
        binding.setListener(listener);
        mBinding = binding;
    }

    @Override
    protected void performBind(SelectPlayerViewModel item) {
        mBinding.setViewmodel(item);
    }

}
