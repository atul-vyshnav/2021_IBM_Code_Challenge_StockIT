package com.facebook.react.views.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.TypedValue;
import com.amplitude.api.DeviceInfo;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.uimanager.PixelUtil;

public class ReactDrawableHelper {
    private static final TypedValue sResolveOutValue = new TypedValue();

    public static Drawable createDrawableFromJSDescription(Context context, ReadableMap readableMap) {
        String string = readableMap.getString("type");
        if ("ThemeAttrAndroid".equals(string)) {
            String string2 = readableMap.getString("attribute");
            SoftAssertions.assertNotNull(string2);
            int identifier = context.getResources().getIdentifier(string2, "attr", DeviceInfo.OS_NAME);
            if (identifier == 0) {
                throw new JSApplicationIllegalArgumentException("Attribute " + string2 + " couldn't be found in the resource list");
            } else if (context.getTheme().resolveAttribute(identifier, sResolveOutValue, true)) {
                return setRadius(readableMap, getDefaultThemeDrawable(context));
            } else {
                throw new JSApplicationIllegalArgumentException("Attribute " + string2 + " couldn't be resolved into a drawable");
            }
        } else if ("RippleAndroid".equals(string)) {
            return setRadius(readableMap, getRippleDrawable(context, readableMap));
        } else {
            throw new JSApplicationIllegalArgumentException("Invalid type for android drawable: " + string);
        }
    }

    private static Drawable getDefaultThemeDrawable(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getResources().getDrawable(sResolveOutValue.resourceId, context.getTheme());
        }
        return context.getResources().getDrawable(sResolveOutValue.resourceId);
    }

    private static RippleDrawable getRippleDrawable(Context context, ReadableMap readableMap) {
        if (Build.VERSION.SDK_INT >= 21) {
            int color = getColor(context, readableMap);
            return new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{color}), (Drawable) null, getMask(readableMap));
        }
        throw new JSApplicationIllegalArgumentException("Ripple drawable is not available on android API <21");
    }

    private static Drawable setRadius(ReadableMap readableMap, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 23 && readableMap.hasKey("rippleRadius") && (drawable instanceof RippleDrawable)) {
            ((RippleDrawable) drawable).setRadius((int) PixelUtil.toPixelFromDIP(readableMap.getDouble("rippleRadius")));
        }
        return drawable;
    }

    private static int getColor(Context context, ReadableMap readableMap) {
        if (readableMap.hasKey("color") && !readableMap.isNull("color")) {
            return readableMap.getInt("color");
        }
        if (context.getTheme().resolveAttribute(16843820, sResolveOutValue, true)) {
            return context.getResources().getColor(sResolveOutValue.resourceId);
        }
        throw new JSApplicationIllegalArgumentException("Attribute colorControlHighlight couldn't be resolved into a drawable");
    }

    private static Drawable getMask(ReadableMap readableMap) {
        if (!readableMap.hasKey("borderless") || readableMap.isNull("borderless") || !readableMap.getBoolean("borderless")) {
            return new ColorDrawable(-1);
        }
        return null;
    }
}
