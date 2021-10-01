package com.google.android.play.core.internal;

import com.google.android.play.core.listener.StateUpdatedListener;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.ae */
public final class C1917ae<StateT> {

    /* renamed from: a */
    protected final Set<StateUpdatedListener<StateT>> f772a = new HashSet();

    /* renamed from: a */
    public final synchronized void mo34136a(StateUpdatedListener<StateT> stateUpdatedListener) {
        this.f772a.add(stateUpdatedListener);
    }

    /* renamed from: a */
    public final synchronized void mo34137a(StateT statet) {
        for (StateUpdatedListener<StateT> onStateUpdate : this.f772a) {
            onStateUpdate.onStateUpdate(statet);
        }
    }

    /* renamed from: b */
    public final synchronized void mo34138b(StateUpdatedListener<StateT> stateUpdatedListener) {
        this.f772a.remove(stateUpdatedListener);
    }
}
