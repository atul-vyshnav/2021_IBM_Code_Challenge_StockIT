package com.google.android.play.core.install;

import com.google.android.play.core.install.model.C1911a;
import com.google.android.play.core.tasks.C2107j;

public class InstallException extends C2107j {

    /* renamed from: a */
    private final int f763a;

    public InstallException(int i) {
        super(String.format("Install Error(%d): %s", new Object[]{Integer.valueOf(i), C1911a.m601a(i)}));
        if (i != 0) {
            this.f763a = i;
            return;
        }
        throw new IllegalArgumentException("errorCode should not be 0.");
    }

    public int getErrorCode() {
        return this.f763a;
    }
}
