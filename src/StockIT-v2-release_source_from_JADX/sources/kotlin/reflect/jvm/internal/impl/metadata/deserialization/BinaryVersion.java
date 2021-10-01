package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import androidx.core.p005os.EnvironmentCompat;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BinaryVersion.kt */
public abstract class BinaryVersion {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final int major;
    private final int minor;
    private final int[] numbers;
    private final int patch;
    private final List<Integer> rest;

    public BinaryVersion(int... iArr) {
        Intrinsics.checkNotNullParameter(iArr, "numbers");
        this.numbers = iArr;
        Integer orNull = ArraysKt.getOrNull(iArr, 0);
        int i = -1;
        this.major = orNull == null ? -1 : orNull.intValue();
        Integer orNull2 = ArraysKt.getOrNull(this.numbers, 1);
        this.minor = orNull2 == null ? -1 : orNull2.intValue();
        Integer orNull3 = ArraysKt.getOrNull(this.numbers, 2);
        this.patch = orNull3 != null ? orNull3.intValue() : i;
        int[] iArr2 = this.numbers;
        this.rest = iArr2.length > 3 ? CollectionsKt.toList(ArraysKt.asList(iArr2).subList(3, this.numbers.length)) : CollectionsKt.emptyList();
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public final int[] toArray() {
        return this.numbers;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0021 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isCompatibleTo(kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion r5) {
        /*
            r4 = this;
            java.lang.String r0 = "ourVersion"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            int r0 = r4.major
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0016
            int r0 = r5.major
            if (r0 != 0) goto L_0x0021
            int r0 = r4.minor
            int r5 = r5.minor
            if (r0 != r5) goto L_0x0021
            goto L_0x0022
        L_0x0016:
            int r3 = r5.major
            if (r0 != r3) goto L_0x0021
            int r0 = r4.minor
            int r5 = r5.minor
            if (r0 > r5) goto L_0x0021
            goto L_0x0022
        L_0x0021:
            r1 = 0
        L_0x0022:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion.isCompatibleTo(kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion):boolean");
    }

    public final boolean isAtLeast(BinaryVersion binaryVersion) {
        Intrinsics.checkNotNullParameter(binaryVersion, "version");
        return isAtLeast(binaryVersion.major, binaryVersion.minor, binaryVersion.patch);
    }

    public final boolean isAtLeast(int i, int i2, int i3) {
        int i4 = this.major;
        if (i4 > i) {
            return true;
        }
        if (i4 < i) {
            return false;
        }
        int i5 = this.minor;
        if (i5 > i2) {
            return true;
        }
        if (i5 >= i2 && this.patch >= i3) {
            return true;
        }
        return false;
    }

    public final boolean isAtMost(int i, int i2, int i3) {
        int i4 = this.major;
        if (i4 < i) {
            return true;
        }
        if (i4 > i) {
            return false;
        }
        int i5 = this.minor;
        if (i5 < i2) {
            return true;
        }
        if (i5 <= i2 && this.patch <= i3) {
            return true;
        }
        return false;
    }

    public String toString() {
        int[] array = toArray();
        ArrayList arrayList = new ArrayList();
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int i2 = array[i];
            if (!(i2 != -1)) {
                break;
            }
            arrayList.add(Integer.valueOf(i2));
        }
        List list = arrayList;
        return list.isEmpty() ? EnvironmentCompat.MEDIA_UNKNOWN : CollectionsKt.joinToString$default(list, ".", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
    }

    public boolean equals(Object obj) {
        if (obj != null && Intrinsics.areEqual((Object) getClass(), (Object) obj.getClass())) {
            BinaryVersion binaryVersion = (BinaryVersion) obj;
            return this.major == binaryVersion.major && this.minor == binaryVersion.minor && this.patch == binaryVersion.patch && Intrinsics.areEqual((Object) this.rest, (Object) binaryVersion.rest);
        }
    }

    public int hashCode() {
        int i = this.major;
        int i2 = i + (i * 31) + this.minor;
        int i3 = i2 + (i2 * 31) + this.patch;
        return i3 + (i3 * 31) + this.rest.hashCode();
    }

    /* compiled from: BinaryVersion.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
