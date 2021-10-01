package p030rx.internal.operators;

import java.util.HashMap;
import java.util.Map;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Func0;
import p030rx.functions.Func1;

/* renamed from: rx.internal.operators.OnSubscribeToMap */
public final class OnSubscribeToMap<T, K, V> implements Observable.OnSubscribe<Map<K, V>>, Func0<Map<K, V>> {
    final Func1<? super T, ? extends K> keySelector;
    final Func0<? extends Map<K, V>> mapFactory;
    final Observable<T> source;
    final Func1<? super T, ? extends V> valueSelector;

    public OnSubscribeToMap(Observable<T> observable, Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12) {
        this(observable, func1, func12, (Func0) null);
    }

    public OnSubscribeToMap(Observable<T> observable, Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12, Func0<? extends Map<K, V>> func0) {
        this.source = observable;
        this.keySelector = func1;
        this.valueSelector = func12;
        if (func0 == null) {
            this.mapFactory = this;
        } else {
            this.mapFactory = func0;
        }
    }

    public Map<K, V> call() {
        return new HashMap();
    }

    public void call(Subscriber<? super Map<K, V>> subscriber) {
        try {
            new ToMapSubscriber(subscriber, (Map) this.mapFactory.call(), this.keySelector, this.valueSelector).subscribeTo(this.source);
        } catch (Throwable th) {
            Exceptions.throwOrReport(th, (Observer<?>) subscriber);
        }
    }

    /* renamed from: rx.internal.operators.OnSubscribeToMap$ToMapSubscriber */
    static final class ToMapSubscriber<T, K, V> extends DeferredScalarSubscriberSafe<T, Map<K, V>> {
        final Func1<? super T, ? extends K> keySelector;
        final Func1<? super T, ? extends V> valueSelector;

        ToMapSubscriber(Subscriber<? super Map<K, V>> subscriber, Map<K, V> map, Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12) {
            super(subscriber);
            this.value = map;
            this.hasValue = true;
            this.keySelector = func1;
            this.valueSelector = func12;
        }

        public void onStart() {
            request(Long.MAX_VALUE);
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    ((Map) this.value).put(this.keySelector.call(t), this.valueSelector.call(t));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    unsubscribe();
                    onError(th);
                }
            }
        }
    }
}
