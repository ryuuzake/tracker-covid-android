package com.trackercovid.view;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.trackercovid.interactor.UserRepositoryImpl;
import com.trackercovid.presenter.LoginPresenter;

public class LoginActivity extends BaseFragmentHolderActivity {

    @Override
    protected void initializeView() {
        super.initializeView();
        appBarLayout.setVisibility(View.GONE);
        bottomNavigation.setVisibility(View.GONE);
    }

    @Override
    protected void initializeFragment() {
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setPresenter(new LoginPresenter(loginFragment, new UserRepositoryImpl(FirebaseAuth.getInstance())));
        setCurrentFragment(loginFragment, false);
    }
}
