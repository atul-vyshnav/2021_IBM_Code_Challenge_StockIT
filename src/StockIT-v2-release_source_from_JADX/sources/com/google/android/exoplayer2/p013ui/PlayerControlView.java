package com.google.android.exoplayer2.p013ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.exoplayer2.C1379C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.p013ui.TimeBar;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Locale;

/* renamed from: com.google.android.exoplayer2.ui.PlayerControlView */
public class PlayerControlView extends FrameLayout {
    public static final int DEFAULT_FAST_FORWARD_MS = 15000;
    public static final int DEFAULT_REPEAT_TOGGLE_MODES = 0;
    public static final int DEFAULT_REWIND_MS = 5000;
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;
    private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;
    public static final int MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR = 100;
    private long[] adGroupTimesMs;
    private final ComponentListener componentListener;
    /* access modifiers changed from: private */
    public ControlDispatcher controlDispatcher;
    private final TextView durationView;
    private long[] extraAdGroupTimesMs;
    private boolean[] extraPlayedAdGroups;
    /* access modifiers changed from: private */
    public final View fastForwardButton;
    private int fastForwardMs;
    /* access modifiers changed from: private */
    public final StringBuilder formatBuilder;
    /* access modifiers changed from: private */
    public final Formatter formatter;
    private final Runnable hideAction;
    private long hideAtMs;
    private boolean isAttachedToWindow;
    private boolean multiWindowTimeBar;
    /* access modifiers changed from: private */
    public final View nextButton;
    /* access modifiers changed from: private */
    public final View pauseButton;
    private final Timeline.Period period;
    /* access modifiers changed from: private */
    public final View playButton;
    /* access modifiers changed from: private */
    public PlaybackPreparer playbackPreparer;
    private boolean[] playedAdGroups;
    /* access modifiers changed from: private */
    public Player player;
    /* access modifiers changed from: private */
    public final TextView positionView;
    /* access modifiers changed from: private */
    public final View previousButton;
    private final String repeatAllButtonContentDescription;
    private final Drawable repeatAllButtonDrawable;
    private final String repeatOffButtonContentDescription;
    private final Drawable repeatOffButtonDrawable;
    private final String repeatOneButtonContentDescription;
    private final Drawable repeatOneButtonDrawable;
    /* access modifiers changed from: private */
    public final ImageView repeatToggleButton;
    /* access modifiers changed from: private */
    public int repeatToggleModes;
    /* access modifiers changed from: private */
    public final View rewindButton;
    private int rewindMs;
    /* access modifiers changed from: private */
    public boolean scrubbing;
    private boolean showMultiWindowTimeBar;
    private boolean showShuffleButton;
    private int showTimeoutMs;
    /* access modifiers changed from: private */
    public final View shuffleButton;
    private final TimeBar timeBar;
    private final Runnable updateProgressAction;
    private VisibilityListener visibilityListener;
    private final Timeline.Window window;

    /* renamed from: com.google.android.exoplayer2.ui.PlayerControlView$VisibilityListener */
    public interface VisibilityListener {
        void onVisibilityChange(int i);
    }

    private static boolean isHandledMediaKey(int i) {
        return i == 90 || i == 89 || i == 85 || i == 126 || i == 127 || i == 87 || i == 88;
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public PlayerControlView(Context context) {
        this(context, (AttributeSet) null);
    }

    public PlayerControlView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PlayerControlView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, attributeSet);
    }

    public PlayerControlView(Context context, AttributeSet attributeSet, int i, AttributeSet attributeSet2) {
        super(context, attributeSet, i);
        int i2 = C1490R.layout.exo_player_control_view;
        this.rewindMs = 5000;
        this.fastForwardMs = 15000;
        this.showTimeoutMs = 5000;
        this.repeatToggleModes = 0;
        this.hideAtMs = C1379C.TIME_UNSET;
        this.showShuffleButton = false;
        if (attributeSet2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, C1490R.styleable.PlayerControlView, 0, 0);
            try {
                this.rewindMs = obtainStyledAttributes.getInt(C1490R.styleable.PlayerControlView_rewind_increment, this.rewindMs);
                this.fastForwardMs = obtainStyledAttributes.getInt(C1490R.styleable.PlayerControlView_fastforward_increment, this.fastForwardMs);
                this.showTimeoutMs = obtainStyledAttributes.getInt(C1490R.styleable.PlayerControlView_show_timeout, this.showTimeoutMs);
                i2 = obtainStyledAttributes.getResourceId(C1490R.styleable.PlayerControlView_controller_layout_id, i2);
                this.repeatToggleModes = getRepeatToggleModes(obtainStyledAttributes, this.repeatToggleModes);
                this.showShuffleButton = obtainStyledAttributes.getBoolean(C1490R.styleable.PlayerControlView_show_shuffle_button, this.showShuffleButton);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        this.period = new Timeline.Period();
        this.window = new Timeline.Window();
        this.formatBuilder = new StringBuilder();
        this.formatter = new Formatter(this.formatBuilder, Locale.getDefault());
        this.adGroupTimesMs = new long[0];
        this.playedAdGroups = new boolean[0];
        this.extraAdGroupTimesMs = new long[0];
        this.extraPlayedAdGroups = new boolean[0];
        this.componentListener = new ComponentListener();
        this.controlDispatcher = new DefaultControlDispatcher();
        this.updateProgressAction = new Runnable() {
            public final void run() {
                PlayerControlView.this.updateProgress();
            }
        };
        this.hideAction = new Runnable() {
            public final void run() {
                PlayerControlView.this.hide();
            }
        };
        LayoutInflater.from(context).inflate(i2, this);
        setDescendantFocusability(262144);
        this.durationView = (TextView) findViewById(C1490R.C1493id.exo_duration);
        this.positionView = (TextView) findViewById(C1490R.C1493id.exo_position);
        TimeBar timeBar2 = (TimeBar) findViewById(C1490R.C1493id.exo_progress);
        this.timeBar = timeBar2;
        if (timeBar2 != null) {
            timeBar2.addListener(this.componentListener);
        }
        View findViewById = findViewById(C1490R.C1493id.exo_play);
        this.playButton = findViewById;
        if (findViewById != null) {
            findViewById.setOnClickListener(this.componentListener);
        }
        View findViewById2 = findViewById(C1490R.C1493id.exo_pause);
        this.pauseButton = findViewById2;
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(this.componentListener);
        }
        View findViewById3 = findViewById(C1490R.C1493id.exo_prev);
        this.previousButton = findViewById3;
        if (findViewById3 != null) {
            findViewById3.setOnClickListener(this.componentListener);
        }
        View findViewById4 = findViewById(C1490R.C1493id.exo_next);
        this.nextButton = findViewById4;
        if (findViewById4 != null) {
            findViewById4.setOnClickListener(this.componentListener);
        }
        View findViewById5 = findViewById(C1490R.C1493id.exo_rew);
        this.rewindButton = findViewById5;
        if (findViewById5 != null) {
            findViewById5.setOnClickListener(this.componentListener);
        }
        View findViewById6 = findViewById(C1490R.C1493id.exo_ffwd);
        this.fastForwardButton = findViewById6;
        if (findViewById6 != null) {
            findViewById6.setOnClickListener(this.componentListener);
        }
        ImageView imageView = (ImageView) findViewById(C1490R.C1493id.exo_repeat_toggle);
        this.repeatToggleButton = imageView;
        if (imageView != null) {
            imageView.setOnClickListener(this.componentListener);
        }
        View findViewById7 = findViewById(C1490R.C1493id.exo_shuffle);
        this.shuffleButton = findViewById7;
        if (findViewById7 != null) {
            findViewById7.setOnClickListener(this.componentListener);
        }
        Resources resources = context.getResources();
        this.repeatOffButtonDrawable = resources.getDrawable(C1490R.C1492drawable.exo_controls_repeat_off);
        this.repeatOneButtonDrawable = resources.getDrawable(C1490R.C1492drawable.exo_controls_repeat_one);
        this.repeatAllButtonDrawable = resources.getDrawable(C1490R.C1492drawable.exo_controls_repeat_all);
        this.repeatOffButtonContentDescription = resources.getString(C1490R.string.exo_controls_repeat_off_description);
        this.repeatOneButtonContentDescription = resources.getString(C1490R.string.exo_controls_repeat_one_description);
        this.repeatAllButtonContentDescription = resources.getString(C1490R.string.exo_controls_repeat_all_description);
    }

    private static int getRepeatToggleModes(TypedArray typedArray, int i) {
        return typedArray.getInt(C1490R.styleable.PlayerControlView_repeat_toggle_modes, i);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player2) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (!(player2 == null || player2.getApplicationLooper() == Looper.getMainLooper())) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player3 = this.player;
        if (player3 != player2) {
            if (player3 != null) {
                player3.removeListener(this.componentListener);
            }
            this.player = player2;
            if (player2 != null) {
                player2.addListener(this.componentListener);
            }
            updateAll();
        }
    }

    public void setShowMultiWindowTimeBar(boolean z) {
        this.showMultiWindowTimeBar = z;
        updateTimeBarMode();
    }

    public void setExtraAdGroupMarkers(long[] jArr, boolean[] zArr) {
        boolean z = false;
        if (jArr == null) {
            this.extraAdGroupTimesMs = new long[0];
            this.extraPlayedAdGroups = new boolean[0];
        } else {
            if (jArr.length == zArr.length) {
                z = true;
            }
            Assertions.checkArgument(z);
            this.extraAdGroupTimesMs = jArr;
            this.extraPlayedAdGroups = zArr;
        }
        updateProgress();
    }

    public void setVisibilityListener(VisibilityListener visibilityListener2) {
        this.visibilityListener = visibilityListener2;
    }

    public void setPlaybackPreparer(PlaybackPreparer playbackPreparer2) {
        this.playbackPreparer = playbackPreparer2;
    }

    public void setControlDispatcher(ControlDispatcher controlDispatcher2) {
        if (controlDispatcher2 == null) {
            controlDispatcher2 = new DefaultControlDispatcher();
        }
        this.controlDispatcher = controlDispatcher2;
    }

    public void setRewindIncrementMs(int i) {
        this.rewindMs = i;
        updateNavigation();
    }

    public void setFastForwardIncrementMs(int i) {
        this.fastForwardMs = i;
        updateNavigation();
    }

    public int getShowTimeoutMs() {
        return this.showTimeoutMs;
    }

    public void setShowTimeoutMs(int i) {
        this.showTimeoutMs = i;
        if (isVisible()) {
            hideAfterTimeout();
        }
    }

    public int getRepeatToggleModes() {
        return this.repeatToggleModes;
    }

    public void setRepeatToggleModes(int i) {
        this.repeatToggleModes = i;
        Player player2 = this.player;
        if (player2 != null) {
            int repeatMode = player2.getRepeatMode();
            if (i == 0 && repeatMode != 0) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 0);
            } else if (i == 1 && repeatMode == 2) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 1);
            } else if (i == 2 && repeatMode == 1) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 2);
            }
        }
        updateRepeatModeButton();
    }

    public boolean getShowShuffleButton() {
        return this.showShuffleButton;
    }

    public void setShowShuffleButton(boolean z) {
        this.showShuffleButton = z;
        updateShuffleButton();
    }

    public void show() {
        if (!isVisible()) {
            setVisibility(0);
            VisibilityListener visibilityListener2 = this.visibilityListener;
            if (visibilityListener2 != null) {
                visibilityListener2.onVisibilityChange(getVisibility());
            }
            updateAll();
            requestPlayPauseFocus();
        }
        hideAfterTimeout();
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(8);
            VisibilityListener visibilityListener2 = this.visibilityListener;
            if (visibilityListener2 != null) {
                visibilityListener2.onVisibilityChange(getVisibility());
            }
            removeCallbacks(this.updateProgressAction);
            removeCallbacks(this.hideAction);
            this.hideAtMs = C1379C.TIME_UNSET;
        }
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    private void hideAfterTimeout() {
        removeCallbacks(this.hideAction);
        if (this.showTimeoutMs > 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int i = this.showTimeoutMs;
            this.hideAtMs = uptimeMillis + ((long) i);
            if (this.isAttachedToWindow) {
                postDelayed(this.hideAction, (long) i);
                return;
            }
            return;
        }
        this.hideAtMs = C1379C.TIME_UNSET;
    }

    private void updateAll() {
        updatePlayPauseButton();
        updateNavigation();
        updateRepeatModeButton();
        updateShuffleButton();
        updateProgress();
    }

    /* access modifiers changed from: private */
    public void updatePlayPauseButton() {
        boolean z;
        if (isVisible() && this.isAttachedToWindow) {
            boolean isPlaying = isPlaying();
            View view = this.playButton;
            int i = 8;
            boolean z2 = true;
            if (view != null) {
                z = (isPlaying && view.isFocused()) | false;
                this.playButton.setVisibility(isPlaying ? 8 : 0);
            } else {
                z = false;
            }
            View view2 = this.pauseButton;
            if (view2 != null) {
                if (isPlaying || !view2.isFocused()) {
                    z2 = false;
                }
                z |= z2;
                View view3 = this.pauseButton;
                if (isPlaying) {
                    i = 0;
                }
                view3.setVisibility(i);
            }
            if (z) {
                requestPlayPauseFocus();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateNavigation() {
        /*
            r6 = this;
            boolean r0 = r6.isVisible()
            if (r0 == 0) goto L_0x008f
            boolean r0 = r6.isAttachedToWindow
            if (r0 != 0) goto L_0x000c
            goto L_0x008f
        L_0x000c:
            com.google.android.exoplayer2.Player r0 = r6.player
            if (r0 == 0) goto L_0x0015
            com.google.android.exoplayer2.Timeline r0 = r0.getCurrentTimeline()
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0022
            boolean r3 = r0.isEmpty()
            if (r3 != 0) goto L_0x0022
            r3 = 1
            goto L_0x0023
        L_0x0022:
            r3 = 0
        L_0x0023:
            if (r3 == 0) goto L_0x0060
            com.google.android.exoplayer2.Player r3 = r6.player
            boolean r3 = r3.isPlayingAd()
            if (r3 != 0) goto L_0x0060
            com.google.android.exoplayer2.Player r3 = r6.player
            int r3 = r3.getCurrentWindowIndex()
            com.google.android.exoplayer2.Timeline$Window r4 = r6.window
            r0.getWindow(r3, r4)
            com.google.android.exoplayer2.Timeline$Window r0 = r6.window
            boolean r0 = r0.isSeekable
            if (r0 != 0) goto L_0x004f
            com.google.android.exoplayer2.Timeline$Window r3 = r6.window
            boolean r3 = r3.isDynamic
            if (r3 == 0) goto L_0x004f
            com.google.android.exoplayer2.Player r3 = r6.player
            boolean r3 = r3.hasPrevious()
            if (r3 == 0) goto L_0x004d
            goto L_0x004f
        L_0x004d:
            r3 = 0
            goto L_0x0050
        L_0x004f:
            r3 = 1
        L_0x0050:
            com.google.android.exoplayer2.Timeline$Window r4 = r6.window
            boolean r4 = r4.isDynamic
            if (r4 != 0) goto L_0x005e
            com.google.android.exoplayer2.Player r4 = r6.player
            boolean r4 = r4.hasNext()
            if (r4 == 0) goto L_0x0062
        L_0x005e:
            r4 = 1
            goto L_0x0063
        L_0x0060:
            r0 = 0
            r3 = 0
        L_0x0062:
            r4 = 0
        L_0x0063:
            android.view.View r5 = r6.previousButton
            r6.setButtonEnabled(r3, r5)
            android.view.View r3 = r6.nextButton
            r6.setButtonEnabled(r4, r3)
            int r3 = r6.fastForwardMs
            if (r3 <= 0) goto L_0x0075
            if (r0 == 0) goto L_0x0075
            r3 = 1
            goto L_0x0076
        L_0x0075:
            r3 = 0
        L_0x0076:
            android.view.View r4 = r6.fastForwardButton
            r6.setButtonEnabled(r3, r4)
            int r3 = r6.rewindMs
            if (r3 <= 0) goto L_0x0082
            if (r0 == 0) goto L_0x0082
            goto L_0x0083
        L_0x0082:
            r1 = 0
        L_0x0083:
            android.view.View r2 = r6.rewindButton
            r6.setButtonEnabled(r1, r2)
            com.google.android.exoplayer2.ui.TimeBar r1 = r6.timeBar
            if (r1 == 0) goto L_0x008f
            r1.setEnabled(r0)
        L_0x008f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p013ui.PlayerControlView.updateNavigation():void");
    }

    /* access modifiers changed from: private */
    public void updateRepeatModeButton() {
        ImageView imageView;
        if (isVisible() && this.isAttachedToWindow && (imageView = this.repeatToggleButton) != null) {
            if (this.repeatToggleModes == 0) {
                imageView.setVisibility(8);
            } else if (this.player == null) {
                setButtonEnabled(false, imageView);
            } else {
                setButtonEnabled(true, imageView);
                int repeatMode = this.player.getRepeatMode();
                if (repeatMode == 0) {
                    this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
                } else if (repeatMode == 1) {
                    this.repeatToggleButton.setImageDrawable(this.repeatOneButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatOneButtonContentDescription);
                } else if (repeatMode == 2) {
                    this.repeatToggleButton.setImageDrawable(this.repeatAllButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatAllButtonContentDescription);
                }
                this.repeatToggleButton.setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateShuffleButton() {
        View view;
        if (isVisible() && this.isAttachedToWindow && (view = this.shuffleButton) != null) {
            if (!this.showShuffleButton) {
                view.setVisibility(8);
                return;
            }
            Player player2 = this.player;
            if (player2 == null) {
                setButtonEnabled(false, view);
                return;
            }
            view.setAlpha(player2.getShuffleModeEnabled() ? 1.0f : 0.3f);
            this.shuffleButton.setEnabled(true);
            this.shuffleButton.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void updateTimeBarMode() {
        Player player2 = this.player;
        if (player2 != null) {
            this.multiWindowTimeBar = this.showMultiWindowTimeBar && canShowMultiWindowTimeBar(player2.getCurrentTimeline(), this.window);
        }
    }

    /* access modifiers changed from: private */
    public void updateProgress() {
        long j;
        long j2;
        long j3;
        int i;
        long j4;
        long j5;
        if (isVisible() && this.isAttachedToWindow) {
            Player player2 = this.player;
            boolean z = true;
            if (player2 != null) {
                Timeline currentTimeline = player2.getCurrentTimeline();
                if (!currentTimeline.isEmpty()) {
                    int currentWindowIndex = this.player.getCurrentWindowIndex();
                    int i2 = this.multiWindowTimeBar ? 0 : currentWindowIndex;
                    int windowCount = this.multiWindowTimeBar ? currentTimeline.getWindowCount() - 1 : currentWindowIndex;
                    long j6 = 0;
                    j4 = 0;
                    i = 0;
                    while (true) {
                        if (i2 > windowCount) {
                            break;
                        }
                        if (i2 == currentWindowIndex) {
                            j4 = C1379C.usToMs(j6);
                        }
                        currentTimeline.getWindow(i2, this.window);
                        if (this.window.durationUs == C1379C.TIME_UNSET) {
                            Assertions.checkState(this.multiWindowTimeBar ^ z);
                            break;
                        }
                        for (int i3 = this.window.firstPeriodIndex; i3 <= this.window.lastPeriodIndex; i3++) {
                            currentTimeline.getPeriod(i3, this.period);
                            int adGroupCount = this.period.getAdGroupCount();
                            for (int i4 = 0; i4 < adGroupCount; i4++) {
                                long adGroupTimeUs = this.period.getAdGroupTimeUs(i4);
                                if (adGroupTimeUs == Long.MIN_VALUE) {
                                    if (this.period.durationUs == C1379C.TIME_UNSET) {
                                    } else {
                                        adGroupTimeUs = this.period.durationUs;
                                    }
                                }
                                long positionInWindowUs = adGroupTimeUs + this.period.getPositionInWindowUs();
                                if (positionInWindowUs >= 0 && positionInWindowUs <= this.window.durationUs) {
                                    long[] jArr = this.adGroupTimesMs;
                                    if (i == jArr.length) {
                                        int length = jArr.length == 0 ? 1 : jArr.length * 2;
                                        this.adGroupTimesMs = Arrays.copyOf(this.adGroupTimesMs, length);
                                        this.playedAdGroups = Arrays.copyOf(this.playedAdGroups, length);
                                    }
                                    this.adGroupTimesMs[i] = C1379C.usToMs(j6 + positionInWindowUs);
                                    this.playedAdGroups[i] = this.period.hasPlayedAdGroup(i4);
                                    i++;
                                }
                            }
                        }
                        j6 += this.window.durationUs;
                        i2++;
                        z = true;
                    }
                    j5 = j6;
                } else {
                    j5 = 0;
                    j4 = 0;
                    i = 0;
                }
                j3 = C1379C.usToMs(j5);
                j2 = this.player.getContentPosition() + j4;
                j = this.player.getContentBufferedPosition() + j4;
                if (this.timeBar != null) {
                    int length2 = this.extraAdGroupTimesMs.length;
                    int i5 = i + length2;
                    long[] jArr2 = this.adGroupTimesMs;
                    if (i5 > jArr2.length) {
                        this.adGroupTimesMs = Arrays.copyOf(jArr2, i5);
                        this.playedAdGroups = Arrays.copyOf(this.playedAdGroups, i5);
                    }
                    System.arraycopy(this.extraAdGroupTimesMs, 0, this.adGroupTimesMs, i, length2);
                    System.arraycopy(this.extraPlayedAdGroups, 0, this.playedAdGroups, i, length2);
                    this.timeBar.setAdGroupTimesMs(this.adGroupTimesMs, this.playedAdGroups, i5);
                }
            } else {
                j3 = 0;
                j2 = 0;
                j = 0;
            }
            TextView textView = this.durationView;
            if (textView != null) {
                textView.setText(Util.getStringForTime(this.formatBuilder, this.formatter, j3));
            }
            TextView textView2 = this.positionView;
            if (textView2 != null && !this.scrubbing) {
                textView2.setText(Util.getStringForTime(this.formatBuilder, this.formatter, j2));
            }
            TimeBar timeBar2 = this.timeBar;
            if (timeBar2 != null) {
                timeBar2.setPosition(j2);
                this.timeBar.setBufferedPosition(j);
                this.timeBar.setDuration(j3);
            }
            removeCallbacks(this.updateProgressAction);
            Player player3 = this.player;
            int playbackState = player3 == null ? 1 : player3.getPlaybackState();
            if (playbackState != 1 && playbackState != 4) {
                long j7 = 1000;
                if (this.player.getPlayWhenReady() && playbackState == 3) {
                    float f = this.player.getPlaybackParameters().speed;
                    if (f > 0.1f) {
                        if (f <= 5.0f) {
                            long max = (long) (1000 / Math.max(1, Math.round(1.0f / f)));
                            long j8 = max - (j2 % max);
                            if (j8 < max / 5) {
                                j8 += max;
                            }
                            if (f != 1.0f) {
                                j8 = (long) (((float) j8) / f);
                            }
                            j7 = j8;
                        } else {
                            j7 = 200;
                        }
                    }
                }
                postDelayed(this.updateProgressAction, j7);
            }
        }
    }

    private void requestPlayPauseFocus() {
        View view;
        View view2;
        boolean isPlaying = isPlaying();
        if (!isPlaying && (view2 = this.playButton) != null) {
            view2.requestFocus();
        } else if (isPlaying && (view = this.pauseButton) != null) {
            view.requestFocus();
        }
    }

    private void setButtonEnabled(boolean z, View view) {
        if (view != null) {
            view.setEnabled(z);
            view.setAlpha(z ? 1.0f : 0.3f);
            view.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void previous() {
        Timeline currentTimeline = this.player.getCurrentTimeline();
        if (!currentTimeline.isEmpty() && !this.player.isPlayingAd()) {
            currentTimeline.getWindow(this.player.getCurrentWindowIndex(), this.window);
            int previousWindowIndex = this.player.getPreviousWindowIndex();
            if (previousWindowIndex == -1 || (this.player.getCurrentPosition() > MAX_POSITION_FOR_SEEK_TO_PREVIOUS && (!this.window.isDynamic || this.window.isSeekable))) {
                seekTo(0);
            } else {
                seekTo(previousWindowIndex, C1379C.TIME_UNSET);
            }
        }
    }

    /* access modifiers changed from: private */
    public void next() {
        Timeline currentTimeline = this.player.getCurrentTimeline();
        if (!currentTimeline.isEmpty() && !this.player.isPlayingAd()) {
            int currentWindowIndex = this.player.getCurrentWindowIndex();
            int nextWindowIndex = this.player.getNextWindowIndex();
            if (nextWindowIndex != -1) {
                seekTo(nextWindowIndex, C1379C.TIME_UNSET);
            } else if (currentTimeline.getWindow(currentWindowIndex, this.window).isDynamic) {
                seekTo(currentWindowIndex, C1379C.TIME_UNSET);
            }
        }
    }

    /* access modifiers changed from: private */
    public void rewind() {
        if (this.rewindMs > 0) {
            seekTo(Math.max(this.player.getCurrentPosition() - ((long) this.rewindMs), 0));
        }
    }

    /* access modifiers changed from: private */
    public void fastForward() {
        if (this.fastForwardMs > 0) {
            long duration = this.player.getDuration();
            long currentPosition = this.player.getCurrentPosition() + ((long) this.fastForwardMs);
            if (duration != C1379C.TIME_UNSET) {
                currentPosition = Math.min(currentPosition, duration);
            }
            seekTo(currentPosition);
        }
    }

    private void seekTo(long j) {
        seekTo(this.player.getCurrentWindowIndex(), j);
    }

    private void seekTo(int i, long j) {
        if (!this.controlDispatcher.dispatchSeekTo(this.player, i, j)) {
            updateProgress();
        }
    }

    /* access modifiers changed from: private */
    public void seekToTimeBarPosition(long j) {
        int i;
        Timeline currentTimeline = this.player.getCurrentTimeline();
        if (this.multiWindowTimeBar && !currentTimeline.isEmpty()) {
            int windowCount = currentTimeline.getWindowCount();
            i = 0;
            while (true) {
                long durationMs = currentTimeline.getWindow(i, this.window).getDurationMs();
                if (j < durationMs) {
                    break;
                } else if (i == windowCount - 1) {
                    j = durationMs;
                    break;
                } else {
                    j -= durationMs;
                    i++;
                }
            }
        } else {
            i = this.player.getCurrentWindowIndex();
        }
        seekTo(i, j);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttachedToWindow = true;
        long j = this.hideAtMs;
        if (j != C1379C.TIME_UNSET) {
            long uptimeMillis = j - SystemClock.uptimeMillis();
            if (uptimeMillis <= 0) {
                hide();
            } else {
                postDelayed(this.hideAction, uptimeMillis);
            }
        } else if (isVisible()) {
            hideAfterTimeout();
        }
        updateAll();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAttachedToWindow = false;
        removeCallbacks(this.updateProgressAction);
        removeCallbacks(this.hideAction);
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            removeCallbacks(this.hideAction);
        } else if (motionEvent.getAction() == 1) {
            hideAfterTimeout();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (this.player == null || !isHandledMediaKey(keyCode)) {
            return false;
        }
        if (keyEvent.getAction() == 0) {
            if (keyCode == 90) {
                fastForward();
            } else if (keyCode == 89) {
                rewind();
            } else if (keyEvent.getRepeatCount() == 0) {
                if (keyCode == 85) {
                    ControlDispatcher controlDispatcher2 = this.controlDispatcher;
                    Player player2 = this.player;
                    controlDispatcher2.dispatchSetPlayWhenReady(player2, !player2.getPlayWhenReady());
                } else if (keyCode == 87) {
                    next();
                } else if (keyCode == 88) {
                    previous();
                } else if (keyCode == 126) {
                    this.controlDispatcher.dispatchSetPlayWhenReady(this.player, true);
                } else if (keyCode == 127) {
                    this.controlDispatcher.dispatchSetPlayWhenReady(this.player, false);
                }
            }
        }
        return true;
    }

    private boolean isPlaying() {
        Player player2 = this.player;
        if (player2 == null || player2.getPlaybackState() == 4 || this.player.getPlaybackState() == 1 || !this.player.getPlayWhenReady()) {
            return false;
        }
        return true;
    }

    private static boolean canShowMultiWindowTimeBar(Timeline timeline, Timeline.Window window2) {
        if (timeline.getWindowCount() > 100) {
            return false;
        }
        int windowCount = timeline.getWindowCount();
        for (int i = 0; i < windowCount; i++) {
            if (timeline.getWindow(i, window2).durationUs == C1379C.TIME_UNSET) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerControlView$ComponentListener */
    private final class ComponentListener implements Player.EventListener, TimeBar.OnScrubListener, View.OnClickListener {
        public /* synthetic */ void onLoadingChanged(boolean z) {
            Player.EventListener.CC.$default$onLoadingChanged(this, z);
        }

        public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Player.EventListener.CC.$default$onPlaybackParametersChanged(this, playbackParameters);
        }

        public /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
            Player.EventListener.CC.$default$onPlayerError(this, exoPlaybackException);
        }

        public /* synthetic */ void onSeekProcessed() {
            Player.EventListener.CC.$default$onSeekProcessed(this);
        }

        public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            Player.EventListener.CC.$default$onTracksChanged(this, trackGroupArray, trackSelectionArray);
        }

        private ComponentListener() {
        }

        public void onScrubStart(TimeBar timeBar, long j) {
            boolean unused = PlayerControlView.this.scrubbing = true;
        }

        public void onScrubMove(TimeBar timeBar, long j) {
            if (PlayerControlView.this.positionView != null) {
                PlayerControlView.this.positionView.setText(Util.getStringForTime(PlayerControlView.this.formatBuilder, PlayerControlView.this.formatter, j));
            }
        }

        public void onScrubStop(TimeBar timeBar, long j, boolean z) {
            boolean unused = PlayerControlView.this.scrubbing = false;
            if (!z && PlayerControlView.this.player != null) {
                PlayerControlView.this.seekToTimeBarPosition(j);
            }
        }

        public void onPlayerStateChanged(boolean z, int i) {
            PlayerControlView.this.updatePlayPauseButton();
            PlayerControlView.this.updateProgress();
        }

        public void onRepeatModeChanged(int i) {
            PlayerControlView.this.updateRepeatModeButton();
            PlayerControlView.this.updateNavigation();
        }

        public void onShuffleModeEnabledChanged(boolean z) {
            PlayerControlView.this.updateShuffleButton();
            PlayerControlView.this.updateNavigation();
        }

        public void onPositionDiscontinuity(int i) {
            PlayerControlView.this.updateNavigation();
            PlayerControlView.this.updateProgress();
        }

        public void onTimelineChanged(Timeline timeline, Object obj, int i) {
            PlayerControlView.this.updateNavigation();
            PlayerControlView.this.updateTimeBarMode();
            PlayerControlView.this.updateProgress();
        }

        public void onClick(View view) {
            if (PlayerControlView.this.player == null) {
                return;
            }
            if (PlayerControlView.this.nextButton == view) {
                PlayerControlView.this.next();
            } else if (PlayerControlView.this.previousButton == view) {
                PlayerControlView.this.previous();
            } else if (PlayerControlView.this.fastForwardButton == view) {
                PlayerControlView.this.fastForward();
            } else if (PlayerControlView.this.rewindButton == view) {
                PlayerControlView.this.rewind();
            } else if (PlayerControlView.this.playButton == view) {
                if (PlayerControlView.this.player.getPlaybackState() == 1) {
                    if (PlayerControlView.this.playbackPreparer != null) {
                        PlayerControlView.this.playbackPreparer.preparePlayback();
                    }
                } else if (PlayerControlView.this.player.getPlaybackState() == 4) {
                    PlayerControlView.this.controlDispatcher.dispatchSeekTo(PlayerControlView.this.player, PlayerControlView.this.player.getCurrentWindowIndex(), C1379C.TIME_UNSET);
                }
                PlayerControlView.this.controlDispatcher.dispatchSetPlayWhenReady(PlayerControlView.this.player, true);
            } else if (PlayerControlView.this.pauseButton == view) {
                PlayerControlView.this.controlDispatcher.dispatchSetPlayWhenReady(PlayerControlView.this.player, false);
            } else if (PlayerControlView.this.repeatToggleButton == view) {
                PlayerControlView.this.controlDispatcher.dispatchSetRepeatMode(PlayerControlView.this.player, RepeatModeUtil.getNextRepeatMode(PlayerControlView.this.player.getRepeatMode(), PlayerControlView.this.repeatToggleModes));
            } else if (PlayerControlView.this.shuffleButton == view) {
                PlayerControlView.this.controlDispatcher.dispatchSetShuffleModeEnabled(PlayerControlView.this.player, true ^ PlayerControlView.this.player.getShuffleModeEnabled());
            }
        }
    }
}
