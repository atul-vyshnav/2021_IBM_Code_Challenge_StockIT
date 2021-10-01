package com.google.android.play.core.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: com.google.android.play.core.internal.k */
public final class C1988k {
    static {
        C1988k.class.getClassLoader();
    }

    private C1988k() {
    }

    /* renamed from: a */
    public static <T extends Parcelable> T m802a(Parcel parcel, Parcelable.Creator<T> creator) {
        if (parcel.readInt() == 0) {
            return null;
        }
        return (Parcelable) creator.createFromParcel(parcel);
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [android.os.IInterface, android.os.IBinder] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m803a(android.os.Parcel r0, android.os.IInterface r1) {
        /*
            r0.writeStrongBinder(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.internal.C1988k.m803a(android.os.Parcel, android.os.IInterface):void");
    }

    /* renamed from: a */
    public static void m804a(Parcel parcel, Parcelable parcelable) {
        if (parcelable == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcelable.writeToParcel(parcel, 0);
    }
}
