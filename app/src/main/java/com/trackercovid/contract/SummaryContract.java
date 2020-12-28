package com.trackercovid.contract;

import com.trackercovid.model.Summary;
import com.trackercovid.presenter.BasePresenter;
import com.trackercovid.view.BaseView;

public interface SummaryContract {
    interface View extends BaseView {
        void showSummary(Summary summary);
    }

    interface Presenter extends BasePresenter {
        void requestSummary();
    }
}
