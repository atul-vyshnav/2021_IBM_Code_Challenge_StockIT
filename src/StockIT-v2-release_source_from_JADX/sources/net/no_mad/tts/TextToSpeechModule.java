package net.no_mad.tts;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import com.amplitude.api.Constants;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import expo.modules.imagepicker.ImagePickerConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class TextToSpeechModule extends ReactContextBaseJavaModule {
    /* access modifiers changed from: private */
    public AudioManager.OnAudioFocusChangeListener afChangeListener;
    /* access modifiers changed from: private */
    public AudioManager audioManager;
    /* access modifiers changed from: private */
    public boolean ducking = false;
    /* access modifiers changed from: private */
    public ArrayList<Promise> initStatusPromises;
    /* access modifiers changed from: private */
    public Boolean ready = null;
    private TextToSpeech tts;

    public String getName() {
        return "TextToSpeech";
    }

    public TextToSpeechModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.audioManager = (AudioManager) reactApplicationContext.getApplicationContext().getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.initStatusPromises = new ArrayList<>();
        TextToSpeech textToSpeech = new TextToSpeech(getReactApplicationContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int i) {
                synchronized (TextToSpeechModule.this.initStatusPromises) {
                    Boolean unused = TextToSpeechModule.this.ready = i == 0 ? Boolean.TRUE : Boolean.FALSE;
                    Iterator it = TextToSpeechModule.this.initStatusPromises.iterator();
                    while (it.hasNext()) {
                        TextToSpeechModule.this.resolveReadyPromise((Promise) it.next());
                    }
                    TextToSpeechModule.this.initStatusPromises.clear();
                }
            }
        });
        this.tts = textToSpeech;
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            public void onStart(String str) {
                TextToSpeechModule.this.sendEvent("tts-start", str);
            }

            public void onDone(String str) {
                if (TextToSpeechModule.this.ducking) {
                    TextToSpeechModule.this.audioManager.abandonAudioFocus(TextToSpeechModule.this.afChangeListener);
                }
                TextToSpeechModule.this.sendEvent("tts-finish", str);
            }

            public void onError(String str) {
                if (TextToSpeechModule.this.ducking) {
                    TextToSpeechModule.this.audioManager.abandonAudioFocus(TextToSpeechModule.this.afChangeListener);
                }
                TextToSpeechModule.this.sendEvent("tts-error", str);
            }

            public void onStop(String str, boolean z) {
                if (TextToSpeechModule.this.ducking) {
                    TextToSpeechModule.this.audioManager.abandonAudioFocus(TextToSpeechModule.this.afChangeListener);
                }
                TextToSpeechModule.this.sendEvent("tts-cancel", str);
            }
        });
    }

    /* access modifiers changed from: private */
    public void resolveReadyPromise(Promise promise) {
        if (this.ready == Boolean.TRUE) {
            promise.resolve("success");
        } else {
            promise.reject("no_engine", "No TTS engine installed");
        }
    }

    private static void resolvePromiseWithStatusCode(int i, Promise promise) {
        switch (i) {
            case -9:
                promise.reject("not_installed_yet", "Unfinished download of voice data");
                return;
            case SplitInstallErrorCode.INCOMPATIBLE_WITH_EXISTING_SESSION:
                promise.reject("invalid_request", "Failure caused by an invalid request");
                return;
            case -7:
                promise.reject("network_timeout", "Failure caused by network timeout.");
                return;
            case -6:
                promise.reject("network_error", "Failure caused by a network connectivity problems");
                return;
            case -5:
                promise.reject("output_error", "Failure related to the output (audio device or a file)");
                return;
            case -4:
                promise.reject("service_error", "Failure of a TTS service");
                return;
            case -3:
                promise.reject("synthesis_error", "Failure of a TTS engine to synthesize the given input");
                return;
            case -2:
                promise.reject("lang_not_supported", "Language is not supported");
                return;
            case -1:
                promise.reject("lang_missing_data", "Language data is missing");
                return;
            case 0:
                promise.resolve("success");
                return;
            case 1:
                promise.resolve("lang_country_available");
                return;
            case 2:
                promise.resolve("lang_country_var_available");
                return;
            default:
                promise.reject("error", "Unknown error code: " + i);
                return;
        }
    }

    @ReactMethod
    public void getInitStatus(Promise promise) {
        synchronized (this.initStatusPromises) {
            if (this.ready == null) {
                this.initStatusPromises.add(promise);
            } else {
                resolveReadyPromise(promise);
            }
        }
    }

    @ReactMethod
    public void speak(String str, ReadableMap readableMap, Promise promise) {
        if (!notReady(promise)) {
            if (!this.ducking || this.audioManager.requestAudioFocus(this.afChangeListener, 3, 3) == 1) {
                String num = Integer.toString(str.hashCode());
                int speak = speak(str, num, readableMap);
                if (speak == 0) {
                    promise.resolve(num);
                } else {
                    resolvePromiseWithStatusCode(speak, promise);
                }
            } else {
                promise.reject("Android AudioManager error, failed to request audio focus");
            }
        }
    }

    @ReactMethod
    public void setDefaultLanguage(String str, Promise promise) {
        Locale locale;
        if (!notReady(promise)) {
            if (str.indexOf("-") != -1) {
                String[] split = str.split("-");
                locale = new Locale(split[0], split[1]);
            } else {
                locale = new Locale(str);
            }
            try {
                resolvePromiseWithStatusCode(this.tts.setLanguage(locale), promise);
            } catch (Exception unused) {
                promise.reject("error", "Unknown error code");
            }
        }
    }

    @ReactMethod
    public void setDucking(Boolean bool, Promise promise) {
        if (!notReady(promise)) {
            this.ducking = bool.booleanValue();
            promise.resolve("success");
        }
    }

    @ReactMethod
    public void setDefaultRate(Float f, Boolean bool, Promise promise) {
        float f2;
        if (!notReady(promise)) {
            if (bool.booleanValue()) {
                promise.resolve(Integer.valueOf(this.tts.setSpeechRate(f.floatValue())));
                return;
            }
            if (f.floatValue() < 0.5f) {
                f2 = f.floatValue() * 2.0f;
            } else {
                f2 = (f.floatValue() * 4.0f) - 1.0f;
            }
            promise.resolve(Integer.valueOf(this.tts.setSpeechRate(f2)));
        }
    }

    @ReactMethod
    public void setDefaultPitch(Float f, Promise promise) {
        if (!notReady(promise)) {
            promise.resolve(Integer.valueOf(this.tts.setPitch(f.floatValue())));
        }
    }

    @ReactMethod
    public void setDefaultVoice(String str, Promise promise) {
        if (!notReady(promise)) {
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    for (Voice next : this.tts.getVoices()) {
                        if (next.getName().equals(str)) {
                            resolvePromiseWithStatusCode(this.tts.setVoice(next), promise);
                            return;
                        }
                    }
                } catch (Exception unused) {
                }
                promise.reject("not_found", "The selected voice was not found");
                return;
            }
            promise.reject("not_available", "Android API 21 level or higher is required");
        }
    }

    @ReactMethod
    public void voices(Promise promise) {
        if (!notReady(promise)) {
            WritableArray createArray = Arguments.createArray();
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    for (Voice next : this.tts.getVoices()) {
                        WritableMap createMap = Arguments.createMap();
                        createMap.putString("id", next.getName());
                        createMap.putString("name", next.getName());
                        createMap.putString(Constants.AMP_TRACKING_OPTION_LANGUAGE, next.getLocale().toLanguageTag());
                        createMap.putInt(ImagePickerConstants.OPTION_QUALITY, next.getQuality());
                        createMap.putInt("latency", next.getLatency());
                        createMap.putBoolean("networkConnectionRequired", next.isNetworkConnectionRequired());
                        createMap.putBoolean("notInstalled", next.getFeatures().contains("notInstalled"));
                        createArray.pushMap(createMap);
                    }
                } catch (Exception unused) {
                }
            }
            promise.resolve(createArray);
        }
    }

    @ReactMethod
    public void stop(Promise promise) {
        if (!notReady(promise)) {
            resolvePromiseWithStatusCode(this.tts.stop(), promise);
        }
    }

    @ReactMethod
    private void requestInstallEngine(Promise promise) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id=com.google.android.tts"));
        try {
            getCurrentActivity().startActivity(intent);
            promise.resolve("success");
        } catch (Exception unused) {
            promise.reject("error", "Could not open Google Text to Speech App in the Play Store");
        }
    }

    @ReactMethod
    private void requestInstallData(Promise promise) {
        Intent intent = new Intent();
        intent.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
        try {
            getCurrentActivity().startActivity(intent);
            promise.resolve("success");
        } catch (ActivityNotFoundException unused) {
            promise.reject("no_engine", "No TTS engine installed");
        }
    }

    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        TextToSpeech textToSpeech = this.tts;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.tts.shutdown();
        }
    }

    private boolean notReady(Promise promise) {
        Boolean bool = this.ready;
        if (bool == null) {
            promise.reject("not_ready", "TTS is not ready");
            return true;
        } else if (bool == Boolean.TRUE) {
            return false;
        } else {
            resolveReadyPromise(promise);
            return true;
        }
    }

    private int speak(String str, String str2, ReadableMap readableMap) {
        String string = readableMap.hasKey("KEY_PARAM_STREAM") ? readableMap.getString("KEY_PARAM_STREAM") : "";
        float f = readableMap.hasKey("KEY_PARAM_VOLUME") ? (float) readableMap.getDouble("KEY_PARAM_VOLUME") : 1.0f;
        float f2 = readableMap.hasKey("KEY_PARAM_PAN") ? (float) readableMap.getDouble("KEY_PARAM_PAN") : 0.0f;
        char c = 65535;
        int i = 0;
        switch (string.hashCode()) {
            case -1192558710:
                if (string.equals("STREAM_VOICE_CALL")) {
                    c = 6;
                    break;
                }
                break;
            case -1048950966:
                if (string.equals("STREAM_NOTIFICATION")) {
                    c = 3;
                    break;
                }
                break;
            case -485436536:
                if (string.equals("STREAM_DTMF")) {
                    c = 1;
                    break;
                }
                break;
            case -485030001:
                if (string.equals("STREAM_RING")) {
                    c = 4;
                    break;
                }
                break;
            case 2081173454:
                if (string.equals("STREAM_SYSTEM")) {
                    c = 5;
                    break;
                }
                break;
            case 2128316594:
                if (string.equals("STREAM_ALARM")) {
                    c = 0;
                    break;
                }
                break;
            case 2139683974:
                if (string.equals("STREAM_MUSIC")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                i = 4;
                break;
            case 1:
                i = 8;
                break;
            case 2:
                i = 3;
                break;
            case 3:
                i = 5;
                break;
            case 4:
                i = 2;
                break;
            case 5:
                i = 1;
                break;
            case 6:
                break;
            default:
                i = Integer.MIN_VALUE;
                break;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Bundle bundle = new Bundle();
            bundle.putInt("streamType", i);
            bundle.putFloat("volume", f);
            bundle.putFloat("pan", f2);
            return this.tts.speak(str, 1, bundle, str2);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("utteranceId", str2);
        hashMap.put("streamType", String.valueOf(i));
        hashMap.put("volume", String.valueOf(f));
        hashMap.put("pan", String.valueOf(f2));
        return this.tts.speak(str, 1, hashMap);
    }

    /* access modifiers changed from: private */
    public void sendEvent(String str, String str2) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("utteranceId", str2);
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, createMap);
    }
}
