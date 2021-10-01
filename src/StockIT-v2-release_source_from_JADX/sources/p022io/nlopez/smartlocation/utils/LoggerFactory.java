package p022io.nlopez.smartlocation.utils;

import android.util.Log;

/* renamed from: io.nlopez.smartlocation.utils.LoggerFactory */
public class LoggerFactory {
    public static Logger buildLogger(boolean z) {
        return z ? new Blabber() : new Sssht();
    }

    /* renamed from: io.nlopez.smartlocation.utils.LoggerFactory$Sssht */
    private static class Sssht implements Logger {
        /* renamed from: d */
        public void mo40195d(String str, Object... objArr) {
        }

        /* renamed from: d */
        public void mo40196d(Throwable th, String str, Object... objArr) {
        }

        /* renamed from: e */
        public void mo40197e(String str, Object... objArr) {
        }

        /* renamed from: e */
        public void mo40198e(Throwable th, String str, Object... objArr) {
        }

        /* renamed from: i */
        public void mo40199i(String str, Object... objArr) {
        }

        /* renamed from: i */
        public void mo40200i(Throwable th, String str, Object... objArr) {
        }

        /* renamed from: v */
        public void mo40201v(String str, Object... objArr) {
        }

        /* renamed from: v */
        public void mo40202v(Throwable th, String str, Object... objArr) {
        }

        /* renamed from: w */
        public void mo40203w(String str, Object... objArr) {
        }

        /* renamed from: w */
        public void mo40204w(Throwable th, String str, Object... objArr) {
        }

        private Sssht() {
        }
    }

    /* renamed from: io.nlopez.smartlocation.utils.LoggerFactory$Blabber */
    private static class Blabber implements Logger {
        private Blabber() {
        }

        private String getTag() {
            return new Exception().getStackTrace()[3].getMethodName();
        }

        private String formatMessage(String str, Object... objArr) {
            return objArr.length == 0 ? str : String.format(str, objArr);
        }

        /* renamed from: v */
        public void mo40201v(String str, Object... objArr) {
            Log.v(getTag(), formatMessage(str, objArr));
        }

        /* renamed from: v */
        public void mo40202v(Throwable th, String str, Object... objArr) {
            Log.v(getTag(), formatMessage(str, objArr), th);
        }

        /* renamed from: d */
        public void mo40195d(String str, Object... objArr) {
            Log.d(getTag(), formatMessage(str, objArr));
        }

        /* renamed from: d */
        public void mo40196d(Throwable th, String str, Object... objArr) {
            Log.d(getTag(), formatMessage(str, objArr), th);
        }

        /* renamed from: i */
        public void mo40199i(String str, Object... objArr) {
            Log.i(getTag(), formatMessage(str, objArr));
        }

        /* renamed from: i */
        public void mo40200i(Throwable th, String str, Object... objArr) {
            Log.i(getTag(), formatMessage(str, objArr), th);
        }

        /* renamed from: w */
        public void mo40203w(String str, Object... objArr) {
            Log.w(getTag(), formatMessage(str, objArr));
        }

        /* renamed from: w */
        public void mo40204w(Throwable th, String str, Object... objArr) {
            Log.w(getTag(), formatMessage(str, objArr), th);
        }

        /* renamed from: e */
        public void mo40197e(String str, Object... objArr) {
            Log.e(getTag(), formatMessage(str, objArr));
        }

        /* renamed from: e */
        public void mo40198e(Throwable th, String str, Object... objArr) {
            Log.e(getTag(), formatMessage(str, objArr), th);
        }
    }
}
