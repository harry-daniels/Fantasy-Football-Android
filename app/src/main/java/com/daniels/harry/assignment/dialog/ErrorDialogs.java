package com.daniels.harry.assignment.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class ErrorDialogs {

    public static void showNetworkErrorDialog(Context c) {

        new AlertDialog.Builder(c)
                .setTitle("Network Error")
                .setMessage("Unable to find an internet connection. " +
                        "Please check you have a Wi-Fi or mobile data connection and try again.")
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    public static void showLocationErrorDialog(Context c) {
        new AlertDialog.Builder(c)
                .setTitle("Location Error")
                .setMessage("Unable to retrieve your location. " +
                        "Ensure you have granted Fantasy PL permission to use your location and " +
                        "have your mobile location enabled.")
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    public static void showSignInErrorDialog(Context c) {
        new AlertDialog.Builder(c)
                .setTitle("Sign In Error")
                .setMessage("Unable to sign in using your Google account. Please try again.")
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    public static void showVolleyErrorDialog(Context c, String message) {
        new AlertDialog.Builder(c)
                .setTitle("HTTP Request Error")
                .setMessage("Error requesting data from web API: " + message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    public static void showParsingErrorDialog(Context c, String message) {
        new AlertDialog.Builder(c)
                .setTitle("Parser Error")
                .setMessage("Error parsing HTTP response: " + message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    public static void showNoStatisticsErrorDialog(Context c) {
        new AlertDialog.Builder(c)
                .setTitle("Nothing to Show")
                .setMessage("Your selected team has no statistics saved yet. Try reloading this page when you have an internet connection.")
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    public static void showGenericErrorDialog(Context c, String message) {
        new AlertDialog.Builder(c)
                .setTitle("Error")
                .setMessage("Error performing action: " + message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }
}
