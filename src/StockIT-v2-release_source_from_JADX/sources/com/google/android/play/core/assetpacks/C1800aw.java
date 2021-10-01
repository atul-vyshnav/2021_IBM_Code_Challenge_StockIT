package com.google.android.play.core.assetpacks;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.common.C1907a;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1976cj;
import com.google.android.play.core.listener.C2005b;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.aw */
final class C1800aw extends C2005b<AssetPackState> {

    /* renamed from: c */
    private final C1847cp f415c;

    /* renamed from: d */
    private final C1827bw f416d;

    /* renamed from: e */
    private final C1976cj<C1902w> f417e;

    /* renamed from: f */
    private final C1818bn f418f;

    /* renamed from: g */
    private final C1830bz f419g;

    /* renamed from: h */
    private final C1907a f420h;

    /* renamed from: i */
    private final C1976cj<Executor> f421i;

    /* renamed from: j */
    private final C1976cj<Executor> f422j;

    /* renamed from: k */
    private final Handler f423k = new Handler(Looper.getMainLooper());

    C1800aw(Context context, C1847cp cpVar, C1827bw bwVar, C1976cj<C1902w> cjVar, C1830bz bzVar, C1818bn bnVar, C1907a aVar, C1976cj<Executor> cjVar2, C1976cj<Executor> cjVar3) {
        super(new C1918af("AssetPackServiceListenerRegistry"), new IntentFilter("com.google.android.play.core.assetpacks.receiver.ACTION_SESSION_UPDATE"), context);
        this.f415c = cpVar;
        this.f416d = bwVar;
        this.f417e = cjVar;
        this.f419g = bzVar;
        this.f418f = bnVar;
        this.f420h = aVar;
        this.f421i = cjVar2;
        this.f422j = cjVar3;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33854a(Context context, Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra("com.google.android.play.core.assetpacks.receiver.EXTRA_SESSION_STATE");
        if (bundleExtra != null) {
            ArrayList<String> stringArrayList = bundleExtra.getStringArrayList("pack_names");
            if (stringArrayList == null || stringArrayList.size() != 1) {
                this.f836a.mo34141b("Corrupt bundle received from broadcast.", new Object[0]);
                return;
            }
            Bundle bundleExtra2 = intent.getBundleExtra("com.google.android.play.core.FLAGS");
            if (bundleExtra2 != null) {
                this.f420h.mo34120a(bundleExtra2);
            }
            AssetPackState a = AssetPackState.m286a(bundleExtra, stringArrayList.get(0), this.f419g, C1802ay.f433a);
            this.f836a.mo34139a("ListenerRegistryBroadcastReceiver.onReceive: %s", a);
            PendingIntent pendingIntent = (PendingIntent) bundleExtra.getParcelable("confirmation_intent");
            if (pendingIntent != null) {
                this.f418f.mo34015a(pendingIntent);
            }
            this.f422j.mo34195a().execute(new C1798au(this, bundleExtra, a));
            this.f421i.mo34195a().execute(new C1799av(this, bundleExtra));
            return;
        }
        this.f836a.mo34141b("Empty bundle received from broadcast.", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo33955a(Bundle bundle) {
        if (this.f415c.mo34033a(bundle)) {
            this.f416d.mo34020a();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo33956a(Bundle bundle, AssetPackState assetPackState) {
        if (this.f415c.mo34038b(bundle)) {
            mo33957a(assetPackState);
            this.f417e.mo34195a().mo33945a();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo33957a(AssetPackState assetPackState) {
        this.f423k.post(new C1797at(this, assetPackState));
    }
}
