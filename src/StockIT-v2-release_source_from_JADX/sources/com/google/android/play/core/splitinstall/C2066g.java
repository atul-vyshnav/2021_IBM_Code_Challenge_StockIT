package com.google.android.play.core.splitinstall;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.play.core.splitinstall.g */
public final class C2066g {

    /* renamed from: a */
    private final Map<String, Map<String, String>> f964a = new HashMap();

    /* renamed from: a */
    public final C2067h mo34352a() {
        HashMap hashMap = new HashMap();
        for (Map.Entry next : this.f964a.entrySet()) {
            hashMap.put((String) next.getKey(), Collections.unmodifiableMap(new HashMap((Map) next.getValue())));
        }
        return new C2067h(Collections.unmodifiableMap(hashMap));
    }

    /* renamed from: a */
    public final void mo34353a(String str, String str2, String str3) {
        if (!this.f964a.containsKey(str2)) {
            this.f964a.put(str2, new HashMap());
        }
        this.f964a.get(str2).put(str, str3);
    }
}
