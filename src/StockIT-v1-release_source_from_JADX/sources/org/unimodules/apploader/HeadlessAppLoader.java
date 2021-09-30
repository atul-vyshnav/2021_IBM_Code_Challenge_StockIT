package org.unimodules.apploader;

import android.content.Context;
import org.unimodules.core.interfaces.Consumer;

public interface HeadlessAppLoader {
    boolean invalidateApp(String str);

    boolean isRunning(String str);

    void loadApp(Context context, Params params, Runnable runnable, Consumer<Boolean> consumer) throws AppConfigurationError;

    public static class AppConfigurationError extends Exception {
        public AppConfigurationError(String str) {
            super(str);
        }

        public AppConfigurationError(String str, Exception exc) {
            super(str, exc);
        }
    }

    public static final class Params {
        private final String appId;
        private final String appUrl;

        public Params(String str, String str2) {
            this.appId = str;
            this.appUrl = str2;
        }

        public String getAppId() {
            return this.appId;
        }

        public String getAppUrl() {
            return this.appUrl;
        }
    }
}
