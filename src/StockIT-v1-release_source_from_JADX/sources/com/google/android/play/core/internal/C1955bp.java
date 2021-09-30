package com.google.android.play.core.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

/* renamed from: com.google.android.play.core.internal.bp */
public final class C1955bp<T> {

    /* renamed from: a */
    private final Object f806a;

    /* renamed from: b */
    private final Field f807b;

    /* renamed from: c */
    private final Class<T> f808c;

    C1955bp(Object obj, Field field, Class<T> cls) {
        this.f806a = obj;
        this.f807b = field;
        this.f808c = cls;
    }

    C1955bp(Object obj, Field field, Class cls, byte[] bArr) {
        this(obj, field, Array.newInstance(cls, 0).getClass());
    }

    /* renamed from: c */
    private Class m697c() {
        return mo34166b().getType().getComponentType();
    }

    /* renamed from: a */
    public final T mo34163a() {
        try {
            return this.f808c.cast(this.f807b.get(this.f806a));
        } catch (Exception e) {
            throw new C1957br(String.format("Failed to get value of field %s of type %s on object of type %s", new Object[]{this.f807b.getName(), this.f806a.getClass().getName(), this.f808c.getName()}), e);
        }
    }

    /* renamed from: a */
    public final void mo34164a(T t) {
        try {
            this.f807b.set(this.f806a, t);
        } catch (Exception e) {
            throw new C1957br(String.format("Failed to set value of field %s of type %s on object of type %s", new Object[]{this.f807b.getName(), this.f806a.getClass().getName(), this.f808c.getName()}), e);
        }
    }

    /* renamed from: a */
    public void mo34165a(Collection collection) {
        Object[] objArr = (Object[]) mo34163a();
        int length = objArr == null ? 0 : objArr.length;
        Object[] objArr2 = (Object[]) Array.newInstance(m697c(), collection.size() + length);
        if (objArr != null) {
            System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
        }
        for (Object obj : collection) {
            objArr2[length] = obj;
            length++;
        }
        mo34164a(objArr2);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final Field mo34166b() {
        return this.f807b;
    }

    /* renamed from: b */
    public void mo34167b(Collection collection) {
        Object[] objArr = (Object[]) mo34163a();
        int i = 0;
        Object[] objArr2 = (Object[]) Array.newInstance(m697c(), (objArr == null ? 0 : objArr.length) + collection.size());
        if (objArr != null) {
            System.arraycopy(objArr, 0, objArr2, collection.size(), objArr.length);
        }
        for (Object obj : collection) {
            objArr2[i] = obj;
            i++;
        }
        mo34164a(objArr2);
    }
}
