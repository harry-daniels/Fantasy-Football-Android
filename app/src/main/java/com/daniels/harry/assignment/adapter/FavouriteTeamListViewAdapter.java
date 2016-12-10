package com.daniels.harry.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.daniels.harry.assignment.databinding.ListitemFavouriteBinding;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamViewModel;
import com.daniels.harry.assignment.viewholder.FavouritePickerListViewHolder;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;
import java.util.Objects;

public class FavouriteTeamListViewAdapter extends SortedListAdapter<FavouriteTeamViewModel> {

    public interface Listener {
        void onClick(FavouriteTeamViewModel model);
    }

    private final Listener mListener;

    public FavouriteTeamListViewAdapter(Context context, Comparator<FavouriteTeamViewModel> comparator, Listener listener) {
        super(context, FavouriteTeamViewModel.class, comparator);
        mListener = listener;
    }

    @Override
    protected ViewHolder<? extends FavouriteTeamViewModel> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final ListitemFavouriteBinding binding = ListitemFavouriteBinding.inflate(inflater, parent, false);
        return new FavouritePickerListViewHolder(binding, mListener);
    }

    @Override
    protected boolean areItemsTheSame(FavouriteTeamViewModel item1, FavouriteTeamViewModel item2) {
        return Objects.equals(item1.getName(), item2.getName());
    }

    @Override
    protected boolean areItemContentsTheSame(FavouriteTeamViewModel oldItem, FavouriteTeamViewModel newItem) {
        return oldItem.equals(newItem);
    }
}
