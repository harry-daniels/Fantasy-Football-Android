package com.daniels.harry.assignment.mapper;


import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.HomeAwayStatJson;
import com.daniels.harry.assignment.model.Fixture;
import com.daniels.harry.assignment.model.HomeAwayStat;
import com.daniels.harry.assignment.util.Calculators;

public class HomeAwayStatMapper {

    public static HomeAwayStat jsonToModel(HomeAwayStatJson json, HomeAwayStat model) {
        if (model == null)
            model = new HomeAwayStat();

        model.wins = json.getWins();
        model.draws = json.getDraws();
        model.losses = json.getLosses();
        model.goalsFor = json.getGoalsFor();
        model.goalsAgainst = json.getGoalsAgainst();

        model.save();

        return model;
    }
}
