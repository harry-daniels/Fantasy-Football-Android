package com.daniels.harry.assignment.listener;

// interface for async database writing listeners
public interface OnDbSaveAsyncListener {
    void onDbSaveSuccess(String tag);
    void onDbSaveFailure(String tag);
}
