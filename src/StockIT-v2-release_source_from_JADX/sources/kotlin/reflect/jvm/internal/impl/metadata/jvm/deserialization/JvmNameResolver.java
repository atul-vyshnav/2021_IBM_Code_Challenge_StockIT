package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.text.StringsKt;

/* compiled from: JvmNameResolver.kt */
public final class JvmNameResolver implements NameResolver {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final List<String> PREDEFINED_STRINGS;
    private static final Map<String, Integer> PREDEFINED_STRINGS_MAP;

    /* renamed from: kotlin  reason: collision with root package name */
    private static final String f1388kotlin;
    private final Set<Integer> localNameIndices;
    private final List<JvmProtoBuf.StringTableTypes.Record> records;
    private final String[] strings;
    private final JvmProtoBuf.StringTableTypes types;

    /* compiled from: JvmNameResolver.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JvmProtoBuf.StringTableTypes.Record.Operation.values().length];
            iArr[JvmProtoBuf.StringTableTypes.Record.Operation.NONE.ordinal()] = 1;
            iArr[JvmProtoBuf.StringTableTypes.Record.Operation.INTERNAL_TO_CLASS_ID.ordinal()] = 2;
            iArr[JvmProtoBuf.StringTableTypes.Record.Operation.DESC_TO_CLASS_ID.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public JvmNameResolver(JvmProtoBuf.StringTableTypes stringTableTypes, String[] strArr) {
        Set<Integer> set;
        Intrinsics.checkNotNullParameter(stringTableTypes, "types");
        Intrinsics.checkNotNullParameter(strArr, "strings");
        this.types = stringTableTypes;
        this.strings = strArr;
        List<Integer> localNameList = stringTableTypes.getLocalNameList();
        if (localNameList.isEmpty()) {
            set = SetsKt.emptySet();
        } else {
            Intrinsics.checkNotNullExpressionValue(localNameList, "");
            set = CollectionsKt.toSet(localNameList);
        }
        this.localNameIndices = set;
        ArrayList arrayList = new ArrayList();
        List<JvmProtoBuf.StringTableTypes.Record> recordList = getTypes().getRecordList();
        arrayList.ensureCapacity(recordList.size());
        for (JvmProtoBuf.StringTableTypes.Record next : recordList) {
            int range = next.getRange();
            for (int i = 0; i < range; i++) {
                arrayList.add(next);
            }
        }
        arrayList.trimToSize();
        Unit unit = Unit.INSTANCE;
        this.records = arrayList;
    }

    public final JvmProtoBuf.StringTableTypes getTypes() {
        return this.types;
    }

    public String getString(int i) {
        String str;
        int i2 = i;
        JvmProtoBuf.StringTableTypes.Record record = this.records.get(i2);
        if (record.hasString()) {
            str = record.getString();
        } else {
            if (record.hasPredefinedIndex()) {
                int size = Companion.getPREDEFINED_STRINGS().size() - 1;
                int predefinedIndex = record.getPredefinedIndex();
                if (predefinedIndex >= 0 && predefinedIndex <= size) {
                    str = Companion.getPREDEFINED_STRINGS().get(record.getPredefinedIndex());
                }
            }
            str = this.strings[i2];
        }
        if (record.getSubstringIndexCount() >= 2) {
            List<Integer> substringIndexList = record.getSubstringIndexList();
            Intrinsics.checkNotNullExpressionValue(substringIndexList, "substringIndexList");
            Integer num = substringIndexList.get(0);
            Integer num2 = substringIndexList.get(1);
            Intrinsics.checkNotNullExpressionValue(num, "begin");
            if (num.intValue() >= 0) {
                int intValue = num.intValue();
                Intrinsics.checkNotNullExpressionValue(num2, "end");
                if (intValue <= num2.intValue() && num2.intValue() <= str.length()) {
                    Intrinsics.checkNotNullExpressionValue(str, "string");
                    str = str.substring(num.intValue(), num2.intValue());
                    Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                }
            }
        }
        String str2 = str;
        if (record.getReplaceCharCount() >= 2) {
            List<Integer> replaceCharList = record.getReplaceCharList();
            Intrinsics.checkNotNullExpressionValue(replaceCharList, "replaceCharList");
            Intrinsics.checkNotNullExpressionValue(str2, "string");
            str2 = StringsKt.replace$default(str2, (char) replaceCharList.get(0).intValue(), (char) replaceCharList.get(1).intValue(), false, 4, (Object) null);
        }
        String str3 = str2;
        JvmProtoBuf.StringTableTypes.Record.Operation operation = record.getOperation();
        if (operation == null) {
            operation = JvmProtoBuf.StringTableTypes.Record.Operation.NONE;
        }
        int i3 = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i3 == 2) {
            Intrinsics.checkNotNullExpressionValue(str3, "string");
            str3 = StringsKt.replace$default(str3, '$', '.', false, 4, (Object) null);
        } else if (i3 == 3) {
            if (str3.length() >= 2) {
                Intrinsics.checkNotNullExpressionValue(str3, "string");
                str3 = str3.substring(1, str3.length() - 1);
                Intrinsics.checkNotNullExpressionValue(str3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            }
            String str4 = str3;
            Intrinsics.checkNotNullExpressionValue(str4, "string");
            str3 = StringsKt.replace$default(str4, '$', '.', false, 4, (Object) null);
        }
        Intrinsics.checkNotNullExpressionValue(str3, "string");
        return str3;
    }

    public String getQualifiedClassName(int i) {
        return getString(i);
    }

    public boolean isLocalClassName(int i) {
        return this.localNameIndices.contains(Integer.valueOf(i));
    }

    /* compiled from: JvmNameResolver.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<String> getPREDEFINED_STRINGS() {
            return JvmNameResolver.PREDEFINED_STRINGS;
        }
    }

    static {
        String joinToString$default = CollectionsKt.joinToString$default(CollectionsKt.listOf('k', 'o', 't', 'l', 'i', 'n'), "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
        f1388kotlin = joinToString$default;
        PREDEFINED_STRINGS = CollectionsKt.listOf(Intrinsics.stringPlus(joinToString$default, "/Any"), Intrinsics.stringPlus(f1388kotlin, "/Nothing"), Intrinsics.stringPlus(f1388kotlin, "/Unit"), Intrinsics.stringPlus(f1388kotlin, "/Throwable"), Intrinsics.stringPlus(f1388kotlin, "/Number"), Intrinsics.stringPlus(f1388kotlin, "/Byte"), Intrinsics.stringPlus(f1388kotlin, "/Double"), Intrinsics.stringPlus(f1388kotlin, "/Float"), Intrinsics.stringPlus(f1388kotlin, "/Int"), Intrinsics.stringPlus(f1388kotlin, "/Long"), Intrinsics.stringPlus(f1388kotlin, "/Short"), Intrinsics.stringPlus(f1388kotlin, "/Boolean"), Intrinsics.stringPlus(f1388kotlin, "/Char"), Intrinsics.stringPlus(f1388kotlin, "/CharSequence"), Intrinsics.stringPlus(f1388kotlin, "/String"), Intrinsics.stringPlus(f1388kotlin, "/Comparable"), Intrinsics.stringPlus(f1388kotlin, "/Enum"), Intrinsics.stringPlus(f1388kotlin, "/Array"), Intrinsics.stringPlus(f1388kotlin, "/ByteArray"), Intrinsics.stringPlus(f1388kotlin, "/DoubleArray"), Intrinsics.stringPlus(f1388kotlin, "/FloatArray"), Intrinsics.stringPlus(f1388kotlin, "/IntArray"), Intrinsics.stringPlus(f1388kotlin, "/LongArray"), Intrinsics.stringPlus(f1388kotlin, "/ShortArray"), Intrinsics.stringPlus(f1388kotlin, "/BooleanArray"), Intrinsics.stringPlus(f1388kotlin, "/CharArray"), Intrinsics.stringPlus(f1388kotlin, "/Cloneable"), Intrinsics.stringPlus(f1388kotlin, "/Annotation"), Intrinsics.stringPlus(f1388kotlin, "/collections/Iterable"), Intrinsics.stringPlus(f1388kotlin, "/collections/MutableIterable"), Intrinsics.stringPlus(f1388kotlin, "/collections/Collection"), Intrinsics.stringPlus(f1388kotlin, "/collections/MutableCollection"), Intrinsics.stringPlus(f1388kotlin, "/collections/List"), Intrinsics.stringPlus(f1388kotlin, "/collections/MutableList"), Intrinsics.stringPlus(f1388kotlin, "/collections/Set"), Intrinsics.stringPlus(f1388kotlin, "/collections/MutableSet"), Intrinsics.stringPlus(f1388kotlin, "/collections/Map"), Intrinsics.stringPlus(f1388kotlin, "/collections/MutableMap"), Intrinsics.stringPlus(f1388kotlin, "/collections/Map.Entry"), Intrinsics.stringPlus(f1388kotlin, "/collections/MutableMap.MutableEntry"), Intrinsics.stringPlus(f1388kotlin, "/collections/Iterator"), Intrinsics.stringPlus(f1388kotlin, "/collections/MutableIterator"), Intrinsics.stringPlus(f1388kotlin, "/collections/ListIterator"), Intrinsics.stringPlus(f1388kotlin, "/collections/MutableListIterator"));
        Iterable<IndexedValue> withIndex = CollectionsKt.withIndex(Companion.getPREDEFINED_STRINGS());
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(withIndex, 10)), 16));
        for (IndexedValue indexedValue : withIndex) {
            linkedHashMap.put((String) indexedValue.getValue(), Integer.valueOf(indexedValue.getIndex()));
        }
        PREDEFINED_STRINGS_MAP = linkedHashMap;
    }
}
