package com.trackercovid.interactor;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.trackercovid.util.BuildVersionProvider;

public class NetworkInfoSourceImpl implements NetworkInfoSource {

    private static final String TAG = NetworkInfoSourceImpl.class.getSimpleName();
    private final BuildVersionProvider buildVersionProvider;
    private ConnectivityManager connectivityManager;

    public NetworkInfoSourceImpl(ConnectivityManager connectivityManager, BuildVersionProvider buildVersionProvider) {
        this.connectivityManager = connectivityManager;
        this.buildVersionProvider = buildVersionProvider;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean getNetworkCapabilities() {
        if (buildVersionProvider.isMarshmallowAndAbove()) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            boolean validated =
                    networkCapabilities == null
                            || !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            return !validated;
        }
        return true;
    }
}
