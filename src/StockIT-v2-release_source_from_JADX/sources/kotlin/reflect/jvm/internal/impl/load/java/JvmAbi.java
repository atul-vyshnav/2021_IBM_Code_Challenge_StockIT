package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import kotlin.text.StringsKt;

/* compiled from: JvmAbi.kt */
public final class JvmAbi {
    public static final JvmAbi INSTANCE = new JvmAbi();
    public static final FqName JVM_FIELD_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmField");
    private static final ClassId REFLECTION_FACTORY_IMPL;

    private JvmAbi() {
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(FqName(\"kotlin.reflect.jvm.internal.ReflectionFactoryImpl\"))");
        REFLECTION_FACTORY_IMPL = classId;
    }

    @JvmStatic
    public static final boolean isGetterName(String str) {
        Intrinsics.checkNotNullParameter(str, "name");
        return StringsKt.startsWith$default(str, "get", false, 2, (Object) null) || StringsKt.startsWith$default(str, "is", false, 2, (Object) null);
    }

    @JvmStatic
    public static final boolean isSetterName(String str) {
        Intrinsics.checkNotNullParameter(str, "name");
        return StringsKt.startsWith$default(str, "set", false, 2, (Object) null);
    }

    @JvmStatic
    public static final String getterName(String str) {
        Intrinsics.checkNotNullParameter(str, "propertyName");
        return startsWithIsPrefix(str) ? str : Intrinsics.stringPlus("get", CapitalizeDecapitalizeKt.capitalizeAsciiOnly(str));
    }

    @JvmStatic
    public static final String setterName(String str) {
        String str2;
        Intrinsics.checkNotNullParameter(str, "propertyName");
        if (startsWithIsPrefix(str)) {
            str2 = str.substring(2);
            Intrinsics.checkNotNullExpressionValue(str2, "(this as java.lang.String).substring(startIndex)");
        } else {
            str2 = CapitalizeDecapitalizeKt.capitalizeAsciiOnly(str);
        }
        return Intrinsics.stringPlus("set", str2);
    }

    @JvmStatic
    public static final boolean startsWithIsPrefix(String str) {
        Intrinsics.checkNotNullParameter(str, "name");
        if (!StringsKt.startsWith$default(str, "is", false, 2, (Object) null) || str.length() == 2) {
            return false;
        }
        char charAt = str.charAt(2);
        if (Intrinsics.compare(97, (int) charAt) > 0 || Intrinsics.compare((int) charAt, 122) > 0) {
            return true;
        }
        return false;
    }
}
