package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.db.SummaryDatabase;
import com.trackercovid.model.Summary;

public class SummaryLocalDataSourceImpl implements SummaryLocalDataSource {
    private final SummaryDatabase database;

    public SummaryLocalDataSourceImpl(SummaryDatabase database) {
        this.database = database;
    }

    @Override
    public void cacheSummary(Summary summary) {
        database.update(summary);
    }

    @Override
    public void getSummary(LoadDataCallback<Summary> callback) {
        final Summary summary = database.getSessionData();
        if (summary != null) {
            callback.onDataLoaded(summary);
        } else {
            callback.onNoDataLoaded();
        }
    }
}
