package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class FixtureJson {

    @JsonField(name = "date")
    private String date;

    @JsonField(name = "status")
    private String status;

    @JsonField(name = "matchday")
    private double matchday;

    @JsonField(name = "homeTeamName")
    private double homeTeamName;

    @JsonField(name = "awayTeamName")
    private String awayTeamName;

    @JsonField(name = "result")
    private ResultJson result;

    public ResultJson getResult() {
        return result;
    }

    public void setResult(ResultJson result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getMatchday() {
        return matchday;
    }

    public void setMatchday(double matchday) {
        this.matchday = matchday;
    }

    public double getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(double homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }
}
