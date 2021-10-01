package com.google.android.play.core.internal;

import android.content.ComponentName;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/* renamed from: com.google.android.play.core.internal.bq */
public final class C1956bq {
    /* renamed from: a */
    public static <T> C1955bp<T> m703a(Object obj, String str, Class<T> cls) {
        return new C1955bp<>(obj, m708a(obj, str), cls);
    }

    /* renamed from: a */
    public static <R, P0> R m704a(Class cls, Class<R> cls2, Class<P0> cls3, P0 p0) {
        try {
            return cls2.cast(m709a((Class<?>) cls, "isDexOptNeeded", (Class<?>[]) new Class[]{cls3}).invoke((Object) null, new Object[]{p0}));
        } catch (Exception e) {
            throw new C1957br(String.format("Failed to invoke static method %s on type %s", new Object[]{"isDexOptNeeded", cls}), e);
        }
    }

    /* renamed from: a */
    public static <R, P0, P1> R m705a(Class cls, Class<R> cls2, Class<P0> cls3, P0 p0, Class<P1> cls4, P1 p1) {
        try {
            return cls2.cast(m709a((Class<?>) cls, "optimizedPathFor", (Class<?>[]) new Class[]{cls3, cls4}).invoke((Object) null, new Object[]{p0, p1}));
        } catch (Exception e) {
            throw new C1957br(String.format("Failed to invoke static method %s on type %s", new Object[]{"optimizedPathFor", cls}), e);
        }
    }

    /* renamed from: a */
    public static <R, P0> R m706a(Object obj, String str, Class<R> cls, Class<P0> cls2, P0 p0) {
        try {
            return cls.cast(m710a(obj, str, (Class<?>[]) new Class[]{cls2}).invoke(obj, new Object[]{p0}));
        } catch (Exception e) {
            throw new C1957br(String.format("Failed to invoke method %s on an object of type %s", new Object[]{str, obj.getClass()}), e);
        }
    }

    /* renamed from: a */
    public static <R, P0, P1, P2> R m707a(Object obj, String str, Class<R> cls, Class<P0> cls2, P0 p0, Class<P1> cls3, P1 p1, Class<P2> cls4, P2 p2) {
        try {
            return cls.cast(m710a(obj, str, (Class<?>[]) new Class[]{cls2, cls3, cls4}).invoke(obj, new Object[]{p0, p1, p2}));
        } catch (Exception e) {
            throw new C1957br(String.format("Failed to invoke method %s on an object of type %s", new Object[]{str, obj.getClass()}), e);
        }
    }

    /* renamed from: a */
    private static Field m708a(Object obj, String str) {
        Class cls = obj.getClass();
        while (cls != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                return declaredField;
            } catch (NoSuchFieldException unused) {
                cls = cls.getSuperclass();
            }
        }
        throw new C1957br(String.format("Failed to find a field named %s on an object of instance %s", new Object[]{str, obj.getClass().getName()}));
    }

    /* renamed from: a */
    private static Method m709a(Class<?> cls, String str, Class<?>... clsArr) {
        Class<?> cls2 = cls;
        while (cls2 != null) {
            try {
                Method declaredMethod = cls2.getDeclaredMethod(str, clsArr);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                return declaredMethod;
            } catch (NoSuchMethodException unused) {
                cls2 = cls2.getSuperclass();
            }
        }
        throw new C1957br(String.format("Could not find a method named %s with parameters %s in type %s", new Object[]{str, Arrays.asList(clsArr), cls}));
    }

    /* renamed from: a */
    private static Method m710a(Object obj, String str, Class<?>... clsArr) {
        return m709a(obj.getClass(), str, clsArr);
    }

    /* renamed from: a */
    public static void m711a(PackageManager packageManager, ComponentName componentName, int i) {
        ComponentInfo componentInfo;
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
        if (componentEnabledSetting != 1) {
            if (componentEnabledSetting != 2) {
                String packageName = componentName.getPackageName();
                String className = componentName.getClassName();
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(packageName, i | 512);
                    ComponentInfo[][] componentInfoArr = {packageInfo.activities, packageInfo.services, packageInfo.providers};
                    int i2 = 0;
                    loop0:
                    while (true) {
                        if (i2 >= 3) {
                            componentInfo = null;
                            break;
                        }
                        ComponentInfo[] componentInfoArr2 = componentInfoArr[i2];
                        if (componentInfoArr2 != null) {
                            int length = componentInfoArr2.length;
                            for (int i3 = 0; i3 < length; i3++) {
                                componentInfo = componentInfoArr2[i3];
                                if (componentInfo.name.equals(className)) {
                                    break loop0;
                                }
                            }
                            continue;
                        }
                        i2++;
                    }
                    if (componentInfo != null) {
                        if (componentInfo.isEnabled()) {
                            return;
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            packageManager.setComponentEnabledSetting(componentName, 1, 1);
        }
    }

    /* renamed from: a */
    public static void m712a(C1967ca caVar, InputStream inputStream, OutputStream outputStream, long j) throws IOException {
        byte[] bArr = new byte[16384];
        InputStream inputStream2 = inputStream;
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream, 4096));
        int readInt = dataInputStream.readInt();
        if (readInt != -771763713) {
            String valueOf = String.valueOf(String.format("%x", new Object[]{Integer.valueOf(readInt)}));
            throw new C1965bz(valueOf.length() != 0 ? "Unexpected magic=".concat(valueOf) : new String("Unexpected magic="));
        }
        int read = dataInputStream.read();
        if (read == 4) {
            long j2 = 0;
            while (true) {
                long j3 = j - j2;
                try {
                    int read2 = dataInputStream.read();
                    if (read2 == -1) {
                        throw new IOException("Patch file overrun");
                    } else if (read2 != 0) {
                        switch (read2) {
                            case 247:
                                read2 = dataInputStream.readUnsignedShort();
                                m716a(bArr, dataInputStream, outputStream, read2, j3);
                                break;
                            case 248:
                                read2 = dataInputStream.readInt();
                                m716a(bArr, dataInputStream, outputStream, read2, j3);
                                break;
                            case 249:
                                long readUnsignedShort = (long) dataInputStream.readUnsignedShort();
                                read2 = dataInputStream.read();
                                if (read2 != -1) {
                                    m715a(bArr, caVar, outputStream, readUnsignedShort, read2, j3);
                                    break;
                                } else {
                                    throw new IOException("Unexpected end of patch");
                                }
                            case ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION:
                                long readUnsignedShort2 = (long) dataInputStream.readUnsignedShort();
                                read2 = dataInputStream.readUnsignedShort();
                                m715a(bArr, caVar, outputStream, readUnsignedShort2, read2, j3);
                                break;
                            case 251:
                                long readUnsignedShort3 = (long) dataInputStream.readUnsignedShort();
                                read2 = dataInputStream.readInt();
                                m715a(bArr, caVar, outputStream, readUnsignedShort3, read2, j3);
                                break;
                            case 252:
                                long readInt2 = (long) dataInputStream.readInt();
                                read2 = dataInputStream.read();
                                if (read2 != -1) {
                                    m715a(bArr, caVar, outputStream, readInt2, read2, j3);
                                    break;
                                } else {
                                    throw new IOException("Unexpected end of patch");
                                }
                            case 253:
                                long readInt3 = (long) dataInputStream.readInt();
                                read2 = dataInputStream.readUnsignedShort();
                                m715a(bArr, caVar, outputStream, readInt3, read2, j3);
                                break;
                            case 254:
                                long readInt4 = (long) dataInputStream.readInt();
                                read2 = dataInputStream.readInt();
                                m715a(bArr, caVar, outputStream, readInt4, read2, j3);
                                break;
                            case 255:
                                long readLong = dataInputStream.readLong();
                                read2 = dataInputStream.readInt();
                                m715a(bArr, caVar, outputStream, readLong, read2, j3);
                                break;
                            default:
                                m716a(bArr, dataInputStream, outputStream, read2, j3);
                                break;
                        }
                        j2 += (long) read2;
                    } else {
                        return;
                    }
                } finally {
                    outputStream.flush();
                }
            }
        } else {
            StringBuilder sb = new StringBuilder(30);
            sb.append("Unexpected version=");
            sb.append(read);
            throw new C1965bz(sb.toString());
        }
    }

    /* renamed from: a */
    public static <T> void m713a(T t) {
        if (t == null) {
            throw null;
        }
    }

    /* renamed from: a */
    public static <T> void m714a(T t, Class<T> cls) {
        if (t == null) {
            throw new IllegalStateException(String.valueOf(cls.getCanonicalName()).concat(" must be set"));
        }
    }

    /* renamed from: a */
    private static void m715a(byte[] bArr, C1967ca caVar, OutputStream outputStream, long j, int i, long j2) throws IOException {
        InputStream b;
        Throwable th;
        byte[] bArr2 = bArr;
        int i2 = i;
        if (i2 < 0) {
            throw new IOException("copyLength negative");
        } else if (j >= 0) {
            long j3 = (long) i2;
            if (j3 <= j2) {
                try {
                    b = new C1968cb(caVar, j, j3).mo34189b();
                    while (i2 > 0) {
                        int min = Math.min(i2, 16384);
                        int i3 = 0;
                        while (i3 < min) {
                            int read = b.read(bArr, i3, min - i3);
                            if (read != -1) {
                                i3 += read;
                            } else {
                                throw new IOException("truncated input stream");
                            }
                        }
                        OutputStream outputStream2 = outputStream;
                        outputStream.write(bArr, 0, min);
                        i2 -= min;
                    }
                    if (b != null) {
                        b.close();
                        return;
                    }
                    return;
                } catch (EOFException e) {
                    throw new IOException("patch underrun", e);
                } catch (Throwable th2) {
                    C1975ci.m765a(th, th2);
                }
            } else {
                throw new IOException("Output length overrun");
            }
        } else {
            throw new IOException("inputOffset negative");
        }
        throw th;
    }

    /* renamed from: a */
    private static void m716a(byte[] bArr, DataInputStream dataInputStream, OutputStream outputStream, int i, long j) throws IOException {
        if (i < 0) {
            throw new IOException("copyLength negative");
        } else if (((long) i) <= j) {
            while (i > 0) {
                try {
                    int min = Math.min(i, 16384);
                    dataInputStream.readFully(bArr, 0, min);
                    outputStream.write(bArr, 0, min);
                    i -= min;
                } catch (EOFException unused) {
                    throw new IOException("patch underrun");
                }
            }
        } else {
            throw new IOException("Output length overrun");
        }
    }

    /* renamed from: b */
    public static <T> C1955bp m717b(Object obj, String str, Class<T> cls) {
        return new C1955bp(obj, m708a(obj, str), cls, (byte[]) null);
    }

    /* renamed from: b */
    public static <T> void m718b(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
        }
    }
}
