package com.facebook.react.views.text.frescosupport;

import android.content.Context;
import android.net.Uri;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.facebook.react.views.text.TextInlineImageSpan;
import java.util.Locale;

public class FrescoBasedReactTextInlineImageShadowNode extends ReactTextInlineImageShadowNode {
    private final Object mCallerContext;
    private final AbstractDraweeControllerBuilder mDraweeControllerBuilder;
    private ReadableMap mHeaders;
    private float mHeight = Float.NaN;
    private String mResizeMode;
    private int mTintColor = 0;
    private Uri mUri;
    private float mWidth = Float.NaN;

    public boolean isVirtual() {
        return true;
    }

    public FrescoBasedReactTextInlineImageShadowNode(AbstractDraweeControllerBuilder abstractDraweeControllerBuilder, Object obj) {
        this.mDraweeControllerBuilder = abstractDraweeControllerBuilder;
        this.mCallerContext = obj;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        if (r1.getScheme() == null) goto L_0x0028;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0036  */
    @com.facebook.react.uimanager.annotations.ReactProp(name = "src")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSource(com.facebook.react.bridge.ReadableArray r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 == 0) goto L_0x0017
            int r1 = r4.size()
            if (r1 != 0) goto L_0x000a
            goto L_0x0017
        L_0x000a:
            r1 = 0
            com.facebook.react.bridge.ReadableMap r4 = r4.getMap(r1)
            java.lang.String r1 = "uri"
            java.lang.String r4 = r4.getString(r1)
            goto L_0x0018
        L_0x0017:
            r4 = r0
        L_0x0018:
            if (r4 == 0) goto L_0x0032
            android.net.Uri r1 = android.net.Uri.parse(r4)     // Catch:{ Exception -> 0x0027 }
            java.lang.String r2 = r1.getScheme()     // Catch:{ Exception -> 0x0025 }
            if (r2 != 0) goto L_0x0025
            goto L_0x0028
        L_0x0025:
            r0 = r1
            goto L_0x0028
        L_0x0027:
        L_0x0028:
            if (r0 != 0) goto L_0x0032
            com.facebook.react.uimanager.ThemedReactContext r0 = r3.getThemedContext()
            android.net.Uri r0 = getResourceDrawableUri(r0, r4)
        L_0x0032:
            android.net.Uri r4 = r3.mUri
            if (r0 == r4) goto L_0x0039
            r3.markUpdated()
        L_0x0039:
            r3.mUri = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.text.frescosupport.FrescoBasedReactTextInlineImageShadowNode.setSource(com.facebook.react.bridge.ReadableArray):void");
    }

    @ReactProp(name = "headers")
    public void setHeaders(ReadableMap readableMap) {
        this.mHeaders = readableMap;
    }

    @ReactProp(name = "tintColor")
    public void setTintColor(int i) {
        this.mTintColor = i;
    }

    public void setWidth(Dynamic dynamic) {
        if (dynamic.getType() == ReadableType.Number) {
            this.mWidth = (float) dynamic.asDouble();
            return;
        }
        throw new JSApplicationIllegalArgumentException("Inline images must not have percentage based width");
    }

    public void setHeight(Dynamic dynamic) {
        if (dynamic.getType() == ReadableType.Number) {
            this.mHeight = (float) dynamic.asDouble();
            return;
        }
        throw new JSApplicationIllegalArgumentException("Inline images must not have percentage based height");
    }

    @ReactProp(name = "resizeMode")
    public void setResizeMode(String str) {
        this.mResizeMode = str;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public ReadableMap getHeaders() {
        return this.mHeaders;
    }

    private static Uri getResourceDrawableUri(Context context, String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return new Uri.Builder().scheme(UriUtil.LOCAL_RESOURCE_SCHEME).path(String.valueOf(context.getResources().getIdentifier(str.toLowerCase(Locale.getDefault()).replace("-", "_"), "drawable", context.getPackageName()))).build();
    }

    public TextInlineImageSpan buildInlineImageSpan() {
        return new FrescoBasedReactTextInlineImageSpan(getThemedContext().getResources(), (int) Math.ceil((double) this.mHeight), (int) Math.ceil((double) this.mWidth), this.mTintColor, getUri(), getHeaders(), getDraweeControllerBuilder(), getCallerContext(), this.mResizeMode);
    }

    public AbstractDraweeControllerBuilder getDraweeControllerBuilder() {
        return this.mDraweeControllerBuilder;
    }

    public Object getCallerContext() {
        return this.mCallerContext;
    }
}
