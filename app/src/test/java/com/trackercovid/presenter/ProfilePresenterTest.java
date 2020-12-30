package com.trackercovid.presenter;

import com.trackercovid.callback.RepositoryCallback;
import com.trackercovid.contract.ProfileContract;
import com.trackercovid.interactor.UserRepository;
import com.trackercovid.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProfilePresenterTest {

    private ProfilePresenter presenter;
    @Mock
    private ProfileContract.View mockView;
    @Mock
    private UserRepository mockRepository;
    @Captor
    private ArgumentCaptor<RepositoryCallback<User>> repositoryCallbackArgumentCaptor;

    private String tName = "User Testing";
    private String tEmail = "test@example.com";
    private User tUserNoPass = new User(tName, tEmail, null);

    @Before
    public void setUp() {
        presenter = new ProfilePresenter(mockView, mockRepository);
    }

    @Test
    public void requestProfile_success() {
        // arrange

        // act
        presenter.requestProfile();

        // assert
        verify(mockRepository).getCurrentUser(repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onSuccess(tUserNoPass);
        verify(mockView).showProfile(tUserNoPass);
    }

    @Test
    public void requestProfile_noData() {
        // arrange

        // act
        presenter.requestProfile();

        // assert
        verify(mockRepository).getCurrentUser(repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onEmpty();
        verify(mockView).redirectToLogin();
    }

    @Test
    public void requestProfile_failed() {
        // arrange

        // act
        presenter.requestProfile();

        // assert
        verify(mockRepository).getCurrentUser(repositoryCallbackArgumentCaptor.capture());
        repositoryCallbackArgumentCaptor.getValue().onError("Error");
        verify(mockView).redirectToLogin();
    }
}