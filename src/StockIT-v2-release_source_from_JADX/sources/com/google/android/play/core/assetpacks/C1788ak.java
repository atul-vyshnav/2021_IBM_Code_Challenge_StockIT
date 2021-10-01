package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1997t;
import com.google.android.play.core.tasks.C2106i;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.ak */
class C1788ak<T> extends C1997t {

    /* renamed from: a */
    final C2106i<T> f387a;

    /* renamed from: b */
    final /* synthetic */ C1795ar f388b;

    C1788ak(C1795ar arVar, C2106i<T> iVar) {
        this.f388b = arVar;
        this.f387a = iVar;
    }

    C1788ak(C1795ar arVar, C2106i iVar, byte[] bArr) {
        this(arVar, iVar);
    }

    C1788ak(C1795ar arVar, C2106i iVar, char[] cArr) {
        this(arVar, iVar);
    }

    C1788ak(C1795ar arVar, C2106i iVar, int[] iArr) {
        this(arVar, iVar);
    }

    C1788ak(C1795ar arVar, C2106i iVar, short[] sArr) {
        this(arVar, iVar);
    }

    /* renamed from: a */
    public void mo33929a() {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onCancelDownloads()", new Object[0]);
    }

    /* renamed from: a */
    public final void mo33930a(int i) {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onCancelDownload(%d)", Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo33931a(int i, Bundle bundle) {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onStartDownload(%d)", Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo33932a(Bundle bundle) {
        this.f388b.f403e.mo34150a();
        int i = bundle.getInt("error_code");
        C1795ar.f399a.mo34141b("onError(%d)", Integer.valueOf(i));
        this.f387a.mo34397b((Exception) new AssetPackException(i));
    }

    /* renamed from: a */
    public void mo33933a(Bundle bundle, Bundle bundle2) {
        this.f388b.f404f.mo34150a();
        C1795ar.f399a.mo34142c("onKeepAlive(%b)", Boolean.valueOf(bundle.getBoolean("keep_alive")));
    }

    /* renamed from: a */
    public void mo33934a(List<Bundle> list) {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onGetSessionStates", new Object[0]);
    }

    /* renamed from: b */
    public void mo33935b() {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onRemoveModule()", new Object[0]);
    }

    /* renamed from: b */
    public final void mo33936b(int i) {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onGetSession(%d)", Integer.valueOf(i));
    }

    /* renamed from: b */
    public void mo33937b(Bundle bundle) {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onNotifyChunkTransferred(%s, %s, %d, session=%d)", bundle.getString("module_name"), bundle.getString("slice_id"), Integer.valueOf(bundle.getInt("chunk_number")), Integer.valueOf(bundle.getInt("session_id")));
    }

    /* renamed from: b */
    public void mo33938b(Bundle bundle, Bundle bundle2) throws RemoteException {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onGetChunkFileDescriptor", new Object[0]);
    }

    /* renamed from: c */
    public void mo33939c(Bundle bundle) {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onNotifyModuleCompleted(%s, sessionId=%d)", bundle.getString("module_name"), Integer.valueOf(bundle.getInt("session_id")));
    }

    /* renamed from: c */
    public void mo33940c(Bundle bundle, Bundle bundle2) {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onRequestDownloadInfo()", new Object[0]);
    }

    /* renamed from: d */
    public void mo33941d(Bundle bundle) {
        this.f388b.f403e.mo34150a();
        C1795ar.f399a.mo34142c("onNotifySessionFailed(%d)", Integer.valueOf(bundle.getInt("session_id")));
    }
}
