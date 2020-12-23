package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.CaseCountryContract;
import com.trackercovid.interactor.CountryRepository;
import com.trackercovid.model.Country;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CaseCountryPresenterTest {

    private CaseCountryPresenter presenter;
    @Mock
    private CountryRepository mockRepository;
    @Mock
    private CaseCountryContract.View mockView;
    @Captor
    private ArgumentCaptor<RepositoryCallback<List<Country>>> repositoryCallbackArgumentCaptor;

    private List<Country> tCountries;

    @Before
    public void setUp() {
        presenter = new CaseCountryPresenter(mockView, mockRepository);
    }

    @Test
    public void requestCountries_success() {
        // arrange

        // act
        presenter.requestCountries();

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountries(repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onSuccess(tCountries);
        verify(mockView).stopLoading();
        verify(mockView).showCountries(tCountries);
    }

    @Test
    public void requestCountries_emptyFail() {
        // arrange

        // act
        presenter.requestCountries();

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountries(repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "No Countries Data Found.";
        repositoryCallbackArgumentCaptor.getValue().onEmpty();
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }

    @Test
    public void requestCountries_fail() {
        // arrange

        // act
        presenter.requestCountries();

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountries(repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "Request failed.";
        repositoryCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }
}