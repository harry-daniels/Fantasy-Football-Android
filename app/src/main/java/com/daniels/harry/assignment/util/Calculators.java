package com.daniels.harry.assignment.util;


import android.location.Location;

import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.LeagueTableJson;
import com.daniels.harry.assignment.jsonobject.MatchdayJson;
import com.daniels.harry.assignment.jsonobject.StandingJson;
import com.daniels.harry.assignment.model.Fixture;

import java.util.List;
import java.util.Locale;
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
        FixtureJson fixture = new FixtureJson();

        for(FixtureJson f : matchday.getFixtures()){
            if(Objects.equals(teamName, f.getHomeTeamName()) || Objects.equals(teamName, f.getAwayTeamName())){
                fixture = f;
                break;
            }
        }

        return fixture;
    }

    public static StandingJson calculateStanding(String teamName, LeagueTableJson table) {
        StandingJson standing = new StandingJson();

        for(StandingJson s : table.getStandings()) {
            if (Objects.equals(s.getTeamName(), teamName)){
                standing = s;
                break;
            }
        }

        return standing;
    }

    public static float calculateDistance(float latA, float longA, float latB, float longB)
    {
        float[] distance = new float[1];
        Location.distanceBetween(latA, longA, latB, longB, distance);

        //TODO: Add to constants
        String formatted = String.format(Locale.ENGLISH.UK, "%.2f", (distance[0] * 0.000621371f));
        Float parsedDistance = Float.valueOf(formatted);

        return parsedDistance;
    }

    public static String calculateDateString(String date) {

        String returnDate = "";
        String[] dateArray = date.split("T");

        returnDate += dateArray[0].substring(8, 10) + "-"
                + dateArray[0].substring(5, 7) + "-"
                + dateArray[0].substring(0,4) + " "
                + dateArray[1].substring(0, 8);

        return returnDate;
    }

    public static String calculateOutcome(boolean isHome, int homeScore, int awayScore) {

        String outcome = "";

        if (isHome && homeScore < awayScore)
        {
            outcome = "LOSS";
        }
        if (isHome && homeScore > awayScore)
        {
            outcome = "WIN";
        }
        if (!isHome && homeScore < awayScore)
        {
            outcome = "WIN";
        }
        if (!isHome && homeScore > awayScore)
        {
            outcome = "LOSS";
        }
        if (homeScore == awayScore)
        {
            outcome = "DRAW";
        }

        return outcome;
    }

    public static String calculateScoreString(int homeScore, int awayScore) {

        String score = "";

        if(awayScore >= homeScore)
        {
            score = awayScore + " - " + homeScore;
        }
        else
        {
            score = homeScore + " - " + awayScore;
        }

        return score;
    }

    public static String calculatePositionString(String position) {

        String returnPosition;

        switch (position)
        {
            case "1" :
                returnPosition = "1st";
                break;
            case "2" :
                returnPosition = "2nd";
                break;
            case "3" :
                returnPosition = "3rd";
                break;
            default :
                returnPosition = position + "th";
                break;
        }

        return returnPosition;
    }
}
