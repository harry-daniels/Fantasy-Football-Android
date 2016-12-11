package com.daniels.harry.assignment.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.daniels.harry.assignment.R;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.squareup.picasso.Picasso;

import java.util.Objects;

@JsonObject
public class FavouriteTeamViewModel implements SortedListAdapter.ViewModel {

    public FavouriteTeamViewModel() {
    }

    public void calculateFixtureDetails()
    {
        if (prevFixture != null)
        {
            if (Objects.equals(prevFixture.getHomeTeamName(), name))
            {
                prevFixture.setOppositionName(prevFixture.getAwayTeamName());
                prevFixture.setLocation("Home");
            }
            else
            {
                prevFixture.setOppositionName(prevFixture.getHomeTeamName());
                prevFixture.setLocation("Away");
            }
        }

        if (nextFixture != null)
        {
            if (Objects.equals(nextFixture.getHomeTeamName(), name))
            {
                nextFixture.setOppositionName(nextFixture.getAwayTeamName());
                nextFixture.setLocation("Home");
            }
            else
            {
                nextFixture.setOppositionName(nextFixture.getHomeTeamName());
                nextFixture.setLocation("Away");
            }
        }
    }

    @BindingAdapter({"bind:crestUrl"})
    public static void loadCrest(ImageView view, String crestUrl) {
        Picasso.with(view.getContext())
                .load(crestUrl)
                .placeholder(R.drawable.icon_team)
                .into(view);
    }

    @JsonField(name = "Name")
    private String name;

    @JsonField(name = "Latitude")
    private double latitude;

    @JsonField(name = "Longitude")
    private double longitude;

    @JsonField(name = "Ground")
    private String ground;

    @JsonField(name = "CrestURL")
    private String crestUrl;

    private int distance;
    private String position;
    private FixtureViewModel prevFixture;
    private FixtureViewModel nextFixture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
}
