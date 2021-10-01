package com.facebook.react.animated;

import com.facebook.common.logging.FLog;
import com.facebook.fbreact.specs.NativeAnimatedModuleSpec;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.uimanager.GuardedFrameCallback;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIManagerModuleListener;
import java.util.ArrayList;
import java.util.Iterator;

@ReactModule(name = "NativeAnimatedModule")
public class NativeAnimatedModule extends NativeAnimatedModuleSpec implements LifecycleEventListener, UIManagerModuleListener {
    public static final String NAME = "NativeAnimatedModule";
    /* access modifiers changed from: private */
    public final GuardedFrameCallback mAnimatedFrameCallback;
    private NativeAnimatedNodesManager mNodesManager;
    private ArrayList<UIThreadOperation> mOperations = new ArrayList<>();
    private ArrayList<UIThreadOperation> mPreOperations = new ArrayList<>();
    /* access modifiers changed from: private */
    public final ReactChoreographer mReactChoreographer = ReactChoreographer.getInstance();

    private interface UIThreadOperation {
        void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager);
    }

    public void addListener(String str) {
    }

    public String getName() {
        return NAME;
    }

    public void onHostDestroy() {
    }

    public void removeListeners(double d) {
    }

    public NativeAnimatedModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mAnimatedFrameCallback = new GuardedFrameCallback(reactApplicationContext) {
            /* access modifiers changed from: protected */
            public void doFrameGuarded(long j) {
                try {
                    NativeAnimatedNodesManager access$000 = NativeAnimatedModule.this.getNodesManager();
                    if (access$000.hasActiveAnimations()) {
                        access$000.runUpdates(j);
                    }
                    ((ReactChoreographer) Assertions.assertNotNull(NativeAnimatedModule.this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, NativeAnimatedModule.this.mAnimatedFrameCallback);
                } catch (Exception e) {
                    FLog.m65e(ReactConstants.TAG, "Exception while executing animated frame callback.", (Throwable) e);
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public void initialize() {
        ReactApplicationContext reactApplicationContextIfActiveOrWarn = getReactApplicationContextIfActiveOrWarn();
        if (reactApplicationContextIfActiveOrWarn != null && !reactApplicationContextIfActiveOrWarn.isBridgeless()) {
            reactApplicationContextIfActiveOrWarn.addLifecycleEventListener(this);
            ((UIManagerModule) reactApplicationContextIfActiveOrWarn.getNativeModule(UIManagerModule.class)).addUIManagerListener(this);
        }
    }

    public void onHostResume() {
        enqueueFrameCallback();
    }

    public void willDispatchViewUpdates(UIManagerModule uIManagerModule) {
        if (!this.mOperations.isEmpty() || !this.mPreOperations.isEmpty()) {
            final ArrayList<UIThreadOperation> arrayList = this.mPreOperations;
            final ArrayList<UIThreadOperation> arrayList2 = this.mOperations;
            this.mPreOperations = new ArrayList<>();
            this.mOperations = new ArrayList<>();
            uIManagerModule.prependUIBlock(new UIBlock() {
                public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                    NativeAnimatedNodesManager access$000 = NativeAnimatedModule.this.getNodesManager();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((UIThreadOperation) it.next()).execute(access$000);
                    }
                }
            });
            uIManagerModule.addUIBlock(new UIBlock() {
                public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                    NativeAnimatedNodesManager access$000 = NativeAnimatedModule.this.getNodesManager();
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        ((UIThreadOperation) it.next()).execute(access$000);
                    }
                }
            });
        }
    }

    public void onHostPause() {
        clearFrameCallback();
    }

    /* access modifiers changed from: private */
    public NativeAnimatedNodesManager getNodesManager() {
        ReactApplicationContext reactApplicationContextIfActiveOrWarn;
        if (this.mNodesManager == null && (reactApplicationContextIfActiveOrWarn = getReactApplicationContextIfActiveOrWarn()) != null) {
            this.mNodesManager = new NativeAnimatedNodesManager((UIManagerModule) reactApplicationContextIfActiveOrWarn.getNativeModule(UIManagerModule.class));
        }
        return this.mNodesManager;
    }

    private void clearFrameCallback() {
        ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).removeFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mAnimatedFrameCallback);
    }

    private void enqueueFrameCallback() {
        ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mAnimatedFrameCallback);
    }

    public void setNodesManager(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
        this.mNodesManager = nativeAnimatedNodesManager;
    }

    public void createAnimatedNode(double d, final ReadableMap readableMap) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.createAnimatedNode(i, readableMap);
            }
        });
    }

    public void startListeningToAnimatedNodeValue(double d) {
        final int i = (int) d;
        final C10835 r4 = new AnimatedNodeValueListener() {
            public void onValueUpdate(double d) {
                WritableMap createMap = Arguments.createMap();
                createMap.putInt("tag", i);
                createMap.putDouble("value", d);
                ReactApplicationContext access$300 = NativeAnimatedModule.this.getReactApplicationContextIfActiveOrWarn();
                if (access$300 != null) {
                    ((DeviceEventManagerModule.RCTDeviceEventEmitter) access$300.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("onAnimatedValueUpdate", createMap);
                }
            }
        };
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.startListeningToAnimatedNodeValue(i, r4);
            }
        });
    }

    public void stopListeningToAnimatedNodeValue(double d) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.stopListeningToAnimatedNodeValue(i);
            }
        });
    }

    public void dropAnimatedNode(double d) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.dropAnimatedNode(i);
            }
        });
    }

    public void setAnimatedNodeValue(double d, final double d2) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.setAnimatedNodeValue(i, d2);
            }
        });
    }

    public void setAnimatedNodeOffset(double d, final double d2) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.setAnimatedNodeOffset(i, d2);
            }
        });
    }

    public void flattenAnimatedNodeOffset(double d) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.flattenAnimatedNodeOffset(i);
            }
        });
    }

    public void extractAnimatedNodeOffset(double d) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.extractAnimatedNodeOffset(i);
            }
        });
    }

    public void startAnimatingNode(double d, double d2, ReadableMap readableMap, Callback callback) {
        final int i = (int) d;
        final int i2 = (int) d2;
        final ReadableMap readableMap2 = readableMap;
        final Callback callback2 = callback;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.startAnimatingNode(i, i2, readableMap2, callback2);
            }
        });
    }

    public void stopAnimation(double d) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.stopAnimation(i);
            }
        });
    }

    public void connectAnimatedNodes(double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.connectAnimatedNodes(i, i2);
            }
        });
    }

    public void disconnectAnimatedNodes(double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.disconnectAnimatedNodes(i, i2);
            }
        });
    }

    public void connectAnimatedNodeToView(double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.connectAnimatedNodeToView(i, i2);
            }
        });
    }

    public void disconnectAnimatedNodeFromView(double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.disconnectAnimatedNodeFromView(i, i2);
            }
        });
    }

    public void restoreDefaultValues(double d) {
        final int i = (int) d;
        this.mPreOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.restoreDefaultValues(i);
            }
        });
    }

    public void addAnimatedEventToView(double d, final String str, final ReadableMap readableMap) {
        final int i = (int) d;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.addAnimatedEventToView(i, str, readableMap);
            }
        });
    }

    public void removeAnimatedEventFromView(double d, final String str, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        this.mOperations.add(new UIThreadOperation() {
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.removeAnimatedEventFromView(i, str, i2);
            }
        });
    }
}
