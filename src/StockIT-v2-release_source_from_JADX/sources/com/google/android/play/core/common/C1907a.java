package com.google.android.play.core.common;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.play.core.common.a */
public final class C1907a {

    /* renamed from: a */
    private final Map<String, Object> f761a = new HashMap();

    /* renamed from: a */
    public final synchronized void mo34120a(Bundle bundle) {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null && this.f761a.get(str) == null) {
                this.f761a.put(str, obj);
            }
        }
    }

    /* renamed from: a */
    public final synchronized boolean mo34121a() {
        Object obj = this.f761a.get("usingExtractorStream");
        if (!(obj instanceof Boolean)) {
            return false;
        }
        return ((Boolean) obj).booleanValue();
    }
}
