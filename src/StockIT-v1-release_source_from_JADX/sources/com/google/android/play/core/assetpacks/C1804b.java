package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1964by;
import com.google.android.play.core.internal.C1999v;
import com.google.android.play.core.internal.C2002y;
import java.util.Arrays;

/* renamed from: com.google.android.play.core.assetpacks.b */
final class C1804b extends C1999v {

    /* renamed from: a */
    private final C1918af f434a = new C1918af("AssetPackExtractionService");

    /* renamed from: b */
    private final Context f435b;

    /* renamed from: c */
    private final AssetPackExtractionService f436c;

    /* renamed from: d */
    private final C1806bb f437d;

    C1804b(Context context, AssetPackExtractionService assetPackExtractionService, C1806bb bbVar) {
        this.f435b = context;
        this.f436c = assetPackExtractionService;
        this.f437d = bbVar;
    }

    /* renamed from: a */
    public final void mo33959a(Bundle bundle, C2002y yVar) throws RemoteException {
        String[] packagesForUid;
        this.f434a.mo34139a("updateServiceState AIDL call", new Object[0]);
        if (!C1964by.m751a(this.f435b) || (packagesForUid = this.f435b.getPackageManager().getPackagesForUid(Binder.getCallingUid())) == null || !Arrays.asList(packagesForUid).contains("com.android.vending")) {
            yVar.mo34239a(new Bundle());
            this.f436c.mo33900a();
            return;
        }
        yVar.mo34240a(this.f436c.mo33899a(bundle), new Bundle());
    }

    /* renamed from: a */
    public final void mo33960a(C2002y yVar) throws RemoteException {
        this.f437d.mo33987f();
        yVar.mo34241b(new Bundle());
    }
}
