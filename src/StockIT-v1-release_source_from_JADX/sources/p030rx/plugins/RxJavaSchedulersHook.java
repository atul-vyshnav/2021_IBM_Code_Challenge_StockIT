package p030rx.plugins;

import java.util.concurrent.ThreadFactory;
import p030rx.Scheduler;
import p030rx.functions.Action0;
import p030rx.internal.schedulers.CachedThreadScheduler;
import p030rx.internal.schedulers.EventLoopsScheduler;
import p030rx.internal.schedulers.NewThreadScheduler;
import p030rx.internal.util.RxThreadFactory;

/* renamed from: rx.plugins.RxJavaSchedulersHook */
public class RxJavaSchedulersHook {
    private static final RxJavaSchedulersHook DEFAULT_INSTANCE = new RxJavaSchedulersHook();

    public Scheduler getComputationScheduler() {
        return null;
    }

    public Scheduler getIOScheduler() {
        return null;
    }

    public Scheduler getNewThreadScheduler() {
        return null;
    }

    @Deprecated
    public Action0 onSchedule(Action0 action0) {
        return action0;
    }

    public static Scheduler createComputationScheduler() {
        return createComputationScheduler(new RxThreadFactory("RxComputationScheduler-"));
    }

    public static Scheduler createComputationScheduler(ThreadFactory threadFactory) {
        if (threadFactory != null) {
            return new EventLoopsScheduler(threadFactory);
        }
        throw new NullPointerException("threadFactory == null");
    }

    public static Scheduler createIoScheduler() {
        return createIoScheduler(new RxThreadFactory("RxIoScheduler-"));
    }

    public static Scheduler createIoScheduler(ThreadFactory threadFactory) {
        if (threadFactory != null) {
            return new CachedThreadScheduler(threadFactory);
        }
        throw new NullPointerException("threadFactory == null");
    }

    public static Scheduler createNewThreadScheduler() {
        return createNewThreadScheduler(new RxThreadFactory("RxNewThreadScheduler-"));
    }

    public static Scheduler createNewThreadScheduler(ThreadFactory threadFactory) {
        if (threadFactory != null) {
            return new NewThreadScheduler(threadFactory);
        }
        throw new NullPointerException("threadFactory == null");
    }

    public static RxJavaSchedulersHook getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }
}
