package p030rx.internal.util;

import p030rx.functions.Func1;

/* renamed from: rx.internal.util.UtilityFunctions */
public final class UtilityFunctions {

    /* renamed from: rx.internal.util.UtilityFunctions$Identity */
    enum Identity implements Func1<Object, Object> {
        INSTANCE;

        public Object call(Object obj) {
            return obj;
        }
    }

    private UtilityFunctions() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Func1<? super T, Boolean> alwaysTrue() {
        return AlwaysTrue.INSTANCE;
    }

    public static <T> Func1<? super T, Boolean> alwaysFalse() {
        return AlwaysFalse.INSTANCE;
    }

    public static <T> Func1<T, T> identity() {
        return Identity.INSTANCE;
    }

    /* renamed from: rx.internal.util.UtilityFunctions$AlwaysTrue */
    enum AlwaysTrue implements Func1<Object, Boolean> {
        INSTANCE;

        public Boolean call(Object obj) {
            return true;
        }
    }

    /* renamed from: rx.internal.util.UtilityFunctions$AlwaysFalse */
    enum AlwaysFalse implements Func1<Object, Boolean> {
        INSTANCE;

        public Boolean call(Object obj) {
            return false;
        }
    }
}
