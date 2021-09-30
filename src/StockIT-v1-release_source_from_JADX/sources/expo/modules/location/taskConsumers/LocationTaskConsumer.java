package expo.modules.location.taskConsumers;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import expo.modules.location.LocationHelpers;
import expo.modules.location.services.LocationTaskService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.unimodules.core.MapHelper;
import org.unimodules.core.arguments.MapArguments;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.interfaces.taskManager.TaskConsumer;
import org.unimodules.interfaces.taskManager.TaskConsumerInterface;
import org.unimodules.interfaces.taskManager.TaskExecutionCallback;
import org.unimodules.interfaces.taskManager.TaskInterface;
import org.unimodules.interfaces.taskManager.TaskManagerUtilsInterface;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

public class LocationTaskConsumer extends TaskConsumer implements TaskConsumerInterface, LifecycleEventListener {
    private static final String FOREGROUND_SERVICE_KEY = "foregroundService";
    private static final String TAG = "LocationTaskConsumer";
    public static int VERSION = 1;
    private static long sLastTimestamp;
    private double mDeferredDistance = 0.0d;
    private List<Location> mDeferredLocations = new ArrayList();
    private boolean mIsHostPaused = true;
    private Location mLastReportedLocation;
    private FusedLocationProviderClient mLocationClient;
    private LocationRequest mLocationRequest;
    private PendingIntent mPendingIntent;
    /* access modifiers changed from: private */
    public LocationTaskService mService;
    private TaskInterface mTask;

    public String taskType() {
        return GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID;
    }

    public LocationTaskConsumer(Context context, TaskManagerUtilsInterface taskManagerUtilsInterface) {
        super(context, taskManagerUtilsInterface);
    }

    public void didRegister(TaskInterface taskInterface) {
        this.mTask = taskInterface;
        startLocationUpdates();
        maybeStartForegroundService();
    }

    public void didUnregister() {
        stopLocationUpdates();
        stopForegroundService();
        this.mTask = null;
        this.mPendingIntent = null;
        this.mLocationRequest = null;
        this.mLocationClient = null;
    }

    public void setOptions(Map<String, Object> map) {
        super.setOptions(map);
        stopLocationUpdates();
        startLocationUpdates();
        maybeStartForegroundService();
    }

    public void didReceiveBroadcast(Intent intent) {
        if (this.mTask != null) {
            LocationResult extractResult = LocationResult.extractResult(intent);
            if (extractResult != null) {
                deferLocations(extractResult.getLocations());
                maybeReportDeferredLocations();
                return;
            }
            try {
                this.mLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    public void onComplete(Task<Location> task) {
                        Location result = task.getResult();
                        if (result != null) {
                            Log.i(LocationTaskConsumer.TAG, "get last location: " + result);
                            LocationTaskConsumer.this.deferLocations(Collections.singletonList(result));
                            LocationTaskConsumer.this.maybeReportDeferredLocations();
                        }
                    }
                });
            } catch (SecurityException e) {
                Log.e(TAG, "Cannot get last location: " + e.getMessage());
            }
        }
    }

    public boolean didExecuteJob(final JobService jobService, final JobParameters jobParameters) {
        List<PersistableBundle> extractDataFromJobParams = getTaskManagerUtils().extractDataFromJobParams(jobParameters);
        ArrayList arrayList = new ArrayList();
        for (PersistableBundle next : extractDataFromJobParams) {
            Bundle bundle = new Bundle();
            Bundle bundle2 = new Bundle();
            if (next != null) {
                bundle2.putAll(next.getPersistableBundle("coords"));
                bundle.putAll(next);
                bundle.putBundle("coords", bundle2);
                arrayList.add(bundle);
            }
        }
        executeTaskWithLocationBundles(arrayList, new TaskExecutionCallback() {
            public void onFinished(Map<String, Object> map) {
                jobService.jobFinished(jobParameters, false);
            }
        });
        return true;
    }

    public static boolean shouldUseForegroundService(Map<String, Object> map) {
        return map.containsKey(FOREGROUND_SERVICE_KEY);
    }

    private void startLocationUpdates() {
        Context context = getContext();
        if (context == null) {
            Log.w(TAG, "The context has been abandoned.");
        } else if (!LocationHelpers.isAnyProviderAvailable(context)) {
            Log.w(TAG, "There is no location provider available.");
        } else {
            this.mLocationRequest = LocationHelpers.prepareLocationRequest(this.mTask.getOptions());
            this.mPendingIntent = preparePendingIntent();
            try {
                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
                this.mLocationClient = fusedLocationProviderClient;
                fusedLocationProviderClient.requestLocationUpdates(this.mLocationRequest, this.mPendingIntent);
            } catch (SecurityException e) {
                Log.w(TAG, "Location request has been rejected.", e);
            }
        }
    }

    private void stopLocationUpdates() {
        PendingIntent pendingIntent;
        FusedLocationProviderClient fusedLocationProviderClient = this.mLocationClient;
        if (fusedLocationProviderClient != null && (pendingIntent = this.mPendingIntent) != null) {
            fusedLocationProviderClient.removeLocationUpdates(pendingIntent);
            this.mPendingIntent.cancel();
        }
    }

    private void maybeStartForegroundService() {
        if (Build.VERSION.SDK_INT >= 26) {
            MapArguments mapArguments = new MapArguments(this.mTask.getOptions());
            final Context context = getContext();
            boolean shouldUseForegroundService = shouldUseForegroundService(this.mTask.getOptions());
            if (context == null) {
                Log.w(TAG, "Context not found when trying to start foreground service.");
            } else if (this.mService != null && !shouldUseForegroundService) {
                stopForegroundService();
            } else if (shouldUseForegroundService) {
                LocationTaskService locationTaskService = this.mService;
                if (locationTaskService == null) {
                    Intent intent = new Intent(context, LocationTaskService.class);
                    Bundle bundle = new Bundle();
                    final Bundle bundle2 = mapArguments.getArguments(FOREGROUND_SERVICE_KEY).toBundle();
                    bundle.putString("appId", this.mTask.getAppId());
                    bundle.putString("taskName", this.mTask.getName());
                    intent.putExtras(bundle);
                    context.startForegroundService(intent);
                    context.bindService(intent, new ServiceConnection() {
                        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                            LocationTaskService unused = LocationTaskConsumer.this.mService = ((LocationTaskService.ServiceBinder) iBinder).getService();
                            LocationTaskConsumer.this.mService.setParentContext(context);
                            LocationTaskConsumer.this.mService.startForeground(bundle2);
                        }

                        public void onServiceDisconnected(ComponentName componentName) {
                            LocationTaskConsumer.this.mService.stop();
                            LocationTaskService unused = LocationTaskConsumer.this.mService = null;
                        }
                    }, 1);
                    return;
                }
                locationTaskService.startForeground(mapArguments.getArguments(FOREGROUND_SERVICE_KEY).toBundle());
            }
        }
    }

    private void stopForegroundService() {
        LocationTaskService locationTaskService = this.mService;
        if (locationTaskService != null) {
            locationTaskService.stop();
        }
    }

    /* access modifiers changed from: private */
    public void deferLocations(List<Location> list) {
        int size = this.mDeferredLocations.size();
        Location location = size > 0 ? this.mDeferredLocations.get(size - 1) : this.mLastReportedLocation;
        for (Location next : list) {
            if (location != null) {
                this.mDeferredDistance += (double) Math.abs(next.distanceTo(location));
            }
            location = next;
        }
        this.mDeferredLocations.addAll(list);
    }

    /* access modifiers changed from: private */
    public void maybeReportDeferredLocations() {
        if (shouldReportDeferredLocations()) {
            Context applicationContext = getContext().getApplicationContext();
            ArrayList arrayList = new ArrayList();
            for (Location next : this.mDeferredLocations) {
                long time = next.getTime();
                if (time > sLastTimestamp) {
                    arrayList.add((PersistableBundle) LocationHelpers.locationToBundle(next, PersistableBundle.class));
                    sLastTimestamp = time;
                }
            }
            if (arrayList.size() > 0) {
                List<Location> list = this.mDeferredLocations;
                this.mLastReportedLocation = list.get(list.size() - 1);
                this.mDeferredDistance = 0.0d;
                this.mDeferredLocations.clear();
                getTaskManagerUtils().scheduleJob(applicationContext, this.mTask, arrayList);
            }
        }
    }

    private boolean shouldReportDeferredLocations() {
        if (this.mDeferredLocations.size() == 0 || this.mTask == null) {
            return false;
        }
        if (!this.mIsHostPaused) {
            return true;
        }
        Location location = this.mLastReportedLocation;
        if (location == null) {
            location = this.mDeferredLocations.get(0);
        }
        List<Location> list = this.mDeferredLocations;
        MapHelper mapHelper = new MapHelper(this.mTask.getOptions());
        double d = mapHelper.getDouble("deferredUpdatesDistance");
        if (list.get(list.size() - 1).getTime() - location.getTime() < mapHelper.getLong("deferredUpdatesInterval") || this.mDeferredDistance < d) {
            return false;
        }
        return true;
    }

    private PendingIntent preparePendingIntent() {
        return getTaskManagerUtils().createTaskIntent(getContext(), this.mTask);
    }

    private void executeTaskWithLocationBundles(ArrayList<Bundle> arrayList, TaskExecutionCallback taskExecutionCallback) {
        if (arrayList.size() <= 0 || this.mTask == null) {
            taskExecutionCallback.onFinished((Map<String, Object>) null);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("locations", arrayList);
        this.mTask.execute(bundle, (Error) null, taskExecutionCallback);
    }

    public void onHostResume() {
        this.mIsHostPaused = false;
        maybeReportDeferredLocations();
    }

    public void onHostPause() {
        this.mIsHostPaused = true;
    }

    public void onHostDestroy() {
        this.mIsHostPaused = true;
    }
}
