package com.daniels.harry.assignment.util;


import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.MatchdayJson;
import com.daniels.harry.assignment.model.Fixture;

import java.util.List;
import java.util.Objects;

public class Calculators {

    public static String calculateOppositionName(String favTeam, String homeTeam, String awayTeam) {
        if (calculateIsHome(favTeam, homeTeam))
            return awayTeam;
        else
            return homeTeam;
    }

    public static boolean calculateIsHome(String favTeam, String homeTeam) {
        if (Objects.equals(favTeam, homeTeam))
            return true;
        else
            return false;
    }

    public static FixtureJson calculateFixture(String teamName, MatchdayJson matchday) {
        return matchday.getFixtures().get(1);
    }
}
