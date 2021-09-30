package kotlin.reflect.jvm.internal.impl.builtins;

import com.facebook.imagepipeline.memory.BitmapPoolType;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassKind;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* compiled from: StandardNames.kt */
public final class StandardNames {
    public static final FqName ANNOTATION_PACKAGE_FQ_NAME;
    public static final FqName BUILT_INS_PACKAGE_FQ_NAME;
    public static final Set<FqName> BUILT_INS_PACKAGE_FQ_NAMES;
    public static final Name BUILT_INS_PACKAGE_NAME;
    public static final FqName COLLECTIONS_PACKAGE_FQ_NAME;
    public static final FqName CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL;
    public static final FqName CONTINUATION_INTERFACE_FQ_NAME_RELEASE;
    public static final FqName COROUTINES_INTRINSICS_PACKAGE_FQ_NAME_EXPERIMENTAL;
    public static final FqName COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL;
    public static final FqName COROUTINES_PACKAGE_FQ_NAME_RELEASE;
    public static final Name ENUM_VALUES;
    public static final Name ENUM_VALUE_OF;
    public static final StandardNames INSTANCE = new StandardNames();
    public static final FqName KOTLIN_REFLECT_FQ_NAME = new FqName("kotlin.reflect");
    public static final List<String> PREFIXES = CollectionsKt.listOf("KProperty", "KMutableProperty", "KFunction", "KSuspendFunction");
    public static final FqName RANGES_PACKAGE_FQ_NAME;
    public static final FqName RESULT_FQ_NAME = new FqName("kotlin.Result");
    public static final FqName TEXT_PACKAGE_FQ_NAME;

    private StandardNames() {
    }

    static {
        Name identifier = Name.identifier("values");
        Intrinsics.checkNotNullExpressionValue(identifier, "identifier(\"values\")");
        ENUM_VALUES = identifier;
        Name identifier2 = Name.identifier("valueOf");
        Intrinsics.checkNotNullExpressionValue(identifier2, "identifier(\"valueOf\")");
        ENUM_VALUE_OF = identifier2;
        FqName fqName = new FqName("kotlin.coroutines");
        COROUTINES_PACKAGE_FQ_NAME_RELEASE = fqName;
        FqName child = fqName.child(Name.identifier(BitmapPoolType.EXPERIMENTAL));
        Intrinsics.checkNotNullExpressionValue(child, "COROUTINES_PACKAGE_FQ_NAME_RELEASE.child(Name.identifier(\"experimental\"))");
        COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL = child;
        FqName child2 = child.child(Name.identifier("intrinsics"));
        Intrinsics.checkNotNullExpressionValue(child2, "COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL.child(Name.identifier(\"intrinsics\"))");
        COROUTINES_INTRINSICS_PACKAGE_FQ_NAME_EXPERIMENTAL = child2;
        FqName child3 = COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL.child(Name.identifier("Continuation"));
        Intrinsics.checkNotNullExpressionValue(child3, "COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL.child(Name.identifier(\"Continuation\"))");
        CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL = child3;
        FqName child4 = COROUTINES_PACKAGE_FQ_NAME_RELEASE.child(Name.identifier("Continuation"));
        Intrinsics.checkNotNullExpressionValue(child4, "COROUTINES_PACKAGE_FQ_NAME_RELEASE.child(Name.identifier(\"Continuation\"))");
        CONTINUATION_INTERFACE_FQ_NAME_RELEASE = child4;
        Name identifier3 = Name.identifier("kotlin");
        Intrinsics.checkNotNullExpressionValue(identifier3, "identifier(\"kotlin\")");
        BUILT_INS_PACKAGE_NAME = identifier3;
        FqName fqName2 = FqName.topLevel(identifier3);
        Intrinsics.checkNotNullExpressionValue(fqName2, "topLevel(BUILT_INS_PACKAGE_NAME)");
        BUILT_INS_PACKAGE_FQ_NAME = fqName2;
        FqName child5 = fqName2.child(Name.identifier("annotation"));
        Intrinsics.checkNotNullExpressionValue(child5, "BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(\"annotation\"))");
        ANNOTATION_PACKAGE_FQ_NAME = child5;
        FqName child6 = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("collections"));
        Intrinsics.checkNotNullExpressionValue(child6, "BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(\"collections\"))");
        COLLECTIONS_PACKAGE_FQ_NAME = child6;
        FqName child7 = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("ranges"));
        Intrinsics.checkNotNullExpressionValue(child7, "BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(\"ranges\"))");
        RANGES_PACKAGE_FQ_NAME = child7;
        FqName child8 = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("text"));
        Intrinsics.checkNotNullExpressionValue(child8, "BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(\"text\"))");
        TEXT_PACKAGE_FQ_NAME = child8;
        FqName fqName3 = BUILT_INS_PACKAGE_FQ_NAME;
        FqName child9 = fqName3.child(Name.identifier("internal"));
        Intrinsics.checkNotNullExpressionValue(child9, "BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(\"internal\"))");
        BUILT_INS_PACKAGE_FQ_NAMES = SetsKt.setOf(fqName3, COLLECTIONS_PACKAGE_FQ_NAME, RANGES_PACKAGE_FQ_NAME, ANNOTATION_PACKAGE_FQ_NAME, KOTLIN_REFLECT_FQ_NAME, child9, COROUTINES_PACKAGE_FQ_NAME_RELEASE);
    }

    /* compiled from: StandardNames.kt */
    public static final class FqNames {
        public static final FqNames INSTANCE;
        public static final FqNameUnsafe _boolean = INSTANCE.fqNameUnsafe("Boolean");
        public static final FqNameUnsafe _byte = INSTANCE.fqNameUnsafe("Byte");
        public static final FqNameUnsafe _char = INSTANCE.fqNameUnsafe("Char");
        public static final FqNameUnsafe _double = INSTANCE.fqNameUnsafe("Double");
        public static final FqNameUnsafe _enum = INSTANCE.fqNameUnsafe("Enum");
        public static final FqNameUnsafe _float = INSTANCE.fqNameUnsafe("Float");
        public static final FqNameUnsafe _int = INSTANCE.fqNameUnsafe("Int");
        public static final FqNameUnsafe _long = INSTANCE.fqNameUnsafe("Long");
        public static final FqNameUnsafe _short = INSTANCE.fqNameUnsafe("Short");
        public static final FqName annotation = INSTANCE.fqName("Annotation");
        public static final FqName annotationRetention = INSTANCE.annotationName("AnnotationRetention");
        public static final FqName annotationTarget = INSTANCE.annotationName("AnnotationTarget");
        public static final FqNameUnsafe any;
        public static final FqNameUnsafe array = INSTANCE.fqNameUnsafe("Array");
        public static final Map<FqNameUnsafe, PrimitiveType> arrayClassFqNameToPrimitiveType;
        public static final FqNameUnsafe charSequence = INSTANCE.fqNameUnsafe("CharSequence");
        public static final FqNameUnsafe cloneable = INSTANCE.fqNameUnsafe("Cloneable");
        public static final FqName collection = INSTANCE.collectionsFqName("Collection");
        public static final FqName comparable = INSTANCE.fqName("Comparable");
        public static final FqName deprecated = INSTANCE.fqName("Deprecated");
        public static final FqName deprecatedSinceKotlin = INSTANCE.fqName("DeprecatedSinceKotlin");
        public static final FqName deprecationLevel = INSTANCE.fqName("DeprecationLevel");
        public static final FqName extensionFunctionType = INSTANCE.fqName("ExtensionFunctionType");
        public static final Map<FqNameUnsafe, PrimitiveType> fqNameToPrimitiveType;
        public static final FqNameUnsafe functionSupertype = INSTANCE.fqNameUnsafe("Function");
        public static final FqNameUnsafe intRange = INSTANCE.rangesFqName("IntRange");
        public static final FqName iterable = INSTANCE.collectionsFqName("Iterable");
        public static final FqName iterator = INSTANCE.collectionsFqName("Iterator");
        public static final FqNameUnsafe kCallable = reflect("KCallable");
        public static final FqNameUnsafe kClass = reflect("KClass");
        public static final FqNameUnsafe kDeclarationContainer = reflect("KDeclarationContainer");
        public static final FqNameUnsafe kMutableProperty0 = reflect("KMutableProperty0");
        public static final FqNameUnsafe kMutableProperty1 = reflect("KMutableProperty1");
        public static final FqNameUnsafe kMutableProperty2 = reflect("KMutableProperty2");
        public static final FqNameUnsafe kMutablePropertyFqName = reflect("KMutableProperty");
        public static final ClassId kProperty;
        public static final FqNameUnsafe kProperty0 = reflect("KProperty0");
        public static final FqNameUnsafe kProperty1 = reflect("KProperty1");
        public static final FqNameUnsafe kProperty2 = reflect("KProperty2");
        public static final FqNameUnsafe kPropertyFqName = reflect("KProperty");
        public static final FqName list = INSTANCE.collectionsFqName("List");
        public static final FqName listIterator = INSTANCE.collectionsFqName("ListIterator");
        public static final FqNameUnsafe longRange = INSTANCE.rangesFqName("LongRange");
        public static final FqName map;
        public static final FqName mapEntry;
        public static final FqName mustBeDocumented = INSTANCE.annotationName("MustBeDocumented");
        public static final FqName mutableCollection = INSTANCE.collectionsFqName("MutableCollection");
        public static final FqName mutableIterable = INSTANCE.collectionsFqName("MutableIterable");
        public static final FqName mutableIterator = INSTANCE.collectionsFqName("MutableIterator");
        public static final FqName mutableList = INSTANCE.collectionsFqName("MutableList");
        public static final FqName mutableListIterator = INSTANCE.collectionsFqName("MutableListIterator");
        public static final FqName mutableMap;
        public static final FqName mutableMapEntry;
        public static final FqName mutableSet = INSTANCE.collectionsFqName("MutableSet");
        public static final FqNameUnsafe nothing = INSTANCE.fqNameUnsafe("Nothing");
        public static final FqNameUnsafe number = INSTANCE.fqNameUnsafe("Number");
        public static final FqName parameterName = INSTANCE.fqName("ParameterName");
        public static final Set<Name> primitiveArrayTypeShortNames;
        public static final Set<Name> primitiveTypeShortNames;
        public static final FqName publishedApi = INSTANCE.fqName("PublishedApi");
        public static final FqName repeatable = INSTANCE.annotationName("Repeatable");
        public static final FqName replaceWith = INSTANCE.fqName("ReplaceWith");
        public static final FqName retention = INSTANCE.annotationName("Retention");
        public static final FqName set = INSTANCE.collectionsFqName("Set");
        public static final FqNameUnsafe string = INSTANCE.fqNameUnsafe("String");
        public static final FqName suppress = INSTANCE.fqName("Suppress");
        public static final FqName target = INSTANCE.annotationName("Target");
        public static final FqName throwable = INSTANCE.fqName("Throwable");
        public static final ClassId uByte;
        public static final FqName uByteArrayFqName = INSTANCE.fqName("UByteArray");
        public static final FqName uByteFqName = INSTANCE.fqName("UByte");
        public static final ClassId uInt;
        public static final FqName uIntArrayFqName = INSTANCE.fqName("UIntArray");
        public static final FqName uIntFqName = INSTANCE.fqName("UInt");
        public static final ClassId uLong;
        public static final FqName uLongArrayFqName = INSTANCE.fqName("ULongArray");
        public static final FqName uLongFqName = INSTANCE.fqName("ULong");
        public static final ClassId uShort;
        public static final FqName uShortArrayFqName = INSTANCE.fqName("UShortArray");
        public static final FqName uShortFqName = INSTANCE.fqName("UShort");
        public static final FqNameUnsafe unit = INSTANCE.fqNameUnsafe("Unit");
        public static final FqName unsafeVariance = INSTANCE.fqName("UnsafeVariance");

        private FqNames() {
        }

        static {
            FqNames fqNames = new FqNames();
            INSTANCE = fqNames;
            any = fqNames.fqNameUnsafe("Any");
            FqName collectionsFqName = INSTANCE.collectionsFqName("Map");
            map = collectionsFqName;
            FqName child = collectionsFqName.child(Name.identifier("Entry"));
            Intrinsics.checkNotNullExpressionValue(child, "map.child(Name.identifier(\"Entry\"))");
            mapEntry = child;
            FqName collectionsFqName2 = INSTANCE.collectionsFqName("MutableMap");
            mutableMap = collectionsFqName2;
            FqName child2 = collectionsFqName2.child(Name.identifier("MutableEntry"));
            Intrinsics.checkNotNullExpressionValue(child2, "mutableMap.child(Name.identifier(\"MutableEntry\"))");
            mutableMapEntry = child2;
            ClassId classId = ClassId.topLevel(kPropertyFqName.toSafe());
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(kPropertyFqName.toSafe())");
            kProperty = classId;
            ClassId classId2 = ClassId.topLevel(uByteFqName);
            Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(uByteFqName)");
            uByte = classId2;
            ClassId classId3 = ClassId.topLevel(uShortFqName);
            Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(uShortFqName)");
            uShort = classId3;
            ClassId classId4 = ClassId.topLevel(uIntFqName);
            Intrinsics.checkNotNullExpressionValue(classId4, "topLevel(uIntFqName)");
            uInt = classId4;
            ClassId classId5 = ClassId.topLevel(uLongFqName);
            Intrinsics.checkNotNullExpressionValue(classId5, "topLevel(uLongFqName)");
            uLong = classId5;
            HashSet newHashSetWithExpectedSize = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
            int i = 0;
            for (PrimitiveType typeName : PrimitiveType.values()) {
                newHashSetWithExpectedSize.add(typeName.getTypeName());
            }
            Collection collection2 = newHashSetWithExpectedSize;
            primitiveTypeShortNames = newHashSetWithExpectedSize;
            HashSet newHashSetWithExpectedSize2 = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
            for (PrimitiveType arrayTypeName : PrimitiveType.values()) {
                newHashSetWithExpectedSize2.add(arrayTypeName.getArrayTypeName());
            }
            Collection collection3 = newHashSetWithExpectedSize2;
            primitiveArrayTypeShortNames = newHashSetWithExpectedSize2;
            HashMap newHashMapWithExpectedSize = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);
            PrimitiveType[] values = PrimitiveType.values();
            int length = values.length;
            int i2 = 0;
            while (i2 < length) {
                PrimitiveType primitiveType = values[i2];
                i2++;
                FqNames fqNames2 = INSTANCE;
                String asString = primitiveType.getTypeName().asString();
                Intrinsics.checkNotNullExpressionValue(asString, "primitiveType.typeName.asString()");
                newHashMapWithExpectedSize.put(fqNames2.fqNameUnsafe(asString), primitiveType);
            }
            fqNameToPrimitiveType = newHashMapWithExpectedSize;
            HashMap newHashMapWithExpectedSize2 = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);
            PrimitiveType[] values2 = PrimitiveType.values();
            int length2 = values2.length;
            while (i < length2) {
                PrimitiveType primitiveType2 = values2[i];
                i++;
                FqNames fqNames3 = INSTANCE;
                String asString2 = primitiveType2.getArrayTypeName().asString();
                Intrinsics.checkNotNullExpressionValue(asString2, "primitiveType.arrayTypeName.asString()");
                newHashMapWithExpectedSize2.put(fqNames3.fqNameUnsafe(asString2), primitiveType2);
            }
            arrayClassFqNameToPrimitiveType = newHashMapWithExpectedSize2;
        }

        private final FqNameUnsafe fqNameUnsafe(String str) {
            FqNameUnsafe unsafe = fqName(str).toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "fqName(simpleName).toUnsafe()");
            return unsafe;
        }

        private final FqName fqName(String str) {
            FqName child = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(str));
            Intrinsics.checkNotNullExpressionValue(child, "BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(simpleName))");
            return child;
        }

        private final FqName collectionsFqName(String str) {
            FqName child = StandardNames.COLLECTIONS_PACKAGE_FQ_NAME.child(Name.identifier(str));
            Intrinsics.checkNotNullExpressionValue(child, "COLLECTIONS_PACKAGE_FQ_NAME.child(Name.identifier(simpleName))");
            return child;
        }

        private final FqNameUnsafe rangesFqName(String str) {
            FqNameUnsafe unsafe = StandardNames.RANGES_PACKAGE_FQ_NAME.child(Name.identifier(str)).toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "RANGES_PACKAGE_FQ_NAME.child(Name.identifier(simpleName)).toUnsafe()");
            return unsafe;
        }

        @JvmStatic
        public static final FqNameUnsafe reflect(String str) {
            Intrinsics.checkNotNullParameter(str, "simpleName");
            FqNameUnsafe unsafe = StandardNames.KOTLIN_REFLECT_FQ_NAME.child(Name.identifier(str)).toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "KOTLIN_REFLECT_FQ_NAME.child(Name.identifier(simpleName)).toUnsafe()");
            return unsafe;
        }

        private final FqName annotationName(String str) {
            FqName child = StandardNames.ANNOTATION_PACKAGE_FQ_NAME.child(Name.identifier(str));
            Intrinsics.checkNotNullExpressionValue(child, "ANNOTATION_PACKAGE_FQ_NAME.child(Name.identifier(simpleName))");
            return child;
        }
    }

    @JvmStatic
    public static final String getFunctionName(int i) {
        return Intrinsics.stringPlus("Function", Integer.valueOf(i));
    }

    @JvmStatic
    public static final ClassId getFunctionClassId(int i) {
        return new ClassId(BUILT_INS_PACKAGE_FQ_NAME, Name.identifier(getFunctionName(i)));
    }

    @JvmStatic
    public static final String getSuspendFunctionName(int i) {
        return Intrinsics.stringPlus(FunctionClassKind.SuspendFunction.getClassNamePrefix(), Integer.valueOf(i));
    }

    @JvmStatic
    public static final boolean isPrimitiveArray(FqNameUnsafe fqNameUnsafe) {
        Intrinsics.checkNotNullParameter(fqNameUnsafe, "arrayFqName");
        return FqNames.arrayClassFqNameToPrimitiveType.get(fqNameUnsafe) != null;
    }

    @JvmStatic
    public static final FqName getPrimitiveFqName(PrimitiveType primitiveType) {
        Intrinsics.checkNotNullParameter(primitiveType, "primitiveType");
        FqName child = BUILT_INS_PACKAGE_FQ_NAME.child(primitiveType.getTypeName());
        Intrinsics.checkNotNullExpressionValue(child, "BUILT_INS_PACKAGE_FQ_NAME.child(primitiveType.typeName)");
        return child;
    }
}
