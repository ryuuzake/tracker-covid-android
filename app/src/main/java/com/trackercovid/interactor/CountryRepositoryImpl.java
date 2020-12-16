package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Country;

import java.util.List;

public class CountryRepositoryImpl implements CountryRepository {
    private final CountryRemoteDataSource remoteDataSource;
    private final CountryLocalDataSource localDataSource;
    private final NetworkInfoSource networkInfoSource;

    public CountryRepositoryImpl(CountryRemoteDataSource remoteDataSource,
                                 CountryLocalDataSource localDataSource,
                                 NetworkInfoSource networkInfoSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.networkInfoSource = networkInfoSource;
    }

    @Override
    public void getCountries(RepositoryCallback<List<Country>> callback) {
        if (networkInfoSource.getNetworkCapabilities()) {
            remoteDataSource.getCountries(new LoadDataCallback<List<Country>>() {
                @Override
                public void onDataLoaded(List<Country> data) {
                    localDataSource.cacheCountries(data);
                    callback.onSuccess(data);
                }

                @Override
                public void onNoDataLoaded() {
                    callback.onEmpty();
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });
        } else {
            localDataSource.getCountries(new LoadDataCallback<List<Country>>() {
                @Override
                public void onDataLoaded(List<Country> data) {
                    callback.onSuccess(data);
                }

                @Override
                public void onNoDataLoaded() {
                    callback.onEmpty();
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });
        }
    }

    @Override
    public void getCountry(String countryName, RepositoryCallback<Country> callback) {
        if (networkInfoSource.getNetworkCapabilities()) {
            remoteDataSource.getCountry(countryName, new LoadDataCallback<Country>() {
                @Override
                public void onDataLoaded(Country data) {
                    localDataSource.cacheCountry(data);
                    callback.onSuccess(data);
                }

                @Override
                public void onNoDataLoaded() {
                    callback.onEmpty();
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });
        } else {
            localDataSource.getCountry(countryName, new LoadDataCallback<Country>() {
                @Override
                public void onDataLoaded(Country data) {
                    callback.onSuccess(data);
                }

                @Override
                public void onNoDataLoaded() {
                    callback.onEmpty();
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });
        }
    }
}
