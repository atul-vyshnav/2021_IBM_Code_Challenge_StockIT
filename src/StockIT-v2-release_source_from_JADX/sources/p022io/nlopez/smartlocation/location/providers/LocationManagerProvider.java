package p022io.nlopez.smartlocation.location.providers;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import p022io.nlopez.smartlocation.OnLocationUpdatedListener;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;
import p022io.nlopez.smartlocation.location.LocationProvider;
import p022io.nlopez.smartlocation.location.LocationStore;
import p022io.nlopez.smartlocation.location.config.LocationAccuracy;
import p022io.nlopez.smartlocation.location.config.LocationParams;
import p022io.nlopez.smartlocation.utils.Logger;

/* renamed from: io.nlopez.smartlocation.location.providers.LocationManagerProvider */
public class LocationManagerProvider implements LocationProvider, LocationListener {
    private static final String LOCATIONMANAGERPROVIDER_ID = "LMP";
    private OnLocationUpdatedListener listener;
    private LocationManager locationManager;
    private LocationStore locationStore;
    private Logger logger;
    private Context mContext;

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public void init(Context context, Logger logger2) {
        this.locationManager = (LocationManager) context.getSystemService(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        this.logger = logger2;
        this.mContext = context;
        this.locationStore = new LocationStore(context);
    }

    public void start(OnLocationUpdatedListener onLocationUpdatedListener, LocationParams locationParams, boolean z) {
        this.listener = onLocationUpdatedListener;
        if (onLocationUpdatedListener == null) {
            this.logger.mo40195d("Listener is null, you sure about this?", new Object[0]);
        }
        Criteria provider = getProvider(locationParams);
        if (!z) {
            this.locationManager.requestLocationUpdates(locationParams.getInterval(), locationParams.getDistance(), provider, this, Looper.getMainLooper());
        } else if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.locationManager.requestSingleUpdate(provider, this, Looper.getMainLooper());
        } else {
            this.logger.mo40199i("Permission check failed. Please handle it in your app before setting up location", new Object[0]);
        }
    }

    public void stop() {
        if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.locationManager.removeUpdates(this);
        }
    }

    public Location getLastLocation() {
        if (this.locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                return null;
            }
            Location lastKnownLocation = this.locationManager.getLastKnownLocation("gps");
            if (lastKnownLocation != null) {
                return lastKnownLocation;
            }
        }
        Location location = this.locationStore.get(LOCATIONMANAGERPROVIDER_ID);
        if (location != null) {
            return location;
        }
        return null;
    }

    private Criteria getProvider(LocationParams locationParams) {
        LocationAccuracy accuracy = locationParams.getAccuracy();
        Criteria criteria = new Criteria();
        int i = C31231.f1257x1a467bf9[accuracy.ordinal()];
        if (i == 1) {
            criteria.setAccuracy(1);
            criteria.setHorizontalAccuracy(3);
            criteria.setVerticalAccuracy(3);
            criteria.setBearingAccuracy(3);
            criteria.setSpeedAccuracy(3);
            criteria.setPowerRequirement(3);
        } else if (i != 2) {
            criteria.setAccuracy(2);
            criteria.setHorizontalAccuracy(1);
            criteria.setVerticalAccuracy(1);
            criteria.setBearingAccuracy(1);
            criteria.setSpeedAccuracy(1);
            criteria.setPowerRequirement(1);
        } else {
            criteria.setAccuracy(2);
            criteria.setHorizontalAccuracy(2);
            criteria.setVerticalAccuracy(2);
            criteria.setBearingAccuracy(2);
            criteria.setSpeedAccuracy(2);
            criteria.setPowerRequirement(2);
        }
        return criteria;
    }

    /* renamed from: io.nlopez.smartlocation.location.providers.LocationManagerProvider$1 */
    static /* synthetic */ class C31231 {

        /* renamed from: $SwitchMap$io$nlopez$smartlocation$location$config$LocationAccuracy */
        static final /* synthetic */ int[] f1257x1a467bf9;

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
                f1257x1a467bf9 = r0
                io.nlopez.smartlocation.location.config.LocationAccuracy r1 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.HIGH     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f1257x1a467bf9     // Catch:{ NoSuchFieldError -> 0x001d }
                io.nlopez.smartlocation.location.config.LocationAccuracy r1 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.MEDIUM     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f1257x1a467bf9     // Catch:{ NoSuchFieldError -> 0x0028 }
                io.nlopez.smartlocation.location.config.LocationAccuracy r1 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.LOW     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = f1257x1a467bf9     // Catch:{ NoSuchFieldError -> 0x0033 }
                io.nlopez.smartlocation.location.config.LocationAccuracy r1 = p022io.nlopez.smartlocation.location.config.LocationAccuracy.LOWEST     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p022io.nlopez.smartlocation.location.providers.LocationManagerProvider.C31231.<clinit>():void");
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
            this.locationStore.put(LOCATIONMANAGERPROVIDER_ID, location);
        }
    }
}
