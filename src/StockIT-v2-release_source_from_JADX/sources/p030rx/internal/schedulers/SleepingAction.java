package p030rx.internal.schedulers;

import p030rx.Scheduler;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Action0;

/* renamed from: rx.internal.schedulers.SleepingAction */
class SleepingAction implements Action0 {
    private final long execTime;
    private final Scheduler.Worker innerScheduler;
    private final Action0 underlying;

    public SleepingAction(Action0 action0, Scheduler.Worker worker, long j) {
        this.underlying = action0;
        this.innerScheduler = worker;
        this.execTime = j;
    }

    public void call() {
        if (!this.innerScheduler.isUnsubscribed()) {
            long now = this.execTime - this.innerScheduler.now();
            if (now > 0) {
                try {
                    Thread.sleep(now);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Exceptions.propagate(e);
                }
            }
            if (!this.innerScheduler.isUnsubscribed()) {
                this.underlying.call();
            }
        }
    }
}
