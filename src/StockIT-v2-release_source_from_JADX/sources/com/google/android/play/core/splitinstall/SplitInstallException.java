package com.google.android.play.core.splitinstall;

import com.google.android.play.core.splitinstall.model.C2073a;
import com.google.android.play.core.tasks.C2107j;

public class SplitInstallException extends C2107j {

    /* renamed from: a */
    private final int f898a;

    public SplitInstallException(int i) {
        super(String.format("Split Install Error(%d): %s", new Object[]{Integer.valueOf(i), C2073a.m1020a(i)}));
        if (i != 0) {
            this.f898a = i;
            return;
        }
        throw new IllegalArgumentException("errorCode should not be 0.");
    }

    public int getErrorCode() {
        return this.f898a;
    }
}
