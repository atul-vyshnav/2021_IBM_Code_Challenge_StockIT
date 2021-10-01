package kotlin.reflect.jvm.internal.impl.load.kotlin.header;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmBytecodeBinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;

/* compiled from: KotlinClassHeader.kt */
public final class KotlinClassHeader {
    private final JvmBytecodeBinaryVersion bytecodeVersion;
    private final String[] data;
    private final int extraInt;
    private final String extraString;
    private final String[] incompatibleData;
    private final Kind kind;
    private final JvmMetadataVersion metadataVersion;
    private final String packageName;
    private final String[] strings;

    private final boolean has(int i, int i2) {
        return (i & i2) != 0;
    }

    public KotlinClassHeader(Kind kind2, JvmMetadataVersion jvmMetadataVersion, JvmBytecodeBinaryVersion jvmBytecodeBinaryVersion, String[] strArr, String[] strArr2, String[] strArr3, String str, int i, String str2) {
        Intrinsics.checkNotNullParameter(kind2, "kind");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(jvmBytecodeBinaryVersion, "bytecodeVersion");
        this.kind = kind2;
        this.metadataVersion = jvmMetadataVersion;
        this.bytecodeVersion = jvmBytecodeBinaryVersion;
        this.data = strArr;
        this.incompatibleData = strArr2;
        this.strings = strArr3;
        this.extraString = str;
        this.extraInt = i;
        this.packageName = str2;
    }

    public final Kind getKind() {
        return this.kind;
    }

    public final JvmMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    public final String[] getData() {
        return this.data;
    }

    public final String[] getIncompatibleData() {
        return this.incompatibleData;
    }

    public final String[] getStrings() {
        return this.strings;
    }

    /* compiled from: KotlinClassHeader.kt */
    public enum Kind {
        UNKNOWN(0),
        CLASS(1),
        FILE_FACADE(2),
        SYNTHETIC_CLASS(3),
        MULTIFILE_CLASS(4),
        MULTIFILE_CLASS_PART(5);
        
        public static final Companion Companion = null;
        /* access modifiers changed from: private */
        public static final Map<Integer, Kind> entryById = null;

        /* renamed from: id */
        private final int f1285id;

        @JvmStatic
        public static final Kind getById(int i) {
            return Companion.getById(i);
        }

        private Kind(int i) {
            this.f1285id = i;
        }

        public final int getId() {
            return this.f1285id;
        }

        static {
            int i;
            Companion = new Companion((DefaultConstructorMarker) null);
            Kind[] values = values();
            Map<Integer, Kind> linkedHashMap = new LinkedHashMap<>(RangesKt.coerceAtLeast(MapsKt.mapCapacity(values.length), 16));
            for (Kind kind : values) {
                linkedHashMap.put(Integer.valueOf(kind.getId()), kind);
            }
            entryById = linkedHashMap;
        }

        /* compiled from: KotlinClassHeader.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            public final Kind getById(int i) {
                Kind kind = (Kind) Kind.entryById.get(Integer.valueOf(i));
                return kind == null ? Kind.UNKNOWN : kind;
            }
        }
    }

    public final String getMultifileClassName() {
        String str = this.extraString;
        if (getKind() == Kind.MULTIFILE_CLASS_PART) {
            return str;
        }
        return null;
    }

    public final List<String> getMultifilePartNames() {
        String[] strArr = this.data;
        List<String> list = null;
        if (!(getKind() == Kind.MULTIFILE_CLASS)) {
            strArr = null;
        }
        if (strArr != null) {
            list = ArraysKt.asList((T[]) strArr);
        }
        return list != null ? list : CollectionsKt.emptyList();
    }

    public final boolean isUnstableJvmIrBinary() {
        return has(this.extraInt, 16) && !has(this.extraInt, 32);
    }

    public final boolean isUnstableFirBinary() {
        return has(this.extraInt, 64) && !has(this.extraInt, 32);
    }

    public final boolean isPreRelease() {
        return has(this.extraInt, 2);
    }

    public String toString() {
        return this.kind + " version=" + this.metadataVersion;
    }
}
