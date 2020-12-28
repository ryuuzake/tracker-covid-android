package com.trackercovid.interactor;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Summary;

public interface SummaryRepository {
    void getSummary(RepositoryCallback<Summary> callback);
}
