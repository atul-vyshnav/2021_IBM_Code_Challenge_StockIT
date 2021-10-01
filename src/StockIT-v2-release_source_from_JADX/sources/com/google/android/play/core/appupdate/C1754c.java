package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.appupdate.c */
final class C1754c extends ResultReceiver {

    /* renamed from: a */
    final /* synthetic */ C2106i f264a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1754c(Handler handler, C2106i iVar) {
        super(handler);
        this.f264a = iVar;
    }

    public final void onReceiveResult(int i, Bundle bundle) {
        C2106i iVar;
        int i2 = 1;
        if (i == 1) {
            iVar = this.f264a;
            i2 = -1;
        } else if (i != 2) {
            iVar = this.f264a;
        } else {
            iVar = this.f264a;
            i2 = 0;
        }
        iVar.mo34398b(Integer.valueOf(i2));
    }
}
