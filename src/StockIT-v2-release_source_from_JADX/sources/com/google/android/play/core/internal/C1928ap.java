package com.google.android.play.core.internal;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.play.core.tasks.C2106i;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.play.core.internal.ap */
public final class C1928ap<T extends IInterface> {

    /* renamed from: a */
    private static final Map<String, Handler> f783a = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Context f784b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final C1918af f785c;

    /* renamed from: d */
    private final String f786d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public final List<C1919ag> f787e = new ArrayList();
    /* access modifiers changed from: private */

    /* renamed from: f */
    public boolean f788f;

    /* renamed from: g */
    private final Intent f789g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public final C1924al<T> f790h;

    /* renamed from: i */
    private final WeakReference<C1923ak> f791i;

    /* renamed from: j */
    private final IBinder.DeathRecipient f792j = new C1920ah(this);
    /* access modifiers changed from: private */

    /* renamed from: k */
    public ServiceConnection f793k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public T f794l;

    public C1928ap(Context context, C1918af afVar, String str, Intent intent, C1924al<T> alVar) {
        this.f784b = context;
        this.f785c = afVar;
        this.f786d = str;
        this.f789g = intent;
        this.f790h = alVar;
        this.f791i = new WeakReference<>((Object) null);
    }

    /* renamed from: a */
    static /* synthetic */ void m628a(C1928ap apVar, C1919ag agVar) {
        if (apVar.f794l == null && !apVar.f788f) {
            apVar.f785c.mo34142c("Initiate binding to the service.", new Object[0]);
            apVar.f787e.add(agVar);
            C1927ao aoVar = new C1927ao(apVar);
            apVar.f793k = aoVar;
            apVar.f788f = true;
            if (!apVar.f784b.bindService(apVar.f789g, aoVar, 1)) {
                apVar.f785c.mo34142c("Failed to bind to the service.", new Object[0]);
                apVar.f788f = false;
                List<C1919ag> list = apVar.f787e;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    C2106i<?> b = list.get(i).mo34144b();
                    if (b != null) {
                        b.mo34397b((Exception) new C1929aq());
                    }
                }
                apVar.f787e.clear();
            }
        } else if (apVar.f788f) {
            apVar.f785c.mo34142c("Waiting to bind to the service.", new Object[0]);
            apVar.f787e.add(agVar);
        } else {
            agVar.run();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public final void m630b(C1919ag agVar) {
        Handler handler;
        synchronized (f783a) {
            if (!f783a.containsKey(this.f786d)) {
                HandlerThread handlerThread = new HandlerThread(this.f786d, 10);
                handlerThread.start();
                f783a.put(this.f786d, new Handler(handlerThread.getLooper()));
            }
            handler = f783a.get(this.f786d);
        }
        handler.post(agVar);
    }

    /* renamed from: f */
    static /* synthetic */ void m635f(C1928ap apVar) {
        apVar.f785c.mo34142c("linkToDeath", new Object[0]);
        try {
            apVar.f794l.asBinder().linkToDeath(apVar.f792j, 0);
        } catch (RemoteException e) {
            apVar.f785c.mo34140a((Throwable) e, "linkToDeath failed", new Object[0]);
        }
    }

    /* renamed from: h */
    static /* synthetic */ void m637h(C1928ap apVar) {
        apVar.f785c.mo34142c("unlinkToDeath", new Object[0]);
        apVar.f794l.asBinder().unlinkToDeath(apVar.f792j, 0);
    }

    /* renamed from: a */
    public final void mo34150a() {
        m630b((C1919ag) new C1922aj(this));
    }

    /* renamed from: a */
    public final void mo34151a(C1919ag agVar) {
        m630b((C1919ag) new C1921ai(this, agVar.mo34144b(), agVar));
    }

    /* renamed from: b */
    public final T mo34152b() {
        return this.f794l;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final /* bridge */ /* synthetic */ void mo34153c() {
        this.f785c.mo34142c("reportBinderDeath", new Object[0]);
        C1923ak akVar = (C1923ak) this.f791i.get();
        if (akVar == null) {
            this.f785c.mo34142c("%s : Binder has died.", this.f786d);
            List<C1919ag> list = this.f787e;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                C2106i<?> b = list.get(i).mo34144b();
                if (b != null) {
                    b.mo34397b((Exception) Build.VERSION.SDK_INT < 15 ? new RemoteException() : new RemoteException(String.valueOf(this.f786d).concat(" : Binder has died.")));
                }
            }
            this.f787e.clear();
            return;
        }
        this.f785c.mo34142c("calling onBinderDied", new Object[0]);
        akVar.mo34147a();
    }
}
