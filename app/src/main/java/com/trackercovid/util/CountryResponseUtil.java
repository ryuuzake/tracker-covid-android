package com.trackercovid.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.model.Country;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CountryResponseUtil {

    public List<Country> toCountryModel(List<CountryResponse> data) {
        ArrayList<Country> countries = new ArrayList<>();

        for (CountryResponse countryResponse : data)
            countries.add(toCountryModel(countryResponse));

        return countries;
    }

    public CountryResponse fromJson(String json) {
        return new Gson().fromJson(json, CountryResponse.class);
    }

    public List<CountryResponse> fromJsonList(String json) {
        Type type = new TypeToken<List<CountryResponse>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    public Country toCountryModel(CountryResponse data) {
        Country countryModel = new Country();
        countryModel.setId(data.getCountryInfo().getId());
        countryModel.setName(data.getCountry());
        countryModel.setLatitude(data.getCountryInfo().getLat());
        countryModel.setLongitude(data.getCountryInfo().getLong());
        countryModel.setCases(data.getCases());
        countryModel.setDeaths(data.getDeaths());
        countryModel.setRecovered(data.getRecovered());
        countryModel.setActive(data.getActive());
        countryModel.setCritical(data.getCritical());
        return countryModel;
    }
}
