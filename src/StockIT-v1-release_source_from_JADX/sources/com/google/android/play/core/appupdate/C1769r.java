package com.google.android.play.core.appupdate;

import android.content.Context;
import java.io.File;

/* renamed from: com.google.android.play.core.appupdate.r */
final class C1769r {

    /* renamed from: a */
    private final Context f296a;

    C1769r(Context context) {
        this.f296a = context;
    }

    /* renamed from: a */
    private static long m264a(File file) {
        if (!file.isDirectory()) {
            return file.length();
        }
        File[] listFiles = file.listFiles();
        long j = 0;
        if (listFiles != null) {
            for (File a : listFiles) {
                j += m264a(a);
            }
        }
        return j;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final long mo33866a() {
        return m264a(new File(this.f296a.getFilesDir(), "assetpacks"));
    }
}
