package com.google.android.play.core.review;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.tasks.C2106i;
import com.google.android.play.core.tasks.Task;

/* renamed from: com.google.android.play.core.review.c */
public final class C2010c implements ReviewManager {

    /* renamed from: a */
    private final C2015h f853a;

    /* renamed from: b */
    private final Handler f854b = new Handler(Looper.getMainLooper());

    C2010c(C2015h hVar) {
        this.f853a = hVar;
    }

    public final Task<Void> launchReviewFlow(Activity activity, ReviewInfo reviewInfo) {
        Intent intent = new Intent(activity, PlayCoreDialogWrapperActivity.class);
        intent.putExtra("confirmation_intent", reviewInfo.mo34258a());
        C2106i iVar = new C2106i();
        intent.putExtra("result_receiver", new C2009b(this.f854b, iVar));
        activity.startActivity(intent);
        return iVar.mo34394a();
    }

    public final Task<ReviewInfo> requestReviewFlow() {
        return this.f853a.mo34267a();
    }
}
