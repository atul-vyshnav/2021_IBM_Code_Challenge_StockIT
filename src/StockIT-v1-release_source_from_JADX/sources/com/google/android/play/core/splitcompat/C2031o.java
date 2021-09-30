package com.google.android.play.core.splitcompat;

import android.util.Log;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitcompat.o */
final class C2031o implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Set f893a;

    /* renamed from: b */
    final /* synthetic */ SplitCompat f894b;

    C2031o(SplitCompat splitCompat, Set set) {
        this.f894b = splitCompat;
        this.f893a = set;
    }

    public final void run() {
        try {
            for (String f : this.f893a) {
                this.f894b.f867b.mo34284f(f);
            }
        } catch (Exception e) {
            Log.e("SplitCompat", "Failed to remove from splitcompat storage split that is already installed", e);
        }
    }
}
