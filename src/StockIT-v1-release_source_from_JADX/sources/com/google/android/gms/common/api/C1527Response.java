package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Result;

/* renamed from: com.google.android.gms.common.api.Response */
/* compiled from: com.google.android.gms:play-services-basement@@17.3.0 */
public class C1527Response<T extends Result> {
    private T zza;

    public C1527Response() {
    }

    protected C1527Response(T t) {
        this.zza = t;
    }

    /* access modifiers changed from: protected */
    public T getResult() {
        return this.zza;
    }

    public void setResult(T t) {
        this.zza = t;
    }
}
