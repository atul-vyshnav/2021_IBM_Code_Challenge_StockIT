package com.google.android.play.core.splitinstall.testing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.internal.C1917ae;
import com.google.android.play.core.internal.C1934av;
import com.google.android.play.core.internal.C1963bx;
import com.google.android.play.core.splitcompat.C2032p;
import com.google.android.play.core.splitinstall.C2064e;
import com.google.android.play.core.splitinstall.C2067h;
import com.google.android.play.core.splitinstall.C2071l;
import com.google.android.play.core.splitinstall.C2076p;
import com.google.android.play.core.splitinstall.SplitInstallException;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FakeSplitInstallManager implements SplitInstallManager {

    /* renamed from: a */
    public static final /* synthetic */ int f993a = 0;

    /* renamed from: c */
    private static final long f994c = TimeUnit.SECONDS.toMillis(1);

    /* renamed from: b */
    private final Handler f995b;

    /* renamed from: d */
    private final Context f996d;

    /* renamed from: e */
    private final C2076p f997e;

    /* renamed from: f */
    private final C1963bx f998f;

    /* renamed from: g */
    private final C1917ae<SplitInstallSessionState> f999g;

    /* renamed from: h */
    private final Executor f1000h;

    /* renamed from: i */
    private final C2064e f1001i;

    /* renamed from: j */
    private final File f1002j;

    /* renamed from: k */
    private final AtomicReference<SplitInstallSessionState> f1003k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public final Set<String> f1004l;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public final Set<String> f1005m;

    /* renamed from: n */
    private final AtomicBoolean f1006n;

    /* renamed from: o */
    private final C2081a f1007o;

    @Deprecated
    public FakeSplitInstallManager(Context context, File file) {
        this(context, file, new C2076p(context, context.getPackageName()));
    }

    FakeSplitInstallManager(Context context, File file, C2076p pVar) {
        Executor a = C2032p.m935a();
        C1963bx bxVar = new C1963bx(context);
        C2081a aVar = C2081a.f1009a;
        this.f995b = new Handler(Looper.getMainLooper());
        this.f1003k = new AtomicReference<>();
        this.f1004l = Collections.synchronizedSet(new HashSet());
        this.f1005m = Collections.synchronizedSet(new HashSet());
        this.f1006n = new AtomicBoolean(false);
        this.f996d = context;
        this.f1002j = file;
        this.f997e = pVar;
        this.f1000h = a;
        this.f998f = bxVar;
        this.f1007o = aVar;
        this.f999g = new C1917ae<>();
        this.f1001i = C2071l.f973a;
    }

    /* renamed from: a */
    static final /* synthetic */ SplitInstallSessionState m1040a(int i, SplitInstallSessionState splitInstallSessionState) {
        int status;
        if (splitInstallSessionState != null && i == splitInstallSessionState.sessionId() && ((status = splitInstallSessionState.status()) == 1 || status == 2 || status == 8 || status == 9 || status == 7)) {
            return SplitInstallSessionState.create(i, 7, splitInstallSessionState.errorCode(), splitInstallSessionState.bytesDownloaded(), splitInstallSessionState.totalBytesToDownload(), splitInstallSessionState.moduleNames(), splitInstallSessionState.languages());
        }
        throw new SplitInstallException(-3);
    }

    /* renamed from: a */
    private final synchronized SplitInstallSessionState m1041a(C2090j jVar) {
        SplitInstallSessionState c = m1054c();
        SplitInstallSessionState a = jVar.mo34366a(c);
        if (this.f1003k.compareAndSet(c, a)) {
            return a;
        }
        return null;
    }

    /* renamed from: a */
    static final /* synthetic */ SplitInstallSessionState m1042a(Integer num, int i, int i2, Long l, Long l2, List list, List list2, SplitInstallSessionState splitInstallSessionState) {
        SplitInstallSessionState create = splitInstallSessionState == null ? SplitInstallSessionState.create(0, 0, 0, 0, 0, new ArrayList(), new ArrayList()) : splitInstallSessionState;
        return SplitInstallSessionState.create(num == null ? create.sessionId() : num.intValue(), i, i2, l == null ? create.bytesDownloaded() : l.longValue(), l2 == null ? create.totalBytesToDownload() : l2.longValue(), list == null ? create.moduleNames() : list, list2 == null ? create.languages() : list2);
    }

    /* renamed from: a */
    private static String m1043a(String str) {
        return str.split("\\.config\\.", 2)[0];
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public final void m1048a(List<Intent> list, List<String> list2, List<String> list3, long j, boolean z) {
        List<Intent> list4 = list;
        this.f1001i.mo34351a().mo34155a(list, new C2089i(this, list2, list3, j, z, list));
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public final boolean m1049a(int i) {
        return m1050a(6, i, (Long) null, (Long) null, (List<String>) null, (Integer) null, (List<String>) null);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public final boolean m1050a(int i, int i2, Long l, Long l2, List<String> list, Integer num, List<String> list2) {
        SplitInstallSessionState a = m1041a((C2090j) new C2082b(num, i, i2, l, l2, list, list2));
        if (a == null) {
            return false;
        }
        m1053b(a);
        return true;
    }

    /* renamed from: b */
    private final void m1053b(SplitInstallSessionState splitInstallSessionState) {
        this.f995b.post(new C2086f(this, splitInstallSessionState));
    }

    /* renamed from: c */
    private final SplitInstallSessionState m1054c() {
        return this.f1003k.get();
    }

    /* renamed from: d */
    private final C2067h m1055d() {
        C2067h c = this.f997e.mo34358c();
        if (c != null) {
            return c;
        }
        throw new IllegalStateException("Language information could not be found. Make sure you are using the target application context, not the tests context, and the app is built as a bundle.");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final File mo34360a() {
        return this.f1002j;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34361a(long j, List list, List list2, List list3) {
        long j2 = j;
        long j3 = j2 / 3;
        long j4 = 0;
        int i = 0;
        while (i < 3) {
            j4 = Math.min(j2, j4 + j3);
            m1050a(2, 0, Long.valueOf(j4), Long.valueOf(j), (List<String>) null, (Integer) null, (List<String>) null);
            SystemClock.sleep(f994c);
            SplitInstallSessionState c = m1054c();
            if (c.status() != 9 && c.status() != 7 && c.status() != 6) {
                i++;
            } else {
                return;
            }
        }
        this.f1000h.execute(new C2088h(this, list, list2, list3, j));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34362a(SplitInstallSessionState splitInstallSessionState) {
        this.f999g.mo34137a(splitInstallSessionState);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34363a(List list, List list2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            File file = (File) list.get(i);
            String a = C1934av.m655a(file);
            Uri fromFile = Uri.fromFile(file);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(fromFile, this.f996d.getContentResolver().getType(fromFile));
            intent.addFlags(1);
            intent.putExtra("module_name", m1043a(a));
            intent.putExtra("split_id", a);
            arrayList.add(intent);
            arrayList2.add(m1043a(C1934av.m655a(file)));
        }
        SplitInstallSessionState c = m1054c();
        if (c != null) {
            this.f1000h.execute(new C2087g(this, c.totalBytesToDownload(), arrayList, arrayList2, list2));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo34364a(List list, List list2, List list3, long j) {
        if (this.f1006n.get()) {
            m1049a(-6);
        } else {
            m1048a((List<Intent>) list, (List<String>) list2, (List<String>) list3, j, false);
        }
    }

    public final Task<Void> cancelInstall(int i) {
        try {
            SplitInstallSessionState a = m1041a((C2090j) new C2085e(i));
            if (a != null) {
                m1053b(a);
            }
            return Tasks.m1079a(null);
        } catch (SplitInstallException e) {
            return Tasks.m1078a((Exception) e);
        }
    }

    public final Task<Void> deferredInstall(List<String> list) {
        return Tasks.m1078a((Exception) new SplitInstallException(-5));
    }

    public final Task<Void> deferredLanguageInstall(List<Locale> list) {
        return Tasks.m1078a((Exception) new SplitInstallException(-5));
    }

    public final Task<Void> deferredLanguageUninstall(List<Locale> list) {
        return Tasks.m1078a((Exception) new SplitInstallException(-5));
    }

    public final Task<Void> deferredUninstall(List<String> list) {
        return Tasks.m1078a((Exception) new SplitInstallException(-5));
    }

    public final Set<String> getInstalledLanguages() {
        return new HashSet(this.f1005m);
    }

    public final Set<String> getInstalledModules() {
        return new HashSet(this.f1004l);
    }

    public final Task<SplitInstallSessionState> getSessionState(int i) {
        SplitInstallSessionState c = m1054c();
        return (c == null || c.sessionId() != i) ? Tasks.m1078a((Exception) new SplitInstallException(-4)) : Tasks.m1079a(c);
    }

    public final Task<List<SplitInstallSessionState>> getSessionStates() {
        SplitInstallSessionState c = m1054c();
        return Tasks.m1079a(c != null ? Collections.singletonList(c) : Collections.emptyList());
    }

    public final void registerListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.f999g.mo34136a(splitInstallStateUpdatedListener);
    }

    public void setShouldNetworkError(boolean z) {
        this.f1006n.set(z);
    }

    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, Activity activity, int i) throws IntentSender.SendIntentException {
        return false;
    }

    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, IntentSenderForResultStarter intentSenderForResultStarter, int i) throws IntentSender.SendIntentException {
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0133, code lost:
        if (r0.contains(r6) == false) goto L_0x013c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.play.core.tasks.Task<java.lang.Integer> startInstall(com.google.android.play.core.splitinstall.SplitInstallRequest r22) {
        /*
            r21 = this;
            r9 = r21
            com.google.android.play.core.splitinstall.testing.d r0 = new com.google.android.play.core.splitinstall.testing.d     // Catch:{ SplitInstallException -> 0x0230 }
            r1 = r22
            r0.<init>(r1)     // Catch:{ SplitInstallException -> 0x0230 }
            com.google.android.play.core.splitinstall.SplitInstallSessionState r0 = r9.m1041a((com.google.android.play.core.splitinstall.testing.C2090j) r0)     // Catch:{ SplitInstallException -> 0x0230 }
            if (r0 == 0) goto L_0x0224
            int r0 = r0.sessionId()     // Catch:{ SplitInstallException -> 0x0230 }
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            java.util.List r2 = r22.getLanguages()
            java.util.Iterator r2 = r2.iterator()
        L_0x0020:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0034
            java.lang.Object r3 = r2.next()
            java.util.Locale r3 = (java.util.Locale) r3
            java.lang.String r3 = r3.getLanguage()
            r10.add(r3)
            goto L_0x0020
        L_0x0034:
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>()
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.io.File r3 = r9.f1002j
            java.io.File[] r3 = r3.listFiles()
            java.lang.String r4 = "FakeSplitInstallManager"
            if (r3 != 0) goto L_0x0058
            java.lang.String r0 = "Specified splits directory does not exist."
            android.util.Log.w(r4, r0)
            com.google.android.play.core.splitinstall.SplitInstallException r0 = new com.google.android.play.core.splitinstall.SplitInstallException
            r1 = -5
            r0.<init>(r1)
            com.google.android.play.core.tasks.Task r0 = com.google.android.play.core.tasks.Tasks.m1078a((java.lang.Exception) r0)
            return r0
        L_0x0058:
            int r5 = r3.length
            r12 = 0
            r13 = 0
        L_0x005c:
            if (r12 >= r5) goto L_0x01a3
            r15 = r3[r12]
            java.lang.String r6 = com.google.android.play.core.internal.C1934av.m655a(r15)
            java.lang.String r7 = m1043a((java.lang.String) r6)
            java.util.List r8 = r22.getModuleNames()
            boolean r7 = r8.contains(r7)
            if (r7 == 0) goto L_0x0136
            java.lang.String r7 = m1043a((java.lang.String) r6)
            java.util.HashSet r8 = new java.util.HashSet
            com.google.android.play.core.internal.bx r1 = r9.f998f
            java.util.List r1 = r1.mo34188a()
            r8.<init>(r1)
            com.google.android.play.core.splitinstall.h r1 = r21.m1055d()
            r17 = r3
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]
            r16 = 0
            r3[r16] = r7
            java.util.List r3 = java.util.Arrays.asList(r3)
            java.util.Map r1 = r1.mo34354a(r3)
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.Collection r7 = r1.values()
            java.util.Iterator r7 = r7.iterator()
        L_0x00a3:
            boolean r18 = r7.hasNext()
            if (r18 == 0) goto L_0x00b9
            java.lang.Object r18 = r7.next()
            r19 = r5
            r5 = r18
            java.util.Set r5 = (java.util.Set) r5
            r3.addAll(r5)
            r5 = r19
            goto L_0x00a3
        L_0x00b9:
            r19 = r5
            java.util.HashSet r5 = new java.util.HashSet
            r5.<init>()
            java.util.Iterator r7 = r8.iterator()
        L_0x00c4:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x00f0
            java.lang.Object r8 = r7.next()
            java.lang.String r8 = (java.lang.String) r8
            r18 = r7
            java.lang.String r7 = "_"
            boolean r20 = r8.contains(r7)
            if (r20 == 0) goto L_0x00e5
            r20 = r0
            r0 = -1
            java.lang.String[] r0 = r8.split(r7, r0)
            r7 = 0
            r8 = r0[r7]
            goto L_0x00e8
        L_0x00e5:
            r20 = r0
            r7 = 0
        L_0x00e8:
            r5.add(r8)
            r7 = r18
            r0 = r20
            goto L_0x00c4
        L_0x00f0:
            r20 = r0
            r7 = 0
            java.util.Set<java.lang.String> r0 = r9.f1005m
            r5.addAll(r0)
            r5.addAll(r10)
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0108:
            boolean r8 = r1.hasNext()
            if (r8 == 0) goto L_0x0129
            java.lang.Object r8 = r1.next()
            java.util.Map$Entry r8 = (java.util.Map.Entry) r8
            java.lang.Object r7 = r8.getKey()
            boolean r7 = r5.contains(r7)
            if (r7 == 0) goto L_0x0127
            java.lang.Object r7 = r8.getValue()
            java.util.Collection r7 = (java.util.Collection) r7
            r0.addAll(r7)
        L_0x0127:
            r7 = 0
            goto L_0x0108
        L_0x0129:
            boolean r1 = r3.contains(r6)
            if (r1 == 0) goto L_0x0188
            boolean r0 = r0.contains(r6)
            if (r0 == 0) goto L_0x013c
            goto L_0x0188
        L_0x0136:
            r20 = r0
            r17 = r3
            r19 = r5
        L_0x013c:
            java.util.List r0 = r22.getLanguages()
            java.util.ArrayList r1 = new java.util.ArrayList
            java.util.Set<java.lang.String> r3 = r9.f1004l
            r1.<init>(r3)
            java.lang.String r3 = ""
            java.lang.String r5 = "base"
            java.lang.String[] r3 = new java.lang.String[]{r3, r5}
            java.util.List r3 = java.util.Arrays.asList(r3)
            r1.addAll(r3)
            com.google.android.play.core.splitinstall.h r3 = r21.m1055d()
            java.util.Map r1 = r3.mo34354a(r1)
            java.util.Iterator r0 = r0.iterator()
        L_0x0162:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0197
            java.lang.Object r3 = r0.next()
            java.util.Locale r3 = (java.util.Locale) r3
            java.lang.String r5 = r3.getLanguage()
            boolean r5 = r1.containsKey(r5)
            if (r5 == 0) goto L_0x0162
            java.lang.String r3 = r3.getLanguage()
            java.lang.Object r3 = r1.get(r3)
            java.util.Set r3 = (java.util.Set) r3
            boolean r3 = r3.contains(r6)
            if (r3 == 0) goto L_0x0162
        L_0x0188:
            long r0 = r15.length()
            long r13 = r13 + r0
            java.lang.String r0 = com.google.android.play.core.internal.C1934av.m655a(r15)
            r2.add(r0)
            r11.add(r15)
        L_0x0197:
            int r12 = r12 + 1
            r1 = r22
            r3 = r17
            r5 = r19
            r0 = r20
            goto L_0x005c
        L_0x01a3:
            r20 = r0
            java.lang.String r0 = java.lang.String.valueOf(r2)
            java.util.List r1 = r22.getModuleNames()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r3 = java.lang.String.valueOf(r0)
            int r3 = r3.length()
            java.lang.String r5 = java.lang.String.valueOf(r1)
            int r5 = r5.length()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            int r3 = r3 + 21
            int r3 = r3 + r5
            r6.<init>(r3)
            java.lang.String r3 = "availableSplits"
            r6.append(r3)
            r6.append(r0)
            java.lang.String r0 = " want "
            r6.append(r0)
            r6.append(r1)
            java.lang.String r0 = r6.toString()
            android.util.Log.i(r4, r0)
            java.util.HashSet r0 = new java.util.HashSet
            java.util.List r1 = r22.getModuleNames()
            r0.<init>(r1)
            boolean r0 = r2.containsAll(r0)
            if (r0 != 0) goto L_0x01fa
            com.google.android.play.core.splitinstall.SplitInstallException r0 = new com.google.android.play.core.splitinstall.SplitInstallException
            r1 = -2
            r0.<init>(r1)
            com.google.android.play.core.tasks.Task r0 = com.google.android.play.core.tasks.Tasks.m1078a((java.lang.Exception) r0)
            return r0
        L_0x01fa:
            r0 = 0
            java.lang.Long r4 = java.lang.Long.valueOf(r0)
            java.lang.Long r5 = java.lang.Long.valueOf(r13)
            java.util.List r6 = r22.getModuleNames()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r20)
            r2 = 1
            r3 = 0
            r1 = r21
            r7 = r0
            r8 = r10
            r1.m1050a(r2, r3, r4, r5, r6, r7, r8)
            java.util.concurrent.Executor r1 = r9.f1000h
            com.google.android.play.core.splitinstall.testing.c r2 = new com.google.android.play.core.splitinstall.testing.c
            r2.<init>(r9, r11, r10)
            r1.execute(r2)
            com.google.android.play.core.tasks.Task r0 = com.google.android.play.core.tasks.Tasks.m1079a(r0)
            return r0
        L_0x0224:
            com.google.android.play.core.splitinstall.SplitInstallException r0 = new com.google.android.play.core.splitinstall.SplitInstallException     // Catch:{ SplitInstallException -> 0x0230 }
            r1 = -100
            r0.<init>(r1)     // Catch:{ SplitInstallException -> 0x0230 }
            com.google.android.play.core.tasks.Task r0 = com.google.android.play.core.tasks.Tasks.m1078a((java.lang.Exception) r0)     // Catch:{ SplitInstallException -> 0x0230 }
            return r0
        L_0x0230:
            r0 = move-exception
            com.google.android.play.core.tasks.Task r0 = com.google.android.play.core.tasks.Tasks.m1078a((java.lang.Exception) r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.splitinstall.testing.FakeSplitInstallManager.startInstall(com.google.android.play.core.splitinstall.SplitInstallRequest):com.google.android.play.core.tasks.Task");
    }

    public final void unregisterListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.f999g.mo34138b(splitInstallStateUpdatedListener);
    }
}
