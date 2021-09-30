package com.zmxv.RNSound;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.exoplayer2.util.MimeTypes;
import com.onesignal.OneSignalDbContract;
import expo.modules.p019av.player.PlayerData;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RNSoundModule extends ReactContextBaseJavaModule implements AudioManager.OnAudioFocusChangeListener {
    static final Object NULL = null;
    String category;
    ReactApplicationContext context;
    Double focusedPlayerKey;
    Boolean mixWithOthers = true;
    Map<Double, MediaPlayer> playerPool = new HashMap();
    Boolean wasPlayingBeforeFocusChange = false;

    @ReactMethod
    public void enable(Boolean bool) {
    }

    public String getName() {
        return "RNSound";
    }

    public RNSoundModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.context = reactApplicationContext;
        this.category = null;
    }

    /* access modifiers changed from: private */
    public void setOnPlay(boolean z, Double d) {
        ReactApplicationContext reactApplicationContext = this.context;
        WritableMap createMap = Arguments.createMap();
        createMap.putBoolean(PlayerData.STATUS_IS_PLAYING_KEY_PATH, z);
        createMap.putDouble("playerKey", d.doubleValue());
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("onPlayChange", createMap);
    }

    @ReactMethod
    public void prepare(String str, Double d, ReadableMap readableMap, final Callback callback) {
        MediaPlayer createMediaPlayer = createMediaPlayer(str);
        char c = 65535;
        if (createMediaPlayer == null) {
            WritableMap createMap = Arguments.createMap();
            createMap.putInt("code", -1);
            createMap.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, "resource not found");
            callback.invoke(createMap, NULL);
            return;
        }
        this.playerPool.put(d, createMediaPlayer);
        String str2 = this.category;
        if (str2 != null) {
            Integer num = null;
            switch (str2.hashCode()) {
                case -1803461041:
                    if (str2.equals("System")) {
                        c = 2;
                        break;
                    }
                    break;
                case 2547280:
                    if (str2.equals("Ring")) {
                        c = 4;
                        break;
                    }
                    break;
                case 63343153:
                    if (str2.equals("Alarm")) {
                        c = 5;
                        break;
                    }
                    break;
                case 82833682:
                    if (str2.equals("Voice")) {
                        c = 3;
                        break;
                    }
                    break;
                case 772508280:
                    if (str2.equals("Ambient")) {
                        c = 1;
                        break;
                    }
                    break;
                case 1943812667:
                    if (str2.equals("Playback")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                num = 3;
            } else if (c == 1) {
                num = 5;
            } else if (c == 2) {
                num = 1;
            } else if (c == 3) {
                num = 0;
            } else if (c == 4) {
                num = 2;
            } else if (c != 5) {
                Log.e("RNSoundModule", String.format("Unrecognised category %s", new Object[]{this.category}));
            } else {
                num = 4;
            }
            if (num != null) {
                createMediaPlayer.setAudioStreamType(num.intValue());
            }
        }
        createMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            boolean callbackWasCalled = false;

            public synchronized void onPrepared(MediaPlayer mediaPlayer) {
                if (!this.callbackWasCalled) {
                    this.callbackWasCalled = true;
                    WritableMap createMap = Arguments.createMap();
                    createMap.putDouble("duration", ((double) mediaPlayer.getDuration()) * 0.001d);
                    try {
                        callback.invoke(RNSoundModule.NULL, createMap);
                    } catch (RuntimeException e) {
                        Log.e("RNSoundModule", "Exception", e);
                    }
                } else {
                    return;
                }
                return;
            }
        });
        createMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            boolean callbackWasCalled = false;

            public synchronized boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                if (this.callbackWasCalled) {
                    return true;
                }
                this.callbackWasCalled = true;
                try {
                    WritableMap createMap = Arguments.createMap();
                    createMap.putInt("what", i);
                    createMap.putInt("extra", i2);
                    callback.invoke(createMap, RNSoundModule.NULL);
                } catch (RuntimeException e) {
                    Log.e("RNSoundModule", "Exception", e);
                }
                return true;
            }
        });
        try {
            if (!readableMap.hasKey("loadSync") || !readableMap.getBoolean("loadSync")) {
                createMediaPlayer.prepareAsync();
            } else {
                createMediaPlayer.prepare();
            }
        } catch (Exception e) {
            Log.e("RNSoundModule", "Exception", e);
        }
    }

    /* access modifiers changed from: protected */
    public MediaPlayer createMediaPlayer(String str) {
        int identifier = this.context.getResources().getIdentifier(str, "raw", this.context.getPackageName());
        MediaPlayer mediaPlayer = new MediaPlayer();
        if (identifier != 0) {
            try {
                AssetFileDescriptor openRawResourceFd = this.context.getResources().openRawResourceFd(identifier);
                mediaPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
                openRawResourceFd.close();
                return mediaPlayer;
            } catch (IOException e) {
                Log.e("RNSoundModule", "Exception", e);
                return null;
            }
        } else if (str.startsWith("http://") || str.startsWith("https://")) {
            mediaPlayer.setAudioStreamType(3);
            Log.i("RNSoundModule", str);
            try {
                mediaPlayer.setDataSource(str);
                return mediaPlayer;
            } catch (IOException e2) {
                Log.e("RNSoundModule", "Exception", e2);
                return null;
            }
        } else if (str.startsWith("asset:/")) {
            try {
                AssetFileDescriptor openFd = this.context.getAssets().openFd(str.replace("asset:/", ""));
                mediaPlayer.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                openFd.close();
                return mediaPlayer;
            } catch (IOException e3) {
                Log.e("RNSoundModule", "Exception", e3);
                return null;
            }
        } else {
            if (new File(str).exists()) {
                mediaPlayer.setAudioStreamType(3);
                Log.i("RNSoundModule", str);
                try {
                    mediaPlayer.setDataSource(str);
                    return mediaPlayer;
                } catch (IOException e4) {
                    Log.e("RNSoundModule", "Exception", e4);
                }
            }
            return null;
        }
    }

    @ReactMethod
    public void play(final Double d, final Callback callback) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer == null) {
            setOnPlay(false, d);
            if (callback != null) {
                callback.invoke(false);
            }
        } else if (!mediaPlayer.isPlaying()) {
            if (!this.mixWithOthers.booleanValue()) {
                ((AudioManager) this.context.getSystemService(MimeTypes.BASE_TYPE_AUDIO)).requestAudioFocus(this, 3, 1);
                this.focusedPlayerKey = d;
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                boolean callbackWasCalled = false;

                public synchronized void onCompletion(MediaPlayer mediaPlayer) {
                    if (!mediaPlayer.isLooping()) {
                        RNSoundModule.this.setOnPlay(false, d);
                        if (!this.callbackWasCalled) {
                            this.callbackWasCalled = true;
                            try {
                                callback.invoke(true);
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                boolean callbackWasCalled = false;

                public synchronized boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    RNSoundModule.this.setOnPlay(false, d);
                    if (this.callbackWasCalled) {
                        return true;
                    }
                    this.callbackWasCalled = true;
                    try {
                        callback.invoke(true);
                    } catch (Exception unused) {
                    }
                    return true;
                }
            });
            mediaPlayer.start();
            setOnPlay(true, d);
        }
    }

    @ReactMethod
    public void pause(Double d, Callback callback) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        if (callback != null) {
            callback.invoke(new Object[0]);
        }
    }

    @ReactMethod
    public void stop(Double d, Callback callback) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
        if (!this.mixWithOthers.booleanValue() && d == this.focusedPlayerKey) {
            ((AudioManager) this.context.getSystemService(MimeTypes.BASE_TYPE_AUDIO)).abandonAudioFocus(this);
        }
        callback.invoke(new Object[0]);
    }

    @ReactMethod
    public void reset(Double d) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }

    @ReactMethod
    public void release(Double d) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            this.playerPool.remove(d);
            if (!this.mixWithOthers.booleanValue() && d == this.focusedPlayerKey) {
                ((AudioManager) this.context.getSystemService(MimeTypes.BASE_TYPE_AUDIO)).abandonAudioFocus(this);
            }
        }
    }

    public void onCatalystInstanceDestroy() {
        Iterator<Map.Entry<Double, MediaPlayer>> it = this.playerPool.entrySet().iterator();
        while (it.hasNext()) {
            MediaPlayer mediaPlayer = (MediaPlayer) it.next().getValue();
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
            it.remove();
        }
    }

    @ReactMethod
    public void setVolume(Double d, Float f, Float f2) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(f.floatValue(), f2.floatValue());
        }
    }

    @ReactMethod
    public void getSystemVolume(Callback callback) {
        try {
            AudioManager audioManager = (AudioManager) this.context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            callback.invoke(Float.valueOf(((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3))));
        } catch (Exception e) {
            WritableMap createMap = Arguments.createMap();
            createMap.putInt("code", -1);
            createMap.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, e.getMessage());
            callback.invoke(createMap);
        }
    }

    @ReactMethod
    public void setSystemVolume(Float f) {
        AudioManager audioManager = (AudioManager) this.context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        audioManager.setStreamVolume(3, Math.round(((float) audioManager.getStreamMaxVolume(3)) * f.floatValue()), 0);
    }

    @ReactMethod
    public void setLooping(Double d, Boolean bool) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(bool.booleanValue());
        }
    }

    @ReactMethod
    public void setSpeed(Double d, Float f) {
        if (Build.VERSION.SDK_INT < 23) {
            Log.w("RNSoundModule", "setSpeed ignored due to sdk limit");
            return;
        }
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(f.floatValue()));
        }
    }

    @ReactMethod
    public void setCurrentTime(Double d, Float f) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(Math.round(f.floatValue() * 1000.0f));
        }
    }

    @ReactMethod
    public void getCurrentTime(Double d, Callback callback) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer == null) {
            callback.invoke(-1, false);
            return;
        }
        callback.invoke(Double.valueOf(((double) mediaPlayer.getCurrentPosition()) * 0.001d), Boolean.valueOf(mediaPlayer.isPlaying()));
    }

    @ReactMethod
    public void setSpeakerphoneOn(Double d, Boolean bool) {
        MediaPlayer mediaPlayer = this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.setAudioStreamType(3);
            AudioManager audioManager = (AudioManager) this.context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            if (bool.booleanValue()) {
                audioManager.setMode(3);
            } else {
                audioManager.setMode(0);
            }
            audioManager.setSpeakerphoneOn(bool.booleanValue());
        }
    }

    @ReactMethod
    public void setCategory(String str, Boolean bool) {
        this.category = str;
        this.mixWithOthers = bool;
    }

    public void onAudioFocusChange(int i) {
        MediaPlayer mediaPlayer;
        if (!this.mixWithOthers.booleanValue() && (mediaPlayer = this.playerPool.get(this.focusedPlayerKey)) != null) {
            if (i <= 0) {
                Boolean valueOf = Boolean.valueOf(mediaPlayer.isPlaying());
                this.wasPlayingBeforeFocusChange = valueOf;
                if (valueOf.booleanValue()) {
                    pause(this.focusedPlayerKey, (Callback) null);
                }
            } else if (this.wasPlayingBeforeFocusChange.booleanValue()) {
                play(this.focusedPlayerKey, (Callback) null);
                this.wasPlayingBeforeFocusChange = false;
            }
        }
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        hashMap.put("IsAndroid", true);
        return hashMap;
    }
}
