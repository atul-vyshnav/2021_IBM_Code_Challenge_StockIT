package com.google.android.play.core.review;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class ReviewInfo implements Parcelable {
    /* renamed from: a */
    public static ReviewInfo m877a(PendingIntent pendingIntent) {
        return new C2008a(pendingIntent);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public abstract PendingIntent mo34258a();

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mo34258a(), 0);
    }
}
