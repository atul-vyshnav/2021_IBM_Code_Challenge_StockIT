package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AssetPackStates {
    /* renamed from: a */
    public static AssetPackStates m288a(long j, Map<String, AssetPackState> map) {
        return new C1813bi(j, map);
    }

    /* renamed from: a */
    public static AssetPackStates m289a(Bundle bundle, C1830bz bzVar) {
        return m291a(bundle, bzVar, (List<String>) new ArrayList());
    }

    /* renamed from: a */
    public static AssetPackStates m290a(Bundle bundle, C1830bz bzVar, C1803az azVar) {
        return m292a(bundle, bzVar, new ArrayList(), azVar);
    }

    /* renamed from: a */
    public static AssetPackStates m291a(Bundle bundle, C1830bz bzVar, List<String> list) {
        return m292a(bundle, bzVar, list, C1805ba.f438a);
    }

    /* renamed from: a */
    private static AssetPackStates m292a(Bundle bundle, C1830bz bzVar, List<String> list, C1803az azVar) {
        Bundle bundle2 = bundle;
        ArrayList<String> stringArrayList = bundle.getStringArrayList("pack_names");
        HashMap hashMap = new HashMap();
        int size = stringArrayList.size();
        for (int i = 0; i < size; i++) {
            String str = stringArrayList.get(i);
            hashMap.put(str, AssetPackState.m286a(bundle, str, bzVar, azVar));
        }
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
            String str2 = list.get(i2);
            hashMap.put(str2, AssetPackState.m287a(str2, 4, 0, 0, 0, 0.0d));
        }
        return m288a(bundle.getLong("total_bytes_to_download"), (Map<String, AssetPackState>) hashMap);
    }

    public abstract Map<String, AssetPackState> packStates();

    public abstract long totalBytes();
}
