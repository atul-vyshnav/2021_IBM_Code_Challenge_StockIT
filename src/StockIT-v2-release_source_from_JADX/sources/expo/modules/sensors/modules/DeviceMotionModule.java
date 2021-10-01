package expo.modules.sensors.modules;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.WindowManager;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.react.uimanager.ViewProps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.services.EventEmitter;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.sensors.SensorService;
import org.unimodules.interfaces.sensors.SensorServiceSubscription;
import org.unimodules.interfaces.sensors.services.AccelerometerService;
import org.unimodules.interfaces.sensors.services.GravitySensorService;
import org.unimodules.interfaces.sensors.services.GyroscopeService;
import org.unimodules.interfaces.sensors.services.LinearAccelerationSensorService;
import org.unimodules.interfaces.sensors.services.RotationVectorSensorService;

public class DeviceMotionModule extends ExportedModule implements SensorEventListener2 {
    private SensorEvent mAccelerationEvent;
    private SensorEvent mAccelerationIncludingGravityEvent;
    /* access modifiers changed from: private */
    public ScheduleDispatchFrameCallback mCurrentFrameCallback = new ScheduleDispatchFrameCallback();
    /* access modifiers changed from: private */
    public DispatchEventRunnable mDispatchEventRunnable = new DispatchEventRunnable();
    /* access modifiers changed from: private */
    public EventEmitter mEventEmitter;
    private SensorEvent mGravityEvent;
    /* access modifiers changed from: private */
    public long mLastUpdate = 0;
    private ModuleRegistry mModuleRegistry = null;
    private SensorEvent mRotationEvent;
    private float[] mRotationMatrix = new float[9];
    private SensorEvent mRotationRateEvent;
    private float[] mRotationResult = new float[3];
    /* access modifiers changed from: private */
    public List<SensorServiceSubscription> mServiceSubscriptions = null;
    /* access modifiers changed from: private */
    public UIManager mUiManager = null;
    /* access modifiers changed from: private */
    public float mUpdateInterval = 0.016666668f;

    public String getName() {
        return "ExponentDeviceMotion";
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onFlushCompleted(Sensor sensor) {
    }

    public DeviceMotionModule(Context context) {
        super(context);
    }

    public Map<String, Object> getConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("Gravity", Double.valueOf(9.80665d));
            }
        });
    }

    @ExpoMethod
    public void setUpdateInterval(int i, Promise promise) {
        this.mUpdateInterval = (float) i;
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void startObserving(Promise promise) {
        if (this.mServiceSubscriptions == null) {
            this.mServiceSubscriptions = new ArrayList();
            for (SensorService createSubscriptionForListener : getSensorKernelServices()) {
                SensorServiceSubscription createSubscriptionForListener2 = createSubscriptionForListener.createSubscriptionForListener(this);
                createSubscriptionForListener2.setUpdateInterval(0);
                this.mServiceSubscriptions.add(createSubscriptionForListener2);
            }
        }
        for (SensorServiceSubscription start : this.mServiceSubscriptions) {
            start.start();
        }
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void stopObserving(final Promise promise) {
        this.mUiManager.runOnUiQueueThread(new Runnable() {
            public void run() {
                for (SensorServiceSubscription stop : DeviceMotionModule.this.mServiceSubscriptions) {
                    stop.stop();
                }
                DeviceMotionModule.this.mCurrentFrameCallback.stop();
                promise.resolve((Object) null);
            }
        });
    }

    @ExpoMethod
    public void isAvailableAsync(Promise promise) {
        SensorManager sensorManager = (SensorManager) getContext().getSystemService("sensor");
        Iterator it = new ArrayList(Arrays.asList(new Integer[]{4, 1, 10, 11, 9})).iterator();
        while (it.hasNext()) {
            if (sensorManager.getDefaultSensor(((Integer) it.next()).intValue()) == null) {
                promise.resolve(false);
                return;
            }
        }
        promise.resolve(true);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mEventEmitter = (EventEmitter) moduleRegistry.getModule(EventEmitter.class);
        this.mUiManager = (UIManager) moduleRegistry.getModule(UIManager.class);
        this.mModuleRegistry = moduleRegistry;
    }

    private List<SensorService> getSensorKernelServices() {
        return Arrays.asList(new SensorService[]{(SensorService) this.mModuleRegistry.getModule(GyroscopeService.class), (SensorService) this.mModuleRegistry.getModule(LinearAccelerationSensorService.class), (SensorService) this.mModuleRegistry.getModule(AccelerometerService.class), (SensorService) this.mModuleRegistry.getModule(RotationVectorSensorService.class), (SensorService) this.mModuleRegistry.getModule(GravitySensorService.class)});
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == 4) {
            this.mRotationRateEvent = sensorEvent;
        } else if (sensor.getType() == 1) {
            this.mAccelerationIncludingGravityEvent = sensorEvent;
        } else if (sensor.getType() == 10) {
            this.mAccelerationEvent = sensorEvent;
        } else if (sensor.getType() == 11) {
            this.mRotationEvent = sensorEvent;
        } else if (sensor.getType() == 9) {
            this.mGravityEvent = sensorEvent;
        }
        this.mCurrentFrameCallback.maybePostFromNonUI();
    }

    private class ScheduleDispatchFrameCallback implements Choreographer.FrameCallback {
        private volatile boolean mIsPosted;
        private boolean mShouldStop;

        private ScheduleDispatchFrameCallback() {
            this.mIsPosted = false;
            this.mShouldStop = false;
        }

        public void doFrame(long j) {
            if (this.mShouldStop) {
                this.mIsPosted = false;
            } else {
                post();
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (((float) (currentTimeMillis - DeviceMotionModule.this.mLastUpdate)) > DeviceMotionModule.this.mUpdateInterval) {
                DeviceMotionModule.this.mUiManager.runOnClientCodeQueueThread(DeviceMotionModule.this.mDispatchEventRunnable);
                long unused = DeviceMotionModule.this.mLastUpdate = currentTimeMillis;
            }
        }

        public void stop() {
            this.mShouldStop = true;
        }

        public void maybePost() {
            if (!this.mIsPosted) {
                this.mIsPosted = true;
                post();
            }
        }

        private void post() {
            Choreographer.getInstance().postFrameCallback(DeviceMotionModule.this.mCurrentFrameCallback);
        }

        public void maybePostFromNonUI() {
            if (!this.mIsPosted) {
                DeviceMotionModule.this.mUiManager.runOnUiQueueThread(new Runnable() {
                    public void run() {
                        ScheduleDispatchFrameCallback.this.maybePost();
                    }
                });
            }
        }
    }

    private class DispatchEventRunnable implements Runnable {
        private DispatchEventRunnable() {
        }

        public void run() {
            DeviceMotionModule.this.mEventEmitter.emit("deviceMotionDidUpdate", DeviceMotionModule.this.eventsToMap());
        }
    }

    /* access modifiers changed from: private */
    public Bundle eventsToMap() {
        double d;
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        Bundle bundle4 = new Bundle();
        Bundle bundle5 = new Bundle();
        SensorEvent sensorEvent = this.mAccelerationEvent;
        if (sensorEvent != null) {
            bundle2.putDouble("x", (double) sensorEvent.values[0]);
            bundle2.putDouble("y", (double) this.mAccelerationEvent.values[1]);
            bundle2.putDouble("z", (double) this.mAccelerationEvent.values[2]);
            bundle.putBundle("acceleration", bundle2);
            d = (double) this.mAccelerationEvent.timestamp;
        } else {
            d = 0.0d;
        }
        SensorEvent sensorEvent2 = this.mAccelerationIncludingGravityEvent;
        if (!(sensorEvent2 == null || this.mGravityEvent == null)) {
            bundle3.putDouble("x", (double) (sensorEvent2.values[0] - (this.mGravityEvent.values[0] * 2.0f)));
            bundle3.putDouble("y", (double) (this.mAccelerationIncludingGravityEvent.values[1] - (this.mGravityEvent.values[1] * 2.0f)));
            bundle3.putDouble("z", (double) (this.mAccelerationIncludingGravityEvent.values[2] - (this.mGravityEvent.values[2] * 2.0f)));
            bundle.putBundle("accelerationIncludingGravity", bundle3);
            d = (double) this.mAccelerationIncludingGravityEvent.timestamp;
        }
        SensorEvent sensorEvent3 = this.mRotationRateEvent;
        if (sensorEvent3 != null) {
            bundle5.putDouble("alpha", Math.toDegrees((double) sensorEvent3.values[0]));
            bundle5.putDouble("beta", Math.toDegrees((double) this.mRotationRateEvent.values[1]));
            bundle5.putDouble("gamma", Math.toDegrees((double) this.mRotationRateEvent.values[2]));
            bundle.putBundle("rotationRate", bundle5);
            d = (double) this.mRotationRateEvent.timestamp;
        }
        SensorEvent sensorEvent4 = this.mRotationEvent;
        if (sensorEvent4 != null) {
            SensorManager.getRotationMatrixFromVector(this.mRotationMatrix, sensorEvent4.values);
            SensorManager.getOrientation(this.mRotationMatrix, this.mRotationResult);
            bundle4.putDouble("alpha", (double) (-this.mRotationResult[0]));
            bundle4.putDouble("beta", (double) (-this.mRotationResult[1]));
            bundle4.putDouble("gamma", (double) this.mRotationResult[2]);
            bundle.putBundle(ViewProps.ROTATION, bundle4);
            d = (double) this.mRotationEvent.timestamp;
        }
        bundle.putDouble("interval", d);
        bundle.putInt("orientation", getOrientation());
        return bundle;
    }

    private int getOrientation() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        if (windowManager == null) {
            return 0;
        }
        int rotation = windowManager.getDefaultDisplay().getRotation();
        if (rotation == 1) {
            return 90;
        }
        if (rotation != 2) {
            return rotation != 3 ? 0 : -90;
        }
        return RotationOptions.ROTATE_180;
    }
}
