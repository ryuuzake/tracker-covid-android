package com.trackercovid.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.HistoricalResponse;
import com.trackercovid.model.Historical;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class HistoricalResponseUtilTest {

    private MockResponseFileReader fileReader = new MockResponseFileReader();
    private HistoricalResponseUtil historicalResponseUtil = new HistoricalResponseUtil();

    @Test
    public void toHistoricalModel() {
        // arrange
        String json = fileReader.readJson("historical_response.json");
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat("MM/dd/yy")
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();
        HistoricalResponse fromJson = gson.fromJson(json, HistoricalResponse.class);

        // act
        Historical expected = historicalResponseUtil.toHistoricalModel(fromJson);

        // assert
        assertNotNull(expected);
    }
}