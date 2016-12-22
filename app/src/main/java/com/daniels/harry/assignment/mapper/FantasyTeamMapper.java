package com.daniels.harry.assignment.mapper;


import com.daniels.harry.assignment.jsonobject.AllTeamsJson;
import com.daniels.harry.assignment.jsonobject.FavouriteTeamJson;
import com.daniels.harry.assignment.jsonobject.LeagueTableJson;
import com.daniels.harry.assignment.jsonobject.StandingJson;
import com.daniels.harry.assignment.model.FantasyTeam;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Fixture;
import com.daniels.harry.assignment.model.HomeAwayStat;
import com.daniels.harry.assignment.util.Calculators;
import com.daniels.harry.assignment.viewmodel.FantasyPlayerViewModel;
import com.daniels.harry.assignment.viewmodel.FantasyTeamDashboardViewModel;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamDashboardViewModel;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamPickerViewModel;
import com.daniels.harry.assignment.viewmodel.FixtureViewModel;
import com.daniels.harry.assignment.viewmodel.TeamEditorViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FantasyTeamMapper {

    public static TeamEditorViewModel modelToTeamEditorViewModel(FantasyTeam team) {
        TeamEditorViewModel vm = new TeamEditorViewModel();
        vm.setBudget(Calculators.calculateBudgetString(team.remainingBudget));

        FantasyPlayerViewModel gk = new FantasyPlayerViewModel();
        gk.setIconResource(Calculators.calculateShirtColourResource(team.gk.colour));
        gk.setSurname(team.gk.player == null ? "SELECT" : team.gk.player.surname);

        FantasyPlayerViewModel lb = new FantasyPlayerViewModel();
        lb.setIconResource(Calculators.calculateShirtColourResource(team.lb.colour));
        lb.setSurname(team.lb.player == null ? "SELECT" : team.lb.player.surname);

        FantasyPlayerViewModel lcb = new FantasyPlayerViewModel();
        lcb.setIconResource(Calculators.calculateShirtColourResource(team.lcb.colour));
        lcb.setSurname(team.lcb.player == null ? "SELECT" : team.lcb.player.surname);

        FantasyPlayerViewModel rcb = new FantasyPlayerViewModel();
        rcb.setIconResource(Calculators.calculateShirtColourResource(team.rcb.colour));
        rcb.setSurname(team.rcb.player == null ? "SELECT" : team.rcb.player.surname);

        FantasyPlayerViewModel rb = new FantasyPlayerViewModel();
        rb.setIconResource(Calculators.calculateShirtColourResource(team.rb.colour));
        rb.setSurname(team.rb.player == null ? "SELECT" : team.rb.player.surname);

        FantasyPlayerViewModel lm = new FantasyPlayerViewModel();
        lm.setIconResource(Calculators.calculateShirtColourResource(team.lm.colour));
        lm.setSurname(team.lm.player == null ? "SELECT" : team.lm.player.surname);

        FantasyPlayerViewModel lcm = new FantasyPlayerViewModel();
        lcm.setIconResource(Calculators.calculateShirtColourResource(team.lcm.colour));
        lcm.setSurname(team.lcm.player == null ? "SELECT" : team.lcm.player.surname);

        FantasyPlayerViewModel rcm = new FantasyPlayerViewModel();
        rcm.setIconResource(Calculators.calculateShirtColourResource(team.rcm.colour));
        rcm.setSurname(team.rcm.player == null ? "SELECT" : team.rcm.player.surname);

        FantasyPlayerViewModel rm = new FantasyPlayerViewModel();
        rm.setIconResource(Calculators.calculateShirtColourResource(team.rm.colour));
        rm.setSurname(team.rm.player == null ? "SELECT" : team.rm.player.surname);

        FantasyPlayerViewModel ls = new FantasyPlayerViewModel();
        ls.setIconResource(Calculators.calculateShirtColourResource(team.ls.colour));
        ls.setSurname(team.ls.player == null ? "SELECT" : team.ls.player.surname);

        FantasyPlayerViewModel rs = new FantasyPlayerViewModel();
        rs.setIconResource(Calculators.calculateShirtColourResource(team.rs.colour));
        rs.setSurname(team.rs.player == null ? "SELECT" : team.rs.player.surname);

        vm.setGk(gk);
        vm.setLb(lb);
        vm.setLcb(lcb);
        vm.setRcb(rcb);
        vm.setRb(rb);
        vm.setLm(lm);
        vm.setLcm(lcm);
        vm.setRcm(rcm);
        vm.setRm(rm);
        vm.setLs(ls);
        vm.setRs(rs);

        return vm;
    }

    public static FantasyTeamDashboardViewModel modelToDashboardViewModel(FantasyTeam team) {
        FantasyTeamDashboardViewModel vm = new FantasyTeamDashboardViewModel();

        vm.setName(team.name);
        vm.setPoints(String.valueOf(team.points));
        vm.setLastUpdated(team.lastUpdated == null ? "N/A" : team.lastUpdated.toString());
        vm.setBudget(Calculators.calculateBudgetString(team.remainingBudget));

        return vm;
    }

    public static FantasyTeamDashboardViewModel jsonToViewModel(LeagueTableJson json, FantasyTeam team) {
        FantasyTeamDashboardViewModel vm = new FantasyTeamDashboardViewModel();

        vm.setPoints(String.valueOf(Calculators.calculateFantasyPoints(json, team)));
        vm.setName(team.name);
        vm.setLastUpdated(new Date().toString());
        vm.setBudget(Calculators.calculateBudgetString(team.remainingBudget));

        return vm;
    }

}
