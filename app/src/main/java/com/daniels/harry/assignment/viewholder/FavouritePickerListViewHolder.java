package com.daniels.harry.assignment.viewholder;

import android.view.View;

import com.daniels.harry.assignment.model.FavouriteTeamModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class FavouritePickerListViewHolder extends SortedListAdapter.ViewHolder<FavouriteTeamModel> {

    private final ListitemFavouriteBinding  mBinding;

    public FavouritePickerListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void performBind(FavouriteTeamModel favouriteTeamModel) {

    }
}
