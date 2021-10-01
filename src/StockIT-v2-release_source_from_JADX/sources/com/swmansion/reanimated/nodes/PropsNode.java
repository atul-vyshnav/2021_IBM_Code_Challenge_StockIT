package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIImplementation;
import com.swmansion.reanimated.C2806Utils;
import com.swmansion.reanimated.NodesManager;
import java.util.Map;

public class PropsNode extends Node implements FinalNode {
    private int mConnectedViewTag = -1;
    private final ReactStylesDiffMap mDiffMap;
    private final Map<String, Integer> mMapping;
    private final JavaOnlyMap mPropMap;
    private final UIImplementation mUIImplementation;

    private static void addProp(WritableMap writableMap, String str, Object obj) {
        if (obj == null) {
            writableMap.putNull(str);
        } else if (obj instanceof Double) {
            writableMap.putDouble(str, ((Double) obj).doubleValue());
        } else if (obj instanceof Integer) {
            writableMap.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Number) {
            writableMap.putDouble(str, ((Number) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            writableMap.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof String) {
            writableMap.putString(str, (String) obj);
        } else if (obj instanceof WritableArray) {
            writableMap.putArray(str, (WritableArray) obj);
        } else if (obj instanceof WritableMap) {
            writableMap.putMap(str, (WritableMap) obj);
        } else {
            throw new IllegalStateException("Unknown type of animated value");
        }
    }

    public PropsNode(int i, ReadableMap readableMap, NodesManager nodesManager, UIImplementation uIImplementation) {
        super(i, readableMap, nodesManager);
        this.mMapping = C2806Utils.processMapping(readableMap.getMap("props"));
        this.mUIImplementation = uIImplementation;
        JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
        this.mPropMap = javaOnlyMap;
        this.mDiffMap = new ReactStylesDiffMap(javaOnlyMap);
    }

    public void connectToView(int i) {
        this.mConnectedViewTag = i;
        dangerouslyRescheduleEvaluate();
    }

    public void disconnectFromView(int i) {
        this.mConnectedViewTag = -1;
    }

    /* access modifiers changed from: protected */
    public Double evaluate() {
        boolean z;
        boolean z2;
        boolean z3;
        WritableMap writableMap;
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        for (Map.Entry next : this.mMapping.entrySet()) {
            Node findNodeById = this.mNodesManager.findNodeById(((Integer) next.getValue()).intValue(), Node.class);
            if (findNodeById instanceof StyleNode) {
                WritableMap writableMap2 = (WritableMap) findNodeById.value();
                ReadableMapKeySetIterator keySetIterator = writableMap2.keySetIterator();
                while (keySetIterator.hasNextKey()) {
                    String nextKey = keySetIterator.nextKey();
                    if (this.mNodesManager.uiProps.contains(nextKey)) {
                        writableMap = this.mPropMap;
                        z = z6;
                        z2 = z5;
                        z3 = true;
                    } else if (this.mNodesManager.nativeProps.contains(nextKey)) {
                        z3 = z4;
                        z = z6;
                        z2 = true;
                        writableMap = createMap2;
                    } else {
                        z2 = z5;
                        z = true;
                        z3 = z4;
                        writableMap = createMap;
                    }
                    ReadableType type = writableMap2.getType(nextKey);
                    int i = C28381.$SwitchMap$com$facebook$react$bridge$ReadableType[type.ordinal()];
                    if (i == 1) {
                        writableMap.putDouble(nextKey, writableMap2.getDouble(nextKey));
                    } else if (i == 2) {
                        writableMap.putString(nextKey, writableMap2.getString(nextKey));
                    } else if (i == 3) {
                        writableMap.putArray(nextKey, (WritableArray) writableMap2.getArray(nextKey));
                    } else {
                        throw new IllegalArgumentException("Unexpected type " + type);
                    }
                    z4 = z3;
                    z5 = z2;
                    z6 = z;
                }
                continue;
            } else {
                String str = (String) next.getKey();
                Object value = findNodeById.value();
                if (this.mNodesManager.uiProps.contains(str)) {
                    addProp(this.mPropMap, str, value);
                    z4 = true;
                } else {
                    addProp(createMap2, str, value);
                    z5 = true;
                }
            }
        }
        int i2 = this.mConnectedViewTag;
        if (i2 != -1) {
            if (z4) {
                this.mUIImplementation.synchronouslyUpdateViewOnUIThread(i2, this.mDiffMap);
            }
            if (z5) {
                this.mNodesManager.enqueueUpdateViewOnNativeThread(this.mConnectedViewTag, createMap2);
            }
            if (z6) {
                WritableMap createMap3 = Arguments.createMap();
                createMap3.putInt("viewTag", this.mConnectedViewTag);
                createMap3.putMap("props", createMap);
                this.mNodesManager.sendEvent("onReanimatedPropsChange", createMap3);
            }
        }
        return ZERO;
    }

    /* renamed from: com.swmansion.reanimated.nodes.PropsNode$1 */
    static /* synthetic */ class C28381 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReadableType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.facebook.react.bridge.ReadableType[] r0 = com.facebook.react.bridge.ReadableType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$facebook$react$bridge$ReadableType = r0
                com.facebook.react.bridge.ReadableType r1 = com.facebook.react.bridge.ReadableType.Number     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReadableType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.facebook.react.bridge.ReadableType r1 = com.facebook.react.bridge.ReadableType.String     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReadableType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.facebook.react.bridge.ReadableType r1 = com.facebook.react.bridge.ReadableType.Array     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.swmansion.reanimated.nodes.PropsNode.C28381.<clinit>():void");
        }
    }

    public void update() {
        if (this.mConnectedViewTag != -1) {
            value();
        }
    }
}
