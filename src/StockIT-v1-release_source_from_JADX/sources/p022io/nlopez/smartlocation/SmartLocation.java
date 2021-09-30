package p022io.nlopez.smartlocation;

import android.content.Context;
import android.location.Location;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import p022io.nlopez.smartlocation.activity.ActivityProvider;
import p022io.nlopez.smartlocation.activity.config.ActivityParams;
import p022io.nlopez.smartlocation.activity.providers.ActivityGooglePlayServicesProvider;
import p022io.nlopez.smartlocation.geocoding.GeocodingProvider;
import p022io.nlopez.smartlocation.geocoding.providers.AndroidGeocodingProvider;
import p022io.nlopez.smartlocation.geofencing.GeofencingProvider;
import p022io.nlopez.smartlocation.geofencing.model.GeofenceModel;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;
import p022io.nlopez.smartlocation.location.LocationProvider;
import p022io.nlopez.smartlocation.location.config.LocationParams;
import p022io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;
import p022io.nlopez.smartlocation.location.utils.LocationState;
import p022io.nlopez.smartlocation.utils.Logger;
import p022io.nlopez.smartlocation.utils.LoggerFactory;

/* renamed from: io.nlopez.smartlocation.SmartLocation */
public class SmartLocation {
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public Logger logger;
    /* access modifiers changed from: private */
    public boolean preInitialize;

    private SmartLocation(Context context2, Logger logger2, boolean z) {
        this.context = context2;
        this.logger = logger2;
        this.preInitialize = z;
    }

    public static SmartLocation with(Context context2) {
        return new Builder(context2).build();
    }

    public LocationControl location() {
        return location(new LocationGooglePlayServicesWithFallbackProvider(this.context));
    }

    public LocationControl location(LocationProvider locationProvider) {
        return new LocationControl(this, locationProvider);
    }

    @Deprecated
    public ActivityRecognitionControl activityRecognition() {
        return activity();
    }

    public ActivityRecognitionControl activity() {
        return activity(new ActivityGooglePlayServicesProvider());
    }

    public ActivityRecognitionControl activity(ActivityProvider activityProvider) {
        return new ActivityRecognitionControl(this, activityProvider);
    }

    public GeofencingControl geofencing() {
        return geofencing(new GeofencingGooglePlayServicesProvider());
    }

    public GeofencingControl geofencing(GeofencingProvider geofencingProvider) {
        return new GeofencingControl(this, geofencingProvider);
    }

    public GeocodingControl geocoding() {
        return geocoding(new AndroidGeocodingProvider());
    }

    public GeocodingControl geocoding(GeocodingProvider geocodingProvider) {
        return new GeocodingControl(this, geocodingProvider);
    }

    /* renamed from: io.nlopez.smartlocation.SmartLocation$Builder */
    public static class Builder {
        private final Context context;
        private boolean loggingEnabled = false;
        private boolean preInitialize = true;

        public Builder(Context context2) {
            this.context = context2;
        }

        public Builder logging(boolean z) {
            this.loggingEnabled = z;
            return this;
        }

        public Builder preInitialize(boolean z) {
            this.preInitialize = z;
            return this;
        }

        public SmartLocation build() {
            return new SmartLocation(this.context, LoggerFactory.buildLogger(this.loggingEnabled), this.preInitialize);
        }
    }

    /* renamed from: io.nlopez.smartlocation.SmartLocation$LocationControl */
    public static class LocationControl {
        private static final Map<Context, LocationProvider> MAPPING = new WeakHashMap();
        private boolean oneFix = false;
        private LocationParams params = LocationParams.BEST_EFFORT;
        private LocationProvider provider;
        private final SmartLocation smartLocation;

        public LocationControl get() {
            return this;
        }

        public LocationControl(SmartLocation smartLocation2, LocationProvider locationProvider) {
            this.smartLocation = smartLocation2;
            if (!MAPPING.containsKey(smartLocation2.context)) {
                MAPPING.put(smartLocation2.context, locationProvider);
            }
            this.provider = MAPPING.get(smartLocation2.context);
            if (smartLocation2.preInitialize) {
                this.provider.init(smartLocation2.context, smartLocation2.logger);
            }
        }

        public LocationControl config(LocationParams locationParams) {
            this.params = locationParams;
            return this;
        }

        public LocationControl oneFix() {
            this.oneFix = true;
            return this;
        }

        public LocationControl continuous() {
            this.oneFix = false;
            return this;
        }

        public LocationState state() {
            return LocationState.with(this.smartLocation.context);
        }

        public Location getLastLocation() {
            return this.provider.getLastLocation();
        }

        public void start(OnLocationUpdatedListener onLocationUpdatedListener) {
            LocationProvider locationProvider = this.provider;
            if (locationProvider != null) {
                locationProvider.start(onLocationUpdatedListener, this.params, this.oneFix);
                return;
            }
            throw new RuntimeException("A provider must be initialized");
        }

        public void stop() {
            this.provider.stop();
        }
    }

    /* renamed from: io.nlopez.smartlocation.SmartLocation$GeocodingControl */
    public static class GeocodingControl {
        private static final Map<Context, GeocodingProvider> MAPPING = new WeakHashMap();
        private boolean directAdded = false;
        private GeocodingProvider provider;
        private boolean reverseAdded = false;
        private final SmartLocation smartLocation;

        public GeocodingControl get() {
            return this;
        }

        public GeocodingControl(SmartLocation smartLocation2, GeocodingProvider geocodingProvider) {
            this.smartLocation = smartLocation2;
            if (!MAPPING.containsKey(smartLocation2.context)) {
                MAPPING.put(smartLocation2.context, geocodingProvider);
            }
            this.provider = MAPPING.get(smartLocation2.context);
            if (smartLocation2.preInitialize) {
                this.provider.init(smartLocation2.context, smartLocation2.logger);
            }
        }

        public void reverse(Location location, OnReverseGeocodingListener onReverseGeocodingListener) {
            add(location);
            start(onReverseGeocodingListener);
        }

        public void direct(String str, OnGeocodingListener onGeocodingListener) {
            add(str);
            start(onGeocodingListener);
        }

        public GeocodingControl add(Location location) {
            this.reverseAdded = true;
            this.provider.addLocation(location, 1);
            return this;
        }

        public GeocodingControl add(Location location, int i) {
            this.reverseAdded = true;
            this.provider.addLocation(location, i);
            return this;
        }

        public GeocodingControl add(String str) {
            this.directAdded = true;
            this.provider.addName(str, 1);
            return this;
        }

        public GeocodingControl add(String str, int i) {
            this.directAdded = true;
            this.provider.addName(str, i);
            return this;
        }

        public void start(OnGeocodingListener onGeocodingListener) {
            start(onGeocodingListener, (OnReverseGeocodingListener) null);
        }

        public void start(OnReverseGeocodingListener onReverseGeocodingListener) {
            start((OnGeocodingListener) null, onReverseGeocodingListener);
        }

        public void start(OnGeocodingListener onGeocodingListener, OnReverseGeocodingListener onReverseGeocodingListener) {
            if (this.provider != null) {
                if (this.directAdded && onGeocodingListener == null) {
                    this.smartLocation.logger.mo40203w("Some places were added for geocoding but the listener was not specified!", new Object[0]);
                }
                if (this.reverseAdded && onReverseGeocodingListener == null) {
                    this.smartLocation.logger.mo40203w("Some places were added for reverse geocoding but the listener was not specified!", new Object[0]);
                }
                this.provider.start(onGeocodingListener, onReverseGeocodingListener);
                return;
            }
            throw new RuntimeException("A provider must be initialized");
        }

        public void stop() {
            this.provider.stop();
        }
    }

    /* renamed from: io.nlopez.smartlocation.SmartLocation$ActivityRecognitionControl */
    public static class ActivityRecognitionControl {
        private static final Map<Context, ActivityProvider> MAPPING = new WeakHashMap();
        private ActivityParams params = ActivityParams.NORMAL;
        private ActivityProvider provider;
        private final SmartLocation smartLocation;

        public ActivityRecognitionControl get() {
            return this;
        }

        public ActivityRecognitionControl(SmartLocation smartLocation2, ActivityProvider activityProvider) {
            this.smartLocation = smartLocation2;
            if (!MAPPING.containsKey(smartLocation2.context)) {
                MAPPING.put(smartLocation2.context, activityProvider);
            }
            this.provider = MAPPING.get(smartLocation2.context);
            if (smartLocation2.preInitialize) {
                this.provider.init(smartLocation2.context, smartLocation2.logger);
            }
        }

        public ActivityRecognitionControl config(ActivityParams activityParams) {
            this.params = activityParams;
            return this;
        }

        public DetectedActivity getLastActivity() {
            return this.provider.getLastActivity();
        }

        public void start(OnActivityUpdatedListener onActivityUpdatedListener) {
            ActivityProvider activityProvider = this.provider;
            if (activityProvider != null) {
                activityProvider.start(onActivityUpdatedListener, this.params);
                return;
            }
            throw new RuntimeException("A provider must be initialized");
        }

        public void stop() {
            this.provider.stop();
        }
    }

    /* renamed from: io.nlopez.smartlocation.SmartLocation$GeofencingControl */
    public static class GeofencingControl {
        private static final Map<Context, GeofencingProvider> MAPPING = new WeakHashMap();
        private GeofencingProvider provider;
        private final SmartLocation smartLocation;

        public GeofencingControl(SmartLocation smartLocation2, GeofencingProvider geofencingProvider) {
            this.smartLocation = smartLocation2;
            if (!MAPPING.containsKey(smartLocation2.context)) {
                MAPPING.put(smartLocation2.context, geofencingProvider);
            }
            this.provider = MAPPING.get(smartLocation2.context);
            if (smartLocation2.preInitialize) {
                this.provider.init(smartLocation2.context, smartLocation2.logger);
            }
        }

        public GeofencingControl add(GeofenceModel geofenceModel) {
            this.provider.addGeofence(geofenceModel);
            return this;
        }

        public GeofencingControl remove(String str) {
            this.provider.removeGeofence(str);
            return this;
        }

        public GeofencingControl addAll(List<GeofenceModel> list) {
            this.provider.addGeofences(list);
            return this;
        }

        public GeofencingControl removeAll(List<String> list) {
            this.provider.removeGeofences(list);
            return this;
        }

        public void start(OnGeofencingTransitionListener onGeofencingTransitionListener) {
            GeofencingProvider geofencingProvider = this.provider;
            if (geofencingProvider != null) {
                geofencingProvider.start(onGeofencingTransitionListener);
                return;
            }
            throw new RuntimeException("A provider must be initialized");
        }

        public void stop() {
            this.provider.stop();
        }
    }
}
