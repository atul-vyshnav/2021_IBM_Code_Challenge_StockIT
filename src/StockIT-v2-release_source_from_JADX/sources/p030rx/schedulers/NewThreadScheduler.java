package p030rx.schedulers;

import p030rx.Scheduler;

@Deprecated
/* renamed from: rx.schedulers.NewThreadScheduler */
public final class NewThreadScheduler extends Scheduler {
    public Scheduler.Worker createWorker() {
        return null;
    }

    private NewThreadScheduler() {
        throw new IllegalStateException("No instances!");
    }
}
