package com.trackercovid.interactor;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {

    private UserRepository repository;

    @Before
    public void setUp() {
        repository = new UserRepositoryImpl(FirebaseAuth.getInstance());
    }

    @Test
    public void createUser() {
        // arrange
        User tUser = new User("User Testing", "testing@example.com", "password");

        // act
        repository.createUser(tUser, new RepositoryCallback<User>() {
            // assert
            @Override
            public void onSuccess(User data) {
                assertEquals(tUser, data);
            }

            @Override
            public void onEmpty() {
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    @Test
    public void loginUser() {
        // arrange
        User tUser = new User("testing@example.com", "password");

        // act
        repository.loginUser(tUser, new RepositoryCallback<User>() {
            // assert
            @Override
            public void onSuccess(User data) {
                assertEquals(tUser, data);
            }

            @Override
            public void onEmpty() {
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    @Test
    public void getCurrentUser() {
        // arrange
        User tUser = new User("testing@example.com", "password");

        // act
        repository.getCurrentUser(new RepositoryCallback<User>() {
            // assert
            @Override
            public void onSuccess(User data) {
                assertEquals(tUser, data);
            }

            @Override
            public void onEmpty() {
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }
}