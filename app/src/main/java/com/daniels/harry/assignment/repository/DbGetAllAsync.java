package com.daniels.harry.assignment.repository;

import android.os.AsyncTask;

import com.daniels.harry.assignment.listener.OnDbGetAsyncListener;
import com.daniels.harry.assignment.listener.OnDbSaveAsyncListener;
import com.orm.SugarRecord;

import java.util.List;

public class DbGetAllAsync<T extends SugarRecord> extends AsyncTask<Void, Integer, List<T>> {

    private OnDbGetAsyncListener<T> mListener;
    private Class<T> mType;
    private String mTag;

    public DbGetAllAsync(final Class<T> type, OnDbGetAsyncListener listener, String tag) {
        mType = type;
        mListener = listener;
        mTag = tag;
    }

    @Override
    protected List<T> doInBackground(Void... params) {
        try {
            List<T> returnObjects = T.listAll(mType);
            return returnObjects;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<T> result) {
        mListener.onDbGetSuccess(mTag, result);
        mListener.onDbGetFailure(mTag);
    }
}
