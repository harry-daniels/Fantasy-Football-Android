package com.daniels.harry.assignment.singleton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class HttpRequestQueue {

    private static HttpRequestQueue mInstance;
    private RequestQueue mRequestQueue;

    private HttpRequestQueue(Context c) {
        mRequestQueue = getRequestQueue(c);
    }


    public static synchronized HttpRequestQueue getInstance (Context c){
        if (mInstance == null) {
            mInstance = new HttpRequestQueue(c);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(Context c) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(c.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addRequest(Request<T> req, Context c) {
        getRequestQueue(c).add(req);
    }

    public void addRequestFinishedListener(RequestQueue.RequestFinishedListener listener, Context c) {
        getRequestQueue(c).addRequestFinishedListener(listener);
    }

    public void removeRequestFinishedListener(RequestQueue.RequestFinishedListener listener, Context c) {
        getRequestQueue(c).removeRequestFinishedListener(listener);
    }
}
