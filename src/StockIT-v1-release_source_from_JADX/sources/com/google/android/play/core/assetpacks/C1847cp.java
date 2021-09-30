package com.google.android.play.core.assetpacks;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1976cj;
import com.google.android.play.core.internal.C1985h;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.google.android.play.core.assetpacks.cp */
final class C1847cp {

    /* renamed from: a */
    private static final C1918af f584a = new C1918af("ExtractorSessionStoreView");

    /* renamed from: b */
    private final C1806bb f585b;

    /* renamed from: c */
    private final C1976cj<C1902w> f586c;

    /* renamed from: d */
    private final C1830bz f587d;

    /* renamed from: e */
    private final C1976cj<Executor> f588e;

    /* renamed from: f */
    private final Map<Integer, C1844cm> f589f = new HashMap();

    /* renamed from: g */
    private final ReentrantLock f590g = new ReentrantLock();

    C1847cp(C1806bb bbVar, C1976cj<C1902w> cjVar, C1830bz bzVar, C1976cj<Executor> cjVar2) {
        this.f585b = bbVar;
        this.f586c = cjVar;
        this.f587d = bzVar;
        this.f588e = cjVar2;
    }

    /* renamed from: a */
    private final <T> T m446a(C1846co<T> coVar) {
        try {
            mo34030a();
            return coVar.mo34027a();
        } finally {
            mo34035b();
        }
    }

    /* renamed from: d */
    private final Map<String, C1844cm> m447d(List<String> list) {
        return (Map) m446a(new C1837cf(this, list));
    }

    /* renamed from: e */
    private final C1844cm m448e(int i) {
        Map<Integer, C1844cm> map = this.f589f;
        Integer valueOf = Integer.valueOf(i);
        C1844cm cmVar = map.get(valueOf);
        if (cmVar != null) {
            return cmVar;
        }
        throw new C1826bv(String.format("Could not find session %d while trying to get it", new Object[]{valueOf}), i);
    }

    /* renamed from: e */
    private static String m449e(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList("pack_names");
        if (stringArrayList != null && !stringArrayList.isEmpty()) {
            return stringArrayList.get(0);
        }
        throw new C1826bv("Session without pack received.");
    }

    /* renamed from: e */
    private static <T> List<T> m450e(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final Map<String, Integer> mo34029a(List<String> list) {
        return (Map) m446a(new C1840ci(this, list));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34030a() {
        this.f590g.lock();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34031a(int i) {
        m446a(new C1839ch(this, i));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34032a(String str, int i, long j) {
        m446a(new C1836ce(this, str, i, j));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo34033a(Bundle bundle) {
        return ((Boolean) m446a(new C1834cc(this, bundle))).booleanValue();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ Map mo34034b(List list) {
        int i;
        Map<String, C1844cm> d = m447d((List<String>) list);
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            C1844cm cmVar = d.get(str);
            if (cmVar == null) {
                i = 8;
            } else {
                if (C1860db.m497a(cmVar.f577c.f572c)) {
                    try {
                        cmVar.f577c.f572c = 6;
                        this.f588e.mo34195a().execute(new C1841cj(this, cmVar));
                        this.f587d.mo34025a(str);
                    } catch (C1826bv unused) {
                        f584a.mo34142c("Session %d with pack %s does not exist, no need to cancel.", Integer.valueOf(cmVar.f575a), str);
                    }
                }
                i = cmVar.f577c.f572c;
            }
            hashMap.put(str, Integer.valueOf(i));
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo34035b() {
        this.f590g.unlock();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ void mo34036b(int i) {
        m448e(i).f577c.f572c = 5;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ void mo34037b(String str, int i, long j) {
        C1844cm cmVar = m447d((List<String>) Arrays.asList(new String[]{str})).get(str);
        if (cmVar == null || C1860db.m500b(cmVar.f577c.f572c)) {
            f584a.mo34141b(String.format("Could not find pack %s while trying to complete it", new Object[]{str}), new Object[0]);
        }
        this.f585b.mo33988f(str, i, j);
        cmVar.f577c.f572c = 4;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final boolean mo34038b(Bundle bundle) {
        return ((Boolean) m446a(new C1835cd(this, bundle))).booleanValue();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final /* synthetic */ Boolean mo34039c(Bundle bundle) {
        boolean z;
        int i = bundle.getInt("session_id");
        if (i == 0) {
            return true;
        }
        Map<Integer, C1844cm> map = this.f589f;
        Integer valueOf = Integer.valueOf(i);
        if (!map.containsKey(valueOf)) {
            return true;
        }
        C1844cm cmVar = this.f589f.get(valueOf);
        if (cmVar.f577c.f572c == 6) {
            z = false;
        } else {
            z = !C1860db.m498a(cmVar.f577c.f572c, bundle.getInt(C1985h.m783a("status", m449e(bundle))));
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final Map<Integer, C1844cm> mo34040c() {
        return this.f589f;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final /* synthetic */ Map mo34041c(List list) {
        HashMap hashMap = new HashMap();
        for (C1844cm next : this.f589f.values()) {
            String str = next.f577c.f570a;
            if (list.contains(str)) {
                C1844cm cmVar = (C1844cm) hashMap.get(str);
                if ((cmVar == null ? -1 : cmVar.f575a) < next.f575a) {
                    hashMap.put(str, next);
                }
            }
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final /* synthetic */ void mo34042c(int i) {
        C1844cm e = m448e(i);
        if (C1860db.m500b(e.f577c.f572c)) {
            C1806bb bbVar = this.f585b;
            C1843cl clVar = e.f577c;
            bbVar.mo33988f(clVar.f570a, e.f576b, clVar.f571b);
            C1843cl clVar2 = e.f577c;
            int i2 = clVar2.f572c;
            if (i2 == 5 || i2 == 6) {
                this.f585b.mo33980d(clVar2.f570a);
                return;
            }
            return;
        }
        throw new C1826bv(String.format("Could not safely delete session %d because it is not in a terminal state.", new Object[]{Integer.valueOf(i)}), i);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final /* synthetic */ Boolean mo34043d(Bundle bundle) {
        C1845cn cnVar;
        Bundle bundle2 = bundle;
        int i = bundle2.getInt("session_id");
        boolean z = false;
        if (i == 0) {
            return false;
        }
        Map<Integer, C1844cm> map = this.f589f;
        Integer valueOf = Integer.valueOf(i);
        if (map.containsKey(valueOf)) {
            C1844cm e = m448e(i);
            int i2 = bundle2.getInt(C1985h.m783a("status", e.f577c.f570a));
            if (C1860db.m498a(e.f577c.f572c, i2)) {
                f584a.mo34139a("Found stale update for session %s with status %d.", valueOf, Integer.valueOf(e.f577c.f572c));
                C1843cl clVar = e.f577c;
                String str = clVar.f570a;
                int i3 = clVar.f572c;
                if (i3 == 4) {
                    this.f586c.mo34195a().mo33947a(i, str);
                } else if (i3 == 5) {
                    this.f586c.mo34195a().mo33946a(i);
                } else if (i3 == 6) {
                    this.f586c.mo34195a().mo33950a((List<String>) Arrays.asList(new String[]{str}));
                }
            } else {
                e.f577c.f572c = i2;
                if (C1860db.m500b(i2)) {
                    mo34031a(i);
                    this.f587d.mo34025a(e.f577c.f570a);
                } else {
                    List<C1845cn> list = e.f577c.f574e;
                    int size = list.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        C1845cn cnVar2 = list.get(i4);
                        ArrayList parcelableArrayList = bundle2.getParcelableArrayList(C1985h.m784a("chunk_intents", e.f577c.f570a, cnVar2.f578a));
                        if (parcelableArrayList != null) {
                            for (int i5 = 0; i5 < parcelableArrayList.size(); i5++) {
                                if (!(parcelableArrayList.get(i5) == null || ((Intent) parcelableArrayList.get(i5)).getData() == null)) {
                                    cnVar2.f581d.get(i5).f569a = true;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            String e2 = m449e(bundle);
            long j = bundle2.getLong(C1985h.m783a("pack_version", e2));
            int i6 = bundle2.getInt(C1985h.m783a("status", e2));
            long j2 = bundle2.getLong(C1985h.m783a("total_bytes_to_download", e2));
            ArrayList<String> stringArrayList = bundle2.getStringArrayList(C1985h.m783a("slice_ids", e2));
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = m450e(stringArrayList).iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                ArrayList parcelableArrayList2 = bundle2.getParcelableArrayList(C1985h.m784a("chunk_intents", e2, str2));
                ArrayList arrayList2 = new ArrayList();
                for (Intent intent : m450e(parcelableArrayList2)) {
                    Iterator<T> it2 = it;
                    if (intent != null) {
                        z = true;
                    }
                    arrayList2.add(new C1842ck(z));
                    it = it2;
                    z = false;
                }
                Iterator<T> it3 = it;
                String string = bundle2.getString(C1985h.m784a("uncompressed_hash_sha256", e2, str2));
                long j3 = bundle2.getLong(C1985h.m784a("uncompressed_size", e2, str2));
                int i7 = bundle2.getInt(C1985h.m784a("patch_format", e2, str2), 0);
                if (i7 != 0) {
                    cnVar = new C1845cn(str2, string, j3, arrayList2, 0, i7);
                } else {
                    cnVar = new C1845cn(str2, string, j3, arrayList2, bundle2.getInt(C1985h.m784a("compression_format", e2, str2), 0), 0);
                }
                arrayList.add(cnVar);
                it = it3;
                z = false;
            }
            this.f589f.put(Integer.valueOf(i), new C1844cm(i, bundle2.getInt("app_version_code"), new C1843cl(e2, j, i6, j2, arrayList)));
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final void mo34044d(int i) {
        m446a(new C1838cg(this, i));
    }
}
