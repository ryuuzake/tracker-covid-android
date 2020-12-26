package com.trackercovid.contract;

import com.trackercovid.model.Country;
import com.trackercovid.presenter.BasePresenter;
import com.trackercovid.view.BaseView;

public interface SummaryContract {
    interface View extends BaseView {
        void showCountrySummary(Country country);
    }

    interface Presenter extends BasePresenter {
        void requestCountrySummary(Country country);
    }
}
