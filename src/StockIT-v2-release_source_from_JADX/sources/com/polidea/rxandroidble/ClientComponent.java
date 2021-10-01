package com.polidea.rxandroidble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import bleshadow.dagger.Binds;
import bleshadow.dagger.Component;
import bleshadow.dagger.Module;
import bleshadow.dagger.Provides;
import bleshadow.javax.inject.Named;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.RxBleAdapterStateObservable;
import com.polidea.rxandroidble.helpers.LocationServicesOkObservable;
import com.polidea.rxandroidble.internal.DeviceComponent;
import com.polidea.rxandroidble.internal.scan.InternalToExternalScanResultConverter;
import com.polidea.rxandroidble.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble.internal.scan.ScanPreconditionsVerifierApi18;
import com.polidea.rxandroidble.internal.scan.ScanPreconditionsVerifierApi24;
import com.polidea.rxandroidble.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble.internal.scan.ScanSetupBuilderImplApi18;
import com.polidea.rxandroidble.internal.scan.ScanSetupBuilderImplApi21;
import com.polidea.rxandroidble.internal.scan.ScanSetupBuilderImplApi23;
import com.polidea.rxandroidble.internal.serialization.ClientOperationQueue;
import com.polidea.rxandroidble.internal.serialization.ClientOperationQueueImpl;
import com.polidea.rxandroidble.internal.util.LocationServicesOkObservableApi23;
import com.polidea.rxandroidble.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble.internal.util.LocationServicesStatusApi18;
import com.polidea.rxandroidble.internal.util.LocationServicesStatusApi23;
import com.polidea.rxandroidble.internal.util.ObservableUtil;
import com.polidea.rxandroidble.scan.ScanResult;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Func1;
import p030rx.schedulers.Schedulers;

@Component(modules = {ClientModule.class, ClientModuleBinder.class})
@ClientScope
public interface ClientComponent {

    public interface ClientComponentFinalizer {
        void onFinalize();
    }

    @Module
    public static abstract class ClientModuleBinder {
        /* access modifiers changed from: package-private */
        @Binds
        @ClientScope
        public abstract ClientOperationQueue bindClientOperationQueue(ClientOperationQueueImpl clientOperationQueueImpl);

        /* access modifiers changed from: package-private */
        @Binds
        @ClientScope
        public abstract RxBleClient bindRxBleClient(RxBleClientImpl rxBleClientImpl);

        /* access modifiers changed from: package-private */
        @Binds
        public abstract Observable<RxBleAdapterStateObservable.BleAdapterState> bindStateObs(RxBleAdapterStateObservable rxBleAdapterStateObservable);

        /* access modifiers changed from: package-private */
        @Binds
        @Named("timeout")
        public abstract Scheduler bindTimeoutScheduler(@Named("computation") Scheduler scheduler);

        /* access modifiers changed from: package-private */
        @Binds
        public abstract Func1<RxBleInternalScanResult, ScanResult> provideScanResultMapper(InternalToExternalScanResultConverter internalToExternalScanResultConverter);
    }

    LocationServicesOkObservable locationServicesOkObservable();

    RxBleClient rxBleClient();

    public static class NamedExecutors {
        public static final String BLUETOOTH_CALLBACKS = "executor_bluetooth_callbacks";
        public static final String BLUETOOTH_INTERACTION = "executor_bluetooth_interaction";
        public static final String CONNECTION_QUEUE = "executor_connection_queue";

        private NamedExecutors() {
        }
    }

    public static class NamedSchedulers {
        public static final String BLUETOOTH_CALLBACKS = "bluetooth_callbacks";
        public static final String BLUETOOTH_INTERACTION = "bluetooth_interaction";
        public static final String COMPUTATION = "computation";
        public static final String TIMEOUT = "timeout";

        private NamedSchedulers() {
        }
    }

    public static class PlatformConstants {
        public static final String BOOL_IS_ANDROID_WEAR = "android-wear";
        public static final String INT_DEVICE_SDK = "device-sdk";
        public static final String INT_TARGET_SDK = "target-sdk";

        private PlatformConstants() {
        }
    }

    public static class NamedBooleanObservables {
        public static final String LOCATION_SERVICES_OK = "location-ok-boolean-observable";

        private NamedBooleanObservables() {
        }
    }

    public static class BluetoothConstants {
        public static final String DISABLE_NOTIFICATION_VALUE = "disable-notification-value";
        public static final String ENABLE_INDICATION_VALUE = "enable-indication-value";
        public static final String ENABLE_NOTIFICATION_VALUE = "enable-notification-value";

        private BluetoothConstants() {
        }
    }

    @Module(subcomponents = {DeviceComponent.class})
    public static class ClientModule {
        private final Context context;

        public ClientModule(Context context2) {
            this.context = context2;
        }

        /* access modifiers changed from: package-private */
        @Provides
        public Context provideApplicationContext() {
            return this.context;
        }

        /* access modifiers changed from: package-private */
        @Provides
        public BluetoothManager provideBluetoothManager() {
            return (BluetoothManager) this.context.getSystemService("bluetooth");
        }

        @Provides
        static BluetoothAdapter provideBluetoothAdapter() {
            return BluetoothAdapter.getDefaultAdapter();
        }

        @Provides
        @Named("computation")
        static Scheduler provideComputationScheduler() {
            return Schedulers.computation();
        }

        @Provides
        @Named("device-sdk")
        static int provideDeviceSdk() {
            return Build.VERSION.SDK_INT;
        }

        /* access modifiers changed from: package-private */
        @Provides
        public ContentResolver provideContentResolver() {
            return this.context.getContentResolver();
        }

        /* access modifiers changed from: package-private */
        @Provides
        public LocationServicesStatus provideLocationServicesStatus(@Named("device-sdk") int i, Provider<LocationServicesStatusApi18> provider, Provider<LocationServicesStatusApi23> provider2) {
            if (i < 23) {
                return provider.get();
            }
            return provider2.get();
        }

        /* access modifiers changed from: package-private */
        @Provides
        @Named("location-ok-boolean-observable")
        public Observable<Boolean> provideLocationServicesOkObservable(@Named("device-sdk") int i, Provider<LocationServicesOkObservableApi23> provider) {
            if (i < 23) {
                return ObservableUtil.justOnNext(true);
            }
            return provider.get();
        }

        @ClientScope
        @Provides
        @Named("executor_connection_queue")
        static ExecutorService provideConnectionQueueExecutorService() {
            return Executors.newCachedThreadPool();
        }

        @ClientScope
        @Provides
        @Named("executor_bluetooth_interaction")
        static ExecutorService provideBluetoothInteractionExecutorService() {
            return Executors.newSingleThreadExecutor();
        }

        @ClientScope
        @Provides
        @Named("executor_bluetooth_callbacks")
        static ExecutorService provideBluetoothCallbacksExecutorService() {
            return Executors.newSingleThreadExecutor();
        }

        @ClientScope
        @Provides
        @Named("bluetooth_interaction")
        static Scheduler provideBluetoothInteractionScheduler(@Named("executor_bluetooth_interaction") ExecutorService executorService) {
            return Schedulers.from(executorService);
        }

        @ClientScope
        @Provides
        @Named("bluetooth_callbacks")
        static Scheduler provideBluetoothCallbacksScheduler(@Named("executor_bluetooth_callbacks") ExecutorService executorService) {
            return Schedulers.from(executorService);
        }

        @Provides
        static ClientComponentFinalizer provideFinalizationCloseable(@Named("executor_bluetooth_interaction") final ExecutorService executorService, @Named("executor_bluetooth_callbacks") final ExecutorService executorService2, @Named("executor_connection_queue") final ExecutorService executorService3) {
            return new ClientComponentFinalizer() {
                public void onFinalize() {
                    executorService.shutdown();
                    executorService2.shutdown();
                    executorService3.shutdown();
                }
            };
        }

        /* access modifiers changed from: package-private */
        @Provides
        public LocationManager provideLocationManager() {
            return (LocationManager) this.context.getSystemService(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        }

        /* access modifiers changed from: package-private */
        @Provides
        @Named("target-sdk")
        public int provideTargetSdk() {
            try {
                return this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 0).targetSdkVersion;
            } catch (Throwable unused) {
                return Integer.MAX_VALUE;
            }
        }

        /* access modifiers changed from: package-private */
        @Provides
        @Named("android-wear")
        public boolean provideIsAndroidWear(@Named("device-sdk") int i) {
            return i >= 20 && this.context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        }

        @ClientScope
        @Provides
        static ScanSetupBuilder provideScanSetupProvider(@Named("device-sdk") int i, Provider<ScanSetupBuilderImplApi18> provider, Provider<ScanSetupBuilderImplApi21> provider2, Provider<ScanSetupBuilderImplApi23> provider3) {
            if (i < 21) {
                return provider.get();
            }
            if (i < 23) {
                return provider2.get();
            }
            return provider3.get();
        }

        @Provides
        @Named("enable-notification-value")
        static byte[] provideEnableNotificationValue() {
            return BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        }

        @Provides
        @Named("enable-indication-value")
        static byte[] provideEnableIndicationValue() {
            return BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
        }

        @Provides
        @Named("disable-notification-value")
        static byte[] provideDisableNotificationValue() {
            return BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
        }

        @Provides
        static ScanPreconditionsVerifier provideScanPreconditionVerifier(@Named("device-sdk") int i, Provider<ScanPreconditionsVerifierApi18> provider, Provider<ScanPreconditionsVerifierApi24> provider2) {
            if (i < 24) {
                return provider.get();
            }
            return provider2.get();
        }
    }
}
