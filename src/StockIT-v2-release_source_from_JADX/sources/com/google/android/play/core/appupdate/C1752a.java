package com.google.android.play.core.appupdate;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.listener.C2005b;

/* renamed from: com.google.android.play.core.appupdate.a */
public final class C1752a extends C2005b<InstallState> {
    public C1752a(Context context) {
        super(new C1918af("AppUpdateListenerRegistry"), new IntentFilter("com.google.android.play.core.install.ACTION_INSTALL_STATUS"), context);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33854a(Context context, Intent intent) {
        Intent intent2 = intent;
        if (!context.getPackageName().equals(intent2.getStringExtra("package.name"))) {
            this.f836a.mo34139a("ListenerRegistryBroadcastReceiver received broadcast for third party app: %s", intent2.getStringExtra("package.name"));
            return;
        }
        this.f836a.mo34139a("List of extras in received intent:", new Object[0]);
        for (String str : intent.getExtras().keySet()) {
            this.f836a.mo34139a("Key: %s; value: %s", str, intent.getExtras().get(str));
        }
        C1918af afVar = this.f836a;
        afVar.mo34139a("List of extras in received intent needed by fromUpdateIntent:", new Object[0]);
        afVar.mo34139a("Key: %s; value: %s", "install.status", Integer.valueOf(intent2.getIntExtra("install.status", 0)));
        afVar.mo34139a("Key: %s; value: %s", "error.code", Integer.valueOf(intent2.getIntExtra("error.code", 0)));
        InstallState a = InstallState.m600a(intent2.getIntExtra("install.status", 0), intent2.getLongExtra("bytes.downloaded", 0), intent2.getLongExtra("total.bytes.to.download", 0), intent2.getIntExtra("error.code", 0), intent2.getStringExtra("package.name"));
        this.f836a.mo34139a("ListenerRegistryBroadcastReceiver.onReceive: %s", a);
        mo34245a(a);
    }
}
