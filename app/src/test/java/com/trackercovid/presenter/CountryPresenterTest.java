package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.CountryContract;
import com.trackercovid.interactor.CountryRepository;
import com.trackercovid.model.Country;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class CountryPresenterTest {

    private CountryPresenter presenter;
    @Mock
    private CountryRepository mockRepository;
    @Mock
    private CountryContract.View mockView;
    @Captor
    private ArgumentCaptor<RepositoryCallback<Country>> repositoryCallbackArgumentCaptor;

    private Country tCountry = new Country();

    @Before
    public void setUp() {
        presenter = new CountryPresenter(mockView, mockRepository);

        tCountry.setName("Italy");
    }

    @Test
    public void requestCountryData_success() {
        // arrange

        // act
        presenter.requestCountryData(tCountry);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountry(eq(tCountry.getName()), repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onSuccess(tCountry);
        verify(mockView).stopLoading();
        verify(mockView).showCountryData(tCountry);
    }

    @Test
    public void requestCountryData_emptyFail() {
        // arrange

        // act
        presenter.requestCountryData(tCountry);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountry(eq(tCountry.getName()), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "No Country Data Found.";
        repositoryCallbackArgumentCaptor.getValue().onEmpty();
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }

    @Test
    public void requestCountryData_fail() {
        // arrange

        // act
        presenter.requestCountryData(tCountry);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).getCountry(eq(tCountry.getName()), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "Request failed.";
        repositoryCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }
}