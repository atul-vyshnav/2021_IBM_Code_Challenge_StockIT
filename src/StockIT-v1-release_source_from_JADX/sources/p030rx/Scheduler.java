package p030rx;

import java.util.concurrent.TimeUnit;
import p030rx.functions.Action0;
import p030rx.functions.Func1;
import p030rx.internal.schedulers.SchedulePeriodicHelper;
import p030rx.internal.schedulers.SchedulerWhen;

/* renamed from: rx.Scheduler */
public abstract class Scheduler {
    public abstract Worker createWorker();

    /* renamed from: rx.Scheduler$Worker */
    public static abstract class Worker implements Subscription {
        public abstract Subscription schedule(Action0 action0);

        public abstract Subscription schedule(Action0 action0, long j, TimeUnit timeUnit);

        public Subscription schedulePeriodically(Action0 action0, long j, long j2, TimeUnit timeUnit) {
            return SchedulePeriodicHelper.schedulePeriodically(this, action0, j, j2, timeUnit, (SchedulePeriodicHelper.NowNanoSupplier) null);
        }

        public long now() {
            return System.currentTimeMillis();
        }
    }

    public long now() {
        return System.currentTimeMillis();
    }

    public <S extends Scheduler & Subscription> S when(Func1<Observable<Observable<Completable>>, Completable> func1) {
        return new SchedulerWhen(func1, this);
    }
}
