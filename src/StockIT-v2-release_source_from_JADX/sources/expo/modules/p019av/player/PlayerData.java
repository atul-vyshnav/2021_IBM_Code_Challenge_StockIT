package expo.modules.p019av.player;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.Surface;
import expo.modules.p019av.AVManagerInterface;
import expo.modules.p019av.AudioEventHandler;
import expo.modules.p019av.AudioFocusNotAcquiredException;
import expo.modules.p019av.progress.AndroidLooperTimeMachine;
import expo.modules.p019av.progress.ProgressLooper;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.unimodules.core.Promise;
import org.unimodules.core.arguments.ReadableArguments;

/* renamed from: expo.modules.av.player.PlayerData */
public abstract class PlayerData implements AudioEventHandler {
    static final String STATUS_ANDROID_IMPLEMENTATION_KEY_PATH = "androidImplementation";
    static final String STATUS_DID_JUST_FINISH_KEY_PATH = "didJustFinish";
    static final String STATUS_DURATION_MILLIS_KEY_PATH = "durationMillis";
    static final String STATUS_HEADERS_KEY_PATH = "headers";
    static final String STATUS_IS_BUFFERING_KEY_PATH = "isBuffering";
    static final String STATUS_IS_LOADED_KEY_PATH = "isLoaded";
    static final String STATUS_IS_LOOPING_KEY_PATH = "isLooping";
    static final String STATUS_IS_MUTED_KEY_PATH = "isMuted";
    public static final String STATUS_IS_PLAYING_KEY_PATH = "isPlaying";
    static final String STATUS_OVERRIDING_EXTENSION_KEY_PATH = "overridingExtension";
    static final String STATUS_PLAYABLE_DURATION_MILLIS_KEY_PATH = "playableDurationMillis";
    static final String STATUS_POSITION_MILLIS_KEY_PATH = "positionMillis";
    static final String STATUS_PROGRESS_UPDATE_INTERVAL_MILLIS_KEY_PATH = "progressUpdateIntervalMillis";
    static final String STATUS_RATE_KEY_PATH = "rate";
    static final String STATUS_SHOULD_CORRECT_PITCH_KEY_PATH = "shouldCorrectPitch";
    static final String STATUS_SHOULD_PLAY_KEY_PATH = "shouldPlay";
    public static final String STATUS_URI_KEY_PATH = "uri";
    static final String STATUS_VOLUME_KEY_PATH = "volume";
    final AVManagerInterface mAVModule;
    ErrorListener mErrorListener = null;
    private FullscreenPresenter mFullscreenPresenter = null;
    boolean mIsMuted = false;
    private int mProgressUpdateIntervalMillis = 500;
    private ProgressLooper mProgressUpdater = new ProgressLooper(new AndroidLooperTimeMachine());
    float mRate = 1.0f;
    final Map<String, Object> mRequestHeaders;
    boolean mShouldCorrectPitch = false;
    boolean mShouldPlay = false;
    private StatusUpdateListener mStatusUpdateListener = null;
    final Uri mUri;
    VideoSizeUpdateListener mVideoSizeUpdateListener = null;
    float mVolume = 1.0f;

    /* renamed from: expo.modules.av.player.PlayerData$ErrorListener */
    public interface ErrorListener {
        void onError(String str);
    }

    /* renamed from: expo.modules.av.player.PlayerData$FullscreenPresenter */
    public interface FullscreenPresenter {
        boolean isBeingPresentedFullscreen();

        void setFullscreenMode(boolean z);
    }

    /* renamed from: expo.modules.av.player.PlayerData$LoadCompletionListener */
    public interface LoadCompletionListener {
        void onLoadError(String str);

        void onLoadSuccess(Bundle bundle);
    }

    /* renamed from: expo.modules.av.player.PlayerData$SetStatusCompletionListener */
    interface SetStatusCompletionListener {
        void onSetStatusComplete();

        void onSetStatusError(String str);
    }

    /* renamed from: expo.modules.av.player.PlayerData$StatusUpdateListener */
    public interface StatusUpdateListener {
        void onStatusUpdate(Bundle bundle);
    }

    /* renamed from: expo.modules.av.player.PlayerData$VideoSizeUpdateListener */
    public interface VideoSizeUpdateListener {
        void onVideoSizeUpdate(Pair<Integer, Integer> pair);
    }

    /* access modifiers changed from: package-private */
    public abstract void applyNewStatus(Integer num, Boolean bool) throws AudioFocusNotAcquiredException, IllegalStateException;

    /* access modifiers changed from: package-private */
    public abstract int getAudioSessionId();

    /* access modifiers changed from: package-private */
    public abstract void getExtraStatusFields(Bundle bundle);

    /* access modifiers changed from: package-private */
    public abstract String getImplementationName();

    public abstract Pair<Integer, Integer> getVideoWidthHeight();

    /* access modifiers changed from: package-private */
    public abstract boolean isLoaded();

    public abstract void load(Bundle bundle, LoadCompletionListener loadCompletionListener);

    /* access modifiers changed from: package-private */
    public abstract void playPlayerWithRateAndMuteIfNecessary() throws AudioFocusNotAcquiredException;

    public abstract void release();

    /* access modifiers changed from: package-private */
    public abstract boolean shouldContinueUpdatingProgress();

    public abstract void tryUpdateVideoSurface(Surface surface);

    public static Bundle getUnloadedStatus() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(STATUS_IS_LOADED_KEY_PATH, false);
        return bundle;
    }

    PlayerData(AVManagerInterface aVManagerInterface, Uri uri, Map<String, Object> map) {
        this.mRequestHeaders = map;
        this.mAVModule = aVManagerInterface;
        this.mUri = uri;
    }

    public static PlayerData createUnloadedPlayerData(AVManagerInterface aVManagerInterface, Context context, ReadableArguments readableArguments, Bundle bundle) {
        String string = readableArguments.getString("uri");
        String str = null;
        Map map = readableArguments.containsKey(STATUS_HEADERS_KEY_PATH) ? readableArguments.getMap(STATUS_HEADERS_KEY_PATH) : null;
        if (readableArguments.containsKey(STATUS_OVERRIDING_EXTENSION_KEY_PATH)) {
            str = readableArguments.getString(STATUS_OVERRIDING_EXTENSION_KEY_PATH);
        }
        String str2 = str;
        Uri parse = Uri.parse(string);
        if (!bundle.containsKey(STATUS_ANDROID_IMPLEMENTATION_KEY_PATH) || !bundle.getString(STATUS_ANDROID_IMPLEMENTATION_KEY_PATH).equals("MediaPlayer")) {
            return new SimpleExoPlayerData(aVManagerInterface, context, parse, str2, map);
        }
        return new MediaPlayerData(aVManagerInterface, context, parse, map);
    }

    private void callStatusUpdateListenerWithStatus(Bundle bundle) {
        StatusUpdateListener statusUpdateListener = this.mStatusUpdateListener;
        if (statusUpdateListener != null) {
            statusUpdateListener.onStatusUpdate(bundle);
        }
    }

    /* access modifiers changed from: package-private */
    public final void callStatusUpdateListenerWithDidJustFinish() {
        Bundle status = getStatus();
        status.putBoolean(STATUS_DID_JUST_FINISH_KEY_PATH, true);
        callStatusUpdateListenerWithStatus(status);
    }

    /* access modifiers changed from: package-private */
    public final void callStatusUpdateListener() {
        callStatusUpdateListenerWithStatus(getStatus());
    }

    /* access modifiers changed from: package-private */
    public final void stopUpdatingProgressIfNecessary() {
        this.mProgressUpdater.stopLooping();
    }

    /* access modifiers changed from: package-private */
    public final void beginUpdatingProgressIfNecessary() {
        if (shouldContinueUpdatingProgress() && !this.mProgressUpdater.isLooping() && this.mStatusUpdateListener != null) {
            this.mProgressUpdater.loop((long) this.mProgressUpdateIntervalMillis, new Function0() {
                public final Object invoke() {
                    return PlayerData.this.lambda$beginUpdatingProgressIfNecessary$0$PlayerData();
                }
            });
        }
    }

    public /* synthetic */ Unit lambda$beginUpdatingProgressIfNecessary$0$PlayerData() {
        callStatusUpdateListener();
        return null;
    }

    public final void setStatusUpdateListener(StatusUpdateListener statusUpdateListener) {
        StatusUpdateListener statusUpdateListener2 = this.mStatusUpdateListener;
        this.mStatusUpdateListener = statusUpdateListener;
        if (statusUpdateListener != null) {
            beginUpdatingProgressIfNecessary();
            if (statusUpdateListener2 == null) {
                callStatusUpdateListener();
                return;
            }
            return;
        }
        stopUpdatingProgressIfNecessary();
    }

    public final void setErrorListener(ErrorListener errorListener) {
        this.mErrorListener = errorListener;
    }

    /* access modifiers changed from: package-private */
    public final boolean shouldPlayerPlay() {
        return this.mShouldPlay && ((double) this.mRate) > 0.0d;
    }

    /* access modifiers changed from: package-private */
    public final void setStatusWithListener(Bundle bundle, SetStatusCompletionListener setStatusCompletionListener) {
        if (bundle.containsKey(STATUS_PROGRESS_UPDATE_INTERVAL_MILLIS_KEY_PATH) && this.mProgressUpdateIntervalMillis != ((int) bundle.getDouble(STATUS_PROGRESS_UPDATE_INTERVAL_MILLIS_KEY_PATH))) {
            this.mProgressUpdateIntervalMillis = (int) bundle.getDouble(STATUS_PROGRESS_UPDATE_INTERVAL_MILLIS_KEY_PATH);
            if (this.mProgressUpdater.isLooping()) {
                stopUpdatingProgressIfNecessary();
                beginUpdatingProgressIfNecessary();
            }
        }
        Boolean bool = null;
        Integer valueOf = bundle.containsKey(STATUS_POSITION_MILLIS_KEY_PATH) ? Integer.valueOf((int) bundle.getDouble(STATUS_POSITION_MILLIS_KEY_PATH)) : null;
        if (bundle.containsKey(STATUS_SHOULD_PLAY_KEY_PATH)) {
            this.mShouldPlay = bundle.getBoolean(STATUS_SHOULD_PLAY_KEY_PATH);
        }
        if (bundle.containsKey(STATUS_RATE_KEY_PATH)) {
            this.mRate = (float) bundle.getDouble(STATUS_RATE_KEY_PATH);
        }
        if (bundle.containsKey(STATUS_SHOULD_CORRECT_PITCH_KEY_PATH)) {
            this.mShouldCorrectPitch = bundle.getBoolean(STATUS_SHOULD_CORRECT_PITCH_KEY_PATH);
        }
        if (bundle.containsKey(STATUS_VOLUME_KEY_PATH)) {
            this.mVolume = (float) bundle.getDouble(STATUS_VOLUME_KEY_PATH);
        }
        if (bundle.containsKey(STATUS_IS_MUTED_KEY_PATH)) {
            this.mIsMuted = bundle.getBoolean(STATUS_IS_MUTED_KEY_PATH);
        }
        if (bundle.containsKey(STATUS_IS_LOOPING_KEY_PATH)) {
            bool = Boolean.valueOf(bundle.getBoolean(STATUS_IS_LOOPING_KEY_PATH));
        }
        try {
            applyNewStatus(valueOf, bool);
            this.mAVModule.abandonAudioFocusIfUnused();
            setStatusCompletionListener.onSetStatusComplete();
        } catch (Throwable th) {
            this.mAVModule.abandonAudioFocusIfUnused();
            setStatusCompletionListener.onSetStatusError(th.toString());
        }
    }

    public final void setStatus(Bundle bundle, final Promise promise) {
        if (bundle != null) {
            try {
                setStatusWithListener(bundle, new SetStatusCompletionListener() {
                    public void onSetStatusComplete() {
                        Promise promise = promise;
                        if (promise == null) {
                            PlayerData.this.callStatusUpdateListener();
                        } else {
                            promise.resolve(PlayerData.this.getStatus());
                        }
                    }

                    public void onSetStatusError(String str) {
                        Promise promise = promise;
                        if (promise == null) {
                            PlayerData.this.callStatusUpdateListener();
                        } else {
                            promise.reject("E_AV_SETSTATUS", str);
                        }
                    }
                });
            } catch (Throwable th) {
                if (promise != null) {
                    promise.reject("E_AV_SETSTATUS", "Encountered an error while setting status!", th);
                }
            }
        } else if (promise != null) {
            promise.reject("E_AV_SETSTATUS", "Cannot set null status.");
        }
    }

    /* access modifiers changed from: package-private */
    public final int getClippedIntegerForValue(Integer num, Integer num2, Integer num3) {
        if (num2 != null && num.intValue() < num2.intValue()) {
            num = num2;
        } else if (num3 != null && num.intValue() > num3.intValue()) {
            num = num3;
        }
        return num.intValue();
    }

    public final synchronized Bundle getStatus() {
        if (!isLoaded()) {
            Bundle unloadedStatus = getUnloadedStatus();
            unloadedStatus.putString(STATUS_ANDROID_IMPLEMENTATION_KEY_PATH, getImplementationName());
            return unloadedStatus;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(STATUS_IS_LOADED_KEY_PATH, true);
        bundle.putString(STATUS_ANDROID_IMPLEMENTATION_KEY_PATH, getImplementationName());
        bundle.putString("uri", this.mUri.getPath());
        bundle.putInt(STATUS_PROGRESS_UPDATE_INTERVAL_MILLIS_KEY_PATH, this.mProgressUpdateIntervalMillis);
        bundle.putBoolean(STATUS_SHOULD_PLAY_KEY_PATH, this.mShouldPlay);
        bundle.putDouble(STATUS_RATE_KEY_PATH, (double) this.mRate);
        bundle.putBoolean(STATUS_SHOULD_CORRECT_PITCH_KEY_PATH, this.mShouldCorrectPitch);
        bundle.putDouble(STATUS_VOLUME_KEY_PATH, (double) this.mVolume);
        bundle.putBoolean(STATUS_IS_MUTED_KEY_PATH, this.mIsMuted);
        bundle.putBoolean(STATUS_DID_JUST_FINISH_KEY_PATH, false);
        getExtraStatusFields(bundle);
        return bundle;
    }

    public final void setVideoSizeUpdateListener(VideoSizeUpdateListener videoSizeUpdateListener) {
        this.mVideoSizeUpdateListener = videoSizeUpdateListener;
    }

    public final void setFullscreenPresenter(FullscreenPresenter fullscreenPresenter) {
        this.mFullscreenPresenter = fullscreenPresenter;
    }

    public boolean isPresentedFullscreen() {
        return this.mFullscreenPresenter.isBeingPresentedFullscreen();
    }

    public void toggleFullscreen() {
        this.mFullscreenPresenter.setFullscreenMode(!isPresentedFullscreen());
    }

    public final void handleAudioFocusInterruptionBegan() {
        if (!this.mIsMuted) {
            pauseImmediately();
        }
    }

    public final void handleAudioFocusGained() {
        try {
            playPlayerWithRateAndMuteIfNecessary();
        } catch (AudioFocusNotAcquiredException unused) {
        }
    }

    public final void onPause() {
        pauseImmediately();
    }

    public final void onResume() {
        try {
            playPlayerWithRateAndMuteIfNecessary();
        } catch (AudioFocusNotAcquiredException unused) {
        }
    }
}
