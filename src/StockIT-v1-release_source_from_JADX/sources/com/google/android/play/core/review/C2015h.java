package com.google.android.play.core.review;

import android.content.Context;
import android.content.Intent;
import com.google.android.play.core.internal.C1914ab;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1928ap;
import com.google.android.play.core.tasks.C2106i;
import com.google.android.play.core.tasks.Task;

/* renamed from: com.google.android.play.core.review.h */
public final class C2015h {
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static final C1918af f861b = new C1918af("ReviewService");

    /* renamed from: a */
    final C1928ap<C1914ab> f862a;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final String f863c;

    public C2015h(Context context) {
        this.f863c = context.getPackageName();
        Context context2 = context;
        this.f862a = new C1928ap(context2, f861b, "com.google.android.finsky.inappreviewservice.InAppReviewService", new Intent("com.google.android.finsky.BIND_IN_APP_REVIEW_SERVICE").setPackage("com.android.vending"), C2011d.f855a);
    }

    /* renamed from: a */
    public final Task<ReviewInfo> mo34267a() {
        f861b.mo34142c("requestInAppReview (%s)", this.f863c);
        C2106i iVar = new C2106i();
        this.f862a.mo34151a((C1919ag) new C2012e(this, iVar, iVar));
        return iVar.mo34394a();
    }
}
