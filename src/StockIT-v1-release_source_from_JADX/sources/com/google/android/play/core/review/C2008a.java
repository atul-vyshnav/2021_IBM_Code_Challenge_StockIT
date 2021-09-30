package com.google.android.play.core.review;

import android.app.PendingIntent;

/* renamed from: com.google.android.play.core.review.a */
final class C2008a extends ReviewInfo {

    /* renamed from: a */
    private final PendingIntent f851a;

    C2008a(PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            this.f851a = pendingIntent;
            return;
        }
        throw new NullPointerException("Null pendingIntent");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final PendingIntent mo34258a() {
        return this.f851a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ReviewInfo) {
            return this.f851a.equals(((ReviewInfo) obj).mo34258a());
        }
        return false;
    }

    public final int hashCode() {
        return this.f851a.hashCode() ^ 1000003;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.f851a);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 26);
        sb.append("ReviewInfo{pendingIntent=");
        sb.append(valueOf);
        sb.append("}");
        return sb.toString();
    }
}
