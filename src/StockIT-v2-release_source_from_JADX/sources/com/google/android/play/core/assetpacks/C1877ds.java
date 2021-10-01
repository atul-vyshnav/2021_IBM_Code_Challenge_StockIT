package com.google.android.play.core.assetpacks;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: com.google.android.play.core.assetpacks.ds */
final /* synthetic */ class C1877ds implements FilenameFilter {

    /* renamed from: a */
    static final FilenameFilter f699a = new C1877ds();

    private C1877ds() {
    }

    public final boolean accept(File file, String str) {
        return C1878dt.f700a.matcher(str).matches();
    }
}
