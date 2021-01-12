package com.trackercovid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.trackercovid.R;
import com.trackercovid.contract.ProfileContract;
import com.trackercovid.interactor.UserRepositoryImpl;
import com.trackercovid.model.User;
import com.trackercovid.presenter.BasePresenter;
import com.trackercovid.presenter.ProfilePresenter;

public abstract class BaseFragmentHolderActivity extends FragmentActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ProfileContract.View {

    protected BaseFragment<? extends BasePresenter> currentFragment;
    protected FrameLayout flFragmentContainer;
    protected AppBarLayout appBarLayout;
    protected BottomNavigationView bottomNavigation;
    private TextView tvName;
    private TextView tvEmail;
    private ImageView btnOption;

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
        tvName = appBarLayout.findViewById(R.id.tv_name);
        tvEmail = appBarLayout.findViewById(R.id.tv_email);
        btnOption = appBarLayout.findViewById(R.id.btn_option);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void showProfile(User user) {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        btnOption.setOnClickListener(v -> redirectToLogin());
    }

    @Override
    public void redirectToLogin() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected abstract void initializeFragment();

    protected void setCurrentFragment(BaseFragment<? extends BasePresenter> fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (currentFragment != null && addToBackStack) {
            fragmentTransaction.addToBackStack(currentFragment.getTitle());
        }
        if (!(fragment instanceof LoginFragment || fragment instanceof RegisterFragment)) {
            new ProfilePresenter(this, new UserRepositoryImpl(FirebaseAuth.getInstance())).requestProfile();
        }

        fragmentTransaction.replace(R.id.fl_fragment_container, fragment, fragment.getTitle());
        fragmentTransaction.commit();

        this.currentFragment = fragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.btm_heatmap:
                if (!(this instanceof HeatMapActivity)) {
                    intent = new Intent(this, HeatMapActivity.class);
                }
                break;
            case R.id.btm_country:
                if (!(this instanceof CaseCountryActivity)) {
                    intent = new Intent(getBaseContext(), CaseCountryActivity.class);
                }
                break;
            default:
                if (!(this instanceof SummaryActivity)) {
                    intent = new Intent(this, SummaryActivity.class);
                }
        }

        if (intent != null) {
            startActivity(intent);
            this.finish();
        }

        return true;
    }
}
