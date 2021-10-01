package p022io.nlopez.smartlocation.location.providers;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import p022io.nlopez.smartlocation.OnLocationUpdatedListener;
import p022io.nlopez.smartlocation.location.LocationProvider;
import p022io.nlopez.smartlocation.location.config.LocationParams;
import p022io.nlopez.smartlocation.utils.GooglePlayServicesListener;
import p022io.nlopez.smartlocation.utils.Logger;

/* renamed from: io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider */
public class LocationGooglePlayServicesWithFallbackProvider implements LocationProvider, GooglePlayServicesListener {
    private Context context;
    private OnLocationUpdatedListener listener;
    private Logger logger;
    private LocationParams params;
    private LocationProvider provider;
    private boolean shouldStart = false;
    private boolean singleUpdate = false;

    public void onConnected(Bundle bundle) {
    }

    public LocationGooglePlayServicesWithFallbackProvider(Context context2) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context2) == 0) {
            this.provider = new LocationGooglePlayServicesProvider((GooglePlayServicesListener) this);
        } else {
            this.provider = new LocationManagerProvider();
        }
    }

    public void init(Context context2, Logger logger2) {
        this.logger = logger2;
        this.context = context2;
        logger2.mo40195d("Currently selected provider = " + this.provider.getClass().getSimpleName(), new Object[0]);
        this.provider.init(context2, logger2);
    }

    public void start(OnLocationUpdatedListener onLocationUpdatedListener, LocationParams locationParams, boolean z) {
        this.shouldStart = true;
        this.listener = onLocationUpdatedListener;
        this.params = locationParams;
        this.singleUpdate = z;
        this.provider.start(onLocationUpdatedListener, locationParams, z);
    }

    public void stop() {
        this.provider.stop();
        this.shouldStart = false;
    }

    public Location getLastLocation() {
        return this.provider.getLastLocation();
    }

    public void onConnectionSuspended(int i) {
        fallbackToLocationManager();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        fallbackToLocationManager();
    }

    private void fallbackToLocationManager() {
        this.logger.mo40195d("FusedLocationProvider not working, falling back and using LocationManager", new Object[0]);
        LocationManagerProvider locationManagerProvider = new LocationManagerProvider();
        this.provider = locationManagerProvider;
        locationManagerProvider.init(this.context, this.logger);
        if (this.shouldStart) {
            this.provider.start(this.listener, this.params, this.singleUpdate);
        }
    }
}
