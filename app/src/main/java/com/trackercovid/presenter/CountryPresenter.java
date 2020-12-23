package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.CountryContract;
import com.trackercovid.interactor.CountryRepository;
import com.trackercovid.model.Country;

public class CountryPresenter implements CountryContract.Presenter {
    private final CountryContract.View view;
    private final CountryRepository repository;

    public CountryPresenter(CountryContract.View view, CountryRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void requestCountryData(Country country) {
        view.startLoading();
        repository.getCountry(country.getName(), new RepositoryCallback<Country>() {
            @Override
            public void onSuccess(Country data) {
                view.stopLoading();
                view.showCountryData(data);
            }

            @Override
            public void onEmpty() {
                view.stopLoading();
                view.showError("No Country Data Found.");
            }

            @Override
            public void onError(String errorMessage) {
                view.stopLoading();
                view.showError(errorMessage);
            }
        });
    }
}
