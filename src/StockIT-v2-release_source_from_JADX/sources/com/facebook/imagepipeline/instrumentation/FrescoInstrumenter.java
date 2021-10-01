package com.facebook.imagepipeline.instrumentation;

public final class FrescoInstrumenter {
    private static volatile Instrumenter sInstance;

    public interface Instrumenter {
        Runnable decorateRunnable(Runnable runnable, String str);

        boolean isTracing();

        Object onBeforeSubmitWork(String str);

        Object onBeginWork(Object obj, String str);

        void onEndWork(Object obj);
    }

    public static void provide(Instrumenter instrumenter) {
        sInstance = instrumenter;
    }

    public static boolean isTracing() {
        Instrumenter instrumenter = sInstance;
        if (instrumenter == null) {
            return false;
        }
        return instrumenter.isTracing();
    }

    public static Object onBeforeSubmitWork(String str) {
        Instrumenter instrumenter = sInstance;
        if (instrumenter == null || str == null) {
            return null;
        }
        return instrumenter.onBeforeSubmitWork(str);
    }

    public static Object onBeginWork(Object obj, String str) {
        Instrumenter instrumenter = sInstance;
        if (instrumenter == null || obj == null) {
            return null;
        }
        return instrumenter.onBeginWork(obj, str);
    }

    public static void onEndWork(Object obj) {
        Instrumenter instrumenter = sInstance;
        if (instrumenter != null && obj != null) {
            instrumenter.onEndWork(obj);
        }
    }

    public static Runnable decorateRunnable(Runnable runnable, String str) {
        Instrumenter instrumenter = sInstance;
        return (instrumenter == null || runnable == null || str == null) ? runnable : instrumenter.decorateRunnable(runnable, str);
    }
}
