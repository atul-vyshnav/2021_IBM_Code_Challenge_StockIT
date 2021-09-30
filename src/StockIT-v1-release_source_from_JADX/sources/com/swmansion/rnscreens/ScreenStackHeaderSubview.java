package com.swmansion.rnscreens;

import android.view.View;
import android.view.ViewParent;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.views.view.ReactViewGroup;

public class ScreenStackHeaderSubview extends ReactViewGroup {
    private int mReactHeight;
    private int mReactWidth;
    private Type mType = Type.RIGHT;

    public enum Type {
        LEFT,
        CENTER,
        RIGHT,
        BACK
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) == 1073741824 && View.MeasureSpec.getMode(i2) == 1073741824) {
            this.mReactWidth = View.MeasureSpec.getSize(i);
            this.mReactHeight = View.MeasureSpec.getSize(i2);
            ViewParent parent = getParent();
            if (parent != null) {
                forceLayout();
                ((View) parent).requestLayout();
            }
        }
        setMeasuredDimension(this.mReactWidth, this.mReactHeight);
    }

    public ScreenStackHeaderSubview(ReactContext reactContext) {
        super(reactContext);
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public Type getType() {
        return this.mType;
    }
}
