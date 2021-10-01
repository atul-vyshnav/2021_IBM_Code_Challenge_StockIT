package com.google.maps.android.data.kml;

import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.HashMap;
import java.util.Map;

public class KmlGroundOverlay {
    private final GroundOverlayOptions mGroundOverlayOptions;
    private String mImageUrl;
    private LatLngBounds mLatLngBox;
    private final Map<String, String> mProperties;

    KmlGroundOverlay(String str, LatLngBounds latLngBounds, float f, int i, HashMap<String, String> hashMap, float f2) {
        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
        this.mGroundOverlayOptions = groundOverlayOptions;
        this.mImageUrl = str;
        this.mProperties = hashMap;
        if (latLngBounds != null) {
            this.mLatLngBox = latLngBounds;
            groundOverlayOptions.positionFromBounds(latLngBounds);
            this.mGroundOverlayOptions.bearing(f2);
            this.mGroundOverlayOptions.zIndex(f);
            this.mGroundOverlayOptions.visible(i != 0);
            return;
        }
        throw new IllegalArgumentException("No LatLonBox given");
    }

    public String getImageUrl() {
        return this.mImageUrl;
    }

    public LatLngBounds getLatLngBox() {
        return this.mLatLngBox;
    }

    public Iterable<String> getProperties() {
        return this.mProperties.keySet();
    }

    public String getProperty(String str) {
        return this.mProperties.get(str);
    }

    public boolean hasProperty(String str) {
        return this.mProperties.get(str) != null;
    }

    /* access modifiers changed from: package-private */
    public GroundOverlayOptions getGroundOverlayOptions() {
        return this.mGroundOverlayOptions;
    }

    public String toString() {
        return "GroundOverlay" + "{" + "\n properties=" + this.mProperties + ",\n image url=" + this.mImageUrl + ",\n LatLngBox=" + this.mLatLngBox + "\n}\n";
    }
}
