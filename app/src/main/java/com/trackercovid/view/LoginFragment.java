package com.trackercovid.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.login_layout, container, false);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btLogin = view.findViewById(R.id.bt_login);
        progressBar = view.findViewById(R.id.progress_circular);
        btLogin.setOnClickListener(v -> presenter.login(etEmail.getText().toString(), etPassword.getText().toString()));
        return view;
    }

    @Override
    public void redirectToHome() {
        // TODO: Add Intent to next Activity
        //new Intent(getContext(), );
        Log.d(TAG, "redirectToHome: success");
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
