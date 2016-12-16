package com.daniels.harry.assignment.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.daniels.harry.assignment.R;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.squareup.picasso.Picasso;

public class FavouriteTeamPickerViewModel implements SortedListAdapter.ViewModel{

    private String id;
    private String teamName;
    private float distance;
    private String crestUrl;
    private float groundLat;
    private float groundLong;

    public FavouriteTeamPickerViewModel() {

    }

    @BindingAdapter({"bind:crestUrl"})
    public static void loadCrest(ImageView view, String crestUrl) {
        Picasso.with(view.getContext())
                .load(crestUrl)
                .placeholder(R.drawable.icon_team)
                .into(view);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getGroundLat() {
        return groundLat;
    }

    public void setGroundLat(float groundLat) {
        this.groundLat = groundLat;
    }

    public float getGroundLong() {
        return groundLong;
    }

    public void setGroundLong(float groundLong) {
        this.groundLong = groundLong;
    }
}
