package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import com.google.android.exoplayer2.C1379C;
import com.google.android.play.core.common.LocalTestingException;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1934av;
import com.google.android.play.core.internal.C1976cj;
import com.google.android.play.core.internal.C1985h;
import com.google.android.play.core.tasks.C2106i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.google.android.play.core.assetpacks.cz */
final class C1857cz implements C1902w {

    /* renamed from: a */
    private static final C1918af f618a = new C1918af("FakeAssetPackService");

    /* renamed from: h */
    private static final AtomicInteger f619h = new AtomicInteger(1);

    /* renamed from: b */
    private final String f620b;

    /* renamed from: c */
    private final C1800aw f621c;

    /* renamed from: d */
    private final C1830bz f622d;

    /* renamed from: e */
    private final Context f623e;

    /* renamed from: f */
    private final C1870dl f624f;

    /* renamed from: g */
    private final C1976cj<Executor> f625g;

    /* renamed from: i */
    private final Handler f626i = new Handler(Looper.getMainLooper());

    C1857cz(File file, C1800aw awVar, C1830bz bzVar, Context context, C1870dl dlVar, C1976cj<Executor> cjVar) {
        this.f620b = file.getAbsolutePath();
        this.f621c = awVar;
        this.f622d = bzVar;
        this.f623e = context;
        this.f624f = dlVar;
        this.f625g = cjVar;
    }

    /* renamed from: a */
    static long m472a(int i, long j) {
        if (i == 2) {
            return j / 2;
        }
        if (i == 3 || i == 4) {
            return j;
        }
        return 0;
    }

    /* renamed from: a */
    private final AssetPackState m473a(String str, int i) throws LocalTestingException {
        long j = 0;
        for (File length : m476b(str)) {
            j += length.length();
        }
        String str2 = str;
        return AssetPackState.m287a(str, i, 0, m472a(i, j), j, this.f622d.mo34026b(str));
    }

    /* renamed from: a */
    private static String m474a(File file) throws LocalTestingException {
        try {
            return C1860db.m496a((List<File>) Arrays.asList(new File[]{file}));
        } catch (NoSuchAlgorithmException e) {
            throw new LocalTestingException("SHA256 algorithm not supported.", e);
        } catch (IOException e2) {
            throw new LocalTestingException(String.format("Could not digest file: %s.", new Object[]{file}), e2);
        }
    }

    /* renamed from: a */
    private final void m475a(int i, String str, int i2) throws LocalTestingException {
        Bundle bundle = new Bundle();
        bundle.putInt("app_version_code", this.f624f.mo34067a());
        bundle.putInt("session_id", i);
        File[] b = m476b(str);
        ArrayList arrayList = new ArrayList();
        long j = 0;
        for (File file : b) {
            j += file.length();
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(i2 == 3 ? new Intent().setData(Uri.EMPTY) : null);
            String a = C1934av.m655a(file);
            bundle.putParcelableArrayList(C1985h.m784a("chunk_intents", str, a), arrayList2);
            bundle.putString(C1985h.m784a("uncompressed_hash_sha256", str, a), m474a(file));
            bundle.putLong(C1985h.m784a("uncompressed_size", str, a), file.length());
            arrayList.add(a);
        }
        bundle.putStringArrayList(C1985h.m783a("slice_ids", str), arrayList);
        bundle.putLong(C1985h.m783a("pack_version", str), (long) this.f624f.mo34067a());
        bundle.putInt(C1985h.m783a("status", str), i2);
        bundle.putInt(C1985h.m783a("error_code", str), 0);
        bundle.putLong(C1985h.m783a("bytes_downloaded", str), m472a(i2, j));
        bundle.putLong(C1985h.m783a("total_bytes_to_download", str), j);
        bundle.putStringArrayList("pack_names", new ArrayList(Arrays.asList(new String[]{str})));
        bundle.putLong("bytes_downloaded", m472a(i2, j));
        bundle.putLong("total_bytes_to_download", j);
        this.f626i.post(new C1856cy(this, new Intent("com.google.android.play.core.assetpacks.receiver.ACTION_SESSION_UPDATE").putExtra("com.google.android.play.core.assetpacks.receiver.EXTRA_SESSION_STATE", bundle)));
    }

    /* renamed from: b */
    private final File[] m476b(String str) throws LocalTestingException {
        File file = new File(this.f620b);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles(new C1855cx(str));
            if (listFiles != null) {
                if (r1 != 0) {
                    for (File a : listFiles) {
                        if (C1934av.m655a(a).equals(str)) {
                            return listFiles;
                        }
                    }
                    throw new LocalTestingException(String.format("No master slice available for pack '%s'.", new Object[]{str}));
                }
                throw new LocalTestingException(String.format("No APKs available for pack '%s'.", new Object[]{str}));
            }
            throw new LocalTestingException(String.format("Failed fetching APKs for pack '%s'.", new Object[]{str}));
        }
        throw new LocalTestingException(String.format("Local testing directory '%s' not found.", new Object[]{file}));
    }

    /* renamed from: a */
    public final Task<AssetPackStates> mo33942a(List<String> list, C1803az azVar, Map<String, Long> map) {
        f618a.mo34142c("getPackStates(%s)", list);
        C2106i iVar = new C2106i();
        this.f625g.mo34195a().execute(new C1853cv(this, list, azVar, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: a */
    public final Task<AssetPackStates> mo33943a(List<String> list, List<String> list2, Map<String, Long> map) {
        f618a.mo34142c("startDownload(%s)", list2);
        C2106i iVar = new C2106i();
        this.f625g.mo34195a().execute(new C1852cu(this, list2, iVar, list));
        return iVar.mo34394a();
    }

    /* renamed from: a */
    public final Task<List<String>> mo33944a(Map<String, Long> map) {
        f618a.mo34142c("syncPacks()", new Object[0]);
        return Tasks.m1079a(new ArrayList());
    }

    /* renamed from: a */
    public final void mo33945a() {
        f618a.mo34142c("keepAlive", new Object[0]);
    }

    /* renamed from: a */
    public final void mo33946a(int i) {
        f618a.mo34142c("notifySessionFailed", new Object[0]);
    }

    /* renamed from: a */
    public final void mo33947a(int i, String str) {
        f618a.mo34142c("notifyModuleCompleted", new Object[0]);
        this.f625g.mo34195a().execute(new C1854cw(this, i, str));
    }

    /* renamed from: a */
    public final void mo33948a(int i, String str, String str2, int i2) {
        f618a.mo34142c("notifyChunkTransferred", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34051a(Intent intent) {
        this.f621c.mo33854a(this.f623e, intent);
    }

    /* renamed from: a */
    public final void mo33949a(String str) {
        f618a.mo34142c("removePack(%s)", str);
    }

    /* renamed from: a */
    public final void mo33950a(List<String> list) {
        f618a.mo34142c("cancelDownload(%s)", list);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34052a(List list, C1803az azVar, C2106i iVar) {
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        long j = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                AssetPackState a = m473a(str, azVar.mo33958a(8, str));
                j += a.totalBytesToDownload();
                hashMap.put(str, a);
            } catch (LocalTestingException e) {
                iVar.mo34395a((Exception) e);
                return;
            }
        }
        iVar.mo34396a(AssetPackStates.m288a(j, (Map<String, AssetPackState>) hashMap));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34053a(List list, C2106i iVar, List list2) {
        List list3 = list;
        C2106i iVar2 = iVar;
        HashMap hashMap = new HashMap();
        int size = list.size();
        long j = 0;
        int i = 0;
        while (i < size) {
            String str = (String) list3.get(i);
            try {
                AssetPackState a = m473a(str, 1);
                j += a.totalBytesToDownload();
                hashMap.put(str, a);
                i++;
            } catch (LocalTestingException e) {
                iVar2.mo34395a((Exception) e);
                return;
            }
        }
        int size2 = list.size();
        int i2 = 0;
        while (i2 < size2) {
            String str2 = (String) list3.get(i2);
            try {
                int andIncrement = f619h.getAndIncrement();
                m475a(andIncrement, str2, 1);
                m475a(andIncrement, str2, 2);
                m475a(andIncrement, str2, 3);
                i2++;
            } catch (LocalTestingException e2) {
                iVar2.mo34395a((Exception) e2);
                return;
            }
        }
        int size3 = list2.size();
        for (int i3 = 0; i3 < size3; i3++) {
            String str3 = (String) list2.get(i3);
            hashMap.put(str3, AssetPackState.m287a(str3, 4, 0, 0, 0, 0.0d));
        }
        iVar2.mo34396a(AssetPackStates.m288a(j, (Map<String, AssetPackState>) hashMap));
    }

    /* renamed from: b */
    public final Task<ParcelFileDescriptor> mo33951b(int i, String str, String str2, int i2) {
        f618a.mo34142c("getChunkFileDescriptor(session=%d, %s, %s, %d)", Integer.valueOf(i), str, str2, Integer.valueOf(i2));
        C2106i iVar = new C2106i();
        try {
            for (File file : m476b(str)) {
                if (C1934av.m655a(file).equals(str2)) {
                    iVar.mo34396a(ParcelFileDescriptor.open(file, C1379C.ENCODING_PCM_MU_LAW));
                    return iVar.mo34394a();
                }
            }
            throw new LocalTestingException(String.format("Local testing slice for '%s' not found.", new Object[]{str2}));
        } catch (FileNotFoundException e) {
            f618a.mo34143d("getChunkFileDescriptor failed", e);
            iVar.mo34395a((Exception) new LocalTestingException("Asset Slice file not found.", e));
        } catch (LocalTestingException e2) {
            f618a.mo34143d("getChunkFileDescriptor failed", e2);
            iVar.mo34395a((Exception) e2);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ void mo34054b(int i, String str) {
        try {
            m475a(i, str, 4);
        } catch (LocalTestingException e) {
            f618a.mo34143d("notifyModuleCompleted failed", e);
        }
    }
}
