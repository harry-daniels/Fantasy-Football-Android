package com.daniels.harry.assignment.model;


import com.daniels.harry.assignment.constant.Enums;
import com.orm.SugarRecord;

public class FantasyPlayer extends SugarRecord {
    public Enums.Position position;
    public boolean isSet;
    public Player player;
    public Enums.ShirtColour colour;

    public FantasyPlayer() {

    }
}
