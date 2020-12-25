package com.trackercovid.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trackercovid.R;
import com.trackercovid.contract.LoginContract;
import com.trackercovid.presenter.LoginPresenter;

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;
    private ProgressBar progressBar;
    private TextView tvRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.login_layout, container, false);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btLogin = view.findViewById(R.id.bt_login);
        tvRegister = view.findViewById(R.id.tv_register);
        progressBar = view.findViewById(R.id.progress_circular);
        btLogin.setOnClickListener(v -> presenter.login(etEmail.getText().toString(), etPassword.getText().toString()));
        tvRegister.setOnClickListener(v -> this.redirectToRegister());
        return view;
    }

    private void redirectToRegister() {
        startActivity(new Intent(getContext(), RegisterActivity.class));
    }

    @Override
    public void redirectToHome() {
        Log.d(TAG, "redirectToHome: success");
        startActivity(new Intent(getContext(), HeatMapActivity.class));
        requireActivity().finish();
    }

    @Override
    public void startLoading() {
        btLogin.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        btLogin.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
