package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import java.io.File;

/* renamed from: com.google.android.play.core.splitinstall.y */
public final class C2096y {

    /* renamed from: a */
    private final Context f1055a;

    C2096y(Context context) {
        this.f1055a = context;
    }

    /* renamed from: a */
    static File m1073a(Context context) {
        String string;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle == null || (string = bundle.getString("local_testing_dir")) == null) {
                return null;
            }
            return new File(context.getExternalFilesDir((String) null), string);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final C2080t mo34372a() {
        return C2080t.m1037a(this.f1055a);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final Context mo34373b() {
        return this.f1055a;
    }
}
