package com.horcrux.svg;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

class RectView extends RenderableView {

    /* renamed from: mH */
    private SVGLength f1145mH;
    private SVGLength mRx;
    private SVGLength mRy;

    /* renamed from: mW */
    private SVGLength f1146mW;

    /* renamed from: mX */
    private SVGLength f1147mX;

    /* renamed from: mY */
    private SVGLength f1148mY;

    public RectView(ReactContext reactContext) {
        super(reactContext);
    }

    @ReactProp(name = "x")
    public void setX(Dynamic dynamic) {
        this.f1147mX = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "y")
    public void setY(Dynamic dynamic) {
        this.f1148mY = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "width")
    public void setWidth(Dynamic dynamic) {
        this.f1146mW = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "height")
    public void setHeight(Dynamic dynamic) {
        this.f1145mH = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "rx")
    public void setRx(Dynamic dynamic) {
        this.mRx = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "ry")
    public void setRy(Dynamic dynamic) {
        this.mRy = SVGLength.from(dynamic);
        invalidate();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Path getPath(android.graphics.Canvas r20, android.graphics.Paint r21) {
        /*
            r19 = this;
            r0 = r19
            android.graphics.Path r9 = new android.graphics.Path
            r9.<init>()
            com.horcrux.svg.SVGLength r1 = r0.f1147mX
            double r1 = r0.relativeOnWidth(r1)
            com.horcrux.svg.SVGLength r3 = r0.f1148mY
            double r3 = r0.relativeOnHeight(r3)
            com.horcrux.svg.SVGLength r5 = r0.f1146mW
            double r5 = r0.relativeOnWidth(r5)
            com.horcrux.svg.SVGLength r7 = r0.f1145mH
            double r7 = r0.relativeOnHeight(r7)
            com.horcrux.svg.SVGLength r10 = r0.mRx
            if (r10 != 0) goto L_0x003e
            com.horcrux.svg.SVGLength r10 = r0.mRy
            if (r10 == 0) goto L_0x0028
            goto L_0x003e
        L_0x0028:
            float r10 = (float) r1
            float r11 = (float) r3
            double r1 = r1 + r5
            float r5 = (float) r1
            double r3 = r3 + r7
            float r6 = (float) r3
            android.graphics.Path$Direction r7 = android.graphics.Path.Direction.CW
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r5
            r5 = r6
            r6 = r7
            r1.addRect(r2, r3, r4, r5, r6)
            r9.close()
            goto L_0x009c
        L_0x003e:
            com.horcrux.svg.SVGLength r10 = r0.mRx
            if (r10 != 0) goto L_0x004a
            com.horcrux.svg.SVGLength r10 = r0.mRy
            double r10 = r0.relativeOnHeight(r10)
        L_0x0048:
            r12 = r10
            goto L_0x005d
        L_0x004a:
            com.horcrux.svg.SVGLength r11 = r0.mRy
            if (r11 != 0) goto L_0x0053
            double r10 = r0.relativeOnWidth(r10)
            goto L_0x0048
        L_0x0053:
            double r10 = r0.relativeOnWidth(r10)
            com.horcrux.svg.SVGLength r12 = r0.mRy
            double r12 = r0.relativeOnHeight(r12)
        L_0x005d:
            r14 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r16 = r5 / r14
            int r18 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1))
            if (r18 <= 0) goto L_0x0067
            r10 = r16
        L_0x0067:
            double r14 = r7 / r14
            int r16 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r16 <= 0) goto L_0x006e
            r12 = r14
        L_0x006e:
            int r14 = android.os.Build.VERSION.SDK_INT
            r15 = 21
            if (r14 < r15) goto L_0x008a
            float r14 = (float) r1
            float r15 = (float) r3
            double r1 = r1 + r5
            float r5 = (float) r1
            double r3 = r3 + r7
            float r6 = (float) r3
            float r7 = (float) r10
            float r8 = (float) r12
            android.graphics.Path$Direction r10 = android.graphics.Path.Direction.CW
            r1 = r9
            r2 = r14
            r3 = r15
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r10
            r1.addRoundRect(r2, r3, r4, r5, r6, r7, r8)
            goto L_0x009c
        L_0x008a:
            android.graphics.RectF r14 = new android.graphics.RectF
            float r15 = (float) r1
            float r0 = (float) r3
            double r1 = r1 + r5
            float r1 = (float) r1
            double r3 = r3 + r7
            float r2 = (float) r3
            r14.<init>(r15, r0, r1, r2)
            float r0 = (float) r10
            float r1 = (float) r12
            android.graphics.Path$Direction r2 = android.graphics.Path.Direction.CW
            r9.addRoundRect(r14, r0, r1, r2)
        L_0x009c:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.RectView.getPath(android.graphics.Canvas, android.graphics.Paint):android.graphics.Path");
    }
}
