package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Summary;

public class SummaryRepositoryImpl implements SummaryRepository {
    private final SummaryRemoteDataSource remoteDataSource;
    private final SummaryLocalDataSource localDataSource;
    private final NetworkInfoSource networkInfoSource;

    public SummaryRepositoryImpl(SummaryRemoteDataSource remoteDataSource,
                                 SummaryLocalDataSource localDataSource,
                                 NetworkInfoSource networkInfoSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.networkInfoSource = networkInfoSource;
    }

    @Override
    public void getSummary(RepositoryCallback<Summary> callback) {
        if (networkInfoSource.getNetworkCapabilities()) {
            remoteDataSource.getSummary(new LoadDataCallback<Summary>() {
                @Override
                public void onDataLoaded(Summary data) {
                    localDataSource.cacheSummary(data);
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
            localDataSource.getSummary(new LoadDataCallback<Summary>() {
                @Override
                public void onDataLoaded(Summary data) {
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
