package com.google.maps.android.data.kml;

import com.thunkable.live.BuildConfig;

public class KmlBoolean {
    public static boolean parseBoolean(String str) {
        return BuildConfig.VERSION_NAME.equals(str) || "true".equals(str);
    }
}
