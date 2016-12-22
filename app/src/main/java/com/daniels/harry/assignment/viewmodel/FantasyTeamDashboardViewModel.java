package com.daniels.harry.assignment.viewmodel;

public class FantasyTeamDashboardViewModel{

    private String name;
    private String points;
    private String lastUpdated;


    public FantasyTeamDashboardViewModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
