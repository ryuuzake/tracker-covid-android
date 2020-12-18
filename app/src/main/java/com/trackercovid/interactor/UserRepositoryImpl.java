package com.trackercovid.interactor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.User;

import org.jetbrains.annotations.NotNull;

public class UserRepositoryImpl implements UserRepository {

    private final FirebaseAuth firebaseAuth;

    public UserRepositoryImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void createUser(User user, RepositoryCallback<User> callback) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(getOnCompleteListener(callback));
    }

    @Override
    public void loginUser(User user, RepositoryCallback<User> callback) {
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(getOnCompleteListener(callback));
    }

    @Override
    public void getCurrentUser(RepositoryCallback<User> callback) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            callback.onSuccess(new User(currentUser.getEmail()));
        }
        callback.onError("No Authenticated User.");
    }

    @NotNull
    private OnCompleteListener<AuthResult> getOnCompleteListener(RepositoryCallback<User> callback) {
        return task -> {
            if (task.isSuccessful()) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    callback.onSuccess(new User(currentUser.getEmail()));
                } else {
                    callback.onEmpty();
                }
            } else {
                callback.onError("Authentication failed.");
            }
        };
    }
}
