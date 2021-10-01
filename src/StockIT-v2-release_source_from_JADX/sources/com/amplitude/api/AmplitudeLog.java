package com.amplitude.api;

import android.util.Log;

public class AmplitudeLog {
    protected static AmplitudeLog instance = new AmplitudeLog();
    private volatile boolean enableLogging = true;
    private volatile int logLevel = 4;

    public static AmplitudeLog getLogger() {
        return instance;
    }

    private AmplitudeLog() {
    }

    /* access modifiers changed from: package-private */
    public AmplitudeLog setEnableLogging(boolean z) {
        this.enableLogging = z;
        return instance;
    }

    /* access modifiers changed from: package-private */
    public AmplitudeLog setLogLevel(int i) {
        this.logLevel = i;
        return instance;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public int mo10604d(String str, String str2) {
        if (!this.enableLogging || this.logLevel > 3) {
            return 0;
        }
        return Log.d(str, str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public int mo10605d(String str, String str2, Throwable th) {
        if (!this.enableLogging || this.logLevel > 3) {
            return 0;
        }
        return Log.d(str, str2, th);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public int mo10606e(String str, String str2) {
        if (!this.enableLogging || this.logLevel > 6) {
            return 0;
        }
        return Log.e(str, str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public int mo10607e(String str, String str2, Throwable th) {
        if (!this.enableLogging || this.logLevel > 6) {
            return 0;
        }
        return Log.e(str, str2, th);
    }

    /* access modifiers changed from: package-private */
    public String getStackTraceString(Throwable th) {
        return Log.getStackTraceString(th);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: i */
    public int mo10609i(String str, String str2) {
        if (!this.enableLogging || this.logLevel > 4) {
            return 0;
        }
        return Log.i(str, str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: i */
    public int mo10610i(String str, String str2, Throwable th) {
        if (!this.enableLogging || this.logLevel > 4) {
            return 0;
        }
        return Log.i(str, str2, th);
    }

    /* access modifiers changed from: package-private */
    public boolean isLoggable(String str, int i) {
        return Log.isLoggable(str, i);
    }

    /* access modifiers changed from: package-private */
    public int println(int i, String str, String str2) {
        return Log.println(i, str, str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: v */
    public int mo10615v(String str, String str2) {
        if (!this.enableLogging || this.logLevel > 2) {
            return 0;
        }
        return Log.v(str, str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: v */
    public int mo10616v(String str, String str2, Throwable th) {
        if (!this.enableLogging || this.logLevel > 2) {
            return 0;
        }
        return Log.v(str, str2, th);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: w */
    public int mo10617w(String str, String str2) {
        if (!this.enableLogging || this.logLevel > 5) {
            return 0;
        }
        return Log.w(str, str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: w */
    public int mo10619w(String str, Throwable th) {
        if (!this.enableLogging || this.logLevel > 5) {
            return 0;
        }
        return Log.w(str, th);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: w */
    public int mo10618w(String str, String str2, Throwable th) {
        if (!this.enableLogging || this.logLevel > 5) {
            return 0;
        }
        return Log.w(str, str2, th);
    }

    /* access modifiers changed from: package-private */
    public int wtf(String str, String str2) {
        if (!this.enableLogging || this.logLevel > 7) {
            return 0;
        }
        return Log.wtf(str, str2);
    }

    /* access modifiers changed from: package-private */
    public int wtf(String str, Throwable th) {
        if (!this.enableLogging || this.logLevel > 7) {
            return 0;
        }
        return Log.wtf(str, th);
    }

    /* access modifiers changed from: package-private */
    public int wtf(String str, String str2, Throwable th) {
        if (!this.enableLogging || this.logLevel > 7) {
            return 0;
        }
        return Log.wtf(str, str2, th);
    }
}
