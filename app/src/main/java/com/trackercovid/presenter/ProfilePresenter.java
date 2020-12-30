package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.ProfileContract;
import com.trackercovid.interactor.UserRepository;
import com.trackercovid.model.User;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;
    private final UserRepository repository;

    public ProfilePresenter(ProfileContract.View view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void requestProfile() {
        repository.getCurrentUser(new RepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                view.showProfile(data);
            }

            @Override
            public void onEmpty() {
                view.redirectToLogin();
            }

            @Override
            public void onError(String errorMessage) {
                view.redirectToLogin();
            }
        });
    }
}
