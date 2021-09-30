package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.internal.C1985h;

public abstract class AssetPackState {
    /* renamed from: a */
    static AssetPackState m286a(Bundle bundle, String str, C1830bz bzVar, C1803az azVar) {
        return m287a(str, azVar.mo33958a(bundle.getInt(C1985h.m783a("status", str)), str), bundle.getInt(C1985h.m783a("error_code", str)), bundle.getLong(C1985h.m783a("bytes_downloaded", str)), bundle.getLong(C1985h.m783a("total_bytes_to_download", str)), bzVar.mo34026b(str));
    }

    /* renamed from: a */
    public static AssetPackState m287a(String str, int i, int i2, long j, long j2, double d) {
        return new C1812bh(str, i, i2, j, j2, (int) Math.rint(100.0d * d));
    }

    public abstract long bytesDownloaded();

    public abstract int errorCode();

    public abstract String name();

    public abstract int status();

    public abstract long totalBytesToDownload();

    public abstract int transferProgressPercentage();
}
