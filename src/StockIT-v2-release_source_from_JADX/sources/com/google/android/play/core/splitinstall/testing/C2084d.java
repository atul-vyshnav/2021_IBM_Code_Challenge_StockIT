package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.splitinstall.SplitInstallException;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import java.util.ArrayList;

/* renamed from: com.google.android.play.core.splitinstall.testing.d */
final /* synthetic */ class C2084d implements C2090j {

    /* renamed from: a */
    private final SplitInstallRequest f1020a;

    C2084d(SplitInstallRequest splitInstallRequest) {
        this.f1020a = splitInstallRequest;
    }

    /* renamed from: a */
    public final SplitInstallSessionState mo34366a(SplitInstallSessionState splitInstallSessionState) {
        SplitInstallRequest splitInstallRequest = this.f1020a;
        int i = FakeSplitInstallManager.f993a;
        if (splitInstallSessionState == null || splitInstallSessionState.hasTerminalStatus()) {
            return SplitInstallSessionState.create(splitInstallSessionState == null ? 1 : 1 + splitInstallSessionState.sessionId(), 1, 0, 0, 0, splitInstallRequest.getModuleNames(), new ArrayList());
        }
        throw new SplitInstallException(-1);
    }
}
