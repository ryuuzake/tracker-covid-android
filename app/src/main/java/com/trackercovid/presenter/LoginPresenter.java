package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.LoginContract;
import com.trackercovid.interactor.UserRepository;
import com.trackercovid.model.User;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View view;
    private final UserRepository repository;

    public LoginPresenter(LoginContract.View view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void login(String email, String password) {
        view.startLoading();
        repository.loginUser(new User(email, password), new RepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                view.stopLoading();
                view.redirectToHome();
            }

            @Override
            public void onEmpty() {
                view.stopLoading();
                view.showError("No User Found.");
            }

            @Override
            public void onError(String errorMessage) {
                view.stopLoading();
                view.showError(errorMessage);
            }
        });
    }
}
