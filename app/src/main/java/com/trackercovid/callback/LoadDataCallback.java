package com.trackercovid.callback;

public interface LoadDataCallback<T> {

    void onDataLoaded(T data);

    void onNoDataLoaded();

    void onError(Throwable e);
}
