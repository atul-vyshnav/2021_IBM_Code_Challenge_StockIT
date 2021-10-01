package com.facebook.react.fabric;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import com.facebook.common.logging.FLog;
import com.facebook.debug.holder.PrinterHolder;
import com.facebook.debug.tags.ReactDebugOverlayTags;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.NativeMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.ReactNoCrashSoftException;
import com.facebook.react.bridge.ReactSoftException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.RetryableMountingLayerException;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.react.fabric.events.EventBeatManager;
import com.facebook.react.fabric.events.EventEmitterWrapper;
import com.facebook.react.fabric.events.FabricEventEmitter;
import com.facebook.react.fabric.mounting.LayoutMetricsConversions;
import com.facebook.react.fabric.mounting.MountingManager;
import com.facebook.react.fabric.mounting.mountitems.BatchMountItem;
import com.facebook.react.fabric.mounting.mountitems.CreateMountItem;
import com.facebook.react.fabric.mounting.mountitems.DeleteMountItem;
import com.facebook.react.fabric.mounting.mountitems.DispatchCommandMountItem;
import com.facebook.react.fabric.mounting.mountitems.DispatchIntCommandMountItem;
import com.facebook.react.fabric.mounting.mountitems.DispatchStringCommandMountItem;
import com.facebook.react.fabric.mounting.mountitems.InsertMountItem;
import com.facebook.react.fabric.mounting.mountitems.MountItem;
import com.facebook.react.fabric.mounting.mountitems.PreAllocateViewMountItem;
import com.facebook.react.fabric.mounting.mountitems.RemoveDeleteMultiMountItem;
import com.facebook.react.fabric.mounting.mountitems.RemoveMountItem;
import com.facebook.react.fabric.mounting.mountitems.SendAccessibilityEvent;
import com.facebook.react.fabric.mounting.mountitems.UpdateEventEmitterMountItem;
import com.facebook.react.fabric.mounting.mountitems.UpdateLayoutMountItem;
import com.facebook.react.fabric.mounting.mountitems.UpdateLocalDataMountItem;
import com.facebook.react.fabric.mounting.mountitems.UpdatePaddingMountItem;
import com.facebook.react.fabric.mounting.mountitems.UpdatePropsMountItem;
import com.facebook.react.fabric.mounting.mountitems.UpdateStateMountItem;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.ReactRoot;
import com.facebook.react.uimanager.ReactRootViewTagGenerator;
import com.facebook.react.uimanager.StateWrapper;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerPropertyUpdater;
import com.facebook.react.uimanager.ViewManagerRegistry;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.systrace.Systrace;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FabricUIManager implements UIManager, LifecycleEventListener {
    public static final boolean ENABLE_FABRIC_LOGS = (ReactFeatureFlags.enableFabricLogs || PrinterHolder.getPrinter().shouldDisplayLogMessage(ReactDebugOverlayTags.FABRIC_UI_MANAGER));
    private static final int FRAME_TIME_MS = 16;
    public static final boolean IS_DEVELOPMENT_ENVIRONMENT = false;
    private static final int MAX_TIME_IN_FRAME_FOR_NON_BATCHED_OPERATIONS_MS = 8;
    private static final int PRE_MOUNT_ITEMS_INITIAL_SIZE_ARRAY = 250;
    public static final String TAG = "FabricUIManager";
    private long mBatchedExecutionTime = 0;
    private Binding mBinding;
    private long mCommitStartTime = 0;
    private int mCurrentSynchronousCommitNumber = 10000;
    /* access modifiers changed from: private */
    public volatile boolean mDestroyed = false;
    /* access modifiers changed from: private */
    public final DispatchUIFrameCallback mDispatchUIFrameCallback;
    private long mDispatchViewUpdatesTime = 0;
    private final EventBeatManager mEventBeatManager;
    private final EventDispatcher mEventDispatcher;
    private long mFinishTransactionCPPTime = 0;
    private long mFinishTransactionTime = 0;
    private boolean mInDispatch = false;
    private long mLayoutTime = 0;
    private List<MountItem> mMountItems = new ArrayList();
    private final Object mMountItemsLock = new Object();
    private final MountingManager mMountingManager;
    private ArrayDeque<MountItem> mPreMountItems = new ArrayDeque<>(250);
    private final Object mPreMountItemsLock = new Object();
    private int mReDispatchCounter = 0;
    private final ReactApplicationContext mReactApplicationContext;
    private final ConcurrentHashMap<Integer, ThemedReactContext> mReactContextForRootTag = new ConcurrentHashMap<>();
    private long mRunStartTime = 0;
    private List<DispatchCommandMountItem> mViewCommandMountItems = new ArrayList();
    private final Object mViewCommandMountItemsLock = new Object();

    public void onHostDestroy() {
    }

    public void profileNextBatch() {
    }

    static {
        FabricSoLoader.staticInit();
    }

    public FabricUIManager(ReactApplicationContext reactApplicationContext, ViewManagerRegistry viewManagerRegistry, EventDispatcher eventDispatcher, EventBeatManager eventBeatManager) {
        this.mDispatchUIFrameCallback = new DispatchUIFrameCallback(reactApplicationContext);
        this.mReactApplicationContext = reactApplicationContext;
        this.mMountingManager = new MountingManager(viewManagerRegistry);
        this.mEventDispatcher = eventDispatcher;
        this.mEventBeatManager = eventBeatManager;
        this.mReactApplicationContext.addLifecycleEventListener(this);
    }

    public <T extends View> int addRootView(T t, WritableMap writableMap, String str) {
        int nextRootViewTag = ReactRootViewTagGenerator.getNextRootViewTag();
        ReactRoot reactRoot = (ReactRoot) t;
        ThemedReactContext themedReactContext = new ThemedReactContext(this.mReactApplicationContext, t.getContext(), reactRoot.getSurfaceID());
        this.mMountingManager.addRootView(nextRootViewTag, t);
        String jSModuleName = reactRoot.getJSModuleName();
        this.mReactContextForRootTag.put(Integer.valueOf(nextRootViewTag), themedReactContext);
        if (ENABLE_FABRIC_LOGS) {
            FLog.m54d(TAG, "Starting surface for module: %s and reactTag: %d", (Object) jSModuleName, (Object) Integer.valueOf(nextRootViewTag));
        }
        this.mBinding.startSurface(nextRootViewTag, jSModuleName, (NativeMap) writableMap);
        if (str != null) {
            this.mBinding.renderTemplateToSurface(nextRootViewTag, str);
        }
        return nextRootViewTag;
    }

    public <T extends View> int startSurface(T t, String str, WritableMap writableMap, int i, int i2) {
        String str2 = str;
        int nextRootViewTag = ReactRootViewTagGenerator.getNextRootViewTag();
        ThemedReactContext themedReactContext = new ThemedReactContext(this.mReactApplicationContext, t.getContext(), str);
        if (ENABLE_FABRIC_LOGS) {
            FLog.m54d(TAG, "Starting surface for module: %s and reactTag: %d", (Object) str, (Object) Integer.valueOf(nextRootViewTag));
        }
        T t2 = t;
        this.mMountingManager.addRootView(nextRootViewTag, t);
        this.mReactContextForRootTag.put(Integer.valueOf(nextRootViewTag), themedReactContext);
        this.mBinding.startSurfaceWithConstraints(nextRootViewTag, str, (NativeMap) writableMap, LayoutMetricsConversions.getMinSize(i), LayoutMetricsConversions.getMaxSize(i), LayoutMetricsConversions.getMinSize(i2), LayoutMetricsConversions.getMaxSize(i2), I18nUtil.getInstance().isRTL(t.getContext()), I18nUtil.getInstance().doLeftAndRightSwapInRTL(t.getContext()));
        return nextRootViewTag;
    }

    public void onRequestEventBeat() {
        this.mEventDispatcher.dispatchAllEvents();
    }

    public void stopSurface(int i) {
        this.mBinding.stopSurface(i);
    }

    public void initialize() {
        this.mEventDispatcher.registerEventEmitter(2, new FabricEventEmitter(this));
        this.mEventDispatcher.addBatchEventDispatchedListener(this.mEventBeatManager);
    }

    public void onCatalystInstanceDestroy() {
        FLog.m76i(TAG, "FabricUIManager.onCatalystInstanceDestroy");
        if (this.mDestroyed) {
            ReactSoftException.logSoftException(TAG, new IllegalStateException("Cannot double-destroy FabricUIManager"));
            return;
        }
        this.mDestroyed = true;
        this.mDispatchUIFrameCallback.stop();
        this.mEventDispatcher.removeBatchEventDispatchedListener(this.mEventBeatManager);
        this.mEventDispatcher.unregisterEventEmitter(2);
        this.mReactApplicationContext.removeLifecycleEventListener(this);
        onHostPause();
        this.mDispatchUIFrameCallback.stop();
        this.mBinding.unregister();
        this.mBinding = null;
        ViewManagerPropertyUpdater.clear();
    }

    private void preallocateView(int i, int i2, String str, ReadableMap readableMap, Object obj, boolean z) {
        ThemedReactContext themedReactContext = this.mReactContextForRootTag.get(Integer.valueOf(i));
        String fabricComponentName = FabricComponents.getFabricComponentName(str);
        synchronized (this.mPreMountItemsLock) {
            this.mPreMountItems.add(new PreAllocateViewMountItem(themedReactContext, i, i2, fabricComponentName, readableMap, (StateWrapper) obj, z));
        }
    }

    private MountItem createMountItem(String str, ReadableMap readableMap, Object obj, int i, int i2, boolean z) {
        String fabricComponentName = FabricComponents.getFabricComponentName(str);
        ThemedReactContext themedReactContext = this.mReactContextForRootTag.get(Integer.valueOf(i));
        if (themedReactContext != null) {
            return new CreateMountItem(themedReactContext, i, i2, fabricComponentName, readableMap, (StateWrapper) obj, z);
        }
        throw new IllegalArgumentException("Unable to find ReactContext for root: " + i);
    }

    private MountItem removeMountItem(int i, int i2, int i3) {
        return new RemoveMountItem(i, i2, i3);
    }

    private MountItem insertMountItem(int i, int i2, int i3) {
        return new InsertMountItem(i, i2, i3);
    }

    private MountItem deleteMountItem(int i) {
        return new DeleteMountItem(i);
    }

    private MountItem removeDeleteMultiMountItem(int[] iArr) {
        return new RemoveDeleteMultiMountItem(iArr);
    }

    private MountItem updateLayoutMountItem(int i, int i2, int i3, int i4, int i5, int i6) {
        return new UpdateLayoutMountItem(i, i2, i3, i4, i5, i6);
    }

    private MountItem updatePaddingMountItem(int i, int i2, int i3, int i4, int i5) {
        return new UpdatePaddingMountItem(i, i2, i3, i4, i5);
    }

    private MountItem updatePropsMountItem(int i, ReadableMap readableMap) {
        return new UpdatePropsMountItem(i, readableMap);
    }

    private MountItem updateLocalDataMountItem(int i, ReadableMap readableMap) {
        return new UpdateLocalDataMountItem(i, readableMap);
    }

    private MountItem updateStateMountItem(int i, Object obj) {
        return new UpdateStateMountItem(i, (StateWrapper) obj);
    }

    private MountItem updateEventEmitterMountItem(int i, Object obj) {
        return new UpdateEventEmitterMountItem(i, (EventEmitterWrapper) obj);
    }

    private MountItem createBatchMountItem(MountItem[] mountItemArr, int i, int i2) {
        return new BatchMountItem(mountItemArr, i, i2);
    }

    private long measure(int i, String str, ReadableMap readableMap, ReadableMap readableMap2, ReadableMap readableMap3, float f, float f2, float f3, float f4) {
        return measure(i, str, readableMap, readableMap2, readableMap3, f, f2, f3, f4, (int[]) null);
    }

    private long measure(int i, String str, ReadableMap readableMap, ReadableMap readableMap2, ReadableMap readableMap3, float f, float f2, float f3, float f4, int[] iArr) {
        Context context;
        if (i < 0) {
            context = this.mReactApplicationContext;
        } else {
            context = this.mReactContextForRootTag.get(Integer.valueOf(i));
        }
        return this.mMountingManager.measure(context, str, readableMap, readableMap2, readableMap3, LayoutMetricsConversions.getYogaSize(f, f2), LayoutMetricsConversions.getYogaMeasureMode(f, f2), LayoutMetricsConversions.getYogaSize(f3, f4), LayoutMetricsConversions.getYogaMeasureMode(f3, f4), iArr);
    }

    public void synchronouslyUpdateViewOnUIThread(int i, ReadableMap readableMap) {
        int i2;
        String str;
        ReactMarkerConstants reactMarkerConstants;
        UiThreadUtil.assertOnUiThread();
        long uptimeMillis = SystemClock.uptimeMillis();
        int i3 = this.mCurrentSynchronousCommitNumber;
        this.mCurrentSynchronousCommitNumber = i3 + 1;
        String str2 = null;
        try {
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_UPDATE_UI_MAIN_THREAD_START, (String) null, i3);
            if (ENABLE_FABRIC_LOGS) {
                FLog.m53d(TAG, "SynchronouslyUpdateViewOnUIThread for tag %d", (Object) Integer.valueOf(i));
            }
            MountItem updatePropsMountItem = updatePropsMountItem(i, readableMap);
            int i4 = i3;
            str = TAG;
            try {
                scheduleMountItem(updatePropsMountItem, i3, uptimeMillis, 0, 0, 0, 0, 0, 0);
                reactMarkerConstants = ReactMarkerConstants.FABRIC_UPDATE_UI_MAIN_THREAD_END;
                i2 = i4;
                str2 = null;
            } catch (Exception e) {
                e = e;
                i2 = i4;
                str2 = null;
                try {
                    ReactSoftException.logSoftException(str, new ReactNoCrashSoftException("Caught exception in synchronouslyUpdateViewOnUIThread", e));
                    reactMarkerConstants = ReactMarkerConstants.FABRIC_UPDATE_UI_MAIN_THREAD_END;
                    ReactMarker.logFabricMarker(reactMarkerConstants, str2, i2);
                } catch (Throwable th) {
                    th = th;
                    ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_UPDATE_UI_MAIN_THREAD_END, str2, i2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                i2 = i4;
                str2 = null;
                ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_UPDATE_UI_MAIN_THREAD_END, str2, i2);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            i2 = i3;
            str = TAG;
            ReactSoftException.logSoftException(str, new ReactNoCrashSoftException("Caught exception in synchronouslyUpdateViewOnUIThread", e));
            reactMarkerConstants = ReactMarkerConstants.FABRIC_UPDATE_UI_MAIN_THREAD_END;
            ReactMarker.logFabricMarker(reactMarkerConstants, str2, i2);
        } catch (Throwable th3) {
            th = th3;
            i2 = i3;
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_UPDATE_UI_MAIN_THREAD_END, str2, i2);
            throw th;
        }
        ReactMarker.logFabricMarker(reactMarkerConstants, str2, i2);
    }

    private void scheduleMountItem(MountItem mountItem, int i, long j, long j2, long j3, long j4, long j5, long j6, long j7) {
        MountItem mountItem2 = mountItem;
        int i2 = i;
        long j8 = j;
        long j9 = j4;
        long j10 = j5;
        long j11 = j6;
        long j12 = j7;
        boolean z = mountItem2 instanceof BatchMountItem;
        if (z) {
            this.mCommitStartTime = j8;
            this.mLayoutTime = j10 - j9;
            this.mFinishTransactionCPPTime = j12 - j11;
            this.mFinishTransactionTime = SystemClock.uptimeMillis() - j11;
            this.mDispatchViewUpdatesTime = SystemClock.uptimeMillis();
        }
        synchronized (this.mMountItemsLock) {
            this.mMountItems.add(mountItem2);
        }
        if (UiThreadUtil.isOnUiThread()) {
            tryDispatchMountItems();
        }
        if (z) {
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_COMMIT_START, (String) null, i2, j8);
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_FINISH_TRANSACTION_START, (String) null, i2, j11);
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_FINISH_TRANSACTION_END, (String) null, i2, j12);
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_DIFF_START, (String) null, i2, j2);
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_DIFF_END, (String) null, i2, j3);
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_LAYOUT_START, (String) null, i2, j9);
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_LAYOUT_END, (String) null, i2, j10);
            ReactMarker.logFabricMarker(ReactMarkerConstants.FABRIC_COMMIT_END, (String) null, i2);
        }
    }

    /* access modifiers changed from: private */
    public void tryDispatchMountItems() {
        if (!this.mInDispatch) {
            try {
                boolean dispatchMountItems = dispatchMountItems();
                this.mInDispatch = false;
                int i = this.mReDispatchCounter;
                if (i < 10 && dispatchMountItems) {
                    if (i > 2) {
                        ReactSoftException.logSoftException(TAG, new ReactNoCrashSoftException("Re-dispatched " + this.mReDispatchCounter + " times. This indicates setState (?) is likely being called too many times during mounting."));
                    }
                    this.mReDispatchCounter++;
                    tryDispatchMountItems();
                }
                this.mReDispatchCounter = 0;
            } catch (Throwable th) {
                this.mInDispatch = false;
                throw th;
            }
        }
    }

    private List<DispatchCommandMountItem> getAndResetViewCommandMountItems() {
        if (!ReactFeatureFlags.allowEarlyViewCommandExecution) {
            return null;
        }
        synchronized (this.mViewCommandMountItemsLock) {
            List<DispatchCommandMountItem> list = this.mViewCommandMountItems;
            if (list.isEmpty()) {
                return null;
            }
            this.mViewCommandMountItems = new ArrayList();
            return list;
        }
    }

    private List<MountItem> getAndResetMountItems() {
        synchronized (this.mMountItemsLock) {
            List<MountItem> list = this.mMountItems;
            if (list.isEmpty()) {
                return null;
            }
            this.mMountItems = new ArrayList();
            return list;
        }
    }

    private ArrayDeque<MountItem> getAndResetPreMountItems() {
        synchronized (this.mPreMountItemsLock) {
            ArrayDeque<MountItem> arrayDeque = this.mPreMountItems;
            if (arrayDeque.isEmpty()) {
                return null;
            }
            this.mPreMountItems = new ArrayDeque<>(250);
            return arrayDeque;
        }
    }

    private boolean dispatchMountItems() {
        if (this.mReDispatchCounter == 0) {
            this.mBatchedExecutionTime = 0;
        }
        this.mRunStartTime = SystemClock.uptimeMillis();
        List<DispatchCommandMountItem> andResetViewCommandMountItems = getAndResetViewCommandMountItems();
        List<MountItem> andResetMountItems = getAndResetMountItems();
        if (andResetMountItems == null && andResetViewCommandMountItems == null) {
            return false;
        }
        if (andResetViewCommandMountItems != null) {
            Systrace.beginSection(0, "FabricUIManager::mountViews viewCommandMountItems to execute: " + andResetViewCommandMountItems.size());
            for (DispatchCommandMountItem next : andResetViewCommandMountItems) {
                if (ENABLE_FABRIC_LOGS) {
                    FLog.m52d(TAG, "dispatchMountItems: Executing viewCommandMountItem: " + next.toString());
                }
                try {
                    next.execute(this.mMountingManager);
                } catch (RetryableMountingLayerException e) {
                    if (next.getRetries() == 0) {
                        next.incrementRetries();
                        dispatchCommandMountItem(next);
                    } else {
                        ReactSoftException.logSoftException(TAG, new ReactNoCrashSoftException("Caught exception executing ViewCommand: " + next.toString(), e));
                    }
                } catch (Throwable th) {
                    ReactSoftException.logSoftException(TAG, new RuntimeException("Caught exception executing ViewCommand: " + next.toString(), th));
                }
            }
            Systrace.endSection(0);
        }
        ArrayDeque<MountItem> andResetPreMountItems = getAndResetPreMountItems();
        if (andResetPreMountItems != null) {
            Systrace.beginSection(0, "FabricUIManager::mountViews preMountItems to execute: " + andResetPreMountItems.size());
            while (!andResetPreMountItems.isEmpty()) {
                andResetPreMountItems.pollFirst().execute(this.mMountingManager);
            }
            Systrace.endSection(0);
        }
        if (andResetMountItems != null) {
            Systrace.beginSection(0, "FabricUIManager::mountViews mountItems to execute: " + andResetMountItems.size());
            long uptimeMillis = SystemClock.uptimeMillis();
            for (MountItem next2 : andResetMountItems) {
                if (ENABLE_FABRIC_LOGS) {
                    for (String str : next2.toString().split("\n")) {
                        FLog.m52d(TAG, "dispatchMountItems: Executing mountItem: " + str);
                    }
                }
                next2.execute(this.mMountingManager);
            }
            this.mBatchedExecutionTime += SystemClock.uptimeMillis() - uptimeMillis;
        }
        Systrace.endSection(0);
        return true;
    }

    /* access modifiers changed from: private */
    public void dispatchPreMountItems(long j) {
        MountItem pollFirst;
        Systrace.beginSection(0, "FabricUIManager::premountViews");
        this.mInDispatch = true;
        while (true) {
            try {
                if (16 - ((System.nanoTime() - j) / 1000000) < 8) {
                    break;
                }
                synchronized (this.mPreMountItemsLock) {
                    if (!this.mPreMountItems.isEmpty()) {
                        pollFirst = this.mPreMountItems.pollFirst();
                    }
                }
                pollFirst.execute(this.mMountingManager);
            } catch (Throwable th) {
                this.mInDispatch = false;
                throw th;
            }
        }
        this.mInDispatch = false;
        Systrace.endSection(0);
    }

    public void setBinding(Binding binding) {
        this.mBinding = binding;
    }

    public void updateRootLayoutSpecs(int i, int i2, int i3) {
        boolean z;
        boolean z2;
        if (ENABLE_FABRIC_LOGS) {
            FLog.m52d(TAG, "Updating Root Layout Specs");
        }
        ThemedReactContext themedReactContext = this.mReactContextForRootTag.get(Integer.valueOf(i));
        if (themedReactContext != null) {
            boolean isRTL = I18nUtil.getInstance().isRTL(themedReactContext);
            z = I18nUtil.getInstance().doLeftAndRightSwapInRTL(themedReactContext);
            z2 = isRTL;
        } else {
            ReactSoftException.logSoftException(TAG, new IllegalStateException("updateRootLayoutSpecs called before ReactContext set for tag: " + i));
            z2 = false;
            z = false;
        }
        this.mBinding.setConstraints(i, LayoutMetricsConversions.getMinSize(i2), LayoutMetricsConversions.getMaxSize(i2), LayoutMetricsConversions.getMinSize(i3), LayoutMetricsConversions.getMaxSize(i3), z2, z);
    }

    public void receiveEvent(int i, String str, WritableMap writableMap) {
        EventEmitterWrapper eventEmitter = this.mMountingManager.getEventEmitter(i);
        if (eventEmitter == null) {
            FLog.m52d(TAG, "Unable to invoke event: " + str + " for reactTag: " + i);
            return;
        }
        eventEmitter.invoke(str, writableMap);
    }

    public void onHostResume() {
        ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, this.mDispatchUIFrameCallback);
    }

    public EventDispatcher getEventDispatcher() {
        return this.mEventDispatcher;
    }

    public void onHostPause() {
        ReactChoreographer.getInstance().removeFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, this.mDispatchUIFrameCallback);
    }

    @Deprecated
    public void dispatchCommand(int i, int i2, ReadableArray readableArray) {
        dispatchCommandMountItem(new DispatchIntCommandMountItem(i, i2, readableArray));
    }

    public void dispatchCommand(int i, String str, ReadableArray readableArray) {
        dispatchCommandMountItem(new DispatchStringCommandMountItem(i, str, readableArray));
    }

    private void dispatchCommandMountItem(DispatchCommandMountItem dispatchCommandMountItem) {
        if (ReactFeatureFlags.allowEarlyViewCommandExecution) {
            synchronized (this.mViewCommandMountItemsLock) {
                this.mViewCommandMountItems.add(dispatchCommandMountItem);
            }
            return;
        }
        synchronized (this.mMountItemsLock) {
            this.mMountItems.add(dispatchCommandMountItem);
        }
    }

    public void sendAccessibilityEvent(int i, int i2) {
        synchronized (this.mMountItemsLock) {
            this.mMountItems.add(new SendAccessibilityEvent(i, i2));
        }
    }

    public void setJSResponder(final int i, final int i2, final boolean z) {
        synchronized (this.mMountItemsLock) {
            this.mMountItems.add(new MountItem() {
                public void execute(MountingManager mountingManager) {
                    mountingManager.setJSResponder(i, i2, z);
                }
            });
        }
    }

    public void clearJSResponder() {
        synchronized (this.mMountItemsLock) {
            this.mMountItems.add(new MountItem() {
                public void execute(MountingManager mountingManager) {
                    mountingManager.clearJSResponder();
                }
            });
        }
    }

    public Map<String, Long> getPerformanceCounters() {
        HashMap hashMap = new HashMap();
        hashMap.put("CommitStartTime", Long.valueOf(this.mCommitStartTime));
        hashMap.put("LayoutTime", Long.valueOf(this.mLayoutTime));
        hashMap.put("DispatchViewUpdatesTime", Long.valueOf(this.mDispatchViewUpdatesTime));
        hashMap.put("RunStartTime", Long.valueOf(this.mRunStartTime));
        hashMap.put("BatchedExecutionTime", Long.valueOf(this.mBatchedExecutionTime));
        hashMap.put("FinishFabricTransactionTime", Long.valueOf(this.mFinishTransactionTime));
        hashMap.put("FinishFabricTransactionCPPTime", Long.valueOf(this.mFinishTransactionCPPTime));
        return hashMap;
    }

    private class DispatchUIFrameCallback extends GuardedFrameCallback {
        private volatile boolean mIsMountingEnabled;

        private DispatchUIFrameCallback(ReactContext reactContext) {
            super(reactContext);
            this.mIsMountingEnabled = true;
        }

        /* access modifiers changed from: package-private */
        public void stop() {
            this.mIsMountingEnabled = false;
        }

        public void doFrameGuarded(long j) {
            if (!this.mIsMountingEnabled || FabricUIManager.this.mDestroyed) {
                FLog.m104w(FabricUIManager.TAG, "Not flushing pending UI operations because of previously thrown Exception");
                return;
            }
            try {
                FabricUIManager.this.dispatchPreMountItems(j);
                FabricUIManager.this.tryDispatchMountItems();
                ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, FabricUIManager.this.mDispatchUIFrameCallback);
            } catch (Exception e) {
                FLog.m65e(FabricUIManager.TAG, "Exception thrown when executing UIFrameGuarded", (Throwable) e);
                stop();
                throw e;
            } catch (Throwable th) {
                ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, FabricUIManager.this.mDispatchUIFrameCallback);
                throw th;
            }
        }
    }
}
