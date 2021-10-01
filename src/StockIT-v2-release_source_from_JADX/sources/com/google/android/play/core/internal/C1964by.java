package com.google.android.play.core.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import com.google.android.play.core.splitcompat.C2032p;

/* renamed from: com.google.android.play.core.internal.by */
public final class C1964by {

    /* renamed from: a */
    private static final C1918af f810a = new C1918af("PhoneskyVerificationUtils");

    /* renamed from: a */
    public static boolean m751a(Context context) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo("com.android.vending", 64).signatures;
            if (signatureArr == null || (r1 = signatureArr.length) == 0) {
                f810a.mo34143d("Phonesky package is not signed -- possibly self-built package. Could not verify.", new Object[0]);
                return false;
            }
            for (Signature byteArray : signatureArr) {
                String a = C2032p.m934a(byteArray.toByteArray());
                if ("8P1sW0EPJcslw7UzRsiXL64w-O50Ed-RBICtay1g24M".equals(a)) {
                    return true;
                }
                if ((Build.TAGS.contains("dev-keys") || Build.TAGS.contains("test-keys")) && "GXWy8XF3vIml3_MfnmSmyuKBpT3B0dWbHRR_4cgq-gA".equals(a)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }
}
