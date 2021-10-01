package com.google.android.play.core.assetpacks;

import java.util.Map;

/* renamed from: com.google.android.play.core.assetpacks.bi */
final class C1813bi extends AssetPackStates {

    /* renamed from: a */
    private final long f463a;

    /* renamed from: b */
    private final Map<String, AssetPackState> f464b;

    C1813bi(long j, Map<String, AssetPackState> map) {
        this.f463a = j;
        this.f464b = map;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackStates) {
            AssetPackStates assetPackStates = (AssetPackStates) obj;
            return this.f463a == assetPackStates.totalBytes() && this.f464b.equals(assetPackStates.packStates());
        }
    }

    public final int hashCode() {
        long j = this.f463a;
        return this.f464b.hashCode() ^ ((((int) (j ^ (j >>> 32))) ^ 1000003) * 1000003);
    }

    public final Map<String, AssetPackState> packStates() {
        return this.f464b;
    }

    public final String toString() {
        long j = this.f463a;
        String valueOf = String.valueOf(this.f464b);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 61);
        sb.append("AssetPackStates{totalBytes=");
        sb.append(j);
        sb.append(", packStates=");
        sb.append(valueOf);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytes() {
        return this.f463a;
    }
}
