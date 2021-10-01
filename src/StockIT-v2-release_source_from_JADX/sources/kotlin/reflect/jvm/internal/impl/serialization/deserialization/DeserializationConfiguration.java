package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeserializationConfiguration.kt */
public interface DeserializationConfiguration {

    /* compiled from: DeserializationConfiguration.kt */
    public static final class DefaultImpls {
        public static boolean getAllowUnstableDependencies(DeserializationConfiguration deserializationConfiguration) {
            Intrinsics.checkNotNullParameter(deserializationConfiguration, "this");
            return false;
        }

        public static boolean getPreserveDeclarationsOrdering(DeserializationConfiguration deserializationConfiguration) {
            Intrinsics.checkNotNullParameter(deserializationConfiguration, "this");
            return false;
        }

        public static boolean getReleaseCoroutines(DeserializationConfiguration deserializationConfiguration) {
            Intrinsics.checkNotNullParameter(deserializationConfiguration, "this");
            return false;
        }

        public static boolean getReportErrorsOnPreReleaseDependencies(DeserializationConfiguration deserializationConfiguration) {
            Intrinsics.checkNotNullParameter(deserializationConfiguration, "this");
            return false;
        }

        public static boolean getSkipMetadataVersionCheck(DeserializationConfiguration deserializationConfiguration) {
            Intrinsics.checkNotNullParameter(deserializationConfiguration, "this");
            return false;
        }

        public static boolean getSkipPrereleaseCheck(DeserializationConfiguration deserializationConfiguration) {
            Intrinsics.checkNotNullParameter(deserializationConfiguration, "this");
            return false;
        }

        public static boolean getTypeAliasesAllowed(DeserializationConfiguration deserializationConfiguration) {
            Intrinsics.checkNotNullParameter(deserializationConfiguration, "this");
            return true;
        }
    }

    boolean getAllowUnstableDependencies();

    boolean getPreserveDeclarationsOrdering();

    boolean getReleaseCoroutines();

    boolean getReportErrorsOnPreReleaseDependencies();

    boolean getSkipMetadataVersionCheck();

    boolean getSkipPrereleaseCheck();

    boolean getTypeAliasesAllowed();

    /* compiled from: DeserializationConfiguration.kt */
    public static final class Default implements DeserializationConfiguration {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        public boolean getAllowUnstableDependencies() {
            return DefaultImpls.getAllowUnstableDependencies(this);
        }

        public boolean getPreserveDeclarationsOrdering() {
            return DefaultImpls.getPreserveDeclarationsOrdering(this);
        }

        public boolean getReleaseCoroutines() {
            return DefaultImpls.getReleaseCoroutines(this);
        }

        public boolean getReportErrorsOnPreReleaseDependencies() {
            return DefaultImpls.getReportErrorsOnPreReleaseDependencies(this);
        }

        public boolean getSkipMetadataVersionCheck() {
            return DefaultImpls.getSkipMetadataVersionCheck(this);
        }

        public boolean getSkipPrereleaseCheck() {
            return DefaultImpls.getSkipPrereleaseCheck(this);
        }

        public boolean getTypeAliasesAllowed() {
            return DefaultImpls.getTypeAliasesAllowed(this);
        }
    }
}
