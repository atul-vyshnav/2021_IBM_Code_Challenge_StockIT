package com.google.android.play.core.internal;

import android.content.Intent;
import android.util.Log;
import com.google.android.play.core.splitinstall.C2063d;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.ar */
final class C1930ar implements Runnable {

    /* renamed from: a */
    final /* synthetic */ List f795a;

    /* renamed from: b */
    final /* synthetic */ C2063d f796b;

    /* renamed from: c */
    final /* synthetic */ C1931as f797c;

    C1930ar(C1931as asVar, List list, C2063d dVar) {
        this.f797c = asVar;
        this.f795a = list;
        this.f796b = dVar;
    }

    public final void run() {
        try {
            if (this.f797c.f800c.mo34157a((List<Intent>) this.f795a)) {
                C1931as.m646a(this.f797c, this.f796b);
            } else {
                C1931as.m647a(this.f797c, this.f795a, this.f796b);
            }
        } catch (Exception e) {
            Log.e("SplitCompat", "Error checking verified files.", e);
            this.f796b.mo34349a(-11);
        }
    }
}
