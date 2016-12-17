package com.daniels.harry.assignment.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.orm.SugarRecord;

public class HomeAwayStat extends SugarRecord {

    public int goalsFor;
    public int goalsAgainst;
    public int wins;
    public int draws;
    public int losses;

    public HomeAwayStat() {

    }
}
