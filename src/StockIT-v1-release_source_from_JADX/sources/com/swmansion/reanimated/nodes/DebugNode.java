package com.swmansion.reanimated.nodes;

import android.util.Log;
import com.facebook.react.bridge.ReadableMap;
import com.onesignal.OneSignalDbContract;
import com.swmansion.reanimated.MapUtils;
import com.swmansion.reanimated.NodesManager;

public class DebugNode extends Node {
    private final String mMessage;
    private final int mValueID;

    public DebugNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mMessage = MapUtils.getString(readableMap, OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, "Reanimated: First argument passed to debug node is either of wrong type or is missing.");
        this.mValueID = MapUtils.getInt(readableMap, "value", "Reanimated: Second argument passed to debug node is either of wrong type or is missing.");
    }

    /* access modifiers changed from: protected */
    public Object evaluate() {
        Object value = this.mNodesManager.findNodeById(this.mValueID, Node.class).value();
        Log.d("REANIMATED", String.format("%s %s", new Object[]{this.mMessage, value}));
        return value;
    }
}
