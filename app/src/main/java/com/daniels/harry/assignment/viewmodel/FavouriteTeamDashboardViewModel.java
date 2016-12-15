package com.daniels.harry.assignment.viewmodel;

import android.databinding.BindingAdapter;
import android.location.Location;
import android.widget.ImageView;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.daniels.harry.assignment.R;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@JsonObject
public class FavouriteTeamDashboardViewModel {

    private String teamName;
    private String distance;
    private String ground;
    private String crestUrl;

    private int position;
    private int points;

    private String wins;
    private String draws;
    private String losses;

    private FixtureViewModel prevFixture;
    private FixtureViewModel nextFixture;

    public FavouriteTeamDashboardViewModel() {

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getDraws() {
        return draws;
    }

    public void setDraws(String draws) {
        this.draws = draws;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

    public FixtureViewModel getPrevFixture() {
        return prevFixture;
    }

    public void setPrevFixture(FixtureViewModel prevFixture) {
        this.prevFixture = prevFixture;
    }

    public FixtureViewModel getNextFixture() {
        return nextFixture;
    }

    public void setNextFixture(FixtureViewModel nextFixture) {
        this.nextFixture = nextFixture;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
