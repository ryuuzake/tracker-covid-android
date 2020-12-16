package com.trackercovid.db;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.trackercovid.model.Country;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CountryDaoTest {

    private CountryDao countryDao;
    private AppDatabase database;

    private int tCountryId = 1;
    private String tCountryName = "Italy";

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        countryDao = database.countryDao();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void insertAndGetCountry() {
        // arrange
        Country country = new Country();
        country.setId(tCountryId);
        country.setName(tCountryName);

        // act
        countryDao.insertCountry(country);
        Country expected = countryDao.getCountry(tCountryName);

        // assert
        assertEquals(country.getId(), expected.getId());
    }

    @Test
    public void insertAndGetCountries() {
        // arrange
        Country country = new Country();
        country.setId(tCountryId);
        country.setName(tCountryName);
        List<Country> countries = Collections.singletonList(country);

        // act
        countryDao.insertCountries(countries);
        List<Country> result = countryDao.getCountries();

        // assert
        for (int i = 0; i < result.size(); i++)
            assertEquals(countries.get(i).getId(), result.get(i).getId());
    }
}