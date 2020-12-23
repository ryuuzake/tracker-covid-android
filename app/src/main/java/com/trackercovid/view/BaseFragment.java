package com.trackercovid.view;

import androidx.fragment.app.Fragment;

import com.trackercovid.presenter.BasePresenter;

public class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T presenter;
    protected String title;

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
