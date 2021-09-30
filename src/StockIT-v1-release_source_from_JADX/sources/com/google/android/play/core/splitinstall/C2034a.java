package com.google.android.play.core.splitinstall;

import android.app.PendingIntent;
import android.content.Intent;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.a */
final class C2034a extends SplitInstallSessionState {

    /* renamed from: a */
    private final int f904a;

    /* renamed from: b */
    private final int f905b;

    /* renamed from: c */
    private final int f906c;

    /* renamed from: d */
    private final long f907d;

    /* renamed from: e */
    private final long f908e;

    /* renamed from: f */
    private final List<String> f909f;

    /* renamed from: g */
    private final List<String> f910g;

    /* renamed from: h */
    private final PendingIntent f911h;

    /* renamed from: i */
    private final List<Intent> f912i;

    C2034a(int i, int i2, int i3, long j, long j2, List<String> list, List<String> list2, PendingIntent pendingIntent, List<Intent> list3) {
        this.f904a = i;
        this.f905b = i2;
        this.f906c = i3;
        this.f907d = j;
        this.f908e = j2;
        this.f909f = list;
        this.f910g = list2;
        this.f911h = pendingIntent;
        this.f912i = list3;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final List<String> mo34318a() {
        return this.f909f;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final List<String> mo34319b() {
        return this.f910g;
    }

    public final long bytesDownloaded() {
        return this.f907d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final List<Intent> mo34321c() {
        return this.f912i;
    }

    public final boolean equals(Object obj) {
        List<String> list;
        List<String> list2;
        PendingIntent pendingIntent;
        if (obj == this) {
            return true;
        }
        if (obj instanceof SplitInstallSessionState) {
            SplitInstallSessionState splitInstallSessionState = (SplitInstallSessionState) obj;
            if (this.f904a == splitInstallSessionState.sessionId() && this.f905b == splitInstallSessionState.status() && this.f906c == splitInstallSessionState.errorCode() && this.f907d == splitInstallSessionState.bytesDownloaded() && this.f908e == splitInstallSessionState.totalBytesToDownload() && ((list = this.f909f) != null ? list.equals(splitInstallSessionState.mo34318a()) : splitInstallSessionState.mo34318a() == null) && ((list2 = this.f910g) != null ? list2.equals(splitInstallSessionState.mo34319b()) : splitInstallSessionState.mo34319b() == null) && ((pendingIntent = this.f911h) != null ? pendingIntent.equals(splitInstallSessionState.resolutionIntent()) : splitInstallSessionState.resolutionIntent() == null)) {
                List<Intent> list3 = this.f912i;
                List<Intent> c = splitInstallSessionState.mo34321c();
                if (list3 != null ? list3.equals(c) : c == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int errorCode() {
        return this.f906c;
    }

    public final int hashCode() {
        int i = this.f904a;
        int i2 = this.f905b;
        int i3 = this.f906c;
        long j = this.f907d;
        long j2 = this.f908e;
        int i4 = (((((((((i ^ 1000003) * 1000003) ^ i2) * 1000003) ^ i3) * 1000003) ^ ((int) ((j >>> 32) ^ j))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003;
        List<String> list = this.f909f;
        int i5 = 0;
        int hashCode = (i4 ^ (list == null ? 0 : list.hashCode())) * 1000003;
        List<String> list2 = this.f910g;
        int hashCode2 = (hashCode ^ (list2 == null ? 0 : list2.hashCode())) * 1000003;
        PendingIntent pendingIntent = this.f911h;
        int hashCode3 = (hashCode2 ^ (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 1000003;
        List<Intent> list3 = this.f912i;
        if (list3 != null) {
            i5 = list3.hashCode();
        }
        return hashCode3 ^ i5;
    }

    @Deprecated
    public final PendingIntent resolutionIntent() {
        return this.f911h;
    }

    public final int sessionId() {
        return this.f904a;
    }

    public final int status() {
        return this.f905b;
    }

    public final String toString() {
        int i = this.f904a;
        int i2 = this.f905b;
        int i3 = this.f906c;
        long j = this.f907d;
        long j2 = this.f908e;
        String valueOf = String.valueOf(this.f909f);
        String valueOf2 = String.valueOf(this.f910g);
        String valueOf3 = String.valueOf(this.f911h);
        String valueOf4 = String.valueOf(this.f912i);
        int length = String.valueOf(valueOf).length();
        int length2 = String.valueOf(valueOf2).length();
        StringBuilder sb = new StringBuilder(length + 251 + length2 + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length());
        sb.append("SplitInstallSessionState{sessionId=");
        sb.append(i);
        sb.append(", status=");
        sb.append(i2);
        sb.append(", errorCode=");
        sb.append(i3);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", moduleNamesNullable=");
        sb.append(valueOf);
        sb.append(", languagesNullable=");
        sb.append(valueOf2);
        sb.append(", resolutionIntent=");
        sb.append(valueOf3);
        sb.append(", splitFileIntents=");
        sb.append(valueOf4);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytesToDownload() {
        return this.f908e;
    }
}
