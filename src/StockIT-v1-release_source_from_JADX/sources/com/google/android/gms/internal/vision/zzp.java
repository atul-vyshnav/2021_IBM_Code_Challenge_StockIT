package com.google.android.gms.internal.vision;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.vision.Frame;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
public final class zzp extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzp> CREATOR = new zzo();
    public int height;

    /* renamed from: id */
    public int f234id;
    public int rotation;
    public int width;
    public long zzar;

    public zzp() {
    }

    public zzp(int i, int i2, int i3, long j, int i4) {
        this.width = i;
        this.height = i2;
        this.f234id = i3;
        this.zzar = j;
        this.rotation = i4;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.width);
        SafeParcelWriter.writeInt(parcel, 3, this.height);
        SafeParcelWriter.writeInt(parcel, 4, this.f234id);
        SafeParcelWriter.writeLong(parcel, 5, this.zzar);
        SafeParcelWriter.writeInt(parcel, 6, this.rotation);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public static zzp zzc(Frame frame) {
        zzp zzp = new zzp();
        zzp.width = frame.getMetadata().getWidth();
        zzp.height = frame.getMetadata().getHeight();
        zzp.rotation = frame.getMetadata().getRotation();
        zzp.f234id = frame.getMetadata().getId();
        zzp.zzar = frame.getMetadata().getTimestampMillis();
        return zzp;
    }
}
