package com.daniels.harry.assignment.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.daniels.harry.assignment.R;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.squareup.picasso.Picasso;

@JsonObject
public class FixtureViewModel implements SortedListAdapter.ViewModel {

    @JsonField(name = "homeTeamName")
    private String homeTeamName;

    @JsonField(name = "awayTeamName")
    private String awayTeamName;

    @JsonField(name = "date")
    private String date;

    private int homeScore;
    private int awayScore;

    private String crestUrl;
    private String outcome;
    private String score;
    private String oppositionName;

    private boolean isAway;

    public FixtureViewModel() {

    }

    @BindingAdapter({"bind:crestUrl"})
    public static void loadCrest(ImageView view, String crestUrl) {
        Picasso.with(view.getContext())
                .load(crestUrl)
                .placeholder(R.drawable.icon_team)
                .into(view);
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        String returnDate = "";
        String[] dateArray = date.split("T");

        returnDate += dateArray[0].substring(8, 10) + "-"
                + dateArray[0].substring(5, 7) + "-"
                + dateArray[0].substring(0,4) + " "
                + dateArray[1].substring(0, 8);

        this.date = returnDate;
    }
    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public String getOutcome() {

        if (isAway && homeScore > awayScore)
        {
            setOutcome("LOSS");
        }
        if (isAway && homeScore < awayScore)
        {
            setOutcome("WIN");
        }
        if (!isAway && homeScore > awayScore)
        {
            setOutcome("WIN");
        }
        if (!isAway && homeScore < awayScore)
        {
            setOutcome("LOSS");
        }
        if (homeScore == awayScore)
        {
            setOutcome("DRAW");
        }

        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getScore() {

        if(awayScore >= homeScore)
        {
            setScore(awayScore + " - " + homeScore);
        }
        else
        {
            setScore(homeScore + " - " + awayScore);
        }
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public boolean isAway() {
        return isAway;
    }

    public void setAway(boolean away) {
        isAway = away;
    }

    public String getOppositionName() {
        return oppositionName;
    }

    public void setOppositionName(String oppositionName) {
        this.oppositionName = oppositionName;
    }
}
