package com.daniels.harry.assignment.listener;

public interface OnDbSaveAsyncListener {
    void onDbSaveSuccess(String tag);
    void onDbSaveFailure(String tag);
}
