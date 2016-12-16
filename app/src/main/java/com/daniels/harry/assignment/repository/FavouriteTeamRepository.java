package com.daniels.harry.assignment.repository;

import com.daniels.harry.assignment.model.FavouriteTeam;

import java.util.List;

public class FavouriteTeamRepository {

    public static void save(FavouriteTeam team) {
        team.save();
    }

    public static void saveAll(List<FavouriteTeam> teams) {
        for (FavouriteTeam t : teams) {
            t.save();
        }
    }

    public static List<FavouriteTeam> getAll() {
        return FavouriteTeam.listAll(FavouriteTeam.class);
    }
}
