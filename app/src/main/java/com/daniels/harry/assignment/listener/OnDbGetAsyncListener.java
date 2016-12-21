package com.daniels.harry.assignment.listener;

import java.util.List;

public interface OnDbGetAsyncListener <T> {

    void onDbGetSuccess(String tag, List<T> result);
    void onDbGetFailure(String tag);
}
