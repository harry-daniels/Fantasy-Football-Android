package com.daniels.harry.assignment.model;

import com.orm.SugarRecord;

import java.util.Date;


public class FantasyTeam extends SugarRecord {

    public String name;
    public int points;
    public float remainingBudget;
    public Date lastUpdated;

    public FantasyPlayer gk;
    public FantasyPlayer lb;
    public FantasyPlayer lcb;
    public FantasyPlayer rcb;
    public FantasyPlayer rb;
    public FantasyPlayer lm;
    public FantasyPlayer lcm;
    public FantasyPlayer rcm;
    public FantasyPlayer rm;
    public FantasyPlayer ls;
    public FantasyPlayer rs;

    public FantasyTeam() {
    }
}
