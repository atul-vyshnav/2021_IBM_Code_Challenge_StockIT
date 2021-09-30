package com.google.android.play.core.assetpacks;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.i */
final class C1887i extends ResultReceiver {

    /* renamed from: a */
    final /* synthetic */ C2106i f718a;

    /* renamed from: b */
    final /* synthetic */ C1888j f719b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1887i(C1888j jVar, Handler handler, C2106i iVar) {
        super(handler);
        this.f719b = jVar;
        this.f718a = iVar;
    }

    public final void onReceiveResult(int i, Bundle bundle) {
        if (i == 1) {
            this.f718a.mo34398b(-1);
            this.f719b.f727h.mo34015a((PendingIntent) null);
        } else if (i != 2) {
            this.f718a.mo34397b((Exception) new AssetPackException(-100));
        } else {
            this.f718a.mo34398b(0);
        }
    }
}
