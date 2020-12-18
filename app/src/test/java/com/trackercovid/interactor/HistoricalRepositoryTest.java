package com.trackercovid.interactor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.HistoricalResponse;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Historical;
import com.trackercovid.util.HistoricalResponseUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HistoricalRepositoryTest {

    private static Throwable e = new Throwable();
    MockResponseFileReader fileReader = new MockResponseFileReader();
    private HistoricalRepository repository;
    @Mock
    private HistoricalRemoteDataSource mockRemoteDataSource;
    @Mock
    private NetworkInfoSource mockNetworkInfo;
    @Mock
    private RepositoryCallback<Historical> mockHistoricalRepositoryCallback;
    @Captor
    private ArgumentCaptor<LoadDataCallback<Historical>> loadDataCallbackArgumentCaptor;
    private HistoricalResponseUtil historicalResponseUtil = new HistoricalResponseUtil();
    private String tCountryName = "Spain";
    private Historical tHistorical;

    @Before
    public void setUp() {
        repository = new HistoricalRepositoryImpl(
                mockRemoteDataSource,
                mockNetworkInfo
        );

        tHistorical = createHistorical();
    }

    private Historical createHistorical() {
        String json = fileReader.readJson("historical_response.json");
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat("MM/dd/yy")
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();
        HistoricalResponse fromJson = gson.fromJson(json, HistoricalResponse.class);
        return historicalResponseUtil.toHistoricalModel(fromJson);
    }

    private void setUpHistorical(boolean isConnected) {
        // arrange
        when(mockNetworkInfo.getNetworkCapabilities()).thenReturn(isConnected);

        // act
        repository.getHistorical(tCountryName, mockHistoricalRepositoryCallback);
    }

    @Test
    public void getHistorical_verifyFromRemoteDataSource_online() {
        // arrange
        setUpHistorical(true);

        // assert
        verify(mockRemoteDataSource).getHistorical(eq(tCountryName), loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onDataLoaded(tHistorical);
        verify(mockHistoricalRepositoryCallback).onSuccess(tHistorical);
    }

    @Test
    public void getHistorical_verifyNoDataFromRemoteDataSource_online() {
        // arrange
        setUpHistorical(true);

        // assert
        verify(mockRemoteDataSource).getHistorical(eq(tCountryName), loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onNoDataLoaded();
        verify(mockHistoricalRepositoryCallback).onEmpty();
    }

    @Test
    public void getHistorical_verifyFailureFromRemoteDataSource_online() {
        // arrange
        setUpHistorical(true);

        // assert
        verify(mockRemoteDataSource).getHistorical(eq(tCountryName), loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onError(e.getMessage());
        verify(mockHistoricalRepositoryCallback).onError(e.getMessage());
    }

    @Test
    public void getHistorical_verifyFailureFromRemoteDataSource_offline() {
        // arrange
        setUpHistorical(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockHistoricalRepositoryCallback).onError(eq("No Internet Connection"));
    }
}