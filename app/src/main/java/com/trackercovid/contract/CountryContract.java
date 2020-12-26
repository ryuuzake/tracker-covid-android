package com.trackercovid.contract;

import com.trackercovid.model.Country;
import com.trackercovid.presenter.BasePresenter;
import com.trackercovid.view.BaseView;

public interface CountryContract {
    interface View extends BaseView {
        void showCountryData(Country country);

        void redirectToDetail();
    }

    interface Presenter extends BasePresenter {
        void requestCountryData(Country country);
    }
}
