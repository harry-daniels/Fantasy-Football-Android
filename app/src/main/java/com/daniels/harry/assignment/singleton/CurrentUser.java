package com.daniels.harry.assignment.singleton;

import com.daniels.harry.assignment.constant.Constants;
import com.daniels.harry.assignment.constant.Enums;
import com.daniels.harry.assignment.model.FantasyPlayer;
import com.daniels.harry.assignment.model.FantasyTeam;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.Player;
import com.daniels.harry.assignment.model.User;
import com.daniels.harry.assignment.util.Calculators;

public class CurrentUser {

    private static CurrentUser mInstance;
    private static String mUserId;

    private User mUser;

    private CurrentUser() {
        mUser = getCurrentUser();
    }


    // singleton instance of user entity currently signed in
    public static synchronized CurrentUser getInstance() {
        if (mInstance == null) {
            mInstance = new CurrentUser();
        }
        return mInstance;
    }

    public static void setUserId(String userId) {
        mUserId = userId;
    }

    private User getCurrentUser() {
        if (mUser == null) {
            mUser = User.find(User.class, "google_Id = ?", mUserId).get(0);
        }

        return mUser;
    }

    public void save() {
        getCurrentUser().save();
    }

    public FavouriteTeam getFavouriteTeam() {
        return mUser.favouriteTeam;
    }

    public void setFavouriteTeam(FavouriteTeam team) {
        mUser.favouriteTeam = team;
        save();
    }

    // create initial fantast team setup
    public void setupFantasyTeam() {
        FantasyTeam ft = new FantasyTeam();
        ft.name = Calculators.calculateName(mUser.name, false) + "FC";
        ft.remainingBudget = Constants.STARTING_BUDGET;

        FantasyPlayer fp = new FantasyPlayer();
        fp.isSet = false;
        fp.colour = Enums.ShirtColour.TRANSLUCENT;
        fp.save();

        ft.gk = fp;
        ft.lb = fp;
        ft.lcb = fp;
        ft.rcb = fp;
        ft.rb = fp;
        ft.lm = fp;
        ft.lcm = fp;
        ft.rcm = fp;
        ft.rm = fp;
        ft.ls = fp;
        ft.rs = fp;

        ft.save();
        mUser.fantasyTeam = ft;
        save();
    }

    // add a new player to the fantasy team
    public void setPlayer(Enums.Position position, Player player) {
        FantasyTeam team = mUser.fantasyTeam;
        team.remainingBudget -= player.price;

        FantasyPlayer fantasyPlayer = new FantasyPlayer();

        fantasyPlayer.player = player;
        fantasyPlayer.position = position;
        fantasyPlayer.isSet = true;
        fantasyPlayer.colour = player.team.colour;

        fantasyPlayer.save();

        switch (position) {
            case GK:
                if (team.gk.player != null) {
                    team.remainingBudget += team.gk.player.price;
                }
                team.gk = fantasyPlayer;
                break;
            case LB:
                if (team.lb.player != null) {
                    team.remainingBudget += team.lb.player.price;
                }
                team.lb = fantasyPlayer;
                break;
            case LCB:
                if (team.lcb.player != null) {
                    team.remainingBudget += team.lcb.player.price;
                }
                team.lcb = fantasyPlayer;
                break;
            case RCB:
                if (team.rcb.player != null) {
                    team.remainingBudget += team.rcb.player.price;
                }
                team.rcb = fantasyPlayer;
                break;
            case RB:
                if (team.rb.player != null) {
                    team.remainingBudget += team.rb.player.price;
                }
                team.rb = fantasyPlayer;
                break;
            case LM:
                if (team.lm.player != null) {
                    team.remainingBudget += team.lm.player.price;
                }
                team.lm = fantasyPlayer;
                break;
            case LCM:
                if (team.lcm.player != null) {
                    team.remainingBudget += team.lcm.player.price;
                }
                team.lcm = fantasyPlayer;
                break;
            case RCM:
                if (team.rcm.player != null) {
                    team.remainingBudget += team.rcm.player.price;
                }
                team.rcm = fantasyPlayer;
                break;
            case RM:
                if (team.rm.player != null) {
                    team.remainingBudget += team.rm.player.price;
                }
                team.rm = fantasyPlayer;
                break;
            case LS:
                if (team.ls.player != null) {
                    team.remainingBudget += team.ls.player.price;
                }
                team.ls = fantasyPlayer;
                break;
            case RS:
                if (team.rs.player != null) {
                    team.remainingBudget += team.rs.player.price;
                }
                team.rs = fantasyPlayer;
                break;
        }

        team.save();
        save();
    }

    public FantasyTeam getFantasyTeam() {
        return mUser.fantasyTeam;
    }

    public Player getPlayer(Enums.Position position) {

        switch (position) {
            case GK:
                if (mUser.fantasyTeam.gk.player != null)
                    return mUser.fantasyTeam.gk.player;
                break;
            case LB:
                if (mUser.fantasyTeam.lb.player != null)
                    return mUser.fantasyTeam.lb.player;
                break;
            case LCB:
                if (mUser.fantasyTeam.lcb.player != null)
                    return mUser.fantasyTeam.lcb.player;
                break;
            case RCB:
                if (mUser.fantasyTeam.rcb.player != null)
                    return mUser.fantasyTeam.rcb.player;
                break;
            case RB:
                if (mUser.fantasyTeam.rb.player != null)
                    return mUser.fantasyTeam.rb.player;
                break;
            case LM:
                if (mUser.fantasyTeam.lm.player != null)
                    return mUser.fantasyTeam.lm.player;
                break;
            case LCM:
                if (mUser.fantasyTeam.lcm.player != null)
                    return mUser.fantasyTeam.lcm.player;
                break;
            case RCM:
                if (mUser.fantasyTeam.rcm.player != null)
                    return mUser.fantasyTeam.rcm.player;
                break;
            case RM:
                if (mUser.fantasyTeam.rm.player != null)
                    return mUser.fantasyTeam.rm.player;
                break;
            case LS:
                if (mUser.fantasyTeam.ls.player != null)
                    return mUser.fantasyTeam.ls.player;
                break;
            case RS:
                if (mUser.fantasyTeam.rs.player != null)
                    return mUser.fantasyTeam.rs.player;
                break;
        }

        return null;
    }

    public boolean isAllPlayersSelected() {
        if (mUser.fantasyTeam.gk.isSet && mUser.fantasyTeam.lb.isSet &&
                mUser.fantasyTeam.lcb.isSet && mUser.fantasyTeam.rcb.isSet &&
                mUser.fantasyTeam.rb.isSet && mUser.fantasyTeam.lm.isSet &&
                mUser.fantasyTeam.lcm.isSet && mUser.fantasyTeam.rcm.isSet &&
                mUser.fantasyTeam.rm.isSet && mUser.fantasyTeam.ls.isSet &&
                mUser.fantasyTeam.rs.isSet) {
            return true;
        } else {
            return false;
        }
    }
}