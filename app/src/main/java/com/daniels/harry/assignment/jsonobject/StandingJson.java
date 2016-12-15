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
    private int playedGames;

    @JsonField(name = "points")
    private int points;

    @JsonField(name = "goals")
    private int goalsFor;

    @JsonField(name = "goalsAgainst")
    private int goalsAgainst;

    @JsonField(name = "wins")
    private int wins;

    @JsonField(name = "draws")
    private int draws;

    @JsonField(name = "losses")
    private int losses;

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

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
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
