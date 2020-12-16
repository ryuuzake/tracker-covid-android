package com.trackercovid.util;

import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.model.Country;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CountryResponseUtilTest {

    private MockResponseFileReader fileReader = new MockResponseFileReader();
    private CountryResponseUtil countryResponseUtil = new CountryResponseUtil();

    @Test
    public void fromJson_success() {
        // arrange
        String json = fileReader.readJson("country_response.json");

        // act
        CountryResponse fromJson = countryResponseUtil.fromJson(json);

        // assert
        assertNotNull(fromJson);
    }

    @Test
    public void fromJson_error() {
        // arrange
        String json = "";

        // act
        CountryResponse fromJson = countryResponseUtil.fromJson(json);

        // assert
        assertNull(fromJson);
    }

    @Test
    public void fromJsonList_success() {
        // arrange
        String jsonList = fileReader.readJson("all_countries_response.json");

        // act
        List<CountryResponse> fromJsonList = countryResponseUtil.fromJsonList(jsonList);

        // assert
        assertNotNull(fromJsonList);
    }

    @Test
    public void fromJsonList_error() {
        // arrange
        String jsonList = "";

        // act
        List<CountryResponse> fromJsonList = countryResponseUtil.fromJsonList(jsonList);

        // assert
        assertNull(fromJsonList);
    }

    @Test
    public void toCountryModel_list() {
        // arrange
        String json = fileReader.readJson("all_countries_response.json");
        List<CountryResponse> fromJson = countryResponseUtil.fromJsonList(json);

        // act
        List<Country> expected = countryResponseUtil.toCountryModel(fromJson);

        // assert
        assertNotNull(expected);
    }

    @Test
    public void toCountryModel_object() {
        // arrange
        String json = fileReader.readJson("country_response.json");
        CountryResponse fromJson = countryResponseUtil.fromJson(json);

        // act
        Country expected = countryResponseUtil.toCountryModel(fromJson);

        // assert
        assertNotNull(expected);
    }
}