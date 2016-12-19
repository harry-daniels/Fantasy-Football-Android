package com.daniels.harry.assignment.model;

import com.daniels.harry.assignment.constant.Enums;
import com.orm.SugarRecord;

public class Player extends SugarRecord {

    public String firstName;
    public String surname;
    public float price;
    public FavouriteTeam team;
    public Enums.Area area;

    public Player () {

    }
}
