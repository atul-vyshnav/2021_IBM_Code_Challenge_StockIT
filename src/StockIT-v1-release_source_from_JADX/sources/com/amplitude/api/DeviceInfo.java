package com.amplitude.api;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

public class DeviceInfo {
    public static final String OS_NAME = "android";
    private static final String SETTING_ADVERTISING_ID = "advertising_id";
    private static final String SETTING_LIMIT_AD_TRACKING = "limit_ad_tracking";
    public static final String TAG = "com.amplitude.api.DeviceInfo";
    private CachedInfo cachedInfo;
    /* access modifiers changed from: private */
    public Context context;
    private boolean locationListening = true;

    private class CachedInfo {
        /* access modifiers changed from: private */
        public String advertisingId;
        /* access modifiers changed from: private */
        public String brand;
        /* access modifiers changed from: private */
        public String carrier;
        /* access modifiers changed from: private */
        public String country;
        /* access modifiers changed from: private */
        public boolean gpsEnabled;
        /* access modifiers changed from: private */
        public String language;
        /* access modifiers changed from: private */
        public boolean limitAdTrackingEnabled;
        /* access modifiers changed from: private */
        public String manufacturer;
        /* access modifiers changed from: private */
        public String model;
        /* access modifiers changed from: private */
        public String osName;
        /* access modifiers changed from: private */
        public String osVersion;
        /* access modifiers changed from: private */
        public String versionName;

        private String getOsName() {
            return DeviceInfo.OS_NAME;
        }

        private CachedInfo() {
            this.advertisingId = getAdvertisingId();
            this.versionName = getVersionName();
            this.osName = getOsName();
            this.osVersion = getOsVersion();
            this.brand = getBrand();
            this.manufacturer = getManufacturer();
            this.model = getModel();
            this.carrier = getCarrier();
            this.country = getCountry();
            this.language = getLanguage();
            this.gpsEnabled = checkGPSEnabled();
        }

        private String getVersionName() {
            try {
                return DeviceInfo.this.context.getPackageManager().getPackageInfo(DeviceInfo.this.context.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                Diagnostics.getLogger().logError("Failed to get version name", e);
                return null;
            }
        }

        private String getOsVersion() {
            return Build.VERSION.RELEASE;
        }

        private String getBrand() {
            return Build.BRAND;
        }

        private String getManufacturer() {
            return Build.MANUFACTURER;
        }

        private String getModel() {
            return Build.MODEL;
        }

        private String getCarrier() {
            try {
                return ((TelephonyManager) DeviceInfo.this.context.getSystemService("phone")).getNetworkOperatorName();
            } catch (Exception e) {
                Diagnostics.getLogger().logError("Failed to get carrier", e);
                return null;
            }
        }

        private String getCountry() {
            String countryFromLocation = getCountryFromLocation();
            if (!C0772Utils.isEmptyString(countryFromLocation)) {
                return countryFromLocation;
            }
            String countryFromNetwork = getCountryFromNetwork();
            if (!C0772Utils.isEmptyString(countryFromNetwork)) {
                return countryFromNetwork;
            }
            return getCountryFromLocale();
        }

        private String getCountryFromLocation() {
            Location mostRecentLocation;
            List<Address> fromLocation;
            if (DeviceInfo.this.isLocationListening() && (mostRecentLocation = DeviceInfo.this.getMostRecentLocation()) != null) {
                try {
                    if (Geocoder.isPresent() && (fromLocation = DeviceInfo.this.getGeocoder().getFromLocation(mostRecentLocation.getLatitude(), mostRecentLocation.getLongitude(), 1)) != null) {
                        for (Address next : fromLocation) {
                            if (next != null) {
                                return next.getCountryCode();
                            }
                        }
                    }
                } catch (IOException e) {
                    Diagnostics.getLogger().logError("Failed to get country from location", e);
                } catch (NullPointerException e2) {
                    Diagnostics.getLogger().logError("Failed to get country from location", e2);
                } catch (NoSuchMethodError e3) {
                    Diagnostics.getLogger().logError("Failed to get country from location", e3);
                } catch (IllegalArgumentException e4) {
                    Diagnostics.getLogger().logError("Failed to get country from location", e4);
                } catch (IllegalStateException e5) {
                    Diagnostics.getLogger().logError("Failed to get country from location", e5);
                }
            }
            return null;
        }

        private String getCountryFromNetwork() {
            String networkCountryIso;
            try {
                TelephonyManager telephonyManager = (TelephonyManager) DeviceInfo.this.context.getSystemService("phone");
                if (telephonyManager.getPhoneType() == 2 || (networkCountryIso = telephonyManager.getNetworkCountryIso()) == null) {
                    return null;
                }
                return networkCountryIso.toUpperCase(Locale.US);
            } catch (Exception e) {
                Diagnostics.getLogger().logError("Failed to get country from network", e);
                return null;
            }
        }

        private String getCountryFromLocale() {
            return Locale.getDefault().getCountry();
        }

        private String getLanguage() {
            return Locale.getDefault().getLanguage();
        }

        private String getAdvertisingId() {
            if ("Amazon".equals(getManufacturer())) {
                return getAndCacheAmazonAdvertisingId();
            }
            return getAndCacheGoogleAdvertisingId();
        }

        private String getAndCacheAmazonAdvertisingId() {
            ContentResolver contentResolver = DeviceInfo.this.context.getContentResolver();
            boolean z = false;
            if (Settings.Secure.getInt(contentResolver, DeviceInfo.SETTING_LIMIT_AD_TRACKING, 0) == 1) {
                z = true;
            }
            this.limitAdTrackingEnabled = z;
            String string = Settings.Secure.getString(contentResolver, DeviceInfo.SETTING_ADVERTISING_ID);
            this.advertisingId = string;
            return string;
        }

        private String getAndCacheGoogleAdvertisingId() {
            try {
                boolean z = true;
                Object invoke = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke((Object) null, new Object[]{DeviceInfo.this.context});
                Boolean bool = (Boolean) invoke.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(invoke, new Object[0]);
                if (bool == null || !bool.booleanValue()) {
                    z = false;
                }
                this.limitAdTrackingEnabled = z;
                this.advertisingId = (String) invoke.getClass().getMethod("getId", new Class[0]).invoke(invoke, new Object[0]);
            } catch (ClassNotFoundException e) {
                AmplitudeLog.getLogger().mo10617w(DeviceInfo.TAG, "Google Play Services SDK not found!");
                Diagnostics.getLogger().logError("Failed to get ADID", e);
            } catch (InvocationTargetException e2) {
                AmplitudeLog.getLogger().mo10617w(DeviceInfo.TAG, "Google Play Services not available");
                Diagnostics.getLogger().logError("Failed to get ADID", e2);
            } catch (Exception e3) {
                AmplitudeLog.getLogger().mo10607e(DeviceInfo.TAG, "Encountered an error connecting to Google Play Services", e3);
                Diagnostics.getLogger().logError("Failed to get ADID", e3);
            }
            return this.advertisingId;
        }

        private boolean checkGPSEnabled() {
            try {
                Integer num = (Integer) Class.forName("com.google.android.gms.common.GooglePlayServicesUtil").getMethod("isGooglePlayServicesAvailable", new Class[]{Context.class}).invoke((Object) null, new Object[]{DeviceInfo.this.context});
                if (num == null || num.intValue() != 0) {
                    return false;
                }
                return true;
            } catch (NoClassDefFoundError e) {
                AmplitudeLog.getLogger().mo10617w(DeviceInfo.TAG, "Google Play Services Util not found!");
                Diagnostics.getLogger().logError("Failed to check GPS enabled", e);
                return false;
            } catch (ClassNotFoundException e2) {
                AmplitudeLog.getLogger().mo10617w(DeviceInfo.TAG, "Google Play Services Util not found!");
                Diagnostics.getLogger().logError("Failed to check GPS enabled", e2);
                return false;
            } catch (NoSuchMethodException e3) {
                AmplitudeLog.getLogger().mo10617w(DeviceInfo.TAG, "Google Play Services not available");
                Diagnostics.getLogger().logError("Failed to check GPS enabled", e3);
                return false;
            } catch (InvocationTargetException e4) {
                AmplitudeLog.getLogger().mo10617w(DeviceInfo.TAG, "Google Play Services not available");
                Diagnostics.getLogger().logError("Failed to check GPS enabled", e4);
                return false;
            } catch (IllegalAccessException e5) {
                AmplitudeLog.getLogger().mo10617w(DeviceInfo.TAG, "Google Play Services not available");
                Diagnostics.getLogger().logError("Failed to check GPS enabled", e5);
                return false;
            } catch (Exception e6) {
                AmplitudeLog logger = AmplitudeLog.getLogger();
                logger.mo10617w(DeviceInfo.TAG, "Error when checking for Google Play Services: " + e6);
                Diagnostics.getLogger().logError("Failed to check GPS enabled", e6);
                return false;
            }
        }
    }

    public DeviceInfo(Context context2) {
        this.context = context2;
    }

    private CachedInfo getCachedInfo() {
        if (this.cachedInfo == null) {
            this.cachedInfo = new CachedInfo();
        }
        return this.cachedInfo;
    }

    public void prefetch() {
        getCachedInfo();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public String getVersionName() {
        return getCachedInfo().versionName;
    }

    public String getOsName() {
        return getCachedInfo().osName;
    }

    public String getOsVersion() {
        return getCachedInfo().osVersion;
    }

    public String getBrand() {
        return getCachedInfo().brand;
    }

    public String getManufacturer() {
        return getCachedInfo().manufacturer;
    }

    public String getModel() {
        return getCachedInfo().model;
    }

    public String getCarrier() {
        return getCachedInfo().carrier;
    }

    public String getCountry() {
        return getCachedInfo().country;
    }

    public String getLanguage() {
        return getCachedInfo().language;
    }

    public String getAdvertisingId() {
        return getCachedInfo().advertisingId;
    }

    public boolean isLimitAdTrackingEnabled() {
        return getCachedInfo().limitAdTrackingEnabled;
    }

    public boolean isGooglePlayServicesEnabled() {
        return getCachedInfo().gpsEnabled;
    }

    public Location getMostRecentLocation() {
        LocationManager locationManager;
        List<String> list;
        Location location;
        Location location2 = null;
        if (!isLocationListening() || (locationManager = (LocationManager) this.context.getSystemService(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID)) == null) {
            return null;
        }
        try {
            list = locationManager.getProviders(true);
        } catch (SecurityException e) {
            Diagnostics.getLogger().logError("Failed to get most recent location", e);
            list = null;
        }
        if (list == null) {
            return null;
        }
        ArrayList<Location> arrayList = new ArrayList<>();
        for (String lastKnownLocation : list) {
            try {
                location = locationManager.getLastKnownLocation(lastKnownLocation);
            } catch (Exception e2) {
                AmplitudeLog.getLogger().mo10617w(TAG, "Failed to get most recent location");
                Diagnostics.getLogger().logError("Failed to get most recent location", e2);
                location = null;
            }
            if (location != null) {
                arrayList.add(location);
            }
        }
        long j = -1;
        for (Location location3 : arrayList) {
            if (location3.getTime() > j) {
                j = location3.getTime();
                location2 = location3;
            }
        }
        return location2;
    }

    public boolean isLocationListening() {
        return this.locationListening;
    }

    public void setLocationListening(boolean z) {
        this.locationListening = z;
    }

    /* access modifiers changed from: protected */
    public Geocoder getGeocoder() {
        return new Geocoder(this.context, Locale.ENGLISH);
    }
}
