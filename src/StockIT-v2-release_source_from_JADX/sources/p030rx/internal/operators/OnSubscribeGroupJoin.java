package p030rx.internal.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.Subscription;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Func1;
import p030rx.functions.Func2;
import p030rx.observers.SerializedObserver;
import p030rx.observers.SerializedSubscriber;
import p030rx.subjects.PublishSubject;
import p030rx.subscriptions.CompositeSubscription;
import p030rx.subscriptions.RefCountSubscription;

/* renamed from: rx.internal.operators.OnSubscribeGroupJoin */
public final class OnSubscribeGroupJoin<T1, T2, D1, D2, R> implements Observable.OnSubscribe<R> {
    final Observable<T1> left;
    final Func1<? super T1, ? extends Observable<D1>> leftDuration;
    final Func2<? super T1, ? super Observable<T2>, ? extends R> resultSelector;
    final Observable<T2> right;
    final Func1<? super T2, ? extends Observable<D2>> rightDuration;

    public OnSubscribeGroupJoin(Observable<T1> observable, Observable<T2> observable2, Func1<? super T1, ? extends Observable<D1>> func1, Func1<? super T2, ? extends Observable<D2>> func12, Func2<? super T1, ? super Observable<T2>, ? extends R> func2) {
        this.left = observable;
        this.right = observable2;
        this.leftDuration = func1;
        this.rightDuration = func12;
        this.resultSelector = func2;
    }

    public void call(Subscriber<? super R> subscriber) {
        ResultManager resultManager = new ResultManager(new SerializedSubscriber(subscriber));
        subscriber.add(resultManager);
        resultManager.init();
    }

    /* renamed from: rx.internal.operators.OnSubscribeGroupJoin$ResultManager */
    final class ResultManager extends HashMap<Integer, Observer<T2>> implements Subscription {
        private static final long serialVersionUID = -3035156013812425335L;
        final RefCountSubscription cancel;
        final CompositeSubscription group;
        boolean leftDone;
        int leftIds;
        boolean rightDone;
        int rightIds;
        final Map<Integer, T2> rightMap = new HashMap();
        final Subscriber<? super R> subscriber;

        /* access modifiers changed from: package-private */
        public Map<Integer, Observer<T2>> leftMap() {
            return this;
        }

        public ResultManager(Subscriber<? super R> subscriber2) {
            this.subscriber = subscriber2;
            this.group = new CompositeSubscription();
            this.cancel = new RefCountSubscription(this.group);
        }

        public void init() {
            LeftObserver leftObserver = new LeftObserver();
            RightObserver rightObserver = new RightObserver();
            this.group.add(leftObserver);
            this.group.add(rightObserver);
            OnSubscribeGroupJoin.this.left.unsafeSubscribe(leftObserver);
            OnSubscribeGroupJoin.this.right.unsafeSubscribe(rightObserver);
        }

        public void unsubscribe() {
            this.cancel.unsubscribe();
        }

        public boolean isUnsubscribed() {
            return this.cancel.isUnsubscribed();
        }

        /* access modifiers changed from: package-private */
        public void errorAll(Throwable th) {
            ArrayList<Observer> arrayList;
            synchronized (this) {
                arrayList = new ArrayList<>(leftMap().values());
                leftMap().clear();
                this.rightMap.clear();
            }
            for (Observer onError : arrayList) {
                onError.onError(th);
            }
            this.subscriber.onError(th);
            this.cancel.unsubscribe();
        }

        /* access modifiers changed from: package-private */
        public void errorMain(Throwable th) {
            synchronized (this) {
                leftMap().clear();
                this.rightMap.clear();
            }
            this.subscriber.onError(th);
            this.cancel.unsubscribe();
        }

        /* access modifiers changed from: package-private */
        public void complete(List<Observer<T2>> list) {
            if (list != null) {
                for (Observer<T2> onCompleted : list) {
                    onCompleted.onCompleted();
                }
                this.subscriber.onCompleted();
                this.cancel.unsubscribe();
            }
        }

        /* renamed from: rx.internal.operators.OnSubscribeGroupJoin$ResultManager$LeftObserver */
        final class LeftObserver extends Subscriber<T1> {
            LeftObserver() {
            }

            public void onNext(T1 t1) {
                int i;
                ArrayList<Object> arrayList;
                try {
                    PublishSubject create = PublishSubject.create();
                    SerializedObserver serializedObserver = new SerializedObserver(create);
                    synchronized (ResultManager.this) {
                        ResultManager resultManager = ResultManager.this;
                        i = resultManager.leftIds;
                        resultManager.leftIds = i + 1;
                        ResultManager.this.leftMap().put(Integer.valueOf(i), serializedObserver);
                    }
                    Observable unsafeCreate = Observable.unsafeCreate(new WindowObservableFunc(create, ResultManager.this.cancel));
                    LeftDurationObserver leftDurationObserver = new LeftDurationObserver(i);
                    ResultManager.this.group.add(leftDurationObserver);
                    ((Observable) OnSubscribeGroupJoin.this.leftDuration.call(t1)).unsafeSubscribe(leftDurationObserver);
                    Object call = OnSubscribeGroupJoin.this.resultSelector.call(t1, unsafeCreate);
                    synchronized (ResultManager.this) {
                        arrayList = new ArrayList<>(ResultManager.this.rightMap.values());
                    }
                    ResultManager.this.subscriber.onNext(call);
                    for (Object onNext : arrayList) {
                        serializedObserver.onNext(onNext);
                    }
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer<?>) this);
                }
            }

            public void onCompleted() {
                ArrayList arrayList;
                synchronized (ResultManager.this) {
                    ResultManager.this.leftDone = true;
                    if (ResultManager.this.rightDone) {
                        arrayList = new ArrayList(ResultManager.this.leftMap().values());
                        ResultManager.this.leftMap().clear();
                        ResultManager.this.rightMap.clear();
                    } else {
                        arrayList = null;
                    }
                }
                ResultManager.this.complete(arrayList);
            }

            public void onError(Throwable th) {
                ResultManager.this.errorAll(th);
            }
        }

        /* renamed from: rx.internal.operators.OnSubscribeGroupJoin$ResultManager$RightObserver */
        final class RightObserver extends Subscriber<T2> {
            RightObserver() {
            }

            public void onNext(T2 t2) {
                int i;
                ArrayList<Observer> arrayList;
                try {
                    synchronized (ResultManager.this) {
                        ResultManager resultManager = ResultManager.this;
                        i = resultManager.rightIds;
                        resultManager.rightIds = i + 1;
                        ResultManager.this.rightMap.put(Integer.valueOf(i), t2);
                    }
                    RightDurationObserver rightDurationObserver = new RightDurationObserver(i);
                    ResultManager.this.group.add(rightDurationObserver);
                    ((Observable) OnSubscribeGroupJoin.this.rightDuration.call(t2)).unsafeSubscribe(rightDurationObserver);
                    synchronized (ResultManager.this) {
                        arrayList = new ArrayList<>(ResultManager.this.leftMap().values());
                    }
                    for (Observer onNext : arrayList) {
                        onNext.onNext(t2);
                    }
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer<?>) this);
                }
            }

            public void onCompleted() {
                ArrayList arrayList;
                synchronized (ResultManager.this) {
                    ResultManager.this.rightDone = true;
                    if (ResultManager.this.leftDone) {
                        arrayList = new ArrayList(ResultManager.this.leftMap().values());
                        ResultManager.this.leftMap().clear();
                        ResultManager.this.rightMap.clear();
                    } else {
                        arrayList = null;
                    }
                }
                ResultManager.this.complete(arrayList);
            }

            public void onError(Throwable th) {
                ResultManager.this.errorAll(th);
            }
        }

        /* renamed from: rx.internal.operators.OnSubscribeGroupJoin$ResultManager$LeftDurationObserver */
        final class LeftDurationObserver extends Subscriber<D1> {

            /* renamed from: id */
            final int f1350id;
            boolean once = true;

            public LeftDurationObserver(int i) {
                this.f1350id = i;
            }

            public void onCompleted() {
                Observer observer;
                if (this.once) {
                    this.once = false;
                    synchronized (ResultManager.this) {
                        observer = (Observer) ResultManager.this.leftMap().remove(Integer.valueOf(this.f1350id));
                    }
                    if (observer != null) {
                        observer.onCompleted();
                    }
                    ResultManager.this.group.remove(this);
                }
            }

            public void onError(Throwable th) {
                ResultManager.this.errorMain(th);
            }

            public void onNext(D1 d1) {
                onCompleted();
            }
        }

        /* renamed from: rx.internal.operators.OnSubscribeGroupJoin$ResultManager$RightDurationObserver */
        final class RightDurationObserver extends Subscriber<D2> {

            /* renamed from: id */
            final int f1351id;
            boolean once = true;

            public RightDurationObserver(int i) {
                this.f1351id = i;
            }

            public void onCompleted() {
                if (this.once) {
                    this.once = false;
                    synchronized (ResultManager.this) {
                        ResultManager.this.rightMap.remove(Integer.valueOf(this.f1351id));
                    }
                    ResultManager.this.group.remove(this);
                }
            }

            public void onError(Throwable th) {
                ResultManager.this.errorMain(th);
            }

            public void onNext(D2 d2) {
                onCompleted();
            }
        }
    }

    /* renamed from: rx.internal.operators.OnSubscribeGroupJoin$WindowObservableFunc */
    static final class WindowObservableFunc<T> implements Observable.OnSubscribe<T> {
        final RefCountSubscription refCount;
        final Observable<T> underlying;

        public WindowObservableFunc(Observable<T> observable, RefCountSubscription refCountSubscription) {
            this.refCount = refCountSubscription;
            this.underlying = observable;
        }

        public void call(Subscriber<? super T> subscriber) {
            Subscription subscription = this.refCount.get();
            WindowSubscriber windowSubscriber = new WindowSubscriber(subscriber, subscription);
            windowSubscriber.add(subscription);
            this.underlying.unsafeSubscribe(windowSubscriber);
        }

        /* renamed from: rx.internal.operators.OnSubscribeGroupJoin$WindowObservableFunc$WindowSubscriber */
        final class WindowSubscriber extends Subscriber<T> {
            private final Subscription ref;
            final Subscriber<? super T> subscriber;

            public WindowSubscriber(Subscriber<? super T> subscriber2, Subscription subscription) {
                super(subscriber2);
                this.subscriber = subscriber2;
                this.ref = subscription;
            }

            public void onNext(T t) {
                this.subscriber.onNext(t);
            }

            public void onError(Throwable th) {
                this.subscriber.onError(th);
                this.ref.unsubscribe();
            }

            public void onCompleted() {
                this.subscriber.onCompleted();
                this.ref.unsubscribe();
            }
        }
    }
}
