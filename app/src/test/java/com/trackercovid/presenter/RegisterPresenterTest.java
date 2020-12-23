package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.RegisterContract;
import com.trackercovid.interactor.UserRepository;
import com.trackercovid.model.User;

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
public class RegisterPresenterTest {

    private RegisterPresenter presenter;
    @Mock
    private RegisterContract.View mockView;
    @Mock
    private UserRepository mockRepository;
    @Captor
    private ArgumentCaptor<RepositoryCallback<User>> repositoryCallbackArgumentCaptor;

    private String tName = "User Testing";
    private String tEmail = "test@example.com";
    private String tPassword = "test";
    private User tUser = new User(tName, tEmail, tPassword);
    private User tUserNoPass = new User(tName, tEmail, null);

    @Before
    public void setUp() {
        presenter = new RegisterPresenter(mockView, mockRepository);
    }

    @Test
    public void register_success() {
        // arrange

        // act
        presenter.register(tName, tEmail, tPassword);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).createUser(eq(tUser), repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onSuccess(tUserNoPass);
        verify(mockView).stopLoading();
        verify(mockView).redirectToLogin();
    }

    @Test
    public void register_emptyFail() {
        // arrange

        // act
        presenter.register(tName, tEmail, tPassword);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).createUser(eq(tUser), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "Cannot Create User.";
        repositoryCallbackArgumentCaptor.getValue().onEmpty();
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }

    @Test
    public void register_fail() {
        // arrange

        // act
        presenter.register(tName, tEmail, tPassword);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).createUser(eq(tUser), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "Authentication failed.";
        repositoryCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }
}