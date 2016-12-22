package com.daniels.harry.assignment.listener;

import java.util.List;

// interface for async database reading listeners
public interface OnDbGetAsyncListener <T> {

    void onDbGetSuccess(String tag, List<T> result);
    void onDbGetFailure(String tag);
}
