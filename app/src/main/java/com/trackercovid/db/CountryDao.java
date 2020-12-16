package com.trackercovid.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.trackercovid.model.Country;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM Country WHERE name = :countryName")
    Country getCountry(String countryName);

    @Query("SELECT * FROM Country")
    List<Country> getCountries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountry(Country country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountries(List<Country> countries);
}
