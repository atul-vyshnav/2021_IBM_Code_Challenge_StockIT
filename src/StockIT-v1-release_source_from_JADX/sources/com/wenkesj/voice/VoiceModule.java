package com.wenkesj.voice;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.onesignal.OneSignalDbContract;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;

public class VoiceModule extends ReactContextBaseJavaModule implements RecognitionListener {
    /* access modifiers changed from: private */
    public boolean isRecognizing = false;
    private String locale = null;
    final ReactApplicationContext reactContext;
    /* access modifiers changed from: private */
    public SpeechRecognizer speech = null;

    public static String getErrorText(int i) {
        switch (i) {
            case 1:
                return "Network timeout";
            case 2:
                return "Network error";
            case 3:
                return "Audio recording error";
            case 4:
                return "error from server";
            case 5:
                return "Client side error";
            case 6:
                return "No speech input";
            case 7:
                return "No match";
            case 8:
                return "RecognitionService busy";
            case 9:
                return "Insufficient permissions";
            default:
                return "Didn't understand, please try again.";
        }
    }

    public String getName() {
        return "RCTVoice";
    }

    public void onEvent(int i, Bundle bundle) {
    }

    public VoiceModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.reactContext = reactApplicationContext;
    }

    private String getLocale(String str) {
        if (str == null || str.equals("")) {
            return Locale.getDefault().toString();
        }
        return str;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0153  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startListening(com.facebook.react.bridge.ReadableMap r12) {
        /*
            r11 = this;
            android.speech.SpeechRecognizer r0 = r11.speech
            if (r0 == 0) goto L_0x000a
            r0.destroy()
            r0 = 0
            r11.speech = r0
        L_0x000a:
            java.lang.String r0 = "RECOGNIZER_ENGINE"
            boolean r1 = r12.hasKey(r0)
            r2 = 0
            r3 = -1
            if (r1 == 0) goto L_0x0047
            java.lang.String r0 = r12.getString(r0)
            int r1 = r0.hashCode()
            r4 = 2108052025(0x7da65239, float:2.7634846E37)
            if (r1 == r4) goto L_0x0022
            goto L_0x002c
        L_0x0022:
            java.lang.String r1 = "GOOGLE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002c
            r0 = 0
            goto L_0x002d
        L_0x002c:
            r0 = -1
        L_0x002d:
            if (r0 == 0) goto L_0x0038
            com.facebook.react.bridge.ReactApplicationContext r0 = r11.reactContext
            android.speech.SpeechRecognizer r0 = android.speech.SpeechRecognizer.createSpeechRecognizer(r0)
            r11.speech = r0
            goto L_0x004f
        L_0x0038:
            com.facebook.react.bridge.ReactApplicationContext r0 = r11.reactContext
            java.lang.String r1 = "com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"
            android.content.ComponentName r1 = android.content.ComponentName.unflattenFromString(r1)
            android.speech.SpeechRecognizer r0 = android.speech.SpeechRecognizer.createSpeechRecognizer(r0, r1)
            r11.speech = r0
            goto L_0x004f
        L_0x0047:
            com.facebook.react.bridge.ReactApplicationContext r0 = r11.reactContext
            android.speech.SpeechRecognizer r0 = android.speech.SpeechRecognizer.createSpeechRecognizer(r0)
            r11.speech = r0
        L_0x004f:
            android.speech.SpeechRecognizer r0 = r11.speech
            r0.setRecognitionListener(r11)
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "android.speech.action.RECOGNIZE_SPEECH"
            r0.<init>(r1)
            com.facebook.react.bridge.ReadableMapKeySetIterator r1 = r12.keySetIterator()
        L_0x005f:
            boolean r4 = r1.hasNextKey()
            if (r4 == 0) goto L_0x0158
            java.lang.String r4 = r1.nextKey()
            int r5 = r4.hashCode()
            r6 = 5
            r7 = 4
            r8 = 3
            r9 = 2
            r10 = 1
            switch(r5) {
                case -799376495: goto L_0x00a8;
                case -530349781: goto L_0x009e;
                case -136377678: goto L_0x0094;
                case 1189324300: goto L_0x008a;
                case 1326635678: goto L_0x0080;
                case 2061034665: goto L_0x0076;
                default: goto L_0x0075;
            }
        L_0x0075:
            goto L_0x00b2
        L_0x0076:
            java.lang.String r5 = "EXTRA_PARTIAL_RESULTS"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x00b2
            r5 = 2
            goto L_0x00b3
        L_0x0080:
            java.lang.String r5 = "EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x00b2
            r5 = 4
            goto L_0x00b3
        L_0x008a:
            java.lang.String r5 = "EXTRA_MAX_RESULTS"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x00b2
            r5 = 1
            goto L_0x00b3
        L_0x0094:
            java.lang.String r5 = "EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x00b2
            r5 = 5
            goto L_0x00b3
        L_0x009e:
            java.lang.String r5 = "EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x00b2
            r5 = 3
            goto L_0x00b3
        L_0x00a8:
            java.lang.String r5 = "EXTRA_LANGUAGE_MODEL"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x00b2
            r5 = 0
            goto L_0x00b3
        L_0x00b2:
            r5 = -1
        L_0x00b3:
            if (r5 == 0) goto L_0x0116
            if (r5 == r10) goto L_0x0103
            if (r5 == r9) goto L_0x00f8
            if (r5 == r8) goto L_0x00e5
            if (r5 == r7) goto L_0x00d2
            if (r5 == r6) goto L_0x00c0
            goto L_0x005f
        L_0x00c0:
            double r4 = r12.getDouble(r4)
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            int r4 = r4.intValue()
            java.lang.String r5 = "android.speech.extras.SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS"
            r0.putExtra(r5, r4)
            goto L_0x005f
        L_0x00d2:
            double r4 = r12.getDouble(r4)
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            int r4 = r4.intValue()
            java.lang.String r5 = "android.speech.extras.SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS"
            r0.putExtra(r5, r4)
            goto L_0x005f
        L_0x00e5:
            double r4 = r12.getDouble(r4)
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            int r4 = r4.intValue()
            java.lang.String r5 = "android.speech.extras.SPEECH_INPUT_MINIMUM_LENGTH_MILLIS"
            r0.putExtra(r5, r4)
            goto L_0x005f
        L_0x00f8:
            boolean r4 = r12.getBoolean(r4)
            java.lang.String r5 = "android.speech.extra.PARTIAL_RESULTS"
            r0.putExtra(r5, r4)
            goto L_0x005f
        L_0x0103:
            double r4 = r12.getDouble(r4)
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            int r4 = r4.intValue()
            java.lang.String r5 = "android.speech.extra.MAX_RESULTS"
            r0.putExtra(r5, r4)
            goto L_0x005f
        L_0x0116:
            java.lang.String r4 = r12.getString(r4)
            int r5 = r4.hashCode()
            r6 = -874741222(0xffffffffcbdc821a, float:-2.8902452E7)
            if (r5 == r6) goto L_0x0133
            r6 = 1951040016(0x744a8210, float:6.4177365E31)
            if (r5 == r6) goto L_0x0129
            goto L_0x013d
        L_0x0129:
            java.lang.String r5 = "LANGUAGE_MODEL_WEB_SEARCH"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x013d
            r4 = 1
            goto L_0x013e
        L_0x0133:
            java.lang.String r5 = "LANGUAGE_MODEL_FREE_FORM"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x013d
            r4 = 0
            goto L_0x013e
        L_0x013d:
            r4 = -1
        L_0x013e:
            java.lang.String r5 = "free_form"
            java.lang.String r6 = "android.speech.extra.LANGUAGE_MODEL"
            if (r4 == 0) goto L_0x0153
            if (r4 == r10) goto L_0x014b
            r0.putExtra(r6, r5)
            goto L_0x005f
        L_0x014b:
            java.lang.String r4 = "web_search"
            r0.putExtra(r6, r4)
            goto L_0x005f
        L_0x0153:
            r0.putExtra(r6, r5)
            goto L_0x005f
        L_0x0158:
            java.lang.String r12 = r11.locale
            java.lang.String r12 = r11.getLocale(r12)
            java.lang.String r1 = "android.speech.extra.LANGUAGE"
            r0.putExtra(r1, r12)
            android.speech.SpeechRecognizer r12 = r11.speech
            r12.startListening(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wenkesj.voice.VoiceModule.startListening(com.facebook.react.bridge.ReadableMap):void");
    }

    /* access modifiers changed from: private */
    public void startSpeechWithPermissions(String str, final ReadableMap readableMap, final Callback callback) {
        this.locale = str;
        new Handler(this.reactContext.getMainLooper()).post(new Runnable() {
            public void run() {
                try {
                    VoiceModule.this.startListening(readableMap);
                    boolean unused = VoiceModule.this.isRecognizing = true;
                    callback.invoke(false);
                } catch (Exception e) {
                    callback.invoke(e.getMessage());
                }
            }
        });
    }

    @ReactMethod
    public void startSpeech(final String str, final ReadableMap readableMap, final Callback callback) {
        if (isPermissionGranted() || !readableMap.getBoolean("REQUEST_PERMISSIONS_AUTO")) {
            startSpeechWithPermissions(str, readableMap, callback);
            return;
        }
        String[] strArr = {"android.permission.RECORD_AUDIO"};
        if (getCurrentActivity() != null) {
            ((PermissionAwareActivity) getCurrentActivity()).requestPermissions(strArr, 1, new PermissionListener() {
                public boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
                    boolean z = true;
                    for (int i2 = 0; i2 < strArr.length; i2++) {
                        z = z && (iArr[i2] == 0);
                    }
                    VoiceModule.this.startSpeechWithPermissions(str, readableMap, callback);
                    return z;
                }
            });
        }
    }

    @ReactMethod
    public void stopSpeech(final Callback callback) {
        new Handler(this.reactContext.getMainLooper()).post(new Runnable() {
            public void run() {
                try {
                    if (VoiceModule.this.speech != null) {
                        VoiceModule.this.speech.stopListening();
                    }
                    boolean unused = VoiceModule.this.isRecognizing = false;
                    callback.invoke(false);
                } catch (Exception e) {
                    callback.invoke(e.getMessage());
                }
            }
        });
    }

    @ReactMethod
    public void cancelSpeech(final Callback callback) {
        new Handler(this.reactContext.getMainLooper()).post(new Runnable() {
            public void run() {
                try {
                    if (VoiceModule.this.speech != null) {
                        VoiceModule.this.speech.cancel();
                    }
                    boolean unused = VoiceModule.this.isRecognizing = false;
                    callback.invoke(false);
                } catch (Exception e) {
                    callback.invoke(e.getMessage());
                }
            }
        });
    }

    @ReactMethod
    public void destroySpeech(final Callback callback) {
        new Handler(this.reactContext.getMainLooper()).post(new Runnable() {
            public void run() {
                try {
                    if (VoiceModule.this.speech != null) {
                        VoiceModule.this.speech.destroy();
                    }
                    SpeechRecognizer unused = VoiceModule.this.speech = null;
                    boolean unused2 = VoiceModule.this.isRecognizing = false;
                    callback.invoke(false);
                } catch (Exception e) {
                    callback.invoke(e.getMessage());
                }
            }
        });
    }

    @ReactMethod
    public void isSpeechAvailable(final Callback callback) {
        new Handler(this.reactContext.getMainLooper()).post(new Runnable() {
            public void run() {
                try {
                    Boolean valueOf = Boolean.valueOf(SpeechRecognizer.isRecognitionAvailable(this.reactContext));
                    callback.invoke(valueOf, false);
                } catch (Exception e) {
                    callback.invoke(false, e.getMessage());
                }
            }
        });
    }

    @ReactMethod
    public void getSpeechRecognitionServices(Promise promise) {
        List<ResolveInfo> queryIntentServices = this.reactContext.getPackageManager().queryIntentServices(new Intent("android.speech.RecognitionService"), 0);
        WritableArray createArray = Arguments.createArray();
        for (ResolveInfo resolveInfo : queryIntentServices) {
            createArray.pushString(resolveInfo.serviceInfo.packageName);
        }
        promise.resolve(createArray);
    }

    private boolean isPermissionGranted() {
        return getReactApplicationContext().checkCallingOrSelfPermission("android.permission.RECORD_AUDIO") == 0;
    }

    @ReactMethod
    public void isRecognizing(Callback callback) {
        callback.invoke(Boolean.valueOf(this.isRecognizing));
    }

    private void sendEvent(String str, @Nullable WritableMap writableMap) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, writableMap);
    }

    public void onBeginningOfSpeech() {
        WritableMap createMap = Arguments.createMap();
        createMap.putBoolean("error", false);
        sendEvent("onSpeechStart", createMap);
        Log.d("ASR", "onBeginningOfSpeech()");
    }

    public void onBufferReceived(byte[] bArr) {
        WritableMap createMap = Arguments.createMap();
        createMap.putBoolean("error", false);
        sendEvent("onSpeechRecognized", createMap);
        Log.d("ASR", "onBufferReceived()");
    }

    public void onEndOfSpeech() {
        WritableMap createMap = Arguments.createMap();
        createMap.putBoolean("error", false);
        sendEvent("onSpeechEnd", createMap);
        Log.d("ASR", "onEndOfSpeech()");
        this.isRecognizing = false;
    }

    public void onError(int i) {
        String format = String.format("%d/%s", new Object[]{Integer.valueOf(i), getErrorText(i)});
        WritableMap createMap = Arguments.createMap();
        createMap.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, format);
        WritableMap createMap2 = Arguments.createMap();
        createMap2.putMap("error", createMap);
        sendEvent("onSpeechError", createMap2);
        Log.d("ASR", "onError() - " + format);
    }

    public void onPartialResults(Bundle bundle) {
        WritableArray createArray = Arguments.createArray();
        Iterator<String> it = bundle.getStringArrayList("results_recognition").iterator();
        while (it.hasNext()) {
            createArray.pushString(it.next());
        }
        WritableMap createMap = Arguments.createMap();
        createMap.putArray("value", createArray);
        sendEvent("onSpeechPartialResults", createMap);
        Log.d("ASR", "onPartialResults()");
    }

    public void onReadyForSpeech(Bundle bundle) {
        WritableMap createMap = Arguments.createMap();
        createMap.putBoolean("error", false);
        sendEvent("onSpeechStart", createMap);
        Log.d("ASR", "onReadyForSpeech()");
    }

    public void onResults(Bundle bundle) {
        WritableArray createArray = Arguments.createArray();
        Iterator<String> it = bundle.getStringArrayList("results_recognition").iterator();
        while (it.hasNext()) {
            createArray.pushString(it.next());
        }
        WritableMap createMap = Arguments.createMap();
        createMap.putArray("value", createArray);
        sendEvent("onSpeechResults", createMap);
        Log.d("ASR", "onResults()");
    }

    public void onRmsChanged(float f) {
        WritableMap createMap = Arguments.createMap();
        createMap.putDouble("value", (double) f);
        sendEvent("onSpeechVolumeChanged", createMap);
    }
}
