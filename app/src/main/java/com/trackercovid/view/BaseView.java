package com.trackercovid.view;

public interface BaseView {
    void startLoading();

    void stopLoading();

    void showError(String errorMessage);
}
