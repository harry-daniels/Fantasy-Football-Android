package com.daniels.harry.assignment.model;

import com.orm.SugarRecord;

public class User extends SugarRecord{

    public String googleId;
    public String name;

    public FavouriteTeam favouriteTeam;

    public User(){

    }

    public User(String gId) {
        this.googleId = gId;
    }
}
