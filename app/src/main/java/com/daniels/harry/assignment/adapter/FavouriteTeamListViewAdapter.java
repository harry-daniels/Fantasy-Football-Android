package com.daniels.harry.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.daniels.harry.assignment.model.FavouriteTeamModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

public class FavouriteTeamListViewAdapter extends SortedListAdapter<FavouriteTeamModel> {

    public FavouriteTeamListViewAdapter(Context context, Class<FavouriteTeamModel> itemClass, Comparator<FavouriteTeamModel> comparator) {
        super(context, itemClass, comparator);
    }

    @Override
    protected ViewHolder<? extends FavouriteTeamModel> onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    protected boolean areItemsTheSame(FavouriteTeamModel favouriteTeamModel, FavouriteTeamModel t1) {
        return false;
    }

    @Override
    protected boolean areItemContentsTheSame(FavouriteTeamModel favouriteTeamModel, FavouriteTeamModel t1) {
        return false;
    }
}
