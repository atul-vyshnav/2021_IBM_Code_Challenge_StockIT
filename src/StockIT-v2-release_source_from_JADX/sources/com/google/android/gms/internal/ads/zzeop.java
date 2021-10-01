package com.google.android.gms.internal.ads;

import java.nio.ByteBuffer;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
public final class zzeop {
    public static final zzeop zziui = new zzeop(1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    private static final zzeop zziuj = new zzeop(0.0d, 1.0d, -1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    private static final zzeop zziuk = new zzeop(-1.0d, 0.0d, 0.0d, -1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    private static final zzeop zziul = new zzeop(0.0d, -1.0d, 1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);

    /* renamed from: a */
    private final double f224a;

    /* renamed from: b */
    private final double f225b;

    /* renamed from: c */
    private final double f226c;

    /* renamed from: d */
    private final double f227d;

    /* renamed from: w */
    private final double f228w;
    private final double zziue;
    private final double zziuf;
    private final double zziug;
    private final double zziuh;

    private zzeop(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        this.zziue = d5;
        this.zziuf = d6;
        this.f228w = d7;
        this.f224a = d;
        this.f225b = d2;
        this.f226c = d3;
        this.f227d = d4;
        this.zziug = d8;
        this.zziuh = d9;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzeop zzeop = (zzeop) obj;
        return Double.compare(zzeop.f224a, this.f224a) == 0 && Double.compare(zzeop.f225b, this.f225b) == 0 && Double.compare(zzeop.f226c, this.f226c) == 0 && Double.compare(zzeop.f227d, this.f227d) == 0 && Double.compare(zzeop.zziug, this.zziug) == 0 && Double.compare(zzeop.zziuh, this.zziuh) == 0 && Double.compare(zzeop.zziue, this.zziue) == 0 && Double.compare(zzeop.zziuf, this.zziuf) == 0 && Double.compare(zzeop.f228w, this.f228w) == 0;
    }

    public final int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zziue);
        long doubleToLongBits2 = Double.doubleToLongBits(this.zziuf);
        long doubleToLongBits3 = Double.doubleToLongBits(this.f228w);
        long doubleToLongBits4 = Double.doubleToLongBits(this.f224a);
        long doubleToLongBits5 = Double.doubleToLongBits(this.f225b);
        long doubleToLongBits6 = Double.doubleToLongBits(this.f226c);
        long doubleToLongBits7 = Double.doubleToLongBits(this.f227d);
        long doubleToLongBits8 = Double.doubleToLongBits(this.zziug);
        long doubleToLongBits9 = Double.doubleToLongBits(this.zziuh);
        return (((((((((((((((((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + ((int) (doubleToLongBits3 ^ (doubleToLongBits3 >>> 32)))) * 31) + ((int) (doubleToLongBits4 ^ (doubleToLongBits4 >>> 32)))) * 31) + ((int) (doubleToLongBits5 ^ (doubleToLongBits5 >>> 32)))) * 31) + ((int) (doubleToLongBits6 ^ (doubleToLongBits6 >>> 32)))) * 31) + ((int) (doubleToLongBits7 ^ (doubleToLongBits7 >>> 32)))) * 31) + ((int) (doubleToLongBits8 ^ (doubleToLongBits8 >>> 32)))) * 31) + ((int) (doubleToLongBits9 ^ (doubleToLongBits9 >>> 32)));
    }

    public final String toString() {
        if (equals(zziui)) {
            return "Rotate 0°";
        }
        if (equals(zziuj)) {
            return "Rotate 90°";
        }
        if (equals(zziuk)) {
            return "Rotate 180°";
        }
        if (equals(zziul)) {
            return "Rotate 270°";
        }
        double d = this.zziue;
        double d2 = this.zziuf;
        double d3 = this.f228w;
        double d4 = this.f224a;
        double d5 = this.f225b;
        double d6 = this.f226c;
        double d7 = this.f227d;
        double d8 = this.zziug;
        double d9 = this.zziuh;
        double d10 = d7;
        StringBuilder sb = new StringBuilder(260);
        sb.append("Matrix{u=");
        sb.append(d);
        sb.append(", v=");
        sb.append(d2);
        sb.append(", w=");
        sb.append(d3);
        sb.append(", a=");
        sb.append(d4);
        sb.append(", b=");
        sb.append(d5);
        sb.append(", c=");
        sb.append(d6);
        sb.append(", d=");
        sb.append(d10);
        sb.append(", tx=");
        sb.append(d8);
        sb.append(", ty=");
        sb.append(d9);
        sb.append("}");
        return sb.toString();
    }

    public static zzeop zzs(ByteBuffer byteBuffer) {
        double zzi = zzbq.zzi(byteBuffer);
        double zzi2 = zzbq.zzi(byteBuffer);
        double zzj = zzbq.zzj(byteBuffer);
        return new zzeop(zzi, zzi2, zzbq.zzi(byteBuffer), zzbq.zzi(byteBuffer), zzj, zzbq.zzj(byteBuffer), zzbq.zzj(byteBuffer), zzbq.zzi(byteBuffer), zzbq.zzi(byteBuffer));
    }
}
