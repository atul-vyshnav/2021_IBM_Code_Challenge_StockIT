package com.google.android.play.core.internal;

import com.google.android.play.core.splitinstall.C2070k;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.bf */
final class C1945bf implements C1933au {
    C1945bf() {
    }

    /* renamed from: a */
    static C1938az m676a() {
        return new C1942bc();
    }

    /* renamed from: a */
    public static void m677a(ClassLoader classLoader, Set<File> set, C1944be beVar) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            for (File parentFile : set) {
                hashSet.add(parentFile.getParentFile());
            }
            Object a = C1940ba.m665a(classLoader);
            C1955bp<List> a2 = C1956bq.m703a(a, "nativeLibraryDirectories", List.class);
            synchronized (C2070k.class) {
                ArrayList arrayList = new ArrayList(a2.mo34163a());
                hashSet.removeAll(arrayList);
                arrayList.addAll(hashSet);
                a2.mo34164a(arrayList);
            }
            ArrayList arrayList2 = new ArrayList();
            Object[] a3 = beVar.mo34162a(a, new ArrayList(hashSet), arrayList2);
            if (!arrayList2.isEmpty()) {
                C1954bo boVar = new C1954bo("Error in makePathElements");
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    C1975ci.m765a(boVar, (IOException) arrayList2.get(i));
                }
                throw boVar;
            }
            synchronized (C2070k.class) {
                C1956bq.m717b(a, "nativeLibraryPathElements", Object.class).mo34167b(Arrays.asList(a3));
            }
        }
    }

    /* renamed from: b */
    static C1944be m678b() {
        return new C1943bd();
    }

    /* renamed from: b */
    public static boolean m679b(ClassLoader classLoader, File file, File file2, boolean z) {
        return C1940ba.m666a(classLoader, file, file2, z, m676a(), "zip", C1940ba.m667b());
    }

    /* renamed from: a */
    public final void mo34158a(ClassLoader classLoader, Set<File> set) {
        m677a(classLoader, set, m678b());
    }

    /* renamed from: a */
    public final boolean mo34159a(ClassLoader classLoader, File file, File file2, boolean z) {
        return m679b(classLoader, file, file2, z);
    }
}
