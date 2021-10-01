package com.google.android.play.core.splitinstall;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SplitInstallRequest {

    /* renamed from: a */
    private final List<String> f900a;

    /* renamed from: b */
    private final List<Locale> f901b;

    public static class Builder {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public final List<String> f902a = new ArrayList();
        /* access modifiers changed from: private */

        /* renamed from: b */
        public final List<Locale> f903b = new ArrayList();

        private Builder() {
        }

        /* synthetic */ Builder(byte[] bArr) {
        }

        public Builder addLanguage(Locale locale) {
            this.f903b.add(locale);
            return this;
        }

        public Builder addModule(String str) {
            this.f902a.add(str);
            return this;
        }

        public SplitInstallRequest build() {
            return new SplitInstallRequest(this);
        }
    }

    /* synthetic */ SplitInstallRequest(Builder builder) {
        this.f900a = new ArrayList(builder.f902a);
        this.f901b = new ArrayList(builder.f903b);
    }

    public static Builder newBuilder() {
        return new Builder((byte[]) null);
    }

    public List<Locale> getLanguages() {
        return this.f901b;
    }

    public List<String> getModuleNames() {
        return this.f900a;
    }

    public String toString() {
        return String.format("SplitInstallRequest{modulesNames=%s,languages=%s}", new Object[]{this.f900a, this.f901b});
    }
}
