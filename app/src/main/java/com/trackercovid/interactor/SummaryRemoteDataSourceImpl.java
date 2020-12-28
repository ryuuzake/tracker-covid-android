package com.trackercovid.interactor;

import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Summary;
import com.trackercovid.retrofit.CountryService;
import com.trackercovid.util.CountryResponseUtil;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryRemoteDataSourceImpl implements SummaryRemoteDataSource {
    private final CountryService service;
    private final CountryResponseUtil countryResponseUtil;

    public SummaryRemoteDataSourceImpl(CountryService service, CountryResponseUtil countryResponseUtil) {
        this.service = service;
        this.countryResponseUtil = countryResponseUtil;
    }

    @Override
    public void getSummary(LoadDataCallback<Summary> callback) {
        final Call<CountryResponse> call = service.getSummary();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(@NotNull Call<CountryResponse> call, @NotNull Response<CountryResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onDataLoaded(countryResponseUtil.toSummaryModel(response.body()));
                    } else {
                        callback.onNoDataLoaded();
                    }
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CountryResponse> call, @NotNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
