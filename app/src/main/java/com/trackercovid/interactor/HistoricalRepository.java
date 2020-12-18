package com.trackercovid.interactor;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Historical;

public interface HistoricalRepository {
    void getHistorical(String countryName, RepositoryCallback<Historical> callback);
}
