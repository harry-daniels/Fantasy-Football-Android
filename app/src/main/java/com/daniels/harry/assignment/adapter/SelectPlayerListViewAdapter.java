package com.daniels.harry.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.daniels.harry.assignment.databinding.ListitemFavouriteBinding;
import com.daniels.harry.assignment.databinding.ListitemPlayerBinding;
import com.daniels.harry.assignment.viewholder.FavouritePickerListViewHolder;
import com.daniels.harry.assignment.viewholder.SelectPlayerListViewHolder;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamPickerViewModel;
import com.daniels.harry.assignment.viewmodel.SelectPlayerViewModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;
import java.util.Objects;

public class SelectPlayerListViewAdapter extends SortedListAdapter<SelectPlayerViewModel> {

    public interface Listener {
        void onClick(SelectPlayerViewModel model);
    }

    private final Listener mListener;

    // list view adapter to handle data bound viewmodels to the list of players
    public SelectPlayerListViewAdapter(Context context, Comparator<SelectPlayerViewModel> comparator, Listener listener) {
        super(context, SelectPlayerViewModel.class, comparator);
        mListener = listener;
    }

    @Override
    protected ViewHolder<? extends SelectPlayerViewModel> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final ListitemPlayerBinding binding = ListitemPlayerBinding.inflate(inflater, parent, false);
        return new SelectPlayerListViewHolder(binding, mListener);
    }

    @Override
    protected boolean areItemsTheSame(SelectPlayerViewModel item1, SelectPlayerViewModel item2) {
        return Objects.equals(item1.getId(), item2.getId());
    }

    @Override
    protected boolean areItemContentsTheSame(SelectPlayerViewModel oldItem, SelectPlayerViewModel newItem) {
        return oldItem.equals(newItem);
    }
}
