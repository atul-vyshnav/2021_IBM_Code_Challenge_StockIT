package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.functions.Func2;
import p030rx.internal.util.UtilityFunctions;

/* renamed from: rx.internal.operators.OperatorSequenceEqual */
public final class OperatorSequenceEqual {
    static final Object LOCAL_ON_COMPLETED = new Object();

    private OperatorSequenceEqual() {
        throw new IllegalStateException("No instances!");
    }

    static <T> Observable<Object> materializeLite(Observable<T> observable) {
        return Observable.concat(observable, Observable.just(LOCAL_ON_COMPLETED));
    }

    public static <T> Observable<Boolean> sequenceEqual(Observable<? extends T> observable, Observable<? extends T> observable2, final Func2<? super T, ? super T, Boolean> func2) {
        return Observable.zip(materializeLite(observable), materializeLite(observable2), new Func2<Object, Object, Boolean>() {
            public Boolean call(Object obj, Object obj2) {
                boolean z = obj == OperatorSequenceEqual.LOCAL_ON_COMPLETED;
                boolean z2 = obj2 == OperatorSequenceEqual.LOCAL_ON_COMPLETED;
                if (z && z2) {
                    return true;
                }
                if (z || z2) {
                    return false;
                }
                return (Boolean) func2.call(obj, obj2);
            }
        }).all(UtilityFunctions.identity());
    }
}
