package com.google.android.play.core.splitcompat;

import java.util.concurrent.ThreadFactory;

/* renamed from: com.google.android.play.core.splitcompat.b */
final class C2018b implements ThreadFactory {
    C2018b() {
    }

    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, "SplitCompatBackgroundThread");
    }
}
