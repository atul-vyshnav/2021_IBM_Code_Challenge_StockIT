package com.horcrux.svg;

import android.graphics.Matrix;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.horcrux.svg.Brush;
import javax.annotation.Nullable;

class MaskView extends GroupView {
    private static final float[] sRawMatrix = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};

    /* renamed from: mH */
    SVGLength f1131mH;
    private Brush.BrushUnits mMaskContentUnits;
    private Brush.BrushUnits mMaskUnits;
    private Matrix mMatrix = null;

    /* renamed from: mW */
    SVGLength f1132mW;

    /* renamed from: mX */
    SVGLength f1133mX;

    /* renamed from: mY */
    SVGLength f1134mY;

    public MaskView(ReactContext reactContext) {
        super(reactContext);
    }

    @ReactProp(name = "x")
    public void setX(Dynamic dynamic) {
        this.f1133mX = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "y")
    public void setY(Dynamic dynamic) {
        this.f1134mY = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "width")
    public void setWidth(Dynamic dynamic) {
        this.f1132mW = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "height")
    public void setHeight(Dynamic dynamic) {
        this.f1131mH = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "maskUnits")
    public void setMaskUnits(int i) {
        if (i == 0) {
            this.mMaskUnits = Brush.BrushUnits.OBJECT_BOUNDING_BOX;
        } else if (i == 1) {
            this.mMaskUnits = Brush.BrushUnits.USER_SPACE_ON_USE;
        }
        invalidate();
    }

    @ReactProp(name = "maskContentUnits")
    public void setMaskContentUnits(int i) {
        if (i == 0) {
            this.mMaskContentUnits = Brush.BrushUnits.OBJECT_BOUNDING_BOX;
        } else if (i == 1) {
            this.mMaskContentUnits = Brush.BrushUnits.USER_SPACE_ON_USE;
        }
        invalidate();
    }

    @ReactProp(name = "maskTransform")
    public void setMaskTransform(@Nullable ReadableArray readableArray) {
        if (readableArray != null) {
            int matrixData = PropHelper.toMatrixData(readableArray, sRawMatrix, this.mScale);
            if (matrixData == 6) {
                if (this.mMatrix == null) {
                    this.mMatrix = new Matrix();
                }
                this.mMatrix.setValues(sRawMatrix);
            } else if (matrixData != -1) {
                FLog.m104w(ReactConstants.TAG, "RNSVG: Transform matrices must be of size 6");
            }
        } else {
            this.mMatrix = null;
        }
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void saveDefinition() {
        if (this.mName != null) {
            getSvgView().defineMask(this, this.mName);
        }
    }
}
