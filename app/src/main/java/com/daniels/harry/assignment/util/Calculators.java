package com.daniels.harry.assignment.util;


import android.content.Context;
import android.location.Location;
import android.telephony.TelephonyManager;

import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.constant.Constants;
import com.daniels.harry.assignment.constant.Enums;
import com.daniels.harry.assignment.jsonobject.FixtureJson;
import com.daniels.harry.assignment.jsonobject.LeagueTableJson;
import com.daniels.harry.assignment.jsonobject.MatchdayJson;
import com.daniels.harry.assignment.jsonobject.StandingJson;
import com.daniels.harry.assignment.model.FantasyTeam;

import java.util.Locale;
import java.util.Objects;


// random logic calculators used throughout the application
public class Calculators {

    public static String calculateMobileNetworkType(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);

        int networkType = mTelephonyManager.getNetworkType();

        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return Constants.NETWORK_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return Constants.NETWORK_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return Constants.NETWORK_4G;
            default:
                return Constants.NETWORK_2G;
        }
    }

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

        for (FixtureJson f : matchday.getFixtures()) {
            if (Objects.equals(teamName, f.getHomeTeamName()) || Objects.equals(teamName, f.getAwayTeamName())) {
                fixture = f;
                break;
            }
        }

        return fixture;
    }

    public static StandingJson calculateStanding(String teamName, LeagueTableJson table) {
        StandingJson standing = new StandingJson();

        for (StandingJson s : table.getStandings()) {
            if (Objects.equals(s.getTeamName(), teamName)) {
                standing = s;
                break;
            }
        }

        return standing;
    }

    public static float calculateDistance(float latA, float longA, float latB, float longB) {
        float[] distance = new float[1];
        Location.distanceBetween(latA, longA, latB, longB, distance);

        String formatted = String.format(Locale.ENGLISH.UK, Constants.FLOAT_2DP, (distance[0] * Constants.KM_TO_MILES));
        Float parsedDistance = Float.valueOf(formatted);

        return parsedDistance;
    }

    public static String calculateDateString(String date) {

        String returnDate = "";
        String[] dateArray = date.split("T");

        returnDate += dateArray[0].substring(8, 10) + "-"
                + dateArray[0].substring(5, 7) + "-"
                + dateArray[0].substring(0, 4) + " "
                + dateArray[1].substring(0, 8);

        return returnDate;
    }

    public static String calculateOutcome(boolean isHome, int homeScore, int awayScore) {

        String outcome = "";

        if (isHome && homeScore < awayScore) {
            outcome = "LOSS";
        }
        if (isHome && homeScore > awayScore) {
            outcome = "WIN";
        }
        if (!isHome && homeScore < awayScore) {
            outcome = "WIN";
        }
        if (!isHome && homeScore > awayScore) {
            outcome = "LOSS";
        }
        if (homeScore == awayScore) {
            outcome = "DRAW";
        }

        return outcome;
    }

    public static String calculateScoreString(int homeScore, int awayScore) {

        String score;

        if (awayScore >= homeScore) {
            score = awayScore + " - " + homeScore;
        } else {
            score = homeScore + " - " + awayScore;
        }

        return score;
    }

    public static String calculatePositionString(String position) {

        String returnPosition;

        switch (position) {
            case "1":
                returnPosition = "1st";
                break;
            case "2":
                returnPosition = "2nd";
                break;
            case "3":
                returnPosition = "3rd";
                break;
            default:
                returnPosition = position + "th";
                break;
        }

        return returnPosition;
    }

    public static Enums.ShirtColour calculateShirtColour(String colour) {
        switch (colour) {
            case "darkred":
                return Enums.ShirtColour.DARKRED;
            case "darkblue":
                return Enums.ShirtColour.DARKBLUE;
            case "lightblue":
                return Enums.ShirtColour.LIGHTBLUE;
            case "lightred":
                return Enums.ShirtColour.LIGHTRED;
            case "orange":
                return Enums.ShirtColour.ORANGE;
            case "turqoise":
                return Enums.ShirtColour.TURQOISE;
            case "white":
                return Enums.ShirtColour.WHITE;
            case "yellow":
                return Enums.ShirtColour.YELLOW;
            case "purple":
                return Enums.ShirtColour.PURPLE;
            default:
                return Enums.ShirtColour.TRANSLUCENT;
        }
    }

    public static String calculateTeamIdFromTag(String tag) {
        String[] splitTag = tag.split("_");
        return splitTag[splitTag.length - 1];
    }

    public static String calculateName(String name, boolean isFirstName) {
        String nameSplit[] = name.split(" ");

        if (isFirstName) {
            return nameSplit[0];
        } else {
            String surnames = "";
            for (int i = 1; i < nameSplit.length; i++) {
                surnames += nameSplit[i] + " ";
            }

            return surnames;
        }
    }

    public static Float calculatePlayerPrice(String price) {
        if (price != null) {
            return Float.valueOf(price.replace(",", "").replace(" ", "").replace("€", ""));
        } else {
            return 100000F;
        }
    }

    public static String calculateBudgetString(float budget) {
        double roundedBudget = (Math.round(budget) / 1000000);
        String budgetString = "£" + roundedBudget + "M";
        return budgetString;
    }

    public static int calculateShirtColourResource(Enums.ShirtColour colour) {
        switch (colour) {
            case DARKRED:
                return R.drawable.icon_shirt_darkred;
            case DARKBLUE:
                return R.drawable.icon_shirt_darkblue;
            case LIGHTBLUE:
                return R.drawable.icon_shirt_lightblue;
            case LIGHTRED:
                return R.drawable.icon_shirt_lightred;
            case ORANGE:
                return R.drawable.icon_shirt_orange;
            case TRANSLUCENT:
                return R.drawable.icon_shirt_translucent;
            case TURQOISE:
                return R.drawable.icon_shirt_turqoise;
            case WHITE:
                return R.drawable.icon_shirt_white;
            case YELLOW:
                return R.drawable.icon_shirt_yellow;
            case PURPLE:
                return R.drawable.icon_shrit_purple;
            default:
                return R.drawable.icon_shirt_translucent;
        }
    }

    public static double calculateShortPlayerValue(float value) {
        double valueNoDecimals = Math.round(value);
        valueNoDecimals = (valueNoDecimals / 1000000);

        return valueNoDecimals;
    }

    public static Enums.Area calculatePlayerAreaFromString(String position) {
        switch (position) {
            case "Keeper":
                return Enums.Area.Keeper;
            case "Centre Back":
            case "Left-Back":
            case "Right-Back":
                return Enums.Area.Defender;
            case "Central Midfield":
            case "Defensive Midfield":
            case "Attacking Midfield":
                    return Enums.Area.Midfielder;
            case "Left Wing":
            case "Right Wing":
            case "Secondary Striker":
            case "Centre Forward":
                return  Enums.Area.Striker;
            default:
                return Enums.Area.Keeper;
        }
    }

    public static Enums.Area calculatePlayerAreaFromEnum(Enums.Position position) {
        switch (position) {
            case GK:
                return Enums.Area.Keeper;
            case LB:
            case LCB:
            case RCB:
            case RB:
                return Enums.Area.Defender;
            case LM:
            case LCM:
            case RCM:
            case RM:
                return Enums.Area.Midfielder;
            case LS:
            case RS:
                return  Enums.Area.Striker;
            default:
                return Enums.Area.Keeper;
        }
    }

    public static int calculateFantasyPoints(LeagueTableJson json, FantasyTeam team) {
        long points = 0;

        for (StandingJson standing : json.getStandings()) {
            if (Objects.equals(team.gk.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.gk.player.price);
            }
            if (Objects.equals(team.lb.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.lb.player.price);
            }
            if (Objects.equals(team.lcb.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.lcb.player.price);
            }
            if (Objects.equals(team.rcb.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.rcb.player.price);
            }
            if (Objects.equals(team.rb.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.rb.player.price);
            }
            if (Objects.equals(team.lm.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.lm.player.price);
            }
            if (Objects.equals(team.lcm.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.lcm.player.price);
            }
            if (Objects.equals(team.rcm.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.rcm.player.price);
            }
            if (Objects.equals(team.rm.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.rm.player.price);
            }
            if (Objects.equals(team.ls.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.ls.player.price);
            }
            if (Objects.equals(team.rs.player.team.name, standing.getTeamName())) {
                points += calculateFantasyPlayerPoints(standing, team.rs.player.price);
            }
        }

        return (int) points;
    }

    private static long calculateFantasyPlayerPoints(StandingJson standing, float playerValue) {
        long points = 0;

        points += Long.valueOf(standing.getHomeStats().getWins() * 7);
        points += Long.valueOf(standing.getHomeStats().getDraws() * 2);
        points += Long.valueOf(standing.getHomeStats().getLosses() * -1);
        points += Long.valueOf(standing.getAwayStats().getWins() * 8);
        points += Long.valueOf(standing.getAwayStats().getDraws() * 3);
        points += Long.valueOf(Math.round(standing.getHomeStats().getGoalsFor() / 3) * 3);
        points += Long.valueOf(Math.round(standing.getHomeStats().getGoalsAgainst() / 3) * -1);
        points += Long.valueOf(Math.round(standing.getAwayStats().getGoalsFor() / 3) * 4);

        if (playerValue > 35000000){
            points = Math.round(Double.valueOf(points * 1.3));
            return points;
        }

        if (playerValue > 20000000) {
            points = Math.round(Double.valueOf(points * 1.2));
            return points;
        }

        if (playerValue > 10000000) {
            points = Math.round(Double.valueOf(points * 1.1));
            return points;
        }

        return points;
    }

}
