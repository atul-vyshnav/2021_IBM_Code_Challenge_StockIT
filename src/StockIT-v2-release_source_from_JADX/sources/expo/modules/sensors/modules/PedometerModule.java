package expo.modules.sensors.modules;

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Bundle;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.interfaces.sensors.SensorService;
import org.unimodules.interfaces.sensors.services.PedometerService;

public class PedometerModule extends BaseSensorModule {
    private Integer stepsAtTheBeginning = null;

    public String getEventName() {
        return "Exponent.pedometerUpdate";
    }

    public String getName() {
        return "ExponentPedometer";
    }

    public PedometerModule(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public SensorService getSensorService() {
        return (SensorService) getModuleRegistry().getModule(PedometerService.class);
    }

    /* access modifiers changed from: protected */
    public Bundle eventToMap(SensorEvent sensorEvent) {
        if (this.stepsAtTheBeginning == null) {
            this.stepsAtTheBeginning = Integer.valueOf(((int) sensorEvent.values[0]) - 1);
        }
        Bundle bundle = new Bundle();
        bundle.putDouble("steps", (double) (sensorEvent.values[0] - ((float) this.stepsAtTheBeginning.intValue())));
        return bundle;
    }

    @ExpoMethod
    public void startObserving(Promise promise) {
        super.startObserving();
        this.stepsAtTheBeginning = null;
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void stopObserving(Promise promise) {
        super.stopObserving();
        this.stepsAtTheBeginning = null;
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void setUpdateInterval(int i, Promise promise) {
        super.setUpdateInterval(i);
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void isAvailableAsync(Promise promise) {
        promise.resolve(Boolean.valueOf(getContext().getPackageManager().hasSystemFeature("android.hardware.sensor.stepcounter")));
    }

    @ExpoMethod
    public void getStepCountAsync(Integer num, Integer num2, Promise promise) {
        promise.reject("E_NOT_AVAILABLE", "Getting step count for date range is not supported on Android yet.");
    }
}
