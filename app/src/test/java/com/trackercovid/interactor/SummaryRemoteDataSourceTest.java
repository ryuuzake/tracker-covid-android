package com.trackercovid.interactor;

import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.CountryResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Summary;
import com.trackercovid.retrofit.CountryService;
import com.trackercovid.util.CountryResponseUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SummaryRemoteDataSourceTest {

    private SummaryRemoteDataSource remoteDataSource;

    @Mock
    private CountryService mockService;
    @Mock
    private Call<CountryResponse> mockCallCountryResponse;
    @Captor
    private ArgumentCaptor<Callback<CountryResponse>> callbackArgumentCaptor;
    @Mock
    private LoadDataCallback<Summary> mockLoadDataCallback;
    @Mock
    private CountryResponseUtil mockCountryResponseUtil;

    private CountryResponse tCountryResponse;
    private Summary tSummary;
    private Throwable t = new Throwable();

    @Before
    public void setUp() {
        remoteDataSource = new SummaryRemoteDataSourceImpl(mockService, mockCountryResponseUtil);

        final String json = new MockResponseFileReader().readJson("all_response.json");
        CountryResponseUtil countryResponseUtil = new CountryResponseUtil();
        tCountryResponse = countryResponseUtil.fromJson(json);
        tSummary = countryResponseUtil.toSummaryModel(tCountryResponse);
    }

    @Test
    public void getSummary_success() {
        // arrange
        when(mockService.getSummary()).thenReturn(mockCallCountryResponse);
        when(mockCountryResponseUtil.toSummaryModel(tCountryResponse)).thenReturn(tSummary);

        // act
        remoteDataSource.getSummary(mockLoadDataCallback);

        // assert
        verify(mockService).getSummary();
        verify(mockCallCountryResponse).enqueue(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponse, Response.success(tCountryResponse));
        verify(mockCountryResponseUtil).toSummaryModel(tCountryResponse);
        verify(mockLoadDataCallback).onDataLoaded(tSummary);
    }

    @Test
    public void getSummary_successNoData() {
        // arrange
        when(mockService.getSummary()).thenReturn(mockCallCountryResponse);
        when(mockCountryResponseUtil.toSummaryModel(tCountryResponse)).thenReturn(tSummary);

        // act
        remoteDataSource.getSummary(mockLoadDataCallback);

        // assert
        verify(mockService).getSummary();
        verify(mockCallCountryResponse).enqueue(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue()
                .onResponse(mockCallCountryResponse, Response.success(null));
        verify(mockLoadDataCallback).onNoDataLoaded();
    }

    @Test
    public void getSummary_failed() {
        // arrange
        ResponseBody body = ResponseBody.create("", MediaType.parse("application/json"));
        Response<CountryResponse> error = Response.error(404, body);
        when(mockService.getSummary()).thenReturn(mockCallCountryResponse);
        when(mockCountryResponseUtil.toSummaryModel(tCountryResponse)).thenReturn(tSummary);

        // act
        remoteDataSource.getSummary(mockLoadDataCallback);

        // assert
        verify(mockService).getSummary();
        verify(mockCallCountryResponse).enqueue(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onResponse(mockCallCountryResponse, error);
        verify(mockLoadDataCallback).onError(error.message());
    }

    @Test
    public void getSummary_failure() {
        // arrange
        when(mockService.getSummary()).thenReturn(mockCallCountryResponse);
        when(mockCountryResponseUtil.toSummaryModel(tCountryResponse)).thenReturn(tSummary);

        // act
        remoteDataSource.getSummary(mockLoadDataCallback);

        // assert
        verify(mockService).getSummary();
        verify(mockCallCountryResponse).enqueue(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onFailure(mockCallCountryResponse, t);
        verify(mockLoadDataCallback).onError(t.getMessage());
    }
}