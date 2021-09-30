package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.p029io.IOUtils;

/* compiled from: ClassMapperLite.kt */
public final class ClassMapperLite {
    public static final ClassMapperLite INSTANCE = new ClassMapperLite();

    /* renamed from: kotlin  reason: collision with root package name */
    private static final String f1387kotlin = CollectionsKt.joinToString$default(CollectionsKt.listOf('k', 'o', 't', 'l', 'i', 'n'), "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
    private static final Map<String, String> map;

    private ClassMapperLite() {
    }

    static {
        int i = 0;
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        List listOf = CollectionsKt.listOf("Boolean", "Z", "Char", "C", "Byte", "B", "Short", ExifInterface.LATITUDE_SOUTH, "Int", "I", "Float", "F", "Long", "J", "Double", "D");
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, listOf.size() - 1, 2);
        if (progressionLastElement >= 0) {
            int i2 = 0;
            while (true) {
                int i3 = i2 + 2;
                int i4 = i2 + 1;
                linkedHashMap.put(f1387kotlin + IOUtils.DIR_SEPARATOR_UNIX + ((String) listOf.get(i2)), listOf.get(i4));
                linkedHashMap.put(f1387kotlin + IOUtils.DIR_SEPARATOR_UNIX + ((String) listOf.get(i2)) + "Array", Intrinsics.stringPlus("[", listOf.get(i4)));
                if (i2 == progressionLastElement) {
                    break;
                }
                i2 = i3;
            }
        }
        linkedHashMap.put(Intrinsics.stringPlus(f1387kotlin, "/Unit"), ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        m2437map$lambda0$add(linkedHashMap, "Any", "java/lang/Object");
        m2437map$lambda0$add(linkedHashMap, "Nothing", "java/lang/Void");
        m2437map$lambda0$add(linkedHashMap, "Annotation", "java/lang/annotation/Annotation");
        for (String str : CollectionsKt.listOf("String", "CharSequence", "Throwable", "Cloneable", "Number", "Comparable", "Enum")) {
            m2437map$lambda0$add(linkedHashMap, str, Intrinsics.stringPlus("java/lang/", str));
        }
        for (String str2 : CollectionsKt.listOf("Iterator", "Collection", "List", "Set", "Map", "ListIterator")) {
            m2437map$lambda0$add(linkedHashMap, Intrinsics.stringPlus("collections/", str2), Intrinsics.stringPlus("java/util/", str2));
            m2437map$lambda0$add(linkedHashMap, Intrinsics.stringPlus("collections/Mutable", str2), Intrinsics.stringPlus("java/util/", str2));
        }
        m2437map$lambda0$add(linkedHashMap, "collections/Iterable", "java/lang/Iterable");
        m2437map$lambda0$add(linkedHashMap, "collections/MutableIterable", "java/lang/Iterable");
        m2437map$lambda0$add(linkedHashMap, "collections/Map.Entry", "java/util/Map$Entry");
        m2437map$lambda0$add(linkedHashMap, "collections/MutableMap.MutableEntry", "java/util/Map$Entry");
        while (true) {
            int i5 = i + 1;
            String stringPlus = Intrinsics.stringPlus("Function", Integer.valueOf(i));
            m2437map$lambda0$add(linkedHashMap, stringPlus, f1387kotlin + "/jvm/functions/Function" + i);
            m2437map$lambda0$add(linkedHashMap, Intrinsics.stringPlus("reflect/KFunction", Integer.valueOf(i)), Intrinsics.stringPlus(f1387kotlin, "/reflect/KFunction"));
            if (i5 > 22) {
                break;
            }
            i = i5;
        }
        for (String str3 : CollectionsKt.listOf("Char", "Byte", "Short", "Int", "Float", "Long", "Double", "String", "Enum")) {
            String stringPlus2 = Intrinsics.stringPlus(str3, ".Companion");
            m2437map$lambda0$add(linkedHashMap, stringPlus2, f1387kotlin + "/jvm/internal/" + str3 + "CompanionObject");
        }
        map = linkedHashMap;
    }

    /* renamed from: map$lambda-0$add  reason: not valid java name */
    private static final void m2437map$lambda0$add(Map<String, String> map2, String str, String str2) {
        map2.put(f1387kotlin + IOUtils.DIR_SEPARATOR_UNIX + str, 'L' + str2 + ';');
    }

    @JvmStatic
    public static final String mapClass(String str) {
        Intrinsics.checkNotNullParameter(str, "classId");
        String str2 = map.get(str);
        if (str2 != null) {
            return str2;
        }
        return 'L' + StringsKt.replace$default(str, '.', '$', false, 4, (Object) null) + ';';
    }
}
