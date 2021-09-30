package com.google.android.play.core.splitinstall;

/* renamed from: com.google.android.play.core.splitinstall.s */
final class C2079s implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SplitInstallSessionState f986a;

    /* renamed from: b */
    final /* synthetic */ int f987b;

    /* renamed from: c */
    final /* synthetic */ int f988c;

    /* renamed from: d */
    final /* synthetic */ C2080t f989d;

    C2079s(C2080t tVar, SplitInstallSessionState splitInstallSessionState, int i, int i2) {
        this.f989d = tVar;
        this.f986a = splitInstallSessionState;
        this.f987b = i;
        this.f988c = i2;
    }

    public final void run() {
        C2080t tVar = this.f989d;
        SplitInstallSessionState splitInstallSessionState = this.f986a;
        tVar.mo34245a(new C2034a(splitInstallSessionState.sessionId(), this.f987b, this.f988c, splitInstallSessionState.bytesDownloaded(), splitInstallSessionState.totalBytesToDownload(), splitInstallSessionState.mo34318a(), splitInstallSessionState.mo34319b(), splitInstallSessionState.resolutionIntent(), splitInstallSessionState.mo34321c()));
    }
}
