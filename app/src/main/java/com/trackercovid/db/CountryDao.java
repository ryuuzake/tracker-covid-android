package com.trackercovid.db;

import com.trackercovid.model.Country;

import java.util.List;

public interface CountryDao {

    Country getCountry(String countryName);

    List<Country> getCountries();

    void insertCountry(Country country);

    void insertCountries(List<Country> countries);
}
