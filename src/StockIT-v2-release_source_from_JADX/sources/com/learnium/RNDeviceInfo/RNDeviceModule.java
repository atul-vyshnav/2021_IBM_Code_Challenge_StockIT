package com.learnium.RNDeviceInfo;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.WebSettings;
import androidx.core.p005os.EnvironmentCompat;
import com.amplitude.api.Constants;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.exoplayer2.util.MimeTypes;
import com.learnium.RNDeviceInfo.resolver.DeviceIdResolver;
import com.learnium.RNDeviceInfo.resolver.DeviceTypeResolver;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nonnull;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

@ReactModule(name = "RNDeviceInfo")
public class RNDeviceModule extends ReactContextBaseJavaModule {
    /* access modifiers changed from: private */
    public static String BATTERY_LEVEL = "batteryLevel";
    /* access modifiers changed from: private */
    public static String BATTERY_STATE = "batteryState";
    /* access modifiers changed from: private */
    public static String LOW_POWER_MODE = "lowPowerMode";
    public static final String NAME = "RNDeviceInfo";
    private final DeviceIdResolver deviceIdResolver;
    private final DeviceTypeResolver deviceTypeResolver;
    private RNInstallReferrerClient installReferrerClient;
    /* access modifiers changed from: private */
    public double mLastBatteryLevel = -1.0d;
    /* access modifiers changed from: private */
    public String mLastBatteryState = "";
    /* access modifiers changed from: private */
    public boolean mLastPowerSaveState = false;
    private BroadcastReceiver receiver;

    @Nonnull
    public String getName() {
        return NAME;
    }

    public RNDeviceModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.deviceTypeResolver = new DeviceTypeResolver(reactApplicationContext);
        this.deviceIdResolver = new DeviceIdResolver(reactApplicationContext);
        this.installReferrerClient = new RNInstallReferrerClient(reactApplicationContext.getBaseContext());
    }

    public void initialize() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        if (Build.VERSION.SDK_INT >= 21) {
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        }
        this.receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                WritableMap access$000 = RNDeviceModule.this.getPowerStateFromIntent(intent);
                if (access$000 != null) {
                    String string = access$000.getString(RNDeviceModule.BATTERY_STATE);
                    Double valueOf = Double.valueOf(access$000.getDouble(RNDeviceModule.BATTERY_LEVEL));
                    Boolean valueOf2 = Boolean.valueOf(access$000.getBoolean(RNDeviceModule.LOW_POWER_MODE));
                    if (!RNDeviceModule.this.mLastBatteryState.equalsIgnoreCase(string) || RNDeviceModule.this.mLastPowerSaveState != valueOf2.booleanValue()) {
                        RNDeviceModule rNDeviceModule = RNDeviceModule.this;
                        rNDeviceModule.sendEvent(rNDeviceModule.getReactApplicationContext(), "RNDeviceInfo_powerStateDidChange", string);
                        String unused = RNDeviceModule.this.mLastBatteryState = string;
                        boolean unused2 = RNDeviceModule.this.mLastPowerSaveState = valueOf2.booleanValue();
                    }
                    if (RNDeviceModule.this.mLastBatteryLevel != valueOf.doubleValue()) {
                        RNDeviceModule rNDeviceModule2 = RNDeviceModule.this;
                        rNDeviceModule2.sendEvent(rNDeviceModule2.getReactApplicationContext(), "RNDeviceInfo_batteryLevelDidChange", valueOf);
                        if (valueOf.doubleValue() <= 0.15d) {
                            RNDeviceModule rNDeviceModule3 = RNDeviceModule.this;
                            rNDeviceModule3.sendEvent(rNDeviceModule3.getReactApplicationContext(), "RNDeviceInfo_batteryLevelIsLow", valueOf);
                        }
                        double unused3 = RNDeviceModule.this.mLastBatteryLevel = valueOf.doubleValue();
                    }
                }
            }
        };
        getReactApplicationContext().registerReceiver(this.receiver, intentFilter);
    }

    public void onCatalystInstanceDestroy() {
        getReactApplicationContext().unregisterReceiver(this.receiver);
    }

    private WifiInfo getWifiInfo() {
        WifiManager wifiManager = (WifiManager) getReactApplicationContext().getApplicationContext().getSystemService("wifi");
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo();
        }
        return null;
    }

    public Map<String, Object> getConstants() {
        String str;
        String str2;
        String str3 = EnvironmentCompat.MEDIA_UNKNOWN;
        try {
            String str4 = getPackageInfo().versionName;
            str = Integer.toString(getPackageInfo().versionCode);
            String str5 = str4;
            str2 = getReactApplicationContext().getApplicationInfo().loadLabel(getReactApplicationContext().getPackageManager()).toString();
            str3 = str5;
        } catch (Exception unused) {
            str2 = str3;
            str = str2;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("uniqueId", getUniqueIdSync());
        hashMap.put("deviceId", Build.BOARD);
        hashMap.put("bundleId", getReactApplicationContext().getPackageName());
        hashMap.put("systemName", Constants.PLATFORM);
        hashMap.put("systemVersion", Build.VERSION.RELEASE);
        hashMap.put("appVersion", str3);
        hashMap.put("buildNumber", str);
        hashMap.put("isTablet", Boolean.valueOf(this.deviceTypeResolver.isTablet()));
        hashMap.put("appName", str2);
        hashMap.put("brand", Build.BRAND);
        hashMap.put("model", Build.MODEL);
        hashMap.put("deviceType", this.deviceTypeResolver.getDeviceType().getValue());
        return hashMap;
    }

    @ReactMethod
    public void isEmulator(Promise promise) {
        promise.resolve(Boolean.valueOf(isEmulatorSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isEmulatorSync() {
        return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN) || Build.MODEL.contains("google_sdk") || Build.MODEL.toLowerCase(Locale.ROOT).contains("droid4x") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || Build.HARDWARE.contains("goldfish") || Build.HARDWARE.contains("ranchu") || Build.HARDWARE.contains("vbox86") || Build.PRODUCT.contains("sdk") || Build.PRODUCT.contains("google_sdk") || Build.PRODUCT.contains("sdk_google") || Build.PRODUCT.contains("sdk_x86") || Build.PRODUCT.contains("vbox86p") || Build.PRODUCT.contains("emulator") || Build.PRODUCT.contains("simulator") || Build.BOARD.toLowerCase(Locale.ROOT).contains("nox") || Build.BOOTLOADER.toLowerCase(Locale.ROOT).contains("nox") || Build.HARDWARE.toLowerCase(Locale.ROOT).contains("nox") || Build.PRODUCT.toLowerCase(Locale.ROOT).contains("nox") || Build.SERIAL.toLowerCase(Locale.ROOT).contains("nox") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public float getFontScaleSync() {
        return getReactApplicationContext().getResources().getConfiguration().fontScale;
    }

    @ReactMethod
    public void getFontScale(Promise promise) {
        promise.resolve(Float.valueOf(getFontScaleSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isPinOrFingerprintSetSync() {
        KeyguardManager keyguardManager = (KeyguardManager) getReactApplicationContext().getSystemService("keyguard");
        if (keyguardManager != null) {
            return keyguardManager.isKeyguardSecure();
        }
        System.err.println("Unable to determine keyguard status. KeyguardManager was null");
        return false;
    }

    @ReactMethod
    public void isPinOrFingerprintSet(Promise promise) {
        promise.resolve(Boolean.valueOf(isPinOrFingerprintSetSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getIpAddressSync() {
        try {
            return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(getWifiInfo().getIpAddress()).array()).getHostAddress();
        } catch (Exception unused) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    @ReactMethod
    public void getIpAddress(Promise promise) {
        promise.resolve(getIpAddressSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isCameraPresentSync() {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                if (((CameraManager) getReactApplicationContext().getSystemService("camera")).getCameraIdList().length > 0) {
                    return true;
                }
                return false;
            } catch (Exception unused) {
                return false;
            }
        } else if (Camera.getNumberOfCameras() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @ReactMethod
    public void isCameraPresent(Promise promise) {
        promise.resolve(Boolean.valueOf(isCameraPresentSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getMacAddressSync() {
        String str;
        WifiInfo wifiInfo = getWifiInfo();
        if (wifiInfo != null) {
            str = wifiInfo.getMacAddress();
        } else {
            str = "";
        }
        if (getReactApplicationContext().checkCallingOrSelfPermission("android.permission.INTERNET") == 0) {
            try {
                for (T t : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                    if (t.getName().equalsIgnoreCase("wlan0")) {
                        byte[] hardwareAddress = t.getHardwareAddress();
                        if (hardwareAddress == null) {
                            str = "";
                        } else {
                            StringBuilder sb = new StringBuilder();
                            int length = hardwareAddress.length;
                            for (int i = 0; i < length; i++) {
                                sb.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                            }
                            if (sb.length() > 0) {
                                sb.deleteCharAt(sb.length() - 1);
                            }
                            str = sb.toString();
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return str;
    }

    @ReactMethod
    public void getMacAddress(Promise promise) {
        promise.resolve(getMacAddressSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getCarrierSync() {
        TelephonyManager telephonyManager = (TelephonyManager) getReactApplicationContext().getSystemService("phone");
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperatorName();
        }
        System.err.println("Unable to get network operator name. TelephonyManager was null");
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getCarrier(Promise promise) {
        promise.resolve(getCarrierSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getTotalDiskCapacitySync() {
        try {
            StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            return BigInteger.valueOf((long) statFs.getBlockCount()).multiply(BigInteger.valueOf((long) statFs.getBlockSize())).doubleValue();
        } catch (Exception unused) {
            return -1.0d;
        }
    }

    @ReactMethod
    public void getTotalDiskCapacity(Promise promise) {
        promise.resolve(Double.valueOf(getTotalDiskCapacitySync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getFreeDiskStorageSync() {
        long j;
        long j2;
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (Build.VERSION.SDK_INT < 18) {
                j2 = (long) statFs.getAvailableBlocks();
                j = (long) statFs.getBlockSize();
            } else {
                j2 = statFs.getAvailableBlocksLong();
                j = statFs.getBlockSizeLong();
            }
            return BigInteger.valueOf(j2).multiply(BigInteger.valueOf(j)).doubleValue();
        } catch (Exception unused) {
            return -1.0d;
        }
    }

    @ReactMethod
    public void getFreeDiskStorage(Promise promise) {
        promise.resolve(Double.valueOf(getFreeDiskStorageSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isBatteryChargingSync() {
        Intent registerReceiver = getReactApplicationContext().registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if ((registerReceiver != null ? registerReceiver.getIntExtra("status", -1) : 0) == 2) {
            return true;
        }
        return false;
    }

    @ReactMethod
    public void isBatteryCharging(Promise promise) {
        promise.resolve(Boolean.valueOf(isBatteryChargingSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public int getUsedMemorySync() {
        Runtime runtime = Runtime.getRuntime();
        return (int) (runtime.totalMemory() - runtime.freeMemory());
    }

    @ReactMethod
    public void getUsedMemory(Promise promise) {
        promise.resolve(Integer.valueOf(getUsedMemorySync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableMap getPowerStateSync() {
        return getPowerStateFromIntent(getReactApplicationContext().registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED")));
    }

    @ReactMethod
    public void getPowerState(Promise promise) {
        promise.resolve(getPowerStateSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getBatteryLevelSync() {
        WritableMap powerStateFromIntent = getPowerStateFromIntent(getReactApplicationContext().registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED")));
        if (powerStateFromIntent == null) {
            return 0.0d;
        }
        return powerStateFromIntent.getDouble(BATTERY_LEVEL);
    }

    @ReactMethod
    public void getBatteryLevel(Promise promise) {
        promise.resolve(Double.valueOf(getBatteryLevelSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isAirplaneModeSync() {
        if (Build.VERSION.SDK_INT < 17) {
            if (Settings.System.getInt(getReactApplicationContext().getContentResolver(), "airplane_mode_on", 0) != 0) {
                return true;
            }
        } else if (Settings.Global.getInt(getReactApplicationContext().getContentResolver(), "airplane_mode_on", 0) != 0) {
            return true;
        }
        return false;
    }

    @ReactMethod
    public void isAirplaneMode(Promise promise) {
        promise.resolve(Boolean.valueOf(isAirplaneModeSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean hasSystemFeatureSync(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        return getReactApplicationContext().getPackageManager().hasSystemFeature(str);
    }

    @ReactMethod
    public void hasSystemFeature(String str, Promise promise) {
        promise.resolve(Boolean.valueOf(hasSystemFeatureSync(str)));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableArray getSystemAvailableFeaturesSync() {
        FeatureInfo[] systemAvailableFeatures = getReactApplicationContext().getPackageManager().getSystemAvailableFeatures();
        WritableArray createArray = Arguments.createArray();
        for (FeatureInfo featureInfo : systemAvailableFeatures) {
            if (featureInfo.name != null) {
                createArray.pushString(featureInfo.name);
            }
        }
        return createArray;
    }

    @ReactMethod
    public void getSystemAvailableFeatures(Promise promise) {
        promise.resolve(getSystemAvailableFeaturesSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isLocationEnabledSync() {
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                return ((LocationManager) getReactApplicationContext().getSystemService(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID)).isLocationEnabled();
            } catch (Exception unused) {
                System.err.println("Unable to determine if location enabled. LocationManager was null");
                return false;
            }
        } else if (Build.VERSION.SDK_INT < 19) {
            return !TextUtils.isEmpty(Settings.Secure.getString(getReactApplicationContext().getContentResolver(), "location_providers_allowed"));
        } else {
            if (Settings.Secure.getInt(getReactApplicationContext().getContentResolver(), "location_mode", 0) != 0) {
                z = true;
            }
            return z;
        }
    }

    @ReactMethod
    public void isLocationEnabled(Promise promise) {
        promise.resolve(Boolean.valueOf(isLocationEnabledSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isHeadphonesConnectedSync() {
        AudioManager audioManager = (AudioManager) getReactApplicationContext().getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        return audioManager.isWiredHeadsetOn() || audioManager.isBluetoothA2dpOn();
    }

    @ReactMethod
    public void isHeadphonesConnected(Promise promise) {
        promise.resolve(Boolean.valueOf(isHeadphonesConnectedSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableMap getAvailableLocationProvidersSync() {
        LocationManager locationManager = (LocationManager) getReactApplicationContext().getSystemService(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        WritableMap createMap = Arguments.createMap();
        try {
            for (String next : locationManager.getProviders(false)) {
                createMap.putBoolean(next, locationManager.isProviderEnabled(next));
            }
        } catch (Exception unused) {
            System.err.println("Unable to get location providers. LocationManager was null");
        }
        return createMap;
    }

    @ReactMethod
    public void getAvailableLocationProviders(Promise promise) {
        promise.resolve(getAvailableLocationProvidersSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInstallReferrerSync() {
        return getReactApplicationContext().getSharedPreferences("react-native-device-info", 0).getString("installReferrer", EnvironmentCompat.MEDIA_UNKNOWN);
    }

    @ReactMethod
    public void getInstallReferrer(Promise promise) {
        promise.resolve(getInstallReferrerSync());
    }

    private PackageInfo getPackageInfo() throws Exception {
        return getReactApplicationContext().getPackageManager().getPackageInfo(getReactApplicationContext().getPackageName(), 0);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInstallerPackageNameSync() {
        String installerPackageName = getReactApplicationContext().getPackageManager().getInstallerPackageName(getReactApplicationContext().getPackageName());
        return installerPackageName == null ? EnvironmentCompat.MEDIA_UNKNOWN : installerPackageName;
    }

    @ReactMethod
    public void getInstallerPackageName(Promise promise) {
        promise.resolve(getInstallerPackageNameSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getFirstInstallTimeSync() {
        try {
            return (double) getPackageInfo().firstInstallTime;
        } catch (Exception unused) {
            return -1.0d;
        }
    }

    @ReactMethod
    public void getFirstInstallTime(Promise promise) {
        promise.resolve(Double.valueOf(getFirstInstallTimeSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getLastUpdateTimeSync() {
        try {
            return (double) getPackageInfo().lastUpdateTime;
        } catch (Exception unused) {
            return -1.0d;
        }
    }

    @ReactMethod
    public void getLastUpdateTime(Promise promise) {
        promise.resolve(Double.valueOf(getLastUpdateTimeSync()));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
        r0 = android.provider.Settings.Global.getString(getReactApplicationContext().getContentResolver(), "device_name");
     */
    @com.facebook.react.bridge.ReactMethod(isBlockingSynchronousMethod = true)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getDeviceNameSync() {
        /*
            r2 = this;
            com.facebook.react.bridge.ReactApplicationContext r0 = r2.getReactApplicationContext()     // Catch:{ Exception -> 0x0028 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ Exception -> 0x0028 }
            java.lang.String r1 = "bluetooth_name"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r1)     // Catch:{ Exception -> 0x0028 }
            if (r0 == 0) goto L_0x0011
            return r0
        L_0x0011:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0028 }
            r1 = 25
            if (r0 < r1) goto L_0x0028
            com.facebook.react.bridge.ReactApplicationContext r0 = r2.getReactApplicationContext()     // Catch:{ Exception -> 0x0028 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ Exception -> 0x0028 }
            java.lang.String r1 = "device_name"
            java.lang.String r0 = android.provider.Settings.Global.getString(r0, r1)     // Catch:{ Exception -> 0x0028 }
            if (r0 == 0) goto L_0x0028
            return r0
        L_0x0028:
            java.lang.String r0 = "unknown"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.learnium.RNDeviceInfo.RNDeviceModule.getDeviceNameSync():java.lang.String");
    }

    @ReactMethod
    public void getDeviceName(Promise promise) {
        promise.resolve(getDeviceNameSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getSerialNumberSync() {
        try {
            if (Build.VERSION.SDK_INT < 26 || getReactApplicationContext().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return EnvironmentCompat.MEDIA_UNKNOWN;
            }
            return Build.getSerial();
        } catch (Exception e) {
            PrintStream printStream = System.err;
            printStream.println("getSerialNumber failed, it probably should not be used: " + e.getMessage());
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    @ReactMethod
    public void getSerialNumber(Promise promise) {
        promise.resolve(getSerialNumberSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getDeviceSync() {
        return Build.DEVICE;
    }

    @ReactMethod
    public void getDevice(Promise promise) {
        promise.resolve(getDeviceSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getBuildIdSync() {
        return Build.ID;
    }

    @ReactMethod
    public void getBuildId(Promise promise) {
        promise.resolve(getBuildIdSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public int getApiLevelSync() {
        return Build.VERSION.SDK_INT;
    }

    @ReactMethod
    public void getApiLevel(Promise promise) {
        promise.resolve(Integer.valueOf(getApiLevelSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getBootloaderSync() {
        return Build.BOOTLOADER;
    }

    @ReactMethod
    public void getBootloader(Promise promise) {
        promise.resolve(getBootloaderSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getDisplaySync() {
        return Build.DISPLAY;
    }

    @ReactMethod
    public void getDisplay(Promise promise) {
        promise.resolve(getDisplaySync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getFingerprintSync() {
        return Build.FINGERPRINT;
    }

    @ReactMethod
    public void getFingerprint(Promise promise) {
        promise.resolve(getFingerprintSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getHardwareSync() {
        return Build.HARDWARE;
    }

    @ReactMethod
    public void getHardware(Promise promise) {
        promise.resolve(getHardwareSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getHostSync() {
        return Build.HOST;
    }

    @ReactMethod
    public void getHost(Promise promise) {
        promise.resolve(getHostSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getProductSync() {
        return Build.PRODUCT;
    }

    @ReactMethod
    public void getProduct(Promise promise) {
        promise.resolve(getProductSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getTagsSync() {
        return Build.TAGS;
    }

    @ReactMethod
    public void getTags(Promise promise) {
        promise.resolve(getTagsSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getTypeSync() {
        return Build.TYPE;
    }

    @ReactMethod
    public void getType(Promise promise) {
        promise.resolve(getTypeSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getSystemManufacturerSync() {
        return Build.MANUFACTURER;
    }

    @ReactMethod
    public void getSystemManufacturer(Promise promise) {
        promise.resolve(getSystemManufacturerSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getCodenameSync() {
        return Build.VERSION.CODENAME;
    }

    @ReactMethod
    public void getCodename(Promise promise) {
        promise.resolve(getCodenameSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getIncrementalSync() {
        return Build.VERSION.INCREMENTAL;
    }

    @ReactMethod
    public void getIncremental(Promise promise) {
        promise.resolve(getIncrementalSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getUniqueIdSync() {
        return Settings.Secure.getString(getReactApplicationContext().getContentResolver(), "android_id");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getAndroidIdSync() {
        return getUniqueIdSync();
    }

    @ReactMethod
    public void getAndroidId(Promise promise) {
        promise.resolve(getAndroidIdSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getMaxMemorySync() {
        return (double) Runtime.getRuntime().maxMemory();
    }

    @ReactMethod
    public void getMaxMemory(Promise promise) {
        promise.resolve(Double.valueOf(getMaxMemorySync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getTotalMemorySync() {
        ActivityManager activityManager = (ActivityManager) getReactApplicationContext().getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
            return (double) memoryInfo.totalMem;
        }
        System.err.println("Unable to getMemoryInfo. ActivityManager was null");
        return -1.0d;
    }

    @ReactMethod
    public void getTotalMemory(Promise promise) {
        promise.resolve(Double.valueOf(getTotalMemorySync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInstanceIdSync() {
        return this.deviceIdResolver.getInstanceIdSync();
    }

    @ReactMethod
    public void getInstanceId(Promise promise) {
        promise.resolve(getInstanceIdSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getBaseOsSync() {
        return Build.VERSION.SDK_INT >= 23 ? Build.VERSION.BASE_OS : EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getBaseOs(Promise promise) {
        promise.resolve(getBaseOsSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getPreviewSdkIntSync() {
        return Build.VERSION.SDK_INT >= 23 ? Integer.toString(Build.VERSION.PREVIEW_SDK_INT) : EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getPreviewSdkInt(Promise promise) {
        promise.resolve(getPreviewSdkIntSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getSecurityPatchSync() {
        return Build.VERSION.SDK_INT >= 23 ? Build.VERSION.SECURITY_PATCH : EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getSecurityPatch(Promise promise) {
        promise.resolve(getSecurityPatchSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getUserAgentSync() {
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                return WebSettings.getDefaultUserAgent(getReactApplicationContext());
            }
            return System.getProperty("http.agent");
        } catch (RuntimeException unused) {
            return System.getProperty("http.agent");
        }
    }

    @ReactMethod
    public void getUserAgent(Promise promise) {
        promise.resolve(getUserAgentSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getPhoneNumberSync() {
        if (getReactApplicationContext() == null) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (getReactApplicationContext().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0 && ((Build.VERSION.SDK_INT < 23 || getReactApplicationContext().checkCallingOrSelfPermission("android.permission.READ_SMS") != 0) && (Build.VERSION.SDK_INT < 26 || getReactApplicationContext().checkCallingOrSelfPermission("android.permission.READ_PHONE_NUMBERS") != 0))) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        TelephonyManager telephonyManager = (TelephonyManager) getReactApplicationContext().getSystemService("phone");
        if (telephonyManager != null) {
            return telephonyManager.getLine1Number();
        }
        System.err.println("Unable to getPhoneNumber. TelephonyManager was null");
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getPhoneNumber(Promise promise) {
        promise.resolve(getPhoneNumberSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableArray getSupportedAbisSync() {
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        if (Build.VERSION.SDK_INT >= 21) {
            for (String pushString : Build.SUPPORTED_ABIS) {
                writableNativeArray.pushString(pushString);
            }
        } else {
            writableNativeArray.pushString(Build.CPU_ABI);
        }
        return writableNativeArray;
    }

    @ReactMethod
    public void getSupportedAbis(Promise promise) {
        promise.resolve(getSupportedAbisSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableArray getSupported32BitAbisSync() {
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        if (Build.VERSION.SDK_INT >= 21) {
            for (String pushString : Build.SUPPORTED_32_BIT_ABIS) {
                writableNativeArray.pushString(pushString);
            }
        }
        return writableNativeArray;
    }

    @ReactMethod
    public void getSupported32BitAbis(Promise promise) {
        promise.resolve(getSupported32BitAbisSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableArray getSupported64BitAbisSync() {
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        if (Build.VERSION.SDK_INT >= 21) {
            for (String pushString : Build.SUPPORTED_64_BIT_ABIS) {
                writableNativeArray.pushString(pushString);
            }
        }
        return writableNativeArray;
    }

    @ReactMethod
    public void getSupported64BitAbis(Promise promise) {
        promise.resolve(getSupported64BitAbisSync());
    }

    /* access modifiers changed from: private */
    public WritableMap getPowerStateFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        int intExtra = intent.getIntExtra("level", -1);
        int intExtra2 = intent.getIntExtra("scale", -1);
        int intExtra3 = intent.getIntExtra("plugged", -1);
        int intExtra4 = intent.getIntExtra("status", -1);
        float f = ((float) intExtra) / ((float) intExtra2);
        String str = intExtra3 == 0 ? "unplugged" : intExtra4 == 2 ? "charging" : intExtra4 == 5 ? "full" : EnvironmentCompat.MEDIA_UNKNOWN;
        PowerManager powerManager = (PowerManager) getReactApplicationContext().getSystemService("power");
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 21) {
            z = powerManager.isPowerSaveMode();
        }
        WritableMap createMap = Arguments.createMap();
        createMap.putString(BATTERY_STATE, str);
        createMap.putDouble(BATTERY_LEVEL, (double) f);
        createMap.putBoolean(LOW_POWER_MODE, z);
        return createMap;
    }

    /* access modifiers changed from: private */
    public void sendEvent(ReactContext reactContext, String str, Object obj) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, obj);
    }
}
