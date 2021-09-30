package com.google.android.gms.internal.play_billing;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public abstract class zzm<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] zza = new Object[0];

    zzm() {
    }

    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public final Object[] toArray() {
        return toArray(zza);
    }

    /* access modifiers changed from: package-private */
    public int zza(Object[] objArr, int i) {
        throw null;
    }

    /* access modifiers changed from: package-private */
    public int zzb() {
        throw null;
    }

    /* access modifiers changed from: package-private */
    public int zzc() {
        throw null;
    }

    /* renamed from: zzd */
    public abstract zzr<E> iterator();

    /* access modifiers changed from: package-private */
    @NullableDecl
    public Object[] zze() {
        throw null;
    }

    public final <T> T[] toArray(T[] tArr) {
        if (tArr != null) {
            int size = size();
            int length = tArr.length;
            if (length < size) {
                Object[] zze = zze();
                if (zze != null) {
                    return Arrays.copyOfRange(zze, zzc(), zzb(), tArr.getClass());
                }
                tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
            } else if (length > size) {
                tArr[size] = null;
            }
            zza(tArr, 0);
            return tArr;
        }
        throw null;
    }
}
