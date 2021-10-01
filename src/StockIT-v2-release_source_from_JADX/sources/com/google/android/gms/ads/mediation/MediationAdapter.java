package com.google.android.gms.ads.mediation;

import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-ads-lite@@19.4.0 */
public interface MediationAdapter extends MediationExtrasReceiver {

    /* compiled from: com.google.android.gms:play-services-ads-lite@@19.4.0 */
    public static class zza {
        private int zzere;

        public final zza zzdv(int i) {
            this.zzere = 1;
            return this;
        }

        public final Bundle zzadg() {
            Bundle bundle = new Bundle();
            bundle.putInt("capabilities", this.zzere);
            return bundle;
        }
    }

    void onDestroy();

    void onPause();

    void onResume();
}
