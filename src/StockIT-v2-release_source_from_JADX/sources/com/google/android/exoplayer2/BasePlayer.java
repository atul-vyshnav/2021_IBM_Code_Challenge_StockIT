package com.google.android.exoplayer2;

import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.util.Util;

public abstract class BasePlayer implements Player {
    protected final Timeline.Window window = new Timeline.Window();

    public final void seekToDefaultPosition() {
        seekToDefaultPosition(getCurrentWindowIndex());
    }

    public final void seekToDefaultPosition(int i) {
        seekTo(i, C1379C.TIME_UNSET);
    }

    public final void seekTo(long j) {
        seekTo(getCurrentWindowIndex(), j);
    }

    public final boolean hasPrevious() {
        return getPreviousWindowIndex() != -1;
    }

    public final void previous() {
        int previousWindowIndex = getPreviousWindowIndex();
        if (previousWindowIndex != -1) {
            seekToDefaultPosition(previousWindowIndex);
        }
    }

    public final boolean hasNext() {
        return getNextWindowIndex() != -1;
    }

    public final void next() {
        int nextWindowIndex = getNextWindowIndex();
        if (nextWindowIndex != -1) {
            seekToDefaultPosition(nextWindowIndex);
        }
    }

    public final void stop() {
        stop(false);
    }

    public final int getNextWindowIndex() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -1;
        }
        return currentTimeline.getNextWindowIndex(getCurrentWindowIndex(), getRepeatModeForNavigation(), getShuffleModeEnabled());
    }

    public final int getPreviousWindowIndex() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -1;
        }
        return currentTimeline.getPreviousWindowIndex(getCurrentWindowIndex(), getRepeatModeForNavigation(), getShuffleModeEnabled());
    }

    public final Object getCurrentTag() {
        int currentWindowIndex = getCurrentWindowIndex();
        Timeline currentTimeline = getCurrentTimeline();
        if (currentWindowIndex >= currentTimeline.getWindowCount()) {
            return null;
        }
        return currentTimeline.getWindow(currentWindowIndex, this.window, true).tag;
    }

    public final int getBufferedPercentage() {
        long bufferedPosition = getBufferedPosition();
        long duration = getDuration();
        if (bufferedPosition == C1379C.TIME_UNSET || duration == C1379C.TIME_UNSET) {
            return 0;
        }
        if (duration == 0) {
            return 100;
        }
        return Util.constrainValue((int) ((bufferedPosition * 100) / duration), 0, 100);
    }

    public final boolean isCurrentWindowDynamic() {
        Timeline currentTimeline = getCurrentTimeline();
        return !currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).isDynamic;
    }

    public final boolean isCurrentWindowSeekable() {
        Timeline currentTimeline = getCurrentTimeline();
        return !currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).isSeekable;
    }

    public final long getContentDuration() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C1379C.TIME_UNSET;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
    }

    private int getRepeatModeForNavigation() {
        int repeatMode = getRepeatMode();
        if (repeatMode == 1) {
            return 0;
        }
        return repeatMode;
    }
}
