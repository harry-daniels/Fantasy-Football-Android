package com.daniels.harry.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.daniels.harry.assignment.databinding.ListitemFavouriteBinding;
import com.daniels.harry.assignment.viewholder.FavouritePickerListViewHolder;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamPickerViewModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;
import java.util.Objects;

public class FavouriteTeamListViewAdapter extends SortedListAdapter<FavouriteTeamPickerViewModel> {

    public interface Listener {
        void onClick(FavouriteTeamPickerViewModel model);
    }

    private final Listener mListener;

    // list view adapter to handle data bound viewmodels to the list of favourite teams
    public FavouriteTeamListViewAdapter(Context context, Comparator<FavouriteTeamPickerViewModel> comparator, Listener listener) {
        super(context, FavouriteTeamPickerViewModel.class, comparator);
        mListener = listener;
    }

    @Override
    protected ViewHolder<? extends FavouriteTeamPickerViewModel> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final ListitemFavouriteBinding binding = ListitemFavouriteBinding.inflate(inflater, parent, false);
        return new FavouritePickerListViewHolder(binding, mListener);
    }

    @Override
    protected boolean areItemsTheSame(FavouriteTeamPickerViewModel item1, FavouriteTeamPickerViewModel item2) {
        return Objects.equals(item1.getId(), item2.getId());
    }

    @Override
    protected boolean areItemContentsTheSame(FavouriteTeamPickerViewModel oldItem, FavouriteTeamPickerViewModel newItem) {
        return oldItem.equals(newItem);
    }
}
