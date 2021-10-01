package com.facebook.react.uimanager;

import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import androidx.core.view.ViewCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.C1045R;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.MatrixMathHelper;
import com.facebook.react.uimanager.ReactAccessibilityDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.util.ReactFindViewUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseViewManager<T extends View, C extends LayoutShadowNode> extends ViewManager<T, C> implements BaseViewManagerInterface<T> {
    private static final float CAMERA_DISTANCE_NORMALIZATION_MULTIPLIER = ((float) Math.sqrt(5.0d));
    private static final int PERSPECTIVE_ARRAY_INVERTED_CAMERA_DISTANCE_INDEX = 2;
    private static final String STATE_BUSY = "busy";
    private static final String STATE_CHECKED = "checked";
    private static final String STATE_EXPANDED = "expanded";
    private static final String STATE_MIXED = "mixed";
    private static MatrixMathHelper.MatrixDecompositionContext sMatrixDecompositionContext = new MatrixMathHelper.MatrixDecompositionContext();
    public static final Map<String, Integer> sStateDescription;
    private static double[] sTransformDecompositionArray = new double[16];

    static {
        HashMap hashMap = new HashMap();
        sStateDescription = hashMap;
        hashMap.put(STATE_BUSY, Integer.valueOf(C1045R.string.state_busy_description));
        sStateDescription.put(STATE_EXPANDED, Integer.valueOf(C1045R.string.state_expanded_description));
        sStateDescription.put("collapsed", Integer.valueOf(C1045R.string.state_collapsed_description));
    }

    @ReactProp(customType = "Color", defaultInt = 0, name = "backgroundColor")
    public void setBackgroundColor(T t, int i) {
        t.setBackgroundColor(i);
    }

    @ReactProp(name = "transform")
    public void setTransform(T t, ReadableArray readableArray) {
        if (readableArray == null) {
            resetTransformProperty(t);
        } else {
            setTransformProperty(t, readableArray);
        }
    }

    @ReactProp(defaultFloat = 1.0f, name = "opacity")
    public void setOpacity(T t, float f) {
        t.setAlpha(f);
    }

    @ReactProp(name = "elevation")
    public void setElevation(T t, float f) {
        ViewCompat.setElevation(t, PixelUtil.toPixelFromDIP(f));
    }

    @ReactProp(name = "zIndex")
    public void setZIndex(T t, float f) {
        ViewGroupManager.setViewZIndex(t, Math.round(f));
        ViewParent parent = t.getParent();
        if (parent instanceof ReactZIndexedViewGroup) {
            ((ReactZIndexedViewGroup) parent).updateDrawingOrder();
        }
    }

    @ReactProp(name = "renderToHardwareTextureAndroid")
    public void setRenderToHardwareTexture(T t, boolean z) {
        t.setLayerType(z ? 2 : 0, (Paint) null);
    }

    @ReactProp(name = "testID")
    public void setTestId(T t, String str) {
        t.setTag(C1045R.C1048id.react_test_id, str);
        t.setTag(str);
    }

    @ReactProp(name = "nativeID")
    public void setNativeId(T t, String str) {
        t.setTag(C1045R.C1048id.view_tag_native_id, str);
        ReactFindViewUtil.notifyViewRendered(t);
    }

    @ReactProp(name = "accessibilityLabel")
    public void setAccessibilityLabel(T t, String str) {
        t.setTag(C1045R.C1048id.accessibility_label, str);
        updateViewContentDescription(t);
    }

    @ReactProp(name = "accessibilityHint")
    public void setAccessibilityHint(T t, String str) {
        t.setTag(C1045R.C1048id.accessibility_hint, str);
        updateViewContentDescription(t);
    }

    @ReactProp(name = "accessibilityRole")
    public void setAccessibilityRole(T t, String str) {
        if (str != null) {
            t.setTag(C1045R.C1048id.accessibility_role, ReactAccessibilityDelegate.AccessibilityRole.fromValue(str));
        }
    }

    @ReactProp(name = "accessibilityState")
    public void setViewState(T t, ReadableMap readableMap) {
        if (readableMap != null) {
            t.setTag(C1045R.C1048id.accessibility_state, readableMap);
            t.setSelected(false);
            t.setEnabled(true);
            ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
            while (keySetIterator.hasNextKey()) {
                String nextKey = keySetIterator.nextKey();
                if (nextKey.equals(STATE_BUSY) || nextKey.equals(STATE_EXPANDED) || (nextKey.equals(STATE_CHECKED) && readableMap.getType(STATE_CHECKED) == ReadableType.String)) {
                    updateViewContentDescription(t);
                    return;
                } else if (Build.VERSION.SDK_INT >= 21 && t.isAccessibilityFocused()) {
                    t.sendAccessibilityEvent(1);
                }
            }
        }
    }

    private void updateViewContentDescription(T t) {
        Dynamic dynamic;
        String str = (String) t.getTag(C1045R.C1048id.accessibility_label);
        ReadableMap readableMap = (ReadableMap) t.getTag(C1045R.C1048id.accessibility_state);
        String str2 = (String) t.getTag(C1045R.C1048id.accessibility_hint);
        ArrayList arrayList = new ArrayList();
        ReadableMap readableMap2 = (ReadableMap) t.getTag(C1045R.C1048id.accessibility_value);
        if (str != null) {
            arrayList.add(str);
        }
        if (readableMap != null) {
            ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
            while (keySetIterator.hasNextKey()) {
                String nextKey = keySetIterator.nextKey();
                Dynamic dynamic2 = readableMap.getDynamic(nextKey);
                if (nextKey.equals(STATE_CHECKED) && dynamic2.getType() == ReadableType.String && dynamic2.asString().equals(STATE_MIXED)) {
                    arrayList.add(t.getContext().getString(C1045R.string.state_mixed_description));
                } else if (nextKey.equals(STATE_BUSY) && dynamic2.getType() == ReadableType.Boolean && dynamic2.asBoolean()) {
                    arrayList.add(t.getContext().getString(C1045R.string.state_busy_description));
                } else if (nextKey.equals(STATE_EXPANDED) && dynamic2.getType() == ReadableType.Boolean) {
                    arrayList.add(t.getContext().getString(dynamic2.asBoolean() ? C1045R.string.state_expanded_description : C1045R.string.state_collapsed_description));
                }
            }
        }
        if (readableMap2 != null && readableMap2.hasKey("text") && (dynamic = readableMap2.getDynamic("text")) != null && dynamic.getType() == ReadableType.String) {
            arrayList.add(dynamic.asString());
        }
        if (str2 != null) {
            arrayList.add(str2);
        }
        if (arrayList.size() > 0) {
            t.setContentDescription(TextUtils.join(", ", arrayList));
        }
    }

    @ReactProp(name = "accessibilityActions")
    public void setAccessibilityActions(T t, ReadableArray readableArray) {
        if (readableArray != null) {
            t.setTag(C1045R.C1048id.accessibility_actions, readableArray);
        }
    }

    @ReactProp(name = "accessibilityValue")
    public void setAccessibilityValue(T t, ReadableMap readableMap) {
        if (readableMap != null) {
            t.setTag(C1045R.C1048id.accessibility_value, readableMap);
            if (readableMap.hasKey("text")) {
                updateViewContentDescription(t);
            }
        }
    }

    @ReactProp(name = "importantForAccessibility")
    public void setImportantForAccessibility(T t, String str) {
        if (str == null || str.equals("auto")) {
            ViewCompat.setImportantForAccessibility(t, 0);
        } else if (str.equals("yes")) {
            ViewCompat.setImportantForAccessibility(t, 1);
        } else if (str.equals("no")) {
            ViewCompat.setImportantForAccessibility(t, 2);
        } else if (str.equals("no-hide-descendants")) {
            ViewCompat.setImportantForAccessibility(t, 4);
        }
    }

    @ReactProp(name = "rotation")
    @Deprecated
    public void setRotation(T t, float f) {
        t.setRotation(f);
    }

    @ReactProp(defaultFloat = 1.0f, name = "scaleX")
    @Deprecated
    public void setScaleX(T t, float f) {
        t.setScaleX(f);
    }

    @ReactProp(defaultFloat = 1.0f, name = "scaleY")
    @Deprecated
    public void setScaleY(T t, float f) {
        t.setScaleY(f);
    }

    @ReactProp(defaultFloat = 0.0f, name = "translateX")
    @Deprecated
    public void setTranslateX(T t, float f) {
        t.setTranslationX(PixelUtil.toPixelFromDIP(f));
    }

    @ReactProp(defaultFloat = 0.0f, name = "translateY")
    @Deprecated
    public void setTranslateY(T t, float f) {
        t.setTranslationY(PixelUtil.toPixelFromDIP(f));
    }

    @ReactProp(name = "accessibilityLiveRegion")
    public void setAccessibilityLiveRegion(T t, String str) {
        if (str == null || str.equals("none")) {
            ViewCompat.setAccessibilityLiveRegion(t, 0);
        } else if (str.equals("polite")) {
            ViewCompat.setAccessibilityLiveRegion(t, 1);
        } else if (str.equals("assertive")) {
            ViewCompat.setAccessibilityLiveRegion(t, 2);
        }
    }

    private static void setTransformProperty(View view, ReadableArray readableArray) {
        sMatrixDecompositionContext.reset();
        TransformHelper.processTransform(readableArray, sTransformDecompositionArray);
        MatrixMathHelper.decomposeMatrix(sTransformDecompositionArray, sMatrixDecompositionContext);
        view.setTranslationX(PixelUtil.toPixelFromDIP(sanitizeFloatPropertyValue((float) sMatrixDecompositionContext.translation[0])));
        view.setTranslationY(PixelUtil.toPixelFromDIP(sanitizeFloatPropertyValue((float) sMatrixDecompositionContext.translation[1])));
        view.setRotation(sanitizeFloatPropertyValue((float) sMatrixDecompositionContext.rotationDegrees[2]));
        view.setRotationX(sanitizeFloatPropertyValue((float) sMatrixDecompositionContext.rotationDegrees[0]));
        view.setRotationY(sanitizeFloatPropertyValue((float) sMatrixDecompositionContext.rotationDegrees[1]));
        view.setScaleX(sanitizeFloatPropertyValue((float) sMatrixDecompositionContext.scale[0]));
        view.setScaleY(sanitizeFloatPropertyValue((float) sMatrixDecompositionContext.scale[1]));
        double[] dArr = sMatrixDecompositionContext.perspective;
        if (dArr.length > 2) {
            float f = (float) dArr[2];
            if (f == 0.0f) {
                f = 7.8125E-4f;
            }
            float f2 = -1.0f / f;
            float f3 = DisplayMetricsHolder.getScreenDisplayMetrics().density;
            view.setCameraDistance(sanitizeFloatPropertyValue(f3 * f3 * f2 * CAMERA_DISTANCE_NORMALIZATION_MULTIPLIER));
        }
    }

    private static float sanitizeFloatPropertyValue(float f) {
        if (f >= -3.4028235E38f && f <= Float.MAX_VALUE) {
            return f;
        }
        if (f < -3.4028235E38f || f == Float.NEGATIVE_INFINITY) {
            return -3.4028235E38f;
        }
        if (f > Float.MAX_VALUE || f == Float.POSITIVE_INFINITY) {
            return Float.MAX_VALUE;
        }
        if (Float.isNaN(f)) {
            return 0.0f;
        }
        throw new IllegalStateException("Invalid float property value: " + f);
    }

    private static void resetTransformProperty(View view) {
        view.setTranslationX(PixelUtil.toPixelFromDIP(0.0f));
        view.setTranslationY(PixelUtil.toPixelFromDIP(0.0f));
        view.setRotation(0.0f);
        view.setRotationX(0.0f);
        view.setRotationY(0.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setCameraDistance(0.0f);
    }

    private void updateViewAccessibility(T t) {
        ReactAccessibilityDelegate.setDelegate(t);
    }

    /* access modifiers changed from: protected */
    public void onAfterUpdateTransaction(T t) {
        super.onAfterUpdateTransaction(t);
        updateViewAccessibility(t);
    }

    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.builder().put("topAccessibilityAction", MapBuilder.m140of("registrationName", "onAccessibilityAction")).build();
    }

    public void setBorderRadius(T t, float f) {
        logUnsupportedPropertyWarning(ViewProps.BORDER_RADIUS);
    }

    public void setBorderBottomLeftRadius(T t, float f) {
        logUnsupportedPropertyWarning(ViewProps.BORDER_BOTTOM_LEFT_RADIUS);
    }

    public void setBorderBottomRightRadius(T t, float f) {
        logUnsupportedPropertyWarning(ViewProps.BORDER_BOTTOM_RIGHT_RADIUS);
    }

    public void setBorderTopLeftRadius(T t, float f) {
        logUnsupportedPropertyWarning(ViewProps.BORDER_TOP_LEFT_RADIUS);
    }

    public void setBorderTopRightRadius(T t, float f) {
        logUnsupportedPropertyWarning(ViewProps.BORDER_TOP_RIGHT_RADIUS);
    }

    private void logUnsupportedPropertyWarning(String str) {
        FLog.m106w(ReactConstants.TAG, "%s doesn't support property '%s'", getName(), str);
    }
}
