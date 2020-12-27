package com.trackercovid.contract;

import com.trackercovid.model.Country;
import com.trackercovid.presenter.BasePresenter;
import com.trackercovid.view.BaseView;

import java.util.List;

public interface CaseCountryContract {
    interface View extends BaseView {
        void showCountries(List<Country> countries);
    }

    interface Presenter extends BasePresenter {
        void requestCountries();
    }
}
