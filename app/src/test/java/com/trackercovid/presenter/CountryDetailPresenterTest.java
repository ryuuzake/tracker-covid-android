package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.CountryDetailContract;
import com.trackercovid.interactor.HistoricalRepository;
import com.trackercovid.model.Historical;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CountryDetailPresenterTest {

    private CountryDetailPresenter presenter;
    @Mock
    private HistoricalRepository mockRepository;
    @Mock
    private CountryDetailContract.View mockView;
    @Captor
    private ArgumentCaptor<RepositoryCallback<Historical>> repositoryCallbackArgumentCaptor;

    private String tCountryName = "Italy";

    @Before
    public void setUp() {
        presenter = new CountryDetailPresenter(mockView, mockRepository);
    }

    @Test
    public void requestHistoricalData_success() {
        // arrange
        Historical tHistorical = new Historical();
        tHistorical.setCountryName(tCountryName);

        // act
        presenter.requestHistoricalData(tCountryName);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getHistorical(eq(tCountryName), repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onSuccess(tHistorical);
        verify(mockView).stopLoading();
        verify(mockView).showHistoricalData(tHistorical);
    }

    @Test
    public void requestHistoricalData_emptyFail() {
        // arrange

        // act
        presenter.requestHistoricalData(tCountryName);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getHistorical(eq(tCountryName), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "No Historical Data Found.";
        repositoryCallbackArgumentCaptor.getValue().onEmpty();
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }

    @Test
    public void requestHistoricalData_fail() {
        // arrange

        // act
        presenter.requestHistoricalData(tCountryName);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getHistorical(eq(tCountryName), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "Request failed.";
        repositoryCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }
}