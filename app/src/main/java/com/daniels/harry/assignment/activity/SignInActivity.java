package com.daniels.harry.assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.dialog.ErrorDialogs;
import com.daniels.harry.assignment.model.User;
import com.daniels.harry.assignment.singleton.CurrentUser;
import com.daniels.harry.assignment.constant.Constants;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.List;

public class SignInActivity extends AppCompatActivity  implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
    }

    // send an intent to the google sign in service upon button click
    public void launchSignIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constants.REQUEST_SIGN_IN);
    }

    // check to see if a user has a valid token still available for silent sign in
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        if (pendingResult.isDone()) {
            GoogleSignInResult signInResult = pendingResult.get();
            handleSignInResult(signInResult);
        } else {
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    if (googleSignInResult.isSuccess()) {
                        handleSignInResult(googleSignInResult);
                    }
                }
            });
        }
    }

    // upon the return from the google sign in service, handle the result
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent i) {
        super.onActivityResult(reqCode, resCode, i);

        if (reqCode == Constants.REQUEST_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(i);
            handleSignInResult(result);
        }
    }

    // ensure that the user has signed in successfully and retrieve or insert a new database entry for the user.
    // then start the main dashboard activity.
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            List<User> possibleUsers = User.find(User.class, "google_Id = ?", account.getId());
            CurrentUser.setUserId(account.getId());

            if(possibleUsers.isEmpty()) {
                User newUser = new User(account.getId(), account.getDisplayName());
                newUser.save();
                CurrentUser.getInstance().setupFantasyTeam();
            }

            Intent i = new Intent(this, DashboardActivity.class);
            startActivity(i);
            finish();

        } else {
            ErrorDialogs.showErrorDialog(this,
                    getString(R.string.dialog_title_signin_error),
                    getString(R.string.dialog_message_signin_error));
        }
    }

    // if google api client failed to connect, show an error dialog
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        ErrorDialogs.showErrorDialog(this,
                getString(R.string.dialog_title_http_error),
                getString(R.string.dialog_message_http_error));
    }

    // handle google sign in button click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                launchSignIn();
                break;
        }
    }
}
