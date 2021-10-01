package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import com.google.android.play.core.internal.C1918af;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitinstall.p */
public final class C2076p {

    /* renamed from: a */
    private static final C1918af f978a = new C1918af("SplitInstallInfoProvider");

    /* renamed from: b */
    private final Context f979b;

    /* renamed from: c */
    private final String f980c;

    public C2076p(Context context) {
        this.f979b = context;
        this.f980c = context.getPackageName();
    }

    public C2076p(Context context, String str) {
        this.f979b = context;
        this.f980c = str;
    }

    /* renamed from: a */
    public static boolean m1024a(String str) {
        return str.startsWith("config.");
    }

    /* renamed from: b */
    public static boolean m1025b(String str) {
        return m1024a(str) || str.contains(".config.");
    }

    /* renamed from: d */
    private final Set<String> m1026d() {
        HashSet hashSet = new HashSet();
        Bundle e = m1027e();
        if (e != null) {
            String string = e.getString("com.android.dynamic.apk.fused.modules");
            if (string == null || string.isEmpty()) {
                f978a.mo34139a("App has no fused modules.", new Object[0]);
            } else {
                Collections.addAll(hashSet, string.split(",", -1));
                hashSet.remove("");
                hashSet.remove("base");
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            String[] strArr = null;
            try {
                PackageInfo packageInfo = this.f979b.getPackageManager().getPackageInfo(this.f980c, 0);
                if (packageInfo != null) {
                    strArr = packageInfo.splitNames;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                f978a.mo34143d("App is not found in PackageManager", new Object[0]);
            }
            if (strArr != null) {
                f978a.mo34139a("Adding splits from package manager: %s", Arrays.toString(strArr));
                Collections.addAll(hashSet, strArr);
            } else {
                f978a.mo34139a("No splits are found or app cannot be found in package manager.", new Object[0]);
            }
            C2074n a = C2075o.m1022a();
            if (a != null) {
                hashSet.addAll(a.mo34289a());
            }
        }
        return hashSet;
    }

    /* renamed from: e */
    private final Bundle m1027e() {
        try {
            ApplicationInfo applicationInfo = this.f979b.getPackageManager().getApplicationInfo(this.f980c, 128);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                return applicationInfo.metaData;
            }
            f978a.mo34139a("App has no applicationInfo or metaData", new Object[0]);
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            f978a.mo34143d("App is not found in PackageManager", new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    public final Set<String> mo34356a() {
        HashSet hashSet = new HashSet();
        for (String next : m1026d()) {
            if (!m1025b(next)) {
                hashSet.add(next);
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final Set<String> mo34357b() {
        C2067h c = mo34358c();
        if (c == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Set<String> d = m1026d();
        d.add("");
        Set<String> a = mo34356a();
        a.add("");
        for (Map.Entry next : c.mo34354a(a).entrySet()) {
            if (d.containsAll((Collection) next.getValue())) {
                hashSet.add((String) next.getKey());
            }
        }
        return hashSet;
    }

    /* renamed from: c */
    public final C2067h mo34358c() {
        Bundle e = m1027e();
        if (e == null) {
            f978a.mo34143d("No metadata found in Context.", new Object[0]);
            return null;
        }
        int i = e.getInt("com.android.vending.splits");
        if (i == 0) {
            f978a.mo34143d("No metadata found in AndroidManifest.", new Object[0]);
            return null;
        }
        try {
            C2067h a = new C2060az(this.f979b.getResources().getXml(i)).mo34343a();
            if (a == null) {
                f978a.mo34143d("Can't parse languages metadata.", new Object[0]);
            }
            return a;
        } catch (Resources.NotFoundException unused) {
            f978a.mo34143d("Resource with languages metadata doesn't exist.", new Object[0]);
            return null;
        }
    }
}
