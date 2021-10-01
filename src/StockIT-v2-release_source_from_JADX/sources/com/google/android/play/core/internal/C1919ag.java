package com.google.android.play.core.internal;

import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.internal.ag */
public abstract class C1919ag implements Runnable {

    /* renamed from: a */
    private final C2106i<?> f774a;

    C1919ag() {
        this.f774a = null;
    }

    public C1919ag(C2106i<?> iVar) {
        this.f774a = iVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo33861a();

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final C2106i<?> mo34144b() {
        return this.f774a;
    }

    public final void run() {
        try {
            mo33861a();
        } catch (Exception e) {
            C2106i<?> iVar = this.f774a;
            if (iVar != null) {
                iVar.mo34397b(e);
            }
        }
    }
}
