package com.daniels.harry.assignment.mapper;


import android.location.Location;

import com.daniels.harry.assignment.jsonobject.AllTeamsJson;
import com.daniels.harry.assignment.jsonobject.FavouriteTeamJson;
import com.daniels.harry.assignment.jsonobject.StandingJson;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Fixture;
import com.daniels.harry.assignment.model.HomeAwayStat;
import com.daniels.harry.assignment.util.Calculators;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamDashboardViewModel;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamPickerViewModel;
import com.daniels.harry.assignment.viewmodel.FixtureViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteTeamMapper {

    public static FavouriteTeam jsonToModel(FavouriteTeamJson teamJson, StandingJson standingJson, FavouriteTeam model, Fixture prevFixture,
                                    Fixture nextFixture, HomeAwayStat homeStat, HomeAwayStat awayStat){
        if(model == null)
            model = new FavouriteTeam();

        model.name = teamJson.getName();
        model.crestUrl = teamJson.getCrestUrl();
        model.apiId = teamJson.getId();
        model.ground = teamJson.getGround();
        model.groundLat = teamJson.getLatitude();
        model.groundLong = teamJson.getLongitude();
        model.colour = teamJson.getColour();
        model.position = standingJson.getPosition();
        model.playedGames = standingJson.getPlayedGames();
        model.points = standingJson.getPoints();
        model.homeStat = homeStat;
        model.awayStat = awayStat;
        model.previousFixture = prevFixture;
        model.nextFixture = nextFixture;
        model.populated = true;

        model.save();

        return new FavouriteTeam();
    }

    public static List<FavouriteTeam> jsonToModels(AllTeamsJson json) {
        List<FavouriteTeam> teams = new ArrayList<FavouriteTeam>();

        for (FavouriteTeamJson j : json.getTeams()){
            FavouriteTeam f = new FavouriteTeam();
            f.apiId = j.getId();
            f.name = j.getName();
            f.crestUrl = j.getCrestUrl();
            f.groundLat = j.getLatitude();
            f.groundLong = j.getLongitude();
            f.colour = j.getColour();

            teams.add(f);
        }

        return teams;
    }

    public static FavouriteTeamDashboardViewModel modelToDashboardViewModel(FavouriteTeam team){
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
        dashboardVm.setPosition(Calculators.calculatePositionString(String.valueOf(team.position)));
        dashboardVm.setGround(team.ground);
        dashboardVm.setCrestUrl(team.crestUrl);
        dashboardVm.setPoints(String.valueOf(team.points));
        dashboardVm.setDistance(String.valueOf(team.distance));
        dashboardVm.setWins(String.valueOf(team.homeStat.wins + team.awayStat.wins));
        dashboardVm.setDraws(String.valueOf(team.homeStat.draws + team.awayStat.draws));
        dashboardVm.setLosses(String.valueOf(team.homeStat.losses + team.awayStat.losses));
        dashboardVm.setPrevFixture(prevFixtureVm);
        dashboardVm.setNextFixture(nextFixtureVm);

        return dashboardVm;
    }

    public static FavouriteTeamPickerViewModel modelToPickerViewModel(FavouriteTeam team) {
        FavouriteTeamPickerViewModel vm = new FavouriteTeamPickerViewModel();

        vm.setCrestUrl(team.crestUrl);
        vm.setGroundLat(team.groundLat);
        vm.setGroundLong(team.groundLong);
        vm.setTeamName(team.name);
        vm.setId(team.apiId);

        return vm;
    }

}
