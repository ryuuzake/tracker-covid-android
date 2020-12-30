package com.trackercovid.contract;

import com.trackercovid.model.User;

public interface ProfileContract {
    interface View {
        void showProfile(User user);

        void redirectToLogin();
    }

    interface Presenter {
        void requestProfile();
    }
}
