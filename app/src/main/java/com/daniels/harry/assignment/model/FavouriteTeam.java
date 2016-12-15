package com.daniels.harry.assignment.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.orm.SugarRecord;

public class FavouriteTeam extends SugarRecord {

    public String apiId;
    public String name;

    private double latitude;
    private double longitude;

    private String ground;
    private String crestUrl;

    private String position;

    private int playedGames;

    private int points;

    private int goalsFor;

    private int goalsAgainst;

    private int wins;

    private int draws;

    private int losses;

    public Fixture previousFixture;
    public Fixture nextFixture;

    public FavouriteTeam(){

    }
}
