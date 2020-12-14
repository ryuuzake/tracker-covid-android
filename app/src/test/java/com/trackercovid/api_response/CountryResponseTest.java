package com.trackercovid.api_response;

import com.trackercovid.MockResponseFileReader;
import com.trackercovid.model.Country;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CountryResponseTest {

    private MockResponseFileReader fileReader = new MockResponseFileReader();

    @Test
    public void fromJson_success() {
        // arrange
        String json = fileReader.readJson("country_response.json");

        // act
        CountryResponse fromJson = CountryResponse.fromJson(json);

        // assert
        assertNotNull(fromJson);
    }

    @Test
    public void fromJson_error() {
        // arrange
        String json = "";

        // act
        CountryResponse fromJson = CountryResponse.fromJson(json);

        // assert
        assertNull(fromJson);
    }

    @Test
    public void fromJsonList_success() {
        // arrange
        String jsonList = fileReader.readJson("all_countries_response.json");

        // act
        List<CountryResponse> fromJsonList = CountryResponse.fromJsonList(jsonList);

        // assert
        assertNotNull(fromJsonList);
    }

    @Test
    public void fromJsonList_error() {
        // arrange
        String jsonList = "";

        // act
        List<CountryResponse> fromJsonList = CountryResponse.fromJsonList(jsonList);

        // assert
        assertNull(fromJsonList);
    }

    @Test
    public void toCountryModel() {
        // arrange
        String json = fileReader.readJson("country_response.json");
        CountryResponse fromJson = CountryResponse.fromJson(json);

        // act
        Country expected = fromJson.toCountryModel();

        // assert
        assertNotNull(expected);
    }
}