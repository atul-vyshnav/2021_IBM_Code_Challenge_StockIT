package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TuplesKt;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.BuiltInAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* compiled from: functionTypes.kt */
public final class FunctionTypesKt {
    public static final boolean isFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor declarationDescriptor = kotlinType.getConstructor().getDeclarationDescriptor();
        return (declarationDescriptor == null ? null : getFunctionalClassKind((DeclarationDescriptor) declarationDescriptor)) == FunctionClassKind.Function;
    }

    public static final boolean isSuspendFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor declarationDescriptor = kotlinType.getConstructor().getDeclarationDescriptor();
        return (declarationDescriptor == null ? null : getFunctionalClassKind((DeclarationDescriptor) declarationDescriptor)) == FunctionClassKind.SuspendFunction;
    }

    public static final boolean isBuiltinFunctionalType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor declarationDescriptor = kotlinType.getConstructor().getDeclarationDescriptor();
        return Intrinsics.areEqual((Object) declarationDescriptor == null ? null : Boolean.valueOf(isBuiltinFunctionalClassDescriptor(declarationDescriptor)), (Object) true);
    }

    public static final boolean isBuiltinFunctionalClassDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        FunctionClassKind functionalClassKind = getFunctionalClassKind(declarationDescriptor);
        return functionalClassKind == FunctionClassKind.Function || functionalClassKind == FunctionClassKind.SuspendFunction;
    }

    public static final boolean isBuiltinExtensionFunctionalType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isBuiltinFunctionalType(kotlinType) && isTypeAnnotatedWithExtensionFunctionType(kotlinType);
    }

    private static final boolean isTypeAnnotatedWithExtensionFunctionType(KotlinType kotlinType) {
        return kotlinType.getAnnotations().findAnnotation(StandardNames.FqNames.extensionFunctionType) != null;
    }

    public static final FunctionClassKind getFunctionalClassKind(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if ((declarationDescriptor instanceof ClassDescriptor) && KotlinBuiltIns.isUnderKotlinPackage(declarationDescriptor)) {
            return getFunctionalClassKind(DescriptorUtilsKt.getFqNameUnsafe(declarationDescriptor));
        }
        return null;
    }

    private static final FunctionClassKind getFunctionalClassKind(FqNameUnsafe fqNameUnsafe) {
        if (!fqNameUnsafe.isSafe() || fqNameUnsafe.isRoot()) {
            return null;
        }
        FunctionClassKind.Companion companion = FunctionClassKind.Companion;
        String asString = fqNameUnsafe.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(asString, "shortName().asString()");
        FqName parent = fqNameUnsafe.toSafe().parent();
        Intrinsics.checkNotNullExpressionValue(parent, "toSafe().parent()");
        return companion.getFunctionalClassKind(asString, parent);
    }

    public static final KotlinType getReceiverTypeFromFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        boolean isBuiltinFunctionalType = isBuiltinFunctionalType(kotlinType);
        if (_Assertions.ENABLED && !isBuiltinFunctionalType) {
            throw new AssertionError(Intrinsics.stringPlus("Not a function type: ", kotlinType));
        } else if (isTypeAnnotatedWithExtensionFunctionType(kotlinType)) {
            return ((TypeProjection) CollectionsKt.first(kotlinType.getArguments())).getType();
        } else {
            return null;
        }
    }

    public static final KotlinType getReturnTypeFromFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        boolean isBuiltinFunctionalType = isBuiltinFunctionalType(kotlinType);
        if (!_Assertions.ENABLED || isBuiltinFunctionalType) {
            KotlinType type = ((TypeProjection) CollectionsKt.last(kotlinType.getArguments())).getType();
            Intrinsics.checkNotNullExpressionValue(type, "arguments.last().type");
            return type;
        }
        throw new AssertionError(Intrinsics.stringPlus("Not a function type: ", kotlinType));
    }

    public static final List<TypeProjection> getValueParameterTypesFromFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        boolean isBuiltinFunctionalType = isBuiltinFunctionalType(kotlinType);
        if (!_Assertions.ENABLED || isBuiltinFunctionalType) {
            List<TypeProjection> arguments = kotlinType.getArguments();
            int isBuiltinExtensionFunctionalType = isBuiltinExtensionFunctionalType(kotlinType);
            boolean z = true;
            int size = arguments.size() - 1;
            if (isBuiltinExtensionFunctionalType > size) {
                z = false;
            }
            if (!_Assertions.ENABLED || z) {
                return arguments.subList(isBuiltinExtensionFunctionalType, size);
            }
            throw new AssertionError(Intrinsics.stringPlus("Not an exact function type: ", kotlinType));
        }
        throw new AssertionError(Intrinsics.stringPlus("Not a function type: ", kotlinType));
    }

    public static final Name extractParameterNameFromFunctionTypeArgument(KotlinType kotlinType) {
        String str;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        AnnotationDescriptor findAnnotation = kotlinType.getAnnotations().findAnnotation(StandardNames.FqNames.parameterName);
        if (findAnnotation == null) {
            return null;
        }
        Object singleOrNull = CollectionsKt.singleOrNull(findAnnotation.getAllValueArguments().values());
        StringValue stringValue = singleOrNull instanceof StringValue ? (StringValue) singleOrNull : null;
        if (stringValue == null || (str = (String) stringValue.getValue()) == null || !Name.isValidIdentifier(str)) {
            str = null;
        }
        if (str == null) {
            return null;
        }
        return Name.identifier(str);
    }

    public static final List<TypeProjection> getFunctionTypeArgumentProjections(KotlinType kotlinType, List<? extends KotlinType> list, List<Name> list2, KotlinType kotlinType2, KotlinBuiltIns kotlinBuiltIns) {
        Name name;
        Intrinsics.checkNotNullParameter(list, "parameterTypes");
        Intrinsics.checkNotNullParameter(kotlinType2, "returnType");
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "builtIns");
        int i = 0;
        ArrayList arrayList = new ArrayList(list.size() + (kotlinType != null ? 1 : 0) + 1);
        Collection collection = arrayList;
        kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(collection, kotlinType == null ? null : TypeUtilsKt.asTypeProjection(kotlinType));
        for (Object next : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KotlinType kotlinType3 = (KotlinType) next;
            if (list2 == null || (name = list2.get(i)) == null || name.isSpecial()) {
                name = null;
            }
            if (name != null) {
                FqName fqName = StandardNames.FqNames.parameterName;
                Name identifier = Name.identifier("name");
                String asString = name.asString();
                Intrinsics.checkNotNullExpressionValue(asString, "name.asString()");
                kotlinType3 = TypeUtilsKt.replaceAnnotations(kotlinType3, Annotations.Companion.create(CollectionsKt.plus(kotlinType3.getAnnotations(), new BuiltInAnnotationDescriptor(kotlinBuiltIns, fqName, MapsKt.mapOf(TuplesKt.m1160to(identifier, new StringValue(asString)))))));
            }
            collection.add(TypeUtilsKt.asTypeProjection(kotlinType3));
            i = i2;
        }
        arrayList.add(TypeUtilsKt.asTypeProjection(kotlinType2));
        return arrayList;
    }

    public static /* synthetic */ SimpleType createFunctionType$default(KotlinBuiltIns kotlinBuiltIns, Annotations annotations, KotlinType kotlinType, List list, List list2, KotlinType kotlinType2, boolean z, int i, Object obj) {
        return createFunctionType(kotlinBuiltIns, annotations, kotlinType, list, list2, kotlinType2, (i & 64) != 0 ? false : z);
    }

    public static final SimpleType createFunctionType(KotlinBuiltIns kotlinBuiltIns, Annotations annotations, KotlinType kotlinType, List<? extends KotlinType> list, List<Name> list2, KotlinType kotlinType2, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "builtIns");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(list, "parameterTypes");
        Intrinsics.checkNotNullParameter(kotlinType2, "returnType");
        List<TypeProjection> functionTypeArgumentProjections = getFunctionTypeArgumentProjections(kotlinType, list, list2, kotlinType2, kotlinBuiltIns);
        int size = list.size();
        if (kotlinType != null) {
            size++;
        }
        ClassDescriptor functionDescriptor = getFunctionDescriptor(kotlinBuiltIns, size, z);
        if (kotlinType != null) {
            annotations = withExtensionFunctionAnnotation(annotations, kotlinBuiltIns);
        }
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        return KotlinTypeFactory.simpleNotNullType(annotations, functionDescriptor, functionTypeArgumentProjections);
    }

    public static final Annotations withExtensionFunctionAnnotation(Annotations annotations, KotlinBuiltIns kotlinBuiltIns) {
        Intrinsics.checkNotNullParameter(annotations, "<this>");
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "builtIns");
        return annotations.hasAnnotation(StandardNames.FqNames.extensionFunctionType) ? annotations : Annotations.Companion.create(CollectionsKt.plus(annotations, new BuiltInAnnotationDescriptor(kotlinBuiltIns, StandardNames.FqNames.extensionFunctionType, MapsKt.emptyMap())));
    }

    public static final ClassDescriptor getFunctionDescriptor(KotlinBuiltIns kotlinBuiltIns, int i, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "builtIns");
        ClassDescriptor suspendFunction = z ? kotlinBuiltIns.getSuspendFunction(i) : kotlinBuiltIns.getFunction(i);
        Intrinsics.checkNotNullExpressionValue(suspendFunction, "if (isSuspendFunction) builtIns.getSuspendFunction(parameterCount) else builtIns.getFunction(parameterCount)");
        return suspendFunction;
    }
}
