package com.google.android.play.core.review;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.review.b */
final class C2009b extends ResultReceiver {

    /* renamed from: a */
    final /* synthetic */ C2106i f852a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2009b(Handler handler, C2106i iVar) {
        super(handler);
        this.f852a = iVar;
    }

    public final void onReceiveResult(int i, Bundle bundle) {
        this.f852a.mo34398b(null);
    }
}
