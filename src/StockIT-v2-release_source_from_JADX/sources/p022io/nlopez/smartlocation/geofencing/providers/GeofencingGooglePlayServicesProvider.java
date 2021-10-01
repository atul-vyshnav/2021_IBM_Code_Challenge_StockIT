package p022io.nlopez.smartlocation.geofencing.providers;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p022io.nlopez.smartlocation.OnGeofencingTransitionListener;
import p022io.nlopez.smartlocation.geofencing.GeofencingProvider;
import p022io.nlopez.smartlocation.geofencing.GeofencingStore;
import p022io.nlopez.smartlocation.geofencing.model.GeofenceModel;
import p022io.nlopez.smartlocation.geofencing.utils.TransitionGeofence;
import p022io.nlopez.smartlocation.utils.GooglePlayServicesListener;
import p022io.nlopez.smartlocation.utils.Logger;

/* renamed from: io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider */
public class GeofencingGooglePlayServicesProvider implements GeofencingProvider, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {
    public static final String BROADCAST_INTENT_ACTION = (GeofencingGooglePlayServicesProvider.class.getCanonicalName() + ".GEOFENCE_TRANSITION");
    public static final String GEOFENCES_EXTRA_ID = "geofences";
    public static final String LOCATION_EXTRA_ID = "location";
    public static final int RESULT_CODE = 10003;
    public static final String TRANSITION_EXTRA_ID = "transition";
    private GoogleApiClient client;
    private Context context;
    private final List<Geofence> geofencesToAdd;
    private final List<String> geofencesToRemove;
    private BroadcastReceiver geofencingReceiver;
    /* access modifiers changed from: private */
    public GeofencingStore geofencingStore;
    private final GooglePlayServicesListener googlePlayServicesListener;
    /* access modifiers changed from: private */
    public OnGeofencingTransitionListener listener;
    /* access modifiers changed from: private */
    public Logger logger;
    private PendingIntent pendingIntent;
    private boolean stopped;

    public GeofencingGooglePlayServicesProvider() {
        this((GooglePlayServicesListener) null);
    }

    public GeofencingGooglePlayServicesProvider(GooglePlayServicesListener googlePlayServicesListener2) {
        this.geofencesToAdd = Collections.synchronizedList(new ArrayList());
        this.geofencesToRemove = Collections.synchronizedList(new ArrayList());
        this.stopped = false;
        this.geofencingReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (GeofencingGooglePlayServicesProvider.BROADCAST_INTENT_ACTION.equals(intent.getAction()) && intent.hasExtra(GeofencingGooglePlayServicesProvider.GEOFENCES_EXTRA_ID)) {
                    GeofencingGooglePlayServicesProvider.this.logger.mo40195d("Received geofencing event", new Object[0]);
                    int intExtra = intent.getIntExtra(GeofencingGooglePlayServicesProvider.TRANSITION_EXTRA_ID, -1);
                    for (String next : intent.getStringArrayListExtra(GeofencingGooglePlayServicesProvider.GEOFENCES_EXTRA_ID)) {
                        GeofenceModel geofenceModel = GeofencingGooglePlayServicesProvider.this.geofencingStore.get(next);
                        if (geofenceModel != null) {
                            GeofencingGooglePlayServicesProvider.this.listener.onGeofenceTransition(new TransitionGeofence(geofenceModel, intExtra));
                        } else {
                            Logger access$000 = GeofencingGooglePlayServicesProvider.this.logger;
                            access$000.mo40203w("Tried to retrieve geofence " + next + " but it was not in the store", new Object[0]);
                        }
                    }
                }
            }
        };
        this.googlePlayServicesListener = googlePlayServicesListener2;
    }

    public void init(Context context2, Logger logger2) {
        this.context = context2;
        this.logger = logger2;
        this.geofencingStore = new GeofencingStore(context2);
        GoogleApiClient build = new GoogleApiClient.Builder(context2).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        this.client = build;
        build.connect();
        this.pendingIntent = PendingIntent.getService(context2, 0, new Intent(context2, GeofencingService.class), 134217728);
    }

    public void addGeofence(GeofenceModel geofenceModel) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(geofenceModel);
        addGeofences(arrayList);
    }

    public void addGeofences(List<GeofenceModel> list) {
        ArrayList arrayList = new ArrayList();
        for (GeofenceModel next : list) {
            this.geofencingStore.put(next.getRequestId(), next);
            arrayList.add(next.toGeofence());
        }
        if (this.client.isConnected()) {
            if (this.geofencesToAdd.size() > 0) {
                arrayList.addAll(this.geofencesToAdd);
                this.geofencesToAdd.clear();
            }
            if (ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                LocationServices.GeofencingApi.addGeofences(this.client, (List<Geofence>) arrayList, this.pendingIntent);
                return;
            }
            return;
        }
        for (GeofenceModel geofence : list) {
            this.geofencesToAdd.add(geofence.toGeofence());
        }
    }

    public void removeGeofence(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        removeGeofences(arrayList);
    }

    public void removeGeofences(List<String> list) {
        for (String remove : list) {
            this.geofencingStore.remove(remove);
        }
        if (this.client.isConnected()) {
            if (this.geofencesToRemove.size() > 0) {
                list.addAll(this.geofencesToRemove);
                this.geofencesToRemove.clear();
            }
            LocationServices.GeofencingApi.removeGeofences(this.client, list);
            return;
        }
        this.geofencesToRemove.addAll(list);
    }

    public void start(OnGeofencingTransitionListener onGeofencingTransitionListener) {
        this.listener = onGeofencingTransitionListener;
        this.context.registerReceiver(this.geofencingReceiver, new IntentFilter(BROADCAST_INTENT_ACTION));
        if (!this.client.isConnected()) {
            this.logger.mo40195d("still not connected - scheduled start when connection is ok", new Object[0]);
        } else if (this.stopped) {
            this.client.connect();
            this.stopped = false;
        }
    }

    public void stop() {
        this.logger.mo40195d("stop", new Object[0]);
        if (this.client.isConnected()) {
            this.client.disconnect();
        }
        try {
            this.context.unregisterReceiver(this.geofencingReceiver);
        } catch (IllegalArgumentException unused) {
            this.logger.mo40195d("Silenced 'receiver not registered' stuff (calling stop more times than necessary did this)", new Object[0]);
        }
        this.stopped = true;
    }

    public void onConnected(Bundle bundle) {
        this.logger.mo40195d("onConnected", new Object[0]);
        if (this.client.isConnected()) {
            if (this.geofencesToAdd.size() > 0) {
                if (ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                    LocationServices.GeofencingApi.addGeofences(this.client, this.geofencesToAdd, this.pendingIntent);
                    this.geofencesToAdd.clear();
                } else {
                    return;
                }
            }
            if (this.geofencesToRemove.size() > 0) {
                LocationServices.GeofencingApi.removeGeofences(this.client, this.geofencesToRemove);
                this.geofencesToRemove.clear();
            }
        }
        GooglePlayServicesListener googlePlayServicesListener2 = this.googlePlayServicesListener;
        if (googlePlayServicesListener2 != null) {
            googlePlayServicesListener2.onConnected(bundle);
        }
    }

    public void onConnectionSuspended(int i) {
        Logger logger2 = this.logger;
        logger2.mo40195d("onConnectionSuspended " + i, new Object[0]);
        GooglePlayServicesListener googlePlayServicesListener2 = this.googlePlayServicesListener;
        if (googlePlayServicesListener2 != null) {
            googlePlayServicesListener2.onConnectionSuspended(i);
        }
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.logger.mo40195d("onConnectionFailed", new Object[0]);
        GooglePlayServicesListener googlePlayServicesListener2 = this.googlePlayServicesListener;
        if (googlePlayServicesListener2 != null) {
            googlePlayServicesListener2.onConnectionFailed(connectionResult);
        }
    }

    /* renamed from: io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider$GeofencingService */
    public static class GeofencingService extends IntentService {
        public GeofencingService() {
            super(GeofencingService.class.getSimpleName());
        }

        /* access modifiers changed from: protected */
        public void onHandleIntent(Intent intent) {
            GeofencingEvent fromIntent = GeofencingEvent.fromIntent(intent);
            if (fromIntent != null && !fromIntent.hasError()) {
                int geofenceTransition = fromIntent.getGeofenceTransition();
                Intent intent2 = new Intent(GeofencingGooglePlayServicesProvider.BROADCAST_INTENT_ACTION);
                intent2.putExtra(GeofencingGooglePlayServicesProvider.TRANSITION_EXTRA_ID, geofenceTransition);
                intent2.putExtra(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID, fromIntent.getTriggeringLocation());
                ArrayList arrayList = new ArrayList();
                for (Geofence requestId : fromIntent.getTriggeringGeofences()) {
                    arrayList.add(requestId.getRequestId());
                }
                intent2.putStringArrayListExtra(GeofencingGooglePlayServicesProvider.GEOFENCES_EXTRA_ID, arrayList);
                sendBroadcast(intent2);
            }
        }
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            this.logger.mo40195d("Geofencing update request successful", new Object[0]);
        } else if (!status.hasResolution() || !(this.context instanceof Activity)) {
            Logger logger2 = this.logger;
            logger2.mo40197e("Registering failed: " + status.getStatusMessage(), new Object[0]);
        } else {
            this.logger.mo40203w("Unable to register, but we can solve this - will startActivityForResult expecting result code 10003 (if received, please try again)", new Object[0]);
            try {
                status.startResolutionForResult((Activity) this.context, RESULT_CODE);
            } catch (IntentSender.SendIntentException e) {
                this.logger.mo40198e(e, "problem with startResolutionForResult", new Object[0]);
            }
        }
    }
}
