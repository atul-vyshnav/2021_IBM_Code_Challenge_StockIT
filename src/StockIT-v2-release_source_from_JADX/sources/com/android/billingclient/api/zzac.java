package com.android.billingclient.api;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzac implements ThreadFactory {
    private final ThreadFactory zza = Executors.defaultThreadFactory();
    private final AtomicInteger zzb = new AtomicInteger(1);

    zzac(BillingClientImpl billingClientImpl) {
    }

    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.zza.newThread(runnable);
        int andIncrement = this.zzb.getAndIncrement();
        StringBuilder sb = new StringBuilder(30);
        sb.append("PlayBillingLibrary-");
        sb.append(andIncrement);
        newThread.setName(sb.toString());
        return newThread;
    }
}
