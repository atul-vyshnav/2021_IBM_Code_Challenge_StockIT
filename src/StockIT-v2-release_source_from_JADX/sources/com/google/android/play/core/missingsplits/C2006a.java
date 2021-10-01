package com.google.android.play.core.missingsplits;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.play.core.internal.C1918af;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.android.play.core.missingsplits.a */
final class C2006a {

    /* renamed from: a */
    private static final C1918af f843a = new C1918af("MissingSplitsAppComponentsHelper");

    /* renamed from: b */
    private final Context f844b;

    /* renamed from: c */
    private final PackageManager f845c;

    C2006a(Context context, PackageManager packageManager) {
        this.f844b = context;
        this.f845c = packageManager;
    }

    /* renamed from: a */
    private final void m868a(List<ComponentInfo> list, int i) {
        for (ComponentInfo next : list) {
            this.f845c.setComponentEnabledSetting(new ComponentName(next.packageName, next.name), i, 1);
        }
    }

    /* renamed from: d */
    private final List<ComponentInfo> m869d() {
        try {
            ArrayList arrayList = new ArrayList();
            PackageInfo packageInfo = this.f845c.getPackageInfo(this.f844b.getPackageName(), 526);
            if (packageInfo.providers != null) {
                Collections.addAll(arrayList, packageInfo.providers);
            }
            if (packageInfo.receivers != null) {
                Collections.addAll(arrayList, packageInfo.receivers);
            }
            if (packageInfo.services != null) {
                Collections.addAll(arrayList, packageInfo.services);
            }
            return arrayList;
        } catch (PackageManager.NameNotFoundException e) {
            f843a.mo34143d("Failed to resolve own package : %s", e);
            return Collections.emptyList();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo34255a() {
        for (ComponentInfo next : m869d()) {
            if (this.f845c.getComponentEnabledSetting(new ComponentName(next.packageName, next.name)) != 2) {
                f843a.mo34139a("Not all non-activity components are disabled", new Object[0]);
                return false;
            }
        }
        f843a.mo34139a("All non-activity components are disabled", new Object[0]);
        return true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo34256b() {
        f843a.mo34142c("Disabling all non-activity components", new Object[0]);
        m868a(m869d(), 2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final void mo34257c() {
        f843a.mo34142c("Resetting enabled state of all non-activity components", new Object[0]);
        m868a(m869d(), 0);
    }
}
