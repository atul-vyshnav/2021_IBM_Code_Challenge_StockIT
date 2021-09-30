package com.google.android.gms.internal.ads;

import java.math.BigInteger;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
public final class zzayg {
    private String zzdqu = "0";
    private BigInteger zzdzy = BigInteger.ONE;

    public final synchronized String zzwx() {
        String bigInteger;
        bigInteger = this.zzdzy.toString();
        this.zzdzy = this.zzdzy.add(BigInteger.ONE);
        this.zzdqu = bigInteger;
        return bigInteger;
    }

    public final synchronized String zzwy() {
        return this.zzdqu;
    }
}
