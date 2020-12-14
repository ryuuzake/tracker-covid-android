package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Country;

import java.util.List;

public interface CountryRemoteDataSource {

    void getCountries(LoadDataCallback<List<Country>> callback);

    void getCountry(LoadDataCallback<Country> callback);
}
