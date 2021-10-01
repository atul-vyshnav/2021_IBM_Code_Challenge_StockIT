package com.google.android.play.core.appupdate;

import android.app.PendingIntent;

/* renamed from: com.google.android.play.core.appupdate.t */
final class C1771t extends AppUpdateInfo {

    /* renamed from: a */
    private final String f298a;

    /* renamed from: b */
    private final int f299b;

    /* renamed from: c */
    private final int f300c;

    /* renamed from: d */
    private final int f301d;

    /* renamed from: e */
    private final Integer f302e;

    /* renamed from: f */
    private final int f303f;

    /* renamed from: g */
    private final long f304g;

    /* renamed from: h */
    private final long f305h;

    /* renamed from: i */
    private final long f306i;

    /* renamed from: j */
    private final long f307j;

    /* renamed from: k */
    private final PendingIntent f308k;

    /* renamed from: l */
    private final PendingIntent f309l;

    /* renamed from: m */
    private final PendingIntent f310m;

    /* renamed from: n */
    private final PendingIntent f311n;

    C1771t(String str, int i, int i2, int i3, Integer num, int i4, long j, long j2, long j3, long j4, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3, PendingIntent pendingIntent4) {
        String str2 = str;
        if (str2 != null) {
            this.f298a = str2;
            this.f299b = i;
            this.f300c = i2;
            this.f301d = i3;
            this.f302e = num;
            this.f303f = i4;
            this.f304g = j;
            this.f305h = j2;
            this.f306i = j3;
            this.f307j = j4;
            this.f308k = pendingIntent;
            this.f309l = pendingIntent2;
            this.f310m = pendingIntent3;
            this.f311n = pendingIntent4;
            return;
        }
        throw new NullPointerException("Null packageName");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final long mo33823a() {
        return this.f306i;
    }

    public final int availableVersionCode() {
        return this.f299b;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final long mo33826b() {
        return this.f307j;
    }

    public final long bytesDownloaded() {
        return this.f304g;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final PendingIntent mo33828c() {
        return this.f308k;
    }

    public final Integer clientVersionStalenessDays() {
        return this.f302e;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final PendingIntent mo33830d() {
        return this.f309l;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final PendingIntent mo33831e() {
        return this.f310m;
    }

    public final boolean equals(Object obj) {
        Integer num;
        PendingIntent pendingIntent;
        PendingIntent pendingIntent2;
        PendingIntent pendingIntent3;
        if (obj == this) {
            return true;
        }
        if (obj instanceof AppUpdateInfo) {
            AppUpdateInfo appUpdateInfo = (AppUpdateInfo) obj;
            if (this.f298a.equals(appUpdateInfo.packageName()) && this.f299b == appUpdateInfo.availableVersionCode() && this.f300c == appUpdateInfo.updateAvailability() && this.f301d == appUpdateInfo.installStatus() && ((num = this.f302e) != null ? num.equals(appUpdateInfo.clientVersionStalenessDays()) : appUpdateInfo.clientVersionStalenessDays() == null) && this.f303f == appUpdateInfo.updatePriority() && this.f304g == appUpdateInfo.bytesDownloaded() && this.f305h == appUpdateInfo.totalBytesToDownload() && this.f306i == appUpdateInfo.mo33823a() && this.f307j == appUpdateInfo.mo33826b() && ((pendingIntent = this.f308k) != null ? pendingIntent.equals(appUpdateInfo.mo33828c()) : appUpdateInfo.mo33828c() == null) && ((pendingIntent2 = this.f309l) != null ? pendingIntent2.equals(appUpdateInfo.mo33830d()) : appUpdateInfo.mo33830d() == null) && ((pendingIntent3 = this.f310m) != null ? pendingIntent3.equals(appUpdateInfo.mo33831e()) : appUpdateInfo.mo33831e() == null)) {
                PendingIntent pendingIntent4 = this.f311n;
                PendingIntent f = appUpdateInfo.mo33832f();
                if (pendingIntent4 != null ? pendingIntent4.equals(f) : f == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final PendingIntent mo33832f() {
        return this.f311n;
    }

    public final int hashCode() {
        int hashCode = (((((((this.f298a.hashCode() ^ 1000003) * 1000003) ^ this.f299b) * 1000003) ^ this.f300c) * 1000003) ^ this.f301d) * 1000003;
        Integer num = this.f302e;
        int i = 0;
        int hashCode2 = num == null ? 0 : num.hashCode();
        int i2 = this.f303f;
        long j = this.f304g;
        long j2 = this.f305h;
        long j3 = this.f306i;
        long j4 = this.f307j;
        int i3 = (((((((((((hashCode ^ hashCode2) * 1000003) ^ i2) * 1000003) ^ ((int) ((j >>> 32) ^ j))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ ((int) ((j3 >>> 32) ^ j3))) * 1000003) ^ ((int) ((j4 >>> 32) ^ j4))) * 1000003;
        PendingIntent pendingIntent = this.f308k;
        int hashCode3 = (i3 ^ (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 1000003;
        PendingIntent pendingIntent2 = this.f309l;
        int hashCode4 = (hashCode3 ^ (pendingIntent2 == null ? 0 : pendingIntent2.hashCode())) * 1000003;
        PendingIntent pendingIntent3 = this.f310m;
        int hashCode5 = (hashCode4 ^ (pendingIntent3 == null ? 0 : pendingIntent3.hashCode())) * 1000003;
        PendingIntent pendingIntent4 = this.f311n;
        if (pendingIntent4 != null) {
            i = pendingIntent4.hashCode();
        }
        return hashCode5 ^ i;
    }

    public final int installStatus() {
        return this.f301d;
    }

    public final String packageName() {
        return this.f298a;
    }

    public final String toString() {
        String str = this.f298a;
        int i = this.f299b;
        int i2 = this.f300c;
        int i3 = this.f301d;
        String valueOf = String.valueOf(this.f302e);
        int i4 = this.f303f;
        long j = this.f304g;
        long j2 = this.f305h;
        long j3 = this.f306i;
        long j4 = this.f307j;
        String valueOf2 = String.valueOf(this.f308k);
        long j5 = j4;
        String valueOf3 = String.valueOf(this.f309l);
        String valueOf4 = String.valueOf(this.f310m);
        long j6 = j3;
        String valueOf5 = String.valueOf(this.f311n);
        int length = str.length();
        int length2 = String.valueOf(valueOf).length();
        int length3 = String.valueOf(valueOf2).length();
        int length4 = String.valueOf(valueOf3).length();
        StringBuilder sb = new StringBuilder(length + 463 + length2 + length3 + length4 + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length());
        sb.append("AppUpdateInfo{packageName=");
        sb.append(str);
        sb.append(", availableVersionCode=");
        sb.append(i);
        sb.append(", updateAvailability=");
        sb.append(i2);
        sb.append(", installStatus=");
        sb.append(i3);
        sb.append(", clientVersionStalenessDays=");
        sb.append(valueOf);
        sb.append(", updatePriority=");
        sb.append(i4);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", additionalSpaceRequired=");
        sb.append(j6);
        sb.append(", assetPackStorageSize=");
        sb.append(j5);
        sb.append(", immediateUpdateIntent=");
        sb.append(valueOf2);
        sb.append(", flexibleUpdateIntent=");
        sb.append(valueOf3);
        sb.append(", immediateDestructiveUpdateIntent=");
        sb.append(valueOf4);
        sb.append(", flexibleDestructiveUpdateIntent=");
        sb.append(valueOf5);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytesToDownload() {
        return this.f305h;
    }

    public final int updateAvailability() {
        return this.f300c;
    }

    public final int updatePriority() {
        return this.f303f;
    }
}
