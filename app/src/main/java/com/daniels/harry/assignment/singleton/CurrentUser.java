package com.daniels.harry.assignment.singleton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.User;

import java.util.List;

public class CurrentUser {

    private static CurrentUser mInstance;
    private static String mUserId;

    private User mUser;

    private CurrentUser() {
        mUser = getCurrentUser();
    }

    public static synchronized CurrentUser getInstance (){
        if (mInstance == null) {
            mInstance = new CurrentUser();
        }
        return mInstance;
    }

    public static void setUserId(String userId)
    {
        mUserId = userId;
    }

    private User getCurrentUser() {
        if (mUser == null) {
            mUser = User.find(User.class, "google_Id = ?", mUserId).get(0);
        }

        return mUser;
    }

    public void save(){
        getCurrentUser().save();
    }

    public FavouriteTeam getFavouriteTeam()
    {
        return mUser.favouriteTeam;
    }

    public void setFavouriteTeam(FavouriteTeam team){
        mUser.favouriteTeam = team;
        save();
    }
}
