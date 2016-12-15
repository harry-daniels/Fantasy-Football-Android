package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class StandingJson {

    @JsonField(name = "position")
    private String position;

    @JsonField(name = "teamName")
    private String teamName;

    @JsonField(name = "playedGames")
    private double playedGames;

    @JsonField(name = "points")
    private double points;

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

    @JsonField(name = "home")
    private HomeAwayStatJson homeStats;

    @JsonField(name = "away")
    private HomeAwayStatJson awayStats;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(double playedGames) {
        this.playedGames = playedGames;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

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

    public HomeAwayStatJson getHomeStats() {
        return homeStats;
    }

    public void setHomeStats(HomeAwayStatJson homeStats) {
        this.homeStats = homeStats;
    }

    public HomeAwayStatJson getAwayStats() {
        return awayStats;
    }

    public void setAwayStats(HomeAwayStatJson awayStats) {
        this.awayStats = awayStats;
    }
}
