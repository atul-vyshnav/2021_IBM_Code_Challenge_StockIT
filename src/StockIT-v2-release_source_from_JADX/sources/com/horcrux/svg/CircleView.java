package com.horcrux.svg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

class CircleView extends RenderableView {
    private SVGLength mCx;
    private SVGLength mCy;

    /* renamed from: mR */
    private SVGLength f1120mR;

    public CircleView(ReactContext reactContext) {
        super(reactContext);
    }

    @ReactProp(name = "cx")
    public void setCx(Dynamic dynamic) {
        this.mCx = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "cy")
    public void setCy(Dynamic dynamic) {
        this.mCy = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "r")
    public void setR(Dynamic dynamic) {
        this.f1120mR = SVGLength.from(dynamic);
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public Path getPath(Canvas canvas, Paint paint) {
        Path path = new Path();
        path.addCircle((float) relativeOnWidth(this.mCx), (float) relativeOnHeight(this.mCy), (float) relativeOnOther(this.f1120mR), Path.Direction.CW);
        return path;
    }
}
