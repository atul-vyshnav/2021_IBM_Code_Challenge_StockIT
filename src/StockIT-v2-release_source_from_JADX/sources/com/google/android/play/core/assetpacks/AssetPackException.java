package com.google.android.play.core.assetpacks;

import com.google.android.play.core.assetpacks.model.C1892a;
import com.google.android.play.core.tasks.C2107j;

public class AssetPackException extends C2107j {

    /* renamed from: a */
    private final int f338a;

    AssetPackException(int i) {
        super(String.format("Asset Pack Download Error(%d): %s", new Object[]{Integer.valueOf(i), C1892a.m564a(i)}));
        if (i != 0) {
            this.f338a = i;
            return;
        }
        throw new IllegalArgumentException("errorCode should not be 0.");
    }

    public int getErrorCode() {
        return this.f338a;
    }
}
