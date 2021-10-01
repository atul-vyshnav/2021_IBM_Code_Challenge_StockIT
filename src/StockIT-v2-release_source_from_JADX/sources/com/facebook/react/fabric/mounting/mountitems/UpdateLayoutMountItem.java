package com.facebook.react.fabric.mounting.mountitems;

import com.facebook.react.fabric.mounting.MountingManager;

public class UpdateLayoutMountItem implements MountItem {
    private final int mHeight;
    private final int mLayoutDirection;
    private final int mReactTag;
    private final int mWidth;

    /* renamed from: mX */
    private final int f100mX;

    /* renamed from: mY */
    private final int f101mY;

    public UpdateLayoutMountItem(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mReactTag = i;
        this.f100mX = i2;
        this.f101mY = i3;
        this.mWidth = i4;
        this.mHeight = i5;
        this.mLayoutDirection = convertLayoutDirection(i6);
    }

    private static int convertLayoutDirection(int i) {
        if (i == 0) {
            return 2;
        }
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        throw new IllegalArgumentException("Unsupported layout direction: " + i);
    }

    public void execute(MountingManager mountingManager) {
        mountingManager.updateLayout(this.mReactTag, this.f100mX, this.f101mY, this.mWidth, this.mHeight);
    }

    public int getX() {
        return this.f100mX;
    }

    public int getY() {
        return this.f101mY;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getLayoutDirection() {
        return this.mLayoutDirection;
    }

    public String toString() {
        return "UpdateLayoutMountItem [" + this.mReactTag + "] - x: " + this.f100mX + " - y: " + this.f101mY + " - height: " + this.mHeight + " - width: " + this.mWidth + " - layoutDirection: " + this.mLayoutDirection;
    }
}
