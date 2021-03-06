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

import static org.mockito.Matchers.eq;
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

    private static Throwable e = new Throwable();
    MockResponseFileReader fileReader = new MockResponseFileReader();
    private List<Country> tCountries;
    private Country tCountry;
    private String tCountryName = "Italy";

    @Before
    public void setUp() {
        repository = new CountryRepositoryImpl(
                mockRemoteDataSource,
                mockLocalDataSource,
                mockNetworkInfoSource
        );

        tCountries = createCountryList();
        tCountry = createCountry();
    }

    private Country createCountry() {
        CountryResponseUtil countryResponseUtil = new CountryResponseUtil();
        CountryResponse countryResponse = countryResponseUtil.fromJson(fileReader.readJson("country_response.json"));
        return countryResponseUtil.toCountryModel(countryResponse);
    }

    private List<Country> createCountryList() {
        CountryResponseUtil countryResponseUtil = new CountryResponseUtil();
        List<CountryResponse> countryResponses = countryResponseUtil
                .fromJsonList(fileReader.readJson("all_countries_response.json"));
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

    private void setUpCountry(boolean isConnected) {
        // arrange
        setUpConnectivity(isConnected);

        // act
        repository.getCountry(tCountryName, mockCountryRepositoryCallback);
    }

    @Test
    public void getCountries_verifyFromRemoteDataSource_online() {
        // arrange
        setUpCountries(true);

        // assert
        verify(mockRemoteDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(tCountries);
        verify(mockCountriesRepositoryCallback).onSuccess(tCountries);
    }

    @Test
    public void getCountries_verifyFromLocalDataSource_online() {
        // arrange
        setUpCountries(true);

        // assert
        verify(mockRemoteDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(tCountries);
        verify(mockLocalDataSource).cacheCountries(tCountries);
        verify(mockCountriesRepositoryCallback).onSuccess(tCountries);
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
        setUpCountries(true);

        // assert
        verify(mockRemoteDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onError(null);
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
        countriesLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(tCountries);
        verify(mockCountriesRepositoryCallback).onSuccess(tCountries);
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
        setUpCountries(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getCountries(countriesLoadDataCallbackArgumentCaptor.capture());
        countriesLoadDataCallbackArgumentCaptor.getValue().onError(null);
        verify(mockCountriesRepositoryCallback).onError(e.getMessage());
    }

    @Test
    public void getCountry_verifyFromRemoteDataSource_online() {
        // arrange
        setUpCountry(true);

        // assert
        verify(mockRemoteDataSource).getCountry(eq(tCountryName), countryLoadDataCallbackArgumentCaptor.capture());
        countryLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(tCountry);
        verify(mockCountryRepositoryCallback).onSuccess(tCountry);
    }

    @Test
    public void getCountry_verifyFromLocalDataSource_online() {
        /// Local Data available
        // arrange
        setUpCountry(true);

        // assert
        verify(mockRemoteDataSource).getCountry(eq(tCountryName), countryLoadDataCallbackArgumentCaptor.capture());
        countryLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(tCountry);
        verify(mockLocalDataSource).cacheCountry(tCountry);
        verify(mockCountryRepositoryCallback).onSuccess(tCountry);
    }

    @Test
    public void getCountry_verifyNoDataFromRemoteDataSource_online() {
        /// Local Data not available, fetch from api, empty response
        // arrange
        setUpCountry(true);

        // assert
        verify(mockRemoteDataSource).getCountry(eq(tCountryName), countryLoadDataCallbackArgumentCaptor.capture());
        countryLoadDataCallbackArgumentCaptor.getValue().onNoDataLoaded();
        verifyNoMoreInteractions(mockLocalDataSource);
        verify(mockCountryRepositoryCallback).onEmpty();
    }

    @Test
    public void getCountry_verifyFailure_online() {
        // arrange
        setUpCountry(true);

        // assert
        verify(mockRemoteDataSource).getCountry(eq(tCountryName), countryLoadDataCallbackArgumentCaptor.capture());
        countryLoadDataCallbackArgumentCaptor.getValue().onError(null);
        verifyNoMoreInteractions(mockLocalDataSource);
        verify(mockCountryRepositoryCallback).onError(e.getMessage());
    }

    @Test
    public void getCountry_verifyFromLocalDataSource_offline() {
        /// Local Data available
        // arrange
        setUpCountry(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getCountry(eq(tCountryName), countryLoadDataCallbackArgumentCaptor.capture());
        countryLoadDataCallbackArgumentCaptor.getValue().onDataLoaded(tCountry);
        verify(mockCountryRepositoryCallback).onSuccess(tCountry);
    }

    @Test
    public void getCountry_verifyNoDataFromLocalDataSource_offline() {
        /// Local Data no data
        // arrange
        setUpCountry(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getCountry(eq(tCountryName), countryLoadDataCallbackArgumentCaptor.capture());
        countryLoadDataCallbackArgumentCaptor.getValue().onNoDataLoaded();
        verify(mockCountryRepositoryCallback).onEmpty();
    }

    @Test
    public void getCountry_verifyFailure_offline() {
        // arrange
        setUpCountry(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getCountry(eq(tCountryName), countryLoadDataCallbackArgumentCaptor.capture());
        countryLoadDataCallbackArgumentCaptor.getValue().onError(null);
        verify(mockCountryRepositoryCallback).onError(e.getMessage());
    }
}
