package expo.modules.sensors.services;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import org.unimodules.core.ModuleRegistry;

public abstract class BaseSensorService extends BaseService implements SensorEventListener2 {
    private Sensor mSensor;
    private SensorManager mSensorManager = ((SensorManager) getContext().getSystemService("sensor"));

    /* access modifiers changed from: package-private */
    public abstract int getSensorType();

    public /* bridge */ /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        super.onCreate(moduleRegistry);
    }

    public /* bridge */ /* synthetic */ void onDestroy() {
        super.onDestroy();
    }

    public /* bridge */ /* synthetic */ void onHostDestroy() {
        super.onHostDestroy();
    }

    public /* bridge */ /* synthetic */ void onHostPause() {
        super.onHostPause();
    }

    public /* bridge */ /* synthetic */ void onHostResume() {
        super.onHostResume();
    }

    BaseSensorService(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void startObserving() {
        Sensor defaultSensor = this.mSensorManager.getDefaultSensor(getSensorType());
        this.mSensor = defaultSensor;
        if (defaultSensor != null) {
            this.mSensorManager.registerListener(this, defaultSensor, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void stopObserving() {
        this.mSensorManager.unregisterListener(this);
    }
}
