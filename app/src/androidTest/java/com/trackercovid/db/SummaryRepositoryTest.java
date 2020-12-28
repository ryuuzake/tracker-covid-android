package com.trackercovid.db;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.trackercovid.model.Summary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SummaryRepositoryTest {

    private SummaryDatabase localDatabase;

    private long tActive = 22079223;
    private long tCases = 81145397;
    private long tCritical = 105364;
    private long tDeaths = 1771982;
    private long tRecovered = 57294192;

    private Summary tSummary = new Summary(tActive, tCases, tCritical, tDeaths, tRecovered);

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        localDatabase = new SummaryDatabase(context);
    }

    @Test
    public void setAndGetSessionData() {
        // arrange

        // act
        localDatabase.setSessionData(tSummary);
        Summary expected = localDatabase.getSessionData();

        // assert
        assertEquals(expected, tSummary);
    }

    @Test
    public void initializeAndGetSessionData() {
        // arrange

        // act
        final Summary actual = localDatabase.initialize(tSummary);
        Summary expected = localDatabase.getSessionData();

        // assert
        assertEquals(expected, actual);
    }
}