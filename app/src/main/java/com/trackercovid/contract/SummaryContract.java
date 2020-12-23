package com.trackercovid.contract;

import com.trackercovid.model.Country;
import com.trackercovid.view.BaseView;

public interface SummaryContract {
    interface View extends BaseView {
        void showCountrySummary(Country country);
    }

    interface Presenter {
        void requestCountrySummary(Country country);
    }
}
