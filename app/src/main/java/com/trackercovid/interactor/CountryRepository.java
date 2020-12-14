package com.trackercovid.interactor;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Country;

import java.util.List;

public interface CountryRepository {
    void getCountries(RepositoryCallback<List<Country>> callback);

    void getCountry(String countryName, RepositoryCallback<Country> callback);
}
