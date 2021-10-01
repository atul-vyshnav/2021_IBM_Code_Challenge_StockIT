package p030rx.internal.schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import p030rx.functions.Func0;
import p030rx.internal.util.RxThreadFactory;
import p030rx.plugins.RxJavaHooks;

/* renamed from: rx.internal.schedulers.GenericScheduledExecutorServiceFactory */
enum GenericScheduledExecutorServiceFactory {
    ;
    
    static final RxThreadFactory THREAD_FACTORY = null;
    static final String THREAD_NAME_PREFIX = "RxScheduledExecutorPool-";

    static {
        THREAD_FACTORY = new RxThreadFactory(THREAD_NAME_PREFIX);
    }

    static ThreadFactory threadFactory() {
        return THREAD_FACTORY;
    }

    public static ScheduledExecutorService create() {
        Func0<? extends ScheduledExecutorService> onGenericScheduledExecutorService = RxJavaHooks.getOnGenericScheduledExecutorService();
        if (onGenericScheduledExecutorService == null) {
            return createDefault();
        }
        return (ScheduledExecutorService) onGenericScheduledExecutorService.call();
    }

    static ScheduledExecutorService createDefault() {
        return Executors.newScheduledThreadPool(1, threadFactory());
    }
}
