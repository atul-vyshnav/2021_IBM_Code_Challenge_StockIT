package org.unimodules.core;

import org.unimodules.core.interfaces.CodedThrowable;

public interface Promise {
    public static final String UNKNOWN_ERROR = "E_UNKNOWN_ERROR";

    void reject(String str, String str2);

    void reject(String str, String str2, Throwable th);

    void reject(String str, Throwable th);

    void reject(Throwable th);

    void resolve(Object obj);

    /* renamed from: org.unimodules.core.Promise$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$reject(Promise _this, Throwable th) {
            if (th instanceof CodedThrowable) {
                CodedThrowable codedThrowable = (CodedThrowable) th;
                _this.reject(codedThrowable.getCode(), codedThrowable.getMessage(), th);
                return;
            }
            _this.reject(Promise.UNKNOWN_ERROR, th);
        }

        public static void $default$reject(Promise _this, String str, String str2) {
            _this.reject(str, str2, (Throwable) null);
        }

        public static void $default$reject(Promise _this, String str, Throwable th) {
            _this.reject(str, th.getMessage(), th);
        }
    }
}
