package com.trackercovid.interactor;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.User;

public interface UserRepository {

    void createUser(User user, RepositoryCallback<User> callback);

    void loginUser(User user, RepositoryCallback<User> callback);

    void getCurrentUser(RepositoryCallback<User> callback);
}
