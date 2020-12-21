package com.trackercovid.contract;

import com.trackercovid.view.BaseView;

public interface LoginContract {
    interface View extends BaseView {
        void redirectToHome();
    }

    interface Presenter {
        void login(String email, String password);
    }
}
