package com.google.android.play.core.assetpacks;

import android.os.ParcelFileDescriptor;
import com.google.android.play.core.tasks.Task;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.play.core.assetpacks.w */
interface C1902w {
    /* renamed from: a */
    Task<AssetPackStates> mo33942a(List<String> list, C1803az azVar, Map<String, Long> map);

    /* renamed from: a */
    Task<AssetPackStates> mo33943a(List<String> list, List<String> list2, Map<String, Long> map);

    /* renamed from: a */
    Task<List<String>> mo33944a(Map<String, Long> map);

    /* renamed from: a */
    void mo33945a();

    /* renamed from: a */
    void mo33946a(int i);

    /* renamed from: a */
    void mo33947a(int i, String str);

    /* renamed from: a */
    void mo33948a(int i, String str, String str2, int i2);

    /* renamed from: a */
    void mo33949a(String str);

    /* renamed from: a */
    void mo33950a(List<String> list);

    /* renamed from: b */
    Task<ParcelFileDescriptor> mo33951b(int i, String str, String str2, int i2);
}
