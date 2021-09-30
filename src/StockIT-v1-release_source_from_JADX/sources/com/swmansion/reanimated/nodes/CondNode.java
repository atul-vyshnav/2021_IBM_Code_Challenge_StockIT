package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.MapUtils;
import com.swmansion.reanimated.NodesManager;

public class CondNode extends Node {
    private final int mCondID;
    private final int mElseBlockID;
    private final int mIfBlockID;

    public CondNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mCondID = MapUtils.getInt(readableMap, "cond", "Reanimated: First argument passed to cond node is either of wrong type or is missing.");
        this.mIfBlockID = MapUtils.getInt(readableMap, "ifBlock", "Reanimated: Second argument passed to cond node is either of wrong type or is missing.");
        this.mElseBlockID = readableMap.hasKey("elseBlock") ? MapUtils.getInt(readableMap, "elseBlock", "Reanimated: Second argument passed to cond node is either of wrong type or is missing.") : -1;
    }

    /* access modifiers changed from: protected */
    public Object evaluate() {
        Object nodeValue = this.mNodesManager.getNodeValue(this.mCondID);
        return (!(nodeValue instanceof Number) || ((Number) nodeValue).doubleValue() == 0.0d) ? this.mElseBlockID != -1 ? this.mNodesManager.getNodeValue(this.mElseBlockID) : ZERO : this.mIfBlockID != -1 ? this.mNodesManager.getNodeValue(this.mIfBlockID) : ZERO;
    }
}
