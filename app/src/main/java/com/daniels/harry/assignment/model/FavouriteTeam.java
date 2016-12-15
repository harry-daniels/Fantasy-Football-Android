package com.daniels.harry.assignment.model;

import com.orm.SugarRecord;

public class FavouriteTeam extends SugarRecord {

    public String apiId;
    public String name;

    public float distance;

    public String ground;
    public String crestUrl;

    public int position;
    public int playedGames;
    public int points;

    public HomeAwayStat homeStat;
    public HomeAwayStat awayStat;

    public Fixture previousFixture;
    public Fixture nextFixture;

    public FavouriteTeam(){

    }
}
