package com.daniels.harry.assignment.viewmodel;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.daniels.harry.assignment.constant.Enums;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class SelectPlayerViewModel implements SortedListAdapter.ViewModel{

    private String id;
    private String name;
    private String teamName;
    private double price;
    private int iconResource;

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView view, int resource) {
        view.setImageResource(resource);
    }

    public SelectPlayerViewModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}
