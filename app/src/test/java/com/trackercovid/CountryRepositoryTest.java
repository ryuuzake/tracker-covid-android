package com.trackercovid;

import android.content.SharedPreferences;

import com.trackercovid.interactor.CountryRemoteDataSource;
import com.trackercovid.interactor.CountryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryRepositoryTest {

    private CountryRepository spyRepository;
    @Mock
    private CountryRemoteDataSource mockRemoteDataSource;
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockSharedPreferencesEditor;

    @Before
    public void setUp() {
        spyRepository = new CountryRepository(mockRemoteDataSource, mockSharedPreferences);
        when(mockSharedPreferences.edit()).thenReturn(mockSharedPreferencesEditor);
    }

    @Test
    public void getCountries_verifyJsonMapping() {
        // act
        doReturn(null).when(spyRepository).getCountries();
    }
}
