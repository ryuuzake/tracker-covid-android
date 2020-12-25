package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.HeatMapContract;
import com.trackercovid.interactor.CountryRepository;
import com.trackercovid.model.Country;

import java.util.List;

public class HeatMapPresenter implements HeatMapContract.Presenter {

    private final HeatMapContract.View view;
    private final CountryRepository repository;

    public HeatMapPresenter(HeatMapContract.View view, CountryRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void start() {
        requestHeatMapData();
    }

    @Override
    public void requestHeatMapData() {
        view.startLoading();
        repository.getCountries(new RepositoryCallback<List<Country>>() {
            @Override
            public void onSuccess(List<Country> data) {
                view.stopLoading();
                view.showHeatMapData(data);
            }

            @Override
            public void onEmpty() {
                view.stopLoading();
                view.showError("No HeatMap Data Found.");
            }

            @Override
            public void onError(String errorMessage) {
                view.stopLoading();
                view.showError(errorMessage);
            }
        });
    }
}
