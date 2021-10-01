package com.google.android.gms.internal.ads;

import kotlin.text.Typography;

/* compiled from: com.google.android.gms:play-services-gass@@19.4.0 */
public enum zzgo implements zzeke {
    UNSUPPORTED(0),
    ARM7(2),
    X86(4),
    ARM64(5),
    X86_64(6);
    
    private static final zzekd<zzgo> zzes = null;
    private final int value;

    public final int zzv() {
        return this.value;
    }

    public final String toString() {
        return "<" + getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.value + " name=" + name() + Typography.greater;
    }

    private zzgo(int i) {
        this.value = i;
    }

    static {
        zzes = new zzgn();
    }
}
