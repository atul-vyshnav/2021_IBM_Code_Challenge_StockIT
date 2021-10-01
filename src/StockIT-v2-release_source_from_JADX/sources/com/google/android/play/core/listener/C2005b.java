package com.google.android.play.core.listener;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1934av;
import com.google.android.play.core.splitcompat.C2032p;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* renamed from: com.google.android.play.core.listener.b */
public abstract class C2005b<StateT> {

    /* renamed from: a */
    protected final C1918af f836a;

    /* renamed from: b */
    protected final Set<StateUpdatedListener<StateT>> f837b = new HashSet();

    /* renamed from: c */
    private final IntentFilter f838c;

    /* renamed from: d */
    private final Context f839d;

    /* renamed from: e */
    private C2004a f840e = null;

    /* renamed from: f */
    private volatile boolean f841f = false;

    protected C2005b(C1918af afVar, IntentFilter intentFilter, Context context) {
        this.f836a = afVar;
        this.f838c = intentFilter;
        this.f839d = C2032p.m933a(context);
    }

    /* renamed from: c */
    private final void m858c() {
        C2004a aVar;
        if ((this.f841f || !this.f837b.isEmpty()) && this.f840e == null) {
            C2004a aVar2 = new C2004a(this);
            this.f840e = aVar2;
            this.f839d.registerReceiver(aVar2, this.f838c);
        }
        if (!this.f841f && this.f837b.isEmpty() && (aVar = this.f840e) != null) {
            this.f839d.unregisterReceiver(aVar);
            this.f840e = null;
        }
    }

    /* renamed from: a */
    public final synchronized void mo34243a() {
        this.f836a.mo34142c("clearListeners", new Object[0]);
        this.f837b.clear();
        m858c();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo33854a(Context context, Intent intent);

    /* renamed from: a */
    public final synchronized void mo34244a(StateUpdatedListener<StateT> stateUpdatedListener) {
        this.f836a.mo34142c("registerListener", new Object[0]);
        C1934av.m656a(stateUpdatedListener, (Object) "Registered Play Core listener should not be null.");
        this.f837b.add(stateUpdatedListener);
        m858c();
    }

    /* renamed from: a */
    public final synchronized void mo34245a(StateT statet) {
        Iterator it = new HashSet(this.f837b).iterator();
        while (it.hasNext()) {
            ((StateUpdatedListener) it.next()).onStateUpdate(statet);
        }
    }

    /* renamed from: a */
    public final synchronized void mo34246a(boolean z) {
        this.f841f = z;
        m858c();
    }

    /* renamed from: b */
    public final synchronized void mo34247b(StateUpdatedListener<StateT> stateUpdatedListener) {
        this.f836a.mo34142c("unregisterListener", new Object[0]);
        C1934av.m656a(stateUpdatedListener, (Object) "Unregistered Play Core listener should not be null.");
        this.f837b.remove(stateUpdatedListener);
        m858c();
    }

    /* renamed from: b */
    public final synchronized boolean mo34248b() {
        return this.f840e != null;
    }
}
