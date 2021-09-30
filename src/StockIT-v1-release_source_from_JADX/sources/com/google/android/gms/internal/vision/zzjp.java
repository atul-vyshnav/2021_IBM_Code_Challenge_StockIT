package com.google.android.gms.internal.vision;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
final class zzjp {
    private static final Logger logger = Logger.getLogger(zzjp.class.getName());
    private static final boolean zzaap = zzk(Long.TYPE);
    private static final boolean zzaaq = zzk(Integer.TYPE);
    private static final zzd zzaar;
    private static final boolean zzaas = zzin();
    private static final long zzaat = ((long) zzi(byte[].class));
    private static final long zzaau;
    private static final long zzaav;
    private static final long zzaaw;
    private static final long zzaax;
    private static final long zzaay;
    private static final long zzaaz;
    private static final long zzaba;
    private static final long zzabb;
    private static final long zzabc;
    private static final long zzabd;
    private static final long zzabe = ((long) zzi(Object[].class));
    private static final long zzabf = ((long) zzj(Object[].class));
    private static final long zzabg;
    private static final int zzabh = ((int) (zzaat & 7));
    static final boolean zzabi = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    private static final Class<?> zzrm = zzfa.zzds();
    private static final boolean zzsr = zzim();
    private static final Unsafe zzyt = zzil();

    private zzjp() {
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzjp.zzabi) {
                return zzjp.zzq(obj, j);
            }
            return zzjp.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzjp.zzabi) {
                zzjp.zza(obj, j, b);
            } else {
                zzjp.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzjp.zzabi) {
                return zzjp.zzs(obj, j);
            }
            return zzjp.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzjp.zzabi) {
                zzjp.zzb(obj, j, z);
            } else {
                zzjp.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            return this.zzabl.getByte(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            this.zzabl.putByte(obj, j, b);
        }

        public final boolean zzm(Object obj, long j) {
            return this.zzabl.getBoolean(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zzabl.putBoolean(obj, j, z);
        }

        public final float zzn(Object obj, long j) {
            return this.zzabl.getFloat(obj, j);
        }

        public final void zza(Object obj, long j, float f) {
            this.zzabl.putFloat(obj, j, f);
        }

        public final double zzo(Object obj, long j) {
            return this.zzabl.getDouble(obj, j);
        }

        public final void zza(Object obj, long j, double d) {
            this.zzabl.putDouble(obj, j, d);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzjp.zzabi) {
                return zzjp.zzq(obj, j);
            }
            return zzjp.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzjp.zzabi) {
                zzjp.zza(obj, j, b);
            } else {
                zzjp.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzjp.zzabi) {
                return zzjp.zzs(obj, j);
            }
            return zzjp.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzjp.zzabi) {
                zzjp.zzb(obj, j, z);
            } else {
                zzjp.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    static boolean zzij() {
        return zzsr;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
    static abstract class zzd {
        Unsafe zzabl;

        zzd(Unsafe unsafe) {
            this.zzabl = unsafe;
        }

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zze(Object obj, long j, byte b);

        public abstract boolean zzm(Object obj, long j);

        public abstract float zzn(Object obj, long j);

        public abstract double zzo(Object obj, long j);

        public abstract byte zzy(Object obj, long j);

        public final int zzk(Object obj, long j) {
            return this.zzabl.getInt(obj, j);
        }

        public final void zzb(Object obj, long j, int i) {
            this.zzabl.putInt(obj, j, i);
        }

        public final long zzl(Object obj, long j) {
            return this.zzabl.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzabl.putLong(obj, j, j2);
        }
    }

    static boolean zzik() {
        return zzaas;
    }

    static <T> T zzh(Class<T> cls) {
        try {
            return zzyt.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzi(Class<?> cls) {
        if (zzsr) {
            return zzaar.zzabl.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzj(Class<?> cls) {
        if (zzsr) {
            return zzaar.zzabl.arrayIndexScale(cls);
        }
        return -1;
    }

    static int zzk(Object obj, long j) {
        return zzaar.zzk(obj, j);
    }

    static void zzb(Object obj, long j, int i) {
        zzaar.zzb(obj, j, i);
    }

    static long zzl(Object obj, long j) {
        return zzaar.zzl(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzaar.zza(obj, j, j2);
    }

    static boolean zzm(Object obj, long j) {
        return zzaar.zzm(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzaar.zza(obj, j, z);
    }

    static float zzn(Object obj, long j) {
        return zzaar.zzn(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzaar.zza(obj, j, f);
    }

    static double zzo(Object obj, long j) {
        return zzaar.zzo(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzaar.zza(obj, j, d);
    }

    static Object zzp(Object obj, long j) {
        return zzaar.zzabl.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzaar.zzabl.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzaar.zzy(bArr, zzaat + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzaar.zze(bArr, zzaat + j, b);
    }

    static Unsafe zzil() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzjr());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzim() {
        Unsafe unsafe = zzyt;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("arrayBaseOffset", new Class[]{Class.class});
            cls.getMethod("arrayIndexScale", new Class[]{Class.class});
            cls.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            cls.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            if (zzfa.zzdr()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            cls.getMethod("getBoolean", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putBoolean", new Class[]{Object.class, Long.TYPE, Boolean.TYPE});
            cls.getMethod("getFloat", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putFloat", new Class[]{Object.class, Long.TYPE, Float.TYPE});
            cls.getMethod("getDouble", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putDouble", new Class[]{Object.class, Long.TYPE, Double.TYPE});
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzin() {
        Unsafe unsafe = zzyt;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            if (zzio() == null) {
                return false;
            }
            if (zzfa.zzdr()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Long.TYPE});
            cls.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            cls.getMethod("getInt", new Class[]{Long.TYPE});
            cls.getMethod("putInt", new Class[]{Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Long.TYPE});
            cls.getMethod("putLong", new Class[]{Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Long.TYPE, Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE});
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzk(Class<?> cls) {
        Class<byte[]> cls2 = byte[].class;
        if (!zzfa.zzdr()) {
            return false;
        }
        try {
            Class<?> cls3 = zzrm;
            cls3.getMethod("peekLong", new Class[]{cls, Boolean.TYPE});
            cls3.getMethod("pokeLong", new Class[]{cls, Long.TYPE, Boolean.TYPE});
            cls3.getMethod("pokeInt", new Class[]{cls, Integer.TYPE, Boolean.TYPE});
            cls3.getMethod("peekInt", new Class[]{cls, Boolean.TYPE});
            cls3.getMethod("pokeByte", new Class[]{cls, Byte.TYPE});
            cls3.getMethod("peekByte", new Class[]{cls});
            cls3.getMethod("pokeByteArray", new Class[]{cls, cls2, Integer.TYPE, Integer.TYPE});
            cls3.getMethod("peekByteArray", new Class[]{cls, cls2, Integer.TYPE, Integer.TYPE});
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static Field zzio() {
        Field zzb2;
        if (zzfa.zzdr() && (zzb2 = zzb(Buffer.class, "effectiveDirectAddress")) != null) {
            return zzb2;
        }
        Field zzb3 = zzb(Buffer.class, "address");
        if (zzb3 == null || zzb3.getType() != Long.TYPE) {
            return null;
        }
        return zzb3;
    }

    private static Field zzb(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static byte zzq(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) (((~j) & 3) << 3)));
    }

    /* access modifiers changed from: private */
    public static byte zzr(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) ((j & 3) << 3)));
    }

    /* access modifiers changed from: private */
    public static void zza(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int zzk = zzk(obj, j2);
        int i = ((~((int) j)) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (zzk & (~(255 << i))));
    }

    /* access modifiers changed from: private */
    public static void zzb(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int i = (((int) j) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (zzk(obj, j2) & (~(255 << i))));
    }

    /* access modifiers changed from: private */
    public static boolean zzs(Object obj, long j) {
        return zzq(obj, j) != 0;
    }

    /* access modifiers changed from: private */
    public static boolean zzt(Object obj, long j) {
        return zzr(obj, j) != 0;
    }

    /* access modifiers changed from: private */
    public static void zzb(Object obj, long j, boolean z) {
        zza(obj, j, z ? (byte) 1 : 0);
    }

    /* access modifiers changed from: private */
    public static void zzc(Object obj, long j, boolean z) {
        zzb(obj, j, z ? (byte) 1 : 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x00d2, code lost:
        r1 = zzaar;
     */
    static {
        /*
            java.lang.Class<double[]> r0 = double[].class
            java.lang.Class<float[]> r1 = float[].class
            java.lang.Class<long[]> r2 = long[].class
            java.lang.Class<int[]> r3 = int[].class
            java.lang.Class<boolean[]> r4 = boolean[].class
            java.lang.Class<com.google.android.gms.internal.vision.zzjp> r5 = com.google.android.gms.internal.vision.zzjp.class
            java.lang.String r5 = r5.getName()
            java.util.logging.Logger r5 = java.util.logging.Logger.getLogger(r5)
            logger = r5
            sun.misc.Unsafe r5 = zzil()
            zzyt = r5
            java.lang.Class r5 = com.google.android.gms.internal.vision.zzfa.zzds()
            zzrm = r5
            java.lang.Class r5 = java.lang.Long.TYPE
            boolean r5 = zzk(r5)
            zzaap = r5
            java.lang.Class r5 = java.lang.Integer.TYPE
            boolean r5 = zzk(r5)
            zzaaq = r5
            sun.misc.Unsafe r5 = zzyt
            r6 = 0
            if (r5 != 0) goto L_0x0038
            goto L_0x005d
        L_0x0038:
            boolean r5 = com.google.android.gms.internal.vision.zzfa.zzdr()
            if (r5 == 0) goto L_0x0056
            boolean r5 = zzaap
            if (r5 == 0) goto L_0x004a
            com.google.android.gms.internal.vision.zzjp$zzc r6 = new com.google.android.gms.internal.vision.zzjp$zzc
            sun.misc.Unsafe r5 = zzyt
            r6.<init>(r5)
            goto L_0x005d
        L_0x004a:
            boolean r5 = zzaaq
            if (r5 == 0) goto L_0x005d
            com.google.android.gms.internal.vision.zzjp$zza r6 = new com.google.android.gms.internal.vision.zzjp$zza
            sun.misc.Unsafe r5 = zzyt
            r6.<init>(r5)
            goto L_0x005d
        L_0x0056:
            com.google.android.gms.internal.vision.zzjp$zzb r6 = new com.google.android.gms.internal.vision.zzjp$zzb
            sun.misc.Unsafe r5 = zzyt
            r6.<init>(r5)
        L_0x005d:
            zzaar = r6
            boolean r5 = zzin()
            zzaas = r5
            boolean r5 = zzim()
            zzsr = r5
            java.lang.Class<byte[]> r5 = byte[].class
            int r5 = zzi(r5)
            long r5 = (long) r5
            zzaat = r5
            int r5 = zzi(r4)
            long r5 = (long) r5
            zzaau = r5
            int r4 = zzj(r4)
            long r4 = (long) r4
            zzaav = r4
            int r4 = zzi(r3)
            long r4 = (long) r4
            zzaaw = r4
            int r3 = zzj(r3)
            long r3 = (long) r3
            zzaax = r3
            int r3 = zzi(r2)
            long r3 = (long) r3
            zzaay = r3
            int r2 = zzj(r2)
            long r2 = (long) r2
            zzaaz = r2
            int r2 = zzi(r1)
            long r2 = (long) r2
            zzaba = r2
            int r1 = zzj(r1)
            long r1 = (long) r1
            zzabb = r1
            int r1 = zzi(r0)
            long r1 = (long) r1
            zzabc = r1
            int r0 = zzj(r0)
            long r0 = (long) r0
            zzabd = r0
            java.lang.Class<java.lang.Object[]> r0 = java.lang.Object[].class
            int r0 = zzi(r0)
            long r0 = (long) r0
            zzabe = r0
            java.lang.Class<java.lang.Object[]> r0 = java.lang.Object[].class
            int r0 = zzj(r0)
            long r0 = (long) r0
            zzabf = r0
            java.lang.reflect.Field r0 = zzio()
            if (r0 == 0) goto L_0x00de
            com.google.android.gms.internal.vision.zzjp$zzd r1 = zzaar
            if (r1 != 0) goto L_0x00d7
            goto L_0x00de
        L_0x00d7:
            sun.misc.Unsafe r1 = r1.zzabl
            long r0 = r1.objectFieldOffset(r0)
            goto L_0x00e0
        L_0x00de:
            r0 = -1
        L_0x00e0:
            zzabg = r0
            long r0 = zzaat
            r2 = 7
            long r0 = r0 & r2
            int r1 = (int) r0
            zzabh = r1
            java.nio.ByteOrder r0 = java.nio.ByteOrder.nativeOrder()
            java.nio.ByteOrder r1 = java.nio.ByteOrder.BIG_ENDIAN
            if (r0 != r1) goto L_0x00f4
            r0 = 1
            goto L_0x00f5
        L_0x00f4:
            r0 = 0
        L_0x00f5:
            zzabi = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjp.<clinit>():void");
    }
}
