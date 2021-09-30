package com.polidea.rxandroidble.internal.util;

import p030rx.Observable;

public class ObservableUtil {
    private static final Observable.Transformer<?, ?> IDENTITY_TRANSFORMER = new Observable.Transformer<Object, Object>() {
        public Observable<Object> call(Observable<Object> observable) {
            return observable;
        }
    };

    private ObservableUtil() {
    }

    public static <T> Observable<T> justOnNext(T t) {
        return Observable.never().startWith(t);
    }

    public static <T> Observable.Transformer<T, T> identityTransformer() {
        return IDENTITY_TRANSFORMER;
    }
}
