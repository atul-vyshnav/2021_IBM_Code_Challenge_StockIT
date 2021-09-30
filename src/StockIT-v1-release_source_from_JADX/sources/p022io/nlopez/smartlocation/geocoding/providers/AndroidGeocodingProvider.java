package p022io.nlopez.smartlocation.geocoding.providers;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import p022io.nlopez.smartlocation.OnGeocodingListener;
import p022io.nlopez.smartlocation.OnReverseGeocodingListener;
import p022io.nlopez.smartlocation.geocoding.GeocodingProvider;
import p022io.nlopez.smartlocation.geocoding.utils.LocationAddress;
import p022io.nlopez.smartlocation.utils.Logger;

/* renamed from: io.nlopez.smartlocation.geocoding.providers.AndroidGeocodingProvider */
public class AndroidGeocodingProvider implements GeocodingProvider {
    /* access modifiers changed from: private */
    public static final String BROADCAST_DIRECT_GEOCODING_ACTION;
    /* access modifiers changed from: private */
    public static final String BROADCAST_REVERSE_GEOCODING_ACTION;
    private static final String DIRECT_GEOCODING_ID = "direct";
    private static final String LOCALE_ID = "locale";
    private static final String LOCATION_ID = "location";
    private static final String NAME_ID = "name";
    private static final String RESULT_ID = "result";
    private static final String REVERSE_GEOCODING_ID = "reverse";
    private Context context;
    private BroadcastReceiver directReceiver;
    private HashMap<Location, Integer> fromLocationList;
    private HashMap<String, Integer> fromNameList;
    /* access modifiers changed from: private */
    public OnGeocodingListener geocodingListener;
    private Locale locale;
    /* access modifiers changed from: private */
    public Logger logger;
    /* access modifiers changed from: private */
    public OnReverseGeocodingListener reverseGeocodingListener;
    private BroadcastReceiver reverseReceiver;

    static {
        Class<AndroidGeocodingProvider> cls = AndroidGeocodingProvider.class;
        BROADCAST_DIRECT_GEOCODING_ACTION = cls.getCanonicalName() + ".DIRECT_GEOCODE_ACTION";
        BROADCAST_REVERSE_GEOCODING_ACTION = cls.getCanonicalName() + ".REVERSE_GEOCODE_ACTION";
    }

    public AndroidGeocodingProvider() {
        this(Locale.getDefault());
    }

    public AndroidGeocodingProvider(Locale locale2) {
        this.directReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (AndroidGeocodingProvider.BROADCAST_DIRECT_GEOCODING_ACTION.equals(intent.getAction())) {
                    AndroidGeocodingProvider.this.logger.mo40195d("sending new direct geocoding response", new Object[0]);
                    if (AndroidGeocodingProvider.this.geocodingListener != null) {
                        AndroidGeocodingProvider.this.geocodingListener.onLocationResolved(intent.getStringExtra("name"), intent.getParcelableArrayListExtra(AndroidGeocodingProvider.RESULT_ID));
                    }
                }
            }
        };
        this.reverseReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (AndroidGeocodingProvider.BROADCAST_REVERSE_GEOCODING_ACTION.equals(intent.getAction())) {
                    AndroidGeocodingProvider.this.logger.mo40195d("sending new reverse geocoding response", new Object[0]);
                    if (AndroidGeocodingProvider.this.reverseGeocodingListener != null) {
                        AndroidGeocodingProvider.this.reverseGeocodingListener.onAddressResolved((Location) intent.getParcelableExtra("location"), (ArrayList) intent.getSerializableExtra(AndroidGeocodingProvider.RESULT_ID));
                    }
                }
            }
        };
        if (locale2 != null) {
            this.locale = locale2;
            this.fromNameList = new HashMap<>();
            this.fromLocationList = new HashMap<>();
            if (!Geocoder.isPresent()) {
                throw new RuntimeException("Android Geocoder not present. Please check if Geocoder.isPresent() before invoking the search");
            }
            return;
        }
        throw new RuntimeException("Locale is null");
    }

    public void init(Context context2, Logger logger2) {
        this.logger = logger2;
        this.context = context2;
    }

    public void addName(String str, int i) {
        this.fromNameList.put(str, Integer.valueOf(i));
    }

    public void addLocation(Location location, int i) {
        this.fromLocationList.put(location, Integer.valueOf(i));
    }

    public void start(OnGeocodingListener onGeocodingListener, OnReverseGeocodingListener onReverseGeocodingListener) {
        this.geocodingListener = onGeocodingListener;
        this.reverseGeocodingListener = onReverseGeocodingListener;
        if (!this.fromNameList.isEmpty() || !this.fromLocationList.isEmpty()) {
            IntentFilter intentFilter = new IntentFilter(BROADCAST_DIRECT_GEOCODING_ACTION);
            IntentFilter intentFilter2 = new IntentFilter(BROADCAST_REVERSE_GEOCODING_ACTION);
            Intent intent = new Intent(this.context, AndroidGeocodingService.class);
            intent.putExtra(LOCALE_ID, this.locale);
            if (!this.fromNameList.isEmpty()) {
                this.context.registerReceiver(this.directReceiver, intentFilter);
                intent.putExtra(DIRECT_GEOCODING_ID, this.fromNameList);
            }
            if (!this.fromLocationList.isEmpty()) {
                this.context.registerReceiver(this.reverseReceiver, intentFilter2);
                intent.putExtra(REVERSE_GEOCODING_ID, this.fromLocationList);
            }
            this.context.startService(intent);
            this.fromNameList.clear();
            this.fromLocationList.clear();
            return;
        }
        this.logger.mo40203w("No direct geocoding or reverse geocoding points added", new Object[0]);
    }

    public void stop() {
        try {
            this.context.unregisterReceiver(this.directReceiver);
        } catch (IllegalArgumentException unused) {
            this.logger.mo40195d("Silenced 'receiver not registered' stuff (calling stop more times than necessary did this)", new Object[0]);
        }
        try {
            this.context.unregisterReceiver(this.reverseReceiver);
        } catch (IllegalArgumentException unused2) {
            this.logger.mo40195d("Silenced 'receiver not registered' stuff (calling stop more times than necessary did this)", new Object[0]);
        }
    }

    /* renamed from: io.nlopez.smartlocation.geocoding.providers.AndroidGeocodingProvider$AndroidGeocodingService */
    public static class AndroidGeocodingService extends IntentService {
        private Geocoder geocoder;

        public AndroidGeocodingService() {
            super(AndroidGeocodingService.class.getSimpleName());
        }

        /* access modifiers changed from: protected */
        public void onHandleIntent(Intent intent) {
            Locale locale = (Locale) intent.getSerializableExtra(AndroidGeocodingProvider.LOCALE_ID);
            if (locale == null) {
                this.geocoder = new Geocoder(this);
            } else {
                this.geocoder = new Geocoder(this, locale);
            }
            if (intent.hasExtra(AndroidGeocodingProvider.DIRECT_GEOCODING_ID)) {
                HashMap hashMap = (HashMap) intent.getSerializableExtra(AndroidGeocodingProvider.DIRECT_GEOCODING_ID);
                for (String str : hashMap.keySet()) {
                    sendDirectGeocodingBroadcast(str, addressFromName(str, ((Integer) hashMap.get(str)).intValue()));
                }
            }
            if (intent.hasExtra(AndroidGeocodingProvider.REVERSE_GEOCODING_ID)) {
                HashMap hashMap2 = (HashMap) intent.getSerializableExtra(AndroidGeocodingProvider.REVERSE_GEOCODING_ID);
                for (Location location : hashMap2.keySet()) {
                    sendReverseGeocodingBroadcast(location, addressFromLocation(location, ((Integer) hashMap2.get(location)).intValue()));
                }
            }
        }

        private void sendDirectGeocodingBroadcast(String str, ArrayList<LocationAddress> arrayList) {
            Intent intent = new Intent(AndroidGeocodingProvider.BROADCAST_DIRECT_GEOCODING_ACTION);
            intent.putExtra("name", str);
            intent.putExtra(AndroidGeocodingProvider.RESULT_ID, arrayList);
            sendBroadcast(intent);
        }

        private void sendReverseGeocodingBroadcast(Location location, ArrayList<Address> arrayList) {
            Intent intent = new Intent(AndroidGeocodingProvider.BROADCAST_REVERSE_GEOCODING_ACTION);
            intent.putExtra("location", location);
            intent.putExtra(AndroidGeocodingProvider.RESULT_ID, arrayList);
            sendBroadcast(intent);
        }

        private ArrayList<Address> addressFromLocation(Location location, int i) {
            try {
                return new ArrayList<>(this.geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), i));
            } catch (IOException unused) {
                return new ArrayList<>();
            }
        }

        private ArrayList<LocationAddress> addressFromName(String str, int i) {
            try {
                List<Address> fromLocationName = this.geocoder.getFromLocationName(str, i);
                ArrayList<LocationAddress> arrayList = new ArrayList<>();
                for (Address locationAddress : fromLocationName) {
                    arrayList.add(new LocationAddress(locationAddress));
                }
                return arrayList;
            } catch (IOException unused) {
                return new ArrayList<>();
            }
        }
    }
}
