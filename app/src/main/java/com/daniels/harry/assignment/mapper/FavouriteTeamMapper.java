package com.daniels.harry.assignment.mapper;


import com.daniels.harry.assignment.jsonobject.FavouriteTeamJson;
import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.StandingJson;
import com.daniels.harry.assignment.model.FavouriteTeam;

public class FavouriteTeamMapper {

    public static FavouriteTeam map(FavouriteTeamJson team, FixtureJson prevFixture,
                                    FixtureJson nextFixture, StandingJson standing){
        return new FavouriteTeam();
    }
}
