package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.LoginContract;
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
public class LoginPresenterTest {

    private LoginPresenter presenter;
    @Mock
    private LoginContract.View mockView;
    @Mock
    private UserRepository mockRepository;
    @Captor
    private ArgumentCaptor<RepositoryCallback<User>> repositoryCallbackArgumentCaptor;

    private String tName = "User Testing";
    private String tEmail = "test@example.com";
    private String tPassword = "test";
    private User tUser = new User(tEmail, tPassword);
    private User tUserNoPass = new User(tName, tEmail, null);

    @Before
    public void setUp() {
        presenter = new LoginPresenter(mockView, mockRepository);
    }

    @Test
    public void login_success() {
        // arrange

        // act
        presenter.login(tEmail, tPassword);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).loginUser(eq(tUser), repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onSuccess(tUserNoPass);
        verify(mockView).stopLoading();
        verify(mockView).redirectToHome();
    }

    @Test
    public void login_emptyFail() {
        // arrange

        // act
        presenter.login(tEmail, tPassword);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).loginUser(eq(tUser), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "No User Found.";
        repositoryCallbackArgumentCaptor.getValue().onEmpty();
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }

    @Test
    public void login_fail() {
        // arrange

        // act
        presenter.login(tEmail, tPassword);

        // assert
        verify(mockView).startLoading();
        verify(mockRepository).loginUser(eq(tUser), repositoryCallbackArgumentCaptor.capture());
        String errorMessage = "Authentication failed.";
        repositoryCallbackArgumentCaptor.getValue().onError(errorMessage);
        verify(mockView).stopLoading();
        verify(mockView).showError(eq(errorMessage));
    }
}