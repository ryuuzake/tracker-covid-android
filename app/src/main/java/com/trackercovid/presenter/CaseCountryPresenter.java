package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.CaseCountryContract;
import com.trackercovid.interactor.CountryRepository;
import com.trackercovid.model.Country;

import java.util.List;

public class CaseCountryPresenter implements CaseCountryContract.Presenter {
    private final CaseCountryContract.View view;
    private final CountryRepository repository;

    public CaseCountryPresenter(CaseCountryContract.View view, CountryRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void requestCountries() {
        view.startLoading();
        repository.getCountries(new RepositoryCallback<List<Country>>() {
            @Override
            public void onSuccess(List<Country> data) {
                view.stopLoading();
                view.showCountries(data);
            }

            @Override
            public void onEmpty() {
                view.stopLoading();
                view.showError("No Countries Data Found.");
            }

            @Override
            public void onError(String errorMessage) {
                view.stopLoading();
                view.showError(errorMessage);
            }
        });
    }
}
