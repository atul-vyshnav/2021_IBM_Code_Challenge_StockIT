package com.polidea.rxandroidble.internal.util;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import bleshadow.javax.inject.Inject;

public class CheckerLocationPermission {
    private final Context context;

    @Inject
    public CheckerLocationPermission(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: package-private */
    public boolean isLocationPermissionGranted() {
        if (Math.min(Build.VERSION.SDK_INT, provideTargetSdk()) < 29) {
            return isPermissionGranted("android.permission.ACCESS_COARSE_LOCATION") || isPermissionGranted("android.permission.ACCESS_FINE_LOCATION");
        }
        return isPermissionGranted("android.permission.ACCESS_FINE_LOCATION");
    }

    private boolean isPermissionGranted(String str) {
        if (str != null) {
            return this.context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
        }
        throw new IllegalArgumentException("permission is null");
    }

    private int provideTargetSdk() {
        try {
            return this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 0).targetSdkVersion;
        } catch (Throwable unused) {
            return Integer.MAX_VALUE;
        }
    }
}
