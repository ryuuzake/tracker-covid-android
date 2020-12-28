package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.db.SummaryDatabase;
import com.trackercovid.model.Summary;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SummaryLocalDataSourceTest {

    private SummaryLocalDataSource localDataSource;
    @Mock
    private SummaryDatabase mockDatabase;
    @Mock
    private LoadDataCallback<Summary> summaryLoadDataCallback;

    private Summary tSummary = new Summary();

    @Before
    public void setUp() {
        localDataSource = new SummaryLocalDataSourceImpl(mockDatabase);
    }

    @Test
    public void cacheSummary() {
        // arrange

        // act
        localDataSource.cacheSummary(tSummary);

        // assert
        verify(mockDatabase).update(tSummary);
    }

    @Test
    public void getSummary_success() {
        // arrange
        when(mockDatabase.getSessionData()).thenReturn(tSummary);

        // act
        localDataSource.getSummary(summaryLoadDataCallback);

        // assert
        verify(mockDatabase).getSessionData();
        verify(summaryLoadDataCallback).onDataLoaded(tSummary);
    }

    @Test
    public void getSummary_noData() {
        // arrange
        when(mockDatabase.getSessionData()).thenReturn(null);

        // act
        localDataSource.getSummary(summaryLoadDataCallback);

        // assert
        verify(mockDatabase).getSessionData();
        verify(summaryLoadDataCallback).onNoDataLoaded();
    }
}