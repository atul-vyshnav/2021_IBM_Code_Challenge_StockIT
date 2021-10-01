package p022io.nlopez.smartlocation.location.utils;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

/* renamed from: io.nlopez.smartlocation.location.utils.LocationState */
public class LocationState {
    private static LocationState instance;
    private Context context;
    private LocationManager locationManager;

    private LocationState(Context context2) {
        this.context = context2;
        this.locationManager = (LocationManager) context2.getSystemService(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
    }

    public static LocationState with(Context context2) {
        if (instance == null) {
            instance = new LocationState(context2.getApplicationContext());
        }
        return instance;
    }

    public boolean locationServicesEnabled() {
        int i;
        if (Build.VERSION.SDK_INT < 19) {
            return !TextUtils.isEmpty(Settings.Secure.getString(this.context.getContentResolver(), "location_providers_allowed"));
        }
        try {
            i = Settings.Secure.getInt(this.context.getContentResolver(), "location_mode");
        } catch (Settings.SettingNotFoundException unused) {
            i = 0;
        }
        if (i != 0) {
            return true;
        }
        return false;
    }

    public boolean isAnyProviderAvailable() {
        return isGpsAvailable() || isNetworkAvailable();
    }

    public boolean isGpsAvailable() {
        return this.locationManager.isProviderEnabled("gps");
    }

    public boolean isNetworkAvailable() {
        return this.locationManager.isProviderEnabled("network");
    }

    public boolean isPassiveAvailable() {
        return this.locationManager.isProviderEnabled("passive");
    }

    @Deprecated
    public boolean isMockSettingEnabled() {
        return !"0".equals(Settings.Secure.getString(this.context.getContentResolver(), "mock_location"));
    }
}
