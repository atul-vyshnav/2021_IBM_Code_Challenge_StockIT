package com.facebook.hermes.reactexecutor;

import com.facebook.hermes.instrumentation.HermesSamplingProfiler;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.react.bridge.JavaScriptExecutorFactory;

public class HermesExecutorFactory implements JavaScriptExecutorFactory {
    private static final String TAG = "Hermes";
    private final RuntimeConfig mConfig;

    public String toString() {
        return "JSIExecutor+HermesRuntime";
    }

    public HermesExecutorFactory() {
        this((RuntimeConfig) null);
    }

    public HermesExecutorFactory(RuntimeConfig runtimeConfig) {
        this.mConfig = runtimeConfig;
    }

    public JavaScriptExecutor create() {
        return new HermesExecutor(this.mConfig);
    }

    public void startSamplingProfiler() {
        HermesSamplingProfiler.enable();
    }

    public void stopSamplingProfiler(String str) {
        HermesSamplingProfiler.dumpSampledTraceToFile(str);
        HermesSamplingProfiler.disable();
    }
}
