package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class MatchdayJson {

    @JsonField(name = "fixtures")
    private List<FixtureJson> fixtures;

    public List<FixtureJson> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<FixtureJson> fixtures) {
        this.fixtures = fixtures;
    }
}
