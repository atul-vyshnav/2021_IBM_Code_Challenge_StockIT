package com.google.android.play.core.splitcompat;

import android.util.Log;
import com.google.android.play.core.internal.C1975ci;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* renamed from: com.google.android.play.core.splitcompat.g */
final class C2023g implements C2025i {

    /* renamed from: a */
    final /* synthetic */ Set f882a;

    /* renamed from: b */
    final /* synthetic */ C2033q f883b;

    /* renamed from: c */
    final /* synthetic */ ZipFile f884c;

    C2023g(Set set, C2033q qVar, ZipFile zipFile) {
        this.f882a = set;
        this.f883b = qVar;
        this.f884c = zipFile;
    }

    /* renamed from: a */
    public final void mo34285a(C2026j jVar, File file, boolean z) throws IOException {
        FileOutputStream fileOutputStream;
        this.f882a.add(file);
        if (!z) {
            Log.i("SplitCompat", String.format("NativeLibraryExtractor: split '%s' has native library '%s' that does not exist; extracting from '%s!%s' to '%s'", new Object[]{this.f883b.mo34294b(), jVar.f885a, this.f883b.mo34293a().getAbsolutePath(), jVar.f886b.getName(), file.getAbsolutePath()}));
            ZipFile zipFile = this.f884c;
            ZipEntry zipEntry = jVar.f886b;
            int i = C2027k.f887a;
            byte[] bArr = new byte[4096];
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            try {
                fileOutputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                    return;
                }
                return;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        C1975ci.m765a(th, th2);
                    }
                }
                throw th;
            }
        } else {
            return;
        }
        throw th;
    }
}
