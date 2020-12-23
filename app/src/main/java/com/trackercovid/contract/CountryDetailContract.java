package com.trackercovid.contract;

import com.trackercovid.model.Historical;
import com.trackercovid.view.BaseView;

public interface CountryDetailContract {
    interface View extends BaseView {
        void showHistoricalData(Historical historical);
    }

    interface Presenter {
        void requestHistoricalData(String countryName);
    }
}
