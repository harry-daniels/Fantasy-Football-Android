package com.daniels.harry.assignment.mapper;


import com.daniels.harry.assignment.jsonobject.AllPlayersJson;
import com.daniels.harry.assignment.jsonobject.PlayerJson;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Player;
import com.daniels.harry.assignment.util.Calculators;
import com.daniels.harry.assignment.viewmodel.SelectPlayerViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlayerMapper {

    public static List<Player> jsonToModels(AllPlayersJson json, String teamId) {
        List<Player> players = new ArrayList<Player>();

        List<FavouriteTeam> possibleTeams = FavouriteTeam.find(FavouriteTeam.class, "api_Id = ?", teamId);
        FavouriteTeam t = possibleTeams.get(0);

        for (PlayerJson p : json.players) {
            Player player = new Player();
            player.firstName = Calculators.calculateName(p.getName(), true);
            player.surname = Calculators.calculateName(p.getName(), false);
            player.price = Calculators.calculatePlayerPrice(p.getValue());
            player.team = t;
            player.area = Calculators.calculatePlayerAreaFromString(p.getPosition());

            players.add(player);
        }

        return players;
    }

    public static SelectPlayerViewModel modelToViewModel(Player player) {
        SelectPlayerViewModel vm = new SelectPlayerViewModel();

        vm.setId(player.getId());
        vm.setTeamName(player.team.name);
        vm.setName(player.firstName + " " + player.surname);
        vm.setPrice(Calculators.calculateShortPlayerValue(player.price));
        vm.setIconResource(Calculators.calculateShirtColourResource(player.team.colour));

        return vm;
    }
}
