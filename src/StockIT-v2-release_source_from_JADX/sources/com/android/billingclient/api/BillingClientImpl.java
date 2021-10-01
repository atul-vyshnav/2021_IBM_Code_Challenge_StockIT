package com.android.billingclient.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.internal.play_billing.zza;
import com.google.android.gms.internal.play_billing.zzd;
import com.google.android.gms.internal.play_billing.zzp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
class BillingClientImpl extends BillingClient {
    /* access modifiers changed from: private */
    public volatile int zza;
    private final String zzb;
    private final Handler zzc;
    private volatile zzh zzd;
    private Context zze;
    /* access modifiers changed from: private */
    public Context zzf;
    /* access modifiers changed from: private */
    public volatile zzd zzg;
    private volatile zzaf zzh;
    /* access modifiers changed from: private */
    public boolean zzi;
    /* access modifiers changed from: private */
    public boolean zzj;
    /* access modifiers changed from: private */
    public int zzk;
    /* access modifiers changed from: private */
    public boolean zzl;
    /* access modifiers changed from: private */
    public boolean zzm;
    /* access modifiers changed from: private */
    public boolean zzn;
    /* access modifiers changed from: private */
    public boolean zzo;
    /* access modifiers changed from: private */
    public boolean zzp;
    /* access modifiers changed from: private */
    public boolean zzq;
    /* access modifiers changed from: private */
    public boolean zzr;
    /* access modifiers changed from: private */
    public boolean zzs;
    private boolean zzt;
    private ExecutorService zzu;

    private BillingClientImpl(Activity activity, boolean z, String str) {
        this(activity.getApplicationContext(), z, new zzah(), str, (String) null);
    }

    private void initialize(Context context, PurchasesUpdatedListener purchasesUpdatedListener, boolean z) {
        Context applicationContext = context.getApplicationContext();
        this.zzf = applicationContext;
        this.zzd = new zzh(applicationContext, purchasesUpdatedListener);
        this.zze = context;
        this.zzt = z;
    }

    private int launchBillingFlowCpp(Activity activity, BillingFlowParams billingFlowParams) {
        return launchBillingFlow(activity, billingFlowParams).getResponseCode();
    }

    private void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, long j) {
        launchPriceChangeConfirmationFlow(activity, priceChangeFlowParams, (PriceChangeConfirmationListener) new zzah(j));
    }

    private void startConnection(long j) {
        zzah zzah = new zzah(j);
        if (isReady()) {
            zza.zzj("BillingClient", "Service connection is valid. No need to re-initialize.");
            zzah.onBillingSetupFinished(zzak.zzp);
        } else if (this.zza == 1) {
            zza.zzk("BillingClient", "Client is already in the process of connecting to billing service.");
            zzah.onBillingSetupFinished(zzak.zzd);
        } else if (this.zza == 3) {
            zza.zzk("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            zzah.onBillingSetupFinished(zzak.zzq);
        } else {
            this.zza = 1;
            this.zzd.zzd();
            zza.zzj("BillingClient", "Starting in-app billing setup.");
            this.zzh = new zzaf(this, zzah, (zzy) null);
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage("com.android.vending");
            List<ResolveInfo> queryIntentServices = this.zzf.getPackageManager().queryIntentServices(intent, 0);
            if (queryIntentServices != null && !queryIntentServices.isEmpty()) {
                ResolveInfo resolveInfo = queryIntentServices.get(0);
                if (resolveInfo.serviceInfo != null) {
                    String str = resolveInfo.serviceInfo.packageName;
                    String str2 = resolveInfo.serviceInfo.name;
                    if (!"com.android.vending".equals(str) || str2 == null) {
                        zza.zzk("BillingClient", "The device doesn't have valid Play Store.");
                    } else {
                        ComponentName componentName = new ComponentName(str, str2);
                        Intent intent2 = new Intent(intent);
                        intent2.setComponent(componentName);
                        intent2.putExtra("playBillingLibraryVersion", this.zzb);
                        if (this.zzf.bindService(intent2, this.zzh, 1)) {
                            zza.zzj("BillingClient", "Service was bonded successfully.");
                            return;
                        }
                        zza.zzk("BillingClient", "Connection to Billing service is blocked.");
                    }
                }
            }
            this.zza = 0;
            zza.zzj("BillingClient", "Billing service unavailable on device.");
            zzah.onBillingSetupFinished(zzak.zzc);
        }
    }

    /* access modifiers changed from: private */
    public final Handler zzD() {
        return Looper.myLooper() == null ? this.zzc : new Handler(Looper.myLooper());
    }

    private final BillingResult zzE(BillingResult billingResult) {
        if (Thread.interrupted()) {
            return billingResult;
        }
        this.zzc.post(new zzq(this, billingResult));
        return billingResult;
    }

    private final BillingResult zzG(String str) {
        try {
            if (((Integer) zzH(new zzn(this, str), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (Runnable) null, zzD()).get(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, TimeUnit.MILLISECONDS)).intValue() == 0) {
                return zzak.zzp;
            }
            return zzak.zzi;
        } catch (Exception unused) {
            zza.zzk("BillingClient", "Exception while checking if billing is supported; try to reconnect");
            return zzak.zzq;
        }
    }

    /* access modifiers changed from: private */
    public final <T> Future<T> zzH(Callable<T> callable, long j, Runnable runnable, Handler handler) {
        long j2 = (long) (((double) j) * 0.95d);
        if (this.zzu == null) {
            this.zzu = Executors.newFixedThreadPool(zza.zza, new zzac(this));
        }
        try {
            Future<T> submit = this.zzu.submit(callable);
            handler.postDelayed(new zzw(submit, runnable), j2);
            return submit;
        } catch (Exception e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 28);
            sb.append("Async task throws exception ");
            sb.append(valueOf);
            zza.zzk("BillingClient", sb.toString());
            return null;
        }
    }

    private final void zzI(BillingResult billingResult, PriceChangeConfirmationListener priceChangeConfirmationListener) {
        if (!Thread.interrupted()) {
            this.zzc.post(new zzs(priceChangeConfirmationListener, billingResult));
        }
    }

    static /* synthetic */ zzag zzi(BillingClientImpl billingClientImpl, String str) {
        String str2;
        String valueOf = String.valueOf(str);
        if (valueOf.length() != 0) {
            str2 = "Querying purchase history, item type: ".concat(valueOf);
        } else {
            str2 = new String("Querying purchase history, item type: ");
        }
        zza.zzj("BillingClient", str2);
        ArrayList arrayList = new ArrayList();
        Bundle zzf2 = zza.zzf(billingClientImpl.zzn, billingClientImpl.zzt, billingClientImpl.zzb);
        String str3 = null;
        while (billingClientImpl.zzl) {
            try {
                Bundle zzh2 = billingClientImpl.zzg.zzh(6, billingClientImpl.zzf.getPackageName(), str, str3, zzf2);
                BillingResult zza2 = zzam.zza(zzh2, "BillingClient", "getPurchaseHistory()");
                if (zza2 != zzak.zzp) {
                    return new zzag(zza2, (List<PurchaseHistoryRecord>) null);
                }
                ArrayList<String> stringArrayList = zzh2.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = zzh2.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = zzh2.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                int i = 0;
                while (i < stringArrayList2.size()) {
                    String str4 = stringArrayList2.get(i);
                    String str5 = stringArrayList3.get(i);
                    String valueOf2 = String.valueOf(stringArrayList.get(i));
                    zza.zzj("BillingClient", valueOf2.length() != 0 ? "Purchase record found for sku : ".concat(valueOf2) : new String("Purchase record found for sku : "));
                    try {
                        PurchaseHistoryRecord purchaseHistoryRecord = new PurchaseHistoryRecord(str4, str5);
                        if (TextUtils.isEmpty(purchaseHistoryRecord.getPurchaseToken())) {
                            zza.zzk("BillingClient", "BUG: empty/null token!");
                        }
                        arrayList.add(purchaseHistoryRecord);
                        i++;
                    } catch (JSONException e) {
                        String valueOf3 = String.valueOf(e);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 48);
                        sb.append("Got an exception trying to decode the purchase: ");
                        sb.append(valueOf3);
                        zza.zzk("BillingClient", sb.toString());
                        return new zzag(zzak.zzl, (List<PurchaseHistoryRecord>) null);
                    }
                }
                str3 = zzh2.getString("INAPP_CONTINUATION_TOKEN");
                String valueOf4 = String.valueOf(str3);
                zza.zzj("BillingClient", valueOf4.length() != 0 ? "Continuation token: ".concat(valueOf4) : new String("Continuation token: "));
                if (TextUtils.isEmpty(str3)) {
                    return new zzag(zzak.zzp, arrayList);
                }
            } catch (RemoteException e2) {
                String valueOf5 = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf5).length() + 64);
                sb2.append("Got exception trying to get purchase history: ");
                sb2.append(valueOf5);
                sb2.append("; try to reconnect");
                zza.zzk("BillingClient", sb2.toString());
                return new zzag(zzak.zzq, (List<PurchaseHistoryRecord>) null);
            }
        }
        zza.zzk("BillingClient", "getPurchaseHistory is not supported on current device");
        return new zzag(zzak.zzj, (List<PurchaseHistoryRecord>) null);
    }

    static /* synthetic */ Purchase.PurchasesResult zzk(BillingClientImpl billingClientImpl, String str) {
        Bundle bundle;
        String valueOf = String.valueOf(str);
        zza.zzj("BillingClient", valueOf.length() != 0 ? "Querying owned items, item type: ".concat(valueOf) : new String("Querying owned items, item type: "));
        ArrayList arrayList = new ArrayList();
        Bundle zzf2 = zza.zzf(billingClientImpl.zzn, billingClientImpl.zzt, billingClientImpl.zzb);
        String str2 = null;
        do {
            try {
                if (billingClientImpl.zzn) {
                    bundle = billingClientImpl.zzg.zzj(9, billingClientImpl.zzf.getPackageName(), str, str2, zzf2);
                } else {
                    bundle = billingClientImpl.zzg.zzi(3, billingClientImpl.zzf.getPackageName(), str, str2);
                }
                BillingResult zza2 = zzam.zza(bundle, "BillingClient", "getPurchase()");
                if (zza2 != zzak.zzp) {
                    return new Purchase.PurchasesResult(zza2, (List<Purchase>) null);
                }
                ArrayList<String> stringArrayList = bundle.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = bundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = bundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                int i = 0;
                while (i < stringArrayList2.size()) {
                    String str3 = stringArrayList2.get(i);
                    String str4 = stringArrayList3.get(i);
                    String valueOf2 = String.valueOf(stringArrayList.get(i));
                    zza.zzj("BillingClient", valueOf2.length() != 0 ? "Sku is owned: ".concat(valueOf2) : new String("Sku is owned: "));
                    try {
                        Purchase purchase = new Purchase(str3, str4);
                        if (TextUtils.isEmpty(purchase.getPurchaseToken())) {
                            zza.zzk("BillingClient", "BUG: empty/null token!");
                        }
                        arrayList.add(purchase);
                        i++;
                    } catch (JSONException e) {
                        String valueOf3 = String.valueOf(e);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 48);
                        sb.append("Got an exception trying to decode the purchase: ");
                        sb.append(valueOf3);
                        zza.zzk("BillingClient", sb.toString());
                        return new Purchase.PurchasesResult(zzak.zzl, (List<Purchase>) null);
                    }
                }
                str2 = bundle.getString("INAPP_CONTINUATION_TOKEN");
                String valueOf4 = String.valueOf(str2);
                zza.zzj("BillingClient", valueOf4.length() != 0 ? "Continuation token: ".concat(valueOf4) : new String("Continuation token: "));
            } catch (Exception e2) {
                String valueOf5 = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf5).length() + 57);
                sb2.append("Got exception trying to get purchases: ");
                sb2.append(valueOf5);
                sb2.append("; try to reconnect");
                zza.zzk("BillingClient", sb2.toString());
                return new Purchase.PurchasesResult(zzak.zzq, (List<Purchase>) null);
            }
        } while (!TextUtils.isEmpty(str2));
        return new Purchase.PurchasesResult(zzak.zzp, arrayList);
    }

    public final void acknowledgePurchase(AcknowledgePurchaseParams acknowledgePurchaseParams, AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        if (!isReady()) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzak.zzq);
        } else if (TextUtils.isEmpty(acknowledgePurchaseParams.getPurchaseToken())) {
            zza.zzk("BillingClient", "Please provide a valid purchase token.");
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzak.zzk);
        } else if (!this.zzn) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzak.zzb);
        } else {
            if (zzH(new zzk(this, acknowledgePurchaseParams, acknowledgePurchaseResponseListener), 30000, new zzj(acknowledgePurchaseResponseListener), zzD()) == null) {
                acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzF());
            }
        }
    }

    public final void consumeAsync(ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) {
        if (!isReady()) {
            consumeResponseListener.onConsumeResponse(zzak.zzq, consumeParams.getPurchaseToken());
            return;
        }
        if (zzH(new zzl(this, consumeParams, consumeResponseListener), 30000, new zzr(consumeResponseListener, consumeParams), zzD()) == null) {
            consumeResponseListener.onConsumeResponse(zzF(), consumeParams.getPurchaseToken());
        }
    }

    public final void endConnection() {
        try {
            this.zze = null;
            this.zzd.zzc();
            if (this.zzh != null) {
                this.zzh.zzc();
            }
            if (!(this.zzh == null || this.zzg == null)) {
                zza.zzj("BillingClient", "Unbinding from service.");
                this.zzf.unbindService(this.zzh);
                this.zzh = null;
            }
            this.zzg = null;
            ExecutorService executorService = this.zzu;
            if (executorService != null) {
                executorService.shutdownNow();
                this.zzu = null;
            }
        } catch (Exception e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
            sb.append("There was an exception while ending connection: ");
            sb.append(valueOf);
            zza.zzk("BillingClient", sb.toString());
        } finally {
            this.zza = 3;
        }
    }

    public final int getConnectionState() {
        return this.zza;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.android.billingclient.api.BillingResult isFeatureSupported(java.lang.String r3) {
        /*
            r2 = this;
            boolean r0 = r2.isReady()
            if (r0 != 0) goto L_0x0009
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzq
            return r3
        L_0x0009:
            int r0 = r3.hashCode()
            switch(r0) {
                case -422092961: goto L_0x0070;
                case 96321: goto L_0x0066;
                case 97314: goto L_0x005c;
                case 98307: goto L_0x0051;
                case 99300: goto L_0x0047;
                case 100293: goto L_0x003c;
                case 207616302: goto L_0x0032;
                case 292218239: goto L_0x0028;
                case 1219490065: goto L_0x001d;
                case 1987365622: goto L_0x0012;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x007b
        L_0x0012:
            java.lang.String r0 = "subscriptions"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 0
            goto L_0x007c
        L_0x001d:
            java.lang.String r0 = "subscriptionsOnVr"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 3
            goto L_0x007c
        L_0x0028:
            java.lang.String r0 = "inAppItemsOnVr"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 2
            goto L_0x007c
        L_0x0032:
            java.lang.String r0 = "priceChangeConfirmation"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 4
            goto L_0x007c
        L_0x003c:
            java.lang.String r0 = "eee"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 9
            goto L_0x007c
        L_0x0047:
            java.lang.String r0 = "ddd"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 7
            goto L_0x007c
        L_0x0051:
            java.lang.String r0 = "ccc"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 8
            goto L_0x007c
        L_0x005c:
            java.lang.String r0 = "bbb"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 5
            goto L_0x007c
        L_0x0066:
            java.lang.String r0 = "aaa"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 6
            goto L_0x007c
        L_0x0070:
            java.lang.String r0 = "subscriptionsUpdate"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x007b
            r0 = 1
            goto L_0x007c
        L_0x007b:
            r0 = -1
        L_0x007c:
            switch(r0) {
                case 0: goto L_0x00db;
                case 1: goto L_0x00d1;
                case 2: goto L_0x00ca;
                case 3: goto L_0x00c2;
                case 4: goto L_0x00b8;
                case 5: goto L_0x00ae;
                case 6: goto L_0x00a4;
                case 7: goto L_0x009a;
                case 8: goto L_0x0090;
                case 9: goto L_0x0090;
                default: goto L_0x007f;
            }
        L_0x007f:
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r0 = "Unsupported feature: "
            int r1 = r3.length()
            if (r1 == 0) goto L_0x00e5
            java.lang.String r3 = r0.concat(r3)
            goto L_0x00ea
        L_0x0090:
            boolean r3 = r2.zzs
            if (r3 == 0) goto L_0x0097
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzp
            goto L_0x0099
        L_0x0097:
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzi
        L_0x0099:
            return r3
        L_0x009a:
            boolean r3 = r2.zzq
            if (r3 == 0) goto L_0x00a1
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzp
            goto L_0x00a3
        L_0x00a1:
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzi
        L_0x00a3:
            return r3
        L_0x00a4:
            boolean r3 = r2.zzr
            if (r3 == 0) goto L_0x00ab
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzp
            goto L_0x00ad
        L_0x00ab:
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzi
        L_0x00ad:
            return r3
        L_0x00ae:
            boolean r3 = r2.zzp
            if (r3 == 0) goto L_0x00b5
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzp
            goto L_0x00b7
        L_0x00b5:
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzi
        L_0x00b7:
            return r3
        L_0x00b8:
            boolean r3 = r2.zzm
            if (r3 == 0) goto L_0x00bf
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzp
            goto L_0x00c1
        L_0x00bf:
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzi
        L_0x00c1:
            return r3
        L_0x00c2:
            java.lang.String r3 = "subs"
            com.android.billingclient.api.BillingResult r3 = r2.zzG(r3)
            return r3
        L_0x00ca:
            java.lang.String r3 = "inapp"
            com.android.billingclient.api.BillingResult r3 = r2.zzG(r3)
            return r3
        L_0x00d1:
            boolean r3 = r2.zzj
            if (r3 == 0) goto L_0x00d8
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzp
            goto L_0x00da
        L_0x00d8:
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzi
        L_0x00da:
            return r3
        L_0x00db:
            boolean r3 = r2.zzi
            if (r3 == 0) goto L_0x00e2
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzp
            goto L_0x00e4
        L_0x00e2:
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzi
        L_0x00e4:
            return r3
        L_0x00e5:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r0)
        L_0x00ea:
            java.lang.String r0 = "BillingClient"
            com.google.android.gms.internal.play_billing.zza.zzk(r0, r3)
            com.android.billingclient.api.BillingResult r3 = com.android.billingclient.api.zzak.zzv
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.BillingClientImpl.isFeatureSupported(java.lang.String):com.android.billingclient.api.BillingResult");
    }

    public final boolean isReady() {
        return (this.zza != 2 || this.zzg == null || this.zzh == null) ? false : true;
    }

    public final BillingResult launchBillingFlow(Activity activity, BillingFlowParams billingFlowParams) {
        String str;
        String str2;
        String str3;
        Future future;
        boolean z;
        int i;
        int i2;
        String str4;
        Activity activity2 = activity;
        String str5 = "BUY_INTENT";
        if (!isReady()) {
            BillingResult billingResult = zzak.zzq;
            zzE(billingResult);
            return billingResult;
        }
        ArrayList<SkuDetails> zzj2 = billingFlowParams.zzj();
        SkuDetails skuDetails = zzj2.get(0);
        String type = skuDetails.getType();
        if (type.equals(BillingClient.SkuType.SUBS) && !this.zzi) {
            zza.zzk("BillingClient", "Current client doesn't support subscriptions.");
            BillingResult billingResult2 = zzak.zzs;
            zzE(billingResult2);
            return billingResult2;
        } else if (billingFlowParams.zzm() && !this.zzl) {
            zza.zzk("BillingClient", "Current client doesn't support extra params for buy intent.");
            BillingResult billingResult3 = zzak.zzh;
            zzE(billingResult3);
            return billingResult3;
        } else if (zzj2.size() <= 1 || this.zzs) {
            String str6 = "";
            for (int i3 = 0; i3 < zzj2.size(); i3++) {
                String valueOf = String.valueOf(str6);
                String valueOf2 = String.valueOf(zzj2.get(i3));
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + String.valueOf(valueOf2).length());
                sb.append(valueOf);
                sb.append(valueOf2);
                String sb2 = sb.toString();
                if (i3 < zzj2.size() - 1) {
                    sb2 = String.valueOf(sb2).concat(", ");
                }
                str6 = sb2;
            }
            StringBuilder sb3 = new StringBuilder(String.valueOf(str6).length() + 41 + String.valueOf(type).length());
            sb3.append("Constructing buy intent for ");
            sb3.append(str6);
            sb3.append(", item type: ");
            sb3.append(type);
            zza.zzj("BillingClient", sb3.toString());
            if (this.zzl) {
                Bundle zze2 = zza.zze(billingFlowParams, this.zzn, this.zzt, this.zzb);
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                String str7 = "";
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                str3 = "; try to reconnect";
                int size = zzj2.size();
                str2 = str6;
                int i4 = 0;
                boolean z2 = false;
                boolean z3 = false;
                boolean z4 = false;
                boolean z5 = false;
                while (i4 < size) {
                    SkuDetails skuDetails2 = zzj2.get(i4);
                    if (!skuDetails2.zze().isEmpty()) {
                        i2 = size;
                        arrayList.add(skuDetails2.zze());
                    } else {
                        i2 = size;
                    }
                    String str8 = str5;
                    try {
                        str4 = new JSONObject(skuDetails2.getOriginalJson()).optString("offer_id_token");
                    } catch (JSONException unused) {
                        str4 = str7;
                    }
                    String zzb2 = skuDetails2.zzb();
                    int zza2 = skuDetails2.zza();
                    String zzd2 = skuDetails2.zzd();
                    arrayList2.add(str4);
                    z2 |= !TextUtils.isEmpty(str4);
                    arrayList3.add(zzb2);
                    z3 |= !TextUtils.isEmpty(zzb2);
                    arrayList4.add(Integer.valueOf(zza2));
                    z4 |= zza2 != 0;
                    z5 |= !TextUtils.isEmpty(zzd2);
                    arrayList5.add(zzd2);
                    i4++;
                    Activity activity3 = activity;
                    size = i2;
                    str5 = str8;
                }
                str = str5;
                if (!arrayList.isEmpty()) {
                    zze2.putStringArrayList("skuDetailsTokens", arrayList);
                }
                if (z2) {
                    if (!this.zzq) {
                        BillingResult billingResult4 = zzak.zzi;
                        zzE(billingResult4);
                        return billingResult4;
                    }
                    zze2.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList2);
                }
                if (z3) {
                    zze2.putStringArrayList("SKU_OFFER_ID_LIST", arrayList3);
                }
                if (z4) {
                    zze2.putIntegerArrayList("SKU_OFFER_TYPE_LIST", arrayList4);
                }
                if (z5) {
                    zze2.putStringArrayList("SKU_SERIALIZED_DOCID_LIST", arrayList5);
                }
                if (!TextUtils.isEmpty(skuDetails.zzc())) {
                    zze2.putString("skuPackageName", skuDetails.zzc());
                    z = true;
                } else {
                    z = false;
                }
                if (!TextUtils.isEmpty((CharSequence) null)) {
                    zze2.putString("accountName", (String) null);
                }
                if (zzj2.size() > 1) {
                    ArrayList arrayList6 = new ArrayList(zzj2.size() - 1);
                    ArrayList arrayList7 = new ArrayList(zzj2.size() - 1);
                    for (int i5 = 1; i5 < zzj2.size(); i5++) {
                        arrayList6.add(zzj2.get(i5).getSku());
                        arrayList7.add(zzj2.get(i5).getType());
                    }
                    zze2.putStringArrayList("additionalSkus", arrayList6);
                    zze2.putStringArrayList("additionalSkuTypes", arrayList7);
                }
                if (!TextUtils.isEmpty(activity.getIntent().getStringExtra("PROXY_PACKAGE"))) {
                    String stringExtra = activity.getIntent().getStringExtra("PROXY_PACKAGE");
                    zze2.putString("proxyPackage", stringExtra);
                    try {
                        zze2.putString("proxyPackageVersion", this.zzf.getPackageManager().getPackageInfo(stringExtra, 0).versionName);
                    } catch (PackageManager.NameNotFoundException unused2) {
                        zze2.putString("proxyPackageVersion", "package not found");
                    }
                }
                if (!this.zzr || !z) {
                    i = this.zzn ? 9 : billingFlowParams.getVrPurchaseFlow() ? 7 : 6;
                } else {
                    i = 15;
                }
                future = zzH(new zzx(this, i, skuDetails, type, billingFlowParams, zze2), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (Runnable) null, this.zzc);
            } else {
                str = str5;
                str3 = "; try to reconnect";
                str2 = str6;
                future = zzH(new zzm(this, skuDetails, type), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (Runnable) null, this.zzc);
            }
            try {
                Bundle bundle = (Bundle) future.get(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, TimeUnit.MILLISECONDS);
                int zza3 = zza.zza(bundle, "BillingClient");
                String zzh2 = zza.zzh(bundle, "BillingClient");
                if (zza3 != 0) {
                    StringBuilder sb4 = new StringBuilder(52);
                    sb4.append("Unable to buy item, Error response code: ");
                    sb4.append(zza3);
                    zza.zzk("BillingClient", sb4.toString());
                    BillingResult.Builder newBuilder = BillingResult.newBuilder();
                    newBuilder.setResponseCode(zza3);
                    newBuilder.setDebugMessage(zzh2);
                    BillingResult build = newBuilder.build();
                    zzE(build);
                    return build;
                }
                Activity activity4 = activity;
                Intent intent = new Intent(activity4, ProxyBillingActivity.class);
                String str9 = str;
                intent.putExtra(str9, (PendingIntent) bundle.getParcelable(str9));
                activity4.startActivity(intent);
                return zzak.zzp;
            } catch (CancellationException | TimeoutException unused3) {
                String str10 = str2;
                StringBuilder sb5 = new StringBuilder(String.valueOf(str10).length() + 68);
                sb5.append("Time out while launching billing flow: ; for sku: ");
                sb5.append(str10);
                sb5.append(str3);
                zza.zzk("BillingClient", sb5.toString());
                BillingResult billingResult5 = zzak.zzr;
                zzE(billingResult5);
                return billingResult5;
            } catch (Exception unused4) {
                StringBuilder sb6 = new StringBuilder(String.valueOf(str2).length() + 69);
                sb6.append("Exception while launching billing flow: ; for sku: ");
                sb6.append(str2);
                sb6.append(str3);
                zza.zzk("BillingClient", sb6.toString());
                BillingResult billingResult6 = zzak.zzq;
                zzE(billingResult6);
                return billingResult6;
            }
        } else {
            zza.zzk("BillingClient", "Current client doesn't support multi-item purchases.");
            BillingResult billingResult7 = zzak.zzu;
            zzE(billingResult7);
            return billingResult7;
        }
    }

    public final void queryPurchaseHistoryAsync(String str, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        if (!isReady()) {
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(zzak.zzq, (List<PurchaseHistoryRecord>) null);
            return;
        }
        if (zzH(new zzab(this, str, purchaseHistoryResponseListener), 30000, new zzt(purchaseHistoryResponseListener), zzD()) == null) {
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(zzF(), (List<PurchaseHistoryRecord>) null);
        }
    }

    public final Purchase.PurchasesResult queryPurchases(String str) {
        if (!isReady()) {
            return new Purchase.PurchasesResult(zzak.zzq, (List<Purchase>) null);
        }
        if (TextUtils.isEmpty(str)) {
            zza.zzk("BillingClient", "Please provide a valid SKU type.");
            return new Purchase.PurchasesResult(zzak.zzg, (List<Purchase>) null);
        }
        try {
            return (Purchase.PurchasesResult) zzH(new zzz(this, str), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (Runnable) null, this.zzc).get(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, TimeUnit.MILLISECONDS);
        } catch (CancellationException | TimeoutException unused) {
            return new Purchase.PurchasesResult(zzak.zzr, (List<Purchase>) null);
        } catch (Exception unused2) {
            return new Purchase.PurchasesResult(zzak.zzl, (List<Purchase>) null);
        }
    }

    public void queryPurchasesAsync(String str, PurchasesResponseListener purchasesResponseListener) {
        if (!isReady()) {
            purchasesResponseListener.onQueryPurchasesResponse(zzak.zzq, zzp.zzg());
        } else if (TextUtils.isEmpty(str)) {
            zza.zzk("BillingClient", "Please provide a valid SKU type.");
            purchasesResponseListener.onQueryPurchasesResponse(zzak.zzg, zzp.zzg());
        } else {
            if (zzH(new zzaa(this, str, purchasesResponseListener), 30000, new zzu(purchasesResponseListener), zzD()) == null) {
                purchasesResponseListener.onQueryPurchasesResponse(zzF(), zzp.zzg());
            }
        }
    }

    public final void querySkuDetailsAsync(SkuDetailsParams skuDetailsParams, SkuDetailsResponseListener skuDetailsResponseListener) {
        if (!isReady()) {
            skuDetailsResponseListener.onSkuDetailsResponse(zzak.zzq, (List<SkuDetails>) null);
            return;
        }
        String skuType = skuDetailsParams.getSkuType();
        List<String> skusList = skuDetailsParams.getSkusList();
        if (TextUtils.isEmpty(skuType)) {
            zza.zzk("BillingClient", "Please fix the input params. SKU type can't be empty.");
            skuDetailsResponseListener.onSkuDetailsResponse(zzak.zzg, (List<SkuDetails>) null);
        } else if (skusList != null) {
            ArrayList arrayList = new ArrayList();
            for (String zza2 : skusList) {
                zzap zzap = new zzap((zzao) null);
                zzap.zza(zza2);
                arrayList.add(zzap.zzb());
            }
            if (zzH(new zzp(this, skuType, arrayList, (String) null, skuDetailsResponseListener), 30000, new zzv(skuDetailsResponseListener), zzD()) == null) {
                skuDetailsResponseListener.onSkuDetailsResponse(zzF(), (List<SkuDetails>) null);
            }
        } else {
            zza.zzk("BillingClient", "Please fix the input params. The list of SKUs can't be empty - set SKU list or SkuWithOffer list.");
            skuDetailsResponseListener.onSkuDetailsResponse(zzak.zzf, (List<SkuDetails>) null);
        }
    }

    public final /* synthetic */ Bundle zze(int i, SkuDetails skuDetails, String str, BillingFlowParams billingFlowParams, Bundle bundle) throws Exception {
        return this.zzg.zzg(i, this.zzf.getPackageName(), skuDetails.getSku(), str, (String) null, bundle);
    }

    public final /* synthetic */ Bundle zzf(SkuDetails skuDetails, String str) throws Exception {
        return this.zzg.zzf(3, this.zzf.getPackageName(), skuDetails.getSku(), str, (String) null);
    }

    public final /* synthetic */ Bundle zzg(String str, Bundle bundle) throws Exception {
        return this.zzg.zzm(8, this.zzf.getPackageName(), str, BillingClient.SkuType.SUBS, bundle);
    }

    public final /* synthetic */ Integer zzn(String str) throws Exception {
        zzd zzd2 = this.zzg;
        String packageName = this.zzf.getPackageName();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BillingFlowParams.EXTRA_PARAM_KEY_VR, true);
        return Integer.valueOf(zzd2.zzc(7, packageName, str, bundle));
    }

    public final /* synthetic */ Object zzo(AcknowledgePurchaseParams acknowledgePurchaseParams, AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) throws Exception {
        try {
            Bundle zzd2 = this.zzg.zzd(9, this.zzf.getPackageName(), acknowledgePurchaseParams.getPurchaseToken(), zza.zzb(acknowledgePurchaseParams, this.zzb));
            int zza2 = zza.zza(zzd2, "BillingClient");
            String zzh2 = zza.zzh(zzd2, "BillingClient");
            BillingResult.Builder newBuilder = BillingResult.newBuilder();
            newBuilder.setResponseCode(zza2);
            newBuilder.setDebugMessage(zzh2);
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(newBuilder.build());
            return null;
        } catch (Exception e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
            sb.append("Error acknowledge purchase; ex: ");
            sb.append(valueOf);
            zza.zzk("BillingClient", sb.toString());
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzak.zzq);
            return null;
        }
    }

    public final /* synthetic */ Object zzp(ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) throws Exception {
        String str;
        int i;
        String purchaseToken = consumeParams.getPurchaseToken();
        try {
            String valueOf = String.valueOf(purchaseToken);
            zza.zzj("BillingClient", valueOf.length() != 0 ? "Consuming purchase with token: ".concat(valueOf) : new String("Consuming purchase with token: "));
            if (this.zzn) {
                Bundle zze2 = this.zzg.zze(9, this.zzf.getPackageName(), purchaseToken, zza.zzc(consumeParams, this.zzn, this.zzb));
                i = zze2.getInt("RESPONSE_CODE");
                str = zza.zzh(zze2, "BillingClient");
            } else {
                i = this.zzg.zza(3, this.zzf.getPackageName(), purchaseToken);
                str = "";
            }
            BillingResult.Builder newBuilder = BillingResult.newBuilder();
            newBuilder.setResponseCode(i);
            newBuilder.setDebugMessage(str);
            BillingResult build = newBuilder.build();
            if (i == 0) {
                zza.zzj("BillingClient", "Successfully consumed purchase.");
                consumeResponseListener.onConsumeResponse(build, purchaseToken);
                return null;
            }
            StringBuilder sb = new StringBuilder(63);
            sb.append("Error consuming purchase with token. Response code: ");
            sb.append(i);
            zza.zzk("BillingClient", sb.toString());
            consumeResponseListener.onConsumeResponse(build, purchaseToken);
            return null;
        } catch (Exception e) {
            String valueOf2 = String.valueOf(e);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 30);
            sb2.append("Error consuming purchase; ex: ");
            sb2.append(valueOf2);
            zza.zzk("BillingClient", sb2.toString());
            consumeResponseListener.onConsumeResponse(zzak.zzq, purchaseToken);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x008d, code lost:
        r0 = null;
        r4 = 4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object zzq(java.lang.String r22, java.util.List r23, java.lang.String r24, com.android.billingclient.api.SkuDetailsResponseListener r25) throws java.lang.Exception {
        /*
            r21 = this;
            r1 = r21
            java.lang.String r2 = "BillingClient"
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            int r3 = r23.size()
            r5 = 0
        L_0x000e:
            r6 = 6
            r7 = 4
            java.lang.String r8 = "Item is unavailable for purchase."
            r9 = 0
            if (r5 >= r3) goto L_0x013e
            int r10 = r5 + 20
            if (r10 <= r3) goto L_0x001b
            r11 = r3
            goto L_0x001c
        L_0x001b:
            r11 = r10
        L_0x001c:
            java.util.ArrayList r12 = new java.util.ArrayList
            r13 = r23
            java.util.List r5 = r13.subList(r5, r11)
            r12.<init>(r5)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            int r11 = r12.size()
            r14 = 0
        L_0x0031:
            if (r14 >= r11) goto L_0x0043
            java.lang.Object r15 = r12.get(r14)
            com.android.billingclient.api.zzaq r15 = (com.android.billingclient.api.zzaq) r15
            java.lang.String r15 = r15.zza()
            r5.add(r15)
            int r14 = r14 + 1
            goto L_0x0031
        L_0x0043:
            android.os.Bundle r11 = new android.os.Bundle
            r11.<init>()
            java.lang.String r14 = "ITEM_ID_LIST"
            r11.putStringArrayList(r14, r5)
            java.lang.String r5 = r1.zzb
            java.lang.String r14 = "playBillingLibraryVersion"
            r11.putString(r14, r5)
            boolean r5 = r1.zzo     // Catch:{ Exception -> 0x0116 }
            if (r5 == 0) goto L_0x0077
            com.google.android.gms.internal.play_billing.zzd r15 = r1.zzg     // Catch:{ Exception -> 0x0116 }
            r16 = 10
            android.content.Context r5 = r1.zzf     // Catch:{ Exception -> 0x0116 }
            java.lang.String r17 = r5.getPackageName()     // Catch:{ Exception -> 0x0116 }
            int r5 = r1.zzk     // Catch:{ Exception -> 0x0116 }
            boolean r14 = r1.zzt     // Catch:{ Exception -> 0x0116 }
            java.lang.String r4 = r1.zzb     // Catch:{ Exception -> 0x0116 }
            android.os.Bundle r20 = com.google.android.gms.internal.play_billing.zza.zzd(r5, r14, r4, r9, r12)     // Catch:{ Exception -> 0x0116 }
            r18 = r22
            r19 = r11
            android.os.Bundle r4 = r15.zzl(r16, r17, r18, r19, r20)     // Catch:{ Exception -> 0x0116 }
            r14 = r22
            goto L_0x0086
        L_0x0077:
            com.google.android.gms.internal.play_billing.zzd r4 = r1.zzg     // Catch:{ Exception -> 0x0116 }
            r5 = 3
            android.content.Context r12 = r1.zzf     // Catch:{ Exception -> 0x0116 }
            java.lang.String r12 = r12.getPackageName()     // Catch:{ Exception -> 0x0116 }
            r14 = r22
            android.os.Bundle r4 = r4.zzk(r5, r12, r14, r11)     // Catch:{ Exception -> 0x0116 }
        L_0x0086:
            if (r4 != 0) goto L_0x0091
            java.lang.String r0 = "querySkuDetailsAsync got null sku details list"
            com.google.android.gms.internal.play_billing.zza.zzk(r2, r0)
        L_0x008d:
            r0 = r9
            r4 = 4
            goto L_0x0141
        L_0x0091:
            java.lang.String r5 = "DETAILS_LIST"
            boolean r11 = r4.containsKey(r5)
            if (r11 != 0) goto L_0x00c4
            int r3 = com.google.android.gms.internal.play_billing.zza.zza(r4, r2)
            java.lang.String r8 = com.google.android.gms.internal.play_billing.zza.zzh(r4, r2)
            if (r3 == 0) goto L_0x00bc
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r5 = 50
            r4.<init>(r5)
            java.lang.String r5 = "getSkuDetails() failed. Response code: "
            r4.append(r5)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            com.google.android.gms.internal.play_billing.zza.zzk(r2, r4)
            r4 = r3
            goto L_0x0141
        L_0x00bc:
            java.lang.String r3 = "getSkuDetails() returned a bundle with neither an error nor a detail list."
            com.google.android.gms.internal.play_billing.zza.zzk(r2, r3)
        L_0x00c1:
            r4 = 6
            goto L_0x0141
        L_0x00c4:
            java.util.ArrayList r4 = r4.getStringArrayList(r5)
            if (r4 != 0) goto L_0x00d0
            java.lang.String r0 = "querySkuDetailsAsync got null response list"
            com.google.android.gms.internal.play_billing.zza.zzk(r2, r0)
            goto L_0x008d
        L_0x00d0:
            r5 = 0
        L_0x00d1:
            int r7 = r4.size()
            if (r5 >= r7) goto L_0x0113
            java.lang.Object r7 = r4.get(r5)
            java.lang.String r7 = (java.lang.String) r7
            com.android.billingclient.api.SkuDetails r8 = new com.android.billingclient.api.SkuDetails     // Catch:{ JSONException -> 0x010a }
            r8.<init>(r7)     // Catch:{ JSONException -> 0x010a }
            java.lang.String r7 = java.lang.String.valueOf(r8)
            java.lang.String r11 = java.lang.String.valueOf(r7)
            int r11 = r11.length()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            int r11 = r11 + 17
            r12.<init>(r11)
            java.lang.String r11 = "Got sku details: "
            r12.append(r11)
            r12.append(r7)
            java.lang.String r7 = r12.toString()
            com.google.android.gms.internal.play_billing.zza.zzj(r2, r7)
            r0.add(r8)
            int r5 = r5 + 1
            goto L_0x00d1
        L_0x010a:
            java.lang.String r0 = "Got a JSON exception trying to decode SkuDetails."
            com.google.android.gms.internal.play_billing.zza.zzk(r2, r0)
            java.lang.String r8 = "Error trying to decode SkuDetails."
            r0 = r9
            goto L_0x00c1
        L_0x0113:
            r5 = r10
            goto L_0x000e
        L_0x0116:
            r0 = move-exception
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r3 = java.lang.String.valueOf(r0)
            int r3 = r3.length()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            int r3 = r3 + 63
            r4.<init>(r3)
            java.lang.String r3 = "querySkuDetailsAsync got a remote exception (try to reconnect)."
            r4.append(r3)
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            com.google.android.gms.internal.play_billing.zza.zzk(r2, r0)
            r4 = -1
            java.lang.String r8 = "Service connection is disconnected."
            r0 = r9
            goto L_0x0141
        L_0x013e:
            java.lang.String r8 = ""
            r4 = 0
        L_0x0141:
            com.android.billingclient.api.BillingResult$Builder r2 = com.android.billingclient.api.BillingResult.newBuilder()
            r2.setResponseCode(r4)
            r2.setDebugMessage(r8)
            com.android.billingclient.api.BillingResult r2 = r2.build()
            r3 = r25
            r3.onSkuDetailsResponse(r2, r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.BillingClientImpl.zzq(java.lang.String, java.util.List, java.lang.String, com.android.billingclient.api.SkuDetailsResponseListener):java.lang.Object");
    }

    public final /* synthetic */ void zzs(BillingResult billingResult) {
        this.zzd.zzb().onPurchasesUpdated(billingResult, (List<Purchase>) null);
    }

    /* access modifiers changed from: private */
    public final BillingResult zzF() {
        if (this.zza == 0 || this.zza == 3) {
            return zzak.zzq;
        }
        return zzak.zzl;
    }

    public final void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, PriceChangeConfirmationListener priceChangeConfirmationListener) {
        if (!isReady()) {
            zzI(zzak.zzq, priceChangeConfirmationListener);
        } else if (priceChangeFlowParams == null || priceChangeFlowParams.getSkuDetails() == null) {
            zza.zzk("BillingClient", "Please fix the input params. priceChangeFlowParams must contain valid sku.");
            zzI(zzak.zzn, priceChangeConfirmationListener);
        } else {
            String sku = priceChangeFlowParams.getSkuDetails().getSku();
            if (sku == null) {
                zza.zzk("BillingClient", "Please fix the input params. priceChangeFlowParams must contain valid sku.");
                zzI(zzak.zzn, priceChangeConfirmationListener);
            } else if (!this.zzm) {
                zza.zzk("BillingClient", "Current client doesn't support price change confirmation flow.");
                zzI(zzak.zzi, priceChangeConfirmationListener);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("playBillingLibraryVersion", this.zzb);
                bundle.putBoolean("subs_price_change", true);
                try {
                    Bundle bundle2 = (Bundle) zzH(new zzo(this, sku, bundle), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (Runnable) null, this.zzc).get(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, TimeUnit.MILLISECONDS);
                    int zza2 = zza.zza(bundle2, "BillingClient");
                    String zzh2 = zza.zzh(bundle2, "BillingClient");
                    BillingResult.Builder newBuilder = BillingResult.newBuilder();
                    newBuilder.setResponseCode(zza2);
                    newBuilder.setDebugMessage(zzh2);
                    BillingResult build = newBuilder.build();
                    if (zza2 != 0) {
                        StringBuilder sb = new StringBuilder(68);
                        sb.append("Unable to launch price change flow, error response code: ");
                        sb.append(zza2);
                        zza.zzk("BillingClient", sb.toString());
                        zzI(build, priceChangeConfirmationListener);
                        return;
                    }
                    zzy zzy = new zzy(this, this.zzc, priceChangeConfirmationListener);
                    Intent intent = new Intent(activity, ProxyBillingActivity.class);
                    intent.putExtra("SUBS_MANAGEMENT_INTENT", (PendingIntent) bundle2.getParcelable("SUBS_MANAGEMENT_INTENT"));
                    intent.putExtra("result_receiver", zzy);
                    activity.startActivity(intent);
                } catch (CancellationException | TimeoutException unused) {
                    StringBuilder sb2 = new StringBuilder(sku.length() + 70);
                    sb2.append("Time out while launching Price Change Flow for sku: ");
                    sb2.append(sku);
                    sb2.append("; try to reconnect");
                    zza.zzk("BillingClient", sb2.toString());
                    zzI(zzak.zzr, priceChangeConfirmationListener);
                } catch (Exception unused2) {
                    StringBuilder sb3 = new StringBuilder(sku.length() + 78);
                    sb3.append("Exception caught while launching Price Change Flow for sku: ");
                    sb3.append(sku);
                    sb3.append("; try to reconnect");
                    zza.zzk("BillingClient", sb3.toString());
                    zzI(zzak.zzq, priceChangeConfirmationListener);
                }
            }
        }
    }

    private BillingClientImpl(Context context, boolean z, PurchasesUpdatedListener purchasesUpdatedListener, String str, String str2) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        this.zzb = str;
        initialize(context, purchasesUpdatedListener, z);
    }

    private BillingClientImpl(String str) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        this.zzb = str;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    BillingClientImpl(java.lang.String r7, boolean r8, android.content.Context r9, com.android.billingclient.api.PurchasesUpdatedListener r10) {
        /*
            r6 = this;
            java.lang.String r7 = "com.android.billingclient.ktx.BuildConfig"
            java.lang.Class r7 = java.lang.Class.forName(r7)     // Catch:{ Exception -> 0x0014 }
            java.lang.String r0 = "VERSION_NAME"
            java.lang.reflect.Field r7 = r7.getField(r0)     // Catch:{ Exception -> 0x0014 }
            r0 = 0
            java.lang.Object r7 = r7.get(r0)     // Catch:{ Exception -> 0x0014 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0014 }
            goto L_0x0016
        L_0x0014:
            java.lang.String r7 = "4.0.0"
        L_0x0016:
            r4 = r7
            r5 = 0
            r0 = r6
            r1 = r9
            r2 = r8
            r3 = r10
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.BillingClientImpl.<init>(java.lang.String, boolean, android.content.Context, com.android.billingclient.api.PurchasesUpdatedListener):void");
    }

    public final void startConnection(BillingClientStateListener billingClientStateListener) {
        if (isReady()) {
            zza.zzj("BillingClient", "Service connection is valid. No need to re-initialize.");
            billingClientStateListener.onBillingSetupFinished(zzak.zzp);
        } else if (this.zza == 1) {
            zza.zzk("BillingClient", "Client is already in the process of connecting to billing service.");
            billingClientStateListener.onBillingSetupFinished(zzak.zzd);
        } else if (this.zza == 3) {
            zza.zzk("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            billingClientStateListener.onBillingSetupFinished(zzak.zzq);
        } else {
            this.zza = 1;
            this.zzd.zzd();
            zza.zzj("BillingClient", "Starting in-app billing setup.");
            this.zzh = new zzaf(this, billingClientStateListener, (zzy) null);
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage("com.android.vending");
            List<ResolveInfo> queryIntentServices = this.zzf.getPackageManager().queryIntentServices(intent, 0);
            if (queryIntentServices != null && !queryIntentServices.isEmpty()) {
                ResolveInfo resolveInfo = queryIntentServices.get(0);
                if (resolveInfo.serviceInfo != null) {
                    String str = resolveInfo.serviceInfo.packageName;
                    String str2 = resolveInfo.serviceInfo.name;
                    if (!"com.android.vending".equals(str) || str2 == null) {
                        zza.zzk("BillingClient", "The device doesn't have valid Play Store.");
                    } else {
                        ComponentName componentName = new ComponentName(str, str2);
                        Intent intent2 = new Intent(intent);
                        intent2.setComponent(componentName);
                        intent2.putExtra("playBillingLibraryVersion", this.zzb);
                        if (this.zzf.bindService(intent2, this.zzh, 1)) {
                            zza.zzj("BillingClient", "Service was bonded successfully.");
                            return;
                        }
                        zza.zzk("BillingClient", "Connection to Billing service is blocked.");
                    }
                }
            }
            this.zza = 0;
            zza.zzj("BillingClient", "Billing service unavailable on device.");
            billingClientStateListener.onBillingSetupFinished(zzak.zzc);
        }
    }
}
