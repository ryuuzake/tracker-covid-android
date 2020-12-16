package com.trackercovid.callback;

public interface RepositoryCallback<T> {
    void onSuccess(T data);

    void onEmpty();

    void onError(String errorMessage);
}
