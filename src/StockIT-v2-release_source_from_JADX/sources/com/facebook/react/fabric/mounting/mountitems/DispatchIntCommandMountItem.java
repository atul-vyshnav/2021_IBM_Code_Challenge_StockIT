package com.facebook.react.fabric.mounting.mountitems;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.fabric.mounting.MountingManager;

public class DispatchIntCommandMountItem extends DispatchCommandMountItem {
    private final ReadableArray mCommandArgs;
    private final int mCommandId;
    private final int mReactTag;

    public DispatchIntCommandMountItem(int i, int i2, ReadableArray readableArray) {
        this.mReactTag = i;
        this.mCommandId = i2;
        this.mCommandArgs = readableArray;
    }

    public void execute(MountingManager mountingManager) {
        mountingManager.receiveCommand(this.mReactTag, this.mCommandId, this.mCommandArgs);
    }

    public String toString() {
        return "DispatchIntCommandMountItem [" + this.mReactTag + "] " + this.mCommandId;
    }
}
