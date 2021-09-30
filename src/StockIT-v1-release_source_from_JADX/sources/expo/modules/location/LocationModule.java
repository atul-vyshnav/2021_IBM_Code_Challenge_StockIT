package expo.modules.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.amplitude.api.DeviceInfo;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import expo.modules.location.LocationModule;
import expo.modules.location.exceptions.LocationBackgroundUnauthorizedException;
import expo.modules.location.exceptions.LocationRequestRejectedException;
import expo.modules.location.exceptions.LocationSettingsUnsatisfiedException;
import expo.modules.location.exceptions.LocationUnauthorizedException;
import expo.modules.location.exceptions.LocationUnavailableException;
import expo.modules.location.taskConsumers.GeofencingTaskConsumer;
import expo.modules.location.taskConsumers.LocationTaskConsumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ActivityEventListener;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.core.interfaces.services.EventEmitter;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.permissions.Permissions;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;
import org.unimodules.interfaces.permissions.PermissionsStatus;
import org.unimodules.interfaces.taskManager.TaskManagerInterface;
import p022io.nlopez.smartlocation.OnGeocodingListener;
import p022io.nlopez.smartlocation.OnLocationUpdatedListener;
import p022io.nlopez.smartlocation.OnReverseGeocodingListener;
import p022io.nlopez.smartlocation.SmartLocation;
import p022io.nlopez.smartlocation.geocoding.utils.LocationAddress;
import p022io.nlopez.smartlocation.location.config.LocationParams;
import p022io.nlopez.smartlocation.location.utils.LocationState;

public class LocationModule extends ExportedModule implements LifecycleEventListener, SensorEventListener, ActivityEventListener {
    public static final int ACCURACY_BALANCED = 3;
    public static final int ACCURACY_BEST_FOR_NAVIGATION = 6;
    public static final int ACCURACY_HIGH = 4;
    public static final int ACCURACY_HIGHEST = 5;
    public static final int ACCURACY_LOW = 2;
    public static final int ACCURACY_LOWEST = 1;
    private static final int CHECK_SETTINGS_REQUEST_CODE = 42;
    private static final double DEGREE_DELTA = 0.0355d;
    public static final int GEOFENCING_EVENT_ENTER = 1;
    public static final int GEOFENCING_EVENT_EXIT = 2;
    public static final int GEOFENCING_REGION_STATE_INSIDE = 1;
    public static final int GEOFENCING_REGION_STATE_OUTSIDE = 2;
    public static final int GEOFENCING_REGION_STATE_UNKNOWN = 0;
    private static final String HEADING_EVENT_NAME = "Expo.headingChanged";
    private static final String LOCATION_EVENT_NAME = "Expo.locationChanged";
    private static final String SHOW_USER_SETTINGS_DIALOG_KEY = "mayShowUserSettingsDialog";
    private static final String TAG = LocationModule.class.getSimpleName();
    private static final float TIME_DELTA = 50.0f;
    private int mAccuracy = 0;
    private ActivityProvider mActivityProvider;
    private Context mContext;
    private EventEmitter mEventEmitter;
    private boolean mGeocoderPaused = false;
    private GeomagneticField mGeofield;
    private float[] mGeomagnetic;
    private float[] mGravity;
    private int mHeadingId;
    private float mLastAzimut = 0.0f;
    private long mLastUpdate = 0;
    private Map<Integer, LocationCallback> mLocationCallbacks = new HashMap();
    private FusedLocationProviderClient mLocationProvider;
    private Map<Integer, LocationRequest> mLocationRequests = new HashMap();
    private List<LocationActivityResultListener> mPendingLocationRequests = new ArrayList();
    private Permissions mPermissionsManager;
    private SensorManager mSensorManager;
    private TaskManagerInterface mTaskManager;
    private UIManager mUIManager;

    public interface OnResultListener {
        void onResult(Location location);
    }

    public String getName() {
        return "ExpoLocation";
    }

    public void onNewIntent(Intent intent) {
    }

    public LocationModule(Context context) {
        super(context);
        this.mContext = context;
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        UIManager uIManager = this.mUIManager;
        if (uIManager != null) {
            uIManager.unregisterLifecycleEventListener(this);
        }
        this.mEventEmitter = (EventEmitter) moduleRegistry.getModule(EventEmitter.class);
        this.mUIManager = (UIManager) moduleRegistry.getModule(UIManager.class);
        this.mPermissionsManager = (Permissions) moduleRegistry.getModule(Permissions.class);
        this.mTaskManager = (TaskManagerInterface) moduleRegistry.getModule(TaskManagerInterface.class);
        this.mActivityProvider = (ActivityProvider) moduleRegistry.getModule(ActivityProvider.class);
        UIManager uIManager2 = this.mUIManager;
        if (uIManager2 != null) {
            uIManager2.registerLifecycleEventListener(this);
        }
    }

    @Deprecated
    @ExpoMethod
    public void requestPermissionsAsync(Promise promise) {
        if (this.mPermissionsManager == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else if (Build.VERSION.SDK_INT == 29) {
            this.mPermissionsManager.askForPermissions(new PermissionsResponseListener(promise) {
                public final /* synthetic */ Promise f$1;

                {
                    this.f$1 = r2;
                }

                public final void onResult(Map map) {
                    LocationModule.this.lambda$requestPermissionsAsync$0$LocationModule(this.f$1, map);
                }
            }, "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_BACKGROUND_LOCATION");
        } else {
            requestForegroundPermissionsAsync(promise);
        }
    }

    public /* synthetic */ void lambda$requestPermissionsAsync$0$LocationModule(Promise promise, Map map) {
        promise.resolve(handleLegacyPermissions(map));
    }

    @Deprecated
    @ExpoMethod
    public void getPermissionsAsync(Promise promise) {
        if (this.mPermissionsManager == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else if (Build.VERSION.SDK_INT == 29) {
            this.mPermissionsManager.getPermissions(new PermissionsResponseListener(promise) {
                public final /* synthetic */ Promise f$1;

                {
                    this.f$1 = r2;
                }

                public final void onResult(Map map) {
                    LocationModule.this.lambda$getPermissionsAsync$1$LocationModule(this.f$1, map);
                }
            }, "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_BACKGROUND_LOCATION");
        } else {
            getForegroundPermissionsAsync(promise);
        }
    }

    public /* synthetic */ void lambda$getPermissionsAsync$1$LocationModule(Promise promise, Map map) {
        promise.resolve(handleLegacyPermissions(map));
    }

    @ExpoMethod
    public void requestForegroundPermissionsAsync(Promise promise) {
        Permissions permissions = this.mPermissionsManager;
        if (permissions == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else {
            permissions.askForPermissions(new PermissionsResponseListener(promise) {
                public final /* synthetic */ Promise f$1;

                {
                    this.f$1 = r2;
                }

                public final void onResult(Map map) {
                    LocationModule.this.lambda$requestForegroundPermissionsAsync$2$LocationModule(this.f$1, map);
                }
            }, "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION");
        }
    }

    public /* synthetic */ void lambda$requestForegroundPermissionsAsync$2$LocationModule(Promise promise, Map map) {
        promise.resolve(handleForegroundLocationPermissions(map));
    }

    @ExpoMethod
    public void requestBackgroundPermissionsAsync(Promise promise) {
        if (this.mPermissionsManager == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else if (!isBackgroundPermissionInManifest()) {
            promise.reject("ERR_NO_PERMISSIONS", "You need to add `ACCESS_BACKGROUND_LOCATION` to the AndroidManifest.");
        } else if (!shouldAskBackgroundPermissions()) {
            requestForegroundPermissionsAsync(promise);
        } else {
            this.mPermissionsManager.askForPermissions(new PermissionsResponseListener(promise) {
                public final /* synthetic */ Promise f$1;

                {
                    this.f$1 = r2;
                }

                public final void onResult(Map map) {
                    LocationModule.this.lambda$requestBackgroundPermissionsAsync$3$LocationModule(this.f$1, map);
                }
            }, "android.permission.ACCESS_BACKGROUND_LOCATION");
        }
    }

    public /* synthetic */ void lambda$requestBackgroundPermissionsAsync$3$LocationModule(Promise promise, Map map) {
        promise.resolve(handleBackgroundLocationPermissions(map));
    }

    @ExpoMethod
    public void getForegroundPermissionsAsync(Promise promise) {
        Permissions permissions = this.mPermissionsManager;
        if (permissions == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else {
            permissions.getPermissions(new PermissionsResponseListener(promise) {
                public final /* synthetic */ Promise f$1;

                {
                    this.f$1 = r2;
                }

                public final void onResult(Map map) {
                    LocationModule.this.lambda$getForegroundPermissionsAsync$4$LocationModule(this.f$1, map);
                }
            }, "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION");
        }
    }

    public /* synthetic */ void lambda$getForegroundPermissionsAsync$4$LocationModule(Promise promise, Map map) {
        promise.resolve(handleForegroundLocationPermissions(map));
    }

    @ExpoMethod
    public void getBackgroundPermissionsAsync(Promise promise) {
        if (this.mPermissionsManager == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else if (!isBackgroundPermissionInManifest()) {
            promise.reject("ERR_NO_PERMISSIONS", "You need to add `ACCESS_BACKGROUND_LOCATION` to the AndroidManifest.");
        } else if (!shouldAskBackgroundPermissions()) {
            getForegroundPermissionsAsync(promise);
        } else {
            this.mPermissionsManager.getPermissions(new PermissionsResponseListener(promise) {
                public final /* synthetic */ Promise f$1;

                {
                    this.f$1 = r2;
                }

                public final void onResult(Map map) {
                    LocationModule.this.lambda$getBackgroundPermissionsAsync$5$LocationModule(this.f$1, map);
                }
            }, "android.permission.ACCESS_BACKGROUND_LOCATION");
        }
    }

    public /* synthetic */ void lambda$getBackgroundPermissionsAsync$5$LocationModule(Promise promise, Map map) {
        promise.resolve(handleBackgroundLocationPermissions(map));
    }

    @ExpoMethod
    public void getLastKnownPositionAsync(Map<String, Object> map, Promise promise) {
        if (isMissingForegroundPermissions()) {
            promise.reject(new LocationUnauthorizedException());
        } else {
            getLastKnownLocation(new OnResultListener(map, promise) {
                public final /* synthetic */ Map f$0;
                public final /* synthetic */ Promise f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void onResult(Location location) {
                    LocationModule.lambda$getLastKnownPositionAsync$6(this.f$0, this.f$1, location);
                }
            });
        }
    }

    static /* synthetic */ void lambda$getLastKnownPositionAsync$6(Map map, Promise promise, Location location) {
        if (LocationHelpers.isLocationValid(location, map)) {
            promise.resolve(LocationHelpers.locationToBundle(location, Bundle.class));
        } else {
            promise.resolve((Object) null);
        }
    }

    @ExpoMethod
    public void getCurrentPositionAsync(Map<String, Object> map, Promise promise) {
        LocationRequest prepareLocationRequest = LocationHelpers.prepareLocationRequest(map);
        boolean z = !map.containsKey(SHOW_USER_SETTINGS_DIALOG_KEY) || ((Boolean) map.get(SHOW_USER_SETTINGS_DIALOG_KEY)).booleanValue();
        if (isMissingForegroundPermissions()) {
            promise.reject(new LocationUnauthorizedException());
        } else if (LocationHelpers.hasNetworkProviderEnabled(this.mContext) || !z) {
            LocationHelpers.requestSingleLocation(this, prepareLocationRequest, promise);
        } else {
            addPendingLocationRequest(prepareLocationRequest, new LocationActivityResultListener(prepareLocationRequest, promise) {
                public final /* synthetic */ LocationRequest f$1;
                public final /* synthetic */ Promise f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void onResult(int i) {
                    LocationModule.this.lambda$getCurrentPositionAsync$7$LocationModule(this.f$1, this.f$2, i);
                }
            });
        }
    }

    public /* synthetic */ void lambda$getCurrentPositionAsync$7$LocationModule(LocationRequest locationRequest, Promise promise, int i) {
        if (i == -1) {
            LocationHelpers.requestSingleLocation(this, locationRequest, promise);
        } else {
            promise.reject(new LocationSettingsUnsatisfiedException());
        }
    }

    @ExpoMethod
    public void getProviderStatusAsync(Promise promise) {
        Context context = this.mContext;
        if (context == null) {
            promise.reject("E_CONTEXT_UNAVAILABLE", "Context is not available");
            return;
        }
        LocationState state = SmartLocation.with(context).location().state();
        Bundle bundle = new Bundle();
        bundle.putBoolean("locationServicesEnabled", state.locationServicesEnabled());
        bundle.putBoolean("gpsAvailable", state.isGpsAvailable());
        bundle.putBoolean("networkAvailable", state.isNetworkAvailable());
        bundle.putBoolean("passiveAvailable", state.isPassiveAvailable());
        bundle.putBoolean("backgroundModeEnabled", state.locationServicesEnabled());
        promise.resolve(bundle);
    }

    @ExpoMethod
    public void watchDeviceHeading(int i, Promise promise) {
        this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        this.mHeadingId = i;
        startHeadingUpdate();
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void watchPositionImplAsync(int i, Map<String, Object> map, Promise promise) {
        if (isMissingForegroundPermissions()) {
            promise.reject(new LocationUnauthorizedException());
            return;
        }
        LocationRequest prepareLocationRequest = LocationHelpers.prepareLocationRequest(map);
        boolean z = !map.containsKey(SHOW_USER_SETTINGS_DIALOG_KEY) || ((Boolean) map.get(SHOW_USER_SETTINGS_DIALOG_KEY)).booleanValue();
        if (LocationHelpers.hasNetworkProviderEnabled(this.mContext) || !z) {
            LocationHelpers.requestContinuousUpdates(this, prepareLocationRequest, i, promise);
        } else {
            addPendingLocationRequest(prepareLocationRequest, new LocationActivityResultListener(prepareLocationRequest, i, promise) {
                public final /* synthetic */ LocationRequest f$1;
                public final /* synthetic */ int f$2;
                public final /* synthetic */ Promise f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void onResult(int i) {
                    LocationModule.this.lambda$watchPositionImplAsync$8$LocationModule(this.f$1, this.f$2, this.f$3, i);
                }
            });
        }
    }

    public /* synthetic */ void lambda$watchPositionImplAsync$8$LocationModule(LocationRequest locationRequest, int i, Promise promise, int i2) {
        if (i2 == -1) {
            LocationHelpers.requestContinuousUpdates(this, locationRequest, i, promise);
        } else {
            promise.reject(new LocationSettingsUnsatisfiedException());
        }
    }

    @ExpoMethod
    public void removeWatchAsync(int i, Promise promise) {
        if (isMissingForegroundPermissions()) {
            promise.reject(new LocationUnauthorizedException());
            return;
        }
        if (i == this.mHeadingId) {
            destroyHeadingWatch();
        } else {
            removeLocationUpdatesForRequest(Integer.valueOf(i));
        }
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void geocodeAsync(String str, Promise promise) {
        if (this.mGeocoderPaused) {
            promise.reject("E_CANNOT_GEOCODE", "Geocoder is not running.");
        } else if (isMissingForegroundPermissions()) {
            promise.reject(new LocationUnauthorizedException());
        } else if (Geocoder.isPresent()) {
            SmartLocation.with(this.mContext).geocoding().direct(str, new OnGeocodingListener(promise) {
                public final /* synthetic */ Promise f$1;

                {
                    this.f$1 = r2;
                }

                public final void onLocationResolved(String str, List list) {
                    LocationModule.this.lambda$geocodeAsync$9$LocationModule(this.f$1, str, list);
                }
            });
        } else {
            promise.reject("E_NO_GEOCODER", "Geocoder service is not available for this device.");
        }
    }

    public /* synthetic */ void lambda$geocodeAsync$9$LocationModule(Promise promise, String str, List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Bundle bundle = (Bundle) LocationHelpers.locationToCoordsBundle(((LocationAddress) it.next()).getLocation(), Bundle.class);
            if (bundle != null) {
                arrayList.add(bundle);
            }
        }
        SmartLocation.with(this.mContext).geocoding().stop();
        promise.resolve(arrayList);
    }

    @ExpoMethod
    public void reverseGeocodeAsync(Map<String, Object> map, Promise promise) {
        if (this.mGeocoderPaused) {
            promise.reject("E_CANNOT_GEOCODE", "Geocoder is not running.");
        } else if (isMissingForegroundPermissions()) {
            promise.reject(new LocationUnauthorizedException());
        } else {
            Location location = new Location("");
            location.setLatitude(((Double) map.get("latitude")).doubleValue());
            location.setLongitude(((Double) map.get("longitude")).doubleValue());
            if (Geocoder.isPresent()) {
                SmartLocation.with(this.mContext).geocoding().reverse(location, new OnReverseGeocodingListener(promise) {
                    public final /* synthetic */ Promise f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void onAddressResolved(Location location, List list) {
                        LocationModule.this.lambda$reverseGeocodeAsync$10$LocationModule(this.f$1, location, list);
                    }
                });
            } else {
                promise.reject("E_NO_GEOCODER", "Geocoder service is not available for this device.");
            }
        }
    }

    public /* synthetic */ void lambda$reverseGeocodeAsync$10$LocationModule(Promise promise, Location location, List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(LocationHelpers.addressToBundle((Address) it.next()));
        }
        SmartLocation.with(this.mContext).geocoding().stop();
        promise.resolve(arrayList);
    }

    @ExpoMethod
    public void enableNetworkProviderAsync(Promise promise) {
        if (LocationHelpers.hasNetworkProviderEnabled(this.mContext)) {
            promise.resolve((Object) null);
        } else {
            addPendingLocationRequest(LocationHelpers.prepareLocationRequest(new HashMap()), new LocationActivityResultListener() {
                public final void onResult(int i) {
                    LocationModule.lambda$enableNetworkProviderAsync$11(Promise.this, i);
                }
            });
        }
    }

    static /* synthetic */ void lambda$enableNetworkProviderAsync$11(Promise promise, int i) {
        if (i == -1) {
            promise.resolve((Object) null);
        } else {
            promise.reject(new LocationSettingsUnsatisfiedException());
        }
    }

    @ExpoMethod
    public void hasServicesEnabledAsync(Promise promise) {
        promise.resolve(Boolean.valueOf(LocationHelpers.isAnyProviderAvailable(getContext())));
    }

    @ExpoMethod
    public void startLocationUpdatesAsync(String str, Map<String, Object> map, Promise promise) {
        if (LocationTaskConsumer.shouldUseForegroundService(map) || !isMissingBackgroundPermissions()) {
            try {
                this.mTaskManager.registerTask(str, LocationTaskConsumer.class, map);
                promise.resolve((Object) null);
            } catch (Exception e) {
                promise.reject(e);
            }
        } else {
            promise.reject(new LocationBackgroundUnauthorizedException());
        }
    }

    @ExpoMethod
    public void stopLocationUpdatesAsync(String str, Promise promise) {
        try {
            this.mTaskManager.unregisterTask(str, LocationTaskConsumer.class);
            promise.resolve((Object) null);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void hasStartedLocationUpdatesAsync(String str, Promise promise) {
        promise.resolve(Boolean.valueOf(this.mTaskManager.taskHasConsumerOfClass(str, LocationTaskConsumer.class)));
    }

    @ExpoMethod
    public void startGeofencingAsync(String str, Map<String, Object> map, Promise promise) {
        if (isMissingBackgroundPermissions()) {
            promise.reject(new LocationBackgroundUnauthorizedException());
            return;
        }
        try {
            this.mTaskManager.registerTask(str, GeofencingTaskConsumer.class, map);
            promise.resolve((Object) null);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void stopGeofencingAsync(String str, Promise promise) {
        if (isMissingBackgroundPermissions()) {
            promise.reject(new LocationBackgroundUnauthorizedException());
            return;
        }
        try {
            this.mTaskManager.unregisterTask(str, GeofencingTaskConsumer.class);
            promise.resolve((Object) null);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void hasStartedGeofencingAsync(String str, Promise promise) {
        if (isMissingBackgroundPermissions()) {
            promise.reject(new LocationBackgroundUnauthorizedException());
        } else {
            promise.resolve(Boolean.valueOf(this.mTaskManager.taskHasConsumerOfClass(str, GeofencingTaskConsumer.class)));
        }
    }

    /* access modifiers changed from: package-private */
    public void requestLocationUpdates(LocationRequest locationRequest, Integer num, final LocationRequestCallbacks locationRequestCallbacks) {
        FusedLocationProviderClient locationProvider = getLocationProvider();
        C30251 r1 = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                Location lastLocation = locationResult != null ? locationResult.getLastLocation() : null;
                if (lastLocation != null) {
                    locationRequestCallbacks.onLocationChanged(lastLocation);
                }
            }

            public void onLocationAvailability(LocationAvailability locationAvailability) {
                if (!locationAvailability.isLocationAvailable()) {
                    locationRequestCallbacks.onLocationError(new LocationUnavailableException());
                }
            }
        };
        if (num != null) {
            this.mLocationCallbacks.put(num, r1);
            this.mLocationRequests.put(num, locationRequest);
        }
        try {
            locationProvider.requestLocationUpdates(locationRequest, r1, Looper.myLooper());
            locationRequestCallbacks.onRequestSuccess();
        } catch (SecurityException e) {
            locationRequestCallbacks.onRequestFailed(new LocationRequestRejectedException(e));
        }
    }

    private boolean isMissingForegroundPermissions() {
        Permissions permissions = this.mPermissionsManager;
        return permissions == null || !permissions.hasGrantedPermissions("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION");
    }

    private boolean isMissingBackgroundPermissions() {
        return this.mPermissionsManager == null || (Build.VERSION.SDK_INT >= 29 && !this.mPermissionsManager.hasGrantedPermissions("android.permission.ACCESS_BACKGROUND_LOCATION"));
    }

    private FusedLocationProviderClient getLocationProvider() {
        if (this.mLocationProvider == null) {
            this.mLocationProvider = LocationServices.getFusedLocationProviderClient(this.mContext);
        }
        return this.mLocationProvider;
    }

    private void getLastKnownLocation(OnResultListener onResultListener) {
        try {
            getLocationProvider().getLastLocation().addOnSuccessListener(new OnSuccessListener() {
                public final void onSuccess(Object obj) {
                    LocationModule.OnResultListener.this.onResult((Location) obj);
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                public final void onCanceled() {
                    LocationModule.OnResultListener.this.onResult((Location) null);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public final void onFailure(Exception exc) {
                    LocationModule.OnResultListener.this.onResult((Location) null);
                }
            });
        } catch (SecurityException unused) {
            onResultListener.onResult((Location) null);
        }
    }

    private void addPendingLocationRequest(LocationRequest locationRequest, LocationActivityResultListener locationActivityResultListener) {
        this.mPendingLocationRequests.add(locationActivityResultListener);
        if (this.mPendingLocationRequests.size() == 1) {
            resolveUserSettingsForRequest(locationRequest);
        }
    }

    private void resolveUserSettingsForRequest(LocationRequest locationRequest) {
        Activity currentActivity = this.mActivityProvider.getCurrentActivity();
        if (currentActivity == null) {
            executePendingRequests(0);
            return;
        }
        Task<LocationSettingsResponse> checkLocationSettings = LocationServices.getSettingsClient(this.mContext).checkLocationSettings(new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build());
        checkLocationSettings.addOnSuccessListener(new OnSuccessListener() {
            public final void onSuccess(Object obj) {
                LocationModule.this.lambda$resolveUserSettingsForRequest$15$LocationModule((LocationSettingsResponse) obj);
            }
        });
        checkLocationSettings.addOnFailureListener(new OnFailureListener(currentActivity) {
            public final /* synthetic */ Activity f$1;

            {
                this.f$1 = r2;
            }

            public final void onFailure(Exception exc) {
                LocationModule.this.lambda$resolveUserSettingsForRequest$16$LocationModule(this.f$1, exc);
            }
        });
    }

    public /* synthetic */ void lambda$resolveUserSettingsForRequest$15$LocationModule(LocationSettingsResponse locationSettingsResponse) {
        executePendingRequests(-1);
    }

    public /* synthetic */ void lambda$resolveUserSettingsForRequest$16$LocationModule(Activity activity, Exception exc) {
        if (((ApiException) exc).getStatusCode() == 6) {
            try {
                this.mUIManager.registerActivityEventListener(this);
                ((ResolvableApiException) exc).startResolutionForResult(activity, 42);
            } catch (IntentSender.SendIntentException unused) {
                executePendingRequests(0);
            }
        } else {
            executePendingRequests(0);
        }
    }

    private void pauseLocationUpdatesForRequest(Integer num) {
        LocationCallback locationCallback = this.mLocationCallbacks.get(num);
        if (locationCallback != null) {
            getLocationProvider().removeLocationUpdates(locationCallback);
        }
    }

    private void resumeLocationUpdates() {
        FusedLocationProviderClient locationProvider = getLocationProvider();
        for (Integer next : this.mLocationCallbacks.keySet()) {
            LocationCallback locationCallback = this.mLocationCallbacks.get(next);
            LocationRequest locationRequest = this.mLocationRequests.get(next);
            if (!(locationCallback == null || locationRequest == null)) {
                try {
                    locationProvider.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                } catch (SecurityException e) {
                    String str = TAG;
                    Log.e(str, "Error occurred while resuming location updates: " + e.toString());
                }
            }
        }
    }

    private void removeLocationUpdatesForRequest(Integer num) {
        pauseLocationUpdatesForRequest(num);
        this.mLocationCallbacks.remove(num);
        this.mLocationRequests.remove(num);
    }

    /* access modifiers changed from: package-private */
    public void sendLocationResponse(int i, Bundle bundle) {
        bundle.putInt("watchId", i);
        this.mEventEmitter.emit(LOCATION_EVENT_NAME, bundle);
    }

    private void executePendingRequests(int i) {
        for (LocationActivityResultListener onResult : this.mPendingLocationRequests) {
            onResult.onResult(i);
        }
        this.mPendingLocationRequests.clear();
    }

    private void startHeadingUpdate() {
        Context context;
        if (this.mSensorManager != null && (context = this.mContext) != null) {
            SmartLocation.LocationControl config = SmartLocation.with(context).location().oneFix().config(LocationParams.BEST_EFFORT);
            Location lastLocation = config.getLastLocation();
            if (lastLocation != null) {
                this.mGeofield = new GeomagneticField((float) lastLocation.getLatitude(), (float) lastLocation.getLongitude(), (float) lastLocation.getAltitude(), System.currentTimeMillis());
            } else {
                config.start(new OnLocationUpdatedListener() {
                    public final void onLocationUpdated(Location location) {
                        LocationModule.this.lambda$startHeadingUpdate$17$LocationModule(location);
                    }
                });
            }
            SensorManager sensorManager = this.mSensorManager;
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(2), 3);
            SensorManager sensorManager2 = this.mSensorManager;
            sensorManager2.registerListener(this, sensorManager2.getDefaultSensor(1), 3);
        }
    }

    public /* synthetic */ void lambda$startHeadingUpdate$17$LocationModule(Location location) {
        this.mGeofield = new GeomagneticField((float) location.getLatitude(), (float) location.getLongitude(), (float) location.getAltitude(), System.currentTimeMillis());
    }

    private void sendUpdate() {
        float[] fArr = new float[9];
        if (SensorManager.getRotationMatrix(fArr, new float[9], this.mGravity, this.mGeomagnetic)) {
            float[] fArr2 = new float[3];
            SensorManager.getOrientation(fArr, fArr2);
            if (((double) Math.abs(fArr2[0] - this.mLastAzimut)) > DEGREE_DELTA && ((float) (System.currentTimeMillis() - this.mLastUpdate)) > TIME_DELTA) {
                this.mLastAzimut = fArr2[0];
                this.mLastUpdate = System.currentTimeMillis();
                float calcMagNorth = calcMagNorth(fArr2[0]);
                float calcTrueNorth = calcTrueNorth(calcMagNorth);
                Bundle bundle = new Bundle();
                Bundle headingToBundle = LocationHelpers.headingToBundle((double) calcTrueNorth, (double) calcMagNorth, this.mAccuracy);
                bundle.putInt("watchId", this.mHeadingId);
                bundle.putBundle("heading", headingToBundle);
                this.mEventEmitter.emit(HEADING_EVENT_NAME, bundle);
            }
        }
    }

    private float calcMagNorth(float f) {
        return (((float) Math.toDegrees((double) f)) + 360.0f) % 360.0f;
    }

    private float calcTrueNorth(float f) {
        GeomagneticField geomagneticField;
        if (isMissingForegroundPermissions() || (geomagneticField = this.mGeofield) == null) {
            return -1.0f;
        }
        return f + geomagneticField.getDeclination();
    }

    private void stopHeadingWatch() {
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void destroyHeadingWatch() {
        stopHeadingWatch();
        this.mSensorManager = null;
        this.mGravity = null;
        this.mGeomagnetic = null;
        this.mGeofield = null;
        this.mHeadingId = 0;
        this.mLastAzimut = 0.0f;
        this.mAccuracy = 0;
    }

    private void startWatching() {
        if (this.mContext != null) {
            if (!isMissingForegroundPermissions()) {
                this.mGeocoderPaused = false;
            }
            resumeLocationUpdates();
        }
    }

    private void stopWatching() {
        if (this.mContext != null) {
            if (Geocoder.isPresent() && !isMissingForegroundPermissions()) {
                SmartLocation.with(this.mContext).geocoding().stop();
                this.mGeocoderPaused = true;
            }
            for (Integer pauseLocationUpdatesForRequest : this.mLocationCallbacks.keySet()) {
                pauseLocationUpdatesForRequest(pauseLocationUpdatesForRequest);
            }
        }
    }

    private Bundle handleForegroundLocationPermissions(Map<String, PermissionsResponse> map) {
        PermissionsResponse permissionsResponse = map.get("android.permission.ACCESS_FINE_LOCATION");
        PermissionsResponse permissionsResponse2 = map.get("android.permission.ACCESS_COARSE_LOCATION");
        Objects.requireNonNull(permissionsResponse);
        Objects.requireNonNull(permissionsResponse2);
        PermissionsStatus permissionsStatus = PermissionsStatus.UNDETERMINED;
        boolean z = true;
        boolean z2 = permissionsResponse2.getCanAskAgain() && permissionsResponse.getCanAskAgain();
        String str = "none";
        if (permissionsResponse.getStatus() == PermissionsStatus.GRANTED) {
            permissionsStatus = PermissionsStatus.GRANTED;
            str = "fine";
        } else if (permissionsResponse2.getStatus() == PermissionsStatus.GRANTED) {
            permissionsStatus = PermissionsStatus.GRANTED;
            str = "coarse";
        } else if (permissionsResponse.getStatus() == PermissionsStatus.DENIED && permissionsResponse2.getStatus() == PermissionsStatus.DENIED) {
            permissionsStatus = PermissionsStatus.DENIED;
        }
        Bundle bundle = new Bundle();
        bundle.putString("status", permissionsStatus.getStatus());
        bundle.putString(PermissionsResponse.EXPIRES_KEY, "never");
        bundle.putBoolean(PermissionsResponse.CAN_ASK_AGAIN_KEY, z2);
        if (permissionsStatus != PermissionsStatus.GRANTED) {
            z = false;
        }
        bundle.putBoolean(PermissionsResponse.GRANTED_KEY, z);
        Bundle bundle2 = new Bundle();
        bundle2.putString("scoped", str);
        bundle2.putString("accuracy", str);
        bundle.putBundle(DeviceInfo.OS_NAME, bundle2);
        return bundle;
    }

    private Bundle handleBackgroundLocationPermissions(Map<String, PermissionsResponse> map) {
        PermissionsResponse permissionsResponse = map.get("android.permission.ACCESS_BACKGROUND_LOCATION");
        Objects.requireNonNull(permissionsResponse);
        PermissionsStatus status = permissionsResponse.getStatus();
        boolean canAskAgain = permissionsResponse.getCanAskAgain();
        Bundle bundle = new Bundle();
        bundle.putString("status", status.getStatus());
        bundle.putString(PermissionsResponse.EXPIRES_KEY, "never");
        bundle.putBoolean(PermissionsResponse.CAN_ASK_AGAIN_KEY, canAskAgain);
        bundle.putBoolean(PermissionsResponse.GRANTED_KEY, status == PermissionsStatus.GRANTED);
        return bundle;
    }

    private Bundle handleLegacyPermissions(Map<String, PermissionsResponse> map) {
        PermissionsResponse permissionsResponse = map.get("android.permission.ACCESS_FINE_LOCATION");
        PermissionsResponse permissionsResponse2 = map.get("android.permission.ACCESS_COARSE_LOCATION");
        Objects.requireNonNull(permissionsResponse);
        Objects.requireNonNull(permissionsResponse2);
        Objects.requireNonNull(map.get("android.permission.ACCESS_BACKGROUND_LOCATION"));
        PermissionsStatus permissionsStatus = PermissionsStatus.UNDETERMINED;
        boolean z = true;
        boolean z2 = permissionsResponse2.getCanAskAgain() && permissionsResponse.getCanAskAgain();
        String str = "none";
        if (permissionsResponse.getStatus() == PermissionsStatus.GRANTED) {
            permissionsStatus = PermissionsStatus.GRANTED;
            str = "fine";
        } else if (permissionsResponse2.getStatus() == PermissionsStatus.GRANTED) {
            permissionsStatus = PermissionsStatus.GRANTED;
            str = "coarse";
        } else if (permissionsResponse.getStatus() == PermissionsStatus.DENIED && permissionsResponse2.getStatus() == PermissionsStatus.DENIED) {
            permissionsStatus = PermissionsStatus.DENIED;
        }
        Bundle bundle = new Bundle();
        bundle.putString("status", permissionsStatus.getStatus());
        bundle.putString(PermissionsResponse.EXPIRES_KEY, "never");
        bundle.putBoolean(PermissionsResponse.CAN_ASK_AGAIN_KEY, z2);
        if (permissionsStatus != PermissionsStatus.GRANTED) {
            z = false;
        }
        bundle.putBoolean(PermissionsResponse.GRANTED_KEY, z);
        Bundle bundle2 = new Bundle();
        bundle2.putString("accuracy", str);
        bundle.putBundle(DeviceInfo.OS_NAME, bundle2);
        return bundle;
    }

    private boolean shouldAskBackgroundPermissions() {
        return Build.VERSION.SDK_INT >= 29;
    }

    private boolean isBackgroundPermissionInManifest() {
        if (Build.VERSION.SDK_INT >= 29) {
            return this.mPermissionsManager.isPermissionPresentInManifest("android.permission.ACCESS_BACKGROUND_LOCATION");
        }
        return true;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 1) {
            this.mGravity = sensorEvent.values;
        } else if (sensorEvent.sensor.getType() == 2) {
            this.mGeomagnetic = sensorEvent.values;
        }
        if (this.mGravity != null && this.mGeomagnetic != null) {
            sendUpdate();
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
        this.mAccuracy = i;
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        if (i == 42) {
            executePendingRequests(i2);
            this.mUIManager.unregisterActivityEventListener(this);
        }
    }

    public void onHostResume() {
        startWatching();
        startHeadingUpdate();
    }

    public void onHostPause() {
        stopWatching();
        stopHeadingWatch();
    }

    public void onHostDestroy() {
        stopWatching();
        stopHeadingWatch();
    }
}
