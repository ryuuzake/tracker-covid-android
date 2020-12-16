package com.trackercovid.interactor;

import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Country;
import com.trackercovid.retrofit.CountryService;
import com.trackercovid.util.CountryResponseUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRemoteDataSourceImpl implements CountryRemoteDataSource {

    private final CountryService service;
    private final CountryResponseUtil util;

    public CountryRemoteDataSourceImpl(CountryService service, CountryResponseUtil util) {
        this.service = service;
        this.util = util;
    }

    @Override
    public void getCountries(LoadDataCallback<List<Country>> callback) {
        Call<List<CountryResponse>> call = service.getAllCountries(null);
        call.enqueue(new Callback<List<CountryResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<CountryResponse>> call, @NotNull Response<List<CountryResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onDataLoaded(util.toCountryModel(response.body()));
                    } else {
                        callback.onNoDataLoaded();
                    }
                } else {
                    callback.onError(response.message(), null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<CountryResponse>> call, @NotNull Throwable t) {
                callback.onError(null, t);
            }
        });
    }

    @Override
    public void getCountry(String countryName, LoadDataCallback<Country> callback) {
        Call<CountryResponse> country = service.getCountry(countryName, null);
        country.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(@NotNull Call<CountryResponse> call, @NotNull Response<CountryResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onDataLoaded(util.toCountryModel(response.body()));
                    } else {
                        callback.onNoDataLoaded();
                    }
                } else {
                    callback.onError(response.message(), null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CountryResponse> call, @NotNull Throwable t) {
                callback.onError(null, t);
            }
        });
    }
}
