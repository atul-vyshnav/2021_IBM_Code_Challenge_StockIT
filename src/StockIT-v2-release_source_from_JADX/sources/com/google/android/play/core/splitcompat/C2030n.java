package com.google.android.play.core.splitcompat;

import android.util.Log;

/* renamed from: com.google.android.play.core.splitcompat.n */
final class C2030n implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SplitCompat f892a;

    C2030n(SplitCompat splitCompat) {
        this.f892a = splitCompat;
    }

    public final void run() {
        try {
            this.f892a.f867b.mo34274a();
        } catch (Exception e) {
            Log.e("SplitCompat", "Failed to cleanup splitcompat storage", e);
        }
    }
}
