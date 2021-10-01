package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1967ca;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* renamed from: com.google.android.play.core.assetpacks.be */
final class C1809be extends C1967ca {

    /* renamed from: a */
    private final File f448a;

    /* renamed from: b */
    private final File f449b;

    /* renamed from: c */
    private final NavigableMap<Long, File> f450c = new TreeMap();

    C1809be(File file, File file2) throws IOException {
        this.f448a = file;
        this.f449b = file2;
        List<File> a = C1878dt.m539a(this.f448a, this.f449b);
        if (!a.isEmpty()) {
            int size = a.size();
            long j = 0;
            for (int i = 0; i < size; i++) {
                File file3 = a.get(i);
                this.f450c.put(Long.valueOf(j), file3);
                j += file3.length();
            }
            return;
        }
        throw new C1826bv(String.format("Virtualized slice archive empty for %s, %s", new Object[]{this.f448a, this.f449b}));
    }

    /* renamed from: a */
    private final InputStream m404a(long j, Long l) throws IOException {
        FileInputStream fileInputStream = new FileInputStream((File) this.f450c.get(l));
        if (fileInputStream.skip(j - l.longValue()) == j - l.longValue()) {
            return fileInputStream;
        }
        throw new C1826bv(String.format("Virtualized slice archive corrupt, could not skip in file with key %s", new Object[]{l}));
    }

    /* renamed from: a */
    public final long mo33992a() {
        Map.Entry<Long, File> lastEntry = this.f450c.lastEntry();
        return lastEntry.getKey().longValue() + lastEntry.getValue().length();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final InputStream mo33993a(long j, long j2) throws IOException {
        if (j < 0 || j2 < 0) {
            throw new C1826bv(String.format("Invalid input parameters %s, %s", new Object[]{Long.valueOf(j), Long.valueOf(j2)}));
        }
        long j3 = j + j2;
        if (j3 <= mo33992a()) {
            Long floorKey = this.f450c.floorKey(Long.valueOf(j));
            Long floorKey2 = this.f450c.floorKey(Long.valueOf(j3));
            if (floorKey.equals(floorKey2)) {
                return new C1808bd(m404a(j, floorKey), j2);
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(m404a(j, floorKey));
            Collection values = this.f450c.subMap(floorKey, false, floorKey2, false).values();
            if (!values.isEmpty()) {
                arrayList.add(new C1861dc(Collections.enumeration(values)));
            }
            arrayList.add(new C1808bd(new FileInputStream((File) this.f450c.get(floorKey2)), j2 - (floorKey2.longValue() - j)));
            return new SequenceInputStream(Collections.enumeration(arrayList));
        }
        throw new C1826bv(String.format("Trying to access archive out of bounds. Archive ends at: %s. Tried accessing: %s", new Object[]{Long.valueOf(mo33992a()), Long.valueOf(j3)}));
    }

    public final void close() {
    }
}
