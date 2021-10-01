package expo.modules.sensors.services;

import android.hardware.SensorEventListener2;

public class SensorServiceSubscription implements org.unimodules.interfaces.sensors.SensorServiceSubscription {
    private boolean mHasBeenReleased = false;
    private boolean mIsEnabled = false;
    private final SensorEventListener2 mSensorEventListener;
    private final SubscribableSensorService mSubscribableSensorService;
    private Long mUpdateInterval = null;

    SensorServiceSubscription(SubscribableSensorService subscribableSensorService, SensorEventListener2 sensorEventListener2) {
        this.mSensorEventListener = sensorEventListener2;
        this.mSubscribableSensorService = subscribableSensorService;
    }

    public void start() {
        assertSubscriptionIsAlive();
        if (!this.mIsEnabled) {
            this.mIsEnabled = true;
            this.mSubscribableSensorService.onSubscriptionEnabledChanged(this);
        }
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public Long getUpdateInterval() {
        return this.mUpdateInterval;
    }

    /* access modifiers changed from: package-private */
    public SensorEventListener2 getSensorEventListener() {
        return this.mSensorEventListener;
    }

    public void setUpdateInterval(long j) {
        assertSubscriptionIsAlive();
        this.mUpdateInterval = Long.valueOf(j);
    }

    public void stop() {
        if (this.mIsEnabled) {
            this.mIsEnabled = false;
            this.mSubscribableSensorService.onSubscriptionEnabledChanged(this);
        }
    }

    public void release() {
        if (!this.mHasBeenReleased) {
            this.mSubscribableSensorService.removeSubscription(this);
            this.mHasBeenReleased = true;
        }
    }

    private void assertSubscriptionIsAlive() {
        if (this.mHasBeenReleased) {
            throw new IllegalStateException("Subscription has been released, cannot call methods on a released subscription.");
        }
    }
}
