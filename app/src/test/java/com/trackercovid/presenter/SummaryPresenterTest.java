package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.SummaryContract;
import com.trackercovid.interactor.SummaryRepository;
import com.trackercovid.model.Summary;

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
public class SummaryPresenterTest {

    private SummaryPresenter presenter;
    @Mock
    private SummaryRepository mockRepository;
    @Mock
    private SummaryContract.View mockView;
    @Captor
    private ArgumentCaptor<RepositoryCallback<Summary>> repositoryCallbackArgumentCaptor;

    private Summary tSummary = new Summary();

    @Before
    public void setUp() {
        presenter = new SummaryPresenter(mockView, mockRepository);

        tSummary.setActive(10_000L);
    }

    @Test
    public void requestCountrySummary_success() {
        // arrange

        // act
        presenter.requestSummary();

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getSummary(repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onSuccess(tSummary);
        verify(mockView).stopLoading();
        verify(mockView).showSummary(tSummary);
    }

    @Test
    public void requestCountrySummary_emptyFail() {
        // arrange

        // act
        presenter.requestSummary();

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getSummary(repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "No Summary Data Found.";
        repositoryCallbackArgumentCaptor.getValue().onEmpty();
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }

    @Test
    public void requestCountrySummary_fail() {
        // arrange

        // act
        presenter.requestSummary();

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getSummary(repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "Request failed.";
        repositoryCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }
}