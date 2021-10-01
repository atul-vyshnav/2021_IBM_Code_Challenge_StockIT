package p030rx.internal.operators;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.functions.Action0;
import p030rx.subjects.Subject;
import p030rx.subscriptions.Subscriptions;

/* renamed from: rx.internal.operators.BufferUntilSubscriber */
public final class BufferUntilSubscriber<T> extends Subject<T, T> {
    static final Observer EMPTY_OBSERVER = new Observer() {
        public void onCompleted() {
        }

        public void onError(Throwable th) {
        }

        public void onNext(Object obj) {
        }
    };
    private boolean forward;
    final State<T> state;

    public static <T> BufferUntilSubscriber<T> create() {
        return new BufferUntilSubscriber<>(new State());
    }

    /* renamed from: rx.internal.operators.BufferUntilSubscriber$State */
    static final class State<T> extends AtomicReference<Observer<? super T>> {
        private static final long serialVersionUID = 8026705089538090368L;
        final ConcurrentLinkedQueue<Object> buffer = new ConcurrentLinkedQueue<>();
        boolean emitting;
        final Object guard = new Object();

        State() {
        }

        /* access modifiers changed from: package-private */
        public boolean casObserverRef(Observer<? super T> observer, Observer<? super T> observer2) {
            return compareAndSet(observer, observer2);
        }
    }

    /* renamed from: rx.internal.operators.BufferUntilSubscriber$OnSubscribeAction */
    static final class OnSubscribeAction<T> implements Observable.OnSubscribe<T> {
        final State<T> state;

        public OnSubscribeAction(State<T> state2) {
            this.state = state2;
        }

        public void call(Subscriber<? super T> subscriber) {
            boolean z;
            if (this.state.casObserverRef((Observer) null, subscriber)) {
                subscriber.add(Subscriptions.create(new Action0() {
                    public void call() {
                        OnSubscribeAction.this.state.set(BufferUntilSubscriber.EMPTY_OBSERVER);
                    }
                }));
                synchronized (this.state.guard) {
                    z = true;
                    if (!this.state.emitting) {
                        this.state.emitting = true;
                    } else {
                        z = false;
                    }
                }
                if (z) {
                    while (true) {
                        Object poll = this.state.buffer.poll();
                        if (poll != null) {
                            NotificationLite.accept((Observer) this.state.get(), poll);
                        } else {
                            synchronized (this.state.guard) {
                                if (this.state.buffer.isEmpty()) {
                                    this.state.emitting = false;
                                    return;
                                }
                            }
                        }
                    }
                }
            } else {
                subscriber.onError(new IllegalStateException("Only one subscriber allowed!"));
            }
        }
    }

    private BufferUntilSubscriber(State<T> state2) {
        super(new OnSubscribeAction(state2));
        this.state = state2;
    }

    private void emit(Object obj) {
        synchronized (this.state.guard) {
            this.state.buffer.add(obj);
            if (this.state.get() != null && !this.state.emitting) {
                this.forward = true;
                this.state.emitting = true;
            }
        }
        if (this.forward) {
            while (true) {
                Object poll = this.state.buffer.poll();
                if (poll != null) {
                    NotificationLite.accept((Observer) this.state.get(), poll);
                } else {
                    return;
                }
            }
        }
    }

    public void onCompleted() {
        if (this.forward) {
            ((Observer) this.state.get()).onCompleted();
        } else {
            emit(NotificationLite.completed());
        }
    }

    public void onError(Throwable th) {
        if (this.forward) {
            ((Observer) this.state.get()).onError(th);
        } else {
            emit(NotificationLite.error(th));
        }
    }

    public void onNext(T t) {
        if (this.forward) {
            ((Observer) this.state.get()).onNext(t);
        } else {
            emit(NotificationLite.next(t));
        }
    }

    public boolean hasObservers() {
        boolean z;
        synchronized (this.state.guard) {
            z = this.state.get() != null;
        }
        return z;
    }
}
