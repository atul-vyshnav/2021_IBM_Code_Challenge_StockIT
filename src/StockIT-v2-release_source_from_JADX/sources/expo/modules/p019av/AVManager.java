package expo.modules.p019av;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import com.google.android.exoplayer2.util.MimeTypes;
import expo.modules.p019av.player.PlayerData;
import expo.modules.p019av.video.VideoView;
import expo.modules.p019av.video.VideoViewWrapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.arguments.ReadableArguments;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.core.interfaces.services.EventEmitter;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.permissions.Permissions;

/* renamed from: expo.modules.av.AVManager */
public class AVManager implements LifecycleEventListener, AudioManager.OnAudioFocusChangeListener, MediaRecorder.OnInfoListener, AVManagerInterface, InternalModule {
    private static final String AUDIO_MODE_INTERRUPTION_MODE_KEY = "interruptionModeAndroid";
    private static final String AUDIO_MODE_PLAY_THROUGH_EARPIECE = "playThroughEarpieceAndroid";
    private static final String AUDIO_MODE_SHOULD_DUCK_KEY = "shouldDuckAndroid";
    private static final String AUDIO_MODE_STAYS_ACTIVE_IN_BACKGROUND = "staysActiveInBackground";
    private static final String RECORDING_OPTIONS_KEY = "android";
    private static final String RECORDING_OPTION_AUDIO_ENCODER_KEY = "audioEncoder";
    private static final String RECORDING_OPTION_BIT_RATE_KEY = "bitRate";
    private static final String RECORDING_OPTION_EXTENSION_KEY = "extension";
    private static final String RECORDING_OPTION_IS_METERING_ENABLED_KEY = "isMeteringEnabled";
    private static final String RECORDING_OPTION_MAX_FILE_SIZE_KEY = "maxFileSize";
    private static final String RECORDING_OPTION_NUMBER_OF_CHANNELS_KEY = "numberOfChannels";
    private static final String RECORDING_OPTION_OUTPUT_FORMAT_KEY = "outputFormat";
    private static final String RECORDING_OPTION_SAMPLE_RATE_KEY = "sampleRate";
    private boolean mAcquiredAudioFocus = false;
    private boolean mAppIsPaused = false;
    private AudioInterruptionMode mAudioInterruptionMode = AudioInterruptionMode.DUCK_OTHERS;
    private final AudioManager mAudioManager;
    private MediaRecorder mAudioRecorder = null;
    private long mAudioRecorderDurationAlreadyRecorded = 0;
    private boolean mAudioRecorderIsMeteringEnabled = false;
    private boolean mAudioRecorderIsPaused = false;
    private boolean mAudioRecorderIsRecording = false;
    private long mAudioRecorderUptimeOfLastStartResume = 0;
    private String mAudioRecordingFilePath = null;
    private final Context mContext;
    private boolean mEnabled = true;
    private boolean mIsDuckingAudio = false;
    private boolean mIsRegistered = false;
    private ModuleRegistry mModuleRegistry;
    private final BroadcastReceiver mNoisyAudioStreamReceiver;
    private boolean mShouldDuckAudio = true;
    private boolean mShouldRouteThroughEarpiece = false;
    /* access modifiers changed from: private */
    public final Map<Integer, PlayerData> mSoundMap = new HashMap();
    private int mSoundMapKeyCount = 0;
    private boolean mStaysActiveInBackground = false;
    private final Set<VideoView> mVideoViewSet = new HashSet();

    /* renamed from: expo.modules.av.AVManager$AudioInterruptionMode */
    private enum AudioInterruptionMode {
        DO_NOT_MIX,
        DUCK_OTHERS
    }

    /* renamed from: expo.modules.av.AVManager$VideoViewCallback */
    private interface VideoViewCallback {
        void runWithVideoView(VideoView videoView);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public AVManager(Context context) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        C29141 r5 = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if ("android.media.AUDIO_BECOMING_NOISY".equals(intent.getAction())) {
                    AVManager.this.abandonAudioFocus();
                }
            }
        };
        this.mNoisyAudioStreamReceiver = r5;
        this.mContext.registerReceiver(r5, new IntentFilter("android.media.AUDIO_BECOMING_NOISY"));
        this.mIsRegistered = true;
    }

    public ModuleRegistry getModuleRegistry() {
        return this.mModuleRegistry;
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        ModuleRegistry moduleRegistry2 = this.mModuleRegistry;
        if (moduleRegistry2 != null) {
            ((UIManager) moduleRegistry2.getModule(UIManager.class)).unregisterLifecycleEventListener(this);
        }
        this.mModuleRegistry = moduleRegistry;
        if (moduleRegistry != null) {
            ((UIManager) moduleRegistry.getModule(UIManager.class)).registerLifecycleEventListener(this);
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(AVManagerInterface.class);
    }

    /* access modifiers changed from: private */
    public void sendEvent(String str, Bundle bundle) {
        EventEmitter eventEmitter;
        ModuleRegistry moduleRegistry = this.mModuleRegistry;
        if (moduleRegistry != null && (eventEmitter = (EventEmitter) moduleRegistry.getModule(EventEmitter.class)) != null) {
            eventEmitter.emit(str, bundle);
        }
    }

    public void onHostResume() {
        if (this.mAppIsPaused) {
            this.mAppIsPaused = false;
            if (!this.mStaysActiveInBackground) {
                for (AudioEventHandler onResume : getAllRegisteredAudioEventHandlers()) {
                    onResume.onResume();
                }
                if (this.mShouldRouteThroughEarpiece) {
                    updatePlaySoundThroughEarpiece(true);
                }
            }
        }
    }

    public void onHostPause() {
        if (!this.mAppIsPaused) {
            this.mAppIsPaused = true;
            if (!this.mStaysActiveInBackground) {
                for (AudioEventHandler onPause : getAllRegisteredAudioEventHandlers()) {
                    onPause.onPause();
                }
                abandonAudioFocus();
                if (this.mShouldRouteThroughEarpiece) {
                    updatePlaySoundThroughEarpiece(false);
                }
            }
        }
    }

    public void onHostDestroy() {
        if (this.mIsRegistered) {
            this.mContext.unregisterReceiver(this.mNoisyAudioStreamReceiver);
            this.mIsRegistered = false;
        }
        Iterator<PlayerData> it = this.mSoundMap.values().iterator();
        while (it.hasNext()) {
            PlayerData next = it.next();
            it.remove();
            if (next != null) {
                next.release();
            }
        }
        for (VideoView unloadPlayerAndMediaController : this.mVideoViewSet) {
            unloadPlayerAndMediaController.unloadPlayerAndMediaController();
        }
        removeAudioRecorder();
        abandonAudioFocus();
    }

    public void registerVideoViewForAudioLifecycle(VideoView videoView) {
        this.mVideoViewSet.add(videoView);
    }

    public void unregisterVideoViewForAudioLifecycle(VideoView videoView) {
        this.mVideoViewSet.remove(videoView);
    }

    private Set<AudioEventHandler> getAllRegisteredAudioEventHandlers() {
        HashSet hashSet = new HashSet();
        hashSet.addAll(this.mVideoViewSet);
        hashSet.addAll(this.mSoundMap.values());
        return hashSet;
    }

    public void onAudioFocusChange(int i) {
        if (i != -3) {
            if (!(i == -2 || i == -1)) {
                if (i == 1) {
                    this.mIsDuckingAudio = false;
                    this.mAcquiredAudioFocus = true;
                    for (AudioEventHandler handleAudioFocusGained : getAllRegisteredAudioEventHandlers()) {
                        handleAudioFocusGained.handleAudioFocusGained();
                    }
                    return;
                }
                return;
            }
        } else if (this.mShouldDuckAudio) {
            this.mIsDuckingAudio = true;
            this.mAcquiredAudioFocus = true;
            updateDuckStatusForAllPlayersPlaying();
            return;
        }
        this.mIsDuckingAudio = false;
        this.mAcquiredAudioFocus = false;
        for (AudioEventHandler handleAudioFocusInterruptionBegan : getAllRegisteredAudioEventHandlers()) {
            handleAudioFocusInterruptionBegan.handleAudioFocusInterruptionBegan();
        }
    }

    public void acquireAudioFocus() throws AudioFocusNotAcquiredException {
        if (!this.mEnabled) {
            throw new AudioFocusNotAcquiredException("Expo Audio is disabled, so audio focus could not be acquired.");
        } else if (this.mAppIsPaused && !this.mStaysActiveInBackground) {
            throw new AudioFocusNotAcquiredException("This experience is currently in the background, so audio focus could not be acquired.");
        } else if (!this.mAcquiredAudioFocus) {
            boolean z = true;
            if (this.mAudioManager.requestAudioFocus(this, 3, this.mAudioInterruptionMode == AudioInterruptionMode.DO_NOT_MIX ? 1 : 3) != 1) {
                z = false;
            }
            this.mAcquiredAudioFocus = z;
            if (!z) {
                throw new AudioFocusNotAcquiredException("Audio focus could not be acquired from the OS at this time.");
            }
        }
    }

    /* access modifiers changed from: private */
    public void abandonAudioFocus() {
        for (AudioEventHandler next : getAllRegisteredAudioEventHandlers()) {
            if (next.requiresAudioFocus()) {
                next.pauseImmediately();
            }
        }
        this.mAcquiredAudioFocus = false;
        this.mAudioManager.abandonAudioFocus(this);
    }

    public void abandonAudioFocusIfUnused() {
        for (AudioEventHandler requiresAudioFocus : getAllRegisteredAudioEventHandlers()) {
            if (requiresAudioFocus.requiresAudioFocus()) {
                return;
            }
        }
        abandonAudioFocus();
    }

    public float getVolumeForDuckAndFocus(boolean z, float f) {
        if (!this.mAcquiredAudioFocus || z) {
            return 0.0f;
        }
        return this.mIsDuckingAudio ? f / 2.0f : f;
    }

    private void updateDuckStatusForAllPlayersPlaying() {
        for (AudioEventHandler updateVolumeMuteAndDuck : getAllRegisteredAudioEventHandlers()) {
            updateVolumeMuteAndDuck.updateVolumeMuteAndDuck();
        }
    }

    private void updatePlaySoundThroughEarpiece(boolean z) {
        this.mAudioManager.setMode(z ? 3 : 0);
        this.mAudioManager.setSpeakerphoneOn(!z);
    }

    public void setAudioIsEnabled(Boolean bool) {
        this.mEnabled = bool.booleanValue();
        if (!bool.booleanValue()) {
            abandonAudioFocus();
        }
    }

    public void setAudioMode(ReadableArguments readableArguments) {
        boolean z = readableArguments.getBoolean(AUDIO_MODE_SHOULD_DUCK_KEY);
        this.mShouldDuckAudio = z;
        if (!z) {
            this.mIsDuckingAudio = false;
            updateDuckStatusForAllPlayersPlaying();
        }
        if (readableArguments.containsKey(AUDIO_MODE_PLAY_THROUGH_EARPIECE)) {
            boolean z2 = readableArguments.getBoolean(AUDIO_MODE_PLAY_THROUGH_EARPIECE);
            this.mShouldRouteThroughEarpiece = z2;
            updatePlaySoundThroughEarpiece(z2);
        }
        if (readableArguments.getInt(AUDIO_MODE_INTERRUPTION_MODE_KEY) == 1) {
            this.mAudioInterruptionMode = AudioInterruptionMode.DO_NOT_MIX;
        }
        this.mAudioInterruptionMode = AudioInterruptionMode.DUCK_OTHERS;
        this.mStaysActiveInBackground = readableArguments.getBoolean(AUDIO_MODE_STAYS_ACTIVE_IN_BACKGROUND);
    }

    private PlayerData tryGetSoundForKey(Integer num, Promise promise) {
        PlayerData playerData = this.mSoundMap.get(num);
        if (playerData == null && promise != null) {
            promise.reject("E_AUDIO_NOPLAYER", "Player does not exist.");
        }
        return playerData;
    }

    /* access modifiers changed from: private */
    public void removeSoundForKey(Integer num) {
        PlayerData remove = this.mSoundMap.remove(num);
        if (remove != null) {
            remove.release();
            abandonAudioFocusIfUnused();
        }
    }

    public void loadForSound(ReadableArguments readableArguments, ReadableArguments readableArguments2, final Promise promise) {
        final int i = this.mSoundMapKeyCount;
        this.mSoundMapKeyCount = i + 1;
        PlayerData createUnloadedPlayerData = PlayerData.createUnloadedPlayerData(this, this.mContext, readableArguments, readableArguments2.toBundle());
        createUnloadedPlayerData.setErrorListener(new PlayerData.ErrorListener() {
            public void onError(String str) {
                AVManager.this.removeSoundForKey(Integer.valueOf(i));
            }
        });
        this.mSoundMap.put(Integer.valueOf(i), createUnloadedPlayerData);
        createUnloadedPlayerData.load(readableArguments2.toBundle(), new PlayerData.LoadCompletionListener() {
            public void onLoadSuccess(Bundle bundle) {
                promise.resolve(Arrays.asList(new Object[]{Integer.valueOf(i), bundle}));
            }

            public void onLoadError(String str) {
                AVManager.this.mSoundMap.remove(Integer.valueOf(i));
                promise.reject("E_LOAD_ERROR", str, (Throwable) null);
            }
        });
        createUnloadedPlayerData.setStatusUpdateListener(new PlayerData.StatusUpdateListener() {
            public void onStatusUpdate(Bundle bundle) {
                Bundle bundle2 = new Bundle();
                bundle2.putInt("key", i);
                bundle2.putBundle("status", bundle);
                AVManager.this.sendEvent("didUpdatePlaybackStatus", bundle2);
            }
        });
    }

    public void unloadForSound(Integer num, Promise promise) {
        if (tryGetSoundForKey(num, promise) != null) {
            removeSoundForKey(num);
            promise.resolve(PlayerData.getUnloadedStatus());
        }
    }

    public void setStatusForSound(Integer num, ReadableArguments readableArguments, Promise promise) {
        PlayerData tryGetSoundForKey = tryGetSoundForKey(num, promise);
        if (tryGetSoundForKey != null) {
            tryGetSoundForKey.setStatus(readableArguments.toBundle(), promise);
        }
    }

    public void replaySound(Integer num, ReadableArguments readableArguments, Promise promise) {
        PlayerData tryGetSoundForKey = tryGetSoundForKey(num, promise);
        if (tryGetSoundForKey != null) {
            tryGetSoundForKey.setStatus(readableArguments.toBundle(), promise);
        }
    }

    public void getStatusForSound(Integer num, Promise promise) {
        PlayerData tryGetSoundForKey = tryGetSoundForKey(num, promise);
        if (tryGetSoundForKey != null) {
            promise.resolve(tryGetSoundForKey.getStatus());
        }
    }

    private void tryRunWithVideoView(Integer num, final VideoViewCallback videoViewCallback, final Promise promise) {
        UIManager uIManager;
        ModuleRegistry moduleRegistry = this.mModuleRegistry;
        if (moduleRegistry != null && (uIManager = (UIManager) moduleRegistry.getModule(UIManager.class)) != null) {
            uIManager.addUIBlock(num.intValue(), new UIManager.UIBlock<VideoViewWrapper>() {
                public void resolve(VideoViewWrapper videoViewWrapper) {
                    videoViewCallback.runWithVideoView(videoViewWrapper.getVideoViewInstance());
                }

                public void reject(Throwable th) {
                    promise.reject("E_VIDEO_TAGINCORRECT", "Invalid view returned from registry.");
                }
            }, VideoViewWrapper.class);
        }
    }

    public void loadForVideo(Integer num, final ReadableArguments readableArguments, final ReadableArguments readableArguments2, final Promise promise) {
        tryRunWithVideoView(num, new VideoViewCallback() {
            public void runWithVideoView(VideoView videoView) {
                videoView.setSource(readableArguments, readableArguments2, promise);
            }
        }, promise);
    }

    public void unloadForVideo(Integer num, final Promise promise) {
        tryRunWithVideoView(num, new VideoViewCallback() {
            public void runWithVideoView(VideoView videoView) {
                videoView.setSource((ReadableArguments) null, (ReadableArguments) null, promise);
            }
        }, promise);
    }

    public void setStatusForVideo(Integer num, final ReadableArguments readableArguments, final Promise promise) {
        tryRunWithVideoView(num, new VideoViewCallback() {
            public void runWithVideoView(VideoView videoView) {
                videoView.setStatus(readableArguments, promise);
            }
        }, promise);
    }

    public void replayVideo(Integer num, final ReadableArguments readableArguments, final Promise promise) {
        tryRunWithVideoView(num, new VideoViewCallback() {
            public void runWithVideoView(VideoView videoView) {
                videoView.setStatus(readableArguments, promise);
            }
        }, promise);
    }

    public void getStatusForVideo(Integer num, final Promise promise) {
        tryRunWithVideoView(num, new VideoViewCallback() {
            public void runWithVideoView(VideoView videoView) {
                promise.resolve(videoView.getStatus());
            }
        }, promise);
    }

    private boolean isMissingAudioRecordingPermissions() {
        return !((Permissions) this.mModuleRegistry.getModule(Permissions.class)).hasGrantedPermissions("android.permission.RECORD_AUDIO");
    }

    private boolean checkAudioRecorderExistsOrReject(Promise promise) {
        if (this.mAudioRecorder == null && promise != null) {
            promise.reject("E_AUDIO_NORECORDER", "Recorder does not exist.");
        }
        return this.mAudioRecorder != null;
    }

    private long getAudioRecorderDurationMillis() {
        if (this.mAudioRecorder == null) {
            return 0;
        }
        long j = this.mAudioRecorderDurationAlreadyRecorded;
        return (!this.mAudioRecorderIsRecording || this.mAudioRecorderUptimeOfLastStartResume <= 0) ? j : j + (SystemClock.uptimeMillis() - this.mAudioRecorderUptimeOfLastStartResume);
    }

    private int getAudioRecorderLevels() {
        int maxAmplitude;
        MediaRecorder mediaRecorder = this.mAudioRecorder;
        if (mediaRecorder == null || !this.mAudioRecorderIsMeteringEnabled || (maxAmplitude = mediaRecorder.getMaxAmplitude()) == 0) {
            return -160;
        }
        return (int) (Math.log(((double) maxAmplitude) / 32767.0d) * 20.0d);
    }

    private Bundle getAudioRecorderStatus() {
        Bundle bundle = new Bundle();
        if (this.mAudioRecorder != null) {
            bundle.putBoolean("canRecord", true);
            bundle.putBoolean("isRecording", this.mAudioRecorderIsRecording);
            bundle.putInt("durationMillis", (int) getAudioRecorderDurationMillis());
            if (this.mAudioRecorderIsMeteringEnabled) {
                bundle.putInt("metering", getAudioRecorderLevels());
            }
        }
        return bundle;
    }

    private void removeAudioRecorder() {
        MediaRecorder mediaRecorder = this.mAudioRecorder;
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (RuntimeException unused) {
            }
            this.mAudioRecorder.release();
            this.mAudioRecorder = null;
        }
        this.mAudioRecordingFilePath = null;
        this.mAudioRecorderIsRecording = false;
        this.mAudioRecorderIsPaused = false;
        this.mAudioRecorderDurationAlreadyRecorded = 0;
        this.mAudioRecorderUptimeOfLastStartResume = 0;
    }

    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        EventEmitter eventEmitter;
        if (i == 801) {
            removeAudioRecorder();
            ModuleRegistry moduleRegistry = this.mModuleRegistry;
            if (moduleRegistry != null && (eventEmitter = (EventEmitter) moduleRegistry.getModule(EventEmitter.class)) != null) {
                eventEmitter.emit("Expo.Recording.recorderUnloaded", new Bundle());
            }
        }
    }

    public void prepareAudioRecorder(ReadableArguments readableArguments, Promise promise) {
        if (isMissingAudioRecordingPermissions()) {
            promise.reject("E_MISSING_PERMISSION", "Missing audio recording permissions.");
            return;
        }
        this.mAudioRecorderIsMeteringEnabled = readableArguments.getBoolean(RECORDING_OPTION_IS_METERING_ENABLED_KEY);
        removeAudioRecorder();
        ReadableArguments arguments = readableArguments.getArguments("android");
        String str = "recording-" + UUID.randomUUID().toString() + arguments.getString(RECORDING_OPTION_EXTENSION_KEY);
        try {
            File file = new File(this.mContext.getCacheDir() + File.separator + "Audio");
            ensureDirExists(file);
            this.mAudioRecordingFilePath = file + File.separator + str;
        } catch (IOException unused) {
        }
        MediaRecorder mediaRecorder = new MediaRecorder();
        this.mAudioRecorder = mediaRecorder;
        mediaRecorder.setAudioSource(0);
        this.mAudioRecorder.setOutputFormat(arguments.getInt(RECORDING_OPTION_OUTPUT_FORMAT_KEY));
        this.mAudioRecorder.setAudioEncoder(arguments.getInt(RECORDING_OPTION_AUDIO_ENCODER_KEY));
        if (arguments.containsKey(RECORDING_OPTION_SAMPLE_RATE_KEY)) {
            this.mAudioRecorder.setAudioSamplingRate(arguments.getInt(RECORDING_OPTION_SAMPLE_RATE_KEY));
        }
        if (arguments.containsKey(RECORDING_OPTION_NUMBER_OF_CHANNELS_KEY)) {
            this.mAudioRecorder.setAudioChannels(arguments.getInt(RECORDING_OPTION_NUMBER_OF_CHANNELS_KEY));
        }
        if (arguments.containsKey(RECORDING_OPTION_BIT_RATE_KEY)) {
            this.mAudioRecorder.setAudioEncodingBitRate(arguments.getInt(RECORDING_OPTION_BIT_RATE_KEY));
        }
        this.mAudioRecorder.setOutputFile(this.mAudioRecordingFilePath);
        if (arguments.containsKey(RECORDING_OPTION_MAX_FILE_SIZE_KEY)) {
            this.mAudioRecorder.setMaxFileSize((long) arguments.getInt(RECORDING_OPTION_MAX_FILE_SIZE_KEY));
            this.mAudioRecorder.setOnInfoListener(this);
        }
        try {
            this.mAudioRecorder.prepare();
            Bundle bundle = new Bundle();
            bundle.putString("uri", Uri.fromFile(new File(this.mAudioRecordingFilePath)).toString());
            bundle.putBundle("status", getAudioRecorderStatus());
            promise.resolve(bundle);
        } catch (Exception e) {
            promise.reject("E_AUDIO_RECORDERNOTCREATED", "Prepare encountered an error: recorder not prepared", e);
            removeAudioRecorder();
        }
    }

    public void startAudioRecording(Promise promise) {
        if (isMissingAudioRecordingPermissions()) {
            promise.reject("E_MISSING_PERMISSION", "Missing audio recording permissions.");
        } else if (checkAudioRecorderExistsOrReject(promise)) {
            try {
                if (!this.mAudioRecorderIsPaused || Build.VERSION.SDK_INT < 24) {
                    this.mAudioRecorder.start();
                } else {
                    this.mAudioRecorder.resume();
                }
                this.mAudioRecorderUptimeOfLastStartResume = SystemClock.uptimeMillis();
                this.mAudioRecorderIsRecording = true;
                this.mAudioRecorderIsPaused = false;
                promise.resolve(getAudioRecorderStatus());
            } catch (IllegalStateException e) {
                promise.reject("E_AUDIO_RECORDING", "Start encountered an error: recording not started", e);
            }
        }
    }

    public void pauseAudioRecording(Promise promise) {
        if (!checkAudioRecorderExistsOrReject(promise)) {
            return;
        }
        if (Build.VERSION.SDK_INT < 24) {
            promise.reject("E_AUDIO_VERSIONINCOMPATIBLE", "Pausing an audio recording is unsupported on Android devices running SDK < 24.");
            return;
        }
        try {
            this.mAudioRecorder.pause();
            this.mAudioRecorderDurationAlreadyRecorded = getAudioRecorderDurationMillis();
            this.mAudioRecorderIsRecording = false;
            this.mAudioRecorderIsPaused = true;
            promise.resolve(getAudioRecorderStatus());
        } catch (IllegalStateException e) {
            promise.reject("E_AUDIO_RECORDINGPAUSE", "Pause encountered an error: recording not paused", e);
        }
    }

    public void stopAudioRecording(Promise promise) {
        if (checkAudioRecorderExistsOrReject(promise)) {
            try {
                this.mAudioRecorder.stop();
                this.mAudioRecorderDurationAlreadyRecorded = getAudioRecorderDurationMillis();
                this.mAudioRecorderIsRecording = false;
                this.mAudioRecorderIsPaused = false;
                promise.resolve(getAudioRecorderStatus());
            } catch (RuntimeException e) {
                this.mAudioRecorderIsPaused = false;
                if (!this.mAudioRecorderIsRecording) {
                    promise.reject("E_AUDIO_RECORDINGSTOP", "Stop encountered an error: recording not started", e);
                    return;
                }
                this.mAudioRecorderIsRecording = false;
                promise.reject("E_AUDIO_NODATA", "Stop encountered an error: no valid audio data has been received", e);
            }
        }
    }

    public void getAudioRecordingStatus(Promise promise) {
        if (checkAudioRecorderExistsOrReject(promise)) {
            promise.resolve(getAudioRecorderStatus());
        }
    }

    public void unloadAudioRecorder(Promise promise) {
        if (checkAudioRecorderExistsOrReject(promise)) {
            removeAudioRecorder();
            promise.resolve((Object) null);
        }
    }

    private static File ensureDirExists(File file) throws IOException {
        if (file.isDirectory() || file.mkdirs()) {
            return file;
        }
        throw new IOException("Couldn't create directory '" + file + "'");
    }
}
