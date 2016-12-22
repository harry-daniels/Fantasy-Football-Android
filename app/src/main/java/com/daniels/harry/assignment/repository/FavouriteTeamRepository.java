package com.daniels.harry.assignment.repository;

import com.daniels.harry.assignment.model.FavouriteTeam;

import java.util.List;

public class FavouriteTeamRepository {

    public static void save(FavouriteTeam team) {
        team.save();
    }
}