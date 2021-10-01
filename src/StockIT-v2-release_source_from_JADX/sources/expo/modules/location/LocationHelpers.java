package expo.modules.location;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.BaseBundle;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import com.amplitude.api.Constants;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.gms.location.LocationRequest;
import java.util.Map;
import org.unimodules.core.Promise;
import org.unimodules.core.errors.CodedException;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;
import p022io.nlopez.smartlocation.location.config.LocationAccuracy;
import p022io.nlopez.smartlocation.location.config.LocationParams;

public class LocationHelpers {
    private static final String TAG = LocationHelpers.class.getSimpleName();

    private static int mapAccuracyToPriority(int i) {
        if (i != 1) {
            return (i == 4 || i == 5 || i == 6) ? 100 : 102;
        }
        return 104;
    }

    public static boolean isAnyProviderAvailable(Context context) {
        LocationManager locationManager;
        if (context == null || (locationManager = (LocationManager) context.getSystemService(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID)) == null) {
            return false;
        }
        if (locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network")) {
            return true;
        }
        return false;
    }

    public static boolean hasNetworkProviderEnabled(Context context) {
        LocationManager locationManager;
        if (context == null || (locationManager = (LocationManager) context.getSystemService(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID)) == null || !locationManager.isProviderEnabled("network")) {
            return false;
        }
        return true;
    }

    public static <BundleType extends BaseBundle> BundleType locationToBundle(Location location, Class<BundleType> cls) {
        if (location == null) {
            return null;
        }
        try {
            BundleType bundletype = (BaseBundle) cls.newInstance();
            BaseBundle locationToCoordsBundle = locationToCoordsBundle(location, cls);
            if (locationToCoordsBundle == null) {
                return null;
            }
            if (bundletype instanceof PersistableBundle) {
                ((PersistableBundle) bundletype).putPersistableBundle("coords", (PersistableBundle) locationToCoordsBundle);
            } else if (bundletype instanceof Bundle) {
                ((Bundle) bundletype).putBundle("coords", (Bundle) locationToCoordsBundle);
                ((Bundle) bundletype).putBoolean("mocked", location.isFromMockProvider());
            }
            bundletype.putDouble("timestamp", (double) location.getTime());
            return bundletype;
        } catch (IllegalAccessException | InstantiationException e) {
            String str = TAG;
            Log.e(str, "Unexpected exception was thrown when converting location to the bundle: " + e.toString());
            return null;
        }
    }

    static <BundleType extends BaseBundle> BundleType locationToCoordsBundle(Location location, Class<BundleType> cls) {
        try {
            BundleType bundletype = (BaseBundle) cls.newInstance();
            bundletype.putDouble("latitude", location.getLatitude());
            bundletype.putDouble("longitude", location.getLongitude());
            bundletype.putDouble("altitude", location.getAltitude());
            bundletype.putDouble("accuracy", (double) location.getAccuracy());
            bundletype.putDouble("heading", (double) location.getBearing());
            bundletype.putDouble("speed", (double) location.getSpeed());
            if (Build.VERSION.SDK_INT >= 26) {
                bundletype.putDouble("altitudeAccuracy", (double) location.getVerticalAccuracyMeters());
            } else {
                bundletype.putString("altitudeAccuracy", (String) null);
            }
            return bundletype;
        } catch (IllegalAccessException | InstantiationException e) {
            String str = TAG;
            Log.e(str, "Unexpected exception was thrown when converting location to coords bundle: " + e.toString());
            return null;
        }
    }

    static Bundle addressToBundle(Address address) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.AMP_TRACKING_OPTION_CITY, address.getLocality());
        bundle.putString("district", address.getSubLocality());
        bundle.putString("street", address.getThoroughfare());
        bundle.putString("region", address.getAdminArea());
        bundle.putString("subregion", address.getSubAdminArea());
        bundle.putString(Constants.AMP_TRACKING_OPTION_COUNTRY, address.getCountryName());
        bundle.putString("postalCode", address.getPostalCode());
        bundle.putString("name", address.getFeatureName());
        bundle.putString("isoCountryCode", address.getCountryCode());
        bundle.putString("timezone", (String) null);
        return bundle;
    }

    static Bundle headingToBundle(double d, double d2, int i) {
        Bundle bundle = new Bundle();
        bundle.putDouble("trueHeading", d);
        bundle.putDouble("magHeading", d2);
        bundle.putInt("accuracy", i);
        return bundle;
    }

    public static LocationRequest prepareLocationRequest(Map<String, Object> map) {
        LocationParams mapOptionsToLocationParams = mapOptionsToLocationParams(map);
        return new LocationRequest().setFastestInterval(mapOptionsToLocationParams.getInterval()).setInterval(mapOptionsToLocationParams.getInterval()).setMaxWaitTime(mapOptionsToLocationParams.getInterval()).setSmallestDisplacement(mapOptionsToLocationParams.getDistance()).setPriority(mapAccuracyToPriority(getAccuracyFromOptions(map)));
    }

    public static LocationParams mapOptionsToLocationParams(Map<String, Object> map) {
        LocationParams.Builder buildLocationParamsForAccuracy = buildLocationParamsForAccuracy(getAccuracyFromOptions(map));
        if (map.containsKey("timeInterval")) {
            buildLocationParamsForAccuracy.setInterval(((Number) map.get("timeInterval")).longValue());
        }
        if (map.containsKey("distanceInterval")) {
            buildLocationParamsForAccuracy.setDistance(((Number) map.get("distanceInterval")).floatValue());
        }
        return buildLocationParamsForAccuracy.build();
    }

    static void requestSingleLocation(LocationModule locationModule, LocationRequest locationRequest, final Promise promise) {
        locationRequest.setNumUpdates(1);
        locationModule.requestLocationUpdates(locationRequest, (Integer) null, new LocationRequestCallbacks() {
            public void onLocationChanged(Location location) {
                promise.resolve(LocationHelpers.locationToBundle(location, Bundle.class));
            }

            public void onLocationError(CodedException codedException) {
                promise.reject(codedException);
            }

            public void onRequestFailed(CodedException codedException) {
                promise.reject(codedException);
            }
        });
    }

    static void requestContinuousUpdates(final LocationModule locationModule, LocationRequest locationRequest, final int i, final Promise promise) {
        locationModule.requestLocationUpdates(locationRequest, Integer.valueOf(i), new LocationRequestCallbacks() {
            public void onLocationChanged(Location location) {
                Bundle bundle = new Bundle();
                bundle.putBundle(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID, (Bundle) LocationHelpers.locationToBundle(location, Bundle.class));
                locationModule.sendLocationResponse(i, bundle);
            }

            public void onRequestSuccess() {
                promise.resolve((Object) null);
            }

            public void onRequestFailed(CodedException codedException) {
                promise.reject(codedException);
            }
        });
    }

    public static boolean isLocationValid(Location location, Map<String, Object> map) {
        if (location == null) {
            return false;
        }
        double d = Double.MAX_VALUE;
        double doubleValue = map.containsKey("maxAge") ? ((Double) map.get("maxAge")).doubleValue() : Double.MAX_VALUE;
        if (map.containsKey("requiredAccuracy")) {
            d = ((Double) map.get("requiredAccuracy")).doubleValue();
        }
        if (((double) (System.currentTimeMillis() - location.getTime())) > doubleValue || ((double) location.getAccuracy()) > d) {
            return false;
        }
        return true;
    }

    private static int getAccuracyFromOptions(Map<String, Object> map) {
        if (map.containsKey("accuracy")) {
            return ((Number) map.get("accuracy")).intValue();
        }
        return 3;
    }

    private static LocationParams.Builder buildLocationParamsForAccuracy(int i) {
        if (i == 1) {
            return new LocationParams.Builder().setAccuracy(LocationAccuracy.LOWEST).setDistance(3000.0f).setInterval(10000);
        }
        if (i == 2) {
            return new LocationParams.Builder().setAccuracy(LocationAccuracy.LOW).setDistance(1000.0f).setInterval(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        }
        if (i == 4) {
            return new LocationParams.Builder().setAccuracy(LocationAccuracy.HIGH).setDistance(50.0f).setInterval(AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
        }
        if (i == 5) {
            return new LocationParams.Builder().setAccuracy(LocationAccuracy.HIGH).setDistance(25.0f).setInterval(1000);
        }
        if (i != 6) {
            return new LocationParams.Builder().setAccuracy(LocationAccuracy.MEDIUM).setDistance(100.0f).setInterval(3000);
        }
        return new LocationParams.Builder().setAccuracy(LocationAccuracy.HIGH).setDistance(0.0f).setInterval(500);
    }
}
