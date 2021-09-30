package com.google.android.play.core.review.testing;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

public class FakeReviewManager implements ReviewManager {

    /* renamed from: a */
    private final Context f864a;

    /* renamed from: b */
    private ReviewInfo f865b;

    public FakeReviewManager(Context context) {
        this.f864a = context;
    }

    public Task<Void> launchReviewFlow(Activity activity, ReviewInfo reviewInfo) {
        return reviewInfo != this.f865b ? Tasks.m1078a((Exception) new C2016a()) : Tasks.m1079a(null);
    }

    public Task<ReviewInfo> requestReviewFlow() {
        ReviewInfo a = ReviewInfo.m877a(PendingIntent.getBroadcast(this.f864a, 0, new Intent(), 0));
        this.f865b = a;
        return Tasks.m1079a(a);
    }
}
