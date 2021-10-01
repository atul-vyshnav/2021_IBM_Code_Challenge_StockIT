package p022io.nlopez.smartlocation.location.providers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import p022io.nlopez.smartlocation.OnLocationUpdatedListener;
import p022io.nlopez.smartlocation.location.LocationStore;
import p022io.nlopez.smartlocation.location.ServiceLocationProvider;
import p022io.nlopez.smartlocation.location.config.LocationParams;
import p022io.nlopez.smartlocation.utils.GooglePlayServicesListener;
import p022io.nlopez.smartlocation.utils.Logger;
import p022io.nlopez.smartlocation.utils.ServiceConnectionListener;

/* renamed from: io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider */
public class LocationGooglePlayServicesProvider implements ServiceLocationProvider, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<Status> {
    private static final String GMS_ID = "GMS";
    public static final int REQUEST_CHECK_SETTINGS = 20001;
    public static final int REQUEST_START_LOCATION_FIX = 10001;
    private boolean alwaysShow;
    private boolean checkLocationSettings;
    private GoogleApiClient client;
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public boolean fulfilledCheckLocationSettings;
    private GooglePlayServicesListener googlePlayServicesListener;
    private OnLocationUpdatedListener listener;
    /* access modifiers changed from: private */
    public LocationRequest locationRequest;
    private LocationStore locationStore;
    /* access modifiers changed from: private */
    public Logger logger;
    private ServiceConnectionListener serviceListener;
    private ResultCallback<LocationSettingsResult> settingsResultCallback;
    private boolean shouldStart;
    private boolean stopped;

    public LocationGooglePlayServicesProvider() {
        this.shouldStart = false;
        this.stopped = false;
        this.alwaysShow = true;
        this.settingsResultCallback = new ResultCallback<LocationSettingsResult>() {
            public void onResult(LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                int statusCode = status.getStatusCode();
                if (statusCode == 0) {
                    LocationGooglePlayServicesProvider.this.logger.mo40195d("All location settings are satisfied.", new Object[0]);
                    boolean unused = LocationGooglePlayServicesProvider.this.fulfilledCheckLocationSettings = true;
                    LocationGooglePlayServicesProvider locationGooglePlayServicesProvider = LocationGooglePlayServicesProvider.this;
                    locationGooglePlayServicesProvider.startUpdating(locationGooglePlayServicesProvider.locationRequest);
                } else if (statusCode == 6) {
                    LocationGooglePlayServicesProvider.this.logger.mo40203w("Location settings are not satisfied. Show the user a dialog toupgrade location settings. You should hook into the Activity onActivityResult and call this provider onActivityResult method for continuing this call flow. ", new Object[0]);
                    if (LocationGooglePlayServicesProvider.this.context instanceof Activity) {
                        try {
                            status.startResolutionForResult((Activity) LocationGooglePlayServicesProvider.this.context, LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException unused2) {
                            LocationGooglePlayServicesProvider.this.logger.mo40199i("PendingIntent unable to execute request.", new Object[0]);
                        }
                    } else {
                        LocationGooglePlayServicesProvider.this.logger.mo40203w("Provided context is not the context of an activity, therefore we cant launch the resolution activity.", new Object[0]);
                    }
                } else if (statusCode == 8502) {
                    LocationGooglePlayServicesProvider.this.logger.mo40199i("Location settings are inadequate, and cannot be fixed here. Dialog not created.", new Object[0]);
                    LocationGooglePlayServicesProvider.this.stop();
                }
            }
        };
        this.checkLocationSettings = false;
        this.fulfilledCheckLocationSettings = false;
    }

    public LocationGooglePlayServicesProvider(GooglePlayServicesListener googlePlayServicesListener2) {
        this();
        this.googlePlayServicesListener = googlePlayServicesListener2;
    }

    public LocationGooglePlayServicesProvider(ServiceConnectionListener serviceConnectionListener) {
        this();
        this.serviceListener = serviceConnectionListener;
    }

    public void init(Context context2, Logger logger2) {
        this.logger = logger2;
        this.context = context2;
        this.locationStore = new LocationStore(context2);
        if (!this.shouldStart) {
            GoogleApiClient build = new GoogleApiClient.Builder(context2).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            this.client = build;
            build.connect();
            return;
        }
        logger2.mo40195d("already started", new Object[0]);
    }

    private LocationRequest createRequest(LocationParams locationParams, boolean z) {
        LocationRequest smallestDisplacement = LocationRequest.create().setFastestInterval(locationParams.getInterval()).setInterval(locationParams.getInterval()).setSmallestDisplacement(locationParams.getDistance());
        int i = C31222.f1256x1a467bf9[locationParams.getAccuracy().ordinal()];
        if (i == 1) {
            smallestDisplacement.setPriority(100);
        } else if (i == 2) {
            smallestDisplacement.setPriority(102);
        } else if (i == 3) {
            smallestDisplacement.setPriority(104);
        } else if (i == 4) {
            smallestDisplacement.setPriority(105);
        }
        if (z) {
            smallestDisplacement.setNumUpdates(1);
        }
        return smallestDisplacement;
    }

    /* renamed from: io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider$2 */
    static /* synthetic */ class C31222 {

        /* renamed from: $SwitchMap$io$nlopez$smartlocation$location$config$LocationAccuracy */
        static final /* synthetic */ int[] f1256x1a467bf9;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        static {
            /*
                io.nlopez.smartlocation.location.config.LocationAccuracy[] r0 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1256x1a467bf9 = r0
                io.nlopez.smartlocation.location.config.LocationAccuracy r1 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.HIGH     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f1256x1a467bf9     // Catch:{ NoSuchFieldError -> 0x001d }
                io.nlopez.smartlocation.location.config.LocationAccuracy r1 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.MEDIUM     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f1256x1a467bf9     // Catch:{ NoSuchFieldError -> 0x0028 }
                io.nlopez.smartlocation.location.config.LocationAccuracy r1 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.LOW     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = f1256x1a467bf9     // Catch:{ NoSuchFieldError -> 0x0033 }
                io.nlopez.smartlocation.location.config.LocationAccuracy r1 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.LOWEST     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p022io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.C31222.<clinit>():void");
        }
    }

    public void start(OnLocationUpdatedListener onLocationUpdatedListener, LocationParams locationParams, boolean z) {
        this.listener = onLocationUpdatedListener;
        if (onLocationUpdatedListener == null) {
            this.logger.mo40195d("Listener is null, you sure about this?", new Object[0]);
        }
        this.locationRequest = createRequest(locationParams, z);
        if (this.client.isConnected()) {
            startUpdating(this.locationRequest);
        } else if (this.stopped) {
            this.shouldStart = true;
            this.client.connect();
            this.stopped = false;
        } else {
            this.shouldStart = true;
            this.logger.mo40195d("still not connected - scheduled start when connection is ok", new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void startUpdating(LocationRequest locationRequest2) {
        if (this.checkLocationSettings && !this.fulfilledCheckLocationSettings) {
            this.logger.mo40195d("startUpdating wont be executed for now, as we have to test the location settings before", new Object[0]);
            checkLocationSettings();
        } else if (!this.client.isConnected()) {
            this.logger.mo40203w("startUpdating executed without the GoogleApiClient being connected!!", new Object[0]);
        } else if (ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.client, locationRequest2, (LocationListener) this, Looper.getMainLooper()).setResultCallback(this);
        } else {
            this.logger.mo40199i("Permission check failed. Please handle it in your app before setting up location", new Object[0]);
        }
    }

    private void checkLocationSettings() {
        LocationServices.SettingsApi.checkLocationSettings(this.client, new LocationSettingsRequest.Builder().setAlwaysShow(this.alwaysShow).addLocationRequest(this.locationRequest).build()).setResultCallback(this.settingsResultCallback);
    }

    public void stop() {
        this.logger.mo40195d("stop", new Object[0]);
        if (this.client.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.client, (LocationListener) this);
            this.client.disconnect();
        }
        this.fulfilledCheckLocationSettings = false;
        this.shouldStart = false;
        this.stopped = true;
    }

    public Location getLastLocation() {
        GoogleApiClient googleApiClient = this.client;
        if (googleApiClient != null && googleApiClient.isConnected()) {
            if (ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                return null;
            }
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.client);
            if (lastLocation != null) {
                return lastLocation;
            }
        }
        LocationStore locationStore2 = this.locationStore;
        if (locationStore2 != null) {
            return locationStore2.get(GMS_ID);
        }
        return null;
    }

    public ServiceConnectionListener getServiceListener() {
        return this.serviceListener;
    }

    public void setServiceListener(ServiceConnectionListener serviceConnectionListener) {
        this.serviceListener = serviceConnectionListener;
    }

    public void onConnected(Bundle bundle) {
        this.logger.mo40195d("onConnected", new Object[0]);
        if (this.shouldStart) {
            startUpdating(this.locationRequest);
        }
        GooglePlayServicesListener googlePlayServicesListener2 = this.googlePlayServicesListener;
        if (googlePlayServicesListener2 != null) {
            googlePlayServicesListener2.onConnected(bundle);
        }
        ServiceConnectionListener serviceConnectionListener = this.serviceListener;
        if (serviceConnectionListener != null) {
            serviceConnectionListener.onConnected();
        }
    }

    public void onConnectionSuspended(int i) {
        Logger logger2 = this.logger;
        logger2.mo40195d("onConnectionSuspended " + i, new Object[0]);
        GooglePlayServicesListener googlePlayServicesListener2 = this.googlePlayServicesListener;
        if (googlePlayServicesListener2 != null) {
            googlePlayServicesListener2.onConnectionSuspended(i);
        }
        ServiceConnectionListener serviceConnectionListener = this.serviceListener;
        if (serviceConnectionListener != null) {
            serviceConnectionListener.onConnectionSuspended();
        }
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Logger logger2 = this.logger;
        logger2.mo40195d("onConnectionFailed " + connectionResult.toString(), new Object[0]);
        GooglePlayServicesListener googlePlayServicesListener2 = this.googlePlayServicesListener;
        if (googlePlayServicesListener2 != null) {
            googlePlayServicesListener2.onConnectionFailed(connectionResult);
        }
        ServiceConnectionListener serviceConnectionListener = this.serviceListener;
        if (serviceConnectionListener != null) {
            serviceConnectionListener.onConnectionFailed();
        }
    }

    public void onLocationChanged(Location location) {
        this.logger.mo40195d("onLocationChanged", location);
        OnLocationUpdatedListener onLocationUpdatedListener = this.listener;
        if (onLocationUpdatedListener != null) {
            onLocationUpdatedListener.onLocationUpdated(location);
        }
        if (this.locationStore != null) {
            this.logger.mo40195d("Stored in SharedPreferences", new Object[0]);
            this.locationStore.put(GMS_ID, location);
        }
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            this.logger.mo40195d("Locations update request successful", new Object[0]);
        } else if (!status.hasResolution() || !(this.context instanceof Activity)) {
            Logger logger2 = this.logger;
            logger2.mo40197e("Registering failed: " + status.getStatusMessage(), new Object[0]);
        } else {
            this.logger.mo40203w("Unable to register, but we can solve this - will startActivityForResult. You should hook into the Activity onActivityResult and call this provider onActivityResult method for continuing this call flow.", new Object[0]);
            try {
                status.startResolutionForResult((Activity) this.context, REQUEST_START_LOCATION_FIX);
            } catch (IntentSender.SendIntentException e) {
                this.logger.mo40198e(e, "problem with startResolutionForResult", new Object[0]);
            }
        }
    }

    public boolean isCheckingLocationSettings() {
        return this.checkLocationSettings;
    }

    public void setCheckLocationSettings(boolean z) {
        this.checkLocationSettings = z;
    }

    public void setLocationSettingsAlwaysShow(boolean z) {
        this.alwaysShow = z;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 20001) {
            if (i2 == -1) {
                this.logger.mo40199i("User agreed to make required location settings changes.", new Object[0]);
                this.fulfilledCheckLocationSettings = true;
                startUpdating(this.locationRequest);
            } else if (i2 == 0) {
                this.logger.mo40199i("User chose not to make required location settings changes.", new Object[0]);
                stop();
            }
        } else if (i != 10001) {
        } else {
            if (i2 == -1) {
                this.logger.mo40199i("User fixed the problem.", new Object[0]);
                startUpdating(this.locationRequest);
            } else if (i2 == 0) {
                this.logger.mo40199i("User chose not to fix the problem.", new Object[0]);
                stop();
            }
        }
    }
}
