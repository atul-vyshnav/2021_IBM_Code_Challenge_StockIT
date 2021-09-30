package expo.modules.sensors.modules;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.os.Bundle;
import android.util.Log;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.core.interfaces.services.EventEmitter;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.sensors.SensorService;
import org.unimodules.interfaces.sensors.SensorServiceSubscription;

public abstract class BaseSensorModule extends ExportedModule implements SensorEventListener2, LifecycleEventListener {
    private boolean mIsObserving = false;
    private ModuleRegistry mModuleRegistry;
    private SensorServiceSubscription mSensorServiceSubscription;

    /* access modifiers changed from: protected */
    public abstract Bundle eventToMap(SensorEvent sensorEvent);

    /* access modifiers changed from: protected */
    public abstract String getEventName();

    /* access modifiers changed from: protected */
    public abstract SensorService getSensorService();

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onFlushCompleted(Sensor sensor) {
    }

    BaseSensorModule(Context context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public ModuleRegistry getModuleRegistry() {
        return this.mModuleRegistry;
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        ModuleRegistry moduleRegistry2 = this.mModuleRegistry;
        if (!(moduleRegistry2 == null || moduleRegistry2.getModule(UIManager.class) == null)) {
            ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).unregisterLifecycleEventListener(this);
        }
        this.mModuleRegistry = moduleRegistry;
        if (moduleRegistry != null && moduleRegistry.getModule(UIManager.class) != null) {
            ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).registerLifecycleEventListener(this);
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        EventEmitter eventEmitter = (EventEmitter) this.mModuleRegistry.getModule(EventEmitter.class);
        if (eventEmitter != null) {
            eventEmitter.emit(getEventName(), eventToMap(sensorEvent));
            return;
        }
        Log.e("E_SENSOR_MODULE", "Could not emit " + getEventName() + " event, no event emitter present.");
    }

    public void setUpdateInterval(int i) {
        getSensorKernelServiceSubscription().setUpdateInterval((long) i);
    }

    private SensorServiceSubscription getSensorKernelServiceSubscription() {
        SensorServiceSubscription sensorServiceSubscription = this.mSensorServiceSubscription;
        if (sensorServiceSubscription != null) {
            return sensorServiceSubscription;
        }
        SensorServiceSubscription createSubscriptionForListener = getSensorService().createSubscriptionForListener(this);
        this.mSensorServiceSubscription = createSubscriptionForListener;
        return createSubscriptionForListener;
    }

    public void startObserving() {
        this.mIsObserving = true;
        getSensorKernelServiceSubscription().start();
    }

    public void stopObserving() {
        if (this.mIsObserving) {
            this.mIsObserving = false;
            getSensorKernelServiceSubscription().stop();
        }
    }

    public void onHostResume() {
        if (this.mIsObserving) {
            getSensorKernelServiceSubscription().start();
        }
    }

    public void onHostPause() {
        if (this.mIsObserving) {
            getSensorKernelServiceSubscription().stop();
        }
    }

    public void onHostDestroy() {
        getSensorKernelServiceSubscription().release();
    }
}
