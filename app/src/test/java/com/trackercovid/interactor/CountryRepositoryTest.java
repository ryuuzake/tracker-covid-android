package com.trackercovid.interactor;

import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Country;
import com.trackercovid.util.CountryResponseUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryRepositoryTest {

    private CountryRepositoryImpl repository;
    @Mock
    private CountryRemoteDataSource mockRemoteDataSource;
    @Mock
    private CountryLocalDataSource mockLocalDataSource;
    @Mock
    private NetworkInfoSource mockNetworkInfoSource;
    @Mock
    private RepositoryCallback<List<Country>> mockCountriesRepositoryCallback;
    @Mock
    private RepositoryCallback<Country> mockCountryRepositoryCallback;
    @Captor
    private ArgumentCaptor<LoadDataCallback<List<Country>>> countriesLoadDataCallbackArgumentCaptor;
    @Captor
    private ArgumentCaptor<LoadDataCallback<Country>> countryLoadDataCallbackArgumentCaptor;

    private List<Country> countries;

    @Before
    public void setUp() {
        repository = new CountryRepositoryImpl(
                mockRemoteDataSource,
                mockLocalDataSource,
                mockNetworkInfoSource
        );

        countries = createCountryList();
    }

    private List<Country> createCountryList() {
        CountryResponseUtil countryResponseUtil = new CountryResponseUtil();
        List<CountryResponse> countryResponses = countryResponseUtil
                .fromJsonList(new MockResponseFileReader().readJson("all_countries_response.json"));
        return countryResponses.stream().map(countryResponseUtil::toCountryModel).collect(Collectors.toList());
    }

    private void setUpConnectivity(boolean isConnected) {
        when(mockNetworkInfoSource.getNetworkCapabilities()).thenReturn(isConnected);
    }

    private void setUpCountries(boolean isConnected) {
        // arrange
        setUpConnectivity(isConnected);

        // act
        repository.getCountries(mockCountriesRepositoryCallback);
    }

    @Test
    public void getCountries_verifyFromRemoteDataSource_online() {
        // arrange
        setUpCountries(true);

        // assert
        verify(mockRemoteDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(countries);
        verify(mockCountriesRepositoryCallback).onSuccess(countries);
    }

    @Test
    public void getCountries_verifyFromLocalDataSource_online() {
        // arrange
        setUpCountries(true);

        // assert
        verify(mockRemoteDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(countries);
        verify(mockLocalDataSource).cacheCountries(countries);
        verify(mockCountriesRepositoryCallback).onSuccess(countries);
    }

    @Test
    public void getCountries_verifyNoDataFromRemoteDataSource_online() {
        // arrange
        setUpCountries(true);

        // assert
        verify(mockRemoteDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onNoDataLoaded();
        verifyNoMoreInteractions(mockLocalDataSource);
        verify(mockCountriesRepositoryCallback).onEmpty();
    }

    @Test
    public void getCountries_verifyFailure_online() {
        // arrange
        Throwable e = new Throwable();
        setUpCountries(true);

        // assert
        verify(mockRemoteDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onError(e);
        verifyNoMoreInteractions(mockLocalDataSource);
        verify(mockCountriesRepositoryCallback).onError(e.getMessage());
    }

    @Test
    public void getCountries_verifyFromLocalDataSource_offline() {
        // arrange
        setUpCountries(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(countries);
        verify(mockCountriesRepositoryCallback).onSuccess(countries);
    }

    @Test
    public void getCountries_verifyNoDataFromLocalDataSource_offline() {
        // arrange
        setUpCountries(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onNoDataLoaded();
        verify(mockCountriesRepositoryCallback).onEmpty();
    }

    @Test
    public void getCountries_verifyFailure_offline() {
        // arrange
        Throwable e = new Throwable();
        setUpCountries(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onError(e);
        verify(mockCountriesRepositoryCallback).onError(e.getMessage());
    }
}
