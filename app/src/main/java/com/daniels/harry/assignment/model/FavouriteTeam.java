package com.daniels.harry.assignment.model;

import com.daniels.harry.assignment.constant.Enums;
import com.orm.SugarRecord;

public class FavouriteTeam extends SugarRecord {

    public String apiId;
    public String name;

    public boolean populated;

    public float distance;
    public float groundLat;
    public float groundLong;

    public String colour;
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
