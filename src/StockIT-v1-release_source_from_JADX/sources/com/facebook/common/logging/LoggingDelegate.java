package com.facebook.common.logging;

public interface LoggingDelegate {
    /* renamed from: d */
    void mo12367d(String str, String str2);

    /* renamed from: d */
    void mo12368d(String str, String str2, Throwable th);

    /* renamed from: e */
    void mo12369e(String str, String str2);

    /* renamed from: e */
    void mo12370e(String str, String str2, Throwable th);

    int getMinimumLoggingLevel();

    /* renamed from: i */
    void mo12372i(String str, String str2);

    /* renamed from: i */
    void mo12373i(String str, String str2, Throwable th);

    boolean isLoggable(int i);

    void log(int i, String str, String str2);

    void setMinimumLoggingLevel(int i);

    /* renamed from: v */
    void mo12378v(String str, String str2);

    /* renamed from: v */
    void mo12379v(String str, String str2, Throwable th);

    /* renamed from: w */
    void mo12380w(String str, String str2);

    /* renamed from: w */
    void mo12381w(String str, String str2, Throwable th);

    void wtf(String str, String str2);

    void wtf(String str, String str2, Throwable th);
}
