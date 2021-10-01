package com.google.android.play.core.review;

import android.content.Context;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.splitcompat.C2032p;

public class ReviewManagerFactory {
    private ReviewManagerFactory() {
    }

    public static ReviewManager create(Context context) {
        PlayCoreDialogWrapperActivity.m591a(context);
        return new C2010c(new C2015h(C2032p.m933a(context)));
    }
}
