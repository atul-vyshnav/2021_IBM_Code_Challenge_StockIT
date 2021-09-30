package com.google.android.play.core.assetpacks;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1975ci;
import com.thunkable.live.BuildConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Properties;

/* renamed from: com.google.android.play.core.assetpacks.dr */
final class C1876dr {

    /* renamed from: a */
    private static final C1918af f691a = new C1918af("SliceMetadataManager");

    /* renamed from: b */
    private final byte[] f692b = new byte[8192];

    /* renamed from: c */
    private final C1806bb f693c;

    /* renamed from: d */
    private final String f694d;

    /* renamed from: e */
    private final int f695e;

    /* renamed from: f */
    private final long f696f;

    /* renamed from: g */
    private final String f697g;

    /* renamed from: h */
    private int f698h;

    C1876dr(C1806bb bbVar, String str, int i, long j, String str2) {
        this.f693c = bbVar;
        this.f694d = str;
        this.f695e = i;
        this.f696f = j;
        this.f697g = str2;
        this.f698h = -1;
    }

    /* renamed from: e */
    private final File m524e() {
        File f = this.f693c.mo33986f(this.f694d, this.f695e, this.f696f, this.f697g);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    /* renamed from: f */
    private final File m525f() throws IOException {
        File c = this.f693c.mo33974c(this.f694d, this.f695e, this.f696f, this.f697g);
        c.getParentFile().mkdirs();
        c.createNewFile();
        return c;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final C1875dq mo34077a() throws IOException {
        File c = this.f693c.mo33974c(this.f694d, this.f695e, this.f696f, this.f697g);
        if (c.exists()) {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(c);
            try {
                properties.load(fileInputStream);
                fileInputStream.close();
                if (properties.getProperty("fileStatus") == null || properties.getProperty("previousChunk") == null) {
                    throw new C1826bv("Slice checkpoint file corrupt.");
                }
                try {
                    int parseInt = Integer.parseInt(properties.getProperty("fileStatus"));
                    String property = properties.getProperty("fileName");
                    long parseLong = Long.parseLong(properties.getProperty("fileOffset", "-1"));
                    long parseLong2 = Long.parseLong(properties.getProperty("remainingBytes", "-1"));
                    int parseInt2 = Integer.parseInt(properties.getProperty("previousChunk"));
                    this.f698h = Integer.parseInt(properties.getProperty("metadataFileCounter", "0"));
                    return new C1875dq(parseInt, property, parseLong, parseLong2, parseInt2);
                } catch (NumberFormatException e) {
                    throw new C1826bv("Slice checkpoint file corrupt.", (Exception) e);
                }
            } catch (Throwable th) {
                C1975ci.m765a(th, th);
            }
        } else {
            throw new C1826bv("Slice checkpoint file does not exist.");
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34078a(int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", ExifInterface.GPS_MEASUREMENT_3D);
        properties.put("fileOffset", String.valueOf(mo34085b().length()));
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.f698h));
        FileOutputStream fileOutputStream = new FileOutputStream(m525f());
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
            return;
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34079a(long j, byte[] bArr, int i, int i2) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(mo34085b(), "rw");
        try {
            randomAccessFile.seek(j);
            randomAccessFile.write(bArr, i, i2);
            randomAccessFile.close();
            return;
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34080a(InputStream inputStream, long j) throws IOException {
        int read;
        RandomAccessFile randomAccessFile = new RandomAccessFile(mo34085b(), "rw");
        try {
            randomAccessFile.seek(j);
            do {
                read = inputStream.read(this.f692b);
                if (read > 0) {
                    randomAccessFile.write(this.f692b, 0, read);
                }
            } while (read == this.f692b.length);
            randomAccessFile.close();
            return;
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34081a(String str, long j, long j2, int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", BuildConfig.VERSION_NAME);
        properties.put("fileName", str);
        properties.put("fileOffset", String.valueOf(j));
        properties.put("remainingBytes", String.valueOf(j2));
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.f698h));
        FileOutputStream fileOutputStream = new FileOutputStream(m525f());
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
            return;
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34082a(byte[] bArr) throws IOException {
        FileOutputStream fileOutputStream;
        this.f698h++;
        try {
            fileOutputStream = new FileOutputStream(new File(m524e(), String.format("%s-LFH.dat", new Object[]{Integer.valueOf(this.f698h)})));
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            return;
        } catch (IOException e) {
            throw new C1826bv("Could not write metadata file.", (Exception) e);
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34083a(byte[] bArr, int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", ExifInterface.GPS_MEASUREMENT_2D);
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.f698h));
        FileOutputStream fileOutputStream = new FileOutputStream(m525f());
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
            File d = this.f693c.mo33978d(this.f694d, this.f695e, this.f696f, this.f697g);
            if (d.exists()) {
                d.delete();
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(d);
            try {
                fileOutputStream2.write(bArr);
                fileOutputStream2.close();
                return;
            } catch (Throwable th) {
                C1975ci.m765a(th, th);
            }
            throw th;
            throw th;
        } catch (Throwable th2) {
            C1975ci.m765a(th, th2);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34084a(byte[] bArr, InputStream inputStream) throws IOException {
        this.f698h++;
        FileOutputStream fileOutputStream = new FileOutputStream(mo34085b());
        try {
            fileOutputStream.write(bArr);
            int read = inputStream.read(this.f692b);
            while (read > 0) {
                fileOutputStream.write(this.f692b, 0, read);
                read = inputStream.read(this.f692b);
            }
            fileOutputStream.close();
            return;
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final File mo34085b() {
        return new File(m524e(), String.format("%s-NAM.dat", new Object[]{Integer.valueOf(this.f698h)}));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo34086b(int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", "4");
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.f698h));
        FileOutputStream fileOutputStream = new FileOutputStream(m525f());
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
            return;
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo34087b(byte[] bArr, int i) throws IOException {
        this.f698h++;
        FileOutputStream fileOutputStream = new FileOutputStream(mo34085b());
        try {
            fileOutputStream.write(bArr, 0, i);
            fileOutputStream.close();
            return;
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final int mo34088c() throws IOException {
        File c = this.f693c.mo33974c(this.f694d, this.f695e, this.f696f, this.f697g);
        if (!c.exists()) {
            return 0;
        }
        FileInputStream fileInputStream = new FileInputStream(c);
        try {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            if (Integer.parseInt(properties.getProperty("fileStatus", "-1")) == 4) {
                return -1;
            }
            if (properties.getProperty("previousChunk") != null) {
                return Integer.parseInt(properties.getProperty("previousChunk")) + 1;
            }
            throw new C1826bv("Slice checkpoint file corrupt.");
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final boolean mo34089d() {
        FileInputStream fileInputStream;
        File c = this.f693c.mo33974c(this.f694d, this.f695e, this.f696f, this.f697g);
        if (!c.exists()) {
            return false;
        }
        try {
            fileInputStream = new FileInputStream(c);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("fileStatus") != null) {
                return Integer.parseInt(properties.getProperty("fileStatus")) == 4;
            }
            f691a.mo34141b("Slice checkpoint file corrupt while checking if extraction finished.", new Object[0]);
            return false;
        } catch (IOException e) {
            f691a.mo34141b("Could not read checkpoint while checking if extraction finished. %s", e);
            return false;
        } catch (Throwable th) {
            C1975ci.m765a(th, th);
        }
        throw th;
    }
}
