package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class HomeAwayStatJson {

    @JsonField(name = "goals")
    private String goalsFor;

    @JsonField(name = "goalsAgainst")
    private String goalsAgainst;

    @JsonField(name = "wins")
    private String wins;

    @JsonField(name = "draws")
    private String draws;

    @JsonField(name = "losses")
    private String losses;

    public String getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(String goalsFor) {
        this.goalsFor = goalsFor;
    }

    public String getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(String goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
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
}
