package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class AllTeamsJson {

    @JsonField(name = "teams")
    private List<FavouriteTeamJson> teams;

    public List<FavouriteTeamJson> getTeams() {
        return teams;
    }

    public void setTeams(List<FavouriteTeamJson> teams) {
        this.teams = teams;
    }
}
