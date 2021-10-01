package com.android.billingclient.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.ResultReceiver;
import com.google.android.gms.internal.play_billing.zza;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public class ProxyBillingActivity extends Activity {
    private ResultReceiver zza;
    private boolean zzb;

    private final Intent zza() {
        Intent intent = new Intent("com.android.vending.billing.PURCHASES_UPDATED");
        intent.setPackage(getApplicationContext().getPackageName());
        return intent;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0049  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r5, int r6, android.content.Intent r7) {
        /*
            r4 = this;
            super.onActivityResult(r5, r6, r7)
            r0 = 0
            java.lang.String r1 = "ProxyBillingActivity"
            r2 = 100
            if (r5 != r2) goto L_0x0073
            com.android.billingclient.api.BillingResult r5 = com.google.android.gms.internal.play_billing.zza.zzg(r7, r1)
            int r5 = r5.getResponseCode()
            r2 = -1
            if (r6 != r2) goto L_0x001b
            if (r5 == 0) goto L_0x0019
            r6 = -1
            goto L_0x001b
        L_0x0019:
            r5 = 0
            goto L_0x0039
        L_0x001b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 85
            r2.<init>(r3)
            java.lang.String r3 = "Activity finished with resultCode "
            r2.append(r3)
            r2.append(r6)
            java.lang.String r6 = " and billing's responseCode: "
            r2.append(r6)
            r2.append(r5)
            java.lang.String r6 = r2.toString()
            com.google.android.gms.internal.play_billing.zza.zzk(r1, r6)
        L_0x0039:
            android.os.ResultReceiver r6 = r4.zza
            if (r6 == 0) goto L_0x0049
            if (r7 != 0) goto L_0x0041
            r7 = 0
            goto L_0x0045
        L_0x0041:
            android.os.Bundle r7 = r7.getExtras()
        L_0x0045:
            r6.send(r5, r7)
            goto L_0x008e
        L_0x0049:
            android.content.Intent r5 = r4.zza()
            if (r7 == 0) goto L_0x006f
            android.os.Bundle r6 = r7.getExtras()
            if (r6 == 0) goto L_0x005d
            android.os.Bundle r6 = r7.getExtras()
            r5.putExtras(r6)
            goto L_0x006f
        L_0x005d:
            java.lang.String r6 = "Got null bundle!"
            com.google.android.gms.internal.play_billing.zza.zzk(r1, r6)
            r6 = 6
            java.lang.String r7 = "RESPONSE_CODE"
            r5.putExtra(r7, r6)
            java.lang.String r6 = "DEBUG_MESSAGE"
            java.lang.String r7 = "An internal error occurred."
            r5.putExtra(r6, r7)
        L_0x006f:
            r4.sendBroadcast(r5)
            goto L_0x008e
        L_0x0073:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r7 = 69
            r6.<init>(r7)
            java.lang.String r7 = "Got onActivityResult with wrong requestCode: "
            r6.append(r7)
            r6.append(r5)
            java.lang.String r5 = "; skipping..."
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            com.google.android.gms.internal.play_billing.zza.zzk(r1, r5)
        L_0x008e:
            r4.zzb = r0
            r4.finish()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.ProxyBillingActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        PendingIntent pendingIntent;
        super.onCreate(bundle);
        if (bundle == null) {
            zza.zzj("ProxyBillingActivity", "Launching Play Store billing flow");
            if (getIntent().hasExtra("BUY_INTENT")) {
                pendingIntent = (PendingIntent) getIntent().getParcelableExtra("BUY_INTENT");
            } else if (getIntent().hasExtra("SUBS_MANAGEMENT_INTENT")) {
                pendingIntent = (PendingIntent) getIntent().getParcelableExtra("SUBS_MANAGEMENT_INTENT");
                this.zza = (ResultReceiver) getIntent().getParcelableExtra("result_receiver");
            } else {
                pendingIntent = null;
            }
            try {
                this.zzb = true;
                startIntentSenderForResult(pendingIntent.getIntentSender(), 100, new Intent(), 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 53);
                sb.append("Got exception while trying to start a purchase flow: ");
                sb.append(valueOf);
                zza.zzk("ProxyBillingActivity", sb.toString());
                ResultReceiver resultReceiver = this.zza;
                if (resultReceiver != null) {
                    resultReceiver.send(6, (Bundle) null);
                } else {
                    Intent zza2 = zza();
                    zza2.putExtra("RESPONSE_CODE", 6);
                    zza2.putExtra("DEBUG_MESSAGE", "An internal error occurred.");
                    sendBroadcast(zza2);
                }
                this.zzb = false;
                finish();
            }
        } else {
            zza.zzj("ProxyBillingActivity", "Launching Play Store billing flow from savedInstanceState");
            this.zzb = bundle.getBoolean("send_cancelled_broadcast_if_finished", false);
            if (bundle.containsKey("result_receiver")) {
                this.zza = (ResultReceiver) bundle.getParcelable("result_receiver");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (isFinishing() && this.zzb) {
            Intent zza2 = zza();
            zza2.putExtra("RESPONSE_CODE", 1);
            zza2.putExtra("DEBUG_MESSAGE", "Billing dialog closed.");
            sendBroadcast(zza2);
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        ResultReceiver resultReceiver = this.zza;
        if (resultReceiver != null) {
            bundle.putParcelable("result_receiver", resultReceiver);
        }
        bundle.putBoolean("send_cancelled_broadcast_if_finished", this.zzb);
    }
}
