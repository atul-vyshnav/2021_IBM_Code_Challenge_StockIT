package expo.modules.sensors.modules;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.interfaces.sensors.SensorService;
import org.unimodules.interfaces.sensors.services.BarometerService;

public class BarometerModule extends BaseSensorModule {
    public String getEventName() {
        return "barometerDidUpdate";
    }

    public String getName() {
        return "ExpoBarometer";
    }

    public BarometerModule(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public SensorService getSensorService() {
        return (SensorService) getModuleRegistry().getModule(BarometerService.class);
    }

    /* access modifiers changed from: protected */
    public Bundle eventToMap(SensorEvent sensorEvent) {
        Bundle bundle = new Bundle();
        bundle.putDouble("pressure", (double) sensorEvent.values[0]);
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
        promise.resolve(Boolean.valueOf(((SensorManager) getContext().getSystemService("sensor")).getDefaultSensor(6) != null));
    }
}
