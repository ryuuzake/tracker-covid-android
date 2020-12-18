package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Historical;

public interface HistoricalRemoteDataSource {
    void getHistorical(String countryName, LoadDataCallback<Historical> callback);
}
