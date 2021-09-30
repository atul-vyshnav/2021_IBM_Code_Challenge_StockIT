package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.listener.C2005b;

/* renamed from: com.google.android.play.core.splitinstall.t */
public final class C2080t extends C2005b<SplitInstallSessionState> {

    /* renamed from: c */
    private static C2080t f990c;

    /* renamed from: d */
    private final Handler f991d = new Handler(Looper.getMainLooper());

    /* renamed from: e */
    private final C2064e f992e;

    public C2080t(Context context, C2064e eVar) {
        super(new C1918af("SplitInstallListenerRegistry"), new IntentFilter("com.google.android.play.core.splitinstall.receiver.SplitInstallUpdateIntentService"), context);
        this.f992e = eVar;
    }

    /* renamed from: a */
    public static synchronized C2080t m1037a(Context context) {
        C2080t tVar;
        synchronized (C2080t.class) {
            if (f990c == null) {
                f990c = new C2080t(context, C2071l.f973a);
            }
            tVar = f990c;
        }
        return tVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33854a(Context context, Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra("session_state");
        if (bundleExtra != null) {
            SplitInstallSessionState a = SplitInstallSessionState.m940a(bundleExtra);
            this.f836a.mo34139a("ListenerRegistryBroadcastReceiver.onReceive: %s", a);
            C2065f a2 = this.f992e.mo34351a();
            if (a.status() != 3 || a2 == null) {
                mo34245a(a);
            } else {
                a2.mo34155a(a.mo34321c(), new C2078r(this, a, intent, context));
            }
        }
    }
}
