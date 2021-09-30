package com.google.android.gms.internal.p015authapi;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

/* renamed from: com.google.android.gms.internal.auth-api.zzo */
final class zzo extends zzg {
    private BaseImplementation.ResultHolder<Status> zzap;

    zzo(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.zzap = resultHolder;
    }

    public final void zzc(Status status) {
        this.zzap.setResult(status);
    }
}
