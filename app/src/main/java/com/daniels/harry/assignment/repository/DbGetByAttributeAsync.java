package com.daniels.harry.assignment.repository;

import android.os.AsyncTask;

import com.daniels.harry.assignment.listener.OnDbGetAsyncListener;
import com.daniels.harry.assignment.model.Player;
import com.orm.SugarRecord;

import java.util.List;

public class DbGetByAttributeAsync<T extends SugarRecord> extends AsyncTask<Void, Integer, List<T>> {

    private OnDbGetAsyncListener<T> mListener;
    private Class<T> mType;
    private String mTag;
    private String mAttribute;
    private String[] mValue;

    public DbGetByAttributeAsync(String attribute, String[] value, final Class<T> type, OnDbGetAsyncListener listener, String tag) {
        mType = type;
        mListener = listener;
        mTag = tag;
        mAttribute = attribute;
        mValue = value;
    }

    @Override
    protected List<T> doInBackground(Void... params) {
        try {
            List<T> returnObjects = T.find(mType, mAttribute, mValue);

            return returnObjects;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<T> result) {

        if (result != null) {
            mListener.onDbGetSuccess(mTag, result);
        } else {
            mListener.onDbGetFailure(mTag);
        }
    }
}
