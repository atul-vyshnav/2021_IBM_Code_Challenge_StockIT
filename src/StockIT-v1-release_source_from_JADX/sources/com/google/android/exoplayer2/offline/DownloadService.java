package com.google.android.exoplayer2.offline;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.HashMap;

public abstract class DownloadService extends Service {
    public static final String ACTION_ADD = "com.google.android.exoplayer.downloadService.action.ADD";
    public static final String ACTION_INIT = "com.google.android.exoplayer.downloadService.action.INIT";
    public static final String ACTION_RELOAD_REQUIREMENTS = "com.google.android.exoplayer.downloadService.action.RELOAD_REQUIREMENTS";
    private static final String ACTION_RESTART = "com.google.android.exoplayer.downloadService.action.RESTART";
    private static final boolean DEBUG = false;
    public static final long DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL = 1000;
    private static final Requirements DEFAULT_REQUIREMENTS = new Requirements(1, false, false);
    public static final int FOREGROUND_NOTIFICATION_ID_NONE = 0;
    public static final String KEY_DOWNLOAD_ACTION = "download_action";
    public static final String KEY_FOREGROUND = "foreground";
    private static final String TAG = "DownloadService";
    private static final HashMap<Class<? extends DownloadService>, RequirementsHelper> requirementsHelpers = new HashMap<>();
    private final String channelId;
    private final int channelName;
    /* access modifiers changed from: private */
    public DownloadManager downloadManager;
    private DownloadManagerListener downloadManagerListener;
    /* access modifiers changed from: private */
    public final ForegroundNotificationUpdater foregroundNotificationUpdater;
    private int lastStartId;
    private boolean startedInForeground;
    private boolean taskRemoved;

    private void logd(String str) {
    }

    /* access modifiers changed from: protected */
    public abstract DownloadManager getDownloadManager();

    /* access modifiers changed from: protected */
    public abstract Scheduler getScheduler();

    public IBinder onBind(Intent intent) {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onTaskStateChanged(DownloadManager.TaskState taskState) {
    }

    protected DownloadService(int i) {
        this(i, 1000);
    }

    protected DownloadService(int i, long j) {
        this(i, j, (String) null, 0);
    }

    protected DownloadService(int i, long j, String str, int i2) {
        ForegroundNotificationUpdater foregroundNotificationUpdater2;
        if (i == 0) {
            foregroundNotificationUpdater2 = null;
        } else {
            foregroundNotificationUpdater2 = new ForegroundNotificationUpdater(i, j);
        }
        this.foregroundNotificationUpdater = foregroundNotificationUpdater2;
        this.channelId = str;
        this.channelName = i2;
    }

    public static Intent buildAddActionIntent(Context context, Class<? extends DownloadService> cls, DownloadAction downloadAction, boolean z) {
        return getIntent(context, cls, ACTION_ADD).putExtra(KEY_DOWNLOAD_ACTION, downloadAction.toByteArray()).putExtra(KEY_FOREGROUND, z);
    }

    public static void startWithAction(Context context, Class<? extends DownloadService> cls, DownloadAction downloadAction, boolean z) {
        Intent buildAddActionIntent = buildAddActionIntent(context, cls, downloadAction, z);
        if (z) {
            Util.startForegroundService(context, buildAddActionIntent);
        } else {
            context.startService(buildAddActionIntent);
        }
    }

    public static void start(Context context, Class<? extends DownloadService> cls) {
        context.startService(getIntent(context, cls, ACTION_INIT));
    }

    public static void startForeground(Context context, Class<? extends DownloadService> cls) {
        Util.startForegroundService(context, getIntent(context, cls, ACTION_INIT).putExtra(KEY_FOREGROUND, true));
    }

    public void onCreate() {
        logd("onCreate");
        String str = this.channelId;
        if (str != null) {
            NotificationUtil.createNotificationChannel(this, str, this.channelName, 2);
        }
        this.downloadManager = getDownloadManager();
        DownloadManagerListener downloadManagerListener2 = new DownloadManagerListener();
        this.downloadManagerListener = downloadManagerListener2;
        this.downloadManager.addListener(downloadManagerListener2);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0057, code lost:
        if (r2.equals(ACTION_INIT) != false) goto L_0x0077;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int onStartCommand(android.content.Intent r8, int r9, int r10) {
        /*
            r7 = this;
            r7.lastStartId = r10
            r9 = 0
            r7.taskRemoved = r9
            java.lang.String r0 = "com.google.android.exoplayer.downloadService.action.RESTART"
            r1 = 1
            if (r8 == 0) goto L_0x0026
            java.lang.String r2 = r8.getAction()
            boolean r3 = r7.startedInForeground
            java.lang.String r4 = "foreground"
            boolean r4 = r8.getBooleanExtra(r4, r9)
            if (r4 != 0) goto L_0x0021
            boolean r4 = r0.equals(r2)
            if (r4 == 0) goto L_0x001f
            goto L_0x0021
        L_0x001f:
            r4 = 0
            goto L_0x0022
        L_0x0021:
            r4 = 1
        L_0x0022:
            r3 = r3 | r4
            r7.startedInForeground = r3
            goto L_0x0027
        L_0x0026:
            r2 = 0
        L_0x0027:
            java.lang.String r3 = "com.google.android.exoplayer.downloadService.action.INIT"
            if (r2 != 0) goto L_0x002c
            r2 = r3
        L_0x002c:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "onStartCommand action: "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r5 = " startId: "
            r4.append(r5)
            r4.append(r10)
            java.lang.String r10 = r4.toString()
            r7.logd(r10)
            r10 = -1
            int r4 = r2.hashCode()
            r5 = 3
            r6 = 2
            switch(r4) {
                case -871181424: goto L_0x006e;
                case -608867945: goto L_0x0064;
                case -382886238: goto L_0x005a;
                case 1015676687: goto L_0x0053;
                default: goto L_0x0052;
            }
        L_0x0052:
            goto L_0x0076
        L_0x0053:
            boolean r0 = r2.equals(r3)
            if (r0 == 0) goto L_0x0076
            goto L_0x0077
        L_0x005a:
            java.lang.String r9 = "com.google.android.exoplayer.downloadService.action.ADD"
            boolean r9 = r2.equals(r9)
            if (r9 == 0) goto L_0x0076
            r9 = 2
            goto L_0x0077
        L_0x0064:
            java.lang.String r9 = "com.google.android.exoplayer.downloadService.action.RELOAD_REQUIREMENTS"
            boolean r9 = r2.equals(r9)
            if (r9 == 0) goto L_0x0076
            r9 = 3
            goto L_0x0077
        L_0x006e:
            boolean r9 = r2.equals(r0)
            if (r9 == 0) goto L_0x0076
            r9 = 1
            goto L_0x0077
        L_0x0076:
            r9 = -1
        L_0x0077:
            if (r9 == 0) goto L_0x00b4
            if (r9 == r1) goto L_0x00b4
            java.lang.String r10 = "DownloadService"
            if (r9 == r6) goto L_0x009a
            if (r9 == r5) goto L_0x0096
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Ignoring unrecognized action: "
            r8.append(r9)
            r8.append(r2)
            java.lang.String r8 = r8.toString()
            com.google.android.exoplayer2.util.Log.m169e(r10, r8)
            goto L_0x00b4
        L_0x0096:
            r7.stopWatchingRequirements()
            goto L_0x00b4
        L_0x009a:
            java.lang.String r9 = "download_action"
            byte[] r8 = r8.getByteArrayExtra(r9)
            if (r8 != 0) goto L_0x00a8
            java.lang.String r8 = "Ignoring ADD action with no action data"
            com.google.android.exoplayer2.util.Log.m169e(r10, r8)
            goto L_0x00b4
        L_0x00a8:
            com.google.android.exoplayer2.offline.DownloadManager r9 = r7.downloadManager     // Catch:{ IOException -> 0x00ae }
            r9.handleAction((byte[]) r8)     // Catch:{ IOException -> 0x00ae }
            goto L_0x00b4
        L_0x00ae:
            r8 = move-exception
            java.lang.String r9 = "Failed to handle ADD action"
            com.google.android.exoplayer2.util.Log.m170e(r10, r9, r8)
        L_0x00b4:
            com.google.android.exoplayer2.scheduler.Requirements r8 = r7.getRequirements()
            boolean r9 = r8.checkRequirements(r7)
            if (r9 == 0) goto L_0x00c4
            com.google.android.exoplayer2.offline.DownloadManager r9 = r7.downloadManager
            r9.startDownloads()
            goto L_0x00c9
        L_0x00c4:
            com.google.android.exoplayer2.offline.DownloadManager r9 = r7.downloadManager
            r9.stopDownloads()
        L_0x00c9:
            r7.maybeStartWatchingRequirements(r8)
            com.google.android.exoplayer2.offline.DownloadManager r8 = r7.downloadManager
            boolean r8 = r8.isIdle()
            if (r8 == 0) goto L_0x00d7
            r7.stop()
        L_0x00d7:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadService.onStartCommand(android.content.Intent, int, int):int");
    }

    public void onTaskRemoved(Intent intent) {
        logd("onTaskRemoved rootIntent: " + intent);
        this.taskRemoved = true;
    }

    public void onDestroy() {
        logd("onDestroy");
        ForegroundNotificationUpdater foregroundNotificationUpdater2 = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater2 != null) {
            foregroundNotificationUpdater2.stopPeriodicUpdates();
        }
        this.downloadManager.removeListener(this.downloadManagerListener);
        maybeStopWatchingRequirements();
    }

    /* access modifiers changed from: protected */
    public Requirements getRequirements() {
        return DEFAULT_REQUIREMENTS;
    }

    /* access modifiers changed from: protected */
    public Notification getForegroundNotification(DownloadManager.TaskState[] taskStateArr) {
        throw new IllegalStateException(getClass().getName() + " is started in the foreground but getForegroundNotification() is not implemented.");
    }

    /* access modifiers changed from: private */
    public void maybeStartWatchingRequirements(Requirements requirements) {
        if (this.downloadManager.getDownloadCount() != 0) {
            Class<?> cls = getClass();
            if (requirementsHelpers.get(cls) == null) {
                RequirementsHelper requirementsHelper = new RequirementsHelper(this, requirements, getScheduler(), cls);
                requirementsHelpers.put(cls, requirementsHelper);
                requirementsHelper.start();
                logd("started watching requirements");
            }
        }
    }

    private void maybeStopWatchingRequirements() {
        if (this.downloadManager.getDownloadCount() <= 0) {
            stopWatchingRequirements();
        }
    }

    private void stopWatchingRequirements() {
        RequirementsHelper remove = requirementsHelpers.remove(getClass());
        if (remove != null) {
            remove.stop();
            logd("stopped watching requirements");
        }
    }

    /* access modifiers changed from: private */
    public void stop() {
        ForegroundNotificationUpdater foregroundNotificationUpdater2 = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater2 != null) {
            foregroundNotificationUpdater2.stopPeriodicUpdates();
            if (this.startedInForeground && Util.SDK_INT >= 26) {
                this.foregroundNotificationUpdater.showNotificationIfNotAlready();
            }
        }
        if (Util.SDK_INT >= 28 || !this.taskRemoved) {
            boolean stopSelfResult = stopSelfResult(this.lastStartId);
            logd("stopSelf(" + this.lastStartId + ") result: " + stopSelfResult);
            return;
        }
        stopSelf();
        logd("stopSelf()");
    }

    /* access modifiers changed from: private */
    public static Intent getIntent(Context context, Class<? extends DownloadService> cls, String str) {
        return new Intent(context, cls).setAction(str);
    }

    private final class DownloadManagerListener implements DownloadManager.Listener {
        private DownloadManagerListener() {
        }

        public void onInitialized(DownloadManager downloadManager) {
            DownloadService downloadService = DownloadService.this;
            downloadService.maybeStartWatchingRequirements(downloadService.getRequirements());
        }

        public void onTaskStateChanged(DownloadManager downloadManager, DownloadManager.TaskState taskState) {
            DownloadService.this.onTaskStateChanged(taskState);
            if (DownloadService.this.foregroundNotificationUpdater == null) {
                return;
            }
            if (taskState.state == 1) {
                DownloadService.this.foregroundNotificationUpdater.startPeriodicUpdates();
            } else {
                DownloadService.this.foregroundNotificationUpdater.update();
            }
        }

        public final void onIdle(DownloadManager downloadManager) {
            DownloadService.this.stop();
        }
    }

    private final class ForegroundNotificationUpdater implements Runnable {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private boolean notificationDisplayed;
        private final int notificationId;
        private boolean periodicUpdatesStarted;
        private final long updateInterval;

        public ForegroundNotificationUpdater(int i, long j) {
            this.notificationId = i;
            this.updateInterval = j;
        }

        public void startPeriodicUpdates() {
            this.periodicUpdatesStarted = true;
            update();
        }

        public void stopPeriodicUpdates() {
            this.periodicUpdatesStarted = false;
            this.handler.removeCallbacks(this);
        }

        public void update() {
            DownloadManager.TaskState[] allTaskStates = DownloadService.this.downloadManager.getAllTaskStates();
            DownloadService downloadService = DownloadService.this;
            downloadService.startForeground(this.notificationId, downloadService.getForegroundNotification(allTaskStates));
            this.notificationDisplayed = true;
            if (this.periodicUpdatesStarted) {
                this.handler.removeCallbacks(this);
                this.handler.postDelayed(this, this.updateInterval);
            }
        }

        public void showNotificationIfNotAlready() {
            if (!this.notificationDisplayed) {
                update();
            }
        }

        public void run() {
            update();
        }
    }

    private static final class RequirementsHelper implements RequirementsWatcher.Listener {
        private final Context context;
        private final Requirements requirements;
        private final RequirementsWatcher requirementsWatcher;
        private final Scheduler scheduler;
        private final Class<? extends DownloadService> serviceClass;

        private RequirementsHelper(Context context2, Requirements requirements2, Scheduler scheduler2, Class<? extends DownloadService> cls) {
            this.context = context2;
            this.requirements = requirements2;
            this.scheduler = scheduler2;
            this.serviceClass = cls;
            this.requirementsWatcher = new RequirementsWatcher(context2, this, requirements2);
        }

        public void start() {
            this.requirementsWatcher.start();
        }

        public void stop() {
            this.requirementsWatcher.stop();
            Scheduler scheduler2 = this.scheduler;
            if (scheduler2 != null) {
                scheduler2.cancel();
            }
        }

        public void requirementsMet(RequirementsWatcher requirementsWatcher2) {
            try {
                notifyService();
                Scheduler scheduler2 = this.scheduler;
                if (scheduler2 != null) {
                    scheduler2.cancel();
                }
            } catch (Exception unused) {
            }
        }

        public void requirementsNotMet(RequirementsWatcher requirementsWatcher2) {
            try {
                notifyService();
            } catch (Exception unused) {
            }
            if (this.scheduler != null) {
                if (!this.scheduler.schedule(this.requirements, this.context.getPackageName(), DownloadService.ACTION_RESTART)) {
                    Log.m169e(DownloadService.TAG, "Scheduling downloads failed.");
                }
            }
        }

        private void notifyService() throws Exception {
            try {
                this.context.startService(DownloadService.getIntent(this.context, this.serviceClass, DownloadService.ACTION_INIT));
            } catch (IllegalStateException e) {
                throw new Exception(e);
            }
        }
    }
}
