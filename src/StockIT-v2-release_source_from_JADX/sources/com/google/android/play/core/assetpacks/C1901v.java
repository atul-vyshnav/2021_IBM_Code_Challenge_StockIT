package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1956bq;
import com.google.android.play.core.internal.C1980cn;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.google.android.play.core.assetpacks.v */
public final class C1901v implements C1980cn<Executor> {
    /* renamed from: b */
    public static Executor m577b() {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor(C1891m.f740a);
        C1956bq.m718b(newSingleThreadExecutor);
        return newSingleThreadExecutor;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        return m577b();
    }
}
