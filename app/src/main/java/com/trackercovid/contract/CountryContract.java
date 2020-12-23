package com.trackercovid.contract;

import com.trackercovid.model.Country;
import com.trackercovid.view.BaseView;

public interface CountryContract {
    interface View extends BaseView {
        void showCountryData(Country country);
    }

    interface Presenter {
        void requestCountryData(Country country);
    }
}
