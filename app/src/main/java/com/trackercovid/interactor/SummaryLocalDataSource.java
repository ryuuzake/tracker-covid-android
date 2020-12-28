package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Summary;

public interface SummaryLocalDataSource {
    void cacheSummary(Summary summary);

    void getSummary(LoadDataCallback<Summary> callback);
}
