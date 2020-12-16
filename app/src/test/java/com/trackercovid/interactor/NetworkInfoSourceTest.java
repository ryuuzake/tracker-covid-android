package com.trackercovid.interactor;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import com.trackercovid.util.BuildVersionProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetworkInfoSourceTest {

    private NetworkInfoSourceImpl networkInfoSource;
    @Mock
    private ConnectivityManager mockConnectivityManager;
    @Mock
    private BuildVersionProvider mockBuildVersionProvider;
    @Mock
    private Network mockNetwork;
    @Mock
    private NetworkCapabilities mockNetworkCapabilities;

    @Before
    public void setUp() {
        networkInfoSource = new NetworkInfoSourceImpl(mockConnectivityManager, mockBuildVersionProvider);
    }

    @Test
    public void getNetworkCapabilities_validated() {
        // arrange
        when(mockBuildVersionProvider.isMarshmallowAndAbove()).thenReturn(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockNetwork);
        when(mockConnectivityManager.getNetworkCapabilities(any(Network.class))).thenReturn(mockNetworkCapabilities);
        when(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)).thenReturn(true);

        // act
        boolean validated = networkInfoSource.getNetworkCapabilities();

        // assert
        verify(mockConnectivityManager).getActiveNetwork();
        verify(mockConnectivityManager).getNetworkCapabilities(mockNetwork);
        verify(mockNetworkCapabilities).hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        assertTrue(validated);
    }

    @Test
    public void getNetworkCapabilities_notValidated() {
        // arrange
        when(mockBuildVersionProvider.isMarshmallowAndAbove()).thenReturn(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockNetwork);
        when(mockConnectivityManager.getNetworkCapabilities(any(Network.class))).thenReturn(mockNetworkCapabilities);
        when(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)).thenReturn(false);

        // act
        boolean validated = networkInfoSource.getNetworkCapabilities();

        // assert
        verify(mockConnectivityManager).getActiveNetwork();
        verify(mockConnectivityManager).getNetworkCapabilities(mockNetwork);
        verify(mockNetworkCapabilities).hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        assertFalse(validated);
    }

    @Test
    public void getNetworkCapabilities_sdkBelowMarshmallow() {
        // arrange
        when(mockBuildVersionProvider.isMarshmallowAndAbove()).thenReturn(false);

        // act
        boolean validated = networkInfoSource.getNetworkCapabilities();

        // assert
        assertTrue(validated);
    }
}