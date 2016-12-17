package com.daniels.harry.assignment.model;

import com.orm.SugarRecord;

public class Team extends SugarRecord {

    public Team() {
    }

    public String name;
    public int points;
    public float remainingBudget;

    public Player gk;
    public Player lb;
    public Player lcb;
    public Player rcb;
    public Player rb;
    public Player lm;
    public Player lcm;
    public Player rcm;
    public Player rm;
    public Player ls;
    public Player rs;
}
