package com.google.android.play.core.assetpacks;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.play.core.assetpacks.bz */
final class C1830bz {

    /* renamed from: a */
    private final Map<String, Double> f548a = new HashMap();

    C1830bz() {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized double mo34024a(String str, C1849cr crVar) {
        double d;
        d = (((double) ((C1823bs) crVar).f507e) + 1.0d) / ((double) ((C1823bs) crVar).f508f);
        this.f548a.put(str, Double.valueOf(d));
        return d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized void mo34025a(String str) {
        this.f548a.put(str, Double.valueOf(0.0d));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final synchronized double mo34026b(String str) {
        Double d = this.f548a.get(str);
        if (d == null) {
            return 0.0d;
        }
        return d.doubleValue();
    }
}
