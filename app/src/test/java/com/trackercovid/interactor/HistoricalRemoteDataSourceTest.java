package com.trackercovid.interactor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.HistoricalResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.model.Historical;
import com.trackercovid.retrofit.HistoricalService;
import com.trackercovid.util.HistoricalResponseUtil;

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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HistoricalRemoteDataSourceTest {

    private static Throwable t = new Throwable();
    private HistoricalRemoteDataSource remoteDataSource;
    @Mock
    private HistoricalService mockService;
    @Mock
    private HistoricalResponseUtil mockUtil;
    @Mock
    private Call<HistoricalResponse> mockCallHistoryResponse;
    @Mock
    private LoadDataCallback<Historical> mockLoadDataCallback;
    @Captor
    private ArgumentCaptor<Callback<HistoricalResponse>> historicalCallbackArgumentCaptor;
    private String tCountryName = "Spain";
    private HistoricalResponse tHistoricalResponse;
    private Historical tHistorical;

    @Before
    public void setUp() {
        remoteDataSource = new HistoricalRemoteDataSourceImpl(mockService, mockUtil);

        createTestData();
    }

    private void createTestData() {
        HistoricalResponseUtil util = new HistoricalResponseUtil();
        String json = new MockResponseFileReader().readJson("historical_response.json");
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat("MM/dd/yy")
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();
        tHistoricalResponse = gson.fromJson(json, HistoricalResponse.class);
        tHistorical = util.toHistoricalModel(tHistoricalResponse);
    }

    @Test
    public void getHistorical_success() {
        // arrange
        when(mockService.getHistoricalData(eq(tCountryName))).thenReturn(mockCallHistoryResponse);
        when(mockUtil.toHistoricalModel(tHistoricalResponse)).thenReturn(tHistorical);

        // act
        remoteDataSource.getHistorical(tCountryName, mockLoadDataCallback);

        // assert
        verify(mockService).getHistoricalData(eq(tCountryName));
        verify(mockCallHistoryResponse).enqueue(historicalCallbackArgumentCaptor.capture());
        historicalCallbackArgumentCaptor.getValue()
                .onResponse(mockCallHistoryResponse, Response.success(tHistoricalResponse));
        verify(mockUtil).toHistoricalModel(tHistoricalResponse);
        verify(mockLoadDataCallback).onDataLoaded(tHistorical);
    }

    @Test
    public void getHistorical_successNoData() {
        // arrange
        when(mockService.getHistoricalData(eq(tCountryName))).thenReturn(mockCallHistoryResponse);
        when(mockUtil.toHistoricalModel(tHistoricalResponse)).thenReturn(tHistorical);

        // act
        remoteDataSource.getHistorical(tCountryName, mockLoadDataCallback);

        // assert
        verify(mockService).getHistoricalData(eq(tCountryName));
        verify(mockCallHistoryResponse).enqueue(historicalCallbackArgumentCaptor.capture());
        historicalCallbackArgumentCaptor.getValue()
                .onResponse(mockCallHistoryResponse, Response.success(null));
        verify(mockLoadDataCallback).onNoDataLoaded();
    }

    @Test
    public void getHistorical_failed() {
        // arrange
        ResponseBody body = ResponseBody.create("", MediaType.parse("application/json"));
        Response<HistoricalResponse> error = Response.error(404, body);
        when(mockService.getHistoricalData(eq(tCountryName))).thenReturn(mockCallHistoryResponse);
        when(mockUtil.toHistoricalModel(tHistoricalResponse)).thenReturn(tHistorical);

        // act
        remoteDataSource.getHistorical(tCountryName, mockLoadDataCallback);

        // assert
        verify(mockService).getHistoricalData(eq(tCountryName));
        verify(mockCallHistoryResponse).enqueue(historicalCallbackArgumentCaptor.capture());
        historicalCallbackArgumentCaptor.getValue()
                .onResponse(mockCallHistoryResponse, error);
        verify(mockLoadDataCallback).onError(error.message());
    }

    @Test
    public void getHistorical_failure() {
        // arrange
        when(mockService.getHistoricalData(eq(tCountryName))).thenReturn(mockCallHistoryResponse);
        when(mockUtil.toHistoricalModel(tHistoricalResponse)).thenReturn(tHistorical);

        // act
        remoteDataSource.getHistorical(tCountryName, mockLoadDataCallback);

        // assert
        verify(mockService).getHistoricalData(eq(tCountryName));
        verify(mockCallHistoryResponse).enqueue(historicalCallbackArgumentCaptor.capture());
        historicalCallbackArgumentCaptor.getValue()
                .onFailure(mockCallHistoryResponse, t);
        verify(mockLoadDataCallback).onError(t.getMessage());
    }
}