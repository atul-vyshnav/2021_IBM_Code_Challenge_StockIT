package expo.modules.sensors.modules;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.interfaces.sensors.SensorService;
import org.unimodules.interfaces.sensors.services.AccelerometerService;

public class AccelerometerModule extends BaseSensorModule {
    public String getEventName() {
        return "accelerometerDidUpdate";
    }

    public String getName() {
        return "ExponentAccelerometer";
    }

    public AccelerometerModule(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public SensorService getSensorService() {
        return (SensorService) getModuleRegistry().getModule(AccelerometerService.class);
    }

    /* access modifiers changed from: protected */
    public Bundle eventToMap(SensorEvent sensorEvent) {
        Bundle bundle = new Bundle();
        bundle.putDouble("x", (double) (sensorEvent.values[0] / 9.80665f));
        bundle.putDouble("y", (double) (sensorEvent.values[1] / 9.80665f));
        bundle.putDouble("z", (double) (sensorEvent.values[2] / 9.80665f));
        return bundle;
    }

    @ExpoMethod
    public void startObserving(Promise promise) {
        super.startObserving();
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void stopObserving(Promise promise) {
        super.stopObserving();
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void setUpdateInterval(int i, Promise promise) {
        super.setUpdateInterval(i);
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void isAvailableAsync(Promise promise) {
        boolean z = true;
        if (((SensorManager) getContext().getSystemService("sensor")).getDefaultSensor(1) == null) {
            z = false;
        }
        promise.resolve(Boolean.valueOf(z));
    }
}
