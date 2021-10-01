package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzfd;
import com.google.android.gms.internal.measurement.zzfd.zzb;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
public abstract class zzfd<MessageType extends zzfd<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzdl<MessageType, BuilderType> {
    private static Map<Object, zzfd<?, ?>> zzd = new ConcurrentHashMap();
    protected zzhy zzb = zzhy.zza();
    private int zzc = -1;

    /* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
    public static class zza<T extends zzfd<T, ?>> extends zzdp<T> {
        private final T zza;

        public zza(T t) {
            this.zza = t;
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
    static final class zzc implements zzey<zzc> {
        public final int zza() {
            throw new NoSuchMethodError();
        }

        public final zzim zzb() {
            throw new NoSuchMethodError();
        }

        public final zzip zzc() {
            throw new NoSuchMethodError();
        }

        public final boolean zzd() {
            throw new NoSuchMethodError();
        }

        public final boolean zze() {
            throw new NoSuchMethodError();
        }

        public final zzgn zza(zzgn zzgn, zzgo zzgo) {
            throw new NoSuchMethodError();
        }

        public final zzgt zza(zzgt zzgt, zzgt zzgt2) {
            throw new NoSuchMethodError();
        }

        public final /* synthetic */ int compareTo(Object obj) {
            throw new NoSuchMethodError();
        }
    }

    /* 'enum' modifier removed */
    /* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
    public static final class zze {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        public static final int zze = 5;
        public static final int zzf = 6;
        public static final int zzg = 7;
        public static final int zzh = 1;
        public static final int zzi = 2;
        public static final int zzj = 1;
        public static final int zzk = 2;
        private static final /* synthetic */ int[] zzl = {1, 2, 3, 4, 5, 6, 7};
        private static final /* synthetic */ int[] zzm = {1, 2};
        private static final /* synthetic */ int[] zzn = {1, 2};

        public static int[] zza() {
            return (int[]) zzl.clone();
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
    public static class zzf<ContainingType extends zzgo, Type> extends zzer<ContainingType, Type> {
    }

    /* access modifiers changed from: protected */
    public abstract Object zza(int i, Object obj, Object obj2);

    /* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
    public static abstract class zzd<MessageType extends zzd<MessageType, BuilderType>, BuilderType> extends zzfd<MessageType, BuilderType> implements zzgq {
        protected zzew<zzc> zzc = zzew.zza();

        /* access modifiers changed from: package-private */
        public final zzew<zzc> zza() {
            if (this.zzc.zzc()) {
                this.zzc = (zzew) this.zzc.clone();
            }
            return this.zzc;
        }
    }

    public String toString() {
        return zzgp.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zza != 0) {
            return this.zza;
        }
        this.zza = zzgz.zza().zza(this).zza(this);
        return this.zza;
    }

    /* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
    public static abstract class zzb<MessageType extends zzfd<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzdn<MessageType, BuilderType> {
        protected MessageType zza;
        private final MessageType zzb;
        private boolean zzc = false;

        protected zzb(MessageType messagetype) {
            this.zzb = messagetype;
            this.zza = (zzfd) messagetype.zza(zze.zzd, (Object) null, (Object) null);
        }

        /* access modifiers changed from: protected */
        public final void zzp() {
            if (this.zzc) {
                MessageType messagetype = (zzfd) this.zza.zza(zze.zzd, (Object) null, (Object) null);
                zza(messagetype, this.zza);
                this.zza = messagetype;
                this.zzc = false;
            }
        }

        public final boolean zzbk() {
            return zzfd.zza(this.zza, false);
        }

        /* renamed from: zzq */
        public MessageType zzs() {
            if (this.zzc) {
                return this.zza;
            }
            MessageType messagetype = this.zza;
            zzgz.zza().zza(messagetype).zzc(messagetype);
            this.zzc = true;
            return this.zza;
        }

        /* renamed from: zzr */
        public final MessageType zzt() {
            MessageType messagetype = (zzfd) zzs();
            if (messagetype.zzbk()) {
                return messagetype;
            }
            throw new zzhw(messagetype);
        }

        public final BuilderType zza(MessageType messagetype) {
            zzp();
            zza(this.zza, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzgz.zza().zza(messagetype).zzb(messagetype, messagetype2);
        }

        private final BuilderType zzb(byte[] bArr, int i, int i2, zzeq zzeq) throws zzfo {
            zzp();
            try {
                zzgz.zza().zza(this.zza).zza(this.zza, bArr, 0, i2 + 0, new zzdt(zzeq));
                return this;
            } catch (zzfo e) {
                throw e;
            } catch (IndexOutOfBoundsException unused) {
                throw zzfo.zza();
            } catch (IOException e2) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: zzb */
        public final BuilderType zza(zzeg zzeg, zzeq zzeq) throws IOException {
            zzp();
            try {
                zzgz.zza().zza(this.zza).zza(this.zza, zzel.zza(zzeg), zzeq);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }

        public final /* synthetic */ zzdn zza(byte[] bArr, int i, int i2, zzeq zzeq) throws zzfo {
            return zzb(bArr, 0, i2, zzeq);
        }

        public final /* synthetic */ zzdn zza(byte[] bArr, int i, int i2) throws zzfo {
            return zzb(bArr, 0, i2, zzeq.zza());
        }

        public final /* synthetic */ zzdn zzo() {
            return (zzb) clone();
        }

        public final /* synthetic */ zzgo zzbs() {
            return this.zzb;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zzb zzb2 = (zzb) ((zzfd) this.zzb).zza(zze.zze, (Object) null, (Object) null);
            zzb2.zza((zzfd) zzs());
            return zzb2;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!((zzfd) zza(zze.zzf, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return false;
        }
        return zzgz.zza().zza(this).zza(this, (zzfd) obj);
    }

    /* access modifiers changed from: protected */
    public final <MessageType extends zzfd<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> BuilderType zzbj() {
        return (zzb) zza(zze.zze, (Object) null, (Object) null);
    }

    public final boolean zzbk() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    public final BuilderType zzbl() {
        BuilderType buildertype = (zzb) zza(zze.zze, (Object) null, (Object) null);
        buildertype.zza(this);
        return buildertype;
    }

    /* access modifiers changed from: package-private */
    public final int zzbi() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final void zzc(int i) {
        this.zzc = i;
    }

    public final void zza(zzen zzen) throws IOException {
        zzgz.zza().zza(this).zza(this, (zzis) zzep.zza(zzen));
    }

    public final int zzbm() {
        if (this.zzc == -1) {
            this.zzc = zzgz.zza().zza(this).zzb(this);
        }
        return this.zzc;
    }

    static <T extends zzfd<?, ?>> T zza(Class<T> cls) {
        T t = (zzfd) zzd.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzfd) zzd.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t == null) {
            t = (zzfd) ((zzfd) zzib.zza(cls)).zza(zze.zzf, (Object) null, (Object) null);
            if (t != null) {
                zzd.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzfd<?, ?>> void zza(Class<T> cls, T t) {
        zzd.put(cls, t);
    }

    protected static Object zza(zzgo zzgo, String str, Object[] objArr) {
        return new zzhb(zzgo, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    protected static final <T extends zzfd<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zze.zza, (Object) null, (Object) null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzd2 = zzgz.zza().zza(t).zzd(t);
        if (z) {
            t.zza(zze.zzb, (Object) zzd2 ? t : null, (Object) null);
        }
        return zzd2;
    }

    protected static zzfj zzbn() {
        return zzfg.zzd();
    }

    protected static zzfm zzbo() {
        return zzgc.zzd();
    }

    protected static zzfm zza(zzfm zzfm) {
        int size = zzfm.size();
        return zzfm.zzc(size == 0 ? 10 : size << 1);
    }

    protected static <E> zzfl<E> zzbp() {
        return zzhc.zzd();
    }

    protected static <E> zzfl<E> zza(zzfl<E> zzfl) {
        int size = zzfl.size();
        return zzfl.zza(size == 0 ? 10 : size << 1);
    }

    public final /* synthetic */ zzgn zzbq() {
        zzb zzb2 = (zzb) zza(zze.zze, (Object) null, (Object) null);
        zzb2.zza(this);
        return zzb2;
    }

    public final /* synthetic */ zzgn zzbr() {
        return (zzb) zza(zze.zze, (Object) null, (Object) null);
    }

    public final /* synthetic */ zzgo zzbs() {
        return (zzfd) zza(zze.zzf, (Object) null, (Object) null);
    }
}
