package com.google.android.exoplayer2.source;

import android.os.Handler;
import com.google.android.exoplayer2.C1379C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class ConcatenatingMediaSource extends CompositeMediaSource<MediaSourceHolder> implements PlayerMessage.Target {
    private static final int MSG_ADD = 0;
    private static final int MSG_MOVE = 2;
    private static final int MSG_NOTIFY_LISTENER = 4;
    private static final int MSG_ON_COMPLETION = 5;
    private static final int MSG_REMOVE = 1;
    private static final int MSG_SET_SHUFFLE_ORDER = 3;
    private final boolean isAtomic;
    private boolean listenerNotificationScheduled;
    private final Map<MediaPeriod, MediaSourceHolder> mediaSourceByMediaPeriod;
    private final Map<Object, MediaSourceHolder> mediaSourceByUid;
    private final List<MediaSourceHolder> mediaSourceHolders;
    private final List<MediaSourceHolder> mediaSourcesPublic;
    private final List<Runnable> pendingOnCompletionActions;
    private final Timeline.Period period;
    private int periodCount;
    private ExoPlayer player;
    private Handler playerApplicationHandler;
    private ShuffleOrder shuffleOrder;
    private final boolean useLazyPreparation;
    private final Timeline.Window window;
    private int windowCount;

    public void maybeThrowSourceInfoRefreshError() throws IOException {
    }

    public ConcatenatingMediaSource(MediaSource... mediaSourceArr) {
        this(false, mediaSourceArr);
    }

    public ConcatenatingMediaSource(boolean z, MediaSource... mediaSourceArr) {
        this(z, new ShuffleOrder.DefaultShuffleOrder(0), mediaSourceArr);
    }

    public ConcatenatingMediaSource(boolean z, ShuffleOrder shuffleOrder2, MediaSource... mediaSourceArr) {
        this(z, false, shuffleOrder2, mediaSourceArr);
    }

    public ConcatenatingMediaSource(boolean z, boolean z2, ShuffleOrder shuffleOrder2, MediaSource... mediaSourceArr) {
        for (MediaSource checkNotNull : mediaSourceArr) {
            Assertions.checkNotNull(checkNotNull);
        }
        this.shuffleOrder = shuffleOrder2.getLength() > 0 ? shuffleOrder2.cloneAndClear() : shuffleOrder2;
        this.mediaSourceByMediaPeriod = new IdentityHashMap();
        this.mediaSourceByUid = new HashMap();
        this.mediaSourcesPublic = new ArrayList();
        this.mediaSourceHolders = new ArrayList();
        this.pendingOnCompletionActions = new ArrayList();
        this.isAtomic = z;
        this.useLazyPreparation = z2;
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        addMediaSources(Arrays.asList(mediaSourceArr));
    }

    public final synchronized void addMediaSource(MediaSource mediaSource) {
        addMediaSource(this.mediaSourcesPublic.size(), mediaSource, (Runnable) null);
    }

    public final synchronized void addMediaSource(MediaSource mediaSource, Runnable runnable) {
        addMediaSource(this.mediaSourcesPublic.size(), mediaSource, runnable);
    }

    public final synchronized void addMediaSource(int i, MediaSource mediaSource) {
        addMediaSource(i, mediaSource, (Runnable) null);
    }

    public final synchronized void addMediaSource(int i, MediaSource mediaSource, Runnable runnable) {
        addMediaSources(i, Collections.singletonList(mediaSource), runnable);
    }

    public final synchronized void addMediaSources(Collection<MediaSource> collection) {
        addMediaSources(this.mediaSourcesPublic.size(), collection, (Runnable) null);
    }

    public final synchronized void addMediaSources(Collection<MediaSource> collection, Runnable runnable) {
        addMediaSources(this.mediaSourcesPublic.size(), collection, runnable);
    }

    public final synchronized void addMediaSources(int i, Collection<MediaSource> collection) {
        addMediaSources(i, collection, (Runnable) null);
    }

    public final synchronized void addMediaSources(int i, Collection<MediaSource> collection, Runnable runnable) {
        for (MediaSource checkNotNull : collection) {
            Assertions.checkNotNull(checkNotNull);
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (MediaSource mediaSourceHolder : collection) {
            arrayList.add(new MediaSourceHolder(mediaSourceHolder));
        }
        this.mediaSourcesPublic.addAll(i, arrayList);
        if (this.player != null && !collection.isEmpty()) {
            this.player.createMessage(this).setType(0).setPayload(new MessageData(i, arrayList, runnable)).send();
        } else if (runnable != null) {
            runnable.run();
        }
    }

    public final synchronized void removeMediaSource(int i) {
        removeMediaSource(i, (Runnable) null);
    }

    public final synchronized void removeMediaSource(int i, Runnable runnable) {
        removeMediaSourceRange(i, i + 1, runnable);
    }

    public final synchronized void removeMediaSourceRange(int i, int i2) {
        removeMediaSourceRange(i, i2, (Runnable) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void removeMediaSourceRange(int r3, int r4, java.lang.Runnable r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.List<com.google.android.exoplayer2.source.ConcatenatingMediaSource$MediaSourceHolder> r0 = r2.mediaSourcesPublic     // Catch:{ all -> 0x0036 }
            com.google.android.exoplayer2.util.Util.removeRange(r0, r3, r4)     // Catch:{ all -> 0x0036 }
            if (r3 != r4) goto L_0x000f
            if (r5 == 0) goto L_0x000d
            r5.run()     // Catch:{ all -> 0x0036 }
        L_0x000d:
            monitor-exit(r2)
            return
        L_0x000f:
            com.google.android.exoplayer2.ExoPlayer r0 = r2.player     // Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x002f
            com.google.android.exoplayer2.ExoPlayer r0 = r2.player     // Catch:{ all -> 0x0036 }
            com.google.android.exoplayer2.PlayerMessage r0 = r0.createMessage(r2)     // Catch:{ all -> 0x0036 }
            r1 = 1
            com.google.android.exoplayer2.PlayerMessage r0 = r0.setType(r1)     // Catch:{ all -> 0x0036 }
            com.google.android.exoplayer2.source.ConcatenatingMediaSource$MessageData r1 = new com.google.android.exoplayer2.source.ConcatenatingMediaSource$MessageData     // Catch:{ all -> 0x0036 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0036 }
            r1.<init>(r3, r4, r5)     // Catch:{ all -> 0x0036 }
            com.google.android.exoplayer2.PlayerMessage r3 = r0.setPayload(r1)     // Catch:{ all -> 0x0036 }
            r3.send()     // Catch:{ all -> 0x0036 }
            goto L_0x0034
        L_0x002f:
            if (r5 == 0) goto L_0x0034
            r5.run()     // Catch:{ all -> 0x0036 }
        L_0x0034:
            monitor-exit(r2)
            return
        L_0x0036:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ConcatenatingMediaSource.removeMediaSourceRange(int, int, java.lang.Runnable):void");
    }

    public final synchronized void moveMediaSource(int i, int i2) {
        moveMediaSource(i, i2, (Runnable) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0009, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void moveMediaSource(int r3, int r4, java.lang.Runnable r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 != r4) goto L_0x000a
            if (r5 == 0) goto L_0x0008
            r5.run()     // Catch:{ all -> 0x003c }
        L_0x0008:
            monitor-exit(r2)
            return
        L_0x000a:
            java.util.List<com.google.android.exoplayer2.source.ConcatenatingMediaSource$MediaSourceHolder> r0 = r2.mediaSourcesPublic     // Catch:{ all -> 0x003c }
            java.util.List<com.google.android.exoplayer2.source.ConcatenatingMediaSource$MediaSourceHolder> r1 = r2.mediaSourcesPublic     // Catch:{ all -> 0x003c }
            java.lang.Object r1 = r1.remove(r3)     // Catch:{ all -> 0x003c }
            r0.add(r4, r1)     // Catch:{ all -> 0x003c }
            com.google.android.exoplayer2.ExoPlayer r0 = r2.player     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x0035
            com.google.android.exoplayer2.ExoPlayer r0 = r2.player     // Catch:{ all -> 0x003c }
            com.google.android.exoplayer2.PlayerMessage r0 = r0.createMessage(r2)     // Catch:{ all -> 0x003c }
            r1 = 2
            com.google.android.exoplayer2.PlayerMessage r0 = r0.setType(r1)     // Catch:{ all -> 0x003c }
            com.google.android.exoplayer2.source.ConcatenatingMediaSource$MessageData r1 = new com.google.android.exoplayer2.source.ConcatenatingMediaSource$MessageData     // Catch:{ all -> 0x003c }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x003c }
            r1.<init>(r3, r4, r5)     // Catch:{ all -> 0x003c }
            com.google.android.exoplayer2.PlayerMessage r3 = r0.setPayload(r1)     // Catch:{ all -> 0x003c }
            r3.send()     // Catch:{ all -> 0x003c }
            goto L_0x003a
        L_0x0035:
            if (r5 == 0) goto L_0x003a
            r5.run()     // Catch:{ all -> 0x003c }
        L_0x003a:
            monitor-exit(r2)
            return
        L_0x003c:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ConcatenatingMediaSource.moveMediaSource(int, int, java.lang.Runnable):void");
    }

    public final synchronized void clear() {
        clear((Runnable) null);
    }

    public final synchronized void clear(Runnable runnable) {
        removeMediaSourceRange(0, getSize(), runnable);
    }

    public final synchronized int getSize() {
        return this.mediaSourcesPublic.size();
    }

    public final synchronized MediaSource getMediaSource(int i) {
        return this.mediaSourcesPublic.get(i).mediaSource;
    }

    public final synchronized void setShuffleOrder(ShuffleOrder shuffleOrder2) {
        setShuffleOrder(shuffleOrder2, (Runnable) null);
    }

    public final synchronized void setShuffleOrder(ShuffleOrder shuffleOrder2, Runnable runnable) {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            int size = getSize();
            if (shuffleOrder2.getLength() != size) {
                shuffleOrder2 = shuffleOrder2.cloneAndClear().cloneAndInsert(0, size);
            }
            exoPlayer.createMessage(this).setType(3).setPayload(new MessageData(0, shuffleOrder2, runnable)).send();
        } else {
            if (shuffleOrder2.getLength() > 0) {
                shuffleOrder2 = shuffleOrder2.cloneAndClear();
            }
            this.shuffleOrder = shuffleOrder2;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public final synchronized void prepareSourceInternal(ExoPlayer exoPlayer, boolean z, TransferListener transferListener) {
        super.prepareSourceInternal(exoPlayer, z, transferListener);
        this.player = exoPlayer;
        this.playerApplicationHandler = new Handler(exoPlayer.getApplicationLooper());
        if (this.mediaSourcesPublic.isEmpty()) {
            notifyListener();
        } else {
            this.shuffleOrder = this.shuffleOrder.cloneAndInsert(0, this.mediaSourcesPublic.size());
            addMediaSourcesInternal(0, this.mediaSourcesPublic);
            scheduleListenerNotification((Runnable) null);
        }
    }

    public final MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator) {
        MediaSourceHolder mediaSourceHolder = this.mediaSourceByUid.get(getMediaSourceHolderUid(mediaPeriodId.periodUid));
        if (mediaSourceHolder == null) {
            mediaSourceHolder = new MediaSourceHolder(new DummyMediaSource());
            mediaSourceHolder.hasStartedPreparing = true;
        }
        DeferredMediaPeriod deferredMediaPeriod = new DeferredMediaPeriod(mediaSourceHolder.mediaSource, mediaPeriodId, allocator);
        this.mediaSourceByMediaPeriod.put(deferredMediaPeriod, mediaSourceHolder);
        mediaSourceHolder.activeMediaPeriods.add(deferredMediaPeriod);
        if (!mediaSourceHolder.hasStartedPreparing) {
            mediaSourceHolder.hasStartedPreparing = true;
            prepareChildSource(mediaSourceHolder, mediaSourceHolder.mediaSource);
        } else if (mediaSourceHolder.isPrepared) {
            deferredMediaPeriod.createPeriod(mediaPeriodId.copyWithPeriodUid(getChildPeriodUid(mediaSourceHolder, mediaPeriodId.periodUid)));
        }
        return deferredMediaPeriod;
    }

    public final void releasePeriod(MediaPeriod mediaPeriod) {
        MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) Assertions.checkNotNull(this.mediaSourceByMediaPeriod.remove(mediaPeriod));
        ((DeferredMediaPeriod) mediaPeriod).releasePeriod();
        mediaSourceHolder.activeMediaPeriods.remove(mediaPeriod);
        maybeReleaseChildSource(mediaSourceHolder);
    }

    public final void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.mediaSourceHolders.clear();
        this.mediaSourceByUid.clear();
        this.player = null;
        this.playerApplicationHandler = null;
        this.shuffleOrder = this.shuffleOrder.cloneAndClear();
        this.windowCount = 0;
        this.periodCount = 0;
    }

    /* access modifiers changed from: protected */
    public final void onChildSourceInfoRefreshed(MediaSourceHolder mediaSourceHolder, MediaSource mediaSource, Timeline timeline, Object obj) {
        updateMediaSourceInternal(mediaSourceHolder, timeline);
    }

    /* access modifiers changed from: protected */
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(MediaSourceHolder mediaSourceHolder, MediaSource.MediaPeriodId mediaPeriodId) {
        for (int i = 0; i < mediaSourceHolder.activeMediaPeriods.size(); i++) {
            if (mediaSourceHolder.activeMediaPeriods.get(i).f139id.windowSequenceNumber == mediaPeriodId.windowSequenceNumber) {
                return mediaPeriodId.copyWithPeriodUid(getPeriodUid(mediaSourceHolder, mediaPeriodId.periodUid));
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int getWindowIndexForChildWindowIndex(MediaSourceHolder mediaSourceHolder, int i) {
        return i + mediaSourceHolder.firstWindowIndexInChild;
    }

    public final void handleMessage(int i, Object obj) throws ExoPlaybackException {
        if (this.player != null) {
            if (i == 0) {
                MessageData messageData = (MessageData) Util.castNonNull(obj);
                this.shuffleOrder = this.shuffleOrder.cloneAndInsert(messageData.index, ((Collection) messageData.customData).size());
                addMediaSourcesInternal(messageData.index, (Collection) messageData.customData);
                scheduleListenerNotification(messageData.actionOnCompletion);
            } else if (i == 1) {
                MessageData messageData2 = (MessageData) Util.castNonNull(obj);
                int i2 = messageData2.index;
                int intValue = ((Integer) messageData2.customData).intValue();
                if (i2 == 0 && intValue == this.shuffleOrder.getLength()) {
                    this.shuffleOrder = this.shuffleOrder.cloneAndClear();
                } else {
                    this.shuffleOrder = this.shuffleOrder.cloneAndRemove(i2, intValue);
                }
                for (int i3 = intValue - 1; i3 >= i2; i3--) {
                    removeMediaSourceInternal(i3);
                }
                scheduleListenerNotification(messageData2.actionOnCompletion);
            } else if (i == 2) {
                MessageData messageData3 = (MessageData) Util.castNonNull(obj);
                ShuffleOrder cloneAndRemove = this.shuffleOrder.cloneAndRemove(messageData3.index, messageData3.index + 1);
                this.shuffleOrder = cloneAndRemove;
                this.shuffleOrder = cloneAndRemove.cloneAndInsert(((Integer) messageData3.customData).intValue(), 1);
                moveMediaSourceInternal(messageData3.index, ((Integer) messageData3.customData).intValue());
                scheduleListenerNotification(messageData3.actionOnCompletion);
            } else if (i == 3) {
                MessageData messageData4 = (MessageData) Util.castNonNull(obj);
                this.shuffleOrder = (ShuffleOrder) messageData4.customData;
                scheduleListenerNotification(messageData4.actionOnCompletion);
            } else if (i == 4) {
                notifyListener();
            } else if (i == 5) {
                List list = (List) Util.castNonNull(obj);
                Handler handler = (Handler) Assertions.checkNotNull(this.playerApplicationHandler);
                for (int i4 = 0; i4 < list.size(); i4++) {
                    handler.post((Runnable) list.get(i4));
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private void scheduleListenerNotification(Runnable runnable) {
        if (!this.listenerNotificationScheduled) {
            ((ExoPlayer) Assertions.checkNotNull(this.player)).createMessage(this).setType(4).send();
            this.listenerNotificationScheduled = true;
        }
        if (runnable != null) {
            this.pendingOnCompletionActions.add(runnable);
        }
    }

    private void notifyListener() {
        this.listenerNotificationScheduled = false;
        List emptyList = this.pendingOnCompletionActions.isEmpty() ? Collections.emptyList() : new ArrayList(this.pendingOnCompletionActions);
        this.pendingOnCompletionActions.clear();
        refreshSourceInfo(new ConcatenatedTimeline(this.mediaSourceHolders, this.windowCount, this.periodCount, this.shuffleOrder, this.isAtomic), (Object) null);
        if (!emptyList.isEmpty()) {
            ((ExoPlayer) Assertions.checkNotNull(this.player)).createMessage(this).setType(5).setPayload(emptyList).send();
        }
    }

    private void addMediaSourcesInternal(int i, Collection<MediaSourceHolder> collection) {
        for (MediaSourceHolder addMediaSourceInternal : collection) {
            addMediaSourceInternal(i, addMediaSourceInternal);
            i++;
        }
    }

    private void addMediaSourceInternal(int i, MediaSourceHolder mediaSourceHolder) {
        if (i > 0) {
            MediaSourceHolder mediaSourceHolder2 = this.mediaSourceHolders.get(i - 1);
            mediaSourceHolder.reset(i, mediaSourceHolder2.firstWindowIndexInChild + mediaSourceHolder2.timeline.getWindowCount(), mediaSourceHolder2.firstPeriodIndexInChild + mediaSourceHolder2.timeline.getPeriodCount());
        } else {
            mediaSourceHolder.reset(i, 0, 0);
        }
        correctOffsets(i, 1, mediaSourceHolder.timeline.getWindowCount(), mediaSourceHolder.timeline.getPeriodCount());
        this.mediaSourceHolders.add(i, mediaSourceHolder);
        this.mediaSourceByUid.put(mediaSourceHolder.uid, mediaSourceHolder);
        if (!this.useLazyPreparation) {
            mediaSourceHolder.hasStartedPreparing = true;
            prepareChildSource(mediaSourceHolder, mediaSourceHolder.mediaSource);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0099  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateMediaSourceInternal(com.google.android.exoplayer2.source.ConcatenatingMediaSource.MediaSourceHolder r12, com.google.android.exoplayer2.Timeline r13) {
        /*
            r11 = this;
            if (r12 == 0) goto L_0x00b3
            com.google.android.exoplayer2.source.ConcatenatingMediaSource$DeferredTimeline r1 = r12.timeline
            com.google.android.exoplayer2.Timeline r2 = r1.getTimeline()
            if (r2 != r13) goto L_0x000b
            return
        L_0x000b:
            int r2 = r13.getWindowCount()
            int r3 = r1.getWindowCount()
            int r2 = r2 - r3
            int r3 = r13.getPeriodCount()
            int r4 = r1.getPeriodCount()
            int r3 = r3 - r4
            r4 = 0
            r7 = 1
            if (r2 != 0) goto L_0x0023
            if (r3 == 0) goto L_0x0029
        L_0x0023:
            int r5 = r12.childIndex
            int r5 = r5 + r7
            r11.correctOffsets(r5, r4, r2, r3)
        L_0x0029:
            boolean r2 = r12.isPrepared
            r8 = 0
            if (r2 == 0) goto L_0x0036
            com.google.android.exoplayer2.source.ConcatenatingMediaSource$DeferredTimeline r1 = r1.cloneWithUpdatedTimeline(r13)
            r12.timeline = r1
            goto L_0x00ad
        L_0x0036:
            boolean r1 = r13.isEmpty()
            if (r1 == 0) goto L_0x0047
            java.lang.Object r1 = com.google.android.exoplayer2.source.ConcatenatingMediaSource.DeferredTimeline.DUMMY_ID
            com.google.android.exoplayer2.source.ConcatenatingMediaSource$DeferredTimeline r1 = com.google.android.exoplayer2.source.ConcatenatingMediaSource.DeferredTimeline.createWithRealTimeline(r13, r1)
            r12.timeline = r1
            goto L_0x00ad
        L_0x0047:
            java.util.List<com.google.android.exoplayer2.source.DeferredMediaPeriod> r1 = r12.activeMediaPeriods
            int r1 = r1.size()
            if (r1 > r7) goto L_0x0051
            r1 = 1
            goto L_0x0052
        L_0x0051:
            r1 = 0
        L_0x0052:
            com.google.android.exoplayer2.util.Assertions.checkState(r1)
            java.util.List<com.google.android.exoplayer2.source.DeferredMediaPeriod> r1 = r12.activeMediaPeriods
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x005f
            r9 = r8
            goto L_0x0068
        L_0x005f:
            java.util.List<com.google.android.exoplayer2.source.DeferredMediaPeriod> r1 = r12.activeMediaPeriods
            java.lang.Object r1 = r1.get(r4)
            com.google.android.exoplayer2.source.DeferredMediaPeriod r1 = (com.google.android.exoplayer2.source.DeferredMediaPeriod) r1
            r9 = r1
        L_0x0068:
            com.google.android.exoplayer2.Timeline$Window r1 = r11.window
            long r1 = r1.getDefaultPositionUs()
            if (r9 == 0) goto L_0x007c
            long r3 = r9.getPreparePositionUs()
            r5 = 0
            int r10 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r10 == 0) goto L_0x007c
            r5 = r3
            goto L_0x007d
        L_0x007c:
            r5 = r1
        L_0x007d:
            com.google.android.exoplayer2.Timeline$Window r2 = r11.window
            com.google.android.exoplayer2.Timeline$Period r3 = r11.period
            r4 = 0
            r1 = r13
            android.util.Pair r1 = r1.getPeriodPosition(r2, r3, r4, r5)
            java.lang.Object r2 = r1.first
            java.lang.Object r1 = r1.second
            java.lang.Long r1 = (java.lang.Long) r1
            long r3 = r1.longValue()
            com.google.android.exoplayer2.source.ConcatenatingMediaSource$DeferredTimeline r1 = com.google.android.exoplayer2.source.ConcatenatingMediaSource.DeferredTimeline.createWithRealTimeline(r13, r2)
            r12.timeline = r1
            if (r9 == 0) goto L_0x00ad
            r9.overridePreparePositionUs(r3)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r9.f139id
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r9.f139id
            java.lang.Object r2 = r2.periodUid
            java.lang.Object r2 = getChildPeriodUid(r12, r2)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r1.copyWithPeriodUid(r2)
            r9.createPeriod(r1)
        L_0x00ad:
            r12.isPrepared = r7
            r11.scheduleListenerNotification(r8)
            return
        L_0x00b3:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ConcatenatingMediaSource.updateMediaSourceInternal(com.google.android.exoplayer2.source.ConcatenatingMediaSource$MediaSourceHolder, com.google.android.exoplayer2.Timeline):void");
    }

    private void removeMediaSourceInternal(int i) {
        MediaSourceHolder remove = this.mediaSourceHolders.remove(i);
        this.mediaSourceByUid.remove(remove.uid);
        DeferredTimeline deferredTimeline = remove.timeline;
        correctOffsets(i, -1, -deferredTimeline.getWindowCount(), -deferredTimeline.getPeriodCount());
        remove.isRemoved = true;
        maybeReleaseChildSource(remove);
    }

    private void moveMediaSourceInternal(int i, int i2) {
        int min = Math.min(i, i2);
        int max = Math.max(i, i2);
        int i3 = this.mediaSourceHolders.get(min).firstWindowIndexInChild;
        int i4 = this.mediaSourceHolders.get(min).firstPeriodIndexInChild;
        List<MediaSourceHolder> list = this.mediaSourceHolders;
        list.add(i2, list.remove(i));
        while (min <= max) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(min);
            mediaSourceHolder.firstWindowIndexInChild = i3;
            mediaSourceHolder.firstPeriodIndexInChild = i4;
            i3 += mediaSourceHolder.timeline.getWindowCount();
            i4 += mediaSourceHolder.timeline.getPeriodCount();
            min++;
        }
    }

    private void correctOffsets(int i, int i2, int i3, int i4) {
        this.windowCount += i3;
        this.periodCount += i4;
        while (i < this.mediaSourceHolders.size()) {
            this.mediaSourceHolders.get(i).childIndex += i2;
            this.mediaSourceHolders.get(i).firstWindowIndexInChild += i3;
            this.mediaSourceHolders.get(i).firstPeriodIndexInChild += i4;
            i++;
        }
    }

    private void maybeReleaseChildSource(MediaSourceHolder mediaSourceHolder) {
        if (mediaSourceHolder.isRemoved && mediaSourceHolder.hasStartedPreparing && mediaSourceHolder.activeMediaPeriods.isEmpty()) {
            releaseChildSource(mediaSourceHolder);
        }
    }

    private static Object getMediaSourceHolderUid(Object obj) {
        return ConcatenatedTimeline.getChildTimelineUidFromConcatenatedUid(obj);
    }

    private static Object getChildPeriodUid(MediaSourceHolder mediaSourceHolder, Object obj) {
        Object childPeriodUidFromConcatenatedUid = ConcatenatedTimeline.getChildPeriodUidFromConcatenatedUid(obj);
        return childPeriodUidFromConcatenatedUid.equals(DeferredTimeline.DUMMY_ID) ? mediaSourceHolder.timeline.replacedId : childPeriodUidFromConcatenatedUid;
    }

    private static Object getPeriodUid(MediaSourceHolder mediaSourceHolder, Object obj) {
        if (mediaSourceHolder.timeline.replacedId.equals(obj)) {
            obj = DeferredTimeline.DUMMY_ID;
        }
        return ConcatenatedTimeline.getConcatenatedUid(mediaSourceHolder.uid, obj);
    }

    static final class MediaSourceHolder implements Comparable<MediaSourceHolder> {
        public List<DeferredMediaPeriod> activeMediaPeriods = new ArrayList();
        public int childIndex;
        public int firstPeriodIndexInChild;
        public int firstWindowIndexInChild;
        public boolean hasStartedPreparing;
        public boolean isPrepared;
        public boolean isRemoved;
        public final MediaSource mediaSource;
        public DeferredTimeline timeline = new DeferredTimeline();
        public final Object uid = new Object();

        public MediaSourceHolder(MediaSource mediaSource2) {
            this.mediaSource = mediaSource2;
        }

        public void reset(int i, int i2, int i3) {
            this.childIndex = i;
            this.firstWindowIndexInChild = i2;
            this.firstPeriodIndexInChild = i3;
            this.hasStartedPreparing = false;
            this.isPrepared = false;
            this.isRemoved = false;
            this.activeMediaPeriods.clear();
        }

        public int compareTo(MediaSourceHolder mediaSourceHolder) {
            return this.firstPeriodIndexInChild - mediaSourceHolder.firstPeriodIndexInChild;
        }
    }

    private static final class MessageData<T> {
        public final Runnable actionOnCompletion;
        public final T customData;
        public final int index;

        public MessageData(int i, T t, Runnable runnable) {
            this.index = i;
            this.actionOnCompletion = runnable;
            this.customData = t;
        }
    }

    private static final class ConcatenatedTimeline extends AbstractConcatenatedTimeline {
        private final HashMap<Object, Integer> childIndexByUid = new HashMap<>();
        private final int[] firstPeriodInChildIndices;
        private final int[] firstWindowInChildIndices;
        private final int periodCount;
        private final Timeline[] timelines;
        private final Object[] uids;
        private final int windowCount;

        public ConcatenatedTimeline(Collection<MediaSourceHolder> collection, int i, int i2, ShuffleOrder shuffleOrder, boolean z) {
            super(z, shuffleOrder);
            this.windowCount = i;
            this.periodCount = i2;
            int size = collection.size();
            this.firstPeriodInChildIndices = new int[size];
            this.firstWindowInChildIndices = new int[size];
            this.timelines = new Timeline[size];
            this.uids = new Object[size];
            int i3 = 0;
            for (MediaSourceHolder next : collection) {
                this.timelines[i3] = next.timeline;
                this.firstPeriodInChildIndices[i3] = next.firstPeriodIndexInChild;
                this.firstWindowInChildIndices[i3] = next.firstWindowIndexInChild;
                this.uids[i3] = next.uid;
                this.childIndexByUid.put(this.uids[i3], Integer.valueOf(i3));
                i3++;
            }
        }

        /* access modifiers changed from: protected */
        public int getChildIndexByPeriodIndex(int i) {
            return Util.binarySearchFloor(this.firstPeriodInChildIndices, i + 1, false, false);
        }

        /* access modifiers changed from: protected */
        public int getChildIndexByWindowIndex(int i) {
            return Util.binarySearchFloor(this.firstWindowInChildIndices, i + 1, false, false);
        }

        /* access modifiers changed from: protected */
        public int getChildIndexByChildUid(Object obj) {
            Integer num = this.childIndexByUid.get(obj);
            if (num == null) {
                return -1;
            }
            return num.intValue();
        }

        /* access modifiers changed from: protected */
        public Timeline getTimelineByChildIndex(int i) {
            return this.timelines[i];
        }

        /* access modifiers changed from: protected */
        public int getFirstPeriodIndexByChildIndex(int i) {
            return this.firstPeriodInChildIndices[i];
        }

        /* access modifiers changed from: protected */
        public int getFirstWindowIndexByChildIndex(int i) {
            return this.firstWindowInChildIndices[i];
        }

        /* access modifiers changed from: protected */
        public Object getChildUidByChildIndex(int i) {
            return this.uids[i];
        }

        public int getWindowCount() {
            return this.windowCount;
        }

        public int getPeriodCount() {
            return this.periodCount;
        }
    }

    private static final class DeferredTimeline extends ForwardingTimeline {
        /* access modifiers changed from: private */
        public static final Object DUMMY_ID = new Object();
        private static final DummyTimeline DUMMY_TIMELINE = new DummyTimeline();
        /* access modifiers changed from: private */
        public final Object replacedId;

        public static DeferredTimeline createWithRealTimeline(Timeline timeline, Object obj) {
            return new DeferredTimeline(timeline, obj);
        }

        public DeferredTimeline() {
            this(DUMMY_TIMELINE, DUMMY_ID);
        }

        private DeferredTimeline(Timeline timeline, Object obj) {
            super(timeline);
            this.replacedId = obj;
        }

        public DeferredTimeline cloneWithUpdatedTimeline(Timeline timeline) {
            return new DeferredTimeline(timeline, this.replacedId);
        }

        public Timeline getTimeline() {
            return this.timeline;
        }

        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            this.timeline.getPeriod(i, period, z);
            if (Util.areEqual(period.uid, this.replacedId)) {
                period.uid = DUMMY_ID;
            }
            return period;
        }

        public int getIndexOfPeriod(Object obj) {
            Timeline timeline = this.timeline;
            if (DUMMY_ID.equals(obj)) {
                obj = this.replacedId;
            }
            return timeline.getIndexOfPeriod(obj);
        }

        public Object getUidOfPeriod(int i) {
            Object uidOfPeriod = this.timeline.getUidOfPeriod(i);
            return Util.areEqual(uidOfPeriod, this.replacedId) ? DUMMY_ID : uidOfPeriod;
        }
    }

    private static final class DummyTimeline extends Timeline {
        public int getPeriodCount() {
            return 1;
        }

        public int getWindowCount() {
            return 1;
        }

        private DummyTimeline() {
        }

        public Timeline.Window getWindow(int i, Timeline.Window window, boolean z, long j) {
            return window.set((Object) null, C1379C.TIME_UNSET, C1379C.TIME_UNSET, false, true, 0, C1379C.TIME_UNSET, 0, 0, 0);
        }

        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            return period.set(0, DeferredTimeline.DUMMY_ID, 0, C1379C.TIME_UNSET, 0);
        }

        public int getIndexOfPeriod(Object obj) {
            return obj == DeferredTimeline.DUMMY_ID ? 0 : -1;
        }

        public Object getUidOfPeriod(int i) {
            return DeferredTimeline.DUMMY_ID;
        }
    }

    private static final class DummyMediaSource extends BaseMediaSource {
        public void maybeThrowSourceInfoRefreshError() throws IOException {
        }

        /* access modifiers changed from: protected */
        public void prepareSourceInternal(ExoPlayer exoPlayer, boolean z, TransferListener transferListener) {
        }

        public void releasePeriod(MediaPeriod mediaPeriod) {
        }

        /* access modifiers changed from: protected */
        public void releaseSourceInternal() {
        }

        private DummyMediaSource() {
        }

        public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator) {
            throw new UnsupportedOperationException();
        }
    }
}
