package com.trackercovid.interactor;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
                .addOnCompleteListener(getOnCompleteListener(user, callback));
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
            callback.onSuccess(new User(currentUser.getDisplayName(), currentUser.getEmail(), null));
        } else {
            callback.onError("No Authenticated User.");
        }
    }

    @NonNull
    private OnCompleteListener<AuthResult> getOnCompleteListener(final User user, RepositoryCallback<User> callback) {
        return task -> {
            if (task.isSuccessful()) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest
                            .Builder().setDisplayName(user.getName()).build();
                    currentUser.updateProfile(profileChangeRequest)
                            .addOnCompleteListener(newTask -> getCurrentUser(callback));
                }
            } else {
                callback.onError("Creating User failed.");
            }
        };
    }

    @NotNull
    private OnCompleteListener<AuthResult> getOnCompleteListener(RepositoryCallback<User> callback) {
        return task -> {
            if (task.isSuccessful()) {
                getCurrentUser(callback);
            } else {
                callback.onError("Authentication failed.");
            }
        };
    }
}
