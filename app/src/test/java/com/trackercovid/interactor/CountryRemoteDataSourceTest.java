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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
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
    @Mock
    private Call<CountryResponse> mockCallCountryResponse;
    @Captor
    private ArgumentCaptor<Callback<List<CountryResponse>>> countriesCallbackArgumentCaptor;
    @Captor
    private ArgumentCaptor<Callback<CountryResponse>> countryCallbackArgumentCaptor;
    @Mock
    private LoadDataCallback<List<Country>> mockCountriesLoadDataCallback;
    @Mock
    private LoadDataCallback<Country> mockCountryLoadDataCallback;
    @Mock
    private CountryResponseUtil mockCountryResponseUtil;

    private List<CountryResponse> tCountryResponses;
    private List<Country> tCountries;
    private CountryResponse tCountryResponse;
    private Country tCountry;
    private String tCountryName = "Italy";
    private Throwable t = new Throwable();

    @Before
    public void setUp() {
        countryRemoteDataSource = new CountryRemoteDataSourceImpl(mockService, mockCountryResponseUtil);

        String jsonList = new MockResponseFileReader().readJson("all_countries_response.json");
        String json = new MockResponseFileReader().readJson("country_response.json");
        CountryResponseUtil countryResponseUtil = new CountryResponseUtil();
        tCountryResponses = countryResponseUtil.fromJsonList(jsonList);
        tCountryResponse = countryResponseUtil.fromJson(json);
        tCountries = countryResponseUtil.toCountryModel(tCountryResponses);
        tCountry = countryResponseUtil.toCountryModel(tCountryResponse);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void getCountries_success() {
        // arrange
        when(mockService.getAllCountries(anyString())).thenReturn(mockCallCountryResponses);
        when(mockCountryResponseUtil.toCountryModel(tCountryResponses)).thenReturn(tCountries);

        // act
        countryRemoteDataSource.getCountries(mockCountriesLoadDataCallback);

        // assert
        verify(mockService).getAllCountries(null);
        verify(mockCallCountryResponses).enqueue(countriesCallbackArgumentCaptor.capture());
        countriesCallbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponses, Response.success(tCountryResponses));
        verify(mockCountryResponseUtil).toCountryModel(tCountryResponses);
        verify(mockCountriesLoadDataCallback).onDataLoaded(tCountries);
    }

    @Test
    public void getCountries_successNoData() {
        // arrange
        when(mockService.getAllCountries(anyString())).thenReturn(mockCallCountryResponses);
        when(mockCountryResponseUtil.toCountryModel(tCountryResponses)).thenReturn(tCountries);

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
        when(mockCountryResponseUtil.toCountryModel(tCountryResponses)).thenReturn(tCountries);
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
        when(mockService.getAllCountries(anyString())).thenReturn(mockCallCountryResponses);
        when(mockCountryResponseUtil.toCountryModel(tCountryResponses)).thenReturn(tCountries);

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
    public void getCountry_success() {
        // arrange
        when(mockService.getCountry(eq(tCountryName), anyString())).thenReturn(mockCallCountryResponse);
        when(mockCountryResponseUtil.toCountryModel(tCountryResponse)).thenReturn(tCountry);

        // act
        countryRemoteDataSource.getCountry(tCountryName, mockCountryLoadDataCallback);

        // assert
        verify(mockService).getCountry(eq(tCountryName), anyString());
        verify(mockCallCountryResponse).enqueue(countryCallbackArgumentCaptor.capture());
        countryCallbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponse, Response.success(tCountryResponse));
        verify(mockCountryResponseUtil).toCountryModel(tCountryResponse);
        verify(mockCountryLoadDataCallback).onDataLoaded(tCountry);
    }

    @Test
    public void getCountry_successNoData() {
        // arrange
        when(mockService.getCountry(eq(tCountryName), anyString())).thenReturn(mockCallCountryResponse);
        when(mockCountryResponseUtil.toCountryModel(tCountryResponse)).thenReturn(tCountry);

        // act
        countryRemoteDataSource.getCountry(tCountryName, mockCountryLoadDataCallback);

        // assert
        verify(mockService).getCountry(eq(tCountryName), anyString());
        verify(mockCallCountryResponse).enqueue(countryCallbackArgumentCaptor.capture());
        countryCallbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponse, Response.success(null));
        verify(mockCountryLoadDataCallback).onNoDataLoaded();
    }

    @Test
    public void getCountry_failed() {
        // arrange
        ResponseBody body = ResponseBody.create("", MediaType.parse("application/json"));
        Response<CountryResponse> error = Response.error(404, body);
        when(mockService.getCountry(eq(tCountryName), anyString())).thenReturn(mockCallCountryResponse);
        when(mockCountryResponseUtil.toCountryModel(tCountryResponse)).thenReturn(tCountry);

        // act
        countryRemoteDataSource.getCountry(tCountryName, mockCountryLoadDataCallback);

        // assert
        verify(mockService).getCountry(eq(tCountryName), anyString());
        verify(mockCallCountryResponse).enqueue(countryCallbackArgumentCaptor.capture());
        countryCallbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponse, error);
        verify(mockCountryLoadDataCallback).onError(error.message(), null);
    }

    @Test
    public void getCountry_failure() {
        // arrange
        when(mockService.getCountry(eq(tCountryName), anyString())).thenReturn(mockCallCountryResponse);
        when(mockCountryResponseUtil.toCountryModel(tCountryResponse)).thenReturn(tCountry);

        // act
        countryRemoteDataSource.getCountry(tCountryName, mockCountryLoadDataCallback);

        // assert
        verify(mockService).getCountry(eq(tCountryName), anyString());
        verify(mockCallCountryResponse).enqueue(countryCallbackArgumentCaptor.capture());
        countryCallbackArgumentCaptor.getValue()
                .onFailure(mockCallCountryResponse, t);
        verify(mockCountryLoadDataCallback).onError(null, t);
    }
}