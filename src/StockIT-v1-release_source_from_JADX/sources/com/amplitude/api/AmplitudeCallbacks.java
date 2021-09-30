package com.amplitude.api;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

class AmplitudeCallbacks implements Application.ActivityLifecycleCallbacks {
    private static final String NULLMSG = "Need to initialize AmplitudeCallbacks with AmplitudeClient instance";
    public static final String TAG = "com.amplitude.api.AmplitudeCallbacks";
    private static AmplitudeLog logger = AmplitudeLog.getLogger();
    private AmplitudeClient clientInstance = null;

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public AmplitudeCallbacks(AmplitudeClient amplitudeClient) {
        if (amplitudeClient == null) {
            logger.mo10606e(TAG, NULLMSG);
            return;
        }
        this.clientInstance = amplitudeClient;
        amplitudeClient.useForegroundTracking();
    }

    public void onActivityPaused(Activity activity) {
        AmplitudeClient amplitudeClient = this.clientInstance;
        if (amplitudeClient == null) {
            logger.mo10606e(TAG, NULLMSG);
        } else {
            amplitudeClient.onExitForeground(getCurrentTimeMillis());
        }
    }

    public void onActivityResumed(Activity activity) {
        AmplitudeClient amplitudeClient = this.clientInstance;
        if (amplitudeClient == null) {
            logger.mo10606e(TAG, NULLMSG);
        } else {
            amplitudeClient.onEnterForeground(getCurrentTimeMillis());
        }
    }

    /* access modifiers changed from: protected */
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
