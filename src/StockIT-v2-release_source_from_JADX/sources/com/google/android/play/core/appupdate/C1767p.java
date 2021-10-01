package com.google.android.play.core.appupdate;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1928ap;
import com.google.android.play.core.internal.C1964by;
import com.google.android.play.core.internal.C1991n;
import com.google.android.play.core.splitcompat.C2032p;
import com.google.android.play.core.tasks.C2106i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

/* renamed from: com.google.android.play.core.appupdate.p */
final class C1767p {
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static final C1918af f288b = new C1918af("AppUpdateService");

    /* renamed from: c */
    private static final Intent f289c = new Intent("com.google.android.play.core.install.BIND_UPDATE_SERVICE").setPackage("com.android.vending");

    /* renamed from: a */
    C1928ap<C1991n> f290a;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final String f291d;

    /* renamed from: e */
    private final Context f292e;

    /* renamed from: f */
    private final C1769r f293f;

    public C1767p(Context context, C1769r rVar) {
        this.f291d = context.getPackageName();
        this.f292e = context;
        this.f293f = rVar;
        if (C1964by.m751a(context)) {
            this.f290a = new C1928ap(C2032p.m933a(context), f288b, "AppUpdateService", f289c, C1761j.f276a);
        }
    }

    /* renamed from: a */
    static /* synthetic */ Bundle m254a(C1767p pVar, String str) {
        Integer num;
        Bundle bundle = new Bundle();
        bundle.putAll(m260d());
        bundle.putString("package.name", str);
        try {
            num = Integer.valueOf(pVar.f292e.getPackageManager().getPackageInfo(pVar.f292e.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException unused) {
            f288b.mo34141b("The current version of the app could not be retrieved", new Object[0]);
            num = null;
        }
        if (num != null) {
            bundle.putInt("app.version.code", num.intValue());
        }
        return bundle;
    }

    /* renamed from: a */
    static /* synthetic */ AppUpdateInfo m255a(C1767p pVar, Bundle bundle, String str) {
        Bundle bundle2 = bundle;
        return AppUpdateInfo.m230a(str, bundle2.getInt("version.code", -1), bundle2.getInt("update.availability"), bundle2.getInt("install.status", 0), bundle2.getInt("client.version.staleness", -1) == -1 ? null : Integer.valueOf(bundle2.getInt("client.version.staleness")), bundle2.getInt("in.app.update.priority", 0), bundle2.getLong("bytes.downloaded"), bundle2.getLong("total.bytes.to.download"), bundle2.getLong("additional.size.required"), pVar.f293f.mo33866a(), (PendingIntent) bundle2.getParcelable("blocking.intent"), (PendingIntent) bundle2.getParcelable("nonblocking.intent"), (PendingIntent) bundle2.getParcelable("blocking.destructive.intent"), (PendingIntent) bundle2.getParcelable("nonblocking.destructive.intent"));
    }

    /* renamed from: c */
    private static <T> Task<T> m259c() {
        f288b.mo34141b("onError(%d)", -9);
        return Tasks.m1078a((Exception) new InstallException(-9));
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public static Bundle m260d() {
        Bundle bundle = new Bundle();
        bundle.putInt("playcore.version.code", 10800);
        return bundle;
    }

    /* renamed from: a */
    public final Task<AppUpdateInfo> mo33864a(String str) {
        if (this.f290a == null) {
            return m259c();
        }
        f288b.mo34142c("requestUpdateInfo(%s)", str);
        C2106i iVar = new C2106i();
        this.f290a.mo34151a((C1919ag) new C1762k(this, iVar, str, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: b */
    public final Task<Void> mo33865b(String str) {
        if (this.f290a == null) {
            return m259c();
        }
        f288b.mo34142c("completeUpdate(%s)", str);
        C2106i iVar = new C2106i();
        this.f290a.mo34151a((C1919ag) new C1763l(this, iVar, iVar, str));
        return iVar.mo34394a();
    }
}
