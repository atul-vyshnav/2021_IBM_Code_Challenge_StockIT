package com.reactcommunity.rndatetimepicker;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "RNDatePickerAndroid")
public class RNDatePickerDialogModule extends ReactContextBaseJavaModule {
    public static final String FRAGMENT_TAG = "RNDatePickerAndroid";

    public String getName() {
        return FRAGMENT_TAG;
    }

    public RNDatePickerDialogModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    private class DatePickerDialogListener implements DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener, DialogInterface.OnClickListener {
        private final Promise mPromise;
        private boolean mPromiseResolved = false;

        public DatePickerDialogListener(Promise promise) {
            this.mPromise = promise;
        }

        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            if (!this.mPromiseResolved && RNDatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                WritableNativeMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_DATE_SET);
                writableNativeMap.putInt("year", i);
                writableNativeMap.putInt("month", i2);
                writableNativeMap.putInt("day", i3);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }

        public void onDismiss(DialogInterface dialogInterface) {
            if (!this.mPromiseResolved && RNDatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                WritableNativeMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_DISMISSED);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (!this.mPromiseResolved && RNDatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                WritableNativeMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_NEUTRAL_BUTTON);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }
    }

    @ReactMethod
    public void dismiss(Promise promise) {
        Common.dismissDialog((FragmentActivity) getCurrentActivity(), FRAGMENT_TAG, promise);
    }

    @ReactMethod
    public void open(final ReadableMap readableMap, final Promise promise) {
        FragmentActivity fragmentActivity = (FragmentActivity) getCurrentActivity();
        if (fragmentActivity == null) {
            promise.reject(RNConstants.ERROR_NO_ACTIVITY, "Tried to open a DatePicker dialog while not attached to an Activity");
            return;
        }
        final FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                ReadableMap readableMap;
                RNDatePickerDialogFragment rNDatePickerDialogFragment = (RNDatePickerDialogFragment) supportFragmentManager.findFragmentByTag(RNDatePickerDialogModule.FRAGMENT_TAG);
                if (rNDatePickerDialogFragment == null || (readableMap = readableMap) == null) {
                    RNDatePickerDialogFragment rNDatePickerDialogFragment2 = new RNDatePickerDialogFragment();
                    ReadableMap readableMap2 = readableMap;
                    if (readableMap2 != null) {
                        rNDatePickerDialogFragment2.setArguments(RNDatePickerDialogModule.this.createFragmentArguments(readableMap2));
                    }
                    DatePickerDialogListener datePickerDialogListener = new DatePickerDialogListener(promise);
                    rNDatePickerDialogFragment2.setOnDismissListener(datePickerDialogListener);
                    rNDatePickerDialogFragment2.setOnDateSetListener(datePickerDialogListener);
                    rNDatePickerDialogFragment2.setOnNeutralButtonActionListener(datePickerDialogListener);
                    rNDatePickerDialogFragment2.show(supportFragmentManager, RNDatePickerDialogModule.FRAGMENT_TAG);
                    return;
                }
                rNDatePickerDialogFragment.update(RNDatePickerDialogModule.this.createFragmentArguments(readableMap));
            }
        });
    }

    /* access modifiers changed from: private */
    public Bundle createFragmentArguments(ReadableMap readableMap) {
        Bundle bundle = new Bundle();
        if (readableMap.hasKey("value") && !readableMap.isNull("value")) {
            bundle.putLong("value", (long) readableMap.getDouble("value"));
        }
        if (readableMap.hasKey(RNConstants.ARG_MINDATE) && !readableMap.isNull(RNConstants.ARG_MINDATE)) {
            bundle.putLong(RNConstants.ARG_MINDATE, (long) readableMap.getDouble(RNConstants.ARG_MINDATE));
        }
        if (readableMap.hasKey(RNConstants.ARG_MAXDATE) && !readableMap.isNull(RNConstants.ARG_MAXDATE)) {
            bundle.putLong(RNConstants.ARG_MAXDATE, (long) readableMap.getDouble(RNConstants.ARG_MAXDATE));
        }
        if (readableMap.hasKey("display") && !readableMap.isNull("display")) {
            bundle.putString("display", readableMap.getString("display"));
        }
        if (readableMap.hasKey(RNConstants.ARG_NEUTRAL_BUTTON_LABEL) && !readableMap.isNull(RNConstants.ARG_NEUTRAL_BUTTON_LABEL)) {
            bundle.putString(RNConstants.ARG_NEUTRAL_BUTTON_LABEL, readableMap.getString(RNConstants.ARG_NEUTRAL_BUTTON_LABEL));
        }
        return bundle;
    }
}
