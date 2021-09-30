package com.google.android.play.core.splitinstall;

import android.app.Activity;
import android.content.IntentSender;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.internal.C1976cj;
import com.google.android.play.core.splitinstall.testing.FakeSplitInstallManager;
import com.google.android.play.core.tasks.Task;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitinstall.i */
final class C2068i implements SplitInstallManager {

    /* renamed from: a */
    private final C1976cj<C2094w> f966a;

    /* renamed from: b */
    private final C1976cj<FakeSplitInstallManager> f967b;

    /* renamed from: c */
    private final C1976cj<File> f968c;

    C2068i(C1976cj<C2094w> cjVar, C1976cj<FakeSplitInstallManager> cjVar2, C1976cj<File> cjVar3) {
        this.f966a = cjVar;
        this.f967b = cjVar2;
        this.f968c = cjVar3;
    }

    /* renamed from: a */
    private final SplitInstallManager m1013a() {
        return (SplitInstallManager) (this.f968c.mo34195a() == null ? this.f966a : this.f967b).mo34195a();
    }

    public final Task<Void> cancelInstall(int i) {
        return m1013a().cancelInstall(i);
    }

    public final Task<Void> deferredInstall(List<String> list) {
        return m1013a().deferredInstall(list);
    }

    public final Task<Void> deferredLanguageInstall(List<Locale> list) {
        return m1013a().deferredLanguageInstall(list);
    }

    public final Task<Void> deferredLanguageUninstall(List<Locale> list) {
        return m1013a().deferredLanguageUninstall(list);
    }

    public final Task<Void> deferredUninstall(List<String> list) {
        return m1013a().deferredUninstall(list);
    }

    public final Set<String> getInstalledLanguages() {
        return m1013a().getInstalledLanguages();
    }

    public final Set<String> getInstalledModules() {
        return m1013a().getInstalledModules();
    }

    public final Task<SplitInstallSessionState> getSessionState(int i) {
        return m1013a().getSessionState(i);
    }

    public final Task<List<SplitInstallSessionState>> getSessionStates() {
        return m1013a().getSessionStates();
    }

    public final void registerListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        m1013a().registerListener(splitInstallStateUpdatedListener);
    }

    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, Activity activity, int i) throws IntentSender.SendIntentException {
        return m1013a().startConfirmationDialogForResult(splitInstallSessionState, activity, i);
    }

    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, IntentSenderForResultStarter intentSenderForResultStarter, int i) throws IntentSender.SendIntentException {
        return m1013a().startConfirmationDialogForResult(splitInstallSessionState, intentSenderForResultStarter, i);
    }

    public final Task<Integer> startInstall(SplitInstallRequest splitInstallRequest) {
        return m1013a().startInstall(splitInstallRequest);
    }

    public final void unregisterListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        m1013a().unregisterListener(splitInstallStateUpdatedListener);
    }
}
