package com.daniels.harry.assignment.util;


import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.jsonobject.FixtureJson;

public class UrlBuilders {

    public static String serialiseString(String s){
        return s.replace(" ", "%20");
    }

    public static String buildFixtureApiUrls(String endpoint, int matchday, boolean isNext){
        if (isNext)
            matchday++;

        return endpoint + matchday;
    }

    public static String buildCrestApiUrls(String endpoint, String favTeamName, FixtureJson prev, FixtureJson next){
        String prevOppositionName = Calculators.calculateOppositionName(favTeamName, prev.getHomeTeamName(), prev.getAwayTeamName());
        String nextOppositionName = Calculators.calculateOppositionName(favTeamName, next.getHomeTeamName(), next.getAwayTeamName());

        prevOppositionName = serialiseString(prevOppositionName);
        nextOppositionName = serialiseString(nextOppositionName);

        return endpoint.replace("#prev#", prevOppositionName).replace("#next#", nextOppositionName);
    }
}
