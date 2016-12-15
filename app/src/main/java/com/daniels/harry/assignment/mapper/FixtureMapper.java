package com.daniels.harry.assignment.mapper;


import com.android.annotations.Nullable;
import com.daniels.harry.assignment.jsonobject.FavouriteTeamJson;
import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.StandingJson;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Fixture;
import com.daniels.harry.assignment.util.Calculators;

public class FixtureMapper {

    public static Fixture jsonToModel(FixtureJson json, Fixture model, String favTeam, String crestUrl) {
        if (model == null)
            model = new Fixture();

        model.date = json.getDate();
        model.oppositionName = Calculators.calculateOppositionName(favTeam, json.getHomeTeamName(), json.getAwayTeamName());
        model.homeScore = json.getResult().getHomeScore();
        model.awayScore = json.getResult().getAwayScore();
        model.crestUrl = crestUrl;

        model.save();

        return model;
    }
}
