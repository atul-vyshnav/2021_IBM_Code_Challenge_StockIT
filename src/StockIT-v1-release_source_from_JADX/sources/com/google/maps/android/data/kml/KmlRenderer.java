package com.google.maps.android.data.kml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.Renderer;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KmlRenderer extends Renderer {
    private static final String LOG_TAG = "KmlRenderer";
    /* access modifiers changed from: private */
    public ArrayList<KmlContainer> mContainers;
    private boolean mGroundOverlayImagesDownloaded = false;
    private final ArrayList<String> mGroundOverlayUrls = new ArrayList<>();
    /* access modifiers changed from: private */
    public HashMap<KmlGroundOverlay, GroundOverlay> mGroundOverlays;
    private boolean mMarkerIconsDownloaded = false;

    KmlRenderer(GoogleMap googleMap, Context context) {
        super(googleMap, context);
    }

    private static BitmapDescriptor scaleIcon(Bitmap bitmap, Double d) {
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, (int) (((double) bitmap.getWidth()) * d.doubleValue()), (int) (((double) bitmap.getHeight()) * d.doubleValue()), false));
    }

    private void removePlacemarks(HashMap<? extends Feature, Object> hashMap) {
        removeFeatures(hashMap);
    }

    static boolean getContainerVisibility(KmlContainer kmlContainer, boolean z) {
        boolean z2 = !kmlContainer.hasProperty("visibility") || Integer.parseInt(kmlContainer.getProperty("visibility")) != 0;
        if (!z || !z2) {
            return false;
        }
        return true;
    }

    private void removeGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> hashMap) {
        for (GroundOverlay remove : hashMap.values()) {
            remove.remove();
        }
    }

    private void removeContainers(Iterable<KmlContainer> iterable) {
        for (KmlContainer next : iterable) {
            removePlacemarks(next.getPlacemarksHashMap());
            removeGroundOverlays(next.getGroundOverlayHashMap());
            removeContainers(next.getContainers());
        }
    }

    public void addLayerToMap() {
        setLayerVisibility(true);
        this.mGroundOverlays = getGroundOverlayMap();
        this.mContainers = getContainerList();
        putStyles();
        assignStyleMap(getStyleMaps(), getStylesRenderer());
        addGroundOverlays(this.mGroundOverlays, this.mContainers);
        addContainerGroupToMap(this.mContainers, true);
        addPlacemarksToMap(getAllFeatures());
        if (!this.mGroundOverlayImagesDownloaded) {
            downloadGroundOverlays();
        }
        if (!this.mMarkerIconsDownloaded) {
            downloadMarkerIcons();
        }
    }

    /* access modifiers changed from: package-private */
    public void storeKmlData(HashMap<String, KmlStyle> hashMap, HashMap<String, String> hashMap2, HashMap<KmlPlacemark, Object> hashMap3, ArrayList<KmlContainer> arrayList, HashMap<KmlGroundOverlay, GroundOverlay> hashMap4) {
        storeData(hashMap, hashMap2, hashMap3, arrayList, hashMap4);
    }

    public void setMap(GoogleMap googleMap) {
        removeLayerFromMap();
        super.setMap(googleMap);
        addLayerToMap();
    }

    /* access modifiers changed from: package-private */
    public boolean hasKmlPlacemarks() {
        return hasFeatures();
    }

    /* access modifiers changed from: package-private */
    public Iterable<? extends Feature> getKmlPlacemarks() {
        return getFeatures();
    }

    public boolean hasNestedContainers() {
        return this.mContainers.size() > 0;
    }

    public Iterable<KmlContainer> getNestedContainers() {
        return this.mContainers;
    }

    public Iterable<KmlGroundOverlay> getGroundOverlays() {
        return this.mGroundOverlays.keySet();
    }

    public void removeLayerFromMap() {
        removePlacemarks(getAllFeatures());
        removeGroundOverlays(this.mGroundOverlays);
        if (hasNestedContainers()) {
            removeContainers(getNestedContainers());
        }
        setLayerVisibility(false);
        clearStylesRenderer();
    }

    private void addPlacemarksToMap(HashMap<? extends Feature, Object> hashMap) {
        for (Feature addFeature : hashMap.keySet()) {
            addFeature(addFeature);
        }
    }

    private void addContainerGroupToMap(Iterable<KmlContainer> iterable, boolean z) {
        for (KmlContainer next : iterable) {
            boolean containerVisibility = getContainerVisibility(next, z);
            if (next.getStyles() != null) {
                putStyles(next.getStyles());
            }
            if (next.getStyleMap() != null) {
                super.assignStyleMap(next.getStyleMap(), getStylesRenderer());
            }
            addContainerObjectToMap(next, containerVisibility);
            if (next.hasContainers()) {
                addContainerGroupToMap(next.getContainers(), containerVisibility);
            }
        }
    }

    private void addContainerObjectToMap(KmlContainer kmlContainer, boolean z) {
        for (Feature next : kmlContainer.getPlacemarks()) {
            boolean z2 = z && getPlacemarkVisibility(next);
            if (next.getGeometry() != null) {
                String id = next.getId();
                Geometry geometry = next.getGeometry();
                KmlStyle placemarkStyle = getPlacemarkStyle(id);
                KmlPlacemark kmlPlacemark = (KmlPlacemark) next;
                Object addKmlPlacemarkToMap = addKmlPlacemarkToMap(kmlPlacemark, geometry, placemarkStyle, kmlPlacemark.getInlineStyle(), z2);
                kmlContainer.setPlacemark(kmlPlacemark, addKmlPlacemarkToMap);
                putContainerFeature(addKmlPlacemarkToMap, next);
            }
        }
    }

    private void downloadMarkerIcons() {
        this.mMarkerIconsDownloaded = true;
        Iterator<String> it = getMarkerIconUrls().iterator();
        while (it.hasNext()) {
            new MarkerIconImageDownload(it.next()).execute(new String[0]);
            it.remove();
        }
    }

    /* access modifiers changed from: private */
    public void addIconToMarkers(String str, HashMap<KmlPlacemark, Object> hashMap) {
        for (Feature next : hashMap.keySet()) {
            KmlStyle kmlStyle = getStylesRenderer().get(next.getId());
            KmlPlacemark kmlPlacemark = (KmlPlacemark) next;
            KmlStyle inlineStyle = kmlPlacemark.getInlineStyle();
            if ("Point".equals(next.getGeometry().getGeometryType())) {
                boolean z = true;
                boolean z2 = inlineStyle != null && str.equals(inlineStyle.getIconUrl());
                if (kmlStyle == null || !str.equals(kmlStyle.getIconUrl())) {
                    z = false;
                }
                if (z2) {
                    scaleBitmap(inlineStyle, hashMap, kmlPlacemark);
                } else if (z) {
                    scaleBitmap(kmlStyle, hashMap, kmlPlacemark);
                }
            }
        }
    }

    private void scaleBitmap(KmlStyle kmlStyle, HashMap<KmlPlacemark, Object> hashMap, KmlPlacemark kmlPlacemark) {
        double iconScale = kmlStyle.getIconScale();
        ((Marker) hashMap.get(kmlPlacemark)).setIcon(scaleIcon(getImagesCache().get(kmlStyle.getIconUrl()), Double.valueOf(iconScale)));
    }

    /* access modifiers changed from: private */
    public void addContainerGroupIconsToMarkers(String str, Iterable<KmlContainer> iterable) {
        for (KmlContainer next : iterable) {
            addIconToMarkers(str, next.getPlacemarksHashMap());
            if (next.hasContainers()) {
                addContainerGroupIconsToMarkers(str, next.getContainers());
            }
        }
    }

    private void addGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> hashMap, Iterable<KmlContainer> iterable) {
        addGroundOverlays(hashMap);
        for (KmlContainer next : iterable) {
            addGroundOverlays(next.getGroundOverlayHashMap(), next.getContainers());
        }
    }

    private void addGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> hashMap) {
        for (KmlGroundOverlay next : hashMap.keySet()) {
            String imageUrl = next.getImageUrl();
            if (!(imageUrl == null || next.getLatLngBox() == null)) {
                if (getImagesCache().get(imageUrl) != null) {
                    addGroundOverlayToMap(imageUrl, this.mGroundOverlays, true);
                } else if (!this.mGroundOverlayUrls.contains(imageUrl)) {
                    this.mGroundOverlayUrls.add(imageUrl);
                }
            }
        }
    }

    private void downloadGroundOverlays() {
        this.mGroundOverlayImagesDownloaded = true;
        Iterator<String> it = this.mGroundOverlayUrls.iterator();
        while (it.hasNext()) {
            new GroundOverlayImageDownload(it.next()).execute(new String[0]);
            it.remove();
        }
    }

    /* access modifiers changed from: private */
    public void addGroundOverlayToMap(String str, HashMap<KmlGroundOverlay, GroundOverlay> hashMap, boolean z) {
        BitmapDescriptor fromBitmap = BitmapDescriptorFactory.fromBitmap(getImagesCache().get(str));
        for (KmlGroundOverlay next : hashMap.keySet()) {
            if (next.getImageUrl().equals(str)) {
                GroundOverlay attachGroundOverlay = attachGroundOverlay(next.getGroundOverlayOptions().image(fromBitmap));
                if (!z) {
                    attachGroundOverlay.setVisible(false);
                }
                hashMap.put(next, attachGroundOverlay);
            }
        }
    }

    /* access modifiers changed from: private */
    public void addGroundOverlayInContainerGroups(String str, Iterable<KmlContainer> iterable, boolean z) {
        for (KmlContainer next : iterable) {
            boolean containerVisibility = getContainerVisibility(next, z);
            addGroundOverlayToMap(str, next.getGroundOverlayHashMap(), containerVisibility);
            if (next.hasContainers()) {
                addGroundOverlayInContainerGroups(str, next.getContainers(), containerVisibility);
            }
        }
    }

    private class MarkerIconImageDownload extends AsyncTask<String, Void, Bitmap> {
        private final String mIconUrl;

        public MarkerIconImageDownload(String str) {
            this.mIconUrl = str;
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(String... strArr) {
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(this.mIconUrl).getContent());
            } catch (MalformedURLException unused) {
                return BitmapFactory.decodeFile(this.mIconUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                Log.e(KmlRenderer.LOG_TAG, "Image at this URL could not be found " + this.mIconUrl);
                return;
            }
            KmlRenderer.this.putImagesCache(this.mIconUrl, bitmap);
            if (KmlRenderer.this.isLayerOnMap()) {
                KmlRenderer kmlRenderer = KmlRenderer.this;
                kmlRenderer.addIconToMarkers(this.mIconUrl, kmlRenderer.getAllFeatures());
                KmlRenderer kmlRenderer2 = KmlRenderer.this;
                kmlRenderer2.addContainerGroupIconsToMarkers(this.mIconUrl, kmlRenderer2.mContainers);
            }
        }
    }

    private class GroundOverlayImageDownload extends AsyncTask<String, Void, Bitmap> {
        private final String mGroundOverlayUrl;

        public GroundOverlayImageDownload(String str) {
            this.mGroundOverlayUrl = str;
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(String... strArr) {
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(this.mGroundOverlayUrl).getContent());
            } catch (MalformedURLException unused) {
                return BitmapFactory.decodeFile(this.mGroundOverlayUrl);
            } catch (IOException e) {
                Log.e(KmlRenderer.LOG_TAG, "Image [" + this.mGroundOverlayUrl + "] download issue", e);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                Log.e(KmlRenderer.LOG_TAG, "Image at this URL could not be found " + this.mGroundOverlayUrl);
                return;
            }
            KmlRenderer.this.putImagesCache(this.mGroundOverlayUrl, bitmap);
            if (KmlRenderer.this.isLayerOnMap()) {
                KmlRenderer kmlRenderer = KmlRenderer.this;
                kmlRenderer.addGroundOverlayToMap(this.mGroundOverlayUrl, kmlRenderer.mGroundOverlays, true);
                KmlRenderer kmlRenderer2 = KmlRenderer.this;
                kmlRenderer2.addGroundOverlayInContainerGroups(this.mGroundOverlayUrl, kmlRenderer2.mContainers, true);
            }
        }
    }
}
