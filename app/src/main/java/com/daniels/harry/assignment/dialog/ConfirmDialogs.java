package com.daniels.harry.assignment.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class ConfirmDialogs {

    public static void showNetworkErrorDialog(Context c) {
        new AlertDialog.Builder(c)
                .setTitle("Network Error")
                .setMessage("Unable to find an internet connection. " +
                        "Please check you have a Wi-Fi or mobile data connection and try again.")
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }
}
