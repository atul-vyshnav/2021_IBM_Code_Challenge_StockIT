package com.jakewharton.rxrelay;

import p030rx.functions.Func1;

final class UtilityFunctions {
    private static final Func1<Object, Object> IDENTITY = new Func1<Object, Object>() {
        public Object call(Object obj) {
            return obj;
        }
    };

    static <T> Func1<T, T> identity() {
        return IDENTITY;
    }

    private UtilityFunctions() {
    }
}
