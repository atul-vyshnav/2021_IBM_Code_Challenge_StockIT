package com.airbnb.android.react.maps;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import expo.modules.imagepicker.ImagePickerConstants;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(name = "AirMapModule")
public class AirMapModule extends ReactContextBaseJavaModule {
    public static final String NAME = "AirMapModule";
    private static final String SNAPSHOT_FORMAT_JPG = "jpg";
    private static final String SNAPSHOT_FORMAT_PNG = "png";
    private static final String SNAPSHOT_RESULT_BASE64 = "base64";
    private static final String SNAPSHOT_RESULT_FILE = "file";

    public String getName() {
        return NAME;
    }

    public AirMapModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        hashMap.put("legalNotice", "This license information is displayed in Settings > Google > Open Source on any device running Google Play services.");
        return hashMap;
    }

    public Activity getActivity() {
        return getCurrentActivity();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    @ReactMethod
    public void takeSnapshot(int i, ReadableMap readableMap, Promise promise) {
        Bitmap.CompressFormat compressFormat;
        ReadableMap readableMap2 = readableMap;
        final ReactApplicationContext reactApplicationContext = getReactApplicationContext();
        final String string = readableMap2.hasKey("format") ? readableMap2.getString("format") : SNAPSHOT_FORMAT_PNG;
        if (string.equals(SNAPSHOT_FORMAT_PNG)) {
            compressFormat = Bitmap.CompressFormat.PNG;
        } else {
            compressFormat = string.equals(SNAPSHOT_FORMAT_JPG) ? Bitmap.CompressFormat.JPEG : null;
        }
        final Bitmap.CompressFormat compressFormat2 = compressFormat;
        final double d = readableMap2.hasKey(ImagePickerConstants.OPTION_QUALITY) ? readableMap2.getDouble(ImagePickerConstants.OPTION_QUALITY) : 1.0d;
        DisplayMetrics displayMetrics = reactApplicationContext.getResources().getDisplayMetrics();
        int i2 = 0;
        Integer valueOf = Integer.valueOf(readableMap2.hasKey("width") ? (int) (((double) displayMetrics.density) * readableMap2.getDouble("width")) : 0);
        if (readableMap2.hasKey("height")) {
            i2 = (int) (((double) displayMetrics.density) * readableMap2.getDouble("height"));
        }
        Integer valueOf2 = Integer.valueOf(i2);
        final int i3 = i;
        final Promise promise2 = promise;
        final Integer num = valueOf;
        final Integer num2 = valueOf2;
        final String string2 = readableMap2.hasKey("result") ? readableMap2.getString("result") : "file";
        ((UIManagerModule) reactApplicationContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i3);
                if (airMapView == null) {
                    promise2.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise2.reject("AirMapView.map is not valid");
                } else {
                    airMapView.map.snapshot(new GoogleMap.SnapshotReadyCallback() {
                        public void onSnapshotReady(@Nullable Bitmap bitmap) {
                            if (bitmap == null) {
                                promise2.reject("Failed to generate bitmap, snapshot = null");
                                return;
                            }
                            if (!(num.intValue() == 0 || num2.intValue() == 0 || (num.intValue() == bitmap.getWidth() && num2.intValue() == bitmap.getHeight()))) {
                                bitmap = Bitmap.createScaledBitmap(bitmap, num.intValue(), num2.intValue(), true);
                            }
                            if (string2.equals("file")) {
                                try {
                                    File createTempFile = File.createTempFile("AirMapSnapshot", "." + string, reactApplicationContext.getCacheDir());
                                    FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
                                    bitmap.compress(compressFormat2, (int) (d * 100.0d), fileOutputStream);
                                    AirMapModule.closeQuietly(fileOutputStream);
                                    promise2.resolve(Uri.fromFile(createTempFile).toString());
                                } catch (Exception e) {
                                    promise2.reject((Throwable) e);
                                }
                            } else if (string2.equals("base64")) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(compressFormat2, (int) (d * 100.0d), byteArrayOutputStream);
                                AirMapModule.closeQuietly(byteArrayOutputStream);
                                promise2.resolve(Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2));
                            }
                        }
                    });
                }
            }
        });
    }

    @ReactMethod
    public void getCamera(final int i, final Promise promise) {
        ((UIManagerModule) getReactApplicationContext().getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i);
                if (airMapView == null) {
                    promise.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise.reject("AirMapView.map is not valid");
                } else {
                    CameraPosition cameraPosition = airMapView.map.getCameraPosition();
                    WritableNativeMap writableNativeMap = new WritableNativeMap();
                    writableNativeMap.putDouble("latitude", cameraPosition.target.latitude);
                    writableNativeMap.putDouble("longitude", cameraPosition.target.longitude);
                    WritableNativeMap writableNativeMap2 = new WritableNativeMap();
                    writableNativeMap2.putMap(TtmlNode.CENTER, writableNativeMap);
                    writableNativeMap2.putDouble("heading", (double) cameraPosition.bearing);
                    writableNativeMap2.putDouble("zoom", (double) cameraPosition.zoom);
                    writableNativeMap2.putDouble("pitch", (double) cameraPosition.tilt);
                    promise.resolve(writableNativeMap2);
                }
            }
        });
    }

    @ReactMethod
    public void pointForCoordinate(int i, ReadableMap readableMap, Promise promise) {
        ReactApplicationContext reactApplicationContext = getReactApplicationContext();
        final double d = (double) reactApplicationContext.getResources().getDisplayMetrics().density;
        double d2 = 0.0d;
        double d3 = readableMap.hasKey("latitude") ? readableMap.getDouble("latitude") : 0.0d;
        if (readableMap.hasKey("longitude")) {
            d2 = readableMap.getDouble("longitude");
        }
        final LatLng latLng = new LatLng(d3, d2);
        final int i2 = i;
        final Promise promise2 = promise;
        ((UIManagerModule) reactApplicationContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i2);
                if (airMapView == null) {
                    promise2.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise2.reject("AirMapView.map is not valid");
                } else {
                    Point screenLocation = airMapView.map.getProjection().toScreenLocation(latLng);
                    WritableNativeMap writableNativeMap = new WritableNativeMap();
                    writableNativeMap.putDouble("x", ((double) screenLocation.x) / d);
                    writableNativeMap.putDouble("y", ((double) screenLocation.y) / d);
                    promise2.resolve(writableNativeMap);
                }
            }
        });
    }

    @ReactMethod
    public void coordinateForPoint(final int i, ReadableMap readableMap, final Promise promise) {
        ReactApplicationContext reactApplicationContext = getReactApplicationContext();
        double d = (double) reactApplicationContext.getResources().getDisplayMetrics().density;
        int i2 = 0;
        int i3 = readableMap.hasKey("x") ? (int) (readableMap.getDouble("x") * d) : 0;
        if (readableMap.hasKey("y")) {
            i2 = (int) (readableMap.getDouble("y") * d);
        }
        final Point point = new Point(i3, i2);
        ((UIManagerModule) reactApplicationContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i);
                if (airMapView == null) {
                    promise.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise.reject("AirMapView.map is not valid");
                } else {
                    LatLng fromScreenLocation = airMapView.map.getProjection().fromScreenLocation(point);
                    WritableNativeMap writableNativeMap = new WritableNativeMap();
                    writableNativeMap.putDouble("latitude", fromScreenLocation.latitude);
                    writableNativeMap.putDouble("longitude", fromScreenLocation.longitude);
                    promise.resolve(writableNativeMap);
                }
            }
        });
    }

    @ReactMethod
    public void getMapBoundaries(final int i, final Promise promise) {
        ((UIManagerModule) getReactApplicationContext().getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i);
                if (airMapView == null) {
                    promise.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise.reject("AirMapView.map is not valid");
                } else {
                    double[][] mapBoundaries = airMapView.getMapBoundaries();
                    WritableNativeMap writableNativeMap = new WritableNativeMap();
                    WritableNativeMap writableNativeMap2 = new WritableNativeMap();
                    WritableNativeMap writableNativeMap3 = new WritableNativeMap();
                    writableNativeMap2.putDouble("longitude", mapBoundaries[0][0]);
                    writableNativeMap2.putDouble("latitude", mapBoundaries[0][1]);
                    writableNativeMap3.putDouble("longitude", mapBoundaries[1][0]);
                    writableNativeMap3.putDouble("latitude", mapBoundaries[1][1]);
                    writableNativeMap.putMap("northEast", writableNativeMap2);
                    writableNativeMap.putMap("southWest", writableNativeMap3);
                    promise.resolve(writableNativeMap);
                }
            }
        });
    }
}
