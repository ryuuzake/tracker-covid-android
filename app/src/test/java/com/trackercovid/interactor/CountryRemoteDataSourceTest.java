package com.trackercovid.interactor;

import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Country;
import com.trackercovid.retrofit.CountryService;
import com.trackercovid.util.CountryResponseUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryRemoteDataSourceTest {

    private CountryRemoteDataSourceImpl countryRemoteDataSource;
    private MockWebServer server = new MockWebServer();

    @Mock
    private CountryService mockService;
    @Mock
    private Call<List<CountryResponse>> mockCallCountryResponses;
    @Captor
    private ArgumentCaptor<Callback<List<CountryResponse>>> countriesCallbackArgumentCaptor;
    @Mock
    private LoadDataCallback<List<Country>> mockCountriesLoadDataCallback;
    @Mock
    private CountryResponseUtil mockCountryResponseUtil;

    private List<CountryResponse> countryResponses;
    private List<Country> countries;

    @Before
    public void setUp() {
        countryRemoteDataSource = new CountryRemoteDataSourceImpl(mockService, mockCountryResponseUtil);

        String json = new MockResponseFileReader().readJson("all_countries_response.json");
        CountryResponseUtil countryResponseUtil = new CountryResponseUtil();
        countryResponses = countryResponseUtil.fromJsonList(json);
        countries = countryResponseUtil.toCountryModel(countryResponses);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void getCountries_success() {
        // arrange
        when(mockService.getAllCountries(anyString())).thenReturn(mockCallCountryResponses);
        when(mockCountryResponseUtil.toCountryModel(countryResponses)).thenReturn(countries);

        // act
        countryRemoteDataSource.getCountries(mockCountriesLoadDataCallback);

        // assert
        verify(mockService).getAllCountries(null);
        verify(mockCallCountryResponses).enqueue(countriesCallbackArgumentCaptor.capture());
        countriesCallbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponses, Response.success(countryResponses));
        verify(mockCountryResponseUtil).toCountryModel(countryResponses);
        verify(mockCountriesLoadDataCallback).onDataLoaded(countries);
    }

    @Test
    public void getCountries_successNoData() {
        // arrange
        when(mockService.getAllCountries(anyString())).thenReturn(mockCallCountryResponses);
        when(mockCountryResponseUtil.toCountryModel(countryResponses)).thenReturn(countries);

        // act
        countryRemoteDataSource.getCountries(mockCountriesLoadDataCallback);

        // assert
        verify(mockService).getAllCountries(null);
        verify(mockCallCountryResponses).enqueue(countriesCallbackArgumentCaptor.capture());
        countriesCallbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponses, Response.success(null));
        verify(mockCountriesLoadDataCallback).onNoDataLoaded();
    }

    @Test
    public void getCountries_failed() {
        // arrange
        when(mockService.getAllCountries(anyString())).thenReturn(mockCallCountryResponses);
        when(mockCountryResponseUtil.toCountryModel(countryResponses)).thenReturn(countries);
        ResponseBody body = ResponseBody.create("", MediaType.parse("application/json"));
        Response<List<CountryResponse>> error = Response.error(404, body);

        // act
        countryRemoteDataSource.getCountries(mockCountriesLoadDataCallback);

        // assert
        verify(mockService).getAllCountries(null);
        verify(mockCallCountryResponses).enqueue(countriesCallbackArgumentCaptor.capture());
        countriesCallbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponses, error);
        verify(mockCountriesLoadDataCallback).onError(error.message(), null);
    }

    @Test
    public void getCountries_failure() {
        // arrange
        Throwable t = new Throwable();
        when(mockService.getAllCountries(anyString())).thenReturn(mockCallCountryResponses);
        when(mockCountryResponseUtil.toCountryModel(countryResponses)).thenReturn(countries);

        // act
        countryRemoteDataSource.getCountries(mockCountriesLoadDataCallback);

        // assert
        verify(mockService).getAllCountries(null);
        verify(mockCallCountryResponses).enqueue(countriesCallbackArgumentCaptor.capture());
        countriesCallbackArgumentCaptor.getValue()
                .onFailure(mockCallCountryResponses, t);
        verify(mockCountriesLoadDataCallback).onError(null, t);
    }

    @Test
    public void getCountry() {
        assertTrue(false);
    }
}