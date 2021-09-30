package com.google.android.play.core.appupdate.testing;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.appupdate.C1752a;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

public class FakeAppUpdateManager implements AppUpdateManager {

    /* renamed from: a */
    private final C1752a f312a;

    /* renamed from: b */
    private final Context f313b;

    /* renamed from: c */
    private int f314c = 0;

    /* renamed from: d */
    private int f315d = 0;

    /* renamed from: e */
    private boolean f316e = false;

    /* renamed from: f */
    private int f317f = 0;

    /* renamed from: g */
    private Integer f318g = null;

    /* renamed from: h */
    private int f319h = 0;

    /* renamed from: i */
    private long f320i = 0;

    /* renamed from: j */
    private long f321j = 0;

    /* renamed from: k */
    private boolean f322k = false;

    /* renamed from: l */
    private boolean f323l = false;

    /* renamed from: m */
    private boolean f324m = false;

    /* renamed from: n */
    private Integer f325n;

    public FakeAppUpdateManager(Context context) {
        this.f312a = new C1752a(context);
        this.f313b = context;
    }

    /* renamed from: a */
    private final int m273a() {
        if (!this.f316e) {
            return 1;
        }
        int i = this.f314c;
        return (i == 0 || i == 4 || i == 5 || i == 6) ? 2 : 3;
    }

    /* renamed from: a */
    private final boolean m274a(AppUpdateInfo appUpdateInfo, AppUpdateOptions appUpdateOptions) {
        int i;
        if (!appUpdateInfo.isUpdateTypeAllowed(appUpdateOptions) && (!AppUpdateOptions.defaultOptions(appUpdateOptions.appUpdateType()).equals(appUpdateOptions) || !appUpdateInfo.isUpdateTypeAllowed(appUpdateOptions.appUpdateType()))) {
            return false;
        }
        if (appUpdateOptions.appUpdateType() == 1) {
            this.f323l = true;
            i = 1;
        } else {
            this.f322k = true;
            i = 0;
        }
        this.f325n = i;
        return true;
    }

    /* renamed from: b */
    private final void m275b() {
        this.f312a.mo34245a(InstallState.m600a(this.f314c, this.f320i, this.f321j, this.f315d, this.f313b.getPackageName()));
    }

    public Task<Void> completeUpdate() {
        int i = this.f315d;
        if (i != 0) {
            return Tasks.m1078a((Exception) new InstallException(i));
        }
        int i2 = this.f314c;
        if (i2 != 11) {
            return i2 == 3 ? Tasks.m1078a((Exception) new InstallException(-8)) : Tasks.m1078a((Exception) new InstallException(-7));
        }
        this.f314c = 3;
        this.f324m = true;
        Integer num = 0;
        if (num.equals(this.f325n)) {
            m275b();
        }
        return Tasks.m1079a(null);
    }

    public void downloadCompletes() {
        int i = this.f314c;
        if (i == 2 || i == 1) {
            this.f314c = 11;
            this.f320i = 0;
            this.f321j = 0;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
                return;
            }
            Integer num2 = 1;
            if (num2.equals(this.f325n)) {
                completeUpdate();
            }
        }
    }

    public void downloadFails() {
        int i = this.f314c;
        if (i == 1 || i == 2) {
            this.f314c = 5;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
            }
            this.f325n = null;
            this.f323l = false;
            this.f314c = 0;
        }
    }

    public void downloadStarts() {
        if (this.f314c == 1) {
            this.f314c = 2;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
            }
        }
    }

    public Task<AppUpdateInfo> getAppUpdateInfo() {
        PendingIntent pendingIntent;
        PendingIntent pendingIntent2;
        int i = this.f315d;
        if (i != 0) {
            return Tasks.m1078a((Exception) new InstallException(i));
        }
        PendingIntent broadcast = (m273a() == 2 && this.f315d == 0) ? PendingIntent.getBroadcast(this.f313b, 0, new Intent(), 0) : null;
        PendingIntent broadcast2 = (m273a() == 2 && this.f315d == 0) ? PendingIntent.getBroadcast(this.f313b, 0, new Intent(), 0) : null;
        if (m273a() == 2 && this.f315d == 0) {
            PendingIntent broadcast3 = PendingIntent.getBroadcast(this.f313b, 0, new Intent(), 0);
            pendingIntent2 = PendingIntent.getBroadcast(this.f313b, 0, new Intent(), 0);
            pendingIntent = broadcast3;
        } else {
            pendingIntent2 = null;
            pendingIntent = null;
        }
        return Tasks.m1079a(AppUpdateInfo.m230a(this.f313b.getPackageName(), this.f317f, m273a(), this.f314c, this.f318g, this.f319h, this.f320i, this.f321j, 0, 0, broadcast2, broadcast, pendingIntent2, pendingIntent));
    }

    public Integer getTypeForUpdateInProgress() {
        return this.f325n;
    }

    public void installCompletes() {
        if (this.f314c == 3) {
            this.f314c = 4;
            this.f316e = false;
            this.f317f = 0;
            this.f318g = null;
            this.f319h = 0;
            this.f320i = 0;
            this.f321j = 0;
            this.f323l = false;
            this.f324m = false;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
            }
            this.f325n = null;
            this.f314c = 0;
        }
    }

    public void installFails() {
        if (this.f314c == 3) {
            this.f314c = 5;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
            }
            this.f325n = null;
            this.f324m = false;
            this.f323l = false;
            this.f314c = 0;
        }
    }

    public boolean isConfirmationDialogVisible() {
        return this.f322k;
    }

    public boolean isImmediateFlowVisible() {
        return this.f323l;
    }

    public boolean isInstallSplashScreenVisible() {
        return this.f324m;
    }

    public void registerListener(InstallStateUpdatedListener installStateUpdatedListener) {
        this.f312a.mo34244a(installStateUpdatedListener);
    }

    public void setBytesDownloaded(long j) {
        if (this.f314c == 2 && j <= this.f321j) {
            this.f320i = j;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
            }
        }
    }

    public void setClientVersionStalenessDays(Integer num) {
        if (this.f316e) {
            this.f318g = num;
        }
    }

    public void setInstallErrorCode(int i) {
        this.f315d = i;
    }

    public void setTotalBytesToDownload(long j) {
        if (this.f314c == 2) {
            this.f321j = j;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
            }
        }
    }

    public void setUpdateAvailable(int i) {
        this.f316e = true;
        this.f317f = i;
    }

    public void setUpdateNotAvailable() {
        this.f316e = false;
        this.f318g = null;
    }

    public void setUpdatePriority(int i) {
        if (this.f316e) {
            this.f319h = i;
        }
    }

    public final Task<Integer> startUpdateFlow(AppUpdateInfo appUpdateInfo, Activity activity, AppUpdateOptions appUpdateOptions) {
        return m274a(appUpdateInfo, appUpdateOptions) ? Tasks.m1079a(-1) : Tasks.m1078a((Exception) new InstallException(-6));
    }

    public boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, int i, Activity activity, int i2) {
        return m274a(appUpdateInfo, AppUpdateOptions.newBuilder(i).build());
    }

    public boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, int i, IntentSenderForResultStarter intentSenderForResultStarter, int i2) {
        return m274a(appUpdateInfo, AppUpdateOptions.newBuilder(i).build());
    }

    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, Activity activity, AppUpdateOptions appUpdateOptions, int i) {
        return m274a(appUpdateInfo, appUpdateOptions);
    }

    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, IntentSenderForResultStarter intentSenderForResultStarter, AppUpdateOptions appUpdateOptions, int i) {
        return m274a(appUpdateInfo, appUpdateOptions);
    }

    public void unregisterListener(InstallStateUpdatedListener installStateUpdatedListener) {
        this.f312a.mo34247b(installStateUpdatedListener);
    }

    public void userAcceptsUpdate() {
        if (this.f322k || this.f323l) {
            this.f322k = false;
            this.f314c = 1;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
            }
        }
    }

    public void userCancelsDownload() {
        int i = this.f314c;
        if (i == 1 || i == 2) {
            this.f314c = 6;
            Integer num = 0;
            if (num.equals(this.f325n)) {
                m275b();
            }
            this.f325n = null;
            this.f323l = false;
            this.f314c = 0;
        }
    }

    public void userRejectsUpdate() {
        if (this.f322k || this.f323l) {
            this.f322k = false;
            this.f323l = false;
            this.f325n = null;
            this.f314c = 0;
        }
    }
}
