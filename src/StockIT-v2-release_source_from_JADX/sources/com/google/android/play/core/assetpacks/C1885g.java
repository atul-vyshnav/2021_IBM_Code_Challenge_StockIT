package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.OnSuccessListener;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.g */
final /* synthetic */ class C1885g implements OnSuccessListener {

    /* renamed from: a */
    private final C1806bb f716a;

    private C1885g(C1806bb bbVar) {
        this.f716a = bbVar;
    }

    /* renamed from: a */
    static OnSuccessListener m553a(C1806bb bbVar) {
        return new C1885g(bbVar);
    }

    public final void onSuccess(Object obj) {
        this.f716a.mo33967a((List<String>) (List) obj);
    }
}
