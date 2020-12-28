package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.SummaryContract;
import com.trackercovid.interactor.SummaryRepository;
import com.trackercovid.model.Summary;

public class SummaryPresenter implements SummaryContract.Presenter {

    private final SummaryContract.View view;
    private final SummaryRepository repository;

    public SummaryPresenter(SummaryContract.View view, SummaryRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void requestSummary() {
        view.startLoading();
        repository.getSummary(new RepositoryCallback<Summary>() {
            @Override
            public void onSuccess(Summary data) {
                view.stopLoading();
                view.showSummary(data);
            }

            @Override
            public void onEmpty() {
                view.stopLoading();
                view.showError("No Summary Data Found.");
            }

            @Override
            public void onError(String errorMessage) {
                view.stopLoading();
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void start() {
        requestSummary();
    }
}
