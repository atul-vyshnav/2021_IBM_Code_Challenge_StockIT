package com.google.android.play.core.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* renamed from: com.google.android.play.core.internal.ao */
final class C1927ao implements ServiceConnection {

    /* renamed from: a */
    final /* synthetic */ C1928ap f782a;

    /* synthetic */ C1927ao(C1928ap apVar) {
        this.f782a = apVar;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f782a.f785c.mo34142c("ServiceConnectionImpl.onServiceConnected(%s)", componentName);
        this.f782a.m630b((C1919ag) new C1925am(this, iBinder));
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        this.f782a.f785c.mo34142c("ServiceConnectionImpl.onServiceDisconnected(%s)", componentName);
        this.f782a.m630b((C1919ag) new C1926an(this));
    }
}
