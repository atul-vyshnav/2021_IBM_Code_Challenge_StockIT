package org.unimodules.adapters.react.views;

import android.util.Log;
import android.view.View;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.common.MapBuilder;
import java.util.HashMap;
import java.util.Map;
import org.unimodules.adapters.react.ArgumentsHelper;
import org.unimodules.core.ViewManager;

public class ViewManagerAdapterUtils {
    static String getViewManagerAdapterName(ViewManager viewManager) {
        return "ViewManagerAdapter_" + viewManager.getName();
    }

    static Map<String, Object> getConstants(ViewManager viewManager) {
        HashMap hashMap = new HashMap();
        hashMap.put("eventNames", viewManager.getExportedEventNames());
        return hashMap;
    }

    static Map<String, Object> getExportedCustomDirectEventTypeConstants(ViewManager viewManager) {
        MapBuilder.Builder builder = MapBuilder.builder();
        for (String next : viewManager.getExportedEventNames()) {
            if (next instanceof String) {
                builder.put(next, MapBuilder.m140of("registrationName", next));
            }
        }
        return builder.build();
    }

    static <V extends View> void setProxiedProperties(String str, ViewManager<V> viewManager, V v, ReadableMap readableMap) {
        ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
        while (keySetIterator.hasNextKey()) {
            String nextKey = keySetIterator.nextKey();
            try {
                ViewManager.PropSetterInfo propSetterInfo = viewManager.getPropSetterInfos().get(nextKey);
                if (propSetterInfo != null) {
                    viewManager.updateProp(v, nextKey, ArgumentsHelper.getNativeArgumentForExpectedClass(readableMap.getDynamic(nextKey), propSetterInfo.getExpectedValueClass()));
                } else {
                    throw new IllegalArgumentException("No setter found for prop " + nextKey + " in " + str);
                }
            } catch (Exception e) {
                Log.e(str, "Error when setting prop " + nextKey + ". " + e.getMessage());
            }
        }
    }
}
