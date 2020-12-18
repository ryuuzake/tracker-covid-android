package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Historical;

public class HistoricalRepositoryImpl implements HistoricalRepository {
    private final HistoricalRemoteDataSource remoteDataSource;
    private final NetworkInfoSource networkInfoSource;

    public HistoricalRepositoryImpl(HistoricalRemoteDataSource remoteDataSource,
                                    NetworkInfoSource networkInfoSource) {
        this.remoteDataSource = remoteDataSource;
        this.networkInfoSource = networkInfoSource;
    }

    @Override
    public void getHistorical(String countryName, RepositoryCallback<Historical> callback) {
        if (networkInfoSource.getNetworkCapabilities()) {
            remoteDataSource.getHistorical(countryName, new LoadDataCallback<Historical>() {
                @Override
                public void onDataLoaded(Historical data) {
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
            callback.onError("No Internet Connection");
        }
    }
}
