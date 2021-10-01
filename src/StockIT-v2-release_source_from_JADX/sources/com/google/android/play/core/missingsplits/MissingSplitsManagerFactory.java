package com.google.android.play.core.missingsplits;

import android.content.Context;
import java.util.concurrent.atomic.AtomicReference;

public class MissingSplitsManagerFactory {

    /* renamed from: a */
    private static final AtomicReference<Boolean> f842a = new AtomicReference<>((Object) null);

    public static MissingSplitsManager create(Context context) {
        return new C2007b(context, Runtime.getRuntime(), new C2006a(context, context.getPackageManager()), f842a);
    }
}
