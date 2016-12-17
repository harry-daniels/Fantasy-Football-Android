package com.daniels.harry.assignment.repository;

import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Player;

import java.util.List;

public class PlayerRepository {

    public static void save(FavouriteTeam team) {
        team.save();
    }

    public static void saveAll(List<Player> players) {
        for (Player p : players) {
            p.save();
        }
    }

    public static List<Player> getAll() {
        return Player.listAll(Player.class);
    }
}
