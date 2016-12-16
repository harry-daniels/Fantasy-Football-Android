package com.daniels.harry.assignment.handler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bluelinelabs.logansquare.LoganSquare;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.activity.FavouritePickerActivity;
import com.daniels.harry.assignment.dialog.ErrorDialogs;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.singleton.CurrentUser;
import com.daniels.harry.assignment.singleton.HttpRequestQueue;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler <T> {

    private T mResultObject;
    private Context mContext;
    private Activity mActivity;
    private HttpRequestQueue mRequestQueue;
    private RequestQueue.RequestFinishedListener mRequestFinishedListener;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mNetworkInfo;

    public HttpRequestHandler(Context c, Activity a, RequestQueue.RequestFinishedListener listener) {
        mContext = c;
        mActivity = a;
        mRequestFinishedListener = listener;
        mRequestQueue = HttpRequestQueue.getInstance(c);
        mConnectivityManager = (ConnectivityManager)c.getSystemService(mContext.CONNECTIVITY_SERVICE);
    }

    public boolean isNetworkConnected(){
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting();
    }

    public void removeRequestFinishedListener(){
        try {
            mRequestQueue.removeRequestFinishedListener(mRequestFinishedListener, mContext);
        } catch (Exception e) {
            ErrorDialogs.showErrorDialog(mContext,
                    mContext.getString(R.string.dialog_title_generic_error),
                    mContext.getString(R.string.dialog_message_generic_error) +
                    e.getMessage());
        }
    }

    public void addRequestFinishedListener() {
        try {
            mRequestQueue.addRequestFinishedListener(mRequestFinishedListener, mContext);
        } catch (Exception e) {
            ErrorDialogs.showErrorDialog(mContext,
                    mContext.getString(R.string.dialog_title_generic_error),
                    mContext.getString(R.string.dialog_message_generic_error) +
                            e.getMessage());
        }
    }

    public void sendJsonObjectRequest(String url, String requestTag, final Class<T> jsonObjectType) {
        if (isNetworkConnected()) {
            JsonObjectRequest request = createJsonObjectRequest(url, requestTag, jsonObjectType);
            mRequestQueue.addRequest(request, mContext);
        } else {
            ErrorDialogs.showErrorDialog(mContext,
                    mContext.getString(R.string.dialog_title_network_error),
                    mContext.getString(R.string.dialog_message_network_error));
        }
    }

    private JsonObjectRequest createJsonObjectRequest(String url, String requestTag, final Class<T> jsonObjectType){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mResultObject = LoganSquare.parse(response.toString(), jsonObjectType);
                        } catch (IOException e) {
                            if (!mActivity.isFinishing()) {
                                ErrorDialogs.showErrorDialog(mContext,
                                        mContext.getString(R.string.dialog_title_parser_error),
                                        mContext.getString(R.string.dialog_message_parser_error));
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                if (!mActivity.isFinishing()) {
                    ErrorDialogs.showErrorDialog(mContext,
                            mContext.getString(R.string.dialog_title_http_error),
                            mContext.getString(R.string.dialog_message_http_error) +
                                    e.getMessage());
                }
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };
        request.setTag(requestTag);

        return request;
    }

    //TODO: Move Api Key
    public Map<String, String> getHttpHeaders() {
        Map<String, String> params = new HashMap<>();
        params.put("X-Auth-Token", "debf9352e2b745759c3eb424fc776d6d");
        return params;
    }

    public T getResultObject() {
        return mResultObject;
    }
}
