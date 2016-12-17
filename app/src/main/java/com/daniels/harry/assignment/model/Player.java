package com.daniels.harry.assignment.model;

import com.orm.SugarRecord;

public class Player extends SugarRecord {

    public String firstName;
    public String surname;
    public float price;
    public FavouriteTeam team;

    public Player () {

    }
}
