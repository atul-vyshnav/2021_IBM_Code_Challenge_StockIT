package com.google.android.play.core.appupdate;

import android.app.PendingIntent;

public abstract class AppUpdateInfo {
    /* renamed from: a */
    public static AppUpdateInfo m230a(String str, int i, int i2, int i3, Integer num, int i4, long j, long j2, long j3, long j4, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3, PendingIntent pendingIntent4) {
        return new C1771t(str, i, i2, i3, num, i4, j, j2, j3, j4, pendingIntent, pendingIntent2, pendingIntent3, pendingIntent4);
    }

    /* renamed from: b */
    private final boolean m231b(AppUpdateOptions appUpdateOptions) {
        return appUpdateOptions.allowAssetPackDeletion() && mo33823a() <= mo33826b();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public abstract long mo33823a();

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final PendingIntent mo33824a(AppUpdateOptions appUpdateOptions) {
        if (appUpdateOptions.appUpdateType() != 0) {
            if (appUpdateOptions.appUpdateType() == 1) {
                if (mo33828c() != null) {
                    return mo33828c();
                }
                if (m231b(appUpdateOptions)) {
                    return mo33831e();
                }
            }
            return null;
        } else if (mo33830d() != null) {
            return mo33830d();
        } else {
            if (m231b(appUpdateOptions)) {
                return mo33832f();
            }
            return null;
        }
    }

    public abstract int availableVersionCode();

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public abstract long mo33826b();

    public abstract long bytesDownloaded();

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public abstract PendingIntent mo33828c();

    public abstract Integer clientVersionStalenessDays();

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public abstract PendingIntent mo33830d();

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public abstract PendingIntent mo33831e();

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public abstract PendingIntent mo33832f();

    public abstract int installStatus();

    public boolean isUpdateTypeAllowed(int i) {
        return mo33824a(AppUpdateOptions.defaultOptions(i)) != null;
    }

    public boolean isUpdateTypeAllowed(AppUpdateOptions appUpdateOptions) {
        return mo33824a(appUpdateOptions) != null;
    }

    public abstract String packageName();

    public abstract long totalBytesToDownload();

    public abstract int updateAvailability();

    public abstract int updatePriority();
}
