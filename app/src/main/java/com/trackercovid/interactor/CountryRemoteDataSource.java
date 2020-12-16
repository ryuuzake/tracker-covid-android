package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Country;

import java.util.List;

public interface CountryRemoteDataSource {

    void getCountries(LoadDataCallback<List<Country>> callback);

    void getCountry(String countryName, LoadDataCallback<Country> callback);
}
