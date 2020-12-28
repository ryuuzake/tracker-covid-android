package com.trackercovid.interactor;

import com.google.gson.Gson;
import com.trackercovid.MockResponseFileReader;
import com.trackercovid.callback.LoadDataCallback;
import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.model.Summary;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SummaryRepositoryTest {

    private static Throwable e = new Throwable();
    MockResponseFileReader fileReader = new MockResponseFileReader();
    private SummaryRepository repository;
    @Mock
    private SummaryRemoteDataSource mockRemoteDataSource;
    @Mock
    private SummaryLocalDataSource mockLocalDataSource;
    @Mock
    private NetworkInfoSource mockNetworkInfoSource;
    @Mock
    private RepositoryCallback<Summary> mockRepositoryCallback;
    @Captor
    private ArgumentCaptor<LoadDataCallback<Summary>> loadDataCallbackArgumentCaptor;
    private Summary tSummary;

    @Before
    public void setUp() {
        repository = new SummaryRepositoryImpl(
                mockRemoteDataSource,
                mockLocalDataSource,
                mockNetworkInfoSource
        );

        tSummary = new Gson().fromJson(fileReader.readJson("all_response.json"), Summary.class);
    }

    private void setUpConnectivity(boolean isConnected) {
        when(mockNetworkInfoSource.getNetworkCapabilities()).thenReturn(isConnected);
    }

    private void setUpAll(boolean isConnected) {
        // arrange
        setUpConnectivity(isConnected);

        // act
        repository.getSummary(mockRepositoryCallback);
    }


    @Test
    public void getAll_verifyFromRemoteDataSource_online() {
        // arrange
        setUpAll(true);

        // assert
        verify(mockRemoteDataSource).getSummary(loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onDataLoaded(tSummary);
        verify(mockRepositoryCallback).onSuccess(tSummary);
    }

    @Test
    public void getSummary_verifyFromLocalDataSource_online() {
        /// Local Data available
        // arrange
        setUpAll(true);

        // assert
        verify(mockRemoteDataSource).getSummary(loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onDataLoaded(tSummary);
        verify(mockLocalDataSource).cacheSummary(tSummary);
        verify(mockRepositoryCallback).onSuccess(tSummary);
    }

    @Test
    public void getSummary_verifyNoDataFromRemoteDataSource_online() {
        /// Local Data not available, fetch from api, empty response
        // arrange
        setUpAll(true);

        // assert
        verify(mockRemoteDataSource).getSummary(loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onNoDataLoaded();
        verifyNoMoreInteractions(mockLocalDataSource);
        verify(mockRepositoryCallback).onEmpty();
    }

    @Test
    public void getSummary_verifyFailure_online() {
        // arrange
        setUpAll(true);

        // assert
        verify(mockRemoteDataSource).getSummary(loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onError(null);
        verifyNoMoreInteractions(mockLocalDataSource);
        verify(mockRepositoryCallback).onError(e.getMessage());
    }

    @Test
    public void getSummary_verifyFromLocalDataSource_offline() {
        /// Local Data available
        // arrange
        setUpAll(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getSummary(loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onDataLoaded(tSummary);
        verify(mockRepositoryCallback).onSuccess(tSummary);
    }

    @Test
    public void getSummary_verifyNoDataFromLocalDataSource_offline() {
        /// Local Data no data
        // arrange
        setUpAll(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getSummary(loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onNoDataLoaded();
        verify(mockRepositoryCallback).onEmpty();
    }

    @Test
    public void getSummary_verifyFailure_offline() {
        // arrange
        setUpAll(false);

        // assert
        verifyNoMoreInteractions(mockRemoteDataSource);
        verify(mockLocalDataSource).getSummary(loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onError(null);
        verify(mockRepositoryCallback).onError(e.getMessage());
    }

}