package com.google.android.play.core.appupdate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.tasks.C2106i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

/* renamed from: com.google.android.play.core.appupdate.e */
final class C1756e implements AppUpdateManager {

    /* renamed from: a */
    private final C1767p f266a;

    /* renamed from: b */
    private final C1752a f267b;

    /* renamed from: c */
    private final Context f268c;

    /* renamed from: d */
    private final Handler f269d = new Handler(Looper.getMainLooper());

    C1756e(C1767p pVar, C1752a aVar, Context context) {
        this.f266a = pVar;
        this.f267b = aVar;
        this.f268c = context;
    }

    public final Task<Void> completeUpdate() {
        return this.f266a.mo33865b(this.f268c.getPackageName());
    }

    public final Task<AppUpdateInfo> getAppUpdateInfo() {
        return this.f266a.mo33864a(this.f268c.getPackageName());
    }

    public final synchronized void registerListener(InstallStateUpdatedListener installStateUpdatedListener) {
        this.f267b.mo34244a(installStateUpdatedListener);
    }

    public final Task<Integer> startUpdateFlow(AppUpdateInfo appUpdateInfo, Activity activity, AppUpdateOptions appUpdateOptions) {
        PlayCoreDialogWrapperActivity.m591a(this.f268c);
        if (!appUpdateInfo.isUpdateTypeAllowed(appUpdateOptions)) {
            return Tasks.m1078a((Exception) new InstallException(-6));
        }
        Intent intent = new Intent(activity, PlayCoreDialogWrapperActivity.class);
        intent.putExtra("confirmation_intent", appUpdateInfo.mo33824a(appUpdateOptions));
        C2106i iVar = new C2106i();
        intent.putExtra("result_receiver", new C1754c(this.f269d, iVar));
        activity.startActivity(intent);
        return iVar.mo34394a();
    }

    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, int i, Activity activity, int i2) throws IntentSender.SendIntentException {
        return startUpdateFlowForResult(appUpdateInfo, (IntentSenderForResultStarter) new C1755d(activity), AppUpdateOptions.defaultOptions(i), i2);
    }

    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, int i, IntentSenderForResultStarter intentSenderForResultStarter, int i2) throws IntentSender.SendIntentException {
        return startUpdateFlowForResult(appUpdateInfo, intentSenderForResultStarter, AppUpdateOptions.defaultOptions(i), i2);
    }

    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, Activity activity, AppUpdateOptions appUpdateOptions, int i) throws IntentSender.SendIntentException {
        return startUpdateFlowForResult(appUpdateInfo, (IntentSenderForResultStarter) new C1755d(activity), appUpdateOptions, i);
    }

    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, IntentSenderForResultStarter intentSenderForResultStarter, AppUpdateOptions appUpdateOptions, int i) throws IntentSender.SendIntentException {
        if (!appUpdateInfo.isUpdateTypeAllowed(appUpdateOptions)) {
            return false;
        }
        intentSenderForResultStarter.startIntentSenderForResult(appUpdateInfo.mo33824a(appUpdateOptions).getIntentSender(), i, (Intent) null, 0, 0, 0, (Bundle) null);
        return true;
    }

    public final synchronized void unregisterListener(InstallStateUpdatedListener installStateUpdatedListener) {
        this.f267b.mo34247b(installStateUpdatedListener);
    }
}
