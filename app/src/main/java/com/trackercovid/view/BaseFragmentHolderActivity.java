package com.trackercovid.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trackercovid.R;
import com.trackercovid.presenter.BasePresenter;

public abstract class BaseFragmentHolderActivity extends FragmentActivity {

    protected BaseFragment<? extends BasePresenter> currentFragment;
    protected FrameLayout flFragmentContainer;
    protected AppBarLayout appBarLayout;
    protected BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
        initializeFragment();
    }

    protected void initializeView() {
        setContentView(R.layout.activity_main);
        flFragmentContainer = findViewById(R.id.fl_fragment_container);
        appBarLayout = findViewById(R.id.appBarLayout);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
    }

    protected abstract void initializeFragment();

    protected void setCurrentFragment(BaseFragment<? extends BasePresenter> fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (currentFragment != null && addToBackStack) {
            fragmentTransaction.addToBackStack(currentFragment.getTitle());
        }

        fragmentTransaction.replace(R.id.fl_fragment_container, fragment, fragment.getTitle());
        fragmentTransaction.commit();

        this.currentFragment = fragment;
    }
}
