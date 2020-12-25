package com.trackercovid.util;

import com.google.android.gms.maps.model.LatLng;
import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.model.Country;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MapUtilTest {

    private MockResponseFileReader fileReader = new MockResponseFileReader();

    @Test
    public void toLatLngs() {
        // arrange
        String jsonList = fileReader.readJson("all_countries_response.json");
        CountryResponseUtil countryResponseUtil = new CountryResponseUtil();
        List<CountryResponse> fromJsonList = countryResponseUtil.fromJsonList(jsonList);
        List<Country> tCountries = countryResponseUtil.toCountryModel(fromJsonList);

        // act
        final List<LatLng> weightedLatLngs = MapUtil.toLatLngs(tCountries);

        // assert
        assertNotNull(fromJsonList);
        assertNotNull(weightedLatLngs);
        for (int i = 0; i < tCountries.size(); i++) {
            assertEquals(tCountries.get(i).getLatitude(), weightedLatLngs.get(i).latitude, 0.1);
            assertEquals(tCountries.get(i).getLongitude(), weightedLatLngs.get(i).longitude, 0.1);
        }
    }
}