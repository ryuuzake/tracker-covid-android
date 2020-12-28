package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Summary;

public interface SummaryRemoteDataSource {
    void getSummary(LoadDataCallback<Summary> callback);
}
