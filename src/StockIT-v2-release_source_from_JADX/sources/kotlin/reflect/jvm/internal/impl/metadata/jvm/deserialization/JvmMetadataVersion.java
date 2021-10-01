package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;

/* compiled from: JvmMetadataVersion.kt */
public final class JvmMetadataVersion extends BinaryVersion {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final JvmMetadataVersion INSTANCE = new JvmMetadataVersion(1, 4, 2);
    public static final JvmMetadataVersion INVALID_VERSION = new JvmMetadataVersion(new int[0]);
    private final boolean isStrictSemantics;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public JvmMetadataVersion(int[] r4, boolean r5) {
        /*
            r3 = this;
            java.lang.String r0 = "versionArray"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            int r0 = r4.length
            int[] r0 = new int[r0]
            int r1 = r4.length
            r2 = 0
            java.lang.System.arraycopy(r4, r2, r0, r2, r1)
            r3.<init>(r0)
            r3.isStrictSemantics = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion.<init>(int[], boolean):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public JvmMetadataVersion(int... iArr) {
        this(iArr, false);
        Intrinsics.checkNotNullParameter(iArr, "numbers");
    }

    public boolean isCompatible() {
        boolean z;
        if (getMajor() == 1 && getMinor() == 0) {
            return false;
        }
        if (this.isStrictSemantics) {
            z = isCompatibleTo(INSTANCE);
        } else {
            z = getMajor() == INSTANCE.getMajor() && getMinor() <= INSTANCE.getMinor() + 1;
        }
        if (z) {
            return true;
        }
        return false;
    }

    /* compiled from: JvmMetadataVersion.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
