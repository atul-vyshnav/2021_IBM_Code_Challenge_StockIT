package com.google.android.play.core.internal;

import android.util.Log;
import java.io.File;

/* renamed from: com.google.android.play.core.internal.bj */
final class C1949bj implements C1937ay {
    C1949bj() {
    }

    /* renamed from: a */
    public final boolean mo34161a(Object obj, File file, File file2) {
        try {
            return !((Boolean) C1956bq.m704a((Class) Class.forName("dalvik.system.DexFile"), Boolean.class, String.class, file.getPath())).booleanValue();
        } catch (ClassNotFoundException unused) {
            Log.e("SplitCompat", "Unexpected missing dalvik.system.DexFile.");
            return false;
        }
    }
}
