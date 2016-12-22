package com.daniels.harry.assignment.repository;

import com.daniels.harry.assignment.constant.Enums;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Player;

import java.util.List;

public class PlayerRepository {

    public static void save(FavouriteTeam team) {
        team.save();
    }
}