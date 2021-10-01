package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1928ap;
import com.google.android.play.core.internal.C1964by;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.splitcompat.C2032p;
import com.google.android.play.core.tasks.C2106i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.android.play.core.assetpacks.ar */
final class C1795ar implements C1902w {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static final C1918af f399a = new C1918af("AssetPackServiceImpl");

    /* renamed from: b */
    private static final Intent f400b = new Intent("com.google.android.play.core.assetmoduleservice.BIND_ASSET_MODULE_SERVICE").setPackage("com.android.vending");
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final String f401c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final C1830bz f402d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public C1928ap<C1996s> f403e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public C1928ap<C1996s> f404f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public final AtomicBoolean f405g = new AtomicBoolean();

    C1795ar(Context context, C1830bz bzVar) {
        this.f401c = context.getPackageName();
        this.f402d = bzVar;
        if (C1964by.m751a(context)) {
            this.f403e = new C1928ap(C2032p.m933a(context), f399a, "AssetPackService", f400b, C1903x.f753a);
            this.f404f = new C1928ap(C2032p.m933a(context), f399a, "AssetPackService-keepAlive", f400b, C1904y.f754a);
        }
        f399a.mo34139a("AssetPackService initiated.", new Object[0]);
    }

    /* renamed from: a */
    static /* synthetic */ ArrayList m325a(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Bundle bundle = new Bundle();
            bundle.putString("module_name", (String) it.next());
            arrayList.add(bundle);
        }
        return arrayList;
    }

    /* renamed from: a */
    static /* synthetic */ List m326a(C1795ar arVar, List list) {
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            AssetPackState next = AssetPackStates.m289a((Bundle) list.get(i), arVar.f402d).packStates().values().iterator().next();
            if (next == null) {
                f399a.mo34141b("onGetSessionStates: Bundle contained no pack.", new Object[0]);
            }
            if (C1860db.m497a(next.status())) {
                arrayList.add(next.name());
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public final void m327a(int i, String str, int i2) {
        if (this.f403e != null) {
            f399a.mo34142c("notifyModuleCompleted", new Object[0]);
            C2106i iVar = new C2106i();
            this.f403e.mo34151a((C1919ag) new C1784ag(this, iVar, i, str, iVar, i2));
            return;
        }
        throw new C1826bv("The Play Store app is not installed or is an unofficial version.", i);
    }

    /* renamed from: b */
    static /* synthetic */ Bundle m331b(Map map) {
        Bundle e = m341e();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            Bundle bundle = new Bundle();
            bundle.putString("installed_asset_module_name", (String) entry.getKey());
            bundle.putLong("installed_asset_module_version", ((Long) entry.getValue()).longValue());
            arrayList.add(bundle);
        }
        e.putParcelableArrayList("installed_asset_module", arrayList);
        return e;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public static Bundle m335c(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("session_id", i);
        return bundle;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public static Bundle m336c(int i, String str) {
        Bundle c = m335c(i);
        c.putString("module_name", str);
        return c;
    }

    /* renamed from: c */
    static /* synthetic */ Bundle m337c(int i, String str, String str2, int i2) {
        Bundle c = m336c(i, str);
        c.putString("slice_id", str2);
        c.putInt("chunk_number", i2);
        return c;
    }

    /* renamed from: d */
    private static <T> Task<T> m340d() {
        f399a.mo34141b("onError(%d)", -11);
        return Tasks.m1078a((Exception) new AssetPackException(-11));
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public static Bundle m341e() {
        Bundle bundle = new Bundle();
        bundle.putInt("playcore_version_code", 10800);
        ArrayList arrayList = new ArrayList();
        arrayList.add(0);
        arrayList.add(1);
        bundle.putIntegerArrayList("supported_compression_formats", arrayList);
        bundle.putIntegerArrayList("supported_patch_formats", new ArrayList());
        return bundle;
    }

    /* renamed from: a */
    public final Task<AssetPackStates> mo33942a(List<String> list, C1803az azVar, Map<String, Long> map) {
        if (this.f403e == null) {
            return m340d();
        }
        f399a.mo34142c("getPackStates(%s)", list);
        C2106i iVar = new C2106i();
        this.f403e.mo34151a((C1919ag) new C1782ae(this, iVar, list, map, iVar, azVar));
        return iVar.mo34394a();
    }

    /* renamed from: a */
    public final Task<AssetPackStates> mo33943a(List<String> list, List<String> list2, Map<String, Long> map) {
        if (this.f403e == null) {
            return m340d();
        }
        f399a.mo34142c("startDownload(%s)", list2);
        C2106i iVar = new C2106i();
        this.f403e.mo34151a((C1919ag) new C1779ab(this, iVar, list2, map, iVar, list));
        iVar.mo34394a().addOnSuccessListener(new C1905z(this));
        return iVar.mo34394a();
    }

    /* renamed from: a */
    public final Task<List<String>> mo33944a(Map<String, Long> map) {
        if (this.f403e == null) {
            return m340d();
        }
        f399a.mo34142c("syncPacks", new Object[0]);
        C2106i iVar = new C2106i();
        this.f403e.mo34151a((C1919ag) new C1781ad(this, iVar, map, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: a */
    public final synchronized void mo33945a() {
        if (this.f404f == null) {
            f399a.mo34143d("Keep alive connection manager is not initialized.", new Object[0]);
            return;
        }
        f399a.mo34142c("keepAlive", new Object[0]);
        if (!this.f405g.compareAndSet(false, true)) {
            f399a.mo34142c("Service is already kept alive.", new Object[0]);
            return;
        }
        C2106i iVar = new C2106i();
        this.f404f.mo34151a((C1919ag) new C1787aj(this, iVar, iVar));
    }

    /* renamed from: a */
    public final void mo33946a(int i) {
        if (this.f403e != null) {
            f399a.mo34142c("notifySessionFailed", new Object[0]);
            C2106i iVar = new C2106i();
            this.f403e.mo34151a((C1919ag) new C1785ah(this, iVar, i, iVar));
            return;
        }
        throw new C1826bv("The Play Store app is not installed or is an unofficial version.", i);
    }

    /* renamed from: a */
    public final void mo33947a(int i, String str) {
        m327a(i, str, 10);
    }

    /* renamed from: a */
    public final void mo33948a(int i, String str, String str2, int i2) {
        if (this.f403e != null) {
            f399a.mo34142c("notifyChunkTransferred", new Object[0]);
            C2106i iVar = new C2106i();
            this.f403e.mo34151a((C1919ag) new C1783af(this, iVar, i, str, str2, i2, iVar));
            return;
        }
        throw new C1826bv("The Play Store app is not installed or is an unofficial version.", i);
    }

    /* renamed from: a */
    public final void mo33949a(String str) {
        if (this.f403e != null) {
            f399a.mo34142c("removePack(%s)", str);
            C2106i iVar = new C2106i();
            this.f403e.mo34151a((C1919ag) new C1778aa(this, iVar, str, iVar));
        }
    }

    /* renamed from: a */
    public final void mo33950a(List<String> list) {
        if (this.f403e != null) {
            f399a.mo34142c("cancelDownloads(%s)", list);
            C2106i iVar = new C2106i();
            this.f403e.mo34151a((C1919ag) new C1780ac(this, iVar, list, iVar));
        }
    }

    /* renamed from: b */
    public final Task<ParcelFileDescriptor> mo33951b(int i, String str, String str2, int i2) {
        if (this.f403e == null) {
            return m340d();
        }
        f399a.mo34142c("getChunkFileDescriptor(%s, %s, %d, session=%d)", str, str2, Integer.valueOf(i2), Integer.valueOf(i));
        C2106i iVar = new C2106i();
        this.f403e.mo34151a((C1919ag) new C1786ai(this, iVar, i, str, str2, i2, iVar));
        return iVar.mo34394a();
    }
}
