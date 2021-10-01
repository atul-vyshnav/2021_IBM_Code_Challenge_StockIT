package com.google.android.play.core.internal;

import android.os.Build;
import java.io.File;

/* renamed from: com.google.android.play.core.internal.av */
public final class C1934av {
    /* renamed from: a */
    public static C1933au m654a() {
        if (Build.VERSION.SDK_INT >= 21) {
            switch (Build.VERSION.SDK_INT) {
                case 21:
                    return new C1940ba();
                case 22:
                    return new C1941bb();
                case 23:
                    return new C1945bf();
                case 24:
                    return new C1946bg();
                case 25:
                    return new C1947bh();
                case 26:
                    return new C1950bk();
                case 27:
                    if (Build.VERSION.PREVIEW_SDK_INT == 0) {
                        return new C1951bl();
                    }
                    break;
            }
            return new C1953bn();
        }
        throw new AssertionError("Unsupported Android Version");
    }

    /* renamed from: a */
    public static String m655a(File file) {
        if (file.getName().endsWith(".apk")) {
            return file.getName().replaceFirst("(_\\d+)?\\.apk", "").replace("base-", "config.").replace("-", ".config.").replace(".config.master", "").replace("config.master", "");
        }
        throw new IllegalArgumentException("Non-apk found in splits directory.");
    }

    /* renamed from: a */
    public static <T> void m656a(T t, Object obj) {
        if (t == null) {
            throw new NullPointerException((String) obj);
        }
    }

    /* renamed from: a */
    public static void m657a(boolean z, Object obj) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }
}
