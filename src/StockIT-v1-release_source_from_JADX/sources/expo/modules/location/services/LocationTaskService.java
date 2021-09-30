package expo.modules.location.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.C1379C;
import com.onesignal.OneSignalDbContract;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class LocationTaskService extends Service {
    private static final String TAG = "LocationTaskService";
    private static int sServiceId = 481756;
    private final IBinder mBinder = new ServiceBinder();
    private String mChannelId;
    private Context mParentContext;
    private int mServiceId;

    public LocationTaskService() {
        int i = sServiceId;
        sServiceId = i + 1;
        this.mServiceId = i;
    }

    public class ServiceBinder extends Binder {
        public ServiceBinder() {
        }

        public LocationTaskService getService() {
            return LocationTaskService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        Log.w(TAG, "onBind");
        return this.mBinder;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return 3;
        }
        this.mChannelId = extras.getString("appId") + ParameterizedMessage.ERROR_MSG_SEPARATOR + extras.getString("taskName");
        return 3;
    }

    public void setParentContext(Context context) {
        this.mParentContext = context;
    }

    public void stop() {
        stopForeground(true);
        stopSelf();
    }

    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        stop();
    }

    public void startForeground(Bundle bundle) {
        startForeground(this.mServiceId, buildServiceNotification(bundle));
    }

    private Notification buildServiceNotification(Bundle bundle) {
        prepareChannel(this.mChannelId);
        Notification.Builder builder = new Notification.Builder(this, this.mChannelId);
        String string = bundle.getString("notificationTitle");
        String string2 = bundle.getString("notificationBody");
        Integer colorStringToInteger = colorStringToInteger(bundle.getString("notificationColor"));
        if (string != null) {
            builder.setContentTitle(string);
        }
        if (string2 != null) {
            builder.setContentText(string2);
        }
        if (colorStringToInteger != null) {
            builder.setColorized(true).setColor(colorStringToInteger.intValue());
        } else {
            builder.setColorized(false);
        }
        Intent launchIntentForPackage = this.mParentContext.getPackageManager().getLaunchIntentForPackage(this.mParentContext.getPackageName());
        if (launchIntentForPackage != null) {
            launchIntentForPackage.setFlags(C1379C.ENCODING_PCM_A_LAW);
            builder.setContentIntent(PendingIntent.getActivity(this, 0, launchIntentForPackage, 134217728));
        }
        return builder.setCategory(NotificationCompat.CATEGORY_SERVICE).setSmallIcon(getApplicationInfo().icon).build();
    }

    private void prepareChannel(String str) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(OneSignalDbContract.NotificationTable.TABLE_NAME);
        if (notificationManager != null) {
            String charSequence = getApplicationInfo().loadLabel(getPackageManager()).toString();
            if (notificationManager.getNotificationChannel(str) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(str, charSequence, 2);
                notificationChannel.setDescription("Background location notification channel");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    private Integer colorStringToInteger(String str) {
        try {
            return Integer.valueOf(Color.parseColor(str));
        } catch (Exception unused) {
            return null;
        }
    }
}
