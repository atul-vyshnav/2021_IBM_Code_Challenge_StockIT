package expo.modules.sensors.services;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import java.util.Map;
import java.util.WeakHashMap;
import org.unimodules.interfaces.sensors.SensorService;
import org.unimodules.interfaces.sensors.SensorServiceSubscription;

public abstract class SubscribableSensorService extends BaseSensorService implements SensorService {
    protected static int DEFAULT_UPDATE_INTERVAL = 100;
    private int mListenersCount = 0;
    private Map<SensorServiceSubscription, Long> mSensorEventListenerLastUpdateMap = new WeakHashMap();

    SubscribableSensorService(Context context) {
        super(context);
    }

    public void onExperienceForegrounded() {
        updateObserving();
    }

    public void onExperienceBackgrounded() {
        updateObserving();
    }

    public SensorServiceSubscription createSubscriptionForListener(SensorEventListener2 sensorEventListener2) {
        SensorServiceSubscription sensorServiceSubscription = new SensorServiceSubscription(this, sensorEventListener2);
        this.mSensorEventListenerLastUpdateMap.put(sensorServiceSubscription, 0L);
        return sensorServiceSubscription;
    }

    /* access modifiers changed from: package-private */
    public void onSubscriptionEnabledChanged(SensorServiceSubscription sensorServiceSubscription) {
        if (sensorServiceSubscription.isEnabled()) {
            this.mListenersCount++;
        } else {
            this.mListenersCount--;
        }
        updateObserving();
    }

    /* access modifiers changed from: package-private */
    public void removeSubscription(SensorServiceSubscription sensorServiceSubscription) {
        this.mSensorEventListenerLastUpdateMap.remove(sensorServiceSubscription);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == getSensorType()) {
            long currentTimeMillis = System.currentTimeMillis();
            for (SensorServiceSubscription next : this.mSensorEventListenerLastUpdateMap.keySet()) {
                if (next != null && next.isEnabled()) {
                    long j = 0;
                    if (this.mSensorEventListenerLastUpdateMap.containsKey(next)) {
                        j = this.mSensorEventListenerLastUpdateMap.get(next).longValue();
                    }
                    long j2 = (long) DEFAULT_UPDATE_INTERVAL;
                    if (next.getUpdateInterval() != null) {
                        j2 = next.getUpdateInterval().longValue();
                    }
                    if (currentTimeMillis - j > j2) {
                        next.getSensorEventListener().onSensorChanged(sensorEvent);
                        this.mSensorEventListenerLastUpdateMap.put(next, Long.valueOf(currentTimeMillis));
                    }
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
        if (sensor.getType() == getSensorType()) {
            for (SensorServiceSubscription next : this.mSensorEventListenerLastUpdateMap.keySet()) {
                if (next.isEnabled()) {
                    next.getSensorEventListener().onAccuracyChanged(sensor, i);
                }
            }
        }
    }

    public void onFlushCompleted(Sensor sensor) {
        if (sensor.getType() == getSensorType()) {
            for (SensorServiceSubscription next : this.mSensorEventListenerLastUpdateMap.keySet()) {
                if (next.isEnabled()) {
                    next.getSensorEventListener().onFlushCompleted(sensor);
                }
            }
        }
    }

    private void updateObserving() {
        if (this.mListenersCount <= 0 || !getExperienceIsForegrounded()) {
            super.stopObserving();
        } else {
            super.startObserving();
        }
    }
}
