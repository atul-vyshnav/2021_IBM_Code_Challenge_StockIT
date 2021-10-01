package com.google.android.play.core.assetpacks;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: com.google.android.play.core.assetpacks.cx */
final /* synthetic */ class C1855cx implements FilenameFilter {

    /* renamed from: a */
    private final String f615a;

    C1855cx(String str) {
        this.f615a = str;
    }

    public final boolean accept(File file, String str) {
        return str.startsWith(String.valueOf(this.f615a).concat("-")) && str.endsWith(".apk");
    }
}
