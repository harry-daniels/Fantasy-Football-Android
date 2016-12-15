package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class LeagueTableJson {

    @JsonField(name = "matchday")
    private String matchday;

    @JsonField(name = "standing")
    private List<StandingJson> standings;

    public String getMatchday() {
        return matchday;
    }

    public void setMatchday(String matchday) {
        this.matchday = matchday;
    }

    public List<StandingJson> getStandings() {
        return standings;
    }

    public void setStandings(List<StandingJson> standings) {
        this.standings = standings;
    }
}
