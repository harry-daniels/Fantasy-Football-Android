package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class AllPlayersJson {

    @JsonField(name = "players")
    public List<PlayerJson> players;

    @JsonField(name = "count")
    public int count;
}
