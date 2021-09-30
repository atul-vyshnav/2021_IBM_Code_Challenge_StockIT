package com.google.android.play.core.assetpacks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1976cj;
import com.google.android.play.core.internal.C1985h;
import com.google.android.play.core.splitinstall.C2076p;
import com.google.android.play.core.tasks.C2106i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.j */
final class C1888j implements AssetPackManager {

    /* renamed from: a */
    private static final C1918af f720a = new C1918af("AssetPackManager");

    /* renamed from: b */
    private final C1806bb f721b;

    /* renamed from: c */
    private final C1976cj<C1902w> f722c;

    /* renamed from: d */
    private final C1800aw f723d;

    /* renamed from: e */
    private final C2076p f724e;

    /* renamed from: f */
    private final C1847cp f725f;

    /* renamed from: g */
    private final C1830bz f726g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public final C1818bn f727h;

    /* renamed from: i */
    private final C1976cj<Executor> f728i;

    /* renamed from: j */
    private final Handler f729j = new Handler(Looper.getMainLooper());

    /* renamed from: k */
    private boolean f730k;

    C1888j(C1806bb bbVar, C1976cj<C1902w> cjVar, C1800aw awVar, C2076p pVar, C1847cp cpVar, C1830bz bzVar, C1818bn bnVar, C1976cj<Executor> cjVar2) {
        this.f721b = bbVar;
        this.f722c = cjVar;
        this.f723d = awVar;
        this.f724e = pVar;
        this.f725f = cpVar;
        this.f726g = bzVar;
        this.f727h = bnVar;
        this.f728i = cjVar2;
    }

    /* renamed from: c */
    private final void m556c() {
        this.f728i.mo34195a().execute(new C1883e(this));
    }

    /* renamed from: d */
    private final void m557d() {
        this.f728i.mo34195a().execute(new C1884f(this));
        this.f730k = true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final int mo34108a(int i, String str) {
        if (!this.f721b.mo33968a(str) && i == 4) {
            return 8;
        }
        if (!this.f721b.mo33968a(str) || i == 4) {
            return i;
        }
        return 4;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34109a() {
        this.f721b.mo33979d();
        this.f721b.mo33976c();
        this.f721b.mo33984e();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34110a(String str, C2106i iVar) {
        if (this.f721b.mo33980d(str)) {
            iVar.mo34396a(null);
            this.f722c.mo34195a().mo33949a(str);
            return;
        }
        iVar.mo34395a((Exception) new IOException(String.format("Failed to remove pack %s.", new Object[]{str})));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34111a(boolean z) {
        boolean b = this.f723d.mo34248b();
        this.f723d.mo34246a(z);
        if (z && !b) {
            m556c();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ void mo34112b() {
        C1806bb bbVar = this.f721b;
        bbVar.getClass();
        this.f722c.mo34195a().mo33944a(this.f721b.mo33972b()).addOnSuccessListener(this.f728i.mo34195a(), C1885g.m553a(bbVar)).addOnFailureListener(this.f728i.mo34195a(), C1886h.f717a);
    }

    public final AssetPackStates cancel(List<String> list) {
        Map<String, Integer> a = this.f725f.mo34029a(list);
        HashMap hashMap = new HashMap();
        for (String next : list) {
            Integer num = a.get(next);
            hashMap.put(next, AssetPackState.m287a(next, num == null ? 0 : num.intValue(), 0, 0, 0, 0.0d));
        }
        this.f722c.mo34195a().mo33950a(list);
        return AssetPackStates.m288a(0, (Map<String, AssetPackState>) hashMap);
    }

    public final void clearListeners() {
        this.f723d.mo34243a();
    }

    public final Task<AssetPackStates> fetch(List<String> list) {
        Map<String, Long> b = this.f721b.mo33972b();
        ArrayList arrayList = new ArrayList(list);
        arrayList.removeAll(b.keySet());
        ArrayList arrayList2 = new ArrayList(list);
        arrayList2.removeAll(arrayList);
        if (!arrayList.isEmpty()) {
            return this.f722c.mo34195a().mo33943a((List<String>) arrayList2, (List<String>) arrayList, b);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("session_id", 0);
        bundle.putInt("error_code", 0);
        for (String next : list) {
            bundle.putInt(C1985h.m783a("status", next), 4);
            bundle.putInt(C1985h.m783a("error_code", next), 0);
            bundle.putLong(C1985h.m783a("total_bytes_to_download", next), 0);
            bundle.putLong(C1985h.m783a("bytes_downloaded", next), 0);
        }
        bundle.putStringArrayList("pack_names", new ArrayList(list));
        bundle.putLong("total_bytes_to_download", 0);
        bundle.putLong("bytes_downloaded", 0);
        return Tasks.m1079a(AssetPackStates.m289a(bundle, this.f726g));
    }

    public final AssetLocation getAssetLocation(String str, String str2) {
        AssetPackLocation assetPackLocation;
        if (!this.f730k) {
            this.f728i.mo34195a().execute(new C1884f(this));
            this.f730k = true;
        }
        if (this.f721b.mo33968a(str)) {
            try {
                assetPackLocation = this.f721b.mo33969b(str);
            } catch (IOException unused) {
            }
        } else {
            if (this.f724e.mo34356a().contains(str)) {
                assetPackLocation = AssetPackLocation.m284a();
            }
            assetPackLocation = null;
        }
        if (assetPackLocation == null) {
            return null;
        }
        if (assetPackLocation.packStorageMethod() == 1) {
            return this.f721b.mo33961a(str, str2);
        }
        if (assetPackLocation.packStorageMethod() == 0) {
            return this.f721b.mo33962a(str, str2, assetPackLocation);
        }
        f720a.mo34139a("The asset %s is not present in Asset Pack %s", str2, str);
        return null;
    }

    public final AssetPackLocation getPackLocation(String str) {
        if (!this.f730k) {
            m557d();
        }
        if (this.f721b.mo33968a(str)) {
            try {
                return this.f721b.mo33969b(str);
            } catch (IOException unused) {
                return null;
            }
        } else if (this.f724e.mo34356a().contains(str)) {
            return AssetPackLocation.m284a();
        } else {
            return null;
        }
    }

    public final Map<String, AssetPackLocation> getPackLocations() {
        Map<String, AssetPackLocation> a = this.f721b.mo33965a();
        HashMap hashMap = new HashMap();
        for (String put : this.f724e.mo34356a()) {
            hashMap.put(put, AssetPackLocation.m284a());
        }
        a.putAll(hashMap);
        return a;
    }

    public final Task<AssetPackStates> getPackStates(List<String> list) {
        return this.f722c.mo34195a().mo33942a(list, (C1803az) new C1831c(this), this.f721b.mo33972b());
    }

    public final synchronized void registerListener(AssetPackStateUpdateListener assetPackStateUpdateListener) {
        boolean b = this.f723d.mo34248b();
        this.f723d.mo34244a(assetPackStateUpdateListener);
        if (!b) {
            m556c();
        }
    }

    public final Task<Void> removePack(String str) {
        C2106i iVar = new C2106i();
        this.f728i.mo34195a().execute(new C1858d(this, str, iVar));
        return iVar.mo34394a();
    }

    public final Task<Integer> showCellularDataConfirmation(Activity activity) {
        if (this.f727h.mo34014a() == null) {
            return Tasks.m1078a((Exception) new AssetPackException(-12));
        }
        Intent intent = new Intent(activity, PlayCoreDialogWrapperActivity.class);
        intent.putExtra("confirmation_intent", this.f727h.mo34014a());
        C2106i iVar = new C2106i();
        intent.putExtra("result_receiver", new C1887i(this, this.f729j, iVar));
        activity.startActivity(intent);
        return iVar.mo34394a();
    }

    public final void unregisterListener(AssetPackStateUpdateListener assetPackStateUpdateListener) {
        this.f723d.mo34247b(assetPackStateUpdateListener);
    }
}
