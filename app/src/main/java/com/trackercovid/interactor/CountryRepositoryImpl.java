package com.trackercovid.interactor;

import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Country;

import java.util.ArrayList;
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
                public void onError(Throwable e) {
                    callback.onError(e.getMessage());
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
                public void onError(Throwable e) {
                    callback.onError(e.getMessage());
                }
            });
        }
    }

    private List<Country> processCountryResponseToModel(List<CountryResponse> data) {
        ArrayList<Country> countries = new ArrayList<>();

        for (CountryResponse countryResponse : data)
            countries.add(countryResponse.toCountryModel());

        return countries;
    }

    @Override
    public void getCountry(String countryName, RepositoryCallback<Country> callback) {

    }
}
