package com.daniels.harry.assignment.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.daniels.harry.assignment.constant.Enums;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Player;

import java.util.List;

public class PlayerRepository extends AsyncTask<List<Player>, Integer, Boolean>{

    public PlayerRepository() {

    }

    private onFinish o;

    public static void save(FavouriteTeam team) {
        team.save();
    }

    public void saveAll(List<Player> players, Context c, onFinish of) {
        o = of;

        execute(players);
    }

    public static List<Player> getAll() {
        return Player.listAll(Player.class);
    }

    public static List<Player> getByArea(Enums.Area area) {
        return Player.find(Player.class, "area = ?", area.toString());
    }

    @Override
    protected Boolean doInBackground(List<Player>... params) {
        Player.saveInTx(params[0]);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        o.callback();
    }

    public interface onFinish {
        void callback();
    }
}
