package com.android.billingclient.api;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.internal.play_billing.zza;
import com.google.android.gms.internal.play_billing.zzc;
import com.google.android.gms.internal.play_billing.zzd;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzaf implements ServiceConnection {
    final /* synthetic */ BillingClientImpl zza;
    private final Object zzb = new Object();
    private boolean zzc = false;
    private BillingClientStateListener zzd;

    /* synthetic */ zzaf(BillingClientImpl billingClientImpl, BillingClientStateListener billingClientStateListener, zzy zzy) {
        this.zza = billingClientImpl;
        this.zzd = billingClientStateListener;
    }

    private final void zzd(BillingResult billingResult) {
        synchronized (this.zzb) {
            BillingClientStateListener billingClientStateListener = this.zzd;
            if (billingClientStateListener != null) {
                billingClientStateListener.onBillingSetupFinished(billingResult);
            }
        }
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zza.zzj("BillingClient", "Billing service connected.");
        zzd unused = this.zza.zzg = zzc.zzn(iBinder);
        if (this.zza.zzH(new zzae(this), 30000, new zzad(this), this.zza.zzD()) == null) {
            zzd(this.zza.zzF());
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        zza.zzk("BillingClient", "Billing service disconnected.");
        zzd unused = this.zza.zzg = null;
        int unused2 = this.zza.zza = 0;
        synchronized (this.zzb) {
            BillingClientStateListener billingClientStateListener = this.zzd;
            if (billingClientStateListener != null) {
                billingClientStateListener.onBillingServiceDisconnected();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r3 = r10.zza.zzf.getPackageName();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        r5 = 16;
        r6 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
        if (r5 < 3) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r6 = r10.zza.zzg.zzb(r5, r3, com.android.billingclient.api.BillingClient.SkuType.SUBS);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002b, code lost:
        if (r6 != 0) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002e, code lost:
        r5 = r5 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0031, code lost:
        r0 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0034, code lost:
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0035, code lost:
        r7 = r10.zza;
        r9 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
        if (r5 < 5) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003b, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003d, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003e, code lost:
        boolean unused = r7.zzj = r8;
        r7 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0043, code lost:
        if (r5 < 3) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0045, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0047, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0048, code lost:
        boolean unused = r7.zzi = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004b, code lost:
        if (r5 >= 3) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x004d, code lost:
        com.google.android.gms.internal.play_billing.zza.zzj("BillingClient", "In-app billing API does not support subscription on this device.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0054, code lost:
        r5 = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0056, code lost:
        if (r5 < 3) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0058, code lost:
        r6 = r10.zza.zzg.zzb(r5, r3, com.android.billingclient.api.BillingClient.SkuType.INAPP);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0064, code lost:
        if (r6 != 0) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0066, code lost:
        int unused = r10.zza.zzk = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x006c, code lost:
        r5 = r5 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006f, code lost:
        r3 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0075, code lost:
        if (r3.zzk < 16) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0077, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0079, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x007a, code lost:
        boolean unused = r3.zzs = r4;
        r3 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0085, code lost:
        if (r3.zzk < 15) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0087, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0089, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x008a, code lost:
        boolean unused = r3.zzr = r4;
        r3 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0095, code lost:
        if (r3.zzk < 14) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0097, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0099, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x009a, code lost:
        boolean unused = r3.zzq = r4;
        r3 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00a5, code lost:
        if (r3.zzk < 12) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a7, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00a9, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00aa, code lost:
        boolean unused = r3.zzp = r4;
        r3 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00b5, code lost:
        if (r3.zzk < 10) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00b7, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00b9, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00ba, code lost:
        boolean unused = r3.zzo = r4;
        r3 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00c5, code lost:
        if (r3.zzk < 9) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00c7, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00c9, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00ca, code lost:
        boolean unused = r3.zzn = r4;
        r3 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00d5, code lost:
        if (r3.zzk < 8) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00d7, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00d9, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00da, code lost:
        boolean unused = r3.zzm = r4;
        r3 = r10.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00e4, code lost:
        if (r3.zzk < 6) goto L_0x00e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00e7, code lost:
        r9 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00e8, code lost:
        boolean unused = r3.zzl = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00f1, code lost:
        if (r10.zza.zzk >= 3) goto L_0x00fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00f3, code lost:
        com.google.android.gms.internal.play_billing.zza.zzk("BillingClient", "In-app billing API version 3 is not supported on this device.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00fa, code lost:
        if (r6 != 0) goto L_0x0103;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00fc, code lost:
        int unused = r10.zza.zza = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0103, code lost:
        int unused = r10.zza.zza = 0;
        com.google.android.gms.internal.play_billing.zzd unused = r10.zza.zzg = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x010e, code lost:
        com.google.android.gms.internal.play_billing.zza.zzk("BillingClient", "Exception while checking if billing is supported; try to reconnect");
        int unused = r10.zza.zza = 0;
        com.google.android.gms.internal.play_billing.zzd unused = r10.zza.zzg = null;
        r6 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0120, code lost:
        if (r6 != 0) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0122, code lost:
        zzd(com.android.billingclient.api.zzak.zzp);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0128, code lost:
        zzd(com.android.billingclient.api.zzak.zza);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x012d, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000b, code lost:
        r0 = 3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0128  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object zza() throws java.lang.Exception {
        /*
            r10 = this;
            java.lang.Object r0 = r10.zzb
            monitor-enter(r0)
            boolean r1 = r10.zzc     // Catch:{ all -> 0x012e }
            r2 = 0
            if (r1 == 0) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x012e }
            return r2
        L_0x000a:
            monitor-exit(r0)     // Catch:{ all -> 0x012e }
            r0 = 3
            r1 = 0
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x010e }
            android.content.Context r3 = r3.zzf     // Catch:{ Exception -> 0x010e }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ Exception -> 0x010e }
            r4 = 16
            r5 = 16
            r6 = 3
        L_0x001c:
            if (r5 < r0) goto L_0x0034
            com.android.billingclient.api.BillingClientImpl r7 = r10.zza     // Catch:{ Exception -> 0x0031 }
            com.google.android.gms.internal.play_billing.zzd r7 = r7.zzg     // Catch:{ Exception -> 0x0031 }
            java.lang.String r8 = "subs"
            int r6 = r7.zzb(r5, r3, r8)     // Catch:{ Exception -> 0x0031 }
            if (r6 != 0) goto L_0x002e
            goto L_0x0035
        L_0x002e:
            int r5 = r5 + -1
            goto L_0x001c
        L_0x0031:
            r0 = r6
            goto L_0x010e
        L_0x0034:
            r5 = 0
        L_0x0035:
            com.android.billingclient.api.BillingClientImpl r7 = r10.zza     // Catch:{ Exception -> 0x0031 }
            r8 = 5
            r9 = 1
            if (r5 < r8) goto L_0x003d
            r8 = 1
            goto L_0x003e
        L_0x003d:
            r8 = 0
        L_0x003e:
            boolean unused = r7.zzj = r8     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r7 = r10.zza     // Catch:{ Exception -> 0x0031 }
            if (r5 < r0) goto L_0x0047
            r8 = 1
            goto L_0x0048
        L_0x0047:
            r8 = 0
        L_0x0048:
            boolean unused = r7.zzi = r8     // Catch:{ Exception -> 0x0031 }
            if (r5 >= r0) goto L_0x0054
            java.lang.String r5 = "BillingClient"
            java.lang.String r7 = "In-app billing API does not support subscription on this device."
            com.google.android.gms.internal.play_billing.zza.zzj(r5, r7)     // Catch:{ Exception -> 0x0031 }
        L_0x0054:
            r5 = 16
        L_0x0056:
            if (r5 < r0) goto L_0x006f
            com.android.billingclient.api.BillingClientImpl r7 = r10.zza     // Catch:{ Exception -> 0x0031 }
            com.google.android.gms.internal.play_billing.zzd r7 = r7.zzg     // Catch:{ Exception -> 0x0031 }
            java.lang.String r8 = "inapp"
            int r6 = r7.zzb(r5, r3, r8)     // Catch:{ Exception -> 0x0031 }
            if (r6 != 0) goto L_0x006c
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int unused = r3.zzk = r5     // Catch:{ Exception -> 0x0031 }
            goto L_0x006f
        L_0x006c:
            int r5 = r5 + -1
            goto L_0x0056
        L_0x006f:
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r5 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            if (r5 < r4) goto L_0x0079
            r4 = 1
            goto L_0x007a
        L_0x0079:
            r4 = 0
        L_0x007a:
            boolean unused = r3.zzs = r4     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r4 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            r5 = 15
            if (r4 < r5) goto L_0x0089
            r4 = 1
            goto L_0x008a
        L_0x0089:
            r4 = 0
        L_0x008a:
            boolean unused = r3.zzr = r4     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r4 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            r5 = 14
            if (r4 < r5) goto L_0x0099
            r4 = 1
            goto L_0x009a
        L_0x0099:
            r4 = 0
        L_0x009a:
            boolean unused = r3.zzq = r4     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r4 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            r5 = 12
            if (r4 < r5) goto L_0x00a9
            r4 = 1
            goto L_0x00aa
        L_0x00a9:
            r4 = 0
        L_0x00aa:
            boolean unused = r3.zzp = r4     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r4 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            r5 = 10
            if (r4 < r5) goto L_0x00b9
            r4 = 1
            goto L_0x00ba
        L_0x00b9:
            r4 = 0
        L_0x00ba:
            boolean unused = r3.zzo = r4     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r4 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            r5 = 9
            if (r4 < r5) goto L_0x00c9
            r4 = 1
            goto L_0x00ca
        L_0x00c9:
            r4 = 0
        L_0x00ca:
            boolean unused = r3.zzn = r4     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r4 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            r5 = 8
            if (r4 < r5) goto L_0x00d9
            r4 = 1
            goto L_0x00da
        L_0x00d9:
            r4 = 0
        L_0x00da:
            boolean unused = r3.zzm = r4     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r4 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            r5 = 6
            if (r4 < r5) goto L_0x00e7
            goto L_0x00e8
        L_0x00e7:
            r9 = 0
        L_0x00e8:
            boolean unused = r3.zzl = r9     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int r3 = r3.zzk     // Catch:{ Exception -> 0x0031 }
            if (r3 >= r0) goto L_0x00fa
            java.lang.String r0 = "BillingClient"
            java.lang.String r3 = "In-app billing API version 3 is not supported on this device."
            com.google.android.gms.internal.play_billing.zza.zzk(r0, r3)     // Catch:{ Exception -> 0x0031 }
        L_0x00fa:
            if (r6 != 0) goto L_0x0103
            com.android.billingclient.api.BillingClientImpl r0 = r10.zza     // Catch:{ Exception -> 0x0031 }
            r3 = 2
            int unused = r0.zza = r3     // Catch:{ Exception -> 0x0031 }
            goto L_0x0120
        L_0x0103:
            com.android.billingclient.api.BillingClientImpl r0 = r10.zza     // Catch:{ Exception -> 0x0031 }
            int unused = r0.zza = r1     // Catch:{ Exception -> 0x0031 }
            com.android.billingclient.api.BillingClientImpl r0 = r10.zza     // Catch:{ Exception -> 0x0031 }
            com.google.android.gms.internal.play_billing.zzd unused = r0.zzg = r2     // Catch:{ Exception -> 0x0031 }
            goto L_0x0120
        L_0x010e:
            java.lang.String r3 = "BillingClient"
            java.lang.String r4 = "Exception while checking if billing is supported; try to reconnect"
            com.google.android.gms.internal.play_billing.zza.zzk(r3, r4)
            com.android.billingclient.api.BillingClientImpl r3 = r10.zza
            int unused = r3.zza = r1
            com.android.billingclient.api.BillingClientImpl r1 = r10.zza
            com.google.android.gms.internal.play_billing.zzd unused = r1.zzg = r2
            r6 = r0
        L_0x0120:
            if (r6 != 0) goto L_0x0128
            com.android.billingclient.api.BillingResult r0 = com.android.billingclient.api.zzak.zzp
            r10.zzd(r0)
            goto L_0x012d
        L_0x0128:
            com.android.billingclient.api.BillingResult r0 = com.android.billingclient.api.zzak.zza
            r10.zzd(r0)
        L_0x012d:
            return r2
        L_0x012e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x012e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.zzaf.zza():java.lang.Object");
    }

    public final /* synthetic */ void zzb() {
        int unused = this.zza.zza = 0;
        zzd unused2 = this.zza.zzg = null;
        zzd(zzak.zzr);
    }

    /* access modifiers changed from: package-private */
    public final void zzc() {
        synchronized (this.zzb) {
            this.zzd = null;
            this.zzc = true;
        }
    }
}
