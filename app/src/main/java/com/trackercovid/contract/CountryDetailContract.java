package com.trackercovid.contract;

import com.trackercovid.model.Historical;
import com.trackercovid.presenter.BasePresenter;
import com.trackercovid.view.BaseView;

public interface CountryDetailContract {
    interface View extends BaseView {
        void showHistoricalData(Historical historical);
    }

    interface Presenter extends BasePresenter {
        void requestHistoricalData(String countryName);
    }
}
