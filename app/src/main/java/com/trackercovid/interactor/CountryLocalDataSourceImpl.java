package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.db.AppDatabase;
import com.trackercovid.db.CountryDao;
import com.trackercovid.model.Country;

import java.util.List;

public class CountryLocalDataSourceImpl implements CountryLocalDataSource {
    private final CountryDao dao;

    public CountryLocalDataSourceImpl(CountryDao dao) {
        this.dao = dao;
    }

    @Override
    public void cacheCountries(List<Country> countries) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.insertCountries(countries));
    }

    @Override
    public void cacheCountry(Country country) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.insertCountry(country));
    }

    @Override
    public void getCountries(LoadDataCallback<List<Country>> callback) {
        AppDatabase.databaseReadExecutor.execute(() -> callback.onDataLoaded(dao.getCountries()));
    }

    @Override
    public void getCountry(String countryName, LoadDataCallback<Country> callback) {
        AppDatabase.databaseReadExecutor.execute(() -> callback.onDataLoaded(dao.getCountry(countryName)));
    }
}
