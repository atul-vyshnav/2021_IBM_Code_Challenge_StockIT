package com.airbnb.android.react.maps;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.WeightedLatLng;

public class AirMapHeatmapManager extends ViewGroupManager<AirMapHeatmap> {
    public String getName() {
        return "AIRMapHeatmap";
    }

    public AirMapHeatmap createViewInstance(ThemedReactContext themedReactContext) {
        return new AirMapHeatmap(themedReactContext);
    }

    @ReactProp(name = "points")
    public void setPoints(AirMapHeatmap airMapHeatmap, ReadableArray readableArray) {
        WeightedLatLng weightedLatLng;
        WeightedLatLng[] weightedLatLngArr = new WeightedLatLng[readableArray.size()];
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableMap map = readableArray.getMap(i);
            LatLng latLng = new LatLng(map.getDouble("latitude"), map.getDouble("longitude"));
            if (map.hasKey("weight")) {
                weightedLatLng = new WeightedLatLng(latLng, map.getDouble("weight"));
            } else {
                weightedLatLng = new WeightedLatLng(latLng);
            }
            weightedLatLngArr[i] = weightedLatLng;
        }
        airMapHeatmap.setPoints(weightedLatLngArr);
    }

    @ReactProp(name = "gradient")
    public void setGradient(AirMapHeatmap airMapHeatmap, ReadableMap readableMap) {
        ReadableArray array = readableMap.getArray("colors");
        int[] iArr = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            iArr[i] = array.getInt(i);
        }
        ReadableArray array2 = readableMap.getArray("startPoints");
        float[] fArr = new float[array2.size()];
        for (int i2 = 0; i2 < array2.size(); i2++) {
            fArr[i2] = (float) array2.getDouble(i2);
        }
        if (readableMap.hasKey("colorMapSize")) {
            airMapHeatmap.setGradient(new Gradient(iArr, fArr, readableMap.getInt("colorMapSize")));
        } else {
            airMapHeatmap.setGradient(new Gradient(iArr, fArr));
        }
    }

    @ReactProp(name = "opacity")
    public void setOpacity(AirMapHeatmap airMapHeatmap, double d) {
        airMapHeatmap.setOpacity(d);
    }

    @ReactProp(name = "radius")
    public void setRadius(AirMapHeatmap airMapHeatmap, int i) {
        airMapHeatmap.setRadius(i);
    }
}
