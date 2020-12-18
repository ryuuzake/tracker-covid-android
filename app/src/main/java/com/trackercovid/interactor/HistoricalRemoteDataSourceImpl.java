package com.trackercovid.interactor;

import com.trackercovid.api_response.HistoricalResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Historical;
import com.trackercovid.retrofit.HistoricalService;
import com.trackercovid.util.HistoricalResponseUtil;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricalRemoteDataSourceImpl implements HistoricalRemoteDataSource {
    private final HistoricalService service;
    private final HistoricalResponseUtil util;

    public HistoricalRemoteDataSourceImpl(HistoricalService service, HistoricalResponseUtil util) {
        this.service = service;
        this.util = util;
    }

    @Override
    public void getHistorical(String countryName, LoadDataCallback<Historical> callback) {
        Call<HistoricalResponse> call = service.getHistoricalData(countryName);
        call.enqueue(new Callback<HistoricalResponse>() {
            @Override
            public void onResponse(@NotNull Call<HistoricalResponse> call, @NotNull Response<HistoricalResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onDataLoaded(util.toHistoricalModel(response.body()));
                    } else {
                        callback.onNoDataLoaded();
                    }
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<HistoricalResponse> call, @NotNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
