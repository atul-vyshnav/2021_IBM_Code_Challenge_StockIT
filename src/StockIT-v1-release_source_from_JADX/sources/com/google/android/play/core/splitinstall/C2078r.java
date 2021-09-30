package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;

/* renamed from: com.google.android.play.core.splitinstall.r */
final class C2078r implements C2063d {

    /* renamed from: a */
    final /* synthetic */ SplitInstallSessionState f982a;

    /* renamed from: b */
    final /* synthetic */ Intent f983b;

    /* renamed from: c */
    final /* synthetic */ Context f984c;

    /* renamed from: d */
    final /* synthetic */ C2080t f985d;

    C2078r(C2080t tVar, SplitInstallSessionState splitInstallSessionState, Intent intent, Context context) {
        this.f985d = tVar;
        this.f982a = splitInstallSessionState;
        this.f983b = intent;
        this.f984c = context;
    }

    /* renamed from: a */
    public final void mo34348a() {
        this.f985d.f991d.post(new C2079s(this.f985d, this.f982a, 5, 0));
    }

    /* renamed from: a */
    public final void mo34349a(int i) {
        this.f985d.f991d.post(new C2079s(this.f985d, this.f982a, 6, i));
    }

    /* renamed from: b */
    public final void mo34350b() {
        if (!this.f983b.getBooleanExtra("triggered_from_app_after_verification", false)) {
            this.f983b.putExtra("triggered_from_app_after_verification", true);
            this.f984c.sendBroadcast(this.f983b);
            return;
        }
        this.f985d.f836a.mo34141b("Splits copied and verified more than once.", new Object[0]);
    }
}
