package com.facebook.react.modules.systeminfo;

import android.content.Context;
import android.os.Build;
import com.facebook.react.C1045R;
import java.util.Locale;

public class AndroidInfoHelpers {
    public static final String DEVICE_LOCALHOST = "localhost";
    public static final String EMULATOR_LOCALHOST = "10.0.2.2";
    public static final String GENYMOTION_LOCALHOST = "10.0.3.2";
    public static final String METRO_HOST_PROP_NAME = "metro.host";
    private static final String TAG = AndroidInfoHelpers.class.getSimpleName();
    private static String metroHostPropValue = null;

    private static boolean isRunningOnGenymotion() {
        return Build.FINGERPRINT.contains("vbox");
    }

    private static boolean isRunningOnStockEmulator() {
        return Build.FINGERPRINT.contains("generic");
    }

    public static String getServerHost(Integer num) {
        return getServerIpAddress(num.intValue());
    }

    public static String getServerHost(Context context) {
        return getServerIpAddress(getDevServerPort(context).intValue());
    }

    public static String getAdbReverseTcpCommand(Integer num) {
        return "adb reverse tcp:" + num + " tcp:" + num;
    }

    public static String getAdbReverseTcpCommand(Context context) {
        return getAdbReverseTcpCommand(getDevServerPort(context));
    }

    public static String getInspectorProxyHost(Context context) {
        return getServerIpAddress(getInspectorProxyPort(context).intValue());
    }

    public static String getFriendlyDeviceName() {
        if (isRunningOnGenymotion()) {
            return Build.MODEL;
        }
        return Build.MODEL + " - " + Build.VERSION.RELEASE + " - API " + Build.VERSION.SDK_INT;
    }

    private static Integer getDevServerPort(Context context) {
        return Integer.valueOf(context.getResources().getInteger(C1045R.integer.react_native_dev_server_port));
    }

    private static Integer getInspectorProxyPort(Context context) {
        return Integer.valueOf(context.getResources().getInteger(C1045R.integer.react_native_dev_server_port));
    }

    private static String getServerIpAddress(int i) {
        String metroHostPropValue2 = getMetroHostPropValue();
        if (metroHostPropValue2.equals("")) {
            if (isRunningOnGenymotion()) {
                metroHostPropValue2 = GENYMOTION_LOCALHOST;
            } else {
                metroHostPropValue2 = isRunningOnStockEmulator() ? EMULATOR_LOCALHOST : DEVICE_LOCALHOST;
            }
        }
        return String.format(Locale.US, "%s:%d", new Object[]{metroHostPropValue2, Integer.valueOf(i)});
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0041, code lost:
        if (r2 == null) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r2.destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006e, code lost:
        if (r2 == null) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r1 = metroHostPropValue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0074, code lost:
        return r1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0069 A[SYNTHETIC, Splitter:B:37:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0078 A[SYNTHETIC, Splitter:B:47:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x007f A[SYNTHETIC, Splitter:B:51:0x007f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized java.lang.String getMetroHostPropValue() {
        /*
            java.lang.Class<com.facebook.react.modules.systeminfo.AndroidInfoHelpers> r0 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.class
            monitor-enter(r0)
            java.lang.String r1 = metroHostPropValue     // Catch:{ all -> 0x0083 }
            if (r1 == 0) goto L_0x000b
            java.lang.String r1 = metroHostPropValue     // Catch:{ all -> 0x0083 }
            monitor-exit(r0)
            return r1
        L_0x000b:
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0058, all -> 0x0053 }
            java.lang.String r3 = "/system/bin/getprop"
            java.lang.String r4 = "metro.host"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4}     // Catch:{ Exception -> 0x0058, all -> 0x0053 }
            java.lang.Process r2 = r2.exec(r3)     // Catch:{ Exception -> 0x0058, all -> 0x0053 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x004e, all -> 0x0049 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x004e, all -> 0x0049 }
            java.io.InputStream r5 = r2.getInputStream()     // Catch:{ Exception -> 0x004e, all -> 0x0049 }
            java.lang.String r6 = "UTF-8"
            java.nio.charset.Charset r6 = java.nio.charset.Charset.forName(r6)     // Catch:{ Exception -> 0x004e, all -> 0x0049 }
            r4.<init>(r5, r6)     // Catch:{ Exception -> 0x004e, all -> 0x0049 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x004e, all -> 0x0049 }
            java.lang.String r1 = ""
        L_0x0032:
            java.lang.String r4 = r3.readLine()     // Catch:{ Exception -> 0x0047 }
            if (r4 == 0) goto L_0x003a
            r1 = r4
            goto L_0x0032
        L_0x003a:
            metroHostPropValue = r1     // Catch:{ Exception -> 0x0047 }
            r3.close()     // Catch:{ Exception -> 0x0040 }
            goto L_0x0041
        L_0x0040:
        L_0x0041:
            if (r2 == 0) goto L_0x0071
        L_0x0043:
            r2.destroy()     // Catch:{ all -> 0x0083 }
            goto L_0x0071
        L_0x0047:
            r1 = move-exception
            goto L_0x005c
        L_0x0049:
            r3 = move-exception
            r7 = r3
            r3 = r1
            r1 = r7
            goto L_0x0076
        L_0x004e:
            r3 = move-exception
            r7 = r3
            r3 = r1
            r1 = r7
            goto L_0x005c
        L_0x0053:
            r2 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
            goto L_0x0076
        L_0x0058:
            r2 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
        L_0x005c:
            java.lang.String r4 = TAG     // Catch:{ all -> 0x0075 }
            java.lang.String r5 = "Failed to query for metro.host prop:"
            com.facebook.common.logging.FLog.m105w((java.lang.String) r4, (java.lang.String) r5, (java.lang.Throwable) r1)     // Catch:{ all -> 0x0075 }
            java.lang.String r1 = ""
            metroHostPropValue = r1     // Catch:{ all -> 0x0075 }
            if (r3 == 0) goto L_0x006e
            r3.close()     // Catch:{ Exception -> 0x006d }
            goto L_0x006e
        L_0x006d:
        L_0x006e:
            if (r2 == 0) goto L_0x0071
            goto L_0x0043
        L_0x0071:
            java.lang.String r1 = metroHostPropValue     // Catch:{ all -> 0x0083 }
            monitor-exit(r0)
            return r1
        L_0x0075:
            r1 = move-exception
        L_0x0076:
            if (r3 == 0) goto L_0x007d
            r3.close()     // Catch:{ Exception -> 0x007c }
            goto L_0x007d
        L_0x007c:
        L_0x007d:
            if (r2 == 0) goto L_0x0082
            r2.destroy()     // Catch:{ all -> 0x0083 }
        L_0x0082:
            throw r1     // Catch:{ all -> 0x0083 }
        L_0x0083:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.systeminfo.AndroidInfoHelpers.getMetroHostPropValue():java.lang.String");
    }
}
