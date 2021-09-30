package expo.modules.p019av.player;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import expo.modules.p019av.AVManagerInterface;
import expo.modules.p019av.AudioFocusNotAcquiredException;
import expo.modules.p019av.player.PlayerData;
import expo.modules.p019av.player.datasource.DataSourceFactoryProvider;
import java.io.IOException;
import java.util.Map;
import org.unimodules.core.Promise;

/* renamed from: expo.modules.av.player.SimpleExoPlayerData */
class SimpleExoPlayerData extends PlayerData implements Player.EventListener, ExtractorMediaSource.EventListener, SimpleExoPlayer.VideoListener, AdaptiveMediaSourceEventListener {
    private static final String IMPLEMENTATION_NAME = "SimpleExoPlayer";
    private static final String TAG = SimpleExoPlayerData.class.getSimpleName();
    private boolean mFirstFrameRendered = false;
    private boolean mIsLoading = true;
    private boolean mIsLooping = false;
    private Integer mLastPlaybackState = null;
    private PlayerData.LoadCompletionListener mLoadCompletionListener = null;
    private String mOverridingExtension;
    private Context mReactContext;
    private SimpleExoPlayer mSimpleExoPlayer = null;
    private Pair<Integer, Integer> mVideoWidthHeight = null;

    /* access modifiers changed from: package-private */
    public String getImplementationName() {
        return IMPLEMENTATION_NAME;
    }

    public void onDownstreamFormatChanged(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public void onLoadCanceled(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public void onLoadCompleted(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public void onLoadError(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException iOException, boolean z) {
    }

    public void onLoadStarted(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public void onMediaPeriodCreated(int i, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    public void onMediaPeriodReleased(int i, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    public void onReadingStarted(int i, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    public void onRepeatModeChanged(int i) {
    }

    public void onSeekProcessed() {
    }

    public void onShuffleModeEnabledChanged(boolean z) {
    }

    public /* synthetic */ void onSurfaceSizeChanged(int i, int i2) {
        VideoListener.CC.$default$onSurfaceSizeChanged(this, i, i2);
    }

    public /* synthetic */ void onTimelineChanged(Timeline timeline, Object obj, int i) {
        Player.EventListener.CC.$default$onTimelineChanged(this, timeline, obj, i);
    }

    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
    }

    public void onUpstreamDiscarded(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    SimpleExoPlayerData(AVManagerInterface aVManagerInterface, Context context, Uri uri, String str, Map<String, Object> map) {
        super(aVManagerInterface, uri, map);
        this.mReactContext = context;
        this.mOverridingExtension = str;
    }

    public void load(Bundle bundle, PlayerData.LoadCompletionListener loadCompletionListener) {
        this.mLoadCompletionListener = loadCompletionListener;
        Handler handler = new Handler();
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        SimpleExoPlayer newSimpleInstance = ExoPlayerFactory.newSimpleInstance(this.mAVModule.getContext(), (RenderersFactory) new DefaultRenderersFactory(this.mAVModule.getContext()), (TrackSelector) new DefaultTrackSelector((TrackSelection.Factory) new AdaptiveTrackSelection.Factory()), (LoadControl) new DefaultLoadControl(), (DrmSessionManager<FrameworkMediaCrypto>) null, (BandwidthMeter) defaultBandwidthMeter);
        this.mSimpleExoPlayer = newSimpleInstance;
        newSimpleInstance.addListener(this);
        this.mSimpleExoPlayer.addVideoListener(this);
        try {
            this.mSimpleExoPlayer.prepare(buildMediaSource(this.mUri, this.mOverridingExtension, handler, ((DataSourceFactoryProvider) this.mAVModule.getModuleRegistry().getModule(DataSourceFactoryProvider.class)).createFactory(this.mReactContext, this.mAVModule.getModuleRegistry(), Util.getUserAgent(this.mAVModule.getContext(), "yourApplicationName"), this.mRequestHeaders, defaultBandwidthMeter.getTransferListener())));
            setStatus(bundle, (Promise) null);
        } catch (IllegalStateException e) {
            onFatalError(e);
        }
    }

    public synchronized void release() {
        if (this.mSimpleExoPlayer != null) {
            this.mSimpleExoPlayer.release();
            this.mSimpleExoPlayer = null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldContinueUpdatingProgress() {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        return simpleExoPlayer != null && simpleExoPlayer.getPlayWhenReady();
    }

    /* access modifiers changed from: package-private */
    public void playPlayerWithRateAndMuteIfNecessary() throws AudioFocusNotAcquiredException {
        if (this.mSimpleExoPlayer != null && shouldPlayerPlay()) {
            if (!this.mIsMuted) {
                this.mAVModule.acquireAudioFocus();
            }
            updateVolumeMuteAndDuck();
            this.mSimpleExoPlayer.setPlaybackParameters(new PlaybackParameters(this.mRate, this.mShouldCorrectPitch ? 1.0f : this.mRate));
            this.mSimpleExoPlayer.setPlayWhenReady(this.mShouldPlay);
        }
    }

    /* access modifiers changed from: package-private */
    public void applyNewStatus(Integer num, Boolean bool) throws AudioFocusNotAcquiredException, IllegalStateException {
        if (this.mSimpleExoPlayer != null) {
            if (bool != null) {
                boolean booleanValue = bool.booleanValue();
                this.mIsLooping = booleanValue;
                if (booleanValue) {
                    this.mSimpleExoPlayer.setRepeatMode(2);
                } else {
                    this.mSimpleExoPlayer.setRepeatMode(0);
                }
            }
            if (!shouldPlayerPlay()) {
                this.mSimpleExoPlayer.setPlayWhenReady(false);
                stopUpdatingProgressIfNecessary();
            }
            updateVolumeMuteAndDuck();
            if (num != null) {
                this.mSimpleExoPlayer.seekTo((long) num.intValue());
            }
            playPlayerWithRateAndMuteIfNecessary();
            return;
        }
        throw new IllegalStateException("mSimpleExoPlayer is null!");
    }

    /* access modifiers changed from: package-private */
    public boolean isLoaded() {
        return this.mSimpleExoPlayer != null;
    }

    /* access modifiers changed from: package-private */
    public void getExtraStatusFields(Bundle bundle) {
        int duration = (int) this.mSimpleExoPlayer.getDuration();
        bundle.putInt("durationMillis", duration);
        boolean z = false;
        bundle.putInt("positionMillis", getClippedIntegerForValue(Integer.valueOf((int) this.mSimpleExoPlayer.getCurrentPosition()), 0, Integer.valueOf(duration)));
        bundle.putInt("playableDurationMillis", getClippedIntegerForValue(Integer.valueOf((int) this.mSimpleExoPlayer.getBufferedPosition()), 0, Integer.valueOf(duration)));
        bundle.putBoolean(PlayerData.STATUS_IS_PLAYING_KEY_PATH, this.mSimpleExoPlayer.getPlayWhenReady() && this.mSimpleExoPlayer.getPlaybackState() == 3);
        if (this.mIsLoading || this.mSimpleExoPlayer.getPlaybackState() == 2) {
            z = true;
        }
        bundle.putBoolean("isBuffering", z);
        bundle.putBoolean("isLooping", this.mIsLooping);
    }

    public Pair<Integer, Integer> getVideoWidthHeight() {
        Pair<Integer, Integer> pair = this.mVideoWidthHeight;
        return pair != null ? pair : new Pair<>(0, 0);
    }

    public void tryUpdateVideoSurface(Surface surface) {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setVideoSurface(surface);
        }
    }

    public int getAudioSessionId() {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        if (simpleExoPlayer != null) {
            return simpleExoPlayer.getAudioSessionId();
        }
        return 0;
    }

    public void pauseImmediately() {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
        }
        stopUpdatingProgressIfNecessary();
    }

    public boolean requiresAudioFocus() {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        return simpleExoPlayer != null && (simpleExoPlayer.getPlayWhenReady() || shouldPlayerPlay()) && !this.mIsMuted;
    }

    public void updateVolumeMuteAndDuck() {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setVolume(this.mAVModule.getVolumeForDuckAndFocus(this.mIsMuted, this.mVolume));
        }
    }

    public void onLoadingChanged(boolean z) {
        this.mIsLoading = z;
        callStatusUpdateListener();
    }

    public void onPlayerStateChanged(boolean z, int i) {
        PlayerData.LoadCompletionListener loadCompletionListener;
        if (i == 3 && (loadCompletionListener = this.mLoadCompletionListener) != null) {
            this.mLoadCompletionListener = null;
            loadCompletionListener.onLoadSuccess(getStatus());
        }
        Integer num = this.mLastPlaybackState;
        if (num == null || i == num.intValue() || i != 4) {
            callStatusUpdateListener();
            if (z && i == 3) {
                beginUpdatingProgressIfNecessary();
            }
        } else {
            callStatusUpdateListenerWithDidJustFinish();
            stopUpdatingProgressIfNecessary();
        }
        this.mLastPlaybackState = Integer.valueOf(i);
    }

    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        onFatalError(exoPlaybackException.getCause());
    }

    public void onPositionDiscontinuity(int i) {
        if (i == 0) {
            callStatusUpdateListenerWithDidJustFinish();
        }
    }

    public void onLoadError(IOException iOException) {
        PlayerData.LoadCompletionListener loadCompletionListener = this.mLoadCompletionListener;
        if (loadCompletionListener != null) {
            this.mLoadCompletionListener = null;
            loadCompletionListener.onLoadError(iOException.toString());
        }
    }

    private void onFatalError(Throwable th) {
        PlayerData.LoadCompletionListener loadCompletionListener = this.mLoadCompletionListener;
        if (loadCompletionListener != null) {
            this.mLoadCompletionListener = null;
            loadCompletionListener.onLoadError(th.toString());
        } else if (this.mErrorListener != null) {
            PlayerData.ErrorListener errorListener = this.mErrorListener;
            errorListener.onError("Player error: " + th.getMessage());
        }
        release();
    }

    public void onVideoSizeChanged(int i, int i2, int i3, float f) {
        this.mVideoWidthHeight = new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
        if (this.mFirstFrameRendered && this.mVideoSizeUpdateListener != null) {
            this.mVideoSizeUpdateListener.onVideoSizeUpdate(this.mVideoWidthHeight);
        }
    }

    public void onRenderedFirstFrame() {
        if (!(this.mFirstFrameRendered || this.mVideoWidthHeight == null || this.mVideoSizeUpdateListener == null)) {
            this.mVideoSizeUpdateListener.onVideoSizeUpdate(this.mVideoWidthHeight);
        }
        this.mFirstFrameRendered = true;
    }

    private MediaSource buildMediaSource(Uri uri, String str, Handler handler, DataSource.Factory factory) {
        String str2;
        try {
            if (uri.getScheme() == null) {
                DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(this.mReactContext.getResources().getIdentifier(uri.toString(), "raw", this.mReactContext.getPackageName())));
                RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this.mReactContext);
                rawResourceDataSource.open(dataSpec);
                uri = rawResourceDataSource.getUri();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error reading raw resource from ExoPlayer", e);
        }
        Uri uri2 = uri;
        if (TextUtils.isEmpty(str)) {
            str2 = String.valueOf(uri2);
        } else {
            str2 = "." + str;
        }
        int inferContentType = Util.inferContentType(str2);
        if (inferContentType == 0) {
            return new DashMediaSource(uri2, factory, (DashChunkSource.Factory) new DefaultDashChunkSource.Factory(factory), handler, (MediaSourceEventListener) this);
        } else if (inferContentType == 1) {
            return new SsMediaSource(uri2, factory, (SsChunkSource.Factory) new DefaultSsChunkSource.Factory(factory), handler, (MediaSourceEventListener) this);
        } else if (inferContentType == 2) {
            return new HlsMediaSource(uri2, factory, handler, this);
        } else {
            if (inferContentType == 3) {
                return new ExtractorMediaSource(uri2, factory, new DefaultExtractorsFactory(), handler, this);
            }
            throw new IllegalStateException("Content of this type is unsupported at the moment. Unsupported type: " + inferContentType);
        }
    }
}
