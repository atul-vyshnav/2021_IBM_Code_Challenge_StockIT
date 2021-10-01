package p022io.nlopez.smartlocation.activity.providers;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import p022io.nlopez.smartlocation.OnActivityUpdatedListener;
import p022io.nlopez.smartlocation.activity.ActivityProvider;
import p022io.nlopez.smartlocation.activity.ActivityStore;
import p022io.nlopez.smartlocation.activity.config.ActivityParams;
import p022io.nlopez.smartlocation.utils.GooglePlayServicesListener;
import p022io.nlopez.smartlocation.utils.Logger;

/* renamed from: io.nlopez.smartlocation.activity.providers.ActivityGooglePlayServicesProvider */
public class ActivityGooglePlayServicesProvider implements ActivityProvider, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {
    /* access modifiers changed from: private */
    public static final String BROADCAST_INTENT_ACTION = (ActivityGooglePlayServicesProvider.class.getCanonicalName() + ".DETECTED_ACTIVITY");
    private static final String DETECTED_ACTIVITY_EXTRA_ID = "activity";
    private static final String GMS_ID = "GMS";
    public static final int RESULT_CODE = 10002;
    private ActivityParams activityParams;
    private BroadcastReceiver activityReceiver;
    private ActivityStore activityStore;
    private GoogleApiClient client;
    private Context context;
    private final GooglePlayServicesListener googlePlayServicesListener;
    private OnActivityUpdatedListener listener;
    /* access modifiers changed from: private */
    public Logger logger;
    private PendingIntent pendingIntent;
    private boolean shouldStart;
    private boolean stopped;

    public ActivityGooglePlayServicesProvider() {
        this((GooglePlayServicesListener) null);
    }

    public ActivityGooglePlayServicesProvider(GooglePlayServicesListener googlePlayServicesListener2) {
        this.shouldStart = false;
        this.stopped = false;
        this.activityReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (ActivityGooglePlayServicesProvider.BROADCAST_INTENT_ACTION.equals(intent.getAction()) && intent.hasExtra(ActivityGooglePlayServicesProvider.DETECTED_ACTIVITY_EXTRA_ID)) {
                    ActivityGooglePlayServicesProvider.this.logger.mo40195d("sending new activity", new Object[0]);
                    ActivityGooglePlayServicesProvider.this.notifyActivity((DetectedActivity) intent.getParcelableExtra(ActivityGooglePlayServicesProvider.DETECTED_ACTIVITY_EXTRA_ID));
                }
            }
        };
        this.googlePlayServicesListener = googlePlayServicesListener2;
    }

    public void init(Context context2, Logger logger2) {
        this.context = context2;
        this.logger = logger2;
        this.activityStore = new ActivityStore(context2);
        if (!this.shouldStart) {
            GoogleApiClient build = new GoogleApiClient.Builder(context2).addApi(ActivityRecognition.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            this.client = build;
            build.connect();
            return;
        }
        logger2.mo40195d("already started", new Object[0]);
    }

    public void start(OnActivityUpdatedListener onActivityUpdatedListener, ActivityParams activityParams2) {
        this.activityParams = activityParams2;
        this.listener = onActivityUpdatedListener;
        this.context.registerReceiver(this.activityReceiver, new IntentFilter(BROADCAST_INTENT_ACTION));
        if (this.client.isConnected()) {
            startUpdating(activityParams2);
        } else if (this.stopped) {
            this.shouldStart = true;
            this.client.connect();
            this.stopped = false;
        } else {
            this.shouldStart = true;
            this.logger.mo40195d("still not connected - scheduled start when connection is ok", new Object[0]);
        }
    }

    private void startUpdating(ActivityParams activityParams2) {
        if (this.client.isConnected()) {
            this.pendingIntent = PendingIntent.getService(this.context, 0, new Intent(this.context, ActivityRecognitionService.class), 134217728);
            ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(this.client, activityParams2.getInterval(), this.pendingIntent).setResultCallback(this);
        }
    }

    public void stop() {
        this.logger.mo40195d("stop", new Object[0]);
        if (this.client.isConnected()) {
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(this.client, this.pendingIntent);
            this.client.disconnect();
        }
        try {
            this.context.unregisterReceiver(this.activityReceiver);
        } catch (IllegalArgumentException unused) {
            this.logger.mo40195d("Silenced 'receiver not registered' stuff (calling stop more times than necessary did this)", new Object[0]);
        }
        this.shouldStart = false;
        this.stopped = true;
    }

    public DetectedActivity getLastActivity() {
        ActivityStore activityStore2 = this.activityStore;
        if (activityStore2 != null) {
            return activityStore2.get(GMS_ID);
        }
        return null;
    }

    public void onConnected(Bundle bundle) {
        this.logger.mo40195d("onConnected", new Object[0]);
        if (this.shouldStart) {
            startUpdating(this.activityParams);
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

    /* access modifiers changed from: private */
    public void notifyActivity(DetectedActivity detectedActivity) {
        OnActivityUpdatedListener onActivityUpdatedListener = this.listener;
        if (onActivityUpdatedListener != null) {
            onActivityUpdatedListener.onActivityUpdated(detectedActivity);
        }
        ActivityStore activityStore2 = this.activityStore;
        if (activityStore2 != null) {
            activityStore2.put(GMS_ID, detectedActivity);
        }
    }

    /* renamed from: io.nlopez.smartlocation.activity.providers.ActivityGooglePlayServicesProvider$ActivityRecognitionService */
    public static class ActivityRecognitionService extends IntentService {
        public ActivityRecognitionService() {
            super(ActivityRecognitionService.class.getSimpleName());
        }

        /* access modifiers changed from: protected */
        public void onHandleIntent(Intent intent) {
            if (ActivityRecognitionResult.hasResult(intent)) {
                DetectedActivity mostProbableActivity = ActivityRecognitionResult.extractResult(intent).getMostProbableActivity();
                Intent intent2 = new Intent(ActivityGooglePlayServicesProvider.BROADCAST_INTENT_ACTION);
                intent2.putExtra(ActivityGooglePlayServicesProvider.DETECTED_ACTIVITY_EXTRA_ID, mostProbableActivity);
                sendBroadcast(intent2);
            }
        }
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            this.logger.mo40195d("Activity update request successful", new Object[0]);
        } else if (!status.hasResolution() || !(this.context instanceof Activity)) {
            Logger logger2 = this.logger;
            logger2.mo40197e("Registering failed: " + status.getStatusMessage(), new Object[0]);
        } else {
            this.logger.mo40203w("Unable to register, but we can solve this - will startActivityForResult expecting result code 10002 (if received, please try again)", new Object[0]);
            try {
                status.startResolutionForResult((Activity) this.context, RESULT_CODE);
            } catch (IntentSender.SendIntentException e) {
                this.logger.mo40198e(e, "problem with startResolutionForResult", new Object[0]);
            }
        }
    }
}
