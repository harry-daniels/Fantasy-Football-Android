package com.daniels.harry.assignment.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class ErrorDialogs {

    public static void showErrorDialog(Context c, String title, String message) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }
}
