package com.trackercovid.contract;

import com.trackercovid.model.Country;
import com.trackercovid.presenter.BasePresenter;
import com.trackercovid.view.BaseView;

import java.util.List;

public interface HeatMapContract {
    interface View extends BaseView {
        void showHeatMapData(List<Country> countries);
    }

    interface Presenter extends BasePresenter {
        void requestHeatMapData();
    }
}
