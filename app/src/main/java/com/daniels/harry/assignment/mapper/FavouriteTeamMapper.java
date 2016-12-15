package com.daniels.harry.assignment.mapper;


import com.daniels.harry.assignment.jsonobject.FavouriteTeamJson;
import com.daniels.harry.assignment.jsonobject.StandingJson;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Fixture;
import com.daniels.harry.assignment.model.HomeAwayStat;
import com.daniels.harry.assignment.util.Calculators;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamDashboardViewModel;
import com.daniels.harry.assignment.viewmodel.FixtureViewModel;

public class FavouriteTeamMapper {

    public static FavouriteTeam jsonToModel(FavouriteTeamJson teamJson, StandingJson standingJson, FavouriteTeam model, Fixture prevFixture,
                                    Fixture nextFixture, HomeAwayStat homeStat, HomeAwayStat awayStat){
        if(model == null)
            model = new FavouriteTeam();

        model.position = standingJson.getPosition();
        model.playedGames = standingJson.getPlayedGames();
        model.points = standingJson.getPoints();
        model.homeStat = homeStat;
        model.awayStat = awayStat;
        model.previousFixture = prevFixture;
        model.nextFixture = nextFixture;

        model.save();

        return new FavouriteTeam();
    }

    public static FavouriteTeamDashboardViewModel modelToViewModel(FavouriteTeam team){
        FavouriteTeamDashboardViewModel dashboardVm = new FavouriteTeamDashboardViewModel();
        FixtureViewModel nextFixtureVm = new FixtureViewModel();
        FixtureViewModel prevFixtureVm = new FixtureViewModel();

        nextFixtureVm.setOppositionName(team.nextFixture.oppositionName);
        nextFixtureVm.setScore(Calculators.calculateScoreString(team.nextFixture.homeScore, team.nextFixture.awayScore));
        nextFixtureVm.setHome(team.nextFixture.isHome);
        nextFixtureVm.setOutcome(Calculators.calculateOutcome(team.nextFixture.isHome, team.nextFixture.homeScore, team.nextFixture.awayScore));
        nextFixtureVm.setDate(Calculators.calculateDateString(team.nextFixture.date));
        nextFixtureVm.setCrestUrl(team.nextFixture.crestUrl);

        prevFixtureVm.setOppositionName(team.previousFixture.oppositionName);
        prevFixtureVm.setScore(Calculators.calculateScoreString(team.previousFixture.homeScore, team.previousFixture.awayScore));
        prevFixtureVm.setHome(team.previousFixture.isHome);
        prevFixtureVm.setOutcome(Calculators.calculateOutcome(team.previousFixture.isHome, team.previousFixture.homeScore, team.previousFixture.awayScore));
        prevFixtureVm.setDate(Calculators.calculateDateString(team.previousFixture.date));
        prevFixtureVm.setCrestUrl(team.previousFixture.crestUrl);

        dashboardVm.setTeamName(team.name);
        dashboardVm.setPosition(team.position);
        dashboardVm.setGround(team.ground);
        dashboardVm.setCrestUrl(team.crestUrl);
        dashboardVm.setPoints(team.points);
        dashboardVm.setDistance(String.valueOf(team.distance));
        dashboardVm.setWins(String.valueOf(team.homeStat.wins + team.awayStat.wins));
        dashboardVm.setDraws(String.valueOf(team.homeStat.draws + team.awayStat.draws));
        dashboardVm.setLosses(String.valueOf(team.homeStat.losses + team.awayStat.losses));
        dashboardVm.setPrevFixture(prevFixtureVm);
        dashboardVm.setNextFixture(nextFixtureVm);

        return dashboardVm;
    }
}
