package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Country;

import java.util.List;

public interface CountryLocalDataSource {
    void cacheCountries(List<Country> countries);

    void getCountries(LoadDataCallback<List<Country>> callback);

    void getCountry(String countryName, LoadDataCallback<Country> capture);
}
