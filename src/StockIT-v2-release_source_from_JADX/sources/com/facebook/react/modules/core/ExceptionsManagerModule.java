package com.facebook.react.modules.core;

import com.facebook.common.logging.FLog;
import com.facebook.fbreact.specs.NativeExceptionsManagerSpec;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.JavascriptException;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.util.ExceptionDataHelper;
import com.facebook.react.util.JSStackTrace;
import com.onesignal.OneSignalDbContract;

@ReactModule(name = "ExceptionsManager")
public class ExceptionsManagerModule extends NativeExceptionsManagerSpec {
    public static final String NAME = "ExceptionsManager";
    private final DevSupportManager mDevSupportManager;

    public String getName() {
        return NAME;
    }

    public ExceptionsManagerModule(DevSupportManager devSupportManager) {
        super((ReactApplicationContext) null);
        this.mDevSupportManager = devSupportManager;
    }

    public void reportFatalException(String str, ReadableArray readableArray, double d) {
        JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
        javaOnlyMap.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, str);
        javaOnlyMap.putArray("stack", readableArray);
        javaOnlyMap.putInt("id", (int) d);
        javaOnlyMap.putBoolean("isFatal", true);
        reportException(javaOnlyMap);
    }

    public void reportSoftException(String str, ReadableArray readableArray, double d) {
        JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
        javaOnlyMap.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, str);
        javaOnlyMap.putArray("stack", readableArray);
        javaOnlyMap.putInt("id", (int) d);
        javaOnlyMap.putBoolean("isFatal", false);
        reportException(javaOnlyMap);
    }

    public void reportException(ReadableMap readableMap) {
        String string = readableMap.hasKey(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE) ? readableMap.getString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE) : "";
        ReadableArray array = readableMap.hasKey("stack") ? readableMap.getArray("stack") : Arguments.createArray();
        int i = readableMap.hasKey("id") ? readableMap.getInt("id") : -1;
        boolean z = false;
        boolean z2 = readableMap.hasKey("isFatal") ? readableMap.getBoolean("isFatal") : false;
        if (this.mDevSupportManager.getDevSupportEnabled()) {
            if (readableMap.getMap("extraData") != null && readableMap.getMap("extraData").hasKey("suppressRedBox")) {
                z = readableMap.getMap("extraData").getBoolean("suppressRedBox");
            }
            if (!z) {
                this.mDevSupportManager.showNewJSError(string, array, i);
                return;
            }
            return;
        }
        String extraDataAsJson = ExceptionDataHelper.getExtraDataAsJson(readableMap);
        if (!z2) {
            FLog.m64e(ReactConstants.TAG, JSStackTrace.format(string, array));
            if (extraDataAsJson != null) {
                FLog.m53d(ReactConstants.TAG, "extraData: %s", (Object) extraDataAsJson);
                return;
            }
            return;
        }
        throw new JavascriptException(JSStackTrace.format(string, array)).setExtraDataAsJson(extraDataAsJson);
    }

    public void updateExceptionMessage(String str, ReadableArray readableArray, double d) {
        int i = (int) d;
        if (this.mDevSupportManager.getDevSupportEnabled()) {
            this.mDevSupportManager.updateJSError(str, readableArray, i);
        }
    }

    public void dismissRedbox() {
        if (this.mDevSupportManager.getDevSupportEnabled()) {
            this.mDevSupportManager.hideRedboxDialog();
        }
    }
}
