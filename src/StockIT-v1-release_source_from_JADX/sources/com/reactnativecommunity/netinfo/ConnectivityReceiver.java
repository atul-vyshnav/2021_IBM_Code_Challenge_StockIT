package com.reactnativecommunity.netinfo;

import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import androidx.core.net.ConnectivityManagerCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.reactnativecommunity.netinfo.types.CellularGeneration;
import com.reactnativecommunity.netinfo.types.ConnectionType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

abstract class ConnectivityReceiver {
    @Nullable
    private CellularGeneration mCellularGeneration = null;
    @Nonnull
    private ConnectionType mConnectionType = ConnectionType.UNKNOWN;
    private final ConnectivityManager mConnectivityManager;
    private boolean mIsInternetReachable = false;
    private Boolean mIsInternetReachableOverride;
    private final ReactApplicationContext mReactContext;
    private final TelephonyManager mTelephonyManager;
    private final WifiManager mWifiManager;

    /* access modifiers changed from: package-private */
    public abstract void register();

    /* access modifiers changed from: package-private */
    public abstract void unregister();

    ConnectivityReceiver(ReactApplicationContext reactApplicationContext) {
        this.mReactContext = reactApplicationContext;
        this.mConnectivityManager = (ConnectivityManager) reactApplicationContext.getSystemService("connectivity");
        this.mWifiManager = (WifiManager) reactApplicationContext.getApplicationContext().getSystemService("wifi");
        this.mTelephonyManager = (TelephonyManager) reactApplicationContext.getSystemService("phone");
    }

    public void getCurrentState(@Nullable String str, Promise promise) {
        promise.resolve(createConnectivityEventMap(str));
    }

    public void setIsInternetReachableOverride(boolean z) {
        this.mIsInternetReachableOverride = Boolean.valueOf(z);
        updateConnectivity(this.mConnectionType, this.mCellularGeneration, this.mIsInternetReachable);
    }

    public void clearIsInternetReachableOverride() {
        this.mIsInternetReachableOverride = null;
    }

    /* access modifiers changed from: package-private */
    public ReactApplicationContext getReactContext() {
        return this.mReactContext;
    }

    /* access modifiers changed from: package-private */
    public ConnectivityManager getConnectivityManager() {
        return this.mConnectivityManager;
    }

    /* access modifiers changed from: package-private */
    public void updateConnectivity(@Nonnull ConnectionType connectionType, @Nullable CellularGeneration cellularGeneration, boolean z) {
        Boolean bool = this.mIsInternetReachableOverride;
        if (bool != null) {
            z = bool.booleanValue();
        }
        boolean z2 = true;
        boolean z3 = connectionType != this.mConnectionType;
        boolean z4 = cellularGeneration != this.mCellularGeneration;
        if (z == this.mIsInternetReachable) {
            z2 = false;
        }
        if (z3 || z4 || z2) {
            this.mConnectionType = connectionType;
            this.mCellularGeneration = cellularGeneration;
            this.mIsInternetReachable = z;
            sendConnectivityChangedEvent();
        }
    }

    private void sendConnectivityChangedEvent() {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) getReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("netInfo.networkStatusDidChange", createConnectivityEventMap((String) null));
    }

    private WritableMap createConnectivityEventMap(@Nullable String str) {
        String str2;
        WritableMap createMap = Arguments.createMap();
        if (NetInfoUtils.isAccessWifiStatePermissionGranted(getReactContext())) {
            createMap.putBoolean("isWifiEnabled", this.mWifiManager.isWifiEnabled());
        }
        if (str != null) {
            str2 = str;
        } else {
            str2 = this.mConnectionType.label;
        }
        createMap.putString("type", str2);
        boolean z = true;
        boolean z2 = !this.mConnectionType.equals(ConnectionType.NONE) && !this.mConnectionType.equals(ConnectionType.UNKNOWN);
        createMap.putBoolean("isConnected", z2);
        if (!this.mIsInternetReachable || (str != null && !str.equals(this.mConnectionType.label))) {
            z = false;
        }
        createMap.putBoolean("isInternetReachable", z);
        if (str == null) {
            str = this.mConnectionType.label;
        }
        WritableMap createDetailsMap = createDetailsMap(str);
        if (z2) {
            createDetailsMap.putBoolean("isConnectionExpensive", ConnectivityManagerCompat.isActiveNetworkMetered(getConnectivityManager()));
        }
        createMap.putMap("details", createDetailsMap);
        return createMap;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(15:17|18|(1:22)|23|24|(1:26)|27|28|29|30|(1:32)|33|34|35|36) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0069 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0078 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x0087 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00a4 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x002d A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0064 A[Catch:{ Exception -> 0x0069 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x007e A[Catch:{ Exception -> 0x0087 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0107  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.facebook.react.bridge.WritableMap createDetailsMap(@javax.annotation.Nonnull java.lang.String r8) {
        /*
            r7 = this;
            com.facebook.react.bridge.WritableMap r0 = com.facebook.react.bridge.Arguments.createMap()
            int r1 = r8.hashCode()
            r2 = -916596374(0xffffffffc95dd96a, float:-908694.6)
            r3 = 0
            r4 = -1
            r5 = 1
            if (r1 == r2) goto L_0x0020
            r2 = 3649301(0x37af15, float:5.11376E-39)
            if (r1 == r2) goto L_0x0016
            goto L_0x002a
        L_0x0016:
            java.lang.String r1 = "wifi"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x002a
            r8 = 1
            goto L_0x002b
        L_0x0020:
            java.lang.String r1 = "cellular"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x002a
            r8 = 0
            goto L_0x002b
        L_0x002a:
            r8 = -1
        L_0x002b:
            if (r8 == 0) goto L_0x0107
            if (r8 == r5) goto L_0x0031
            goto L_0x011f
        L_0x0031:
            com.facebook.react.bridge.ReactApplicationContext r8 = r7.getReactContext()
            boolean r8 = com.reactnativecommunity.netinfo.NetInfoUtils.isAccessWifiStatePermissionGranted(r8)
            if (r8 == 0) goto L_0x011f
            android.net.wifi.WifiManager r8 = r7.mWifiManager
            android.net.wifi.WifiInfo r8 = r8.getConnectionInfo()
            if (r8 == 0) goto L_0x011f
            java.lang.String r1 = r8.getSSID()     // Catch:{ Exception -> 0x005e }
            if (r1 == 0) goto L_0x005e
            java.lang.String r2 = "<unknown ssid>"
            boolean r2 = r1.contains(r2)     // Catch:{ Exception -> 0x005e }
            if (r2 != 0) goto L_0x005e
            java.lang.String r2 = "\""
            java.lang.String r6 = ""
            java.lang.String r1 = r1.replace(r2, r6)     // Catch:{ Exception -> 0x005e }
            java.lang.String r2 = "ssid"
            r0.putString(r2, r1)     // Catch:{ Exception -> 0x005e }
        L_0x005e:
            java.lang.String r1 = r8.getBSSID()     // Catch:{ Exception -> 0x0069 }
            if (r1 == 0) goto L_0x0069
            java.lang.String r2 = "bssid"
            r0.putString(r2, r1)     // Catch:{ Exception -> 0x0069 }
        L_0x0069:
            int r1 = r8.getRssi()     // Catch:{ Exception -> 0x0078 }
            r2 = 100
            int r1 = android.net.wifi.WifiManager.calculateSignalLevel(r1, r2)     // Catch:{ Exception -> 0x0078 }
            java.lang.String r2 = "strength"
            r0.putInt(r2, r1)     // Catch:{ Exception -> 0x0078 }
        L_0x0078:
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0087 }
            r2 = 21
            if (r1 < r2) goto L_0x0087
            int r1 = r8.getFrequency()     // Catch:{ Exception -> 0x0087 }
            java.lang.String r2 = "frequency"
            r0.putInt(r2, r1)     // Catch:{ Exception -> 0x0087 }
        L_0x0087:
            int r1 = r8.getIpAddress()     // Catch:{ Exception -> 0x00a4 }
            long r1 = (long) r1     // Catch:{ Exception -> 0x00a4 }
            java.math.BigInteger r1 = java.math.BigInteger.valueOf(r1)     // Catch:{ Exception -> 0x00a4 }
            byte[] r1 = r1.toByteArray()     // Catch:{ Exception -> 0x00a4 }
            com.reactnativecommunity.netinfo.NetInfoUtils.reverseByteArray(r1)     // Catch:{ Exception -> 0x00a4 }
            java.net.InetAddress r1 = java.net.InetAddress.getByAddress(r1)     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r1 = r1.getHostAddress()     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r2 = "ipAddress"
            r0.putString(r2, r1)     // Catch:{ Exception -> 0x00a4 }
        L_0x00a4:
            int r8 = r8.getIpAddress()     // Catch:{ Exception -> 0x011f }
            long r1 = (long) r8     // Catch:{ Exception -> 0x011f }
            java.math.BigInteger r8 = java.math.BigInteger.valueOf(r1)     // Catch:{ Exception -> 0x011f }
            byte[] r8 = r8.toByteArray()     // Catch:{ Exception -> 0x011f }
            com.reactnativecommunity.netinfo.NetInfoUtils.reverseByteArray(r8)     // Catch:{ Exception -> 0x011f }
            java.net.InetAddress r8 = java.net.InetAddress.getByAddress(r8)     // Catch:{ Exception -> 0x011f }
            java.net.NetworkInterface r8 = java.net.NetworkInterface.getByInetAddress(r8)     // Catch:{ Exception -> 0x011f }
            java.util.List r8 = r8.getInterfaceAddresses()     // Catch:{ Exception -> 0x011f }
            java.lang.Object r8 = r8.get(r5)     // Catch:{ Exception -> 0x011f }
            java.net.InterfaceAddress r8 = (java.net.InterfaceAddress) r8     // Catch:{ Exception -> 0x011f }
            short r8 = r8.getNetworkPrefixLength()     // Catch:{ Exception -> 0x011f }
            int r8 = 32 - r8
            int r8 = r4 << r8
            java.util.Locale r1 = java.util.Locale.US     // Catch:{ Exception -> 0x011f }
            java.lang.String r2 = "%d.%d.%d.%d"
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x011f }
            int r6 = r8 >> 24
            r6 = r6 & 255(0xff, float:3.57E-43)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x011f }
            r4[r3] = r6     // Catch:{ Exception -> 0x011f }
            int r3 = r8 >> 16
            r3 = r3 & 255(0xff, float:3.57E-43)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x011f }
            r4[r5] = r3     // Catch:{ Exception -> 0x011f }
            r3 = 2
            int r5 = r8 >> 8
            r5 = r5 & 255(0xff, float:3.57E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x011f }
            r4[r3] = r5     // Catch:{ Exception -> 0x011f }
            r3 = 3
            r8 = r8 & 255(0xff, float:3.57E-43)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x011f }
            r4[r3] = r8     // Catch:{ Exception -> 0x011f }
            java.lang.String r8 = java.lang.String.format(r1, r2, r4)     // Catch:{ Exception -> 0x011f }
            java.lang.String r1 = "subnet"
            r0.putString(r1, r8)     // Catch:{ Exception -> 0x011f }
            goto L_0x011f
        L_0x0107:
            com.reactnativecommunity.netinfo.types.CellularGeneration r8 = r7.mCellularGeneration
            if (r8 == 0) goto L_0x0112
            java.lang.String r8 = r8.label
            java.lang.String r1 = "cellularGeneration"
            r0.putString(r1, r8)
        L_0x0112:
            android.telephony.TelephonyManager r8 = r7.mTelephonyManager
            java.lang.String r8 = r8.getNetworkOperatorName()
            if (r8 == 0) goto L_0x011f
            java.lang.String r1 = "carrier"
            r0.putString(r1, r8)
        L_0x011f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnativecommunity.netinfo.ConnectivityReceiver.createDetailsMap(java.lang.String):com.facebook.react.bridge.WritableMap");
    }
}
