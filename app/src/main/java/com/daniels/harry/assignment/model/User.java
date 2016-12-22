package com.daniels.harry.assignment.model;

import com.orm.SugarRecord;

public class User extends SugarRecord{

    public String googleId;
    public String name;

    public FavouriteTeam favouriteTeam;
    public FantasyTeam fantasyTeam;

    public User(){

    }

    public User(String gId, String name) {
        this.googleId = gId;
        this.name = name;
    }
}
