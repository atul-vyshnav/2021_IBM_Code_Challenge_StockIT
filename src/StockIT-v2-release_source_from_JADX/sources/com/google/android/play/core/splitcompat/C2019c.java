package com.google.android.play.core.splitcompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.play.core.internal.C1934av;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitcompat.c */
public final class C2019c {

    /* renamed from: a */
    private final long f871a;

    /* renamed from: b */
    private final Context f872b;

    /* renamed from: c */
    private File f873c;

    public C2019c(Context context) throws PackageManager.NameNotFoundException {
        this.f872b = context;
        this.f871a = (long) context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
    }

    /* renamed from: a */
    private static File m897a(File file, String str) throws IOException {
        File file2 = new File(file, str);
        if (file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
            return file2;
        }
        throw new IllegalArgumentException("split ID cannot be placed in target directory");
    }

    /* renamed from: c */
    public static void m898c(File file) throws IOException {
        File[] listFiles;
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File c : listFiles) {
                m898c(c);
            }
        }
        if (file.exists() && !file.delete()) {
            throw new IOException(String.format("Failed to delete '%s'", new Object[]{file.getAbsolutePath()}));
        }
    }

    /* renamed from: d */
    private static void m899d(File file) throws IOException {
        if (!file.exists()) {
            file.mkdirs();
            if (!file.isDirectory()) {
                String valueOf = String.valueOf(file.getAbsolutePath());
                throw new IOException(valueOf.length() != 0 ? "Unable to create directory: ".concat(valueOf) : new String("Unable to create directory: "));
            }
        } else if (!file.isDirectory()) {
            throw new IllegalArgumentException("File input must be directory when it exists.");
        }
    }

    /* renamed from: f */
    private final File m900f() throws IOException {
        File file = new File(m901g(), "verified-splits");
        m899d(file);
        return file;
    }

    /* renamed from: g */
    private final File m901g() throws IOException {
        File file = new File(m903h(), Long.toString(this.f871a));
        m899d(file);
        return file;
    }

    /* renamed from: g */
    private final File m902g(String str) throws IOException {
        File a = m897a(m905i(), str);
        m899d(a);
        return a;
    }

    /* renamed from: h */
    private final File m903h() throws IOException {
        if (this.f873c == null) {
            Context context = this.f872b;
            if (context != null) {
                this.f873c = context.getFilesDir();
            } else {
                throw new IllegalStateException("context must be non-null to populate null filesDir");
            }
        }
        File file = new File(this.f873c, "splitcompat");
        m899d(file);
        return file;
    }

    /* renamed from: h */
    private static String m904h(String str) {
        return String.valueOf(str).concat(".apk");
    }

    /* renamed from: i */
    private final File m905i() throws IOException {
        File file = new File(m901g(), "native-libraries");
        m899d(file);
        return file;
    }

    /* renamed from: a */
    public final File mo34271a(File file) throws IOException {
        return m897a(m900f(), file.getName());
    }

    /* renamed from: a */
    public final File mo34272a(String str) throws IOException {
        return m897a(mo34278c(), m904h(str));
    }

    /* renamed from: a */
    public final File mo34273a(String str, String str2) throws IOException {
        return m897a(m902g(str), str2);
    }

    /* renamed from: a */
    public final void mo34274a() throws IOException {
        File h = m903h();
        String[] list = h.list();
        if (list != null) {
            for (String str : list) {
                if (!str.equals(Long.toString(this.f871a))) {
                    File file = new File(h, str);
                    String valueOf = String.valueOf(file);
                    long j = this.f871a;
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 118);
                    sb.append("FileStorage: removing directory for different version code (directory = ");
                    sb.append(valueOf);
                    sb.append(", current version code = ");
                    sb.append(j);
                    sb.append(")");
                    Log.d("SplitCompat", sb.toString());
                    m898c(file);
                }
            }
        }
    }

    /* renamed from: b */
    public final File mo34275b() throws IOException {
        return new File(m901g(), "lock.tmp");
    }

    /* renamed from: b */
    public final File mo34276b(String str) throws IOException {
        return m897a(m900f(), m904h(str));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo34277b(File file) throws IOException {
        C1934av.m657a(file.getParentFile().getParentFile().equals(m905i()), (Object) "File to remove is not a native library");
        m898c(file);
    }

    /* renamed from: c */
    public final File mo34278c() throws IOException {
        File file = new File(m901g(), "unverified-splits");
        m899d(file);
        return file;
    }

    /* renamed from: c */
    public final File mo34279c(String str) throws IOException {
        File file = new File(m901g(), "dex");
        m899d(file);
        File a = m897a(file, str);
        m899d(a);
        return a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final Set<C2033q> mo34280d() throws IOException {
        File f = m900f();
        HashSet hashSet = new HashSet();
        File[] listFiles = f.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile() && file.getName().endsWith(".apk")) {
                    String name = file.getName();
                    hashSet.add(new C2033q(file, name.substring(0, name.length() - 4)));
                }
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final void mo34281d(String str) throws IOException {
        m898c(m902g(str));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final List<String> mo34282e() throws IOException {
        ArrayList arrayList = new ArrayList();
        File[] listFiles = m905i().listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    arrayList.add(file.getName());
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final Set<File> mo34283e(String str) throws IOException {
        HashSet hashSet = new HashSet();
        File[] listFiles = m902g(str).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    hashSet.add(file);
                }
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final void mo34284f(String str) throws IOException {
        m898c(mo34276b(str));
    }
}
