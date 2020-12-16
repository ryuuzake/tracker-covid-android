package com.trackercovid.util;

import android.os.Build;

public class BuildVersionProviderImpl implements BuildVersionProvider {
    @Override
    public boolean isMarshmallowAndAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
