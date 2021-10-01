package p008cl.json.social;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Build;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;

/* renamed from: cl.json.social.TargetChosenReceiver */
public class TargetChosenReceiver extends BroadcastReceiver {
    private static final String EXTRA_RECEIVER_TOKEN = "receiver_token";
    private static final Object LOCK = new Object();
    private static Callback failureCallback;
    private static TargetChosenReceiver sLastRegisteredReceiver;
    private static String sTargetChosenReceiveAction;
    private static Callback successCallback;

    public static boolean isSupported() {
        return Build.VERSION.SDK_INT >= 22;
    }

    public static void registerCallbacks(Callback callback, Callback callback2) {
        successCallback = callback;
        failureCallback = callback2;
    }

    public static IntentSender getSharingSenderIntent(ReactContext reactContext) {
        synchronized (LOCK) {
            if (sTargetChosenReceiveAction == null) {
                sTargetChosenReceiveAction = reactContext.getPackageName() + "/" + TargetChosenReceiver.class.getName() + "_ACTION";
            }
            Context applicationContext = reactContext.getApplicationContext();
            if (sLastRegisteredReceiver != null) {
                applicationContext.unregisterReceiver(sLastRegisteredReceiver);
            }
            TargetChosenReceiver targetChosenReceiver = new TargetChosenReceiver();
            sLastRegisteredReceiver = targetChosenReceiver;
            applicationContext.registerReceiver(targetChosenReceiver, new IntentFilter(sTargetChosenReceiveAction));
        }
        Intent intent = new Intent(sTargetChosenReceiveAction);
        intent.setPackage(reactContext.getPackageName());
        intent.putExtra(EXTRA_RECEIVER_TOKEN, sLastRegisteredReceiver.hashCode());
        return PendingIntent.getBroadcast(reactContext, 0, intent, 1342177280).getIntentSender();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        if (r5.hasExtra(EXTRA_RECEIVER_TOKEN) == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        if (r5.getIntExtra(EXTRA_RECEIVER_TOKEN, 0) == hashCode()) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        r4 = (android.content.ComponentName) r5.getParcelableExtra("android.intent.extra.CHOSEN_COMPONENT");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
        if (r4 == null) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        sendCallback(true, true, r4.flattenToString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004a, code lost:
        sendCallback(true, true, "OK");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r4, android.content.Intent r5) {
        /*
            r3 = this;
            java.lang.Object r0 = LOCK
            monitor-enter(r0)
            cl.json.social.TargetChosenReceiver r1 = sLastRegisteredReceiver     // Catch:{ all -> 0x005a }
            if (r1 == r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            return
        L_0x0009:
            android.content.Context r4 = r4.getApplicationContext()     // Catch:{ all -> 0x005a }
            cl.json.social.TargetChosenReceiver r1 = sLastRegisteredReceiver     // Catch:{ all -> 0x005a }
            r4.unregisterReceiver(r1)     // Catch:{ all -> 0x005a }
            r4 = 0
            sLastRegisteredReceiver = r4     // Catch:{ all -> 0x005a }
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            java.lang.String r4 = "receiver_token"
            boolean r4 = r5.hasExtra(r4)
            if (r4 == 0) goto L_0x0059
            java.lang.String r4 = "receiver_token"
            r0 = 0
            int r4 = r5.getIntExtra(r4, r0)
            int r1 = r3.hashCode()
            if (r4 == r1) goto L_0x002c
            goto L_0x0059
        L_0x002c:
            java.lang.String r4 = "android.intent.extra.CHOSEN_COMPONENT"
            android.os.Parcelable r4 = r5.getParcelableExtra(r4)
            android.content.ComponentName r4 = (android.content.ComponentName) r4
            r5 = 2
            r1 = 1
            if (r4 == 0) goto L_0x004a
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r1)
            r5[r0] = r2
            java.lang.String r4 = r4.flattenToString()
            r5[r1] = r4
            sendCallback(r1, r5)
            goto L_0x0059
        L_0x004a:
            java.lang.Object[] r4 = new java.lang.Object[r5]
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r1)
            r4[r0] = r5
            java.lang.String r5 = "OK"
            r4[r1] = r5
            sendCallback(r1, r4)
        L_0x0059:
            return
        L_0x005a:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: p008cl.json.social.TargetChosenReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    public static void sendCallback(boolean z, Object... objArr) {
        if (z) {
            Callback callback = successCallback;
            if (callback != null) {
                callback.invoke(objArr);
            }
        } else {
            Callback callback2 = failureCallback;
            if (callback2 != null) {
                callback2.invoke(objArr);
            }
        }
        successCallback = null;
        failureCallback = null;
    }
}
