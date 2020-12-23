package com.trackercovid.contract;

import com.trackercovid.view.BaseView;

public interface RegisterContract {
    interface View extends BaseView {
        void redirectToLogin();
    }

    interface Presenter {
        void register(String name, String email, String password);
    }
}
