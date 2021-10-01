package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.KotlinRetention;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.KotlinTarget;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaEnumValueAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;

/* compiled from: JavaAnnotationMapper.kt */
public final class JavaAnnotationTargetMapper {
    public static final JavaAnnotationTargetMapper INSTANCE = new JavaAnnotationTargetMapper();
    private static final Map<String, KotlinRetention> retentionNameList = MapsKt.mapOf(TuplesKt.m1160to("RUNTIME", KotlinRetention.RUNTIME), TuplesKt.m1160to("CLASS", KotlinRetention.BINARY), TuplesKt.m1160to("SOURCE", KotlinRetention.SOURCE));
    private static final Map<String, EnumSet<KotlinTarget>> targetNameLists = MapsKt.mapOf(TuplesKt.m1160to("PACKAGE", EnumSet.noneOf(KotlinTarget.class)), TuplesKt.m1160to("TYPE", EnumSet.of(KotlinTarget.CLASS, KotlinTarget.FILE)), TuplesKt.m1160to("ANNOTATION_TYPE", EnumSet.of(KotlinTarget.ANNOTATION_CLASS)), TuplesKt.m1160to("TYPE_PARAMETER", EnumSet.of(KotlinTarget.TYPE_PARAMETER)), TuplesKt.m1160to("FIELD", EnumSet.of(KotlinTarget.FIELD)), TuplesKt.m1160to("LOCAL_VARIABLE", EnumSet.of(KotlinTarget.LOCAL_VARIABLE)), TuplesKt.m1160to("PARAMETER", EnumSet.of(KotlinTarget.VALUE_PARAMETER)), TuplesKt.m1160to("CONSTRUCTOR", EnumSet.of(KotlinTarget.CONSTRUCTOR)), TuplesKt.m1160to("METHOD", EnumSet.of(KotlinTarget.FUNCTION, KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER)), TuplesKt.m1160to("TYPE_USE", EnumSet.of(KotlinTarget.TYPE)));

    private JavaAnnotationTargetMapper() {
    }

    public final Set<KotlinTarget> mapJavaTargetArgumentByName(String str) {
        EnumSet enumSet = targetNameLists.get(str);
        return enumSet == null ? SetsKt.emptySet() : enumSet;
    }

    public final ConstantValue<?> mapJavaTargetArguments$descriptors_jvm(List<? extends JavaAnnotationArgument> list) {
        Intrinsics.checkNotNullParameter(list, "arguments");
        Collection arrayList = new ArrayList();
        for (Object next : list) {
            if (next instanceof JavaEnumValueAnnotationArgument) {
                arrayList.add(next);
            }
        }
        Collection arrayList2 = new ArrayList();
        for (JavaEnumValueAnnotationArgument entryName : (List) arrayList) {
            Name entryName2 = entryName.getEntryName();
            CollectionsKt.addAll(arrayList2, mapJavaTargetArgumentByName(entryName2 == null ? null : entryName2.asString()));
        }
        Iterable<KotlinTarget> iterable = (List) arrayList2;
        Collection arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (KotlinTarget name : iterable) {
            ClassId classId = ClassId.topLevel(StandardNames.FqNames.annotationTarget);
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(StandardNames.FqNames.annotationTarget)");
            Name identifier = Name.identifier(name.name());
            Intrinsics.checkNotNullExpressionValue(identifier, "identifier(kotlinTarget.name)");
            arrayList3.add(new EnumValue(classId, identifier));
        }
        return new ArrayValue((List) arrayList3, JavaAnnotationTargetMapper$mapJavaTargetArguments$1.INSTANCE);
    }

    public final ConstantValue<?> mapJavaRetentionArgument$descriptors_jvm(JavaAnnotationArgument javaAnnotationArgument) {
        EnumValue enumValue = null;
        JavaEnumValueAnnotationArgument javaEnumValueAnnotationArgument = javaAnnotationArgument instanceof JavaEnumValueAnnotationArgument ? (JavaEnumValueAnnotationArgument) javaAnnotationArgument : null;
        if (javaEnumValueAnnotationArgument != null) {
            Map<String, KotlinRetention> map = retentionNameList;
            Name entryName = javaEnumValueAnnotationArgument.getEntryName();
            KotlinRetention kotlinRetention = map.get(entryName == null ? null : entryName.asString());
            if (kotlinRetention != null) {
                ClassId classId = ClassId.topLevel(StandardNames.FqNames.annotationRetention);
                Intrinsics.checkNotNullExpressionValue(classId, "topLevel(StandardNames.FqNames.annotationRetention)");
                Name identifier = Name.identifier(kotlinRetention.name());
                Intrinsics.checkNotNullExpressionValue(identifier, "identifier(retention.name)");
                enumValue = new EnumValue(classId, identifier);
            }
        }
        return enumValue;
    }
}
