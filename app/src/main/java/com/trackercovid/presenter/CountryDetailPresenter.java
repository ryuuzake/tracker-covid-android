package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.CountryDetailContract;
import com.trackercovid.interactor.HistoricalRepository;
import com.trackercovid.model.Historical;

public class CountryDetailPresenter implements CountryDetailContract.Presenter {
    private final CountryDetailContract.View view;
    private final HistoricalRepository repository;

    public CountryDetailPresenter(CountryDetailContract.View view, HistoricalRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void requestHistoricalData(String countryName) {
        view.startLoading();
        repository.getHistorical(countryName, new RepositoryCallback<Historical>() {
            @Override
            public void onSuccess(Historical data) {
                view.stopLoading();
                view.showHistoricalData(data);
            }

            @Override
            public void onEmpty() {
                view.stopLoading();
                view.showError("No Historical Data Found.");
            }

            @Override
            public void onError(String errorMessage) {
                view.stopLoading();
                view.showError(errorMessage);
            }
        });
    }
}
