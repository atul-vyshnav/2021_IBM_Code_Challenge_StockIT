package com.jakewharton.rxrelay;

import com.jakewharton.rxrelay.RelaySubscriptionManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Scheduler;
import p030rx.functions.Action1;
import p030rx.functions.Func1;
import p030rx.schedulers.Timestamped;

public class ReplayRelay<T> extends Relay<T, T> {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final RelaySubscriptionManager<T> ssm;
    private final ReplayState<T> state;

    private interface EvictionPolicy {
        void evict(NodeList<Object> nodeList);

        boolean test(Object obj, long j);
    }

    private interface ReplayState<T> {
        boolean isEmpty();

        T latest();

        void next(T t);

        boolean replayObserver(RelaySubscriptionManager.RelayObserver<? super T> relayObserver);

        int size();

        T[] toArray(T[] tArr);
    }

    public static <T> ReplayRelay<T> create() {
        return create(16);
    }

    public static <T> ReplayRelay<T> create(int i) {
        final UnboundedReplayState unboundedReplayState = new UnboundedReplayState(i);
        RelaySubscriptionManager relaySubscriptionManager = new RelaySubscriptionManager();
        relaySubscriptionManager.onStart = new Action1<RelaySubscriptionManager.RelayObserver<T>>() {
            public void call(RelaySubscriptionManager.RelayObserver<T> relayObserver) {
                relayObserver.index(Integer.valueOf(unboundedReplayState.replayObserverFromIndex(0, relayObserver).intValue()));
            }
        };
        relaySubscriptionManager.onAdded = new Action1<RelaySubscriptionManager.RelayObserver<T>>() {
            /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
                r2 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
                r3 = ((java.lang.Integer) r7.index()).intValue();
                r4 = r2.get();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
                if (r3 == r4) goto L_0x002e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
                r7.index(r2.replayObserverFromIndex(java.lang.Integer.valueOf(r3), r7));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
                monitor-enter(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:17:0x0033, code lost:
                if (r4 != r2.get()) goto L_0x0039;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
                r7.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x0038, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:24:0x003b, code lost:
                r2 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:25:0x003c, code lost:
                r1 = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
                throw r2;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:30:0x003f, code lost:
                r2 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:31:0x0040, code lost:
                r5 = r2;
                r2 = r1;
                r1 = r5;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:32:0x0044, code lost:
                r2 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:33:0x0046, code lost:
                r1 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:34:0x0047, code lost:
                r2 = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:35:0x0048, code lost:
                if (r2 == false) goto L_0x004a;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:36:0x004a, code lost:
                monitor-enter(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
                r7.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:44:0x0052, code lost:
                throw r1;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void call(com.jakewharton.rxrelay.RelaySubscriptionManager.RelayObserver<T> r7) {
                /*
                    r6 = this;
                    monitor-enter(r7)
                    boolean r0 = r7.first     // Catch:{ all -> 0x0055 }
                    if (r0 == 0) goto L_0x0053
                    boolean r0 = r7.emitting     // Catch:{ all -> 0x0055 }
                    if (r0 == 0) goto L_0x000a
                    goto L_0x0053
                L_0x000a:
                    r0 = 0
                    r7.first = r0     // Catch:{ all -> 0x0055 }
                    r1 = 1
                    r7.emitting = r1     // Catch:{ all -> 0x0055 }
                    monitor-exit(r7)     // Catch:{ all -> 0x0055 }
                    com.jakewharton.rxrelay.ReplayRelay$UnboundedReplayState r2 = r0     // Catch:{ all -> 0x0046 }
                L_0x0013:
                    java.lang.Object r3 = r7.index()     // Catch:{ all -> 0x0046 }
                    java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x0046 }
                    int r3 = r3.intValue()     // Catch:{ all -> 0x0046 }
                    int r4 = r2.get()     // Catch:{ all -> 0x0046 }
                    if (r3 == r4) goto L_0x002e
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0046 }
                    java.lang.Integer r3 = r2.replayObserverFromIndex(r3, r7)     // Catch:{ all -> 0x0046 }
                    r7.index(r3)     // Catch:{ all -> 0x0046 }
                L_0x002e:
                    monitor-enter(r7)     // Catch:{ all -> 0x0046 }
                    int r3 = r2.get()     // Catch:{ all -> 0x003b }
                    if (r4 != r3) goto L_0x0039
                    r7.emitting = r0     // Catch:{ all -> 0x003b }
                    monitor-exit(r7)     // Catch:{ all -> 0x0044 }
                    return
                L_0x0039:
                    monitor-exit(r7)     // Catch:{ all -> 0x003b }
                    goto L_0x0013
                L_0x003b:
                    r2 = move-exception
                    r1 = 0
                L_0x003d:
                    monitor-exit(r7)     // Catch:{ all -> 0x0044 }
                    throw r2     // Catch:{ all -> 0x003f }
                L_0x003f:
                    r2 = move-exception
                    r5 = r2
                    r2 = r1
                    r1 = r5
                    goto L_0x0048
                L_0x0044:
                    r2 = move-exception
                    goto L_0x003d
                L_0x0046:
                    r1 = move-exception
                    r2 = 0
                L_0x0048:
                    if (r2 != 0) goto L_0x0052
                    monitor-enter(r7)
                    r7.emitting = r0     // Catch:{ all -> 0x004f }
                    monitor-exit(r7)     // Catch:{ all -> 0x004f }
                    goto L_0x0052
                L_0x004f:
                    r0 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x004f }
                    throw r0
                L_0x0052:
                    throw r1
                L_0x0053:
                    monitor-exit(r7)     // Catch:{ all -> 0x0055 }
                    return
                L_0x0055:
                    r0 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x0055 }
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.jakewharton.rxrelay.ReplayRelay.C22632.call(com.jakewharton.rxrelay.RelaySubscriptionManager$RelayObserver):void");
            }
        };
        return new ReplayRelay<>(relaySubscriptionManager, relaySubscriptionManager, unboundedReplayState);
    }

    static <T> ReplayRelay<T> createUnbounded() {
        BoundedState boundedState = new BoundedState(new EmptyEvictionPolicy(), UtilityFunctions.identity(), UtilityFunctions.identity());
        return createWithState(boundedState, new DefaultOnAdd(boundedState));
    }

    public static <T> ReplayRelay<T> createWithSize(int i) {
        BoundedState boundedState = new BoundedState(new SizeEvictionPolicy(i), UtilityFunctions.identity(), UtilityFunctions.identity());
        return createWithState(boundedState, new DefaultOnAdd(boundedState));
    }

    public static <T> ReplayRelay<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        BoundedState boundedState = new BoundedState(new TimeEvictionPolicy(timeUnit.toMillis(j), scheduler), new AddTimestamped(scheduler), new RemoveTimestamped());
        return createWithState(boundedState, new TimedOnAdd(boundedState, scheduler));
    }

    public static <T> ReplayRelay<T> createWithTimeAndSize(long j, TimeUnit timeUnit, int i, Scheduler scheduler) {
        BoundedState boundedState = new BoundedState(new PairEvictionPolicy(new SizeEvictionPolicy(i), new TimeEvictionPolicy(timeUnit.toMillis(j), scheduler)), new AddTimestamped(scheduler), new RemoveTimestamped());
        return createWithState(boundedState, new TimedOnAdd(boundedState, scheduler));
    }

    private static <T> ReplayRelay<T> createWithState(final BoundedState<T> boundedState, Action1<RelaySubscriptionManager.RelayObserver<T>> action1) {
        RelaySubscriptionManager relaySubscriptionManager = new RelaySubscriptionManager();
        relaySubscriptionManager.onStart = action1;
        relaySubscriptionManager.onAdded = new Action1<RelaySubscriptionManager.RelayObserver<T>>() {
            /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
                r2 = (com.jakewharton.rxrelay.ReplayRelay.NodeList.Node) r7.index();
                r3 = r1.tail();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
                if (r2 == r3) goto L_0x0028;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
                r7.index(r1.replayObserverFromIndex(r2, r7));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
                monitor-enter(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
                if (r3 != r1.tail()) goto L_0x0035;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
                r7.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:20:0x0034, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:23:0x0037, code lost:
                r2 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:24:0x0038, code lost:
                r1 = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
                throw r2;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:29:0x003b, code lost:
                r2 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:30:0x003c, code lost:
                r5 = r2;
                r2 = r1;
                r1 = r5;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:31:0x0040, code lost:
                r2 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:32:0x0042, code lost:
                r1 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:33:0x0043, code lost:
                r2 = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:34:0x0044, code lost:
                if (r2 == false) goto L_0x0046;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:35:0x0046, code lost:
                monitor-enter(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
                r7.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:43:0x004e, code lost:
                throw r1;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void call(com.jakewharton.rxrelay.RelaySubscriptionManager.RelayObserver<T> r7) {
                /*
                    r6 = this;
                    monitor-enter(r7)
                    boolean r0 = r7.first     // Catch:{ all -> 0x0051 }
                    if (r0 == 0) goto L_0x004f
                    boolean r0 = r7.emitting     // Catch:{ all -> 0x0051 }
                    if (r0 == 0) goto L_0x000a
                    goto L_0x004f
                L_0x000a:
                    r0 = 0
                    r7.first = r0     // Catch:{ all -> 0x0051 }
                    r1 = 1
                    r7.emitting = r1     // Catch:{ all -> 0x0051 }
                    monitor-exit(r7)     // Catch:{ all -> 0x0051 }
                L_0x0011:
                    java.lang.Object r2 = r7.index()     // Catch:{ all -> 0x0042 }
                    com.jakewharton.rxrelay.ReplayRelay$NodeList$Node r2 = (com.jakewharton.rxrelay.ReplayRelay.NodeList.Node) r2     // Catch:{ all -> 0x0042 }
                    com.jakewharton.rxrelay.ReplayRelay$BoundedState r3 = r1     // Catch:{ all -> 0x0042 }
                    com.jakewharton.rxrelay.ReplayRelay$NodeList$Node r3 = r3.tail()     // Catch:{ all -> 0x0042 }
                    if (r2 == r3) goto L_0x0028
                    com.jakewharton.rxrelay.ReplayRelay$BoundedState r4 = r1     // Catch:{ all -> 0x0042 }
                    com.jakewharton.rxrelay.ReplayRelay$NodeList$Node r2 = r4.replayObserverFromIndex(r2, r7)     // Catch:{ all -> 0x0042 }
                    r7.index(r2)     // Catch:{ all -> 0x0042 }
                L_0x0028:
                    monitor-enter(r7)     // Catch:{ all -> 0x0042 }
                    com.jakewharton.rxrelay.ReplayRelay$BoundedState r2 = r1     // Catch:{ all -> 0x0037 }
                    com.jakewharton.rxrelay.ReplayRelay$NodeList$Node r2 = r2.tail()     // Catch:{ all -> 0x0037 }
                    if (r3 != r2) goto L_0x0035
                    r7.emitting = r0     // Catch:{ all -> 0x0037 }
                    monitor-exit(r7)     // Catch:{ all -> 0x0040 }
                    return
                L_0x0035:
                    monitor-exit(r7)     // Catch:{ all -> 0x0037 }
                    goto L_0x0011
                L_0x0037:
                    r2 = move-exception
                    r1 = 0
                L_0x0039:
                    monitor-exit(r7)     // Catch:{ all -> 0x0040 }
                    throw r2     // Catch:{ all -> 0x003b }
                L_0x003b:
                    r2 = move-exception
                    r5 = r2
                    r2 = r1
                    r1 = r5
                    goto L_0x0044
                L_0x0040:
                    r2 = move-exception
                    goto L_0x0039
                L_0x0042:
                    r1 = move-exception
                    r2 = 0
                L_0x0044:
                    if (r2 != 0) goto L_0x004e
                    monitor-enter(r7)
                    r7.emitting = r0     // Catch:{ all -> 0x004b }
                    monitor-exit(r7)     // Catch:{ all -> 0x004b }
                    goto L_0x004e
                L_0x004b:
                    r0 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x004b }
                    throw r0
                L_0x004e:
                    throw r1
                L_0x004f:
                    monitor-exit(r7)     // Catch:{ all -> 0x0051 }
                    return
                L_0x0051:
                    r0 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x0051 }
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.jakewharton.rxrelay.ReplayRelay.C22643.call(com.jakewharton.rxrelay.RelaySubscriptionManager$RelayObserver):void");
            }
        };
        return new ReplayRelay<>(relaySubscriptionManager, relaySubscriptionManager, boundedState);
    }

    ReplayRelay(Observable.OnSubscribe<T> onSubscribe, RelaySubscriptionManager<T> relaySubscriptionManager, ReplayState<T> replayState) {
        super(onSubscribe);
        this.ssm = relaySubscriptionManager;
        this.state = replayState;
    }

    public void call(T t) {
        if (this.ssm.active) {
            this.state.next(t);
            for (RelaySubscriptionManager.RelayObserver relayObserver : this.ssm.observers()) {
                if (caughtUp(relayObserver)) {
                    relayObserver.onNext(t);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int subscriberCount() {
        return ((RelaySubscriptionManager.State) this.ssm.get()).observers.length;
    }

    public boolean hasObservers() {
        return this.ssm.observers().length > 0;
    }

    private boolean caughtUp(RelaySubscriptionManager.RelayObserver<? super T> relayObserver) {
        if (relayObserver.caughtUp) {
            return true;
        }
        if (!this.state.replayObserver(relayObserver)) {
            return false;
        }
        relayObserver.caughtUp = true;
        relayObserver.index((Object) null);
        return false;
    }

    private static final class UnboundedReplayState<T> extends AtomicInteger implements ReplayState<T> {
        private final ArrayList<Object> list;

        UnboundedReplayState(int i) {
            this.list = new ArrayList<>(i);
        }

        public void next(T t) {
            this.list.add(NotificationLite.next(t));
            getAndIncrement();
        }

        private void accept(Observer<? super T> observer, int i) {
            NotificationLite.accept(observer, this.list.get(i));
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
            r4.index(java.lang.Integer.valueOf(replayObserverFromIndex(r0, r4).intValue()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
            throw new java.lang.IllegalStateException("failed to find lastEmittedLink for: " + r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x000b, code lost:
            r0 = (java.lang.Integer) r4.index();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
            if (r0 == null) goto L_0x0024;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean replayObserver(com.jakewharton.rxrelay.RelaySubscriptionManager.RelayObserver<? super T> r4) {
            /*
                r3 = this;
                monitor-enter(r4)
                r0 = 0
                r4.first = r0     // Catch:{ all -> 0x003b }
                boolean r1 = r4.emitting     // Catch:{ all -> 0x003b }
                if (r1 == 0) goto L_0x000a
                monitor-exit(r4)     // Catch:{ all -> 0x003b }
                return r0
            L_0x000a:
                monitor-exit(r4)     // Catch:{ all -> 0x003b }
                java.lang.Object r0 = r4.index()
                java.lang.Integer r0 = (java.lang.Integer) r0
                if (r0 == 0) goto L_0x0024
                java.lang.Integer r0 = r3.replayObserverFromIndex(r0, r4)
                int r0 = r0.intValue()
                java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
                r4.index(r0)
                r4 = 1
                return r4
            L_0x0024:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "failed to find lastEmittedLink for: "
                r1.append(r2)
                r1.append(r4)
                java.lang.String r4 = r1.toString()
                r0.<init>(r4)
                throw r0
            L_0x003b:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x003b }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jakewharton.rxrelay.ReplayRelay.UnboundedReplayState.replayObserver(com.jakewharton.rxrelay.RelaySubscriptionManager$RelayObserver):boolean");
        }

        /* access modifiers changed from: package-private */
        public Integer replayObserverFromIndex(Integer num, RelaySubscriptionManager.RelayObserver<? super T> relayObserver) {
            int intValue = num.intValue();
            while (intValue < get()) {
                accept(relayObserver, intValue);
                intValue++;
            }
            return Integer.valueOf(intValue);
        }

        public int size() {
            return get();
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public T[] toArray(T[] tArr) {
            int size = size();
            if (size > 0) {
                if (size > tArr.length) {
                    tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
                }
                for (int i = 0; i < size; i++) {
                    tArr[i] = this.list.get(i);
                }
                if (tArr.length > size) {
                    tArr[size] = null;
                }
            } else if (tArr.length > 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        public T latest() {
            int i = get();
            if (i > 0) {
                return NotificationLite.getValue(this.list.get(i - 1));
            }
            return null;
        }
    }

    private static final class BoundedState<T> implements ReplayState<T> {
        final Func1<Object, Object> enterTransform;
        final EvictionPolicy evictionPolicy;
        final Func1<Object, Object> leaveTransform;
        final NodeList<Object> list;
        volatile NodeList.Node<Object> tail;
        volatile boolean terminated;

        BoundedState(EvictionPolicy evictionPolicy2, Func1<Object, Object> func1, Func1<Object, Object> func12) {
            NodeList<Object> nodeList = new NodeList<>();
            this.list = nodeList;
            this.tail = nodeList.tail;
            this.evictionPolicy = evictionPolicy2;
            this.enterTransform = func1;
            this.leaveTransform = func12;
        }

        public void next(T t) {
            if (!this.terminated) {
                this.list.addLast(this.enterTransform.call(NotificationLite.next(t)));
                this.evictionPolicy.evict(this.list);
                this.tail = this.list.tail;
            }
        }

        private void accept(Observer<? super T> observer, NodeList.Node<Object> node) {
            NotificationLite.accept(observer, this.leaveTransform.call(node.value));
        }

        private void acceptTest(Observer<? super T> observer, NodeList.Node<Object> node, long j) {
            T t = node.value;
            if (!this.evictionPolicy.test(t, j)) {
                NotificationLite.accept(observer, this.leaveTransform.call(t));
            }
        }

        /* access modifiers changed from: package-private */
        public NodeList.Node<Object> head() {
            return this.list.head;
        }

        /* access modifiers changed from: package-private */
        public NodeList.Node<Object> tail() {
            return this.tail;
        }

        public boolean replayObserver(RelaySubscriptionManager.RelayObserver<? super T> relayObserver) {
            synchronized (relayObserver) {
                relayObserver.first = false;
                if (relayObserver.emitting) {
                    return false;
                }
                relayObserver.index(replayObserverFromIndex((NodeList.Node) relayObserver.index(), relayObserver));
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public NodeList.Node<Object> replayObserverFromIndex(NodeList.Node<Object> node, RelaySubscriptionManager.RelayObserver<? super T> relayObserver) {
            while (true) {
                NodeList.Node<T> node2 = node;
                if (node2 == tail()) {
                    return node2;
                }
                accept(relayObserver, node2.next);
                node2 = node2.next;
            }
        }

        /* access modifiers changed from: package-private */
        public NodeList.Node<Object> replayObserverFromIndexTest(NodeList.Node<Object> node, RelaySubscriptionManager.RelayObserver<? super T> relayObserver, long j) {
            while (true) {
                NodeList.Node<T> node2 = node;
                if (node2 == tail()) {
                    return node2;
                }
                acceptTest(relayObserver, node2.next, j);
                node2 = node2.next;
            }
        }

        public int size() {
            int i = 0;
            for (NodeList.Node<T> node = head().next; node != null; node = node.next) {
                i++;
            }
            return i;
        }

        public boolean isEmpty() {
            return head().next == null;
        }

        public T[] toArray(T[] tArr) {
            ArrayList arrayList = new ArrayList();
            for (NodeList.Node<T> node = head().next; node != null; node = node.next) {
                arrayList.add(this.leaveTransform.call(node.value));
            }
            return arrayList.toArray(tArr);
        }

        public T latest() {
            NodeList.Node<T> node = head().next;
            if (node == null) {
                return null;
            }
            while (node != tail()) {
                node = node.next;
            }
            return NotificationLite.getValue(this.leaveTransform.call(node.value));
        }
    }

    private static final class SizeEvictionPolicy implements EvictionPolicy {
        private final int maxSize;

        public boolean test(Object obj, long j) {
            return false;
        }

        SizeEvictionPolicy(int i) {
            this.maxSize = i;
        }

        public void evict(NodeList<Object> nodeList) {
            while (nodeList.size() > this.maxSize) {
                nodeList.removeFirst();
            }
        }
    }

    private static final class TimeEvictionPolicy implements EvictionPolicy {
        private final long maxAgeMillis;
        private final Scheduler scheduler;

        TimeEvictionPolicy(long j, Scheduler scheduler2) {
            this.maxAgeMillis = j;
            this.scheduler = scheduler2;
        }

        public void evict(NodeList<Object> nodeList) {
            long now = this.scheduler.now();
            while (!nodeList.isEmpty() && test(nodeList.head.next.value, now)) {
                nodeList.removeFirst();
            }
        }

        public boolean test(Object obj, long j) {
            return ((Timestamped) obj).getTimestampMillis() <= j - this.maxAgeMillis;
        }
    }

    private static final class PairEvictionPolicy implements EvictionPolicy {
        private final EvictionPolicy first;
        private final EvictionPolicy second;

        PairEvictionPolicy(EvictionPolicy evictionPolicy, EvictionPolicy evictionPolicy2) {
            this.first = evictionPolicy;
            this.second = evictionPolicy2;
        }

        public void evict(NodeList<Object> nodeList) {
            this.first.evict(nodeList);
            this.second.evict(nodeList);
        }

        public boolean test(Object obj, long j) {
            return this.first.test(obj, j) || this.second.test(obj, j);
        }
    }

    private static final class AddTimestamped implements Func1<Object, Object> {
        private final Scheduler scheduler;

        AddTimestamped(Scheduler scheduler2) {
            this.scheduler = scheduler2;
        }

        public Object call(Object obj) {
            return new Timestamped(this.scheduler.now(), obj);
        }
    }

    static final class RemoveTimestamped implements Func1<Object, Object> {
        RemoveTimestamped() {
        }

        public Object call(Object obj) {
            return ((Timestamped) obj).getValue();
        }
    }

    private static final class DefaultOnAdd<T> implements Action1<RelaySubscriptionManager.RelayObserver<T>> {
        private final BoundedState<T> state;

        DefaultOnAdd(BoundedState<T> boundedState) {
            this.state = boundedState;
        }

        public void call(RelaySubscriptionManager.RelayObserver<T> relayObserver) {
            BoundedState<T> boundedState = this.state;
            relayObserver.index(boundedState.replayObserverFromIndex(boundedState.head(), relayObserver));
        }
    }

    private static final class TimedOnAdd<T> implements Action1<RelaySubscriptionManager.RelayObserver<T>> {
        private final Scheduler scheduler;
        private final BoundedState<T> state;

        TimedOnAdd(BoundedState<T> boundedState, Scheduler scheduler2) {
            this.state = boundedState;
            this.scheduler = scheduler2;
        }

        public void call(RelaySubscriptionManager.RelayObserver<T> relayObserver) {
            NodeList.Node<Object> node;
            if (!this.state.terminated) {
                BoundedState<T> boundedState = this.state;
                node = boundedState.replayObserverFromIndexTest(boundedState.head(), relayObserver, this.scheduler.now());
            } else {
                BoundedState<T> boundedState2 = this.state;
                node = boundedState2.replayObserverFromIndex(boundedState2.head(), relayObserver);
            }
            relayObserver.index(node);
        }
    }

    static final class NodeList<T> {
        final Node<T> head;
        int size;
        Node<T> tail;

        NodeList() {
            Node<T> node = new Node<>(null);
            this.head = node;
            this.tail = node;
        }

        static final class Node<T> {
            volatile Node<T> next;
            final T value;

            Node(T t) {
                this.value = t;
            }
        }

        /* access modifiers changed from: package-private */
        public void addLast(T t) {
            Node<T> node = this.tail;
            Node<T> node2 = new Node<>(t);
            node.next = node2;
            this.tail = node2;
            this.size++;
        }

        /* access modifiers changed from: package-private */
        public T removeFirst() {
            if (this.head.next != null) {
                Node<T> node = this.head.next;
                this.head.next = node.next;
                if (this.head.next == null) {
                    this.tail = this.head;
                }
                this.size--;
                return node.value;
            }
            throw new IllegalStateException("Empty!");
        }

        /* access modifiers changed from: package-private */
        public boolean isEmpty() {
            return this.size == 0;
        }

        /* access modifiers changed from: package-private */
        public int size() {
            return this.size;
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this.tail = this.head;
            this.size = 0;
        }
    }

    private static final class EmptyEvictionPolicy implements EvictionPolicy {
        public void evict(NodeList<Object> nodeList) {
        }

        public boolean test(Object obj, long j) {
            return true;
        }

        EmptyEvictionPolicy() {
        }
    }

    public int size() {
        return this.state.size();
    }

    public boolean hasAnyValue() {
        return !this.state.isEmpty();
    }

    public boolean hasValue() {
        return hasAnyValue();
    }

    public T[] getValues(T[] tArr) {
        return this.state.toArray(tArr);
    }

    public Object[] getValues() {
        Object[] values = getValues(EMPTY_ARRAY);
        return values == EMPTY_ARRAY ? new Object[0] : values;
    }

    public T getValue() {
        return this.state.latest();
    }
}
