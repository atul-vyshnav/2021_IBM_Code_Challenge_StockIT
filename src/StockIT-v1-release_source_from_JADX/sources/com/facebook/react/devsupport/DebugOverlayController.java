package com.facebook.react.devsupport;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.ReactConstants;
import com.google.android.exoplayer2.C1379C;

class DebugOverlayController {
    /* access modifiers changed from: private */
    public FrameLayout mFPSDebugViewContainer;
    /* access modifiers changed from: private */
    public final ReactContext mReactContext;
    /* access modifiers changed from: private */
    public final WindowManager mWindowManager;

    public static void requestPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName()));
            intent.setFlags(C1379C.ENCODING_PCM_MU_LAW);
            FLog.m104w(ReactConstants.TAG, "Overlay permissions needs to be granted in order for react native apps to run in dev mode");
            if (canHandleIntent(context, intent)) {
                context.startActivity(intent);
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean permissionCheck(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.canDrawOverlays(context);
        }
        return hasPermission(context, "android.permission.SYSTEM_ALERT_WINDOW");
    }

    private static boolean hasPermission(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
            if (packageInfo.requestedPermissions != null) {
                for (String equals : packageInfo.requestedPermissions) {
                    if (equals.equals(str)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            FLog.m65e(ReactConstants.TAG, "Error while retrieving package info", (Throwable) e);
        }
        return false;
    }

    private static boolean canHandleIntent(Context context, Intent intent) {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public DebugOverlayController(ReactContext reactContext) {
        this.mReactContext = reactContext;
        this.mWindowManager = (WindowManager) reactContext.getSystemService("window");
    }

    public void setFpsDebugViewVisible(final boolean z) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                if (!z || DebugOverlayController.this.mFPSDebugViewContainer != null) {
                    if (!z && DebugOverlayController.this.mFPSDebugViewContainer != null) {
                        DebugOverlayController.this.mFPSDebugViewContainer.removeAllViews();
                        DebugOverlayController.this.mWindowManager.removeView(DebugOverlayController.this.mFPSDebugViewContainer);
                        FrameLayout unused = DebugOverlayController.this.mFPSDebugViewContainer = null;
                    }
                } else if (!DebugOverlayController.permissionCheck(DebugOverlayController.this.mReactContext)) {
                    FLog.m52d(ReactConstants.TAG, "Wait for overlay permission to be set");
                } else {
                    FrameLayout unused2 = DebugOverlayController.this.mFPSDebugViewContainer = new FpsView(DebugOverlayController.this.mReactContext);
                    DebugOverlayController.this.mWindowManager.addView(DebugOverlayController.this.mFPSDebugViewContainer, new WindowManager.LayoutParams(-1, -1, WindowOverlayCompat.TYPE_SYSTEM_OVERLAY, 24, -3));
                }
            }
        });
    }
}
