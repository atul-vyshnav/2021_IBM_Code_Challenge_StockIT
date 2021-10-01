package com.jakewharton.rxrelay;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.functions.Action0;
import p030rx.functions.Action1;
import p030rx.functions.Actions;
import p030rx.subscriptions.Subscriptions;

final class RelaySubscriptionManager<T> extends AtomicReference<State<T>> implements Observable.OnSubscribe<T> {
    boolean active = true;
    volatile Object latest;
    Action1<RelayObserver<T>> onAdded = Actions.empty();
    Action1<RelayObserver<T>> onStart = Actions.empty();

    RelaySubscriptionManager() {
        super(State.EMPTY);
    }

    public void call(Subscriber<? super T> subscriber) {
        RelayObserver relayObserver = new RelayObserver(subscriber);
        addUnsubscriber(subscriber, relayObserver);
        this.onStart.call(relayObserver);
        if (!subscriber.isUnsubscribed()) {
            add(relayObserver);
            if (subscriber.isUnsubscribed()) {
                remove(relayObserver);
            }
        }
    }

    private void addUnsubscriber(Subscriber<? super T> subscriber, final RelayObserver<T> relayObserver) {
        subscriber.add(Subscriptions.create(new Action0() {
            public void call() {
                RelaySubscriptionManager.this.remove(relayObserver);
            }
        }));
    }

    /* access modifiers changed from: package-private */
    public void setLatest(Object obj) {
        this.latest = obj;
    }

    /* access modifiers changed from: package-private */
    public Object getLatest() {
        return this.latest;
    }

    /* access modifiers changed from: package-private */
    public RelayObserver<T>[] observers() {
        return ((State) get()).observers;
    }

    private void add(RelayObserver<T> relayObserver) {
        State state;
        do {
            state = (State) get();
        } while (!compareAndSet(state, state.add(relayObserver)));
        this.onAdded.call(relayObserver);
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    void remove(com.jakewharton.rxrelay.RelaySubscriptionManager.RelayObserver<T> r3) {
        /*
            r2 = this;
        L_0x0000:
            java.lang.Object r0 = r2.get()
            com.jakewharton.rxrelay.RelaySubscriptionManager$State r0 = (com.jakewharton.rxrelay.RelaySubscriptionManager.State) r0
            com.jakewharton.rxrelay.RelaySubscriptionManager$State r1 = r0.remove(r3)
            if (r1 == r0) goto L_0x0012
            boolean r0 = r2.compareAndSet(r0, r1)
            if (r0 == 0) goto L_0x0000
        L_0x0012:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jakewharton.rxrelay.RelaySubscriptionManager.remove(com.jakewharton.rxrelay.RelaySubscriptionManager$RelayObserver):void");
    }

    /* access modifiers changed from: package-private */
    public RelayObserver<T>[] next(Object obj) {
        setLatest(obj);
        return ((State) get()).observers;
    }

    static final class State<T> {
        static final State EMPTY = new State(new RelayObserver[0]);
        final RelayObserver[] observers;

        State(RelayObserver[] relayObserverArr) {
            this.observers = relayObserverArr;
        }

        /* access modifiers changed from: package-private */
        public State add(RelayObserver relayObserver) {
            RelayObserver[] relayObserverArr = this.observers;
            int length = relayObserverArr.length;
            RelayObserver[] relayObserverArr2 = new RelayObserver[(length + 1)];
            System.arraycopy(relayObserverArr, 0, relayObserverArr2, 0, length);
            relayObserverArr2[length] = relayObserver;
            return new State(relayObserverArr2);
        }

        /* access modifiers changed from: package-private */
        public State remove(RelayObserver relayObserver) {
            RelayObserver[] relayObserverArr = this.observers;
            if (r1 == 1 && relayObserverArr[0] == relayObserver) {
                return EMPTY;
            }
            if (r1 == 0) {
                return this;
            }
            int i = r1 - 1;
            RelayObserver[] relayObserverArr2 = new RelayObserver[i];
            int i2 = 0;
            for (RelayObserver relayObserver2 : relayObserverArr) {
                if (relayObserver2 != relayObserver) {
                    if (i2 == i) {
                        return this;
                    }
                    relayObserverArr2[i2] = relayObserver2;
                    i2++;
                }
            }
            if (i2 == 0) {
                return EMPTY;
            }
            if (i2 < i) {
                RelayObserver[] relayObserverArr3 = new RelayObserver[i2];
                System.arraycopy(relayObserverArr2, 0, relayObserverArr3, 0, i2);
                relayObserverArr2 = relayObserverArr3;
            }
            return new State(relayObserverArr2);
        }
    }

    static final class RelayObserver<T> implements Observer<T> {
        final Observer<? super T> actual;
        protected volatile boolean caughtUp;
        boolean emitting;
        boolean fastPath;
        boolean first = true;
        private volatile Object index;
        List<Object> queue;

        RelayObserver(Observer<? super T> observer) {
            this.actual = observer;
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            throw new AssertionError();
        }

        public void onCompleted() {
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
            r1.fastPath = true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void emitNext(java.lang.Object r2) {
            /*
                r1 = this;
                boolean r0 = r1.fastPath
                if (r0 != 0) goto L_0x0026
                monitor-enter(r1)
                r0 = 0
                r1.first = r0     // Catch:{ all -> 0x0023 }
                boolean r0 = r1.emitting     // Catch:{ all -> 0x0023 }
                if (r0 == 0) goto L_0x001e
                java.util.List<java.lang.Object> r0 = r1.queue     // Catch:{ all -> 0x0023 }
                if (r0 != 0) goto L_0x0017
                java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0023 }
                r0.<init>()     // Catch:{ all -> 0x0023 }
                r1.queue = r0     // Catch:{ all -> 0x0023 }
            L_0x0017:
                java.util.List<java.lang.Object> r0 = r1.queue     // Catch:{ all -> 0x0023 }
                r0.add(r2)     // Catch:{ all -> 0x0023 }
                monitor-exit(r1)     // Catch:{ all -> 0x0023 }
                return
            L_0x001e:
                monitor-exit(r1)     // Catch:{ all -> 0x0023 }
                r0 = 1
                r1.fastPath = r0
                goto L_0x0026
            L_0x0023:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0023 }
                throw r2
            L_0x0026:
                rx.Observer<? super T> r0 = r1.actual
                com.jakewharton.rxrelay.NotificationLite.accept(r0, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jakewharton.rxrelay.RelaySubscriptionManager.RelayObserver.emitNext(java.lang.Object):void");
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0013, code lost:
            if (r2 == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0015, code lost:
            emitLoop((java.util.List<java.lang.Object>) null, r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void emitFirst(java.lang.Object r2) {
            /*
                r1 = this;
                monitor-enter(r1)
                boolean r0 = r1.first     // Catch:{ all -> 0x001c }
                if (r0 == 0) goto L_0x001a
                boolean r0 = r1.emitting     // Catch:{ all -> 0x001c }
                if (r0 == 0) goto L_0x000a
                goto L_0x001a
            L_0x000a:
                r0 = 0
                r1.first = r0     // Catch:{ all -> 0x001c }
                if (r2 == 0) goto L_0x0010
                r0 = 1
            L_0x0010:
                r1.emitting = r0     // Catch:{ all -> 0x001c }
                monitor-exit(r1)     // Catch:{ all -> 0x001c }
                if (r2 == 0) goto L_0x0019
                r0 = 0
                r1.emitLoop(r0, r2)
            L_0x0019:
                return
            L_0x001a:
                monitor-exit(r1)     // Catch:{ all -> 0x001c }
                return
            L_0x001c:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x001c }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jakewharton.rxrelay.RelaySubscriptionManager.RelayObserver.emitFirst(java.lang.Object):void");
        }

        private void emitLoop(List<Object> list, Object obj) {
            boolean z = true;
            boolean z2 = true;
            while (true) {
                if (list != null) {
                    try {
                        for (Object accept : list) {
                            accept(accept);
                        }
                    } catch (Throwable th) {
                        th = th;
                        z = false;
                        if (!z) {
                            synchronized (this) {
                                this.emitting = false;
                            }
                        }
                        throw th;
                    }
                }
                if (z2) {
                    accept(obj);
                    z2 = false;
                }
                synchronized (this) {
                    try {
                        list = this.queue;
                        this.queue = null;
                        if (list == null) {
                            this.emitting = false;
                            try {
                                return;
                            } catch (Throwable th2) {
                                th = th2;
                                try {
                                    throw th;
                                } catch (Throwable th3) {
                                    th = th3;
                                }
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        z = false;
                    }
                }
            }
        }

        private void accept(Object obj) {
            if (obj != null) {
                NotificationLite.accept(this.actual, obj);
            }
        }

        /* access modifiers changed from: package-private */
        public <I> I index() {
            return this.index;
        }

        /* access modifiers changed from: package-private */
        public void index(Object obj) {
            this.index = obj;
        }
    }
}
