package com.google.android.play.core.assetpacks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/* renamed from: com.google.android.play.core.assetpacks.dc */
public final class C1861dc extends InputStream {

    /* renamed from: a */
    private final Enumeration<File> f637a;

    /* renamed from: b */
    private InputStream f638b;

    public C1861dc(Enumeration<File> enumeration) throws IOException {
        this.f637a = enumeration;
        mo34056a();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34056a() throws IOException {
        InputStream inputStream = this.f638b;
        if (inputStream != null) {
            inputStream.close();
        }
        this.f638b = this.f637a.hasMoreElements() ? new FileInputStream(this.f637a.nextElement()) : null;
    }

    public final void close() throws IOException {
        super.close();
        InputStream inputStream = this.f638b;
        if (inputStream != null) {
            inputStream.close();
            this.f638b = null;
        }
    }

    public final int read() throws IOException {
        while (true) {
            InputStream inputStream = this.f638b;
            if (inputStream == null) {
                return -1;
            }
            int read = inputStream.read();
            if (read != -1) {
                return read;
            }
            mo34056a();
        }
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.f638b == null) {
            return -1;
        }
        if (bArr == null) {
            throw null;
        } else if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
            throw new IndexOutOfBoundsException();
        } else if (i2 == 0) {
            return 0;
        } else {
            do {
                int read = this.f638b.read(bArr, i, i2);
                if (read > 0) {
                    return read;
                }
                mo34056a();
            } while (this.f638b != null);
            return -1;
        }
    }
}
