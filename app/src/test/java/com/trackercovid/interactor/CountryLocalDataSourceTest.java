package com.trackercovid.interactor;

import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.db.CountryDao;
import com.trackercovid.model.Country;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryLocalDataSourceTest {

    private CountryLocalDataSourceImpl countryLocalDataSource;

    @Mock
    private CountryDao mockCountryDao;
    @Mock
    private LoadDataCallback<List<Country>> mockCountriesLoadDataCallback;
    @Mock
    private LoadDataCallback<Country> mockCountryLoadDataCallback;

    private List<Country> tCountries;
    private Country tCountry;
    private String tCountryName = "Italy";

    @Before
    public void setUp() {
        countryLocalDataSource = new CountryLocalDataSourceImpl(mockCountryDao);
    }

    @Test
    public void cacheCountries() {
        // arrange

        // act
        countryLocalDataSource.cacheCountries(tCountries);

        // assert
        verify(mockCountryDao).insertCountries(tCountries);
    }

    @Test
    public void cacheCountry() {
        // arrange

        // act
        countryLocalDataSource.cacheCountry(tCountry);

        // assert
        verify(mockCountryDao).insertCountry(tCountry);
    }

    @Test
    public void getCountries() {
        // arrange
        when(mockCountryDao.getCountries()).thenReturn(tCountries);

        // act
        countryLocalDataSource.getCountries(mockCountriesLoadDataCallback);

        // assert
        verify(mockCountryDao).getCountries();
        verify(mockCountriesLoadDataCallback).onDataLoaded(tCountries);
    }

    @Test
    public void getCountry() {
        // arrange
        when(mockCountryDao.getCountry(anyString())).thenReturn(tCountry);

        // act
        countryLocalDataSource.getCountry(tCountryName, mockCountryLoadDataCallback);

        // assert
        verify(mockCountryDao).getCountry(tCountryName);
        verify(mockCountryLoadDataCallback).onDataLoaded(tCountry);
    }
}