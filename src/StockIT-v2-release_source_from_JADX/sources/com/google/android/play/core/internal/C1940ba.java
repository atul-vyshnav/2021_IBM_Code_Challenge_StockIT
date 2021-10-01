package com.google.android.play.core.internal;

import android.util.Log;
import com.google.android.play.core.splitinstall.C2070k;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.ba */
final class C1940ba implements C1933au {
    C1940ba() {
    }

    /* renamed from: a */
    static C1938az m664a() {
        return new C1935aw();
    }

    /* renamed from: a */
    static Object m665a(ClassLoader classLoader) {
        return C1956bq.m703a((Object) classLoader, "pathList", Object.class).mo34163a();
    }

    /* renamed from: a */
    static boolean m666a(ClassLoader classLoader, File file, File file2, boolean z, C1938az azVar, String str, C1937ay ayVar) {
        ArrayList arrayList = new ArrayList();
        Object a = m665a(classLoader);
        C1955bp b = C1956bq.m717b(a, "dexElements", Object.class);
        List<Object> asList = Arrays.asList((Object[]) b.mo34163a());
        ArrayList arrayList2 = new ArrayList();
        for (Object a2 : asList) {
            arrayList2.add(C1956bq.m703a(a2, str, File.class).mo34163a());
        }
        if (arrayList2.contains(file2)) {
            return true;
        }
        if (z || ayVar.mo34161a(a, file2, file)) {
            b.mo34165a((Collection) Arrays.asList(azVar.mo34160a(a, new ArrayList(Collections.singleton(file2)), file, arrayList)));
            if (arrayList.isEmpty()) {
                return true;
            }
            C1954bo boVar = new C1954bo("DexPathList.makeDexElement failed");
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                IOException iOException = (IOException) arrayList.get(i);
                Log.e("SplitCompat", "DexPathList.makeDexElement failed", iOException);
                C1975ci.m765a(boVar, iOException);
            }
            C1956bq.m717b(a, "dexElementsSuppressedExceptions", IOException.class).mo34165a((Collection) arrayList);
            throw boVar;
        }
        String valueOf = String.valueOf(file2.getPath());
        Log.w("SplitCompat", valueOf.length() != 0 ? "Should be optimized ".concat(valueOf) : new String("Should be optimized "));
        return false;
    }

    /* renamed from: b */
    static C1937ay m667b() {
        return new C1936ax();
    }

    /* renamed from: b */
    static void m668b(ClassLoader classLoader, Set<File> set) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            for (File next : set) {
                String valueOf = String.valueOf(next.getParentFile().getAbsolutePath());
                Log.d("Splitcompat", valueOf.length() != 0 ? "Adding native library parent directory: ".concat(valueOf) : new String("Adding native library parent directory: "));
                hashSet.add(next.getParentFile());
            }
            C1955bp b = C1956bq.m717b(m665a(classLoader), "nativeLibraryDirectories", File.class);
            hashSet.removeAll(Arrays.asList((File[]) b.mo34163a()));
            synchronized (C2070k.class) {
                int size = hashSet.size();
                StringBuilder sb = new StringBuilder(30);
                sb.append("Adding directories ");
                sb.append(size);
                Log.d("Splitcompat", sb.toString());
                b.mo34167b(hashSet);
            }
        }
    }

    /* renamed from: a */
    public final void mo34158a(ClassLoader classLoader, Set<File> set) {
        m668b(classLoader, set);
    }

    /* renamed from: a */
    public final boolean mo34159a(ClassLoader classLoader, File file, File file2, boolean z) {
        return m666a(classLoader, file, file2, z, m664a(), "zip", m667b());
    }
}
