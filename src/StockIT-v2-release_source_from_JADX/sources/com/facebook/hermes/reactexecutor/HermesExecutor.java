package com.facebook.hermes.reactexecutor;

import com.facebook.jni.HybridData;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.soloader.SoLoader;
import com.polidea.multiplatformbleadapter.utils.Constants;

public class HermesExecutor extends JavaScriptExecutor {
    private static String mode_;

    public static native boolean canLoadFile(String str);

    private static native HybridData initHybrid(long j, boolean z);

    private static native HybridData initHybridDefaultConfig();

    static {
        SoLoader.loadLibrary("hermes");
        try {
            SoLoader.loadLibrary("hermes-executor-debug");
            mode_ = Constants.BluetoothLogLevel.DEBUG;
        } catch (UnsatisfiedLinkError unused) {
            SoLoader.loadLibrary("hermes-executor-release");
            mode_ = "Release";
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    HermesExecutor(@javax.annotation.Nullable com.facebook.hermes.reactexecutor.RuntimeConfig r3) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0007
            com.facebook.jni.HybridData r3 = initHybridDefaultConfig()
            goto L_0x000f
        L_0x0007:
            long r0 = r3.heapSizeMB
            boolean r3 = r3.es6Proxy
            com.facebook.jni.HybridData r3 = initHybrid(r0, r3)
        L_0x000f:
            r2.<init>(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.hermes.reactexecutor.HermesExecutor.<init>(com.facebook.hermes.reactexecutor.RuntimeConfig):void");
    }

    public String getName() {
        return "HermesExecutor" + mode_;
    }
}
