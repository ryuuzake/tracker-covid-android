package com.trackercovid.view;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.trackercovid.interactor.UserRepositoryImpl;
import com.trackercovid.presenter.RegisterPresenter;

public class RegisterActivity extends BaseFragmentHolderActivity {

    @Override
    protected void initializeView() {
        super.initializeView();
        appBarLayout.setVisibility(View.GONE);
        bottomNavigation.setVisibility(View.GONE);
    }

    @Override
    protected void initializeFragment() {
        RegisterFragment currentFragment = new RegisterFragment();
        currentFragment.setPresenter(new RegisterPresenter(
                currentFragment,
                new UserRepositoryImpl(FirebaseAuth.getInstance())
        ));
        setCurrentFragment(currentFragment, true);
    }
}
