package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.RegisterContract;
import com.trackercovid.interactor.UserRepository;
import com.trackercovid.model.User;

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterContract.View view;
    private final UserRepository repository;

    public RegisterPresenter(RegisterContract.View view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void register(String name, String email, String password) {
        view.startLoading();
        repository.createUser(new User(name, email, password), new RepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                view.stopLoading();
                view.redirectToLogin();
            }

            @Override
            public void onEmpty() {
                view.stopLoading();
                view.showError("Cannot Create User.");
            }

            @Override
            public void onError(String errorMessage) {
                view.stopLoading();
                view.showError(errorMessage);
            }
        });
    }
}
