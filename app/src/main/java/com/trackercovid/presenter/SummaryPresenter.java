package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.SummaryContract;
import com.trackercovid.interactor.CountryRepository;
import com.trackercovid.model.Country;

public class SummaryPresenter implements SummaryContract.Presenter {

    private final SummaryContract.View view;
    private final CountryRepository repository;

    public SummaryPresenter(SummaryContract.View view, CountryRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void requestCountrySummary(Country country) {
        view.startLoading();
        repository.getCountry(country.getName(), new RepositoryCallback<Country>() {
            @Override
            public void onSuccess(Country data) {
                view.stopLoading();
                view.showCountrySummary(data);
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
}
