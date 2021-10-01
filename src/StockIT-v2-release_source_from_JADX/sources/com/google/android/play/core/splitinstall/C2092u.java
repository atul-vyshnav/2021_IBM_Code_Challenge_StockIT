package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.u */
final class C2092u implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SplitInstallRequest f1043a;

    /* renamed from: b */
    final /* synthetic */ C2094w f1044b;

    C2092u(C2094w wVar, SplitInstallRequest splitInstallRequest) {
        this.f1044b = wVar;
        this.f1043a = splitInstallRequest;
    }

    public final void run() {
        C2080t a = this.f1044b.f1047b;
        List<String> moduleNames = this.f1043a.getModuleNames();
        List a2 = C2094w.m1071b(this.f1043a.getLanguages());
        Bundle bundle = new Bundle();
        bundle.putInt("session_id", 0);
        bundle.putInt("status", 5);
        bundle.putInt("error_code", 0);
        if (!moduleNames.isEmpty()) {
            bundle.putStringArrayList("module_names", new ArrayList(moduleNames));
        }
        if (!a2.isEmpty()) {
            bundle.putStringArrayList("languages", new ArrayList(a2));
        }
        bundle.putLong("total_bytes_to_download", 0);
        bundle.putLong("bytes_downloaded", 0);
        a.mo34245a(SplitInstallSessionState.m940a(bundle));
    }
}
