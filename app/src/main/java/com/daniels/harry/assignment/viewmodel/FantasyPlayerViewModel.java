package com.daniels.harry.assignment.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class FantasyPlayerViewModel {

    private long id;
    private String surname;
    private int iconResource;

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView view, int resource) {
        view.setImageResource(resource);
    }

    public FantasyPlayerViewModel() {

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}
