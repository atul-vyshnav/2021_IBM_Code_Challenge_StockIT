package com.clipsub.RNShake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.facebook.infer.annotation.Assertions;
import javax.annotation.Nullable;

public class CustomShakeDetector implements SensorEventListener {
    private static final int MAGNITUDE_THRESHOLD = 25;
    private static final int MAX_SAMPLES = 25;
    private static final int MIN_TIME_BETWEEN_SAMPLES_MS = 20;
    private static final int PERCENT_OVER_THRESHOLD_FOR_SHAKE = 66;
    private static final int VISIBLE_TIME_RANGE_MS = 500;
    private int mCurrentIndex;
    private long mLastTimestamp;
    @Nullable
    private double[] mMagnitudes;
    @Nullable
    private SensorManager mSensorManager;
    private final ShakeListener mShakeListener;
    @Nullable
    private long[] mTimestamps;

    public interface ShakeListener {
        void onShake();
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public CustomShakeDetector(ShakeListener shakeListener) {
        this.mShakeListener = shakeListener;
    }

    public void start(SensorManager sensorManager) {
        Assertions.assertNotNull(sensorManager);
        Sensor defaultSensor = sensorManager.getDefaultSensor(1);
        if (defaultSensor != null) {
            this.mSensorManager = sensorManager;
            this.mLastTimestamp = -1;
            this.mCurrentIndex = 0;
            this.mMagnitudes = new double[25];
            this.mTimestamps = new long[25];
            sensorManager.registerListener(this, defaultSensor, 2);
        }
    }

    public void stop() {
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            this.mSensorManager = null;
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.timestamp - this.mLastTimestamp >= 20) {
            Assertions.assertNotNull(this.mTimestamps);
            Assertions.assertNotNull(this.mMagnitudes);
            float f = sensorEvent.values[0];
            float f2 = sensorEvent.values[1];
            float f3 = sensorEvent.values[2];
            this.mLastTimestamp = sensorEvent.timestamp;
            this.mTimestamps[this.mCurrentIndex] = sensorEvent.timestamp;
            this.mMagnitudes[this.mCurrentIndex] = Math.sqrt(Math.pow((double) f, 2.0d) + Math.pow((double) f2, 2.0d) + Math.pow((double) f3, 2.0d));
            maybeDispatchShake(sensorEvent.timestamp);
            this.mCurrentIndex = (this.mCurrentIndex + 1) % 25;
        }
    }

    private void maybeDispatchShake(long j) {
        Assertions.assertNotNull(this.mTimestamps);
        Assertions.assertNotNull(this.mMagnitudes);
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 25; i3++) {
            int i4 = ((this.mCurrentIndex - i3) + 25) % 25;
            if (j - this.mTimestamps[i4] < 500) {
                i2++;
                if (this.mMagnitudes[i4] >= 25.0d) {
                    i++;
                }
            }
        }
        if (((double) i) / ((double) i2) > 0.66d) {
            this.mShakeListener.onShake();
        }
    }
}
