package com.daniels.harry.assignment.handler;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bluelinelabs.logansquare.LoganSquare;
import com.daniels.harry.assignment.singleton.HttpRequestQueue;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler <T> {

    private T mResultObject;
    private Context mContext;
    private HttpRequestQueue mRequestQueue;
    private RequestQueue.RequestFinishedListener mRequestFinishedListener;

    public HttpRequestHandler(Context c, RequestQueue.RequestFinishedListener listener) {
        mContext = c;
        mRequestFinishedListener = listener;
        mRequestQueue = HttpRequestQueue.getInstance(c);
    }

    public void removeRequestFinishedListener(){
        mRequestQueue.removeRequestFinishedListener(mRequestFinishedListener, mContext);
    }

    public void addRequestFinishedListener() {
        mRequestQueue.addRequestFinishedListener(mRequestFinishedListener, mContext);
    }

    public void sendJsonObjectRequest(String url, String requestTag, final Class<T> jsonObjectType) {
        JsonObjectRequest request = createJsonObjectRequest(url, requestTag, jsonObjectType);
        mRequestQueue.addRequest(request, mContext);
    }

    private JsonObjectRequest createJsonObjectRequest(String url, String requestTag, final Class<T> jsonObjectType){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mResultObject = LoganSquare.parse(response.toString(), jsonObjectType);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Add error handling
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
