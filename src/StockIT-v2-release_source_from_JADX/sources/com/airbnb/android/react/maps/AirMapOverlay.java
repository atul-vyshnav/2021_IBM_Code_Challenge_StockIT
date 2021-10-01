package com.airbnb.android.react.maps;

import android.content.Context;
import android.graphics.Bitmap;
import com.facebook.react.bridge.ReadableArray;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class AirMapOverlay extends AirMapFeature implements ImageReadable {
    private LatLngBounds bounds;
    private GroundOverlay groundOverlay;
    private GroundOverlayOptions groundOverlayOptions;
    private Bitmap iconBitmap;
    private BitmapDescriptor iconBitmapDescriptor;
    private final ImageReader mImageReader;
    private GoogleMap map;
    private boolean tappable;
    private float transparency;
    private float zIndex;

    public AirMapOverlay(Context context) {
        super(context);
        this.mImageReader = new ImageReader(context, getResources(), this);
    }

    public void setBounds(ReadableArray readableArray) {
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(readableArray.getArray(1).getDouble(0), readableArray.getArray(0).getDouble(1)), new LatLng(readableArray.getArray(0).getDouble(0), readableArray.getArray(1).getDouble(1)));
        this.bounds = latLngBounds;
        GroundOverlay groundOverlay2 = this.groundOverlay;
        if (groundOverlay2 != null) {
            groundOverlay2.setPositionFromBounds(latLngBounds);
        }
    }

    public void setZIndex(float f) {
        this.zIndex = f;
        GroundOverlay groundOverlay2 = this.groundOverlay;
        if (groundOverlay2 != null) {
            groundOverlay2.setZIndex(f);
        }
    }

    public void setTransparency(float f) {
        this.transparency = f;
        GroundOverlay groundOverlay2 = this.groundOverlay;
        if (groundOverlay2 != null) {
            groundOverlay2.setTransparency(f);
        }
    }

    public void setImage(String str) {
        this.mImageReader.setImage(str);
    }

    public void setTappable(boolean z) {
        this.tappable = z;
        GroundOverlay groundOverlay2 = this.groundOverlay;
        if (groundOverlay2 != null) {
            groundOverlay2.setClickable(z);
        }
    }

    public GroundOverlayOptions getGroundOverlayOptions() {
        if (this.groundOverlayOptions == null) {
            this.groundOverlayOptions = createGroundOverlayOptions();
        }
        return this.groundOverlayOptions;
    }

    private GroundOverlayOptions createGroundOverlayOptions() {
        GroundOverlayOptions groundOverlayOptions2 = this.groundOverlayOptions;
        if (groundOverlayOptions2 != null) {
            return groundOverlayOptions2;
        }
        GroundOverlayOptions groundOverlayOptions3 = new GroundOverlayOptions();
        BitmapDescriptor bitmapDescriptor = this.iconBitmapDescriptor;
        if (bitmapDescriptor != null) {
            groundOverlayOptions3.image(bitmapDescriptor);
        } else {
            groundOverlayOptions3.image(BitmapDescriptorFactory.defaultMarker());
            groundOverlayOptions3.visible(false);
        }
        groundOverlayOptions3.positionFromBounds(this.bounds);
        groundOverlayOptions3.zIndex(this.zIndex);
        return groundOverlayOptions3;
    }

    public Object getFeature() {
        return this.groundOverlay;
    }

    public void addToMap(GoogleMap googleMap) {
        GroundOverlayOptions groundOverlayOptions2 = getGroundOverlayOptions();
        if (groundOverlayOptions2 != null) {
            GroundOverlay addGroundOverlay = googleMap.addGroundOverlay(groundOverlayOptions2);
            this.groundOverlay = addGroundOverlay;
            addGroundOverlay.setClickable(this.tappable);
            return;
        }
        this.map = googleMap;
    }

    public void removeFromMap(GoogleMap googleMap) {
        this.map = null;
        GroundOverlay groundOverlay2 = this.groundOverlay;
        if (groundOverlay2 != null) {
            groundOverlay2.remove();
            this.groundOverlay = null;
            this.groundOverlayOptions = null;
        }
    }

    public void setIconBitmap(Bitmap bitmap) {
        this.iconBitmap = bitmap;
    }

    public void setIconBitmapDescriptor(BitmapDescriptor bitmapDescriptor) {
        this.iconBitmapDescriptor = bitmapDescriptor;
    }

    public void update() {
        GroundOverlay groundOverlay2 = getGroundOverlay();
        this.groundOverlay = groundOverlay2;
        if (groundOverlay2 != null) {
            groundOverlay2.setVisible(true);
            this.groundOverlay.setImage(this.iconBitmapDescriptor);
            this.groundOverlay.setTransparency(this.transparency);
            this.groundOverlay.setClickable(this.tappable);
        }
    }

    private GroundOverlay getGroundOverlay() {
        GroundOverlayOptions groundOverlayOptions2;
        GroundOverlay groundOverlay2 = this.groundOverlay;
        if (groundOverlay2 != null) {
            return groundOverlay2;
        }
        if (this.map == null || (groundOverlayOptions2 = getGroundOverlayOptions()) == null) {
            return null;
        }
        return this.map.addGroundOverlay(groundOverlayOptions2);
    }
}
