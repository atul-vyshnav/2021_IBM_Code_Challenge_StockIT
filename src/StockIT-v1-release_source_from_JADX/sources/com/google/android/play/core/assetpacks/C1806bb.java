package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1975ci;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/* renamed from: com.google.android.play.core.assetpacks.bb */
final class C1806bb {

    /* renamed from: a */
    private static final C1918af f439a = new C1918af("AssetPackStorage");

    /* renamed from: b */
    private static final long f440b = TimeUnit.DAYS.toMillis(14);

    /* renamed from: c */
    private static final long f441c = TimeUnit.DAYS.toMillis(28);

    /* renamed from: d */
    private final Context f442d;

    /* renamed from: e */
    private final C1870dl f443e;

    C1806bb(Context context, C1870dl dlVar) {
        this.f442d = context;
        this.f443e = dlVar;
    }

    /* renamed from: a */
    private final File m364a(String str, int i) {
        return new File(m369g(str), String.valueOf(i));
    }

    /* renamed from: a */
    private static List<String> m365a(PackageInfo packageInfo, String str) {
        ArrayList arrayList = new ArrayList();
        if (packageInfo.splitNames == null) {
            return arrayList;
        }
        int i = (-Arrays.binarySearch(packageInfo.splitNames, str)) - 1;
        while (i < packageInfo.splitNames.length && packageInfo.splitNames[i].startsWith(str)) {
            arrayList.add(packageInfo.applicationInfo.splitSourceDirs[i]);
            i++;
        }
        return arrayList;
    }

    /* renamed from: a */
    private static void m366a(File file) {
        if (file.listFiles() != null && file.listFiles().length > 1) {
            long b = m367b(file);
            for (File file2 : file.listFiles()) {
                if (!file2.getName().equals(String.valueOf(b)) && !file2.getName().equals("stale.tmp")) {
                    m368c(file2);
                }
            }
        }
    }

    /* renamed from: b */
    private static long m367b(File file) {
        if (!file.exists()) {
            return -1;
        }
        ArrayList arrayList = new ArrayList();
        try {
            for (File file2 : file.listFiles()) {
                if (!file2.getName().equals("stale.tmp")) {
                    arrayList.add(Long.valueOf(file2.getName()));
                }
            }
        } catch (NumberFormatException e) {
            f439a.mo34140a((Throwable) e, "Corrupt asset pack directories.", new Object[0]);
        }
        if (arrayList.isEmpty()) {
            return -1;
        }
        Collections.sort(arrayList);
        return ((Long) arrayList.get(arrayList.size() - 1)).longValue();
    }

    /* renamed from: c */
    private static boolean m368c(File file) {
        boolean z;
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            z = true;
            for (File c : listFiles) {
                z &= m368c(c);
            }
        } else {
            z = true;
        }
        return file.delete() && true == z;
    }

    /* renamed from: g */
    private final File m369g(String str) {
        return new File(m374i(), str);
    }

    /* renamed from: g */
    private final File m370g(String str, int i, long j) {
        return new File(mo33973c(str, i, j), "merge.tmp");
    }

    /* renamed from: g */
    private final List<File> m371g() {
        ArrayList arrayList = new ArrayList();
        try {
            if (m374i().exists()) {
                if (m374i().listFiles() != null) {
                    for (File file : m374i().listFiles()) {
                        if (!file.getCanonicalPath().equals(m372h().getCanonicalPath())) {
                            arrayList.add(file);
                        }
                    }
                    return arrayList;
                }
            }
            return arrayList;
        } catch (IOException e) {
            f439a.mo34141b("Could not process directory while scanning installed packs. %s", e);
        }
    }

    /* renamed from: h */
    private final File m372h() {
        return new File(m374i(), "_tmp");
    }

    /* renamed from: h */
    private final File m373h(String str, int i, long j) {
        return new File(new File(new File(m372h(), str), String.valueOf(i)), String.valueOf(j));
    }

    /* renamed from: i */
    private final File m374i() {
        return new File(this.f442d.getFilesDir(), "assetpacks");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0073  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.play.core.assetpacks.AssetLocation mo33961a(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            r0 = 0
            r1 = 0
            android.content.Context r2 = r9.f442d     // Catch:{ NameNotFoundException -> 0x0013 }
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0013 }
            android.content.Context r3 = r9.f442d     // Catch:{ NameNotFoundException -> 0x0013 }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ NameNotFoundException -> 0x0013 }
            android.content.pm.PackageInfo r2 = r2.getPackageInfo(r3, r1)     // Catch:{ NameNotFoundException -> 0x0013 }
            goto L_0x001d
        L_0x0013:
            com.google.android.play.core.internal.af r2 = f439a
            java.lang.Object[] r3 = new java.lang.Object[r1]
            java.lang.String r4 = "Could not find PackageInfo."
            r2.mo34141b(r4, r3)
            r2 = r0
        L_0x001d:
            r3 = 1
            if (r2 != 0) goto L_0x0022
            r4 = r0
            goto L_0x0087
        L_0x0022:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            int r5 = android.os.Build.VERSION.SDK_INT
            r6 = 21
            if (r5 >= r6) goto L_0x0035
            android.content.pm.ApplicationInfo r2 = r2.applicationInfo
            java.lang.String r2 = r2.sourceDir
            r4.add(r2)
            goto L_0x0087
        L_0x0035:
            java.lang.String[] r5 = r2.splitNames
            if (r5 == 0) goto L_0x005b
            android.content.pm.ApplicationInfo r5 = r2.applicationInfo
            java.lang.String[] r5 = r5.splitSourceDirs
            if (r5 != 0) goto L_0x0040
            goto L_0x005b
        L_0x0040:
            java.lang.String[] r5 = r2.splitNames
            int r5 = java.util.Arrays.binarySearch(r5, r10)
            if (r5 >= 0) goto L_0x0054
            com.google.android.play.core.internal.af r5 = f439a
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r6[r1] = r10
            java.lang.String r7 = "Asset Pack '%s' is not installed."
            r5.mo34139a(r7, r6)
            goto L_0x0066
        L_0x0054:
            android.content.pm.ApplicationInfo r6 = r2.applicationInfo
            java.lang.String[] r6 = r6.splitSourceDirs
            r5 = r6[r5]
            goto L_0x0067
        L_0x005b:
            com.google.android.play.core.internal.af r5 = f439a
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r6[r1] = r10
            java.lang.String r7 = "No splits present for package %s."
            r5.mo34139a(r7, r6)
        L_0x0066:
            r5 = r0
        L_0x0067:
            if (r5 != 0) goto L_0x0073
            android.content.pm.ApplicationInfo r5 = r2.applicationInfo
            java.lang.String r5 = r5.sourceDir
            r4.add(r5)
            java.lang.String r5 = "config."
            goto L_0x0080
        L_0x0073:
            r4.add(r5)
            java.lang.String r5 = java.lang.String.valueOf(r10)
            java.lang.String r6 = ".config."
            java.lang.String r5 = r5.concat(r6)
        L_0x0080:
            java.util.List r2 = m365a((android.content.pm.PackageInfo) r2, (java.lang.String) r5)
            r4.addAll(r2)
        L_0x0087:
            if (r4 != 0) goto L_0x008a
            goto L_0x00cc
        L_0x008a:
            java.io.File r2 = new java.io.File
            java.lang.String r5 = "assets"
            r2.<init>(r5, r11)
            java.lang.String r2 = r2.getPath()
            int r5 = r4.size()
            r6 = 0
        L_0x009a:
            r7 = 2
            if (r6 >= r5) goto L_0x00bc
            java.lang.Object r8 = r4.get(r6)
            java.lang.String r8 = (java.lang.String) r8
            com.google.android.play.core.assetpacks.AssetLocation r7 = com.google.android.play.core.assetpacks.C1860db.m493a((java.lang.String) r8, (java.lang.String) r2)     // Catch:{ IOException -> 0x00ad }
            int r6 = r6 + 1
            if (r7 == 0) goto L_0x009a
            r0 = r7
            goto L_0x00cc
        L_0x00ad:
            r10 = move-exception
            com.google.android.play.core.internal.af r2 = f439a
            java.lang.Object[] r4 = new java.lang.Object[r7]
            r4[r1] = r8
            r4[r3] = r11
            java.lang.String r11 = "Failed to parse APK file '%s' looking for asset '%s'."
            r2.mo34140a((java.lang.Throwable) r10, (java.lang.String) r11, (java.lang.Object[]) r4)
            goto L_0x00cc
        L_0x00bc:
            com.google.android.play.core.internal.af r2 = f439a
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r1] = r11
            r5[r3] = r10
            r5[r7] = r4
            java.lang.String r10 = "The asset %s is not present in Asset Pack %s. Searched in APKs: %s"
            r2.mo34139a(r10, r5)
        L_0x00cc:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.assetpacks.C1806bb.mo33961a(java.lang.String, java.lang.String):com.google.android.play.core.assetpacks.AssetLocation");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final AssetLocation mo33962a(String str, String str2, AssetPackLocation assetPackLocation) {
        File file = new File(assetPackLocation.assetsPath(), str2);
        if (file.exists()) {
            return AssetLocation.m280a(file.getPath(), 0, file.length());
        }
        f439a.mo34139a("The asset %s is not present in Asset Pack %s. Searched in folder: %s", str2, str, assetPackLocation.assetsPath());
        return null;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final File mo33963a(String str, int i, long j) {
        return new File(m364a(str, i), String.valueOf(j));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final File mo33964a(String str, int i, long j, String str2) {
        return new File(new File(new File(m373h(str, i, j), "_slices"), "_unverified"), str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final Map<String, AssetPackLocation> mo33965a() {
        HashMap hashMap = new HashMap();
        try {
            for (File next : m371g()) {
                AssetPackLocation b = mo33969b(next.getName());
                if (b != null) {
                    hashMap.put(next.getName(), b);
                }
            }
        } catch (IOException e) {
            f439a.mo34141b("Could not process directory while scanning installed packs: %s", e);
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo33966a(String str, int i, long j, int i2) throws IOException {
        File g = m370g(str, i, j);
        Properties properties = new Properties();
        properties.put("numberOfMerges", String.valueOf(i2));
        g.getParentFile().mkdirs();
        g.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(g);
        properties.store(fileOutputStream, (String) null);
        fileOutputStream.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo33967a(List<String> list) {
        int a = this.f443e.mo34067a();
        List<File> g = m371g();
        int size = g.size();
        for (int i = 0; i < size; i++) {
            File file = g.get(i);
            if (!list.contains(file.getName()) && m367b(file) != ((long) a)) {
                m368c(file);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo33968a(String str) {
        try {
            return mo33975c(str) != null;
        } catch (IOException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final AssetPackLocation mo33969b(String str) throws IOException {
        String c = mo33975c(str);
        if (c == null) {
            return null;
        }
        File file = new File(c, "assets");
        if (file.isDirectory()) {
            return AssetPackLocation.m285a(c, file.getCanonicalPath());
        }
        f439a.mo34141b("Failed to find assets directory: %s", file);
        return null;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final File mo33970b(String str, int i, long j) {
        return new File(mo33963a(str, i, j), "_metadata");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final File mo33971b(String str, int i, long j, String str2) {
        return new File(new File(new File(m373h(str, i, j), "_slices"), "_verified"), str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final Map<String, Long> mo33972b() {
        HashMap hashMap = new HashMap();
        for (String next : mo33965a().keySet()) {
            hashMap.put(next, Long.valueOf(mo33985f(next)));
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final File mo33973c(String str, int i, long j) {
        return new File(m373h(str, i, j), "_packs");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final File mo33974c(String str, int i, long j, String str2) {
        return new File(mo33986f(str, i, j, str2), "checkpoint.dat");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final String mo33975c(String str) throws IOException {
        int length;
        File file = new File(m374i(), str);
        if (!file.exists()) {
            f439a.mo34139a("Pack not found with pack name: %s", str);
            return null;
        }
        File file2 = new File(file, String.valueOf(this.f443e.mo34067a()));
        if (!file2.exists()) {
            f439a.mo34139a("Pack not found with pack name: %s app version: %s", str, Integer.valueOf(this.f443e.mo34067a()));
            return null;
        }
        File[] listFiles = file2.listFiles();
        if (listFiles == null || (length = listFiles.length) == 0) {
            f439a.mo34139a("No pack version found for pack name: %s app version: %s", str, Integer.valueOf(this.f443e.mo34067a()));
            return null;
        } else if (length <= 1) {
            return listFiles[0].getCanonicalPath();
        } else {
            f439a.mo34141b("Multiple pack versions found for pack name: %s app version: %s", str, Integer.valueOf(this.f443e.mo34067a()));
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final void mo33976c() {
        List<File> g = m371g();
        int size = g.size();
        for (int i = 0; i < size; i++) {
            File file = g.get(i);
            if (file.listFiles() != null) {
                m366a(file);
                long b = m367b(file);
                if (((long) this.f443e.mo34067a()) != b) {
                    try {
                        new File(new File(file, String.valueOf(b)), "stale.tmp").createNewFile();
                    } catch (IOException unused) {
                        f439a.mo34141b("Could not write staleness marker.", new Object[0]);
                    }
                }
                for (File a : file.listFiles()) {
                    m366a(a);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final int mo33977d(String str, int i, long j) throws IOException {
        File g = m370g(str, i, j);
        if (!g.exists()) {
            return 0;
        }
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(g);
        try {
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("numberOfMerges") != null) {
                try {
                    return Integer.parseInt(properties.getProperty("numberOfMerges"));
                } catch (NumberFormatException e) {
                    throw new C1826bv("Merge checkpoint file corrupt.", (Exception) e);
                }
            } else {
                throw new C1826bv("Merge checkpoint file corrupt.");
            }
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final File mo33978d(String str, int i, long j, String str2) {
        return new File(mo33986f(str, i, j, str2), "checkpoint_ext.dat");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final void mo33979d() {
        List<File> g = m371g();
        int size = g.size();
        for (int i = 0; i < size; i++) {
            File file = g.get(i);
            if (file.listFiles() != null) {
                for (File file2 : file.listFiles()) {
                    File file3 = new File(file2, "stale.tmp");
                    if (file3.exists() && System.currentTimeMillis() - file3.lastModified() > f441c) {
                        m368c(file2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final boolean mo33980d(String str) {
        if (!m369g(str).exists()) {
            return true;
        }
        return m368c(m369g(str));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final int mo33981e(String str) {
        return (int) m367b(m369g(str));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final File mo33982e(String str, int i, long j) {
        return new File(new File(m373h(str, i, j), "_slices"), "_metadata");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final File mo33983e(String str, int i, long j, String str2) {
        return new File(mo33986f(str, i, j, str2), "slice.zip");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final void mo33984e() {
        if (m372h().exists()) {
            for (File file : m372h().listFiles()) {
                if (System.currentTimeMillis() - file.lastModified() > f440b) {
                    m368c(file);
                } else {
                    m366a(file);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final long mo33985f(String str) {
        return m367b(m364a(str, mo33981e(str)));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final File mo33986f(String str, int i, long j, String str2) {
        return new File(mo33982e(str, i, j), str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final void mo33987f() {
        m368c(m374i());
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final void mo33988f(String str, int i, long j) {
        if (m373h(str, i, j).exists()) {
            m368c(m373h(str, i, j));
        }
    }
}
