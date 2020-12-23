package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.SummaryContract;
import com.trackercovid.interactor.CountryRepository;
import com.trackercovid.model.Country;

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
    private CountryRepository mockRepository;
    @Mock
    private SummaryContract.View mockView;
    @Captor
    private ArgumentCaptor<RepositoryCallback<Country>> repositoryCallbackArgumentCaptor;

    private Country tCountry = new Country();

    @Before
    public void setUp() {
        presenter = new SummaryPresenter(mockView, mockRepository);

        tCountry.setName("Italy");
    }

    @Test
    public void requestCountrySummary_success() {
        // arrange

        // act
        presenter.requestCountrySummary(tCountry);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountry(eq(tCountry.getName()), repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onSuccess(tCountry);
        verify(mockView).stopLoading();
        verify(mockView).showCountrySummary(tCountry);
    }

    @Test
    public void requestCountrySummary_emptyFail() {
        // arrange

        // act
        presenter.requestCountrySummary(tCountry);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountry(eq(tCountry.getName()), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "No Summary Data Found.";
        repositoryCallbackArgumentCaptor.getValue().onEmpty();
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }

    @Test
    public void requestCountrySummary_fail() {
        // arrange

        // act
        presenter.requestCountrySummary(tCountry);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountry(eq(tCountry.getName()), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "Request failed.";
        repositoryCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }
}