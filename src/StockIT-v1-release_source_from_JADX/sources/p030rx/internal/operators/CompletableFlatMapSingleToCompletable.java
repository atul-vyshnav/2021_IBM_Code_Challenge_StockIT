package p030rx.internal.operators;

import p030rx.Completable;
import p030rx.CompletableSubscriber;
import p030rx.Single;
import p030rx.SingleSubscriber;
import p030rx.Subscription;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Func1;

/* renamed from: rx.internal.operators.CompletableFlatMapSingleToCompletable */
public final class CompletableFlatMapSingleToCompletable<T> implements Completable.OnSubscribe {
    final Func1<? super T, ? extends Completable> mapper;
    final Single<T> source;

    public CompletableFlatMapSingleToCompletable(Single<T> single, Func1<? super T, ? extends Completable> func1) {
        this.source = single;
        this.mapper = func1;
    }

    public void call(CompletableSubscriber completableSubscriber) {
        SourceSubscriber sourceSubscriber = new SourceSubscriber(completableSubscriber, this.mapper);
        completableSubscriber.onSubscribe(sourceSubscriber);
        this.source.subscribe(sourceSubscriber);
    }

    /* renamed from: rx.internal.operators.CompletableFlatMapSingleToCompletable$SourceSubscriber */
    static final class SourceSubscriber<T> extends SingleSubscriber<T> implements CompletableSubscriber {
        final CompletableSubscriber actual;
        final Func1<? super T, ? extends Completable> mapper;

        public SourceSubscriber(CompletableSubscriber completableSubscriber, Func1<? super T, ? extends Completable> func1) {
            this.actual = completableSubscriber;
            this.mapper = func1;
        }

        public void onSuccess(T t) {
            try {
                Completable completable = (Completable) this.mapper.call(t);
                if (completable == null) {
                    onError(new NullPointerException("The mapper returned a null Completable"));
                } else {
                    completable.subscribe((CompletableSubscriber) this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onCompleted() {
            this.actual.onCompleted();
        }

        public void onSubscribe(Subscription subscription) {
            add(subscription);
        }
    }
}
