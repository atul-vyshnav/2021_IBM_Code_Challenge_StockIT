package p022io.nlopez.smartlocation.location.providers;

import android.content.Context;
import android.location.Location;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import p022io.nlopez.smartlocation.OnLocationUpdatedListener;
import p022io.nlopez.smartlocation.location.LocationProvider;
import p022io.nlopez.smartlocation.location.ServiceLocationProvider;
import p022io.nlopez.smartlocation.location.config.LocationParams;
import p022io.nlopez.smartlocation.utils.Logger;

/* renamed from: io.nlopez.smartlocation.location.providers.MultiFallbackProvider */
public class MultiFallbackProvider implements LocationProvider {
    private Context context;
    private LocationProvider currentProvider;
    private OnLocationUpdatedListener locationListener;
    private LocationParams locationParams;
    private Logger logger;
    /* access modifiers changed from: private */
    public Queue<LocationProvider> providers = new LinkedList();
    private boolean shouldStart;
    private boolean singleUpdate;

    MultiFallbackProvider() {
    }

    public void init(Context context2, Logger logger2) {
        this.context = context2;
        this.logger = logger2;
        LocationProvider currentProvider2 = getCurrentProvider();
        if (currentProvider2 != null) {
            currentProvider2.init(context2, logger2);
        }
    }

    public void start(OnLocationUpdatedListener onLocationUpdatedListener, LocationParams locationParams2, boolean z) {
        this.shouldStart = true;
        this.locationListener = onLocationUpdatedListener;
        this.locationParams = locationParams2;
        this.singleUpdate = z;
        LocationProvider currentProvider2 = getCurrentProvider();
        if (currentProvider2 != null) {
            currentProvider2.start(onLocationUpdatedListener, locationParams2, z);
        }
    }

    public void stop() {
        LocationProvider currentProvider2 = getCurrentProvider();
        if (currentProvider2 != null) {
            currentProvider2.stop();
        }
    }

    public Location getLastLocation() {
        LocationProvider currentProvider2 = getCurrentProvider();
        if (currentProvider2 == null) {
            return null;
        }
        return currentProvider2.getLastLocation();
    }

    /* access modifiers changed from: package-private */
    public boolean addProvider(LocationProvider locationProvider) {
        return this.providers.add(locationProvider);
    }

    /* access modifiers changed from: package-private */
    public Collection<LocationProvider> getProviders() {
        return this.providers;
    }

    /* access modifiers changed from: package-private */
    public LocationProvider getCurrentProvider() {
        if (this.currentProvider == null && !this.providers.isEmpty()) {
            this.currentProvider = this.providers.poll();
        }
        return this.currentProvider;
    }

    /* access modifiers changed from: package-private */
    public void fallbackProvider() {
        if (!this.providers.isEmpty()) {
            this.currentProvider.stop();
            LocationProvider poll = this.providers.poll();
            this.currentProvider = poll;
            poll.init(this.context, this.logger);
            if (this.shouldStart) {
                this.currentProvider.start(this.locationListener, this.locationParams, this.singleUpdate);
            }
        }
    }

    /* renamed from: io.nlopez.smartlocation.location.providers.MultiFallbackProvider$Builder */
    public static class Builder {
        private MultiFallbackProvider builtProvider = new MultiFallbackProvider();

        public Builder withGooglePlayServicesProvider() {
            return withServiceProvider(new LocationGooglePlayServicesProvider());
        }

        public Builder withDefaultProvider() {
            return withProvider(new LocationManagerProvider());
        }

        public Builder withServiceProvider(ServiceLocationProvider serviceLocationProvider) {
            serviceLocationProvider.setServiceListener(new FallbackListenerWrapper(this.builtProvider, serviceLocationProvider));
            return withProvider(serviceLocationProvider);
        }

        public Builder withProvider(LocationProvider locationProvider) {
            this.builtProvider.addProvider(locationProvider);
            return this;
        }

        public MultiFallbackProvider build() {
            if (this.builtProvider.providers.isEmpty()) {
                withDefaultProvider();
            }
            return this.builtProvider;
        }
    }
}
