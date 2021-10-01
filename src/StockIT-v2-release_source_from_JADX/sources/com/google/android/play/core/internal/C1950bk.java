package com.google.android.play.core.internal;

import com.RNFetchBlob.RNFetchBlobConst;
import java.io.File;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.bk */
final class C1950bk implements C1933au {
    C1950bk() {
    }

    /* renamed from: b */
    static void m688b(ClassLoader classLoader, Set<File> set) {
        C1945bf.m677a(classLoader, set, new C1948bi());
    }

    /* renamed from: b */
    static boolean m689b(ClassLoader classLoader, File file, File file2, boolean z) {
        return C1940ba.m666a(classLoader, file, file2, z, C1945bf.m676a(), RNFetchBlobConst.RNFB_RESPONSE_PATH, new C1949bj());
    }

    /* renamed from: a */
    public final void mo34158a(ClassLoader classLoader, Set<File> set) {
        m688b(classLoader, set);
    }

    /* renamed from: a */
    public final boolean mo34159a(ClassLoader classLoader, File file, File file2, boolean z) {
        return m689b(classLoader, file, file2, z);
    }
}
